package kingswood.idphoto.vo;

import java.io.Serializable;

public class PhotoSize implements Comparable<PhotoSize>, Serializable {

	private static final long serialVersionUID = 9054878810076517964L;
	
	private double width;
	private double height;
	private String desc;
	private int id; 
	
	
	
	public PhotoSize(){
		
	}
	
	public PhotoSize(double width, double height, String desc, int id){
		this.width = width;
		this.height = height;
		this.desc = desc;
		this.id = id;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(PhotoSize another) {
		return this.getId() - another.getId();
	}
	
}
