package kingswood.idphoto.util;

import kingswood.idphoto.Runtime;

public class DimensionConvertor {
	
	public static double convertCMToDpi(double cmValue){
		return Runtime.X_DPI * cmValue / 2.54;
	}
	
}
