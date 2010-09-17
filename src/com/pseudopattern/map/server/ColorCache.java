package com.pseudopattern.map.server;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ColorCache {
	
	private static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	
	public static AColorSpectrum getScheme(String name){
		if(memcache.contains(name)){
			return (AColorSpectrum) memcache.get(name);
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			AColorSpectrum toReturn = pm.getObjectById(AColorSpectrum.class, name);
			memcache.put(name, toReturn);
			return toReturn;
		} finally {
			pm.close();
		}

	}
	
	public static SimpleMap getMap(String name,int param){
		String fullName = name+"-"+param;
		if(memcache.contains(fullName)){
			return (SimpleMap) memcache.get(fullName);
		}
		AColorSpectrum spec = getScheme(name);
		SimpleMap map = (SimpleMap) spec.getMap(param);
		memcache.put(fullName,map);
		return map;
	}

}
