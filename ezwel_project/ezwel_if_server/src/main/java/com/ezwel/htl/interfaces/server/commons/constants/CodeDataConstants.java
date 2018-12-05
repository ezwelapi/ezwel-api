package com.ezwel.htl.interfaces.server.commons.constants;

public class CodeDataConstants {
	
	//시설 구분 : API
	public final static String CD_API_G0010001;
	//시설 이미지 유형 : 시설이미지
	public final static String CD_FACL_IMG_TYPE_G0080001;
	//사용 여부 : Y
	public final static String CD_Y;
	//사용 여부 : N
	public final static String CD_N;
	//확정 상태 : 확정
	public final static String CD_CONFIRM_STATUS_G0060003;
	//시설 상태 : 완료
	public final static String CD_FACL_STATUS_G0040003;
	
	static { 
		CD_API_G0010001 = "G0010001";
		CD_FACL_IMG_TYPE_G0080001 = "G0080001";
		CD_Y = "Y";
		CD_N = "N";
		CD_CONFIRM_STATUS_G0060003 = "G0060003"; 
		CD_FACL_STATUS_G0040003 = "G0040003";
	}
}
