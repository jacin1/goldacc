/**
 * 
 *
 */
package com.xsyi.core.cache;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import com.xsyi.core.IConstants;

/**
 * <dl>
 * <dt><b>Title:</b>缓存池管理器</dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b>使用EhCache作为缓存池</dt>
 * <dd>
 * <p>
 * 管理接收对象的缓存管理器</dd>
 * </dl>
 * 
 * @author tangtao7481
 * @version , Feb 6, 2013 3:10:22 PM
 * 
 */
//@Component
public class ObjectCacheManager implements IConstants {
	
	private Log logger = LogFactory.getLog(ObjectCacheManager.class);
	
	@Autowired
	private CacheManager cacheManager;
	
	//增加锁机制
	private Object lock = new Object();

	//缓存对象总记录数据
	private double cacheSize = 0;

	/**
	 * 将对象加入到缓存
	 * @param key
	 * @param object
	 */
	public void putObjectToCache(String key,Object object) {
		synchronized (lock) {
			try {
				cacheManager.getCache(IConstants.DEFAULT_DRACO_CACHE_NAME).put(key,object);
				cacheSize++;
			} catch (Exception ex) {
				logger.error("写缓存系统失败",ex);
			}
		}
	}
	
	/**
	 * 从缓存中获取对象,但对象还存在缓存池中
	 * @param key
	 * @return
	 */
	public Object getObjectFromCache(String key) {
		synchronized (lock) {
			try {
				Object object = cacheManager.getCache(IConstants.DEFAULT_DRACO_CACHE_NAME).get(key);
				if( object!=null && object instanceof SimpleValueWrapper){
					return ((SimpleValueWrapper)object).get();
				}
				return object;
			} catch (Exception ex) {
				logger.error("读缓存系统失败",ex);
			}
			return null;
		}
	}

	/**
	 * 从缓冲中移除对象
	 * @param key
	 * @return
	 */
	public Object evictObjectFromCache(String key) {
		synchronized (lock) {
			try {
				Object object = cacheManager.getCache(IConstants.DEFAULT_DRACO_CACHE_NAME).get(key);
				if( object!=null ){
					cacheManager.getCache(IConstants.DEFAULT_DRACO_CACHE_NAME).evict(key);
				}
				if( object instanceof SimpleValueWrapper){
					return ((SimpleValueWrapper)object).get();
				}
				return object;
			} catch (Exception ex) {
				logger.error("读缓存系统失败",ex);
			}
			return null;
		}
	}
	
	/**
	 * 从ObjectKeyPool中获取UUID Key值
	 * 
	 * @return java.lang.String
	 * @throws Exception
	 */
	public String getObjectKey() throws Exception {
		return UUID.randomUUID().toString();
	}

	/**
	 * 返回缓存记录数
	 * @return
	 */
	public double getCacheSize() {
		return cacheSize;
	}

}
