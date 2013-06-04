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
	private ClientTypeDao clientDao;
	private FileTagDao fileTagDao;

	public List<FileTag> getAllTags() {
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
	public void setClientDao(ClientTypeDao clientDao) {
		this.clientDao = clientDao;
	}

	@Autowired
	public void setFileTagDao(FileTagDao fileTagDao) {
		this.fileTagDao = fileTagDao;
	}
}
