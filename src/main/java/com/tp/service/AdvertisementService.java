package com.tp.service;

import com.google.common.collect.Lists;
import com.tp.cache.MemcachedObjectType;
import com.tp.cache.SpyMemcachedClient;
import com.tp.dao.AdvertisementDao;
import com.tp.dto.AdDTO;
import com.tp.entity.Advertisement;
import com.tp.mapper.BeanMapper;
import com.tp.mapper.JaxbMapper;
import com.tp.orm.Page;
import com.tp.orm.PageRequest;
import com.tp.orm.PropertyFilter;
import com.tp.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class AdvertisementService {

    private AdvertisementDao advertisementDao;
    private SpyMemcachedClient memcachedClient;

    @Transactional(readOnly = false)
    public void save(Advertisement entity) {
        memcachedClient.delete(MemcachedObjectType.AD_XML.getPrefix()+entity.getStore());
        advertisementDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        advertisementDao.delete(id);
    }

    public Advertisement get(Long id) {
        return advertisementDao.get(id);
    }

    public Page<Advertisement> searchAdvertisement(final Page<Advertisement> page, final List<PropertyFilter> filters) {
        return advertisementDao.findPage(page, filters);
    }

    public String toXml(final Page<Advertisement> page, final List<PropertyFilter> filters,String store) {
        String xml = memcachedClient.get(MemcachedObjectType.AD_XML.getPrefix()+store);
        if (xml == null) {
            List<Advertisement> ads = searchAdvertisement(page, filters).getResult();
            xml = convertListToXml(ads);
            memcachedClient.set(MemcachedObjectType.AD_XML.getPrefix()+store, MemcachedObjectType.AD_XML.getExpiredTime(), xml);
        }
        return xml;
    }

    private String convertListToXml(List<Advertisement> ads) {
       List<AdDTO> dtos= BeanMapper.mapList(ads,AdDTO.class);

        for (AdDTO dto : dtos) {
            dto.setVersion("1");
            dto.setDownloadUrl(Constants.getDomain()+"/image.action?path="+dto.getDownloadUrl());
        }
        return JaxbMapper.toXml(dtos, "ads", AdDTO.class, Constants.ENCODE_UTF_8);
    }

    public String getJsonByType(String type) {
        Page<Advertisement> page=new Page<Advertisement>();
        List<PropertyFilter> filters = Lists.newArrayList();
        filters.add(new PropertyFilter("EQS_dtype", type));
        filters.add(new PropertyFilter("EQS_store", Constants.ST_LOCK));
        filters.add(new PropertyFilter("EQL_status", "1"));
        if (!page.isOrderBySetted()) {
            page.setOrderBy("sort");
            page.setOrderDir(PageRequest.Sort.ASC);
        }
        page = searchAdvertisement(page, filters);

        return toJson(page.getResult());
    }

    private String toJson(List<Advertisement> ads) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (Advertisement ad : ads) {
            buffer.append("{");
            buffer.append("\"pic\":\"/UMS/image.action?path=" + ad.getImgLink() + "\"");
            buffer.append(",");
            buffer.append("\"href\":\"" + ad.getLink() + "\"");
            buffer.append(",");
            buffer.append("\"ext\":" + true);
            buffer.append("}");
            buffer.append(",");
        }
        String json = StringUtils.substringBeforeLast(buffer.toString(), ",");
        json += "]";
        return json;
    }

    @Autowired
    public void setAdvertisementDao(AdvertisementDao advertisementDao) {
        this.advertisementDao = advertisementDao;
    }

    @Autowired
    public void setMemcachedClient(SpyMemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}
