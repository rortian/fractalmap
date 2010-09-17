package com.pseudopattern.map.server;



import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pseudopattern.map.client.UploaderService;
import com.pseudopattern.map.server.interpolator.Interpolator;

public class UploaderServiceImpl extends RemoteServiceServlet implements
		UploaderService {

	@Override
	public Boolean upload(String object) {
		JSONParser parser = new JSONParser();
		//System.out.println(object);
		Object json = null;
		try {
			json = parser.parse(object);
		} catch (ParseException e) {
			
		}
		if(json instanceof Map){
			String type = (String)((Map)json).get("type");
			if(type.equals("spectrum")){
				return fileSpectrum((Map)json);
			}
			if(type.equals("link")){
				return fileLink((Map)json);
			}
		}
		return Boolean.FALSE;
	}
	
	private Boolean fileLink(Map json) {
		String name = (String)json.get("name");
		String url = (String)json.get("url");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			ALink link = new ALink(name,url);
			pm.makePersistent(link);
			return Boolean.TRUE;
		} finally {
			pm.close();
			DefaultLinksServiceImpl.clearLinks();
		}

	}

	private Boolean fileSpectrum(Map json){
		String name = (String)json.get("name");
		Object data = json.get("data");
		if(data instanceof Map){
			Interpolator red = makeInterpolator((Map) ((Map) data).get("red"));
			Interpolator green = makeInterpolator((Map) ((Map) data).get("green"));
			Interpolator blue = makeInterpolator((Map) ((Map) data).get("blue"));
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				try {
					pm.getObjectById(AColorSpectrum.class, name);
					return Boolean.FALSE;
				} catch (JDOObjectNotFoundException e){
					
				}
				AColorSpectrum yo = new AColorSpectrum(name,red,green,blue);
				pm.makePersistent(yo);
				return Boolean.TRUE;
			} finally {
				pm.close();
				AvailableSchemesServiceImpl.clearSchemes();
			}
		}
		return Boolean.FALSE;
	}
	
	private Interpolator makeInterpolator(Map color){
		JSONArray xs = (JSONArray) color.get("x");
		JSONArray ys = (JSONArray) color.get("y");
		double[] x = new double[xs.size()];
		double[] y = new double[ys.size()];
		for(int i=0;i<x.length;i++){
			x[i] =((Number)xs.get(i)).doubleValue();
		}
		for(int i=0;i<y.length;i++){
			y[i] = ((Number)ys.get(i)).doubleValue();
		}
		return Interpolator.get(x, y);
	}
	
	

}
