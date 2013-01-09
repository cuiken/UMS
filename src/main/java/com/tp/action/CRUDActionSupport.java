package com.tp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public abstract class CRUDActionSupport<T> extends ActionSupport implements
		ModelDriven<T>, Preparable {

	private static final long serialVersionUID = 1L;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String RELOAD = "reload";

	@Override
	public String execute() throws Exception {

		return list();
	}

	public abstract String list() throws Exception;

	@Override
	public abstract String input() throws Exception;

	public abstract String save() throws Exception;

	public abstract String delete() throws Exception;

	public void prepare() throws Exception {
	}

	public void prepareInput() throws Exception {
		prepareModel();
	}

	public void prepareSave() throws Exception {
		prepareModel();
	}

	protected abstract void prepareModel() throws Exception;
}
