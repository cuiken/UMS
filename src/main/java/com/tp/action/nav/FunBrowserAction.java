package com.tp.action.nav;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tp.dto.FunBrowserLaunchDTO;
import com.tp.entity.nav.LaunchLog;
import com.tp.mapper.BeanMapper;
import com.tp.mapper.JsonMapper;
import com.tp.service.nav.FunBrowserLaunchService;
import com.tp.utils.Constants;
import com.tp.utils.Struts2Utils;

@Namespace("/nav")
public class FunBrowserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private FunBrowserLaunchService launcherService;

	@Override
	public String execute() throws Exception {

		return compare();
	}

	public String getClient() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();
		request.getRequestDispatcher("/file-download.action?inputPath=client/nav/funbrowser.apk")
				.forward(request, response);
		return null;
	}

	public String compare() throws Exception {
		String version = Struts2Utils.getParameter(Constants.PARA_CLIENT_VERSION);
		if(StringUtils.isNotBlank(version)){
			
		}

		return null;
	}

	public String saveLog() throws Exception {
		String json = Struts2Utils.getParameter("param");
		JsonMapper mapper = JsonMapper.buildNonDefaultMapper();
		FunBrowserLaunchDTO dto = mapper.fromJson(json, FunBrowserLaunchDTO.class);
		LaunchLog log = BeanMapper.map(dto, LaunchLog.class);
		launcherService.save(log);
		return null;
	}

	@Autowired
	public void setLauncherService(FunBrowserLaunchService launcherService) {
		this.launcherService = launcherService;
	}
}
