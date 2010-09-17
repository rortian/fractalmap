package com.pseudopattern.map.server;

import java.util.ArrayList;
import java.util.HashSet;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;


import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pseudopattern.map.client.AvailableSchemesService;

public class AvailableSchemesServiceImpl extends RemoteServiceServlet implements
		AvailableSchemesService {
	
	private static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

	@Override
	public String[] getSchemes(String[] got) {

		HashSet<String> set = new HashSet<String>();
		ArrayList<String> all = getAll();
		ArrayList<String> names = new ArrayList<String>();
		for (String s : got) {
			if (s.contains("%20")) {
				set.add(s.replaceAll("%20", " "));
			} else {
				set.add(s);
			}
		}

		for (String s : all) {
			if (!set.contains(s)) {
				names.add(s);
			}
		}
		String[] toReturn = new String[names.size()];
		names.toArray(toReturn);
		return toReturn;

	}
	
	public ArrayList<String> getAll(){
		if(memcache.contains("colorschemenames")){
			return (ArrayList<String>) memcache.get("colorschemenames");
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Extent<AColorSpectrum> extent = pm.getExtent(AColorSpectrum.class);
			ArrayList<String> toReturn = new ArrayList<String>();
			for(AColorSpectrum acs : extent){
				toReturn.add(acs.getName());
			}
			memcache.put("colorschemenames", toReturn);
			return toReturn;
		} finally {
			pm.close();
		}
		
	}
	
	public static void clearSchemes(){
		memcache.delete("colorschemenames");
	}

}
