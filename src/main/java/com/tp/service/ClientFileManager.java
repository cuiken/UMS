package com.tp.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.ClientFileDao;
import com.tp.entity.ClientFile;
import com.tp.utils.Constants;

@Component
@Transactional
public class ClientFileManager {

	private ClientFileDao clientFileDao;

	public List<ClientFile> getAll() {
		return clientFileDao.getAll();
	}

	public void save(ClientFile entity) {
		clientFileDao.save(entity);
	}

	public ClientFile get(Long id) {
		return clientFileDao.get(id);
	}

	public void delete(Long id) {
		clientFileDao.delete(id);
	}

	public ClientFile getClientByVersion(String version) {
		return clientFileDao.findUniqueBy("version", version);
	}

	public String getNewestVersionCode(String dtype) {
		return clientFileDao.getNewestVersionCode(dtype);
	}

	public ClientFile getByVersion(String version) {
		return clientFileDao.findUniqueBy("version", version);
	}

	public String getNewestClient(String versionFromClient, String dtype) {
		if (versionFromClient == null || versionFromClient.isEmpty()) {
			return "";
		}

		if (dtype == null || dtype.isEmpty()) {
			return "";
		}
		String maxVersion = getNewestVersionCode(dtype);
        if(StringUtils.isBlank(maxVersion)){
            maxVersion="1.0.0";
        }

		return compareVersion(versionFromClient, maxVersion);
	}

	public String compareVersion(String clientVersion, String versionInServer) {

		Validate.notNull(versionInServer, "版本号不能为空");

		String maxVersion = matchVersionNumber(versionInServer);
		String vsclient = matchVersionNumber(clientVersion);
		String[] maxvs = StringUtils.split(maxVersion, Constants.DOT_SEPARATOR);
		String[] vs = StringUtils.split(vsclient, Constants.DOT_SEPARATOR);
		if (maxvs.length > 2 && vs.length > 2) {
			if (Integer.parseInt(maxvs[0]) > Integer.parseInt(vs[0])) {
				return versionInServer;
			}
			if (Integer.parseInt(maxvs[0]) == Integer.parseInt(vs[0])
					&& Integer.parseInt(maxvs[1]) > Integer.parseInt(vs[1])) {
				return versionInServer;
			}

			if (Integer.parseInt(maxvs[0]) == Integer.parseInt(vs[0])
					&& Integer.parseInt(maxvs[1]) == Integer.parseInt(vs[1])
					&& Integer.parseInt(maxvs[2]) > Integer.parseInt(vs[2])) {
				return versionInServer;
			}

		}
		return "";
	}

	private String matchVersionNumber(String version) {
		Pattern p = Pattern.compile("[\\d|.]+");
		Matcher m = p.matcher(version);
		while (m.find()) {
			return m.group();
		}
		return "";
	}

	@Autowired
	public void setClientFileDao(ClientFileDao clientFileDao) {
		this.clientFileDao = clientFileDao;
	}
}
