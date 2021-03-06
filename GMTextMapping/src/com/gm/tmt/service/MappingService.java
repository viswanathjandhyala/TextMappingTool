package com.gm.tmt.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.gm.tmt.bean.MappingBean;
import com.gm.tmt.bean.ScreenLayerBean;
import com.gm.tmt.controller.MappingController;

@Path("/mappingservice")
public class MappingService {

	static List<MappingBean> mappingBeanList = null;
	static List<String> screenNames = null;
	static List<ScreenLayerBean> screenLayers = null;

	@SuppressWarnings("unchecked")
	@GET
	@Path("/textstring/")
	@Produces("application/json")
	public String getTextString(){
		mappingBeanList = MappingController.getTextString();
		JSONObject textString = new JSONObject();
		JSONObject textStringData = new JSONObject();
		JSONArray devText = new JSONArray();
		for(int i=0;i<mappingBeanList.size();i++){
			textStringData = new JSONObject();
			textStringData.put("term", mappingBeanList.get(i).getText_string());
			devText.add(textStringData);
		}
		textString.put("textString", devText);
		return textString.toJSONString();
	}

	@SuppressWarnings({ "unchecked"})
	@GET
	@Path("/searchdata/{searchTerm}")
	@Produces("application/json")
	public String getSearchData(@PathParam("searchTerm") String searchTerm){
		JSONObject data = new JSONObject();
		JSONObject results = new JSONObject();
		JSONArray dataArray = new JSONArray();

		if(!searchTerm.equalsIgnoreCase("")){
			mappingBeanList = MappingController.getSearchData(searchTerm);

			for(int i=0;i<mappingBeanList.size();i++){
				data = new JSONObject();
				data.put("propertyname", mappingBeanList.get(i).getProperty_name());
				data.put("textstring", mappingBeanList.get(i).getText_string());
				data.put("maxpixelwidth", mappingBeanList.get(i).getMax_pixel_width());
				dataArray.add(data);
			}
			results.put("results", dataArray);
		}else{
			results.put("results", "");
		}
		return results.toJSONString();
	}
	
	/**
	 * 
	 * @param textString
	 * @param termDescription
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	@POST
	@Path("/newterm/{textstring},{content}")
	@Produces("application/json")
	public String addNewTerm(@PathParam("textstring") String textString,
			@PathParam("content") String termDescription){

		JSONObject tmtInsertData = new JSONObject();
		JSONObject tmtInsert = new JSONObject();
		JSONArray tmtInsertArray = new JSONArray();
		boolean insert = false;
		insert = MappingController.addNewTerm(textString, termDescription);
		tmtInsert.put("value", insert);
		tmtInsertArray.add(tmtInsert);
		tmtInsertData.put("insert", tmtInsertArray);
		return tmtInsertData.toJSONString();
	}

	@SuppressWarnings({ "unchecked"})
	@GET
	@Path("/shortkey/")
	@Produces("application/json")
	public String getShortKey(){

		String shortKeyId = "";
		JSONObject shortKeyData = new JSONObject();
		JSONObject shortKey = new JSONObject();
		JSONArray shortKeyArray = new JSONArray();
		shortKeyId = MappingController.getShortKey();
		shortKey.put("shortkey", shortKeyId);
		shortKeyArray.add(shortKey);
		shortKeyData.put("id", shortKeyArray);
		return shortKeyData.toJSONString();
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	@GET
	@Path("/shortkey/{term}")
	@Produces("application/json")
	public String getShortKey(@PathParam("term") String term){
		String shortKeyId = "";
		JSONObject shortKeyData = new JSONObject();
		JSONObject shortKey = new JSONObject();
		JSONArray shortKeyArray = new JSONArray();
		shortKeyId = MappingController.getShortKey(term);
		shortKey.put("shortkey", shortKeyId);
		shortKeyArray.add(shortKey);
		shortKeyData.put("id", shortKeyArray);
		return shortKeyData.toJSONString();
	}

	/**
	 * Inserts screen information into 'screen' table for every unique combination of <p>
	 * file name, screen name and project name.
	 * @param fileName
	 * @param screenName
	 * @param projectName
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	@POST
	@Path("/addscreen/{fileName},{screenName},{projectName},{modelyear},{domain}")
	@Produces("application/json")
	public String insertScreenDeatils(@PathParam("fileName") String fileName, 
			@PathParam("screenName") String screenName,
			@PathParam("projectName") String projectName,
			@PathParam("modelyear") String modelYear,
			@PathParam("domain") String domain){

		JSONObject fileInsert = new JSONObject();
		JSONObject fileInsertData = new JSONObject();
		JSONArray fileInsertArray = new JSONArray();
		boolean inserted = MappingController.insertScreenDetails(fileName, screenName, projectName, modelYear, domain);
		fileInsert.put("addedfile", inserted);
		fileInsertArray.add(fileInsert);
		fileInsertData.put("inserted", fileInsertArray);
		return fileInsertData.toJSONString();
	}

	/**
	 * Inserts screen layer information into 'screen_layers' information table.
	 * @param mappedData
	 * @param fileName
	 * @param screenName
	 * @param projectName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/mappeddata/")
	@Produces("application/json")
	public String insertScreenLayers(@QueryParam("mappedData") String mappedData,
			@QueryParam("fileName") String fileName, 
			@QueryParam("screenName") String screenName,
			@QueryParam("projectName") String projectName){

		JSONObject mappInsert = new JSONObject();
		JSONObject mappInsertData = new JSONObject();
		JSONArray mappInsertArray = new JSONArray();
		boolean inserted = MappingController.insertScreenLayers(fileName, screenName, projectName, mappedData);
		mappInsert.put("inserted", inserted);
		mappInsertArray.add(mappInsert);
		mappInsertData.put("insert", mappInsertArray);
		return mappInsertData.toJSONString();
	}
	
	/**
	 * This method will get the screen names from screen table
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/screenName/")
	@Produces("application/json")
	public String getScreeNames(){
		screenNames = MappingController.getScreenNames();
		JSONObject screenName = new JSONObject();
		JSONObject screenNameData = new JSONObject();
		JSONArray screenNameArray = new JSONArray();
		for(int i=0;i<screenNames.size();i++){
			screenNameData = new JSONObject();
			screenNameData.put("screenName", screenNames.get(i));
			screenNameArray.add(screenNameData);
		}
		screenName.put("Screen_Names", screenNameArray);
		return screenName.toJSONString();
	}
	
	/**
	 * This method will get the screen layer details based on screen Name selected
	 * @param screenName
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/screenLayers/{screenName}")
	@Produces("application/json")
	public String getScreeLayerdetails(@PathParam("screenName") String screenName){
		
		System.out.println(screenName);
		screenLayers = MappingController.getScreenLayersDetails(screenName);
		JSONObject screenLayer = new JSONObject();
		JSONObject screenLayerData = new JSONObject();
		JSONArray screenLayerArray = new JSONArray();
		for(int i=0;i<screenLayers.size();i++){
			screenLayerData = new JSONObject();
			screenLayerData.put("screenId", screenLayers.get(i).getScreenId());
			screenLayerData.put("fileName", screenLayers.get(i).getFile_name());
			screenLayerData.put("screenName", screenLayers.get(i).getScreen_name());
			screenLayerData.put("projectName", screenLayers.get(i).getProject_name());
			screenLayerData.put("layerName", screenLayers.get(i).getLayer_name());
			screenLayerData.put("fieldWidth", screenLayers.get(i).getField_width());
			screenLayerData.put("fontFamily", screenLayers.get(i).getFont_family());
			screenLayerData.put("fontSize", screenLayers.get(i).getFont_size());
			screenLayerData.put("fontStyle", screenLayers.get(i).getFont_style());
			screenLayerData.put("lblShortKey", screenLayers.get(i).getLblShortKey());
			screenLayerArray.add(screenLayerData);
		}
		screenLayer.put("aaData", screenLayerArray);
		return screenLayer.toJSONString();
	}
}