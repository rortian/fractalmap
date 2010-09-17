package com.pseudopattern.map.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("schemes")
public interface AvailableSchemesService extends RemoteService {
	
	String[] getSchemes(String[] got);

}
