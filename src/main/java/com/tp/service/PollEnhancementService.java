package com.tp.service;

import com.tp.cache.MemcachedObjectType;
import com.tp.cache.SpyMemcachedClient;
import com.tp.dao.PollEnhancementDao;
import com.tp.dto.Poll2DTO;
import com.tp.entity.PollEnhancement;
import com.tp.mapper.BeanMapper;
import com.tp.mapper.JaxbMapper;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: ken.cui
 * Date: 13-3-26
 * Time: 上午10:31
 */
@Component
@Transactional
public class PollEnhancementService {
    private PollEnhancementDao pollEnhancementDao;
    private SpyMemcachedClient spyMemcachedClient;

    public PollEnhancement get(Long id) {
        return pollEnhancementDao.get(id);
    }

    public void delete(Long id) {
        pollEnhancementDao.delete(id);
    }

    public void save(PollEnhancement entity) {
        spyMemcachedClient.delete(MemcachedObjectType.POLL2_XML.getPrefix());
        pollEnhancementDao.save(entity);
    }

    public Page<PollEnhancement> searchPage(final Page<PollEnhancement> page, final List<PropertyFilter> filters) {
        return pollEnhancementDao.findPage(page, filters);
    }

    public String toXml(final Page<PollEnhancement> page, final List<PropertyFilter> filters) {
        String key = MemcachedObjectType.POLL2_XML.getPrefix();
        String xml = spyMemcachedClient.get(key);
        if (xml == null) {
            List<PollEnhancement> polls = searchPage(page, filters).getResult();
            xml = convertListToXml(polls);
            spyMemcachedClient.set(key, MemcachedObjectType.POLL2_XML.getExpiredTime(), xml);
        }
        return xml;
    }

    private String convertListToXml(List<PollEnhancement> polls) {
        List<Poll2DTO> dtos = BeanMapper.mapList(polls, Poll2DTO.class);

        for (Poll2DTO dto : dtos) {
            dto.setImageDownUrl(Constants.getDomain() + "/image.action?path=" + dto.getImageDownUrl());
        }
        return JaxbMapper.toXml(dtos, "service", Poll2DTO.class, Constants.ENCODE_UTF_8);
    }

    @Autowired
    public void setPollEnhancementDao(PollEnhancementDao pollEnhancementDao) {
        this.pollEnhancementDao = pollEnhancementDao;
    }

    @Autowired
    public void setSpyMemcachedClient(SpyMemcachedClient spyMemcachedClient) {
        this.spyMemcachedClient = spyMemcachedClient;
    }
}
