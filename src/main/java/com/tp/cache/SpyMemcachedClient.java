package com.tp.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

public class SpyMemcachedClient implements DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SpyMemcachedClient.class);

	private MemcachedClient memcachedClient;

	private long shutdownTimeout = 2500;

	private long updateTimeout = 2500;

	public <T> T get(String key) {
		try {
			return (T) memcachedClient.get(key);
		} catch (RuntimeException e) {
			handleException(e, key);
		}
		return null;
	}

	public <T> Map<String, T> getBulk(Collection<String> keys) {
		try {
			return (Map<String, T>) memcachedClient.getBulk(keys);
		} catch (RuntimeException e) {
			handleException(e, StringUtils.join(keys, ","));
			return null;
		}
	}

	public void set(String key, int expiredTime, Object value) {
		memcachedClient.set(key, expiredTime, value);
	}

	/**
	 * 安全的Set方法, 保证在updateTimeout秒内返回执行结果, 否则返回false并取消操作.
	 */
	public boolean safeSet(String key, int expiration, Object value) {
		Future<Boolean> future = memcachedClient.set(key, expiration, value);
		try {
			return future.get(updateTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			future.cancel(false);
		}
		return false;
	}

	public void delete(String key) {
		memcachedClient.delete(key);
	}

	public boolean safeDelete(String key) {
		Future<Boolean> future = memcachedClient.delete(key);
		try {
			return future.get(updateTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			future.cancel(false);
		}
		return false;
	}

	public long incr(String key, int by, long defaultValue) {
		return memcachedClient.incr(key, by, defaultValue);
	}

	public long decr(String key, int by, long defaultValue) {
		return memcachedClient.decr(key, by, defaultValue);
	}

	private void handleException(Exception e, String key) {
		logger.warn("spymemcached client receive an exception with key:" + key, e);
	}

	@Override
	public void destroy() throws Exception {
		if (memcachedClient != null) {
			memcachedClient.shutdown(shutdownTimeout, TimeUnit.MILLISECONDS);
		}
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public void setShutdownTimeout(long shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

	public void setUpdateTimeout(long updateTimeout) {
		this.updateTimeout = updateTimeout;
	}

}
