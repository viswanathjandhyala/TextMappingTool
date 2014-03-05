package com.gm.tmt.controller;

import java.util.List;

import com.gm.tmt.bean.MappingBean;
import com.gm.tmt.dao.MappingDAO;

public class MappingController {

	public static List<MappingBean> getTextString() {
		return MappingDAO.getTextString();
	}

	public static List<MappingBean> getSearchData(String searchTerm) {
		return MappingDAO.getSearchData(searchTerm);
	}

	public static boolean addNewTerm(String textString,
			String termDescription){
		return MappingDAO.addNewTerm(textString, termDescription);
	}
	
	public static boolean insertScreenLayers(String file_name, String screen_name, String project_name,String mappedData){
		return MappingDAO.readJSON(file_name, screen_name, project_name, mappedData);
	}
	
	public static String getShortKey(){
		return MappingDAO.getShortKey();
	}
	
	public static String getShortKey(String term){
		return MappingDAO.getShortKey(term);
	}
	
	public static boolean insertScreenDetails(String fileName, String screenName, String projectName, String modelYear, String domain){
		return MappingDAO.insertScreenDetails(fileName, screenName, projectName, modelYear, domain);
	}
}