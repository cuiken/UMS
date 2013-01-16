package com.tp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tp.cache.MemcachedObjectType;
import com.tp.cache.SpyMemcachedClient;
import com.tp.dao.PollingDao;
import com.tp.dao.PollingPreviewDao;
import com.tp.dto.PollDTO;
import com.tp.dto.PollDTO.Preview;
import com.tp.entity.Polling;
import com.tp.entity.PollingPreview;
import com.tp.mapper.BeanMapper;
import com.tp.mapper.JaxbMapper;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.utils.Constants;
import com.tp.utils.Digests;
import com.tp.utils.Encodes;
import com.tp.utils.FileUtils;

@Component
@Transactional
public class PollingService {

	private PollingDao pollingDao;
	private PollingPreviewDao pollingPreviewDao;
	private SpyMemcachedClient spyMemcachedClient;

	//polling manager
	public void savePolling(Polling entity) {
		spyMemcachedClient.delete(MemcachedObjectType.POLL_XML.getPrefix() + entity.getStore());
		pollingDao.save(entity);
	}

	public Polling getPolling(Long id) {
		return pollingDao.get(id);
	}

	public Page<Polling> searchPage(final Page<Polling> page, final List<PropertyFilter> filters) {
		return pollingDao.findPage(page, filters);
	}

	public void deletePolling(Long id) {
		pollingDao.delete(id);
	}

	public String findPollXmlByStore(String store) {
		List<Polling> polls = pollingDao.findBy("store", store);
		List<PollDTO> dtos = Lists.newArrayList();
		for (Polling poll : polls) {
			if (poll.getStatus() == 0L) {
				continue;
			}
			dtos.add(BeanMapper.map(poll, PollDTO.class));
		}
		for (PollDTO dto : dtos) { //低效率做法，设置属性
			if (!dto.getPreviews().isEmpty()) {
				for (Preview preview : dto.getPreviews()) {
					String ext = FileUtils.getExtension(preview.getName());
					File image = new File(Constants.LOCKER_STORAGE, preview.getLink());
					String fname = "default";
					InputStream fileStream = null;
					try {
						fileStream = new FileInputStream(image);
						byte[] input = Digests.md5(fileStream);
						fname = Encodes.encodeHex(input);
					} catch (Exception e) {

					} finally {
						IOUtils.closeQuietly(fileStream);
					}
					preview.setName(fname + "." + ext);
					preview.setLink(Constants.getDomain() + "/image.action?path=" + preview.getLink());
				}

			}
		}
		return JaxbMapper.toXml(dtos, "service", PollDTO.class, Constants.ENCODE_UTF_8);
	}

	public String toXml(String store) {
		String json = spyMemcachedClient.get(MemcachedObjectType.POLL_XML.getPrefix() + store);
		if (json == null) {
			json = findPollXmlByStore(store);
			spyMemcachedClient.set(MemcachedObjectType.POLL_XML.getPrefix() + store,
					MemcachedObjectType.POLL_XML.getExpiredTime(), json);
		}
		return json;
	}

	//preview manager
	public void savePPreview(PollingPreview entity) {
		pollingPreviewDao.save(entity);
	}

	public PollingPreview getPPreview(Long id) {
		return pollingPreviewDao.get(id);
	}

	public void deletePollPreview(PollingPreview entity) {
		pollingPreviewDao.delete(entity);
	}

	public void deletePollPreviews(List<PollingPreview> previews) {
		for (PollingPreview entity : previews) {
			pollingPreviewDao.delete(entity);
		}
	}

	@Autowired
	public void setPollingDao(PollingDao pollingDao) {
		this.pollingDao = pollingDao;
	}

	@Autowired
	public void setPollingPreviewDao(PollingPreviewDao pollingPreviewDao) {
		this.pollingPreviewDao = pollingPreviewDao;
	}

	@Autowired
	public void setSpyMemcachedClient(SpyMemcachedClient spyMemcachedClient) {
		this.spyMemcachedClient = spyMemcachedClient;
	}
}
