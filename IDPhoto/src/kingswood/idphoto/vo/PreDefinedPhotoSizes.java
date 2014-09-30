package kingswood.idphoto.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PreDefinedPhotoSizes {
	
	private static List<PhotoSize> PHOTO_SIZES = new ArrayList<PhotoSize>();
	
	private static List<PhotoSize> SYSTEM_PHOTO_SIZES = new ArrayList<PhotoSize>();
	
	private static PreDefinedPhotoSizes INSTANCE; 
	
	private PreDefinedPhotoSizes(){}
	
	public static PreDefinedPhotoSizes getInstance(){
		
		if(null == INSTANCE){
			INSTANCE = new PreDefinedPhotoSizes();
			
			PhotoSize size0 = new PhotoSize(0, 0, "Custom Size", -1);
			PhotoSize size1 = new PhotoSize(50.8, 50.8, "", 1);
			PhotoSize size2 = new PhotoSize(25, 35, "", 2);
			PhotoSize size3 = new PhotoSize(35, 49, "", 3);
			PhotoSize size4 = new PhotoSize(35, 45, "", 4);
			PhotoSize size5 = new PhotoSize(50, 70, "", 5);
			PhotoSize size6 = new PhotoSize(36, 47, "", 6);
			PhotoSize size7 = new PhotoSize(30, 40, "", 7);
			PhotoSize size8 = new PhotoSize(35, 40, "", 8);
			
			SYSTEM_PHOTO_SIZES.add(size0);
			SYSTEM_PHOTO_SIZES.add(size1);
			SYSTEM_PHOTO_SIZES.add(size2);
			SYSTEM_PHOTO_SIZES.add(size3);
			SYSTEM_PHOTO_SIZES.add(size4);
			SYSTEM_PHOTO_SIZES.add(size5);
			SYSTEM_PHOTO_SIZES.add(size6);
			SYSTEM_PHOTO_SIZES.add(size7);
			SYSTEM_PHOTO_SIZES.add(size8);
			
			PHOTO_SIZES.addAll(SYSTEM_PHOTO_SIZES);
			
		}
		
		
		
		return INSTANCE;
	}
	
	public List<PhotoSize> getPhotoSizes(){
		
		// process user added size
		
		loadUserDefinedPhotoSizes();
		
		Collections.sort(PHOTO_SIZES);
		
		return PHOTO_SIZES;
		
	}
	
	private void loadUserDefinedPhotoSizes(){
		// TODO
	}
	
	public PhotoSize getPhotoSize(int id){
		loadUserDefinedPhotoSizes();
		for(int i = 0;i<PHOTO_SIZES.size();i++){
			PhotoSize photoSize = PHOTO_SIZES.get(i);
			if(photoSize.getId() == id){
				return photoSize;
			}
		}
		
		return new PhotoSize();
	}
	
	
}
