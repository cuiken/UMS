package com.tp.action.nav;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.action.CRUDActionSupport;
import com.tp.entity.nav.Board;
import com.tp.entity.nav.HotLink;
import com.tp.entity.nav.Tag;
import com.tp.service.nav.NavigatorService;

@Results({ @Result(name = "blist", location = "board.action", type = "redirect"),
		@Result(name = "tlist", location = "nav-tag.action", type = "redirect") })
public class HotLinkAction extends CRUDActionSupport<HotLink> {

	private static final long serialVersionUID = 1L;
	private HotLink entity;
	private NavigatorService navigatorService;
	private Long id;
	private Long bid;
	private Long tid;

	@Override
	public HotLink getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {

		return SUCCESS;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	public String save() throws Exception {
		if (bid != null) {
			Board b = navigatorService.getBoard(bid);
			entity.setBoard(b);
		}
		if (tid != null) {
			Tag t = navigatorService.getNavTag(tid);
			entity.setTag(t);
		}
		navigatorService.saveHotLink(entity);
		if (tid != null) {
			return "tlist";
		}
		return "blist";
	}

	@Override
	public String delete() throws Exception {

		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new HotLink();
		} else {
			entity = navigatorService.getHotLink(id);
		}

	}

	@Autowired
	public void setNavigatorService(NavigatorService navigatorService) {
		this.navigatorService = navigatorService;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

}
