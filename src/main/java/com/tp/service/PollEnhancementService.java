package com.tp.service;

import com.tp.cache.MemcachedObjectType;
import com.tp.cache.SpyMemcachedClient;
import com.tp.dao.PollEnhancementDao;
import com.tp.dao.log.LogAppUseDao;
import com.tp.dto.LogAppUseDTO;
import com.tp.dto.Poll2DTO;
import com.tp.dto.PollCoopDTO;
import com.tp.entity.PollEnhancement;
import com.tp.entity.log.LogAppUse;
import com.tp.mapper.BeanMapper;
import com.tp.mapper.JaxbMapper;
import com.tp.mapper.JsonMapper;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(PollEnhancementService.class);
    private PollEnhancementDao pollEnhancementDao;
    private LogAppUseDao logAppUseDao;
    private SpyMemcachedClient spyMemcachedClient;

    public PollEnhancement get(Long id) {
        return pollEnhancementDao.get(id);
    }

    public void delete(Long id) {
        pollEnhancementDao.delete(id);
    }

    public void save(PollEnhancement entity) {
        if(entity.getDtype().equals("2")){
            spyMemcachedClient.delete(MemcachedObjectType.POLL_COOP.getPrefix());
        }else{
            spyMemcachedClient.delete(MemcachedObjectType.POLL2_XML.getPrefix());
        }
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

    public String toCoopXml(final Page<PollEnhancement> page, final List<PropertyFilter> filters){
        String key = MemcachedObjectType.POLL_COOP.getPrefix();
        String xml = spyMemcachedClient.get(key);
        if (xml == null) {
            List<PollEnhancement> polls = searchPage(page, filters).getResult();
            xml = convertListToCoopXml(polls);
            spyMemcachedClient.set(key, MemcachedObjectType.POLL_COOP.getExpiredTime(), xml);
        }
        return xml;
    }

    public void saveLog(String json) {
        try {
            JsonMapper mapper = JsonMapper.buildNormalMapper();
            LogAppUseDTO dto = mapper.fromJson(json, LogAppUseDTO.class);
            LogAppUse entity = BeanMapper.map(dto, LogAppUse.class);
            logAppUseDao.save(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String convertListToXml(List<PollEnhancement> polls) {
        List<Poll2DTO> dtos = BeanMapper.mapList(polls, Poll2DTO.class);

        for (Poll2DTO dto : dtos) {
            dto.setImageDownUrl(Constants.getDomain() + "/image.action?path=" + dto.getImageDownUrl());
        }
        return JaxbMapper.toXml(dtos, "service", Poll2DTO.class, Constants.ENCODE_UTF_8);
    }

    private String convertListToCoopXml(List<PollEnhancement> polls){
        List<PollCoopDTO> dtos=BeanMapper.mapList(polls,PollCoopDTO.class);
        for(PollCoopDTO dto:dtos){
            dto.setImageDownUrl(Constants.getDomain() + "/image.action?path=" + dto.getImageDownUrl());
        }
        return JaxbMapper.toXml(dtos, "service", PollCoopDTO.class, Constants.ENCODE_UTF_8);
    }

    @Autowired
    public void setPollEnhancementDao(PollEnhancementDao pollEnhancementDao) {
        this.pollEnhancementDao = pollEnhancementDao;
    }

    @Autowired
    public void setSpyMemcachedClient(SpyMemcachedClient spyMemcachedClient) {
        this.spyMemcachedClient = spyMemcachedClient;
    }

    @Autowired
    public void setLogAppUseDao(LogAppUseDao logAppUseDao) {
        this.logAppUseDao = logAppUseDao;
    }
}
