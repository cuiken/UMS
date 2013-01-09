package com.tp.service;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.google.common.collect.Lists;
import com.tp.entity.FileInfo;
import com.tp.entity.FileStoreInfo;
import com.tp.entity.Shelf;
import com.tp.entity.ShelfFileLink;
import com.tp.entity.Store;
import com.tp.entity.ThemeFile;

public class FileStoreInfoObserver implements Observer {

	private FileManager fileManager;

	@Override
	public void update(Observable o, Object obj) {

		if (obj instanceof Long) { // 执行多语言的delete方法,同步删除商店的信息

			Long id = (Long) obj;
			fileManager.deleteStoreInfoByFmId(id);
		} else if (obj instanceof FileInfo) { // 执行多语言的save方法

			FileInfo info = (FileInfo) obj;
			Long fiId = info.getId();
			ThemeFile theme = info.getTheme();
			if (isFileInStore(theme)) {// 已经上架文件
				List<Store> allUseStores = getStores(theme);
				saveOrUpdateStoreInfo(info, fiId, allUseStores);
			}
		}
	}

	private boolean isFileInStore(ThemeFile theme) {
		return !theme.getInfoStore().isEmpty();
	}

	private List<Store> getStores(ThemeFile theme) {
		List<Store> stores = Lists.newArrayList();
		List<Shelf> shelfs = getShelf(theme.getShelfFiles());
		for (Shelf shelf : shelfs) {
			Store store = shelf.getStore();
			if (!stores.contains(store)) {
				stores.add(store);
			}
		}
		return stores;
	}

	private List<Shelf> getShelf(List<ShelfFileLink> links) {
		List<Shelf> shelfs = Lists.newArrayList();
		for (ShelfFileLink link : links) {
			shelfs.add(link.getShelf());
		}
		return shelfs;
	}

	/**
	 * 新增或者更新对应文件的商店多语言信息
	 */
	private void saveOrUpdateStoreInfo(FileInfo source, Long fiId, List<Store> stores) {
		boolean isExist = fileManager.isInfoInStore(fiId);
		if (isExist) {
			update(fiId, source);

		} else { // 新增到各个商店
			save(source, stores, fiId);
		}
	}

	private void update(Long fiId, FileInfo source) {
		List<FileStoreInfo> targetInfos = fileManager.getStoreInfoByFiId(fiId);
		for (FileStoreInfo target : targetInfos) {
			target.setTitle(source.getTitle());
			target.setShortDescription(source.getShortDescription());
			target.setLongDescription(source.getLongDescription());
			target.setAuthor(source.getAuthor());
			target.setPrice(source.getPrice());
			fileManager.saveStoreInfo(target);
		}
	}

	private void save(FileInfo source, List<Store> stores, Long fiId) {
		for (Store store : stores) {
			FileStoreInfo target = new FileStoreInfo();
			target.setTitle(source.getTitle());
			target.setShortDescription(source.getShortDescription());
			target.setLongDescription(source.getLongDescription());
			target.setLanguage(source.getLanguage());
			target.setAuthor(source.getAuthor());
			target.setTheme(source.getTheme());
			target.setFiId(fiId);
			target.setPrice(source.getPrice());
			target.setStore(store);
			fileManager.saveStoreInfo(target);
		}
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

}
