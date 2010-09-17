package com.pseudopattern.map.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Uploader implements EntryPoint {
	
	String spectrum = "{\"name\":\"\", \"type\":\"spectrum\", "+
		"\"data\":{\"red\":{\"x\":[], \"y\":[]},\"green\":{\"x\":[], \"y\":[]}, "+
		"\"blue\":{\"x\":[], \"y\":[]} } }";
	
	String link = "{\"name\":\"\",\"type\":\"link\", " +
			"\"url\":\"http://pseudomap.appspot.com/TileLayout.html?\" }";
	
	private UploaderServiceAsync uploadService = GWT.create(UploaderService.class);

	@Override
	public void onModuleLoad() {
		VerticalPanel vp = new VerticalPanel();
		RootPanel.get().add(vp);
		vp.add(new Label("Input"));
		final TextArea ta = new TextArea();
		vp.add(ta);
		ta.setVisibleLines(50);
		ta.setCharacterWidth(80);
		final Button b = new Button("Submit");
		vp.add(b);
		final ListBox lb = new ListBox();
		lb.addItem("spectrum");
		lb.addItem("link");
		vp.add(lb);
		setArea(ta,lb.getItemText(0));
		lb.addChangeHandler(new ChangeHandler(){

			@Override
			public void onChange(ChangeEvent event) {
				setArea(ta,lb.getValue(lb.getSelectedIndex()));	
			}
			
		});
		
		
		b.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				b.setEnabled(false);
				uploadService.upload(ta.getText(), new AsyncCallback<Boolean>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						b.setEnabled(true);
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result){
							Window.alert("Success!");
							
						} else {
							Window.alert("Didn't work. Name probably taken.");
						}
						b.setEnabled(true);
						
					}
					
				});
			}
			
		});
	}
	
	private void setArea(TextArea ta,String key){
		if(key.equals("spectrum")){
			ta.setText(spectrum);
		} else if(key.equals("link")){
			ta.setText(link);
		}
	}

}
