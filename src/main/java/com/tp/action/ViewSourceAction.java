package com.tp.action;

import java.io.File;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionSupport;
import com.tp.cache.SpyMemcachedClient;
import com.tp.utils.ServletUtils;
import com.tp.utils.Struts2Utils;

@Namespace("/report")
public class ViewSourceAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(ViewSourceAction.class);
	private String path;
	private long MAX_FILE_SIZE = 30000000;
	private String userDir = System.getProperty("user.dir");
	private SpyMemcachedClient memcachedClient;
	private List<MemcachedStatus> status=Lists.newArrayList();

	@Override
	public String execute() throws Exception {
		Map<SocketAddress, Map<String, String>> allStatus = memcachedClient.getMemcachedClient().getStats();
		for (Map.Entry<SocketAddress, Map<String, String>> entry : allStatus.entrySet()) {
			MemcachedStatus memstatus = new MemcachedStatus();
			memstatus.setAddress(entry.getKey().toString());
			Map<String, String> values = entry.getValue();
			List<Status> ss = Lists.newArrayList();
			for (Map.Entry<String, String> perStatus : values.entrySet()) {
				Status s = new Status();
				s.setKey(perStatus.getKey());
				s.setValue(perStatus.getValue());
				ss.add(s);
			}
			memstatus.setStatus(ss);
			status.add(memstatus);
		}

		return SUCCESS;
	}

	public String view() throws Exception {
		try {
			HttpServletResponse response = Struts2Utils.getResponse();
			HttpServletRequest request = Struts2Utils.getRequest();
			String userDir = System.getProperty("user.dir");
			String download = request.getParameter("download");
			String fname = request.getParameter("fname");

			if (StringUtils.isBlank(fname)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "fname parametter is required.");
				return null;
			}
			path = StringUtils.strip(path, null);

			File file;
			if (StringUtils.isBlank(path) && userDir != null) {

				file = new File(userDir + "/logs", fname);
			} else {
				file = new File(path, fname);
			}
			OutputStream output = response.getOutputStream();

			if (!file.exists()) {

				Struts2Utils.renderText("文件不存在");
				return null;
			}
			if (file.length() > MAX_FILE_SIZE && download == null) {
				Struts2Utils.renderText("文件过大，请带上下载参数download=true保存到本地");
				return null;
			}
			if (download != null) {
				ServletUtils.setFileDownloadHeader(response, file.getName());
			}
			FileUtils.copyFile(file, output);
			output.flush();
		} catch (Exception e) {
			logger.warn(e.getMessage() + "下载日志文件时非正常中断");
		}
		return null;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUserDir() {
		return userDir;
	}

	public List<MemcachedStatus> getStatus() {
		return status;
	}

	@Autowired
	public void setMemcachedClient(SpyMemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	class MemcachedStatus {
		String address;
		List<Status> status;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public List<Status> getStatus() {
			return status;
		}

		public void setStatus(List<Status> status) {
			this.status = status;
		}

	}

	class Status {
		String key;
		String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
