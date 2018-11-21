package com.ezwel.htl.interfaces.commons.exception;


import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageCode;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 *  API RunTimeException
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIType
public class APIException extends RunTimeException {

	private static final long serialVersionUID = 8537976232349035843L;

	public static final String DEFAULT_MESSAGE;
	public static final Integer DEFAULT_EXCEPTION_CODE;
	
	static {
		DEFAULT_MESSAGE = MessageCode.getMessage(MessageCode.RESPONSE_CODE_9000);
		DEFAULT_EXCEPTION_CODE = MessageCode.RESPONSE_CODE_9000;
	}
	
    public APIException()
    {
        super(DEFAULT_MESSAGE);

        super.init(); 
        setResultCode(DEFAULT_EXCEPTION_CODE);
    }

    public APIException(Integer resultCode, String message)
    {
        super(message);

        super.init(); 
        setResultCode(resultCode);
    }
    
    public APIException(String message)
    {
        super(message);
        
        super.init(); 
        setResultCode(DEFAULT_EXCEPTION_CODE);
    }
    
    public APIException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
        
        super.init(); 
        setResultCode(DEFAULT_EXCEPTION_CODE);
    }

    public APIException(Integer resultCode, String message, Object... arguments)
    {
        super(formatMessage(message, arguments));
        
        super.init(); 
        setResultCode(resultCode);
    }
    
    public APIException(String message, Object... arguments)
    {
        super(formatMessage(message, arguments));
        
        super.init(); 
        setResultCode(DEFAULT_EXCEPTION_CODE);
    }

    public APIException(Integer resultCode, Throwable cause)
    {
        super(MessageCode.getMessage(resultCode), cause);
        
        super.init(); 
        setResultCode(DEFAULT_EXCEPTION_CODE);
    }
    
    public APIException(String message, Throwable cause)
    {
        super(message, cause);
        
        super.init(); 
        setResultCode(DEFAULT_EXCEPTION_CODE);
    }

    public APIException(String message, Object arguments[], Throwable cause)
    {
        super(formatMessage(message, arguments), cause);
   
        super.init(); 
        setResultCode(DEFAULT_EXCEPTION_CODE);
    }
    
    
    private static String formatMessage(String message, Object... arguments)
    {
        return APIUtil.formatMessage(APIUtil.NVL(message, DEFAULT_MESSAGE), arguments);
    }


}
