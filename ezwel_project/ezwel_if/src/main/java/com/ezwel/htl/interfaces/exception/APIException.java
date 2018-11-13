package com.ezwel.htl.interfaces.exception;


import com.ezwel.htl.interfaces.annotation.APIService;
import com.ezwel.htl.interfaces.constants.IOperateCode;
import com.ezwel.htl.interfaces.utils.APIUtil;

/**
 * <pre>
 *  API RunTimeException
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIService
public class APIException extends RunTimeException {

	private static final long serialVersionUID = 8537976232349035843L;

	public static final String DEFAULT_MESSAGE = "어플리케이션에서 오류가 발생하였습니다.";
    
    public APIException()
    {
        super(DEFAULT_MESSAGE);

        super.init(); 
        setResultCode(IOperateCode.API_RESULT_CODE_ERROR);
    }

    public APIException(String message, int resultCode)
    {
        super(message);

        super.init(); 
        setResultCode(resultCode);
    }
    
    public APIException(String message)
    {
        super(message);
        
        super.init(); 
        setResultCode(IOperateCode.API_RESULT_CODE_ERROR);
    }
    
    public APIException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
        
        super.init(); 
        setResultCode(IOperateCode.API_RESULT_CODE_ERROR);
    }

    public APIException(String message, Object arguments[], int resultCode)
    {
        super(formatMessage(message, arguments));
        
        super.init(); 
        setResultCode(resultCode);
    }
    
    public APIException(String message, Object... arguments)
    {
        super(formatMessage(message, arguments));
        
        super.init(); 
        setResultCode(IOperateCode.API_RESULT_CODE_ERROR);
    }

    public APIException(String message, Throwable cause)
    {
        super(message, cause);
        
        super.init(); 
        setResultCode(IOperateCode.API_RESULT_CODE_ERROR);
    }

    public APIException(String message, Object arguments[], Throwable cause)
    {
        super(formatMessage(message, arguments), cause);
   
        super.init(); 
        setResultCode(IOperateCode.API_RESULT_CODE_ERROR);
    }
    
    
    private static String formatMessage(String message, Object... arguments)
    {
        return APIUtil.formatMessage(APIUtil.NVL(message, DEFAULT_MESSAGE), arguments);
    }


}
