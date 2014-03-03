package com.gm.tmt.bean;

public class MappingBean {

	private int id;
	private int fid;
	private String property_name;
	private String text_string;
	private String font_name;
	private String font_style;
	private String font_size;
	private String max_pixel_width;
	private String no_of_lines;
	private String height;
	private String resolution;
	private String context;
	private String shortkey;
	private String max_language;
	private int other_lang_trans_count;
	private String new_request;
	
	public MappingBean(){}
	
	/*public MappingBean(String propertyName, String developerText,String font,String fontSize,String fieldLength){
		this.PropertyName = propertyName;
		this.DeveloperText = developerText;
		this.Fontsize = fontSize;
		this.FieldLength = fieldLength;
		this.Font = font;
	}
	
	public MappingBean(String propertyName,String status,String developerText, String englishUS){
		this.PropertyName = propertyName;
		this.Status = status;
		this.DeveloperText = developerText;
		this.EnglishUS = englishUS;
	}*/
	
	public MappingBean(String propertyName,String textString, String maxPixelWidth){
		this.property_name = propertyName;
		this.text_string = textString;
		this.max_pixel_width = maxPixelWidth;
	}
	
	public MappingBean(String textString){
		this.text_string = textString;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public String getText_string() {
		return text_string;
	}

	public void setText_string(String text_string) {
		this.text_string = text_string;
	}

	public String getFont_name() {
		return font_name;
	}

	public void setFont_name(String font_name) {
		this.font_name = font_name;
	}

	public String getFont_style() {
		return font_style;
	}

	public void setFont_style(String font_style) {
		this.font_style = font_style;
	}

	public String getFont_size() {
		return font_size;
	}

	public void setFont_size(String font_size) {
		this.font_size = font_size;
	}

	public String getMax_pixel_width() {
		return max_pixel_width;
	}

	public void setMax_pixel_width(String max_pixel_width) {
		this.max_pixel_width = max_pixel_width;
	}

	public String getNo_of_lines() {
		return no_of_lines;
	}

	public void setNo_of_lines(String no_of_lines) {
		this.no_of_lines = no_of_lines;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getShortkey() {
		return shortkey;
	}

	public void setShortkey(String shortkey) {
		this.shortkey = shortkey;
	}

	public String getMax_language() {
		return max_language;
	}

	public void setMax_language(String max_language) {
		this.max_language = max_language;
	}

	public int getOther_lang_trans_count() {
		return other_lang_trans_count;
	}

	public void setOther_lang_trans_count(int other_lang_trans_count) {
		this.other_lang_trans_count = other_lang_trans_count;
	}

	public String getNew_request() {
		return new_request;
	}

	public void setNew_request(String new_request) {
		this.new_request = new_request;
	}
}