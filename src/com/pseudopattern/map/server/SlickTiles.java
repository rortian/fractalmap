package com.pseudopattern.map.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class SlickTiles extends HttpServlet {
	
	private static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	
	private static int defaultColorParam = 20;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("image/png");
		String path = req.getPathInfo();
		String info = path.substring(1);
		Map<String,String[]> params = req.getParameterMap();
		TileDescription key = getDescription(info,params);
		String color = "Rainbow";
		int param = defaultColorParam;
		if(params.containsKey("scheme")){
			color = params.get("scheme")[0];
		}
		if(params.containsKey("colorparam")){
			param = Integer.parseInt(params.get("colorparam")[0]);
		}
		if(memcache.contains(key)){
			ImageData iData = (ImageData) memcache.get(key);
			if((iData.getParam()==param)&&(color.equals(iData.getColor()))){
				resp.getOutputStream().write(iData.getData());
			} else {
				ColorMap cm  = Rainbow.map;
				if(!color.equals("Rainbow")){
					cm = ColorCache.getMap(color, param);
				}
				byte[] data = iData.getData();
				PNGHelper helper = new PNGHelper();
				helper.replaceColors(data, cm);
				resp.getOutputStream().write(data);
			}
		} else {
			PNG png;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			if(color.equals("Rainbow")){
				png = new PNG(os);
			} else {
				SimpleMap sm = ColorCache.getMap(params.get("scheme")[0], param);
				png = new PNG(os,sm);
			}
			double length = Math.pow(0.5, key.getZoom());
			int N = 4*Math.round((float)Math.pow(2,key.getZoom()));
			double mult = (N/2)-0.5;
			double x_init = - mult * length;
			double y_init =  mult * length;
			double x_info = x_init + length*(key.getX_id());
			double y_info = y_init - length*(key.getY_id());
			Mapper map = new Mapper(new Complex(key.getReal(),
					key.getImag()), key.getM(), key.getN());
			int gridSize = 256;
			Complex center = new Complex(x_info, y_info);
			double delta = length / gridSize;
			Complex temp = new Complex(-delta, delta);
			Complex init = center.plus(temp.times(gridSize / 2 + 0.5));
			Complex delta_x = Complex.ONE.times(delta);
			Complex delta_y = Complex.I.times(-delta);
			short[][] magOrbits = new short[gridSize][gridSize];
			for (int j = 0; j < gridSize; j++) {
				for (int i = 0; i < gridSize; i++) {
					temp = init.plus(delta_x.times(i)).plus(delta_y.times(j));
					magOrbits[j][i] = (short)map.escape(temp, 256);
				}
			}
		    png.setData(magOrbits);
		    png.writeFile();
		    byte[] data = os.toByteArray();
		    resp.getOutputStream().write(data);
		    resp.getOutputStream().close();
		    ImageData imageData = new ImageData(color,param,data);
		    memcache.put(key, imageData);
		}
	}

	
	
	
	public static TileDescription getDescription(String info,Map<String,String[]> params){
		String[] parts = info.split("x");
		double real,imag = 0.0;
		if(parts[0].contains("_")){
			String[] lambda = parts[0].split("_");
			real = Double.parseDouble(lambda[0]);
			imag = Double.parseDouble(lambda[1]);
		} else {
			real = Double.parseDouble(parts[0]);
		}
		int zoom = Integer.parseInt(parts[1]);
		int x_id = Integer.parseInt(parts[2]);
		int y_id = Integer.parseInt(parts[3]);
		int m = 3,n = 3;
		if(params.containsKey("m")){
			m = Integer.parseInt(params.get("m")[0]);
		}
		if(params.containsKey("n")){
			n = Integer.parseInt(params.get("n")[0]);
		}
		return new TileDescription(m,n,zoom,x_id,y_id,real,imag);
	}

}
