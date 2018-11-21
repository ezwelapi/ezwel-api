package com.ezwel.htl.interfaces.commons.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateCode;
import com.ezwel.htl.interfaces.commons.entity.CommonHeader;
import com.ezwel.htl.interfaces.commons.entity.RuntimeHeader;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;

/**
 * <pre>
 * ThreadLocal 초기화, 삭제 및 공통헤더 관리 클래스 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIType
public class Local {

	private static final Logger logger = LoggerFactory.getLogger(Local.class);

	/**
	 * Thread local variable containing each thread's CommonHeader
	 */
    private static final ThreadLocal<CommonHeader> threadLocalHeader = new ThreadLocal<CommonHeader>() {
            @Override
            @APIOperation(description="ThreadLocal의 초기값을 설정하여 리턴합니다.", isExecTest=true)
            protected CommonHeader initialValue() {

            	CommonHeader header = new CommonHeader();
            	header.setGuid(APIUtil.getId());
            	header.setStartTimeMillis(APIUtil.currentTimeMillis());

            	if(logger.isDebugEnabled()) {
	            	logger.debug(new StringBuilder()
	            		.append(OperateCode.LINE_SEPARATOR)
	            		.append(" ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ")
	            		.append(OperateCode.LINE_SEPARATOR)
	            		.append(" ■■ [START]    ")
	            		.append(OperateCode.LINE_SEPARATOR)
	            		.append(" ■■ initialValue    ")
	            		.append(OperateCode.LINE_SEPARATOR)
	            		.append(" ■■ guid : " + header.getGuid() )
	            		.append(OperateCode.LINE_SEPARATOR)
	            		.append(" ■■ startTimeMillis : " + header.getStartTimeMillis() )
	            		.append(OperateCode.LINE_SEPARATOR)
	            		.append(" ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ")
	            		.append(OperateCode.LINE_SEPARATOR)
	            		.toString()
	            	);
            	}

                return header;
        }
    };

    /**
     * Local.commonHeader()
     * @return
     */
    @APIOperation(description="ThreadLocal을 초기화 하고 CommonHeader를 리턴합니다.", isExecTest=true)
    public static CommonHeader commonHeader() {
        return threadLocalHeader.get();
    }

    /**
     * @Name : getId
     * @Description : Returns the current thread's unique ID, assigning it if necessary
     * @LastUpdated : 2014. 9. 13. 오후 7:06:14
     * @return
     */
    @APIOperation(description="ThreadLocal의 GUID를 리턴합니다.", isExecTest=true)
    public static String getId() {
        return threadLocalHeader.get().getGuid();
    }

    /**
     * @Name : setId
     * @Description : ThreadLocal threadLocal id set
     * @LastUpdated : 2014. 9. 13. 오후 7:06:21
     * @param id
     */
    @APIOperation(description="ThreadLocal의 GUID를 세팅합니다.", isExecTest=true)
    public static void setId(String id) {
    	threadLocalHeader.get().setGuid(id);
    }

	@APIOperation(description="caller의 고유 아이디를생성하고 타입@메소드 정보와 실행시간(timeMillis)를 로컬오브젝트에 세팅후 고유 아이디를 리턴합니다.", isExecTest=true)
	public static String startOperation() {
		String localKey = getLocalKey();
		StackTraceElement beforeStack = StackTraceUtil.getCurrentStack(Local.class);
		CommonHeader common = Local.commonHeader();
		RuntimeHeader runtime = new RuntimeHeader();
		runtime.setStartTimeMillis(APIUtil.currentTimeMillis());
		runtime.setClassName(beforeStack.getClassName());
		runtime.setMethodName(beforeStack.getMethodName());
		common.addRuntimeHeader(localKey, runtime);
		return localKey;
	}
	
	@APIOperation(description="바인드된 고유아디에 해당하는 로컬변수의 객체를 조회하고 종료 시간을 세팅합니다.", isExecTest=true)
	public static RuntimeHeader endOperation(String localKey) {
		RuntimeHeader runtime = Local.commonHeader().getRuntimeHeader(localKey);
		if(runtime == null) {
			throw new APIException("로컬키 '{}'에 해당하는 RuntimeHeader가 초기화되지 않았습니다. 메소드 실행시점에 APIUtil.startTimeMillis()를 실행하세요.", localKey);
		}
		runtime.setEndTimeMillis();
		return runtime;
	}
	

	
    /**
     * 쓰레드 내 메소드를 call한 타입@메소드의 고유 키 발급
     * @param str
     * @return
     */
	@APIOperation(description="쓰레드 내 메소드를 call한 타입@메소드의 고유 키 발급(ThreadName + caller type + caller method + RandomUUID)", isExecTest=true)
    public static String getLocalKey() {
    	StackTraceElement beforeStack = StackTraceUtil.getCurrentStack(Local.class);
    	StringBuffer out = new StringBuffer();
    	String threadName = Thread.currentThread().getName();
    	
    	out.append(threadName);
    	out.append(OperateCode.STR_HYPHEN);
		out.append(beforeStack.getClassName());
		out.append(OperateCode.STR_AT);
		out.append(beforeStack.getMethodName());
    	out.append(OperateCode.STR_HYPHEN);
    	out.append(APIUtil.getRandomUUID());
    	
    	return out.toString();
    }
	
    /**
     * @Name : remove
     * @Description : ThreadLocal threadLocal data remove
     * @LastUpdated : 2014. 9. 13. 오후 7:06:50
     */
	@APIOperation(description="Thread 소멸시점에 최종 AP 실행시간을 로깅하고 ThreadLocal의 내용을 삭제합니다.", isExecTest=true)
    public static void remove() {

    	if(logger.isDebugEnabled()) {
	    	logger.debug(new StringBuilder()
	    		.append(OperateCode.LINE_SEPARATOR)
	    		.append(" ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ")
	    		.append(OperateCode.LINE_SEPARATOR)
	            .append(" ■■ [END]    ")
	    		.append(OperateCode.LINE_SEPARATOR)
        		.append(" ■■ guid : " + commonHeader().getGuid() )
        		.append(OperateCode.LINE_SEPARATOR)
        		.append(" ■■ RunTime lapTimeMillis : ")
				.append(commonHeader().getLapTimeMillis())
				.append("(ms)")
	    		.append(OperateCode.LINE_SEPARATOR)
				.append(" ■■ StartTimeMillis : ")
				.append(commonHeader().getStartTimeMillis())
	    		.append(OperateCode.LINE_SEPARATOR)
				.append(" ■■ EndTimeMillis : ")
				.append(commonHeader().getEndTimeMillis())
	    		.append(OperateCode.LINE_SEPARATOR)
	    		.append(" ■■ ThreadLocal END ")
	    		.append(OperateCode.LINE_SEPARATOR)
	    		.append(" ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ")
	    		.append(OperateCode.LINE_SEPARATOR)
	    		.toString()
			);
    	}

    	threadLocalHeader.remove();
    }

}
