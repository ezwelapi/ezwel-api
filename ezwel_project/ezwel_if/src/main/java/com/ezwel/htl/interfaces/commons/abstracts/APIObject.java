package com.ezwel.htl.interfaces.commons.abstracts;

import java.lang.reflect.Field;

import com.ezwel.htl.interfaces.commons.annotation.APIService;
import com.ezwel.htl.interfaces.commons.constants.OperateCode;

/**
 * <pre>
 *  APIObject for APIModel
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIService
public abstract class APIObject {
	
	private StringBuffer buffer;
	
	public String toString() {
		
		if( buffer == null ) {
			buffer = new StringBuffer();
		}
		scanObject(buffer, 1);
		return buffer.toString();
	}
	
	private void scanObject(StringBuffer buffer, int level) {
		
		try {
			if( buffer.length() == 0 ) {
				buffer.append(OperateCode.LINE_SEPARATOR);
				buffer.append(this.getClass().getCanonicalName());
				buffer.append(OperateCode.LINE_SEPARATOR);
			}
			
			//tab
			for(int i = 0; i < ((level - 1) * 1);i++) {
				buffer.append(OperateCode.STR_TAB);
			}
			buffer.append(OperateCode.STR_MID_BRACKET_R);
			buffer.append(OperateCode.LINE_SEPARATOR);
			
			Field[] field = this.getClass().getDeclaredFields();
			Class<?> fieldType = null;
			Object fieldValue = null;
			for(Field item : field) {
				item.setAccessible(true);
				fieldType = item.getType();
				fieldValue = item.get(this);
				
				//tab
				for(int i = 0; i < (level * 1);i++) {
					buffer.append(OperateCode.STR_TAB);
				}
				
				buffer.append(fieldType.getSimpleName());
				buffer.append(OperateCode.STR_WHITE_SPACE);
				buffer.append(item.getName());
				buffer.append(OperateCode.STR_WHITE_SPACE);
				buffer.append(OperateCode.STR_COLON);		
				buffer.append(OperateCode.STR_WHITE_SPACE);
				buffer.append(fieldValue);
				buffer.append(OperateCode.LINE_SEPARATOR);
			}
			
			//tab
			for(int i = 0; i < ((level - 1) * 1);i++) {
				buffer.append(OperateCode.STR_TAB);
			}		
			buffer.append(OperateCode.STR_MID_BRACKET_L);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}	
}
