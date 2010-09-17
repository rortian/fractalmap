package com.pseudopattern.map.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UploaderServiceAsync {

	void upload(String object, AsyncCallback<Boolean> callback);

}
