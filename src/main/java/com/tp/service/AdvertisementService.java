package com.tp.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.tp.cache.MemcachedObjectType;
import com.tp.cache.SpyMemcachedClient;
import com.tp.dto.AdDTO;
import com.tp.mapper.BeanMapper;
import com.tp.mapper.JaxbMapper;
import com.tp.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.AdvertisementDao;
import com.tp.entity.Advertisement;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;

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
        List<AdDTO> dtos = Lists.newArrayList();
        for (Advertisement entity : ads) {
            if (entity.getStatus() == 1L) {
                dtos.add(BeanMapper.map(entity, AdDTO.class));
            }
        }

        for (AdDTO dto : dtos) {
            dto.setVersion("1");
            dto.setDownloadUrl(Constants.getDomain()+"/image.action?path="+dto.getDownloadUrl());
        }
        return JaxbMapper.toXml(dtos, "ads", AdDTO.class, Constants.ENCODE_UTF_8);
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
