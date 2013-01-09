package com.tp.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tp.entity.LogCmccResult;
import com.tp.service.LogService;
import com.tp.utils.Encodes;
import com.tp.utils.Struts2Utils;

@Namespace("/log")
public class CmccThemeSyncAction extends ActionSupport {

	String id;
	String sid;
	Integer result;
	String sign;

	String output;

	final private static String KEY = "qj_theme";
	private static final long serialVersionUID = 1L;
	
	private LogService logService;

	@Override
	public String execute() {
		LogCmccResult log=new LogCmccResult();
		log.setResult(String.valueOf(result));
		log.setSid(sid);
		log.setSign(sign);
		log.setThemeId(id);
		logService.saveCmccResult(log);
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("id=").append(id).append("&sid=").append(sid).append("&result=").append(result.toString())
					.append(KEY);
			String md5 = Encodes.encodeMd5(sb.toString());
			if (md5.equalsIgnoreCase(sign)) {
				output = sign;
				Struts2Utils.renderText(output);
				return null;
			} else {
				Struts2Utils.renderText("error:校验码错误！");
			}
		} catch (Exception e) {
			Struts2Utils.renderText("error:必要参数缺失！");
			return null;
		}
		return null;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
