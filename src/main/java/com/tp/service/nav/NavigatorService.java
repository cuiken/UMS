package com.tp.service.nav;

import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.nav.BoardDao;
import com.tp.dao.nav.BoardIconDao;
import com.tp.dao.nav.HotLinkDao;
import com.tp.dao.nav.NavTagDao;
import com.tp.dao.nav.NavigatorDao;
import com.tp.dao.nav.NavigatorPreviewDao;
import com.tp.dao.nav.TagIconDao;
import com.tp.entity.nav.Board;
import com.tp.entity.nav.BoardIcon;
import com.tp.entity.nav.HotLink;
import com.tp.entity.nav.Navigator;
import com.tp.entity.nav.NavigatorPreview;
import com.tp.entity.nav.Tag;
import com.tp.entity.nav.TagIcon;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;

@Component
@Transactional
public class NavigatorService {

	private NavTagDao tagDao;
	private BoardDao boardDao;
	private NavigatorDao navigatorDao;
	private BoardIconDao boardIconDao;
	private TagIconDao tagIconDao;
	private NavigatorPreviewDao navPreviewDao;
	private HotLinkDao hotLinkDao;

	public HotLink getHotLink(Long id) {
		return hotLinkDao.get(id);
	}

	public void saveHotLink(HotLink entity) {
		hotLinkDao.save(entity);
	}

	public Board getBoardByValue(String name) {
		return boardDao.findUniqueBy("value", name);
	}

	public Board getBoardByUUID(String uuid) {
		return boardDao.findUniqueBy("uuid", uuid);
	}

	public Tag getTagByValue(String name) {
		return tagDao.findUniqueBy("value", name);
	}

	//board icon
	public void saveBoardIcon(BoardIcon entity) {
		boardIconDao.save(entity);
	}

	//tag icon
	public void saveTagIcon(TagIcon entity) {
		tagIconDao.save(entity);
	}

	//nav icon
	public void saveNavIcon(NavigatorPreview entity) {
		navPreviewDao.save(entity);
	}

	//tag manage
	public List<Tag> getAllTags() {
		return tagDao.getAll();
	}

	public Tag getNavTag(Long id) {
		return tagDao.get(id);
	}

	public void saveTag(Tag entity) {
		tagDao.save(entity);
	}

	public void deleteTag(Long id) {
		tagDao.delete(id);
	}

	//board manage
	public List<Board> getAllBoards() {
		return boardDao.getAll();
	}

	public Board getBoard(Long id) {
		return boardDao.get(id);
	}

	public void saveBoard(Board entity) {
		boardDao.save(entity);
	}

	public void deleteBoard(Long id) {
		boardDao.delete(id);
	}

	//nav manage
	public List<Navigator> getAllNav() {
		return navigatorDao.getAll();
	}

	public Page<Navigator> searchNavigator(final Page<Navigator> page, final List<PropertyFilter> filters) {
		return navigatorDao.findPage(page, filters);
	}

	public Navigator getNav(Long id) {
		return navigatorDao.get(id);
	}

	public void deleteNav(Long id) {
		navigatorDao.delete(id);
	}

	public void saveNav(Navigator entity) {
		navigatorDao.save(entity);
	}

	public String toXml() throws Exception {
		StringBuilder buffer = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("<category>");
		List<Board> boards = this.getAllBoards();
		for (Board board : boards) {
			buffer.append("<Button id=\"" + board.getUuid() + "\" bid=\"" + board.getId() + "\" title=\""
					+ board.getName() + "\" div=\"" + board.getValue() + "\">");
			for (Navigator nav : board.getNavigators()) {
				buffer.append("<Button id=\"" + nav.getUuid() + "\" bid=\"" + nav.getId() + "\" title=\""
						+ nav.getName() + "\" link=\"" + URLEncoder.encode(nav.getNavAddr(), "utf-8") + "\"/>");
			}
			for (Tag tag : board.getTags()) {
				buffer.append("<Button id=\"" + tag.getUuid() + "\" bid=\"" + tag.getId() + "\" title=\""
						+ tag.getName() + "\" div=\"" + tag.getValue() + "\">");
				for (Navigator nav : tag.getNavigators()) {
					buffer.append("<Button id=\"" + nav.getUuid() + "\" bid=\"" + nav.getId() + "\" title=\""
							+ nav.getName() + "\" link=\"" + URLEncoder.encode(nav.getNavAddr(), "utf-8") + "\"/>");
				}
				buffer.append("</Button>");
			}
			buffer.append("</Button>");
		}
		buffer.append("</category>");
		return buffer.toString();
	}

	@Autowired
	public void setTagDao(NavTagDao tagDao) {
		this.tagDao = tagDao;
	}

	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Autowired
	public void setNavigatorDao(NavigatorDao navigatorDao) {
		this.navigatorDao = navigatorDao;
	}

	@Autowired
	public void setBoardIconDao(BoardIconDao boardIconDao) {
		this.boardIconDao = boardIconDao;
	}

	@Autowired
	public void setTagIconDao(TagIconDao tagIconDao) {
		this.tagIconDao = tagIconDao;
	}

	@Autowired
	public void setNavPreviewDao(NavigatorPreviewDao navPreviewDao) {
		this.navPreviewDao = navPreviewDao;
	}

	@Autowired
	public void setHotLinkDao(HotLinkDao hotLinkDao) {
		this.hotLinkDao = hotLinkDao;
	}

}
