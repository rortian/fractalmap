package com.pseudopattern.map.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("links")
public interface DefaultLinksService extends RemoteService {
	
	LinkInfo[] getLinks();
}
