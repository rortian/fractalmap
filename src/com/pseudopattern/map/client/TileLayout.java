package com.pseudopattern.map.client;


import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;


public class TileLayout implements EntryPoint {
	
	AvailableSchemesServiceAsync schemesService = 
		GWT.create(AvailableSchemesService.class);
	
	DefaultLinksServiceAsync dLinksService = 
		GWT.create(DefaultLinksService.class);
	
	int zoom,old_x,old_y;
	double offset_x,offset_y;
	TileWorker tw;
	WestBar wb;
	ParamSelector ps;
	MyImage[] currentTiles;
	
	private class TileWorker {
		
		LayoutPanel panel;
		
		public TileWorker(LayoutPanel panel){
			this.panel = panel;
			fill();
		}
		
		public void fill(){
			//panel.clear();
			int height = Window.getClientHeight() - 75;
			int width = Window.getClientWidth() - 150;
			String zStr = String.valueOf(zoom);
			int xTiles = 0,yTiles = 0,tiles = 0,basis = 4;
			String lambda = wb.getLambda();
			String query = wb.getQuery();
			query = wb.colorPart(query);
			if(zoom == 0){
				offset_x = (width-1024)/2.0;
				offset_y = (height-1024)/2.0;
				xTiles = yTiles = 4;
				tiles = 16;
			} else {
				xTiles = (int)Math.floor(width/256.0)+1;
				yTiles = (int)Math.floor(height/256.0)+1;
				tiles = xTiles*yTiles;
				offset_x = (width-xTiles*256)/2.0;
				offset_y = (height-yTiles*256)/2.0;
				basis = (int) (4*Math.pow(2, zoom));
				}
			if (currentTiles != null) {
				for (MyImage temp : currentTiles) {
					panel.remove(temp);
				}
			}
			int x_init = 0;
			int y_init = 0;
			if(zoom > 0){
				x_init = initialX(xTiles,basis);
				y_init = initialY(yTiles,basis);
			}
			currentTiles = new MyImage[tiles];
			for(int i=0;i<tiles;i++){
				currentTiles[i] = new MyImage();
				int x = i % xTiles;
				int y = i / xTiles;
				int current_x = x_init + x;
				int current_y = y_init + y;
				currentTiles[i].setUrl("/slick/"+lambda+"x"+zStr+"x"+
						current_x+"x"+current_y+query);
				panel.add(currentTiles[i]);
				panel.setWidgetLeftWidth(currentTiles[i], offset_x + 256*x, Unit.PX, 256, Unit.PX);
				panel.setWidgetTopHeight(currentTiles[i], offset_y + 256*y, Unit.PX, 256, Unit.PX);
				Zoomer zoomer = new Zoomer(current_x,current_y);
				currentTiles[i].addDoubleClickListener(zoomer);
				currentTiles[i].addMouseWheelHandler(zoomer);
			}
			
		}
		
		
		private int initialX(int tiles,int basis){
			int focus = old_x*2;
			int over = tiles/2;
			int init = focus-over;
			if(init < 0)
				return 0;
			if(init + tiles/2.0 > basis)
				return basis-tiles-1;
			return init;
		}
		
		private int initialY(int tiles,int basis){
			int focus = old_y*2;
			int over = tiles/2;
			int init = focus-over;
			if(init < 0)
				return 0;
			if(init + tiles/2.0 > basis)
				return basis-tiles-1;
			return init;
		}
		
	}
	
	private class Zoomer implements DoubleClickHandler,MouseWheelHandler{

		int x,y;
		
		public Zoomer(int x,int y){
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			zoom++;
			old_x = x;
			old_y = y;
			tw.fill();
		}

		@Override
		public void onMouseWheel(MouseWheelEvent event) {
			if(event.isNorth()){
				zoom++;
				old_x = x;
				old_y = y;
				tw.fill();
			} else if(event.isSouth()){
				if (zoom > 0) {
					if (zoom == 1) {
						zoom = 0;
						tw.fill();
					} else {
						old_x = x/4;
						old_y = y/4;
						zoom--;
						tw.fill();
					}
				}
			} 
		}
		
		
	}
	
	private class MyImage extends Image {
		
		public MyImage(){
			super();
		}
		
		public HandlerRegistration addDoubleClickListener(DoubleClickHandler h){
			return addDomHandler(h,DoubleClickEvent.getType());
		}
	}

	@Override
	public void onModuleLoad() {
		currentTiles = null;
		offset_x = offset_y = zoom = 0;
		old_x=old_y;
		DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
		LayoutPanel northPanel = new LayoutPanel();
		LayoutPanel blank = new LayoutPanel();
		northPanel.add(blank);
		northPanel.setWidgetLeftWidth(blank, 0, Unit.PX, 150, Unit.PX);
		HTML title = new HTML("<h1>Fractal Map</h1>");
		northPanel.add(title);
		northPanel.setWidgetLeftRight(title, 150, Unit.PX, 0, Unit.PCT);
		dock.addNorth(northPanel, 75);
		
		wb = new WestBar();
		ps = new ParamSelector();
		dock.addWest(wb, 150);
		LayoutPanel center = new LayoutPanel();
		center.addStyleName("tiles");
		dock.add(center);
		RootLayoutPanel.get().add(dock);
		wb.check();
		wb.addGenerateHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				generateLink();
				
			}
			
		});
		
		setUpParams();
		fillSchemes();
		defaultLinks();
		tw = new TileWorker(center);
		wb.applyListener(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				tw.fill();
				
			}
			
		});
		center.add(ps);
		center.setWidgetTopHeight(ps, 50, Unit.PX, 570, Unit.PX);
		center.setWidgetLeftWidth(ps, 0, Unit.PX, 500, Unit.PX);
		ps.setVisible(false);
		wb.addPlaneHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ps.setVisible(true);
				
			}
			
		});
		
		ps.addImageHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ps.setVisible(false);
				wb.setReal(ps.getX());
				wb.setImag(ps.getY());
				
			}
			
		});
		
	}
	
	
	
	private void defaultLinks() {
		dLinksService.getLinks(new AsyncCallback<LinkInfo[]>(){

			@Override
			public void onFailure(Throwable caught) {
				
				
			}

			@Override
			public void onSuccess(LinkInfo[] result) {
				FlowPanel panel = wb.defaultPanel();
				for(LinkInfo info : result){
					panel.add(new Anchor(info.name,info.url));
					panel.add(new HTML("<br>"));
				}
				
			}
			
		});
		
	}

	private void setUpParams(){
		Map<String,List<String>> params = Window.Location.getParameterMap();
		if(params.containsKey("real")){
			wb.setReal(params.get("real").get(0));
		}
		if(params.containsKey("imag")){
			wb.setImag(params.get("imag").get(0));
		}
		if(params.containsKey("m")){
			wb.setM(params.get("m").get(0));
		}
		if(params.containsKey("n")){
			wb.setN(params.get("n").get(0));
		}
		if(params.containsKey("scheme")){
			wb.setScheme(params.get("scheme").get(0));
		}
		if(params.containsKey("colorparam")){
			wb.setColorParam(params.get("colorparam").get(0));
		}
		if(params.containsKey("zoom")&&params.containsKey("oldx")&&
				params.containsKey("oldy")){
			int temp = zoom;
			int oldx = 3,oldy = 3;
			try {
				temp = Integer.parseInt(params.get("zoom").get(0));
				oldx = Integer.parseInt(params.get("oldx").get(0));
				oldy = Integer.parseInt(params.get("oldy").get(0));
			} catch (NumberFormatException e){
				
			}
			zoom = temp;
			old_x = oldx;
			old_y = oldy;
		}
	}
	
	private void fillSchemes(){
		
		schemesService.getSchemes(wb.notRainbow(),new AsyncCallback<String[]>(){

			@Override
			public void onFailure(Throwable caught) {
				
				
			}

			@Override
			public void onSuccess(String[] result) {
				wb.addToSchemes(result);
				
			}
			
		});
	}
	
	private void generateLink() {
		wb.check();
		UrlBuilder builder = new UrlBuilder();
		builder.setHost("map.pseudopattern.com");
		builder.setProtocol("http");
		builder.setPath("TileLayout.html");
		wb.setParams(builder);
		if(zoom!=0){
			builder.setParameter("zoom", String.valueOf(zoom));
			builder.setParameter("oldx", String.valueOf(old_x));
			builder.setParameter("oldy", String.valueOf(old_y));
		}
		FlowPanel fp = wb.getCustomLinks();
		fp.clear();
		fp.add(new Anchor("My Link",builder.buildString()));
	}

}
