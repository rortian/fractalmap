package com.pseudopattern.map.server;

import java.util.ArrayList;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pseudopattern.map.client.DefaultLinksService;
import com.pseudopattern.map.client.LinkInfo;

public class DefaultLinksServiceImpl extends RemoteServiceServlet implements
		DefaultLinksService {

	private static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	
	@Override
	public LinkInfo[] getLinks() {
		if(memcache.contains("defaultlinks"))
			return (LinkInfo[]) memcache.get("defaultlinks");
		ArrayList<ALink> links = new ArrayList<ALink>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Extent<ALink> extent = pm.getExtent(ALink.class);
			for(ALink link : extent)
				links.add(link);
			LinkInfo[] toReturn = new LinkInfo[links.size()];
			for(int i=0;i<toReturn.length;i++){
				LinkInfo current = new LinkInfo();
				ALink yo = links.get(i);
				current.name = yo.getName();
				current.url = yo.getUrl();
				toReturn[i] = current;
			}
			memcache.put("defaultlinks", toReturn);
			return toReturn;
		} finally {
			pm.close();
		}
		
	}
	
	public static void clearLinks(){
		memcache.delete("defaultlinks");
	}

}
