package com.tp.service;

import java.util.Observable;

import com.tp.entity.FileInfo;

public class FileInfoObservable extends Observable {

	private FileStoreInfoObserver observer=new FileStoreInfoObserver();;
	
	private FileManager fileManager;

	public void saveFileInfo(FileInfo info) {
		observer.setFileManager(fileManager);
		fileManager.saveFileInfo(info);
		addObserver(observer);
		setChanged();
		notifyObservers(info);
	}

	public void deleteFileInfo(Long id) {
		observer.setFileManager(fileManager);
		fileManager.deleteFileInfo(id);
		addObserver(observer);
		setChanged();
		notifyObservers(id);
	}

	
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
}
