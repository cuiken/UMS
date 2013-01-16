package com.tp.action;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tp.entity.FileType;
import com.tp.entity.Polling;
import com.tp.entity.PollingPreview;
import com.tp.entity.Store;
import com.tp.orm.Page;
import com.tp.orm.PageRequest.Sort;
import com.tp.orm.PropertyFilter;
import com.tp.service.CategoryManager;
import com.tp.service.PollingService;
import com.tp.utils.Constants;
import com.tp.utils.Encodes;
import com.tp.utils.FileUtils;
import com.tp.utils.ServletUtils;
import com.tp.utils.Struts2Utils;
import com.tp.utils.UUIDGenerator;

@Namespace("/poll")
@Results({ @Result(name = CRUDActionSupport.RELOAD, params = { "filter_EQS_store", "${store}" }, location = "polling.action", type = "redirect") })
public class PollingAction extends CRUDActionSupport<Polling> {

	private static final long serialVersionUID = 1L;
	private Polling entity;
	private Long id;
	private Page<Polling> page = new Page<Polling>();
	private List<Integer> sliders = Lists.newArrayList();
	private PollingService pollingService;
	private CategoryManager categoryManager;
	private File pollImage;
	private String pollImageFileName;

	private String store;

	@Override
	public Polling getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		String defaultFilter = Struts2Utils.getParameter("filter_EQS_store");
		if (defaultFilter == null) {
			PropertyFilter filter = new PropertyFilter("EQS_store", "lock");
			filters.add(filter);
		}
		if (!page.isOrderBySetted()) {
			page.setOrderBy("createTime");
			page.setOrderDir(Sort.DESC);
		}
		page = pollingService.searchPage(page, filters);
		sliders = page.getSlider(10);
		return SUCCESS;
	}

	public String toXml() throws Exception {
		String filter = Struts2Utils.getParameter("store");
		String xml=pollingService.toXml(filter);
		ServletUtils.setEtag(Struts2Utils.getResponse(), "W/\"" + Encodes.encodeMd5(xml) + "\"");
		Struts2Utils.renderXml(xml);
		return null;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	public String save() throws Exception {
		pollingService.savePolling(entity);
		savePreview(entity);
		return RELOAD;
	}

	public String change() throws Exception {
		String id = Struts2Utils.getParameter("id");
		Polling poll = pollingService.getPolling(Long.valueOf(id));
		if (poll.getStatus() == 1L)
			poll.setStatus(0L);
		else
			poll.setStatus(1L);
		pollingService.savePolling(poll);
		return RELOAD;
	}

	private void savePreview(Polling poll) throws Exception {
		if (pollImage != null) {
			List<PollingPreview> pps = poll.getPreviews();
			if (pps != null) {
				pollingService.deletePollPreviews(pps);
			}
			List<String> imgExts = Arrays.asList(Constants.IMG_EXTENSION);
			String extension = FileUtils.getExtension(pollImageFileName);
			if (extension.equalsIgnoreCase(FileType.ZIP.getValue())) {
				List<File> uploads = FileUtils.unZip(pollImage, Constants.POLL_STORAGE);
				for (File file : uploads) {
					PollingPreview pp = new PollingPreview();
					pp.setName(file.getName());
					pp.setAddr(file.getPath());
					pp.setSpec(FileUtils.getImageSpec(file.getName()));
					pp.setPolling(poll);
					pollingService.savePPreview(pp);
				}

			} else if (imgExts.contains(extension)) {
				File targetDir = new File(Constants.POLL_STORAGE);
				File targetFile = new File(targetDir, UUIDGenerator.uuid2() + "." + extension);
				org.apache.commons.io.FileUtils.copyFile(pollImage, targetFile);
				PollingPreview pp = new PollingPreview();
				pp.setName(targetFile.getName());
				pp.setAddr(StringUtils.substringAfter(targetFile.getPath(), Constants.LOCKER_STORAGE));
				pp.setSpec(FileUtils.getImageSpec(pollImageFileName));
				pp.setPolling(poll);
				pollingService.savePPreview(pp);
			}
		}
	}

	@Override
	public String delete() throws Exception {
		pollingService.deletePolling(id);
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new Polling();
			entity.setUuid(UUIDGenerator.uuid2());
		} else {
			entity = pollingService.getPolling(id);
		}

	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Store> getStores() {
		return categoryManager.getAllStore();
	}

	public Page<Polling> getPage() {
		return page;
	}

	public List<Integer> getSliders() {
		return sliders;
	}

	public File getPollImage() {
		return pollImage;
	}

	public void setPollImage(File pollImage) {
		this.pollImage = pollImage;
	}

	public String getPollImageFileName() {
		return pollImageFileName;
	}

	public void setPollImageFileName(String pollImageFileName) {
		this.pollImageFileName = pollImageFileName;
	}

	public String getStore() {
		if (store == null || store.isEmpty())
			store = "lock";
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	@Autowired
	public void setPollingService(PollingService pollingService) {
		this.pollingService = pollingService;
	}

	@Autowired
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
}
