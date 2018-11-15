package com.ezwel.htl.interfaces.commons.abstracts;

import java.io.Serializable;

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
public abstract class AbstractEntity extends APIObject implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8331021898097280487L;
	
}
