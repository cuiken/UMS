package com.tp.utils;

import java.util.LinkedList;
import java.util.List;

import com.tp.dao.ICacheSaver;
/*
 * 一个同步写入数据库操作的列表缓存。
 * 平时这个列表可被任意线程（在web应用里往往是每个request对应的线程）添加新的元素E，
 * 而当向数据库写入自身内容时会被锁定保护，写完立即清空。
 * 
 * 写入的触发条件是有新元素添加进来时（即save方法被调用时），当前时间离上次写入时间
 * lastChechPoint超过配置文件指定的时长commitDuration。如果在向数据库写入时出错，
 * 当前缓存的内容cacheList将不会被清空，而是保留到下次触发写入时与新数据一并尝试写
 * 入。
 * 
 * 
 */
public class WriteSychronziedListCache<E> {
	private List<E> cacheList;

	ICacheSaver<E> saver;
	private long commitDuration;
	
	private long lastCheckPoint;
	

	public long getCommitDuration() {
		return commitDuration;
	}

	public void setCommitDuration(long commitDuration) {
		this.commitDuration = commitDuration;
	}

	public WriteSychronziedListCache() {
		cacheList = new LinkedList<E>();
		lastCheckPoint = System.currentTimeMillis();
	}

	public ICacheSaver<E> getSaver() {
		return saver;
	}

	public void setSaver(ICacheSaver<E> saver) {
		this.saver = saver;
	}

	public void save(E e) {
		if (System.currentTimeMillis() - lastCheckPoint < commitDuration) {
			cacheList.add(e);
			return;
		} else {
			synchronized (cacheList) {

				try {
					saver.saveList(cacheList);
				} catch (Exception e1) {
					return;
				}
				cacheList.clear();
				lastCheckPoint = System.currentTimeMillis();
			}
			return;
		}
	}

}
