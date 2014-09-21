package kingswood.idphoto.vo;

import java.io.Serializable;

public class PhotoSize implements Serializable {

	private static final long serialVersionUID = 9054878810076517964L;
	
	private String desc;
	private float width;
	private float height;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
}
