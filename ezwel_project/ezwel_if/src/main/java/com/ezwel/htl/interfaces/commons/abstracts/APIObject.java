package com.ezwel.htl.interfaces.commons.abstracts;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 *  APIObject for APIModel
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Data
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="API Model Super Class")
public abstract class APIObject implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -5668980456639902223L;
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		scanObject(buffer, 1);
		return buffer.toString();
	}
	
	private void scanObject(StringBuffer buffer, int level) {
		
		try {
			if( buffer.length() == 0 ) {
				buffer.append(OperateConstants.LINE_SEPARATOR);
				buffer.append(this.getClass().getCanonicalName());
				buffer.append(OperateConstants.LINE_SEPARATOR);
			}
			
			//tab
			for(int i = 0; i < ((level - 1) * 1);i++) {
				buffer.append(OperateConstants.STR_TAB);
			}
			buffer.append(OperateConstants.STR_MID_BRACKET_R);
			buffer.append(OperateConstants.LINE_SEPARATOR);
			
			Field[] field = this.getClass().getDeclaredFields();
			Class<?> fieldType = null;
			Object fieldValue = null;
			for(Field item : field) {
				item.setAccessible(true);
				fieldType = item.getType();
				fieldValue = item.get(this);
				
				//tab
				for(int i = 0; i < (level * 1);i++) {
					buffer.append(OperateConstants.STR_TAB);
				}
				
				buffer.append(fieldType.getSimpleName());
				buffer.append(OperateConstants.STR_WHITE_SPACE);
				buffer.append(item.getName());
				buffer.append(OperateConstants.STR_WHITE_SPACE);
				buffer.append(OperateConstants.STR_COLON);		
				buffer.append(OperateConstants.STR_WHITE_SPACE);
				buffer.append(fieldValue);
				buffer.append(OperateConstants.LINE_SEPARATOR);
			}
			
			//tab
			for(int i = 0; i < ((level - 1) * 1);i++) {
				buffer.append(OperateConstants.STR_TAB);
			}		
			buffer.append(OperateConstants.STR_MID_BRACKET_L);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}	
}
