package com.tp.service.nav;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tp.dao.nav.ClickLogDao;
import com.tp.entity.nav.Board;
import com.tp.entity.nav.BoardIcon;
import com.tp.entity.nav.ClickLog;
import com.tp.entity.nav.Navigator;
import com.tp.entity.nav.NavigatorPreview;
import com.tp.entity.nav.Tag;
import com.tp.entity.nav.TagIcon;
import com.tp.utils.Constants;
import com.tpadsz.navigator.IButtonSourceAdapter;
import com.tpadsz.navigator.entity.Button;

@Component
public class ButtonSourceAdapter implements IButtonSourceAdapter {

	private static final long ONE_WEEK_MILLI_SECONDS = 7 * 24 * 60 * 60 * 1000;
	private NavigatorService navigatorService;
	private ClickLogDao clickLogDao;

	@Override
	public Map<Button, Integer> getAllClicksOfClass(Integer cid, String userId,
			Long millis) {

		Board board = navigatorService.getBoard(cid.longValue());
		Map<Button, Integer> maps = Maps.newHashMap();
		List<Tag> tags = board.getTags();

		List<Navigator> navis = board.getNavigators();

		Map<Long, Integer> navClicks = getNavigatorClicks(userId);

		maps.putAll(getTag(tags, navClicks));
		maps.putAll(getNavigator(navis, navClicks));
		return maps;
	}

	private Map<Long, Integer> getTagClicks(String userId) {
		Map<Long, Integer> tagMap = Maps.newHashMap();
		List<Map<String, Object>> tagClicks = clickLogDao
				.countTagClicks(userId);
		for (Map<String, Object> tag : tagClicks) {
			String uuid = (String) tag.get("uuid");
			Long clicks = (Long) tag.get("clicks");
			tagMap.put(Long.valueOf(uuid), clicks.intValue());
		}
		return tagMap;
	}

	private Map<Long, Integer> getNavigatorClicks(String userId) {
		List<Map<String, Object>> navClicks = clickLogDao
				.countNavClicks(userId);
		Map<Long, Integer> navMap = Maps.newHashMap();
		for (Map<String, Object> nav : navClicks) {
			String uuid = (String) nav.get("uuid");
			Long clicks = (Long) nav.get("clicks");
			if (uuid == null || uuid.equals("null")) {
				continue;
			}
			navMap.put(Long.valueOf(uuid), clicks.intValue());
		}
		navMap.putAll(getTagClicks(userId));
		return navMap;
	}

	private void setPicSize(HashMap<String, String> picMaps, String key,
			String value) {

		if (key.equals("1")) {
			picMaps.put("1x1", value);
		} else if (key.equals("2")) {
			picMaps.put("2x1", value);
		} else if (key.equals("4")) {
			picMaps.put("2x2", value);
		} else if (key.equals("6")) {
			picMaps.put("3x2", value);
		}

	}

	private Map<Button, Integer> getTag(List<Tag> tags,
			Map<Long, Integer> navMap) {
		Map<Button, Integer> btns = Maps.newHashMap();

		for (Tag tag : tags) {
			HashMap<String, String> picMaps = Maps.newHashMap();
			Button btn = new Button();
			btn.setId(tag.getUuid());
			btn.setTitle(tag.getName());
			btn.setAction(Constants.getDomain() + "/nav/homeDetails?t="
					+ tag.getId() + "&bid=" + tag.getUuid());
			btn.setValue(tag.getValue());
			for (TagIcon icon : tag.getIcons()) {
				setPicSize(picMaps, icon.getLevel(), icon.getValue());
			}
			btn.setPictures(picMaps);
			int clicks = 0;
			if (!navMap.isEmpty() && navMap.containsKey(tag.getUuid())) {

				clicks = navMap.get(tag.getUuid());
			}
			clicks = clicks + navigatorClick(tag, navMap);
			if (clicks != 0)
				btns.put(btn, clicks);
		}
		return btns;
	}

	private int navigatorClick(Tag tag, Map<Long, Integer> navMap) {
		List<Navigator> navis = tag.getNavigators();
		int clicks = 0;
		for (Navigator nav : navis) {
			int click = 0;
			if (!navMap.isEmpty() && navMap.containsKey(nav.getUuid()))
				click = navMap.get(nav.getUuid());
			clicks += click;
		}
		return clicks;
	}

	private Map<Button, Integer> getNavigator(List<Navigator> navis,
			Map<Long, Integer> clickMap) {
		Map<Button, Integer> btns = Maps.newHashMap();

		for (Navigator nav : navis) {
			HashMap<String, String> picMaps = Maps.newHashMap();
			Button btn = new Button();
			btn.setId(nav.getUuid());
			btn.setTitle(nav.getName());
			btn.setAction(nav.getNavAddr());
			for (NavigatorPreview prev : nav.getPreviews()) {

				setPicSize(picMaps, prev.getLevel(), prev.getValue());
				btn.setPictures(picMaps);
			}
			int clicks = 0;
			if (!clickMap.isEmpty() && clickMap.containsKey(nav.getUuid()))
				clicks = clickMap.get(nav.getUuid());
			if (clicks != 0)
				btns.put(btn, clicks);
		}
		return btns;
	}

	@Override
	public Map<Button, Integer> getAllNewsButtonClicks(String userId,
			Long millis) {
		millis = ONE_WEEK_MILLI_SECONDS;
		Map<Long, Integer> navClicks = getNavigatorClicks(userId);
		Map<Button, Integer> newsClick = Maps.newHashMap();
		newsClick.putAll(getClicks("military", navClicks));
		newsClick.putAll(getClicks("entertainment", navClicks));
		newsClick.putAll(getClicks("sports", navClicks));
		newsClick.putAll(getClicks("tech", navClicks));
		newsClick.putAll(getClicks("finance", navClicks));
		newsClick.putAll(getClicks("cars", navClicks));

		return newsClick;
	}

	private Map<Button, Integer> getClicks(String name,
			Map<Long, Integer> navClicks) {
		Board news = navigatorService.getBoardByValue(name);
		Map<Button, Integer> newButtons = getNavigator(news.getNavigators(),
				navClicks);
		Integer clicks = 0;
		for (Button btn : newButtons.keySet()) {
			clicks += newButtons.get(btn);
		}
		Button btn = getButton(name);
		Map<Button, Integer> buttonClick = Maps.newHashMap();
		buttonClick.put(btn, clicks);
		return buttonClick;
	}

	@Override
	public Map<Button, Integer> getAllShoppingButtonClicks(String userId,
			Long millis) {
		millis = ONE_WEEK_MILLI_SECONDS;
		Board board = navigatorService.getBoardByValue("shop");
		return getAllClicksOfClass(board.getId().intValue(), userId, millis);
	}

	@Override
	public Map<Button, Integer> getAllTravelingButtonClicks(String userId,
			Long millis) {
		millis = ONE_WEEK_MILLI_SECONDS;
		Board board = navigatorService.getBoardByValue("life");
		return getAllClicksOfClass(board.getId().intValue(), userId, millis);
	}

	@Override
	public void logClick(Map<String, String> userIds, Long btnId) {

		if (userIds != null && !userIds.isEmpty()) {
			String imei = userIds.get("imei");
			if (imei == null || imei.isEmpty()) {
				imei = userIds.get("imsi");
			}

			ClickLog log = new ClickLog();
			log.setButtonId(btnId.longValue());
			log.setUserId(imei);
			log.setDate(new Date());
			clickLogDao.save(log);
		} else {
			ClickLog log = new ClickLog();
			log.setButtonId(btnId.longValue());
			log.setUserId("00000000");
			log.setDate(new Date());
			clickLogDao.save(log);
		}
	}

	@Override
	public List<Button> getRandom4News() {
		// Board board = navigatorService.getBoardByValue("news");
		// return random(board);
		Button news = getButton("news");
		Button entertainment = getButton("entertainment");
		Button sports = getButton("sports");
		Button military = getButton("military");
		Button cars = getButton("cars");
		Button tech = getButton("tech");
		Button finance = getButton("finance");
		List<Button> bts = Lists.newArrayList();
		bts.add(news);
		bts.add(entertainment);
		bts.add(sports);
		bts.add(military);
		bts.add(cars);
		bts.add(tech);
		bts.add(finance);
		Collections.shuffle(bts);
		return bts.subList(0, 4);
	}

	@Override
	public List<Button> getRandom4Shopping() {
		Board board = navigatorService.getBoardByValue("shop");
		return random(board);
	}

	@Override
	public List<Button> getRandom4Traveling() {
		Board board = navigatorService.getBoardByValue("life");
		return random(board);
	}

	@Override
	public Button getNewsButton(String temp) {

		return getButton("news");
	}

	@Override
	public Button getShoppingButton(String temp) {

		return getButton("shop");
	}

	@Override
	public Button getTravelingButton(String temp) {

		return getButton("life");
	}

	@Override
	public Button getReadingButton(String temp) {

		return getButton("read");
	}

	@Override
	public List<Button> getRandom4Reading() {
		Board board = navigatorService.getBoardByValue("read");
		return random(board);
	}

	@Override
	public boolean isShoppingHotterThanTraveling(String userId) {
		if (userId == null || userId.equals(""))
			return true;
		Map<Button, Integer> shop = getAllShoppingButtonClicks(userId,
				ONE_WEEK_MILLI_SECONDS);
		Map<Button, Integer> trave = getAllTravelingButtonClicks(userId,
				ONE_WEEK_MILLI_SECONDS);
		int shopClicks = 0;
		int traveClicks = 0;
		for (Entry<Button, Integer> e : shop.entrySet()) {
			shopClicks += e.getValue();
		}
		for (Entry<Button, Integer> e : trave.entrySet()) {
			traveClicks += e.getValue();
		}
		return shopClicks >= traveClicks;
	}

	@Override
	public List<Button> getBottom(String userId, Long millis) {
		Board board = navigatorService.getBoardByValue("leisure");

		return getAllByBoard(board);
	}

	@Override
	public List<Button> getRandomBottom() {
		Board board = navigatorService.getBoardByValue("leisure");
		return random(board);
	}

	@Override
	public Map<Button, Integer> getAllReadingButtonClicks(String userId,
			Long millis) {
		millis = ONE_WEEK_MILLI_SECONDS;
		Board board = navigatorService.getBoardByValue("read");
		return getAllClicksOfClass(board.getId().intValue(), userId, millis);
	}

	private Button getButton(String btnName) {
		HashMap<String, String> picMaps = Maps.newHashMap();
		Board board = navigatorService.getBoardByValue(btnName);
		Button button = new Button();
		button.setId(board.getUuid());
		button.setTitle(btnName);
		button.setAction(Constants.getDomain() + "/nav/homeDetails?b="
				+ board.getId() + "&bid=" + board.getUuid());
		button.setValue(board.getValue());
		for (BoardIcon icon : board.getIcons()) {
			setPicSize(picMaps, icon.getLevel(), icon.getValue());
		}
		button.setPictures(picMaps);
		return button;
	}

	private Button getTagButton(String btnName) {
		HashMap<String, String> picMaps = Maps.newHashMap();
		Tag tag = navigatorService.getTagByValue(btnName);
		Button button = new Button();
		button.setId(tag.getUuid());
		button.setTitle(btnName);
		button.setAction(Constants.getDomain() + "/nav/homeDetails?t="
				+ tag.getId() + "&bid=" + tag.getUuid());
		button.setValue(tag.getValue());
		for (TagIcon icon : tag.getIcons()) {
			setPicSize(picMaps, icon.getLevel(), icon.getValue());
		}
		button.setPictures(picMaps);
		return button;
	}

	private List<Button> getAllByBoard(Board board) {
		List<Button> buttons = Lists.newArrayList();

		List<Tag> tags = board.getTags();

		for (Tag tag : tags) {
			HashMap<String, String> picMaps = Maps.newHashMap();
			Button button = new Button();
			button.setId(tag.getUuid());
			button.setTitle(tag.getName());
			button.setAction(Constants.getDomain() + "/nav/homeDetails?t="
					+ tag.getId() + "&bid=" + tag.getUuid());
			button.setValue(tag.getValue());
			for (TagIcon icon : tag.getIcons()) {
				setPicSize(picMaps, icon.getLevel(), icon.getValue());
			}
			button.setPictures(picMaps);
			buttons.add(button);
		}
		List<Navigator> navs = board.getNavigators();
		for (Navigator nav : navs) {
			HashMap<String, String> picMaps1 = Maps.newHashMap();
			Button button = new Button();
			button.setId((nav.getUuid()));
			button.setTitle(nav.getName());
			button.setAction(nav.getNavAddr());

			for (NavigatorPreview prev : nav.getPreviews()) {
				setPicSize(picMaps1, prev.getLevel(), prev.getValue());
			}
			button.setPictures(picMaps1);
			buttons.add(button);
		}

		return buttons;
	}

	private List<Button> random(Board board) {
		List<Button> buttons = getAllByBoard(board);

		if (buttons.size() > 4) {
			Collections.shuffle(buttons);
			return buttons.subList(0, 4);
		}

		return buttons;
	}

	@Override
	public List<Button> getDefaultNews() {
		List<Button> ret = new ArrayList<Button>(2);
		Button btnPE = getButton("sports");
		Button btnEnt = getButton("entertainment");

		btnPE.setPicture(btnPE.getPictures().get("1x1"));
		btnEnt.setPicture(btnEnt.getPictures().get("1x1"));
		ret.add(btnPE);
		ret.add(btnEnt);

		return ret;
	}

	@Override
	public List<Button> getDefaultBottom() {
		List<Button> ret = new ArrayList<Button>(4);
		Button btnDate = getButton("friends");
		Button btnFood = getTagButton("food");
		Button btnMusic = getTagButton("music");
		Button btnGame = getTagButton("game");

		btnDate.setPicture(btnDate.getPictures().get("1x1"));
		btnFood.setPicture(btnFood.getPictures().get("1x1"));
		btnMusic.setPicture(btnDate.getPictures().get("1x1"));
		btnGame.setPicture(btnDate.getPictures().get("1x1"));

		ret.add(btnDate);
		ret.add(btnFood);
		ret.add(btnMusic);
		ret.add(btnGame);

		return ret;
	}

	@Override
	public List<Button> getDefaultReading() {
		List<Button> ret = new ArrayList<Button>(2);
		Button btnPE = getTagButton("funny");
		Button btnEnt = getTagButton("novel");

		btnPE.setPicture(btnPE.getPictures().get("1x1"));
		btnEnt.setPicture(btnEnt.getPictures().get("1x1"));
		ret.add(btnPE);
		ret.add(btnEnt);

		return ret;
	}

	@Override
	public List<Button> getDefaultShopping() {
		List<Button> ret = new ArrayList<Button>(0);
		return ret;
	}

	@Autowired
	public void setNavigatorService(NavigatorService navigatorService) {
		this.navigatorService = navigatorService;
	}

	@Autowired
	public void setClickLogDao(ClickLogDao clickLogDao) {
		this.clickLogDao = clickLogDao;
	}

	@Override
	public Button getMostRecentButtonOfCategory(String uuid,
			String uuidException) {
		Button news = getButton("news");
		if (uuid.equals(news.getId().toString())) {
			Button entertainment = getButton("entertainment");
			Button sports = getButton("sports");
			Button military = getButton("military");
			Button cars = getButton("cars");
			Button tech = getButton("tech");
			Button finance = getButton("finance");
			List<Map<String, Object>> clicks = clickLogDao
					.getNewsNewestClickButton(String.valueOf(entertainment
							.getId()), String.valueOf(sports.getId()), String
							.valueOf(military.getId()), String.valueOf(cars
							.getId()), String.valueOf(tech.getId()), String
							.valueOf(finance.getId()));
			String btnId = null;
			for (Map<String, Object> button : clicks) {
				btnId = (String) button.get("button_id");
			}
			Board b = navigatorService.getBoardByUUID(btnId);
			return getButton(b.getValue());
		}
		return null;

	}

	@Override
	public Map<Button, Integer> getAllFriendsButtonClicks(String userId,
			Long millis) {
		millis = ONE_WEEK_MILLI_SECONDS;
		Board board = navigatorService.getBoardByValue("friends");
		return getAllClicksOfClass(board.getId().intValue(), userId, millis);
	}

	@Override
	public Map<Button, Integer> getAllLeisureButtonClicks(String userId,
			Long millis) {
		millis = ONE_WEEK_MILLI_SECONDS;
		Board board = navigatorService.getBoardByValue("leisure");
		return getAllClicksOfClass(board.getId().intValue(), userId, millis);
	}
}
