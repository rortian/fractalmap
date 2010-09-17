package com.pseudopattern.map.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AvailableSchemesServiceAsync {

	void getSchemes(String[] got,AsyncCallback<String[]> callback);

}
