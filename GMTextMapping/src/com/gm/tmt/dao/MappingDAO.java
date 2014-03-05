package com.gm.tmt.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gm.tmt.bean.MappingBean;
import com.gm.tmt.bean.ScreenLayerBean;

public class MappingDAO {

	static String dburl;
	static String dbusername;
	static String dbpassword;
	static Connection conn = null;
	static PreparedStatement pst =null;
	static ResultSet rs = null;
	static List<MappingBean> mappingBeanList = null;
	static Map<String, MappingBean> mapBean = null;
	static List<String> screenNames = null;
	static List<ScreenLayerBean> screenLayers = null;
	static int master_id;
	static int shortkey;

	public static void getProperty(){

		InputStream in = null;
		String propertiesFile = "db.properties";
		Properties defaultProps = new Properties();
		try {
			in = MappingDAO.class.getClassLoader().getResourceAsStream(propertiesFile);
			if (in != null) {
				defaultProps.load(in);
				dburl = defaultProps.getProperty("dburl");
				dbpassword = defaultProps.getProperty("dbpassword");
				dbusername = defaultProps.getProperty("dbusername");
			}
		} catch (IOException ex) {
			System.out.println("The properties file was not found!");
			return;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					System.out.println("IO Error closing the properties file");
					return;
				}
			}
		}
	}

	public static void getId(){
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select max(master_id) as master_id, max(shortkey) as shortkey from terms");
			rs = pst.executeQuery();
			while(rs.next()){
				master_id = rs.getInt("master_id");
				shortkey = rs.getInt("shortkey");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}

	public static Connection getConnection() throws InstantiationException, IllegalAccessException {

		getProperty();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbusername, dbpassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(){
		try{
			if(pst!=null)
				pst.close();
			if(rs!=null)
				rs.close();
			if(conn!=null)
				conn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}

	public static List<MappingBean> getTextString(){
		mappingBeanList = new ArrayList<MappingBean>();
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select english_us_string from terms");
			rs = pst.executeQuery();
			while(rs.next()){
				mappingBeanList.add(new MappingBean(rs.getString("english_us_string")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return mappingBeanList;
	}

	public static List<MappingBean> getSearchData(String searchTerm){
		mappingBeanList = new ArrayList<MappingBean>();

		try{
			conn = getConnection();
			pst = conn.prepareStatement("select english_us_string, max_pixel_width from terms where english_us_string like '%"+searchTerm+"%' " );
			rs = pst.executeQuery();
			while(rs.next()){
				mappingBeanList.add(new MappingBean("", 
						rs.getString("english_us_string"),
						rs.getString("max_pixel_width")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return mappingBeanList;
	}

	public void getDatabaseMetaData() throws InstantiationException, IllegalAccessException
	{
		try {

			conn = getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			String[] types = {"TABLE"};

			ResultSet rs = dbmd.getTables(null, null, "%", types);
			while (rs.next()) {
				System.out.println(rs.getMetaData());
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean addNewTerm(String textString,
			String termDescription){

		getId();	/* get max val of master_id and shortkey from terms table */
		boolean inserted = false;
		try{
			conn = getConnection();
			pst = conn.prepareStatement("insert into terms values(?,?,?,?,?,?,?,?,?,?,?)");
			pst.setInt(1, ++master_id);
			pst.setInt(2, 000000);		/* shortkey has to be 0 for new terms; indicating it is a new term */
			pst.setString(3, textString);
			pst.setString(4, termDescription);
			pst.setString(5, "");
			pst.setString(6, "");
			pst.setString(7, "");
			pst.setString(8, "");
			pst.setString(9, "");
			pst.setString(10, "");
			pst.setString(11, "");
			int i = pst.executeUpdate();
			if(i==1){
				inserted = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return inserted;
	}

	public static String getShortKey(){

		String shortKeyId  = "";
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select idshortkey from generalmotors.shortkey limit 1");
			rs = pst.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				shortKeyId = rs.getString("idshortkey");
			}
			pst = conn.prepareStatement("insert into generalmotors.addedshortkeys values('"+shortKeyId+"')");
			pst.executeUpdate();
			pst = conn.prepareStatement("delete from generalmotors.shortkey where idshortkey='"+shortKeyId+"'");
			pst.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return shortKeyId;
	}
	
	public static String getShortKey(String term) {
		String shortkey = "";
		try {
			System.out.println("select shortkey from generalmotors.terms where english_us_string = '" + term + "'");
			conn = getConnection();
			pst = conn.prepareStatement("select shortkey from generalmotors.terms where english_us_string = '" + term + "'");
			rs = pst.executeQuery();
			while (rs.next()) {
				System.out.println("Shortkey -> " + rs.getString("shortkey"));
				if (rs.getString("shortkey") == "") {
					shortkey = "000000";
				} else 
					shortkey = rs.getString("shortkey");
			}
			System.out.println("shortkey ---> " + shortkey);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection();
		}
		return shortkey;
	}

	public static String getDate(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");
		return sdf.format(date);
	}

	public static boolean insertScreenDetails(String file_name, String screen_name, String project_name, String model_year, String domain){
		System.out.println("pkvn - " + file_name + screen_name + project_name);
		int screenId = getScreenDataFrmTable(file_name, screen_name, project_name);
		System.out.println("insertScreenDetails::screenId - " + screenId);
		boolean insert = false;
		if(screenId==0){
			try{
				System.out.println("Inside try...");
				conn = getConnection();
				pst = conn.prepareStatement("insert into generalmotors.screen(file_name, screen_name, project_name, model_year, domain, creation_timeStamp,"
						+ "created_by, last_edited_timestamp, last_edit_by) values(?,?,?,?,?,?,?,?,?)");
				pst.setString(1, file_name);	 /*screen name*/ 	
				pst.setString(2, screen_name);	 /*file name*/ 	
				pst.setString(3, project_name);	 /*project name*/ 
				pst.setString(4, model_year);	 /*model year*/ 	
				pst.setString(5, domain);		 /*domain*/ 		
				pst.setString(6, getDate());
				pst.setString(7, "guest");
				pst.setString(8, getDate());
				pst.setString(9, "");
				System.out.println("pst toString() - " + pst.toString());
				int i = pst.executeUpdate();
				if(i == 1){
					insert = true;
				}
			}catch(SQLException sqlex){
				sqlex.getMessage();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				closeConnection();
			}
			return insert;
		}else{
			return insert;
		}
	}

	/**
	 * It will read the JSON file from the service and inserts into the table by getting the screen id
	 * @param filename
	 * @param screenname
	 * @param projectname
	 * @param mappedData
	 * @return
	 */
	public static boolean readJSON(String filename, String screenname, String projectname, String mappedData){

		int screenId = getScreenDataFrmTable(filename, screenname, projectname);
		System.out.println("readJSON screenId - " + screenId);
		JSONParser parser = new JSONParser();
		JSONArray a = null;
		try {
			a = (JSONArray) parser.parse(mappedData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int i=0;
		String[] lname = new String[a.size()];
		String[] fWidth = new String[a.size()];
		String[] font_family = new String[a.size()];
		String[] font_size = new String[a.size()];
		String[] font_style = new String[a.size()];
		String[] skey = new String[a.size()];
		String[] file_name = new String[a.size()];
		String[] screen_name = new String[a.size()];
		String[] project_name = new String[a.size()];

		if(screenId!=0){
			for (Object obj : a) {
				JSONObject jsonObject = (JSONObject) obj;
				String layerName = (String) jsonObject.get("layerName");
				lname[i] = layerName;
				String fieldWidth = (String) jsonObject.get("fieldWidth");
				fWidth[i] = fieldWidth;
				System.out.println("fieldWidth - " + fieldWidth);
				String fontFamily = (String) jsonObject.get("fontFamily");
				font_family[i] = fontFamily;
				System.out.println("fontFamily - " + fontFamily);
				String fontSize = (String) jsonObject.get("fontSize");
				font_size[i] = fontSize;
				String fontStyle = (String) jsonObject.get("fontStyle");
				font_style[i] = fontStyle;
				String shortkey = (String) jsonObject.get("shortkey");
				skey[i] = shortkey; 
				String fileName = (String) jsonObject.get("fileName");
				file_name[i] = fileName; 
				String screenName = (String) jsonObject.get("screenName");
				screen_name[i] = screenName; 
				String projectName = (String) jsonObject.get("projectName");
				project_name[i] = projectName; 
				i++;
			}
			return insertDataIntoScrnLyrs(screenId, lname, fWidth, font_family, font_size, font_style, skey, file_name, screen_name, project_name);
		}else{
			return false;
		}
	}

	/**
	 * 
	 * @param screenId
	 * @param lname
	 * @param fWidth
	 * @param font_family
	 * @param font_size
	 * @param font_style
	 * @param skey
	 * @param file_name
	 * @param screen_name
	 * @param project_name
	 * @return
	 */
	public static boolean insertDataIntoScrnLyrs(int screenId,
			String[] lname, 
			String[] fWidth, 
			String[] font_family, 
			String[] font_size, 
			String[] font_style,
			String[] skey, 
			String[] file_name,
			String[] screen_name, 
			String[] project_name) {
	//	String checkQuery = "select * from generalmotors.screen_layers where screenId = '"+screenId+"'";
	//	String delstmt = "delete from generalmotors.screen_layers where screenId = '"+screenId+"'";
		boolean chkStatus = checkScreenId(screenId);
		boolean delSuccess = false;
		try{
			if(chkStatus){
				delSuccess = deleteScreenIdDetails(screenId);
			}
			else
				delSuccess = true;
			//System.out.println(checkQuery);
			//System.out.println(delstmt);

			/* check if screen already exists */
		//	pst = conn.prepareStatement(checkQuery);

			//ResultSet rSet = pst.executeQuery();
			//System.out.println("Screen already present in screen_layers: " + rSet.next());

			/*if (rSet.next()) {
				 delete all screen layer entries for a screenId from the table if a screen already exists 
				pst = conn.prepareStatement(delstmt);
				delSuccess = pst.execute();
				System.out.println(delSuccess);
			}
			else 
				delSuccess = true;
*/			
			/* after successful deletion insert the fresh values */
			if (delSuccess) {
				System.out.println("in if");
				conn = getConnection();
				pst = conn.prepareStatement("insert into screen_layers(screenId, file_name, screen_name, project_name, layer_name,"
						+ "field_width, font_family, font_size, font_style, lblshortkey) values(?,?,?,?,?,?,?,?,?,?)");
				for(int i=0;i<lname.length;i++){
					pst.setInt(1, screenId);
					pst.setString(2, file_name[i]);
					pst.setString(3, screen_name[i]);
					pst.setString(4, project_name[i]);
					pst.setString(5, lname[i]);
					pst.setString(6, fWidth[i]);
					pst.setString(7, font_family[i]);
					pst.setString(8, font_size[i]);
					pst.setString(9, font_style[i]);
					pst.setString(10, skey[i]);
					pst.addBatch();
				}
				pst.executeBatch();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return true;
	}
	
	/**
	 * To Check if Screen layers details exists for the particualr screen id
	 * @param screenid
	 * @return boolean
	 */
	public static boolean checkScreenId(int screenid){

		boolean chkStatus = false;
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select screenId from generalmotors.screen_layers where screenId = '"+screenid+"'");
			rs = pst.executeQuery();
			if(rs.next()){
				chkStatus = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return chkStatus;
	}
	
	/**
	 * Delete the details if screen id present on screen layers
	 * @param screenid
	 * @return boolean
	 */
	public static boolean deleteScreenIdDetails(int screenid){

		boolean delStatus = false;
		try{
			conn = getConnection();
			pst = conn.prepareStatement("delete from generalmotors.screen_layers where screenId = '"+screenid+"'");
			int i = pst.executeUpdate();
			if(i==1){
				delStatus = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return delStatus;
	}

	/**
	 * Gets the data from the Screen table for the given file_name, screen_name and project_name. <p>
	 * This helps to find if a file is already present in the Screen table.
	 * @author pavana
	 * @param String file_name
	 * @param String screen_name
	 * @param String project_name
	 * @return void
	 */
	public static int getScreenDataFrmTable(String file_name, String screen_name, String project_name) {

		System.out.println("getScreenDataFrmTable");
		int screen_id = 0;
		try {
			conn = getConnection();
			pst = conn.prepareStatement("select screenId from generalmotors.screen where file_name='"+file_name+"' and "
					+ "screen_name='"+screen_name+"' and project_name='"+project_name+"'");
			rs = pst.executeQuery();
			while (rs.next()) {
				screen_id = rs.getInt("screenId");
				System.out.println("while: "+screen_id);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection();
		}
		System.out.println("getScreenDataFrmTable - done");
		return screen_id;
	}
	
	/**
	 * This method will return the screen Names from screen table
	 * @return List<String>
	 */
	public static List<String> getScreenNames(){

		screenNames = new ArrayList<String>();
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select screen_name from screen group by screen_name");
			rs = pst.executeQuery();
			while(rs.next()){
				screenNames.add(rs.getString("screen_name"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return screenNames;
	}
	
	/**
	 * This method will return the layer details based on the screen name
	 * @param screenName
	 * @return
	 */
	public static List<ScreenLayerBean> getScreenLayersDetails(String screenName){
		
		screenLayers = new ArrayList<ScreenLayerBean>();
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select * from screen_layers where screen_name='"+screenName+"'");
			rs = pst.executeQuery();
			
			while(rs.next()){
				System.out.println(rs.getString("file_name"));
				screenLayers.add(new ScreenLayerBean(rs.getInt("screenId"), 
													 rs.getString("file_name"), 
													 rs.getString("screen_name"),
													 rs.getString("project_name"), 
													 rs.getString("layer_name"), 
													 rs.getString("field_width"), 
													 rs.getString("font_family"), 
													 rs.getString("font_size"), 
													 rs.getString("font_style"), 
													 rs.getString("lblShortKey")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return screenLayers;
	}

	public static void main(String args[]) {
		System.out.println(getScreenDataFrmTable("Dashboard_Panel_Master.json", "Panel", "Dashboard"));
		System.out.println(getShortKey("FRONT"));
		System.out.println("Added Print in Main Method of DAO");
	}
}
