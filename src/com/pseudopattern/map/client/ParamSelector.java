package com.pseudopattern.map.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;

import com.google.gwt.user.client.ui.Widget;

public class ParamSelector extends Composite {

	private static ParamSelectorUiBinder uiBinder = GWT
			.create(ParamSelectorUiBinder.class);

	interface ParamSelectorUiBinder extends UiBinder<Widget, ParamSelector> {
	}

	
	@UiField LayoutPanel layout;
	
	@UiField Image image;
	
	@UiField InlineLabel xValue,yValue;
	
	PositionHelper ph;

	public ParamSelector() {
		initWidget(uiBinder.createAndBindUi(this));
		ph = new PositionHelper();
		image.addMouseMoveHandler(ph);
	}
	
	public LayoutPanel layout(){
		return layout;
	}
	
	public void addImageHandler(ClickHandler handler){
		image.addClickHandler(handler);
	}
	
	public String getX(){
		return ph.getX();
	}
	
	public String getY(){
		return ph.getY();
	}
	
	class PositionHelper implements MouseMoveHandler {
		
		int x,y;
		
		public PositionHelper(){
			x = y = 250;
			setLabels();
		}

		@Override
		public void onMouseMove(MouseMoveEvent event) {
			x = event.getRelativeX(image.getElement());
			y = event.getRelativeY(image.getElement());
			setLabels();
		}
		
		public void setLabels(){
			xValue.setText(getX());
			yValue.setText(getY());
		}
		
		public String getX(){
			return String.valueOf((x-250.0)/250.0);
		}
		
		public String getY(){
			return String.valueOf((250.0-y)/250.0);
		}
		
	}
	
	
	

}
