package com.tp.service;

import java.util.List;

import com.tp.dao.*;
import com.tp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tp.dto.ShelfDTO;
import com.tp.mapper.JsonMapper;

@Component
@Transactional
public class CategoryManager {

	private CategoryDao categoryDao;
	private StoreDao storeDao;
	private ShelfDao shelfDao;
	private ShelfFileLinkDao sflDao;
	private ClientTypeDao clientDao;
	private FileStoreInfoDao storeInfoDao;
	private ThemeFileDao themeFileDao;
    private FileTagDao fileTagDao;

    public List<FileTag> getAllTags(){
        return fileTagDao.getAll();
    }

	public Category getCategory(Long id) {
		return categoryDao.get(id);
	}

	public Store getStore(Long id) {
		return storeDao.get(id);
	}

	public Store getStoreByValue(String value) {
		return storeDao.findUniqueBy("value", value);
	}

	public List<Category> getCategories() {
		return categoryDao.getAll();
	}

	public List<Store> getAllStore() {
		return storeDao.getAll();
	}

	public void saveCategory(Category category) {
		categoryDao.save(category);
	}

	public void saveStore(Store entity) {

		storeDao.save(entity);

	}

	public void createDefaultShelf(Store store) {

		for (Shelf.Type st : Shelf.Type.values()) {
			Shelf shelf = new Shelf();
			shelf.setName(st.getDisplayName());
			shelf.setValue(st.getValue());
			shelf.setDescription("created by default");
			shelf.setStore(store);
			this.saveShelf(shelf);
		}
	}

	public void deleteStore(Long id) {
		storeDao.delete(id);
	}

	public void deleteCategory(Long id) {
		categoryDao.delete(id);
	}

	public Shelf getShelf(Long id) {
		return shelfDao.get(id);
	}

	public void saveShelf(Shelf entity) {
		shelfDao.save(entity);
	}

	public List<Shelf> getAllShelf() {
		return shelfDao.getAll();
	}

	public void deleteShelf(Long id) {
		shelfDao.delete(id);
	}

	public String jsonString(List<Shelf> shelfs) {
		List<ShelfDTO> sdto = Lists.newArrayList();
		for (Shelf shelf : shelfs) {
			ShelfDTO dto = new ShelfDTO();
			dto.setId(shelf.getId());
			dto.setName(shelf.getName());
			dto.setValue(shelf.getValue());
			dto.setDescription(shelf.getDescription());
			sdto.add(dto);
		}
		JsonMapper mapper = JsonMapper.buildNormalMapper();
		return mapper.toJson(sdto);
	}

	public void copyAllStore(Long storeId, Store targetStore) {
		Store originalStore = this.getStore(storeId);
		List<Shelf> oriShelfs = originalStore.getShelfs();
		List<ThemeFile> singleThemeFile = Lists.newArrayList();
		for (Shelf oriShelf : oriShelfs) {
			Shelf targetShelf = new Shelf();
			targetShelf.setName(oriShelf.getName());
			copyShelfFiles(targetShelf, oriShelf.getShelfFile());
			targetShelf.setDescription(oriShelf.getDescription());
			targetShelf.setStore(targetStore);
			targetShelf.setValue(oriShelf.getValue());
			this.saveShelf(targetShelf);
			for (ThemeFile file : getThemes(oriShelf.getShelfFile())) { //remove the theme file where in one store on diff shelfs 
				if (!singleThemeFile.contains(file)) {
					singleThemeFile.add(file);
				}

			}
		}
		copyFileStoreInfo(singleThemeFile, targetStore);
	}

	private List<ThemeFile> getThemes(List<ShelfFileLink> links) {
		List<ThemeFile> themes = Lists.newArrayList();
		for (ShelfFileLink shelfFile : links) {
			themes.add(shelfFile.getTheme());
		}
		return themes;
	}

	private void copyShelfFiles(Shelf targetShelf, List<ShelfFileLink> oriLinks) {
		for (ShelfFileLink ori : oriLinks) {
			ShelfFileLink targetLink = new ShelfFileLink();
			targetLink.setTheme(ori.getTheme());
			targetLink.setShelf(targetShelf);
			targetLink.setSort(ori.getSort());
			sflDao.save(targetLink);
		}

	}

	/**
	 * 整合FileStoreInfo信息
	 * 
	 */
	public void merge(Shelf shelf, List<Long> ids) {
		List<ThemeFile> themes = getThemes(shelf.getShelfFile());
		Store store = shelf.getStore();

		if (ids == null) {
			for (ThemeFile f : themes) {
				if (!isFileInStore(store, shelf, f)) {
					storeInfoDao.deleteByThemeAndStore(f.getId(), store.getId());
				}
			}

			return;
		}
		List<Long> checkedIds = Lists.newArrayList();
		List<ThemeFile> checkedThemes = Lists.newArrayList();
		checkedThemes.addAll(themes);
		checkedIds.addAll(ids);

		for (ThemeFile file : checkedThemes) {
			Long id = file.getId();
			if (!checkedIds.contains(id) && !isFileInStore(store, shelf, file)) {
				storeInfoDao.deleteByThemeAndStore(id, store.getId());
			} else {
				checkedIds.remove(id);
			}
		}
		for (Long id : checkedIds) {
			ThemeFile file = themeFileDao.get(id);
			if (!isFileInStore(store, shelf, file)) {
				copyFileStoreInfo(file, store);
			}

		}
	}

	private boolean isFileInStore(Store store, Shelf sh, ThemeFile theme) {
		List<ThemeFile> allFileInStore = Lists.newArrayList();
		List<Shelf> shelfs = store.getShelfs();
		for (Shelf shelf : shelfs) {
			if (!shelf.equals(sh))
				allFileInStore.addAll(getThemes(shelf.getShelfFile()));
		}
		return allFileInStore.contains(theme);
	}

	private void copyFileStoreInfo(List<ThemeFile> themes, Store store) {
		for (ThemeFile theme : themes) {
			copyFileStoreInfo(theme, store);
		}
	}

	private void copyFileStoreInfo(ThemeFile theme, Store store) {
		List<FileInfo> infos = theme.getFileInfo();
		for (FileInfo fmi : infos) {
			FileStoreInfo storeInfo = new FileStoreInfo();
			storeInfo.setTitle(fmi.getTitle());
			storeInfo.setShortDescription(fmi.getShortDescription());
			storeInfo.setLongDescription(fmi.getLongDescription());
			storeInfo.setAuthor(fmi.getAuthor());
			storeInfo.setPrice(fmi.getPrice());
			storeInfo.setLanguage(fmi.getLanguage());
			storeInfo.setFiId(fmi.getId());
			storeInfo.setTheme(theme);
			storeInfo.setStore(store);
			storeInfoDao.save(storeInfo);
		}
	}

	@Transactional(readOnly = true)
	public boolean isStoreNameUnique(String newStoreName, String oldStoreName) {
		return storeDao.isPropertyUnique("name", newStoreName, oldStoreName);
	}

	@Transactional(readOnly = true)
	public boolean isStoreValueUnique(String newValue, String oldValue) {
		return storeDao.isPropertyUnique("value", newValue, oldValue);
	}

	public boolean isCategoryUnique(String newName, String oldName) {
		return categoryDao.isPropertyUnique("name", newName, oldName);
	}

	public boolean isShelfUnique(String newName, String oldName, Long id) {
		return shelfDao.isShelfNameUnique(newName, oldName, id);
	}

	public void saveClientType(ClientType entity) {
		clientDao.save(entity);
	}

	public ClientType getClientType(Long id) {
		return clientDao.get(id);
	}

	public void deleteClientType(Long id) {
		clientDao.delete(id);
	}

	public List<ClientType> getClientTypes() {
		return clientDao.getAll();
	}

	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Autowired
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	@Autowired
	public void setShelfDao(ShelfDao shelfDao) {
		this.shelfDao = shelfDao;
	}

	@Autowired
	public void setStoreInfoDao(FileStoreInfoDao storeInfoDao) {
		this.storeInfoDao = storeInfoDao;
	}

	@Autowired
	public void setThemeFileDao(ThemeFileDao themeFileDao) {
		this.themeFileDao = themeFileDao;
	}

	@Autowired
	public void setSflDao(ShelfFileLinkDao sflDao) {
		this.sflDao = sflDao;
	}

	@Autowired
	public void setClientDao(ClientTypeDao clientDao) {
		this.clientDao = clientDao;
	}

    @Autowired
    public void setFileTagDao(FileTagDao fileTagDao) {
        this.fileTagDao = fileTagDao;
    }
}
