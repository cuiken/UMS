package com.tp.action.nav;

import java.io.File;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tp.action.CRUDActionSupport;
import com.tp.entity.nav.Board;
import com.tp.entity.nav.Tag;
import com.tp.entity.nav.TagIcon;
import com.tp.service.nav.NavigatorService;
import com.tp.utils.Constants;
import com.tp.utils.FileUtils;
import com.tp.utils.UUIDGenerator;

@Namespace("/nav")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "nav-tag.action", type = "redirect") })
public class NavTagAction extends CRUDActionSupport<Tag> {

	private static final long serialVersionUID = 1L;

	private Tag entity;
	private Long id;
	private Long boardId;
	private List<Tag> tags = Lists.newArrayList();
	private File upload;

	private NavigatorService navigatorService;

	@Override
	public Tag getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {
		tags = navigatorService.getAllTags();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	public String save() throws Exception {
		
		if (boardId != null) {
			Board board = navigatorService.getBoard(boardId);
			entity.setBoard(board);
		}
		navigatorService.saveTag(entity);
		if (upload != null) {
			List<File> icons = FileUtils.unZip(upload, Constants.NAV_STORAGE);
			for (File file : icons) {
				entity.getIcons().clear();
				TagIcon icon = new TagIcon();
				icon.setTag(entity);
				icon.setName(file.getName());
				icon.setValue(file.getPath());
				icon.setLevel(FileUtils.getIconLevel(file.getName()));
				navigatorService.saveTagIcon(icon);
			}
		}

		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		navigatorService.deleteTag(id);
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new Tag();
			entity.setUuid(UUIDGenerator.randomLong());
		} else {
			entity = navigatorService.getNavTag(id);
		}
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public List<Board> getBoards() {
		return navigatorService.getAllBoards();
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	@Autowired
	public void setNavigatorService(NavigatorService navigatorService) {
		this.navigatorService = navigatorService;
	}
}
