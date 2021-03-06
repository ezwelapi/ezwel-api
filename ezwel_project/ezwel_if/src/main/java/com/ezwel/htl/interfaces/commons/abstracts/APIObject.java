package com.ezwel.htl.interfaces.commons.abstracts;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
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
@EqualsAndHashCode(callSuper=false)
@APIModel(modelNames="API Model Super Class")
public abstract class APIObject implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -5668980456639902223L;
	
	@APIFields(description = "isMultiThread")
	private boolean isMultiThread = false;
	
	@APIFields(description = "GUID")
	private String guid;
	
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
			
			List<Field> fields = new ArrayList<Field>();
			if(this.getClass().getFields() != null) {
				fields.addAll(Arrays.asList(this.getClass().getFields()));
			}
			if(this.getClass().getDeclaredFields() != null) {
				fields.addAll(Arrays.asList(this.getClass().getDeclaredFields()));
			}

			Class<?> fieldType = null;
			Object fieldValue = null;
			for(Field item : fields) {
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
	
	public boolean isMultiThread() {
		return isMultiThread;
	}

	public void setMultiThread(boolean isMultiThread) {
		this.isMultiThread = isMultiThread;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
}
