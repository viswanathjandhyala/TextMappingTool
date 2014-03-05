package com.gm.tmt.bean;

public class ScreenLayerBean {
	
	private int screenId;
	private String file_name;
	private String screen_name;
	private String project_name;
	private String layer_name;
	private String field_width;
	private String font_family;
	private String font_size;
	private String font_style;
	private String lblShortKey;
	
	public ScreenLayerBean(int screenId, 
						   String file_name,
						   String screen_name,
						   String project_name,
						   String layer_name,
						   String field_width,
						   String font_family,
						   String font_size,
						   String font_style,
						   String lblShortKey) {
		this.screenId = screenId;
		this.file_name = file_name;
		this.screen_name = screen_name;
		this.project_name = project_name;
		this.layer_name = layer_name;
		this.field_width = field_width;
		this.font_family = font_family;
		this.font_size = font_size;
		this.font_style = font_style;
		this.lblShortKey = lblShortKey;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getLayer_name() {
		return layer_name;
	}

	public void setLayer_name(String layer_name) {
		this.layer_name = layer_name;
	}

	public String getField_width() {
		return field_width;
	}

	public void setField_width(String field_width) {
		this.field_width = field_width;
	}

	public String getFont_family() {
		return font_family;
	}

	public void setFont_family(String font_family) {
		this.font_family = font_family;
	}

	public String getFont_size() {
		return font_size;
	}

	public void setFont_size(String font_size) {
		this.font_size = font_size;
	}

	public String getFont_style() {
		return font_style;
	}

	public void setFont_style(String font_style) {
		this.font_style = font_style;
	}

	public String getLblShortKey() {
		return lblShortKey;
	}

	public void setLblShortKey(String lblShortKey) {
		this.lblShortKey = lblShortKey;
	}

}
