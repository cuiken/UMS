package com.tp.action.nav;

import java.io.File;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.action.CRUDActionSupport;
import com.tp.entity.nav.Board;
import com.tp.entity.nav.BoardIcon;
import com.tp.service.nav.NavigatorService;
import com.tp.utils.Constants;
import com.tp.utils.FileUtils;
import com.tp.utils.UUIDGenerator;

@Namespace("/nav")
@Results({ @Result(name = CRUDActionSupport.RELOAD, location = "board.action", type = "redirect") })
public class BoardAction extends CRUDActionSupport<Board> {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Board entity;
	private List<Board> boards;
	private NavigatorService navigatorService;
	private File upload;

	@Override
	public Board getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {
		boards = navigatorService.getAllBoards();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	public String save() throws Exception {
		
		navigatorService.saveBoard(entity);
		if (upload != null) {
			List<File> icons = FileUtils.unZip(upload, Constants.NAV_STORAGE);
			for (File file : icons) {
				entity.getIcons().clear();
				BoardIcon icon = new BoardIcon();
				icon.setBoard(entity);
				icon.setName(file.getName());
				icon.setValue(file.getPath());
				icon.setLevel(FileUtils.getIconLevel(file.getName()));
				navigatorService.saveBoardIcon(icon);
			}
		}
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		navigatorService.deleteBoard(id);
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new Board();
			entity.setUuid(UUIDGenerator.randomLong());
		} else {
			entity = navigatorService.getBoard(id);
		}
	}

	public List<Board> getBoards() {
		return boards;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Autowired
	public void setNavigatorService(NavigatorService navigatorService) {
		this.navigatorService = navigatorService;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

}
