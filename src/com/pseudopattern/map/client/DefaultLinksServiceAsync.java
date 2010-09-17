package com.pseudopattern.map.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DefaultLinksServiceAsync {

	void getLinks(AsyncCallback<LinkInfo[]> callback);

}
