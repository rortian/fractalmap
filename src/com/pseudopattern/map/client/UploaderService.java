package com.pseudopattern.map.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("upload")
public interface UploaderService extends RemoteService {

	Boolean upload(String object);
}
