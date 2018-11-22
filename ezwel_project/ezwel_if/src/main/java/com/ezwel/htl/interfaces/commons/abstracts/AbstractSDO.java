package com.ezwel.htl.interfaces.commons.abstracts;

import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 *  AbstractSDO for *SDO
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Data
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="API SDO Super Class")
public abstract class AbstractSDO extends APIObject {

	
}
