package com.pseudopattern.map.server;


import java.io.IOException;
import java.util.Map;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.pseudopattern.map.client.TileInfo;



@SuppressWarnings("serial")
public class TileImageService extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		resp.setContentType("image/png");
		String[] parts = req.getRequestURI().split("/");
		TileInfo tileInfo = getInfo(parts[parts.length - 1]);
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ServletOutputStream sos = resp.getOutputStream();
		PNG png = null;
		
		int m = 3,n = 3;
		Map<String,String[]> params = req.getParameterMap();
		Mapper map;
		if(params.containsKey("m")){
			String[] ss = params.get("m");
			//System.out.println("m has "+ss.length+" pieces");
			m = Integer.parseInt(ss[0]);
		}
		if(params.containsKey("n")){
			String[] ss = params.get("n");
			//System.out.println("n has "+ss.length+" pieces");
			n = Integer.parseInt(ss[0]);
		}
		if(params.containsKey("scheme")){
			int param = 20;
			if(params.containsKey("colorparam")){
				param = Integer.parseInt(params.get("colorparam")[0]);
			}
			SimpleMap smap = ColorCache.getMap(params.get("scheme")[0], param);
			png = new PNG(sos,smap);
		} else {
			png = new PNG(sos);
		}
		map = new Mapper(new Complex(tileInfo.lambda_x,
				tileInfo.lambda_y), m, n);
		int gridSize = 256;
		Complex center = new Complex(tileInfo.x, tileInfo.y);
		double delta = tileInfo.length / gridSize;
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
	}
	
	private TileInfo getInfo(String graphicName){

		String[] specs = graphicName.split("x");
		TileInfo info = new TileInfo();
		if (specs.length == 1) {
			info.x = 0.5;
			info.y = -0.5;
			info.lambda_x = 0;
			info.lambda_y = 0.125;
			info.length = 1;
			return info;
		}
		if(specs[0].contains("_")){
			String[] parts = specs[0].split("_");
			info.lambda_x = Double.parseDouble(parts[0]);
			info.lambda_y = Double.parseDouble(parts[1]);
		} else {
			info.lambda_x = Double.parseDouble(specs[0]);
			info.lambda_y = 0.0;
		}
		int zoom = Integer.parseInt(specs[1]);
		info.length = Math.pow(0.5, zoom);
		//System.out.println(specs[2]);
		String[] last = specs[2].split(".png");
		int boxId = Integer.parseInt(last[0]);
		int N = 4*Math.round((float)Math.pow(2,zoom));
		double mult = initMultiple(N);
		double x_init = - mult * info.length;
		double y_init =  mult * info.length;
		//System.out.println("("+x_init+","+y_init+")");
		info.x = x_init + info.length*(boxId % N);
		info.y = y_init - info.length*(boxId / N);
		//System.out.println("("+info.x+","+info.y+")");
		return info;
		
	}
	
	private double initMultiple(int N){
		if(N==4){
			return 1.5;
		}
		return (N/2)-0.5;
	}
	

}
