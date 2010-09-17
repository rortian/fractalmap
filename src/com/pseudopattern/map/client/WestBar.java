package com.pseudopattern.map.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class WestBar extends Composite {

	private static WestBarUiBinder uiBinder = GWT.create(WestBarUiBinder.class);

	interface WestBarUiBinder extends UiBinder<Widget, WestBar> {
	}
	
	@UiField LayoutPanel paramPanel;

	@UiField LayoutPanel colorPanel;
	
	@UiField TextBox lambdaReal,lambdaImag,mBox,nBox,colorParam;
	
	@UiField ListBox schemeList;
	
	@UiField FlowPanel defaultLinksPanel,customLinksPanel;
	
	@UiField Button generateLink,applyOne,applyTwo,planeButton;
	
	int lastN,lastM,lastColorParam;
	
	double lastReal,lastI;
	
	
	public WestBar() {
		initWidget(uiBinder.createAndBindUi(this));
		lambdaReal.setVisibleLength(12);
		lambdaImag.setVisibleLength(12);
		lambdaImag.setText("0.0");
		lambdaReal.setText("0.23");
		mBox.setText("3");
		nBox.setText("3");
		mBox.setVisibleLength(12);
		nBox.setVisibleLength(12);
		colorParam.setText("20");
		colorParam.setVisibleLength(8);
		schemeList.addItem("Rainbow");
	}
	
	public void applyListener(ClickHandler handler){
		applyOne.addClickHandler(handler);
		applyTwo.addClickHandler(handler);
	}
	
	public FlowPanel defaultPanel(){
		return defaultLinksPanel;
	}
	
	public void addGenerateHandler(ClickHandler handler){
		generateLink.addClickHandler(handler);
	}
	
	public void addPlaneHandler(ClickHandler handler){
		planeButton.addClickHandler(handler);
	}
	
	public void addToSchemes(String[] schemes){
		for(String s : schemes){
			if(s.contains(" ")){
				schemeList.addItem(s,s.replaceAll(" ", "%20"));
			} else {
				schemeList.addItem(s);
			}
		}
	}
	
	
	
	public int numSchemes(){
		return schemeList.getItemCount();
	}
	
	public String[] notRainbow(){
		String[] toReturn = new String[schemeList.getItemCount()-1];
		for(int i=0;i<toReturn.length;i++){
			toReturn[i] = schemeList.getValue(i+1);
		}
		return toReturn;
	}
	
	public String colorPart(String url){
		String value = schemeList.getValue(schemeList.getSelectedIndex());
		if(value.equals("Rainbow")){
			return url;
		}
		String toReturn = url;
		if(url.length()>0){
			toReturn += "&scheme="+value;
		} else {
			toReturn = "?scheme="+value;
		} if(lastColorParam != 20){
			if(toReturn.length()>0){
				toReturn += "&colorparam="+colorParam.getText();
			} else {
				toReturn = "?colorparam="+colorParam.getText();
			}
		}
		return toReturn;
	}
	
	public void check(){
		checkM();
		checkN();
		checkReal();
		checkImag();
		checkColorParam();
	}
	
	public String getLambda(){
		check();
		if(lastI == 0)
			return lambdaReal.getText();
		return lambdaReal.getText() + "_" + lambdaImag.getText();
	}
	
	public String getQuery(){
		String toReturn = "";
		if(lastM != 3){
			toReturn = "?m=" + mBox.getText();
		}
		if(lastN != 3){
			if(toReturn.length()>0){
				toReturn += "&n=" + nBox.getText();
			} else {
				toReturn = "?n=" + nBox.getText();
			}
		}
		return toReturn;
	}
	
	private void checkM(){
		try {
			int m = Integer.parseInt(mBox.getText());
			if(m > 1 && m < 10){
				lastM = m;
			}
		} catch (NumberFormatException e) {
			
		}
		mBox.setText(String.valueOf(lastM));
	}
	
	private void checkN(){
		try {
			int n = Integer.parseInt(nBox.getText());
			if(n > 0 && n < 10){
				lastN = n;
			}
		} catch (NumberFormatException e) {
			
		}
		nBox.setText(String.valueOf(lastN));
	}
	
	private void checkColorParam(){
		try {
			int param = Integer.parseInt(colorParam.getText());
			if(param > 1 && param < 500){
				lastColorParam = param;
			}
		} catch (NumberFormatException e) {
			
		}
		colorParam.setText(String.valueOf(lastColorParam));
	}
	
	private void checkReal(){
		try {
			double x = Double.parseDouble(lambdaReal.getText());
			if(Math.abs(x)<4){
				lastReal = x;
			}
		} catch (NumberFormatException e){
			
		}
		lambdaReal.setText(String.valueOf(lastReal));
	}
	
	private void checkImag(){
		try {
			double y = Double.parseDouble(lambdaImag.getText());
			if(Math.abs(y)<4){
				lastI = y;
			}
		} catch (NumberFormatException e){
			
		}
		lambdaImag.setText(String.valueOf(lastI));
	}

	public void setReal(String string) {
		lambdaReal.setText(string);
	}

	public void setImag(String string) {
		lambdaImag.setText(string);
	}

	public void setM(String string) {
		mBox.setText(string);
	}
	
	public void setN(String string) {
		nBox.setText(string);
	}

	public void setScheme(String string) {
		if(!string.equals("Rainbow")){
			if(schemeList.getItemCount()==1){
				String[] temp = new String[1];
				temp[0] = string;
				addToSchemes(temp);
				schemeList.setItemSelected(0, false);
				schemeList.setItemSelected(1, true);
			}
		}
	}


	public void setColorParam(String string) {
		colorParam.setText(string);
	}

	public void setParams(UrlBuilder builder) {

		if(!lambdaReal.equals("0.23")){
			builder.setParameter("real", lambdaReal.getText());
		}
		if(!lambdaImag.equals("0.0")){
			builder.setParameter("imag", lambdaImag.getText());
		}
		if(!mBox.equals("3")){
			builder.setParameter("m", mBox.getText());
		}
		if(!nBox.equals("3")){
			builder.setParameter("n", nBox.getText());
		}
		if(schemeList.getSelectedIndex()!=0){
			builder.setParameter("scheme", schemeList.getValue(schemeList.getSelectedIndex()));
		}
		if(!colorParam.equals(20)){
			builder.setParameter("colorparam", colorParam.getText());
		}
		
	}

	public FlowPanel getCustomLinks() {
		return customLinksPanel;
	}
	
	

	

}
