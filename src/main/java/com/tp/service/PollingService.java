package com.tp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.PollingDao;
import com.tp.dao.PollingPreviewDao;
import com.tp.entity.Polling;
import com.tp.entity.PollingPreview;
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

	//polling manager
	public void savePolling(Polling entity) {
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

	public List<Polling> findByStore(String store) {
		return pollingDao.findBy("store", store);
	}

	public String toXml(List<Polling> polls) throws Exception {
		StringBuilder buffer = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("<service>");
		for (Polling poll : polls) {
			if (poll.getStatus() == 0L)
				continue;
			buffer.append("<poll  id=\"" + poll.getUuid() + "\">");
			buffer.append("<type>" + poll.getSerType() + "</type>");
			buffer.append("<name>" + poll.getName() + "</name>");
			buffer.append("<description>" + poll.getSerDesc() + "</description>");
			buffer.append("<link>" + poll.getLink() + "</link>");
			buffer.append("<showBegin>" + poll.getShowBegin() + "</showBegin>");
			buffer.append("<showEnd>" + poll.getShowEnd() + "</showEnd>");
			buffer.append("<showPerday>" + poll.getShowPerday() + "</showPerday>");
			buffer.append("<showTotal>" + poll.getShowTotal() + "</showTotal>");
			buffer.append("<previews>");
			if (!poll.getPreviews().isEmpty()) {
				for (PollingPreview preview : poll.getPreviews()) {
					String ext = FileUtils.getExtension(preview.getName());
					File image = new File(Constants.LOCKER_STORAGE, preview.getAddr());
					String fname = "default";
					InputStream fileStream = null;
					try {
						fileStream = new FileInputStream(image);
						byte[] input = Digests.md5(fileStream);
						fname = Encodes.encodeHex(input);
					} finally {
						IOUtils.closeQuietly(fileStream);
					}
					buffer.append("<preview>");
					buffer.append("<name>" + fname + "." + ext + "</name>");
					buffer.append("<spec>" + preview.getSpec() + "</spec>");
					buffer.append("<link>" + Constants.getDomain() + "/image.action?path="
							+ URLEncoder.encode(preview.getAddr(), "UTF-8") + "</link>");

					buffer.append("</preview>");
				}
			}
			buffer.append("</previews>");
			buffer.append("</poll>");
		}
		buffer.append("</service>");
		return buffer.toString();
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
}
