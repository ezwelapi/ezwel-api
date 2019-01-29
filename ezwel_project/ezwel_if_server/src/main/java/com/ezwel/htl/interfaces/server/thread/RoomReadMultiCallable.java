package com.ezwel.htl.interfaces.server.thread;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractComponent;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;


@APIType(description="객실 정보 조회 멀티쓰레드 서비스")
public class RoomReadMultiCallable extends AbstractComponent implements Callable<RoomReadOutSDO> {

	private static final Logger logger = LoggerFactory.getLogger(RoomReadMultiCallable.class);
	
	private static final boolean IS_LOGGING = false;
	
	private OutsideIFService outsideIFService;
	
	private UserAgentSDO userAgentSDO;
	
	private RoomReadInSDO roomReadSDO;
	
	private Integer count;
	
	/**
	 * Constructor
	 * @param inImageParam
	 * @param count
	 */
		//ThreadLocal 초기화
	public RoomReadMultiCallable(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO, Integer count) {
		Local.commonHeader();
		
		logger.debug("- RoomReadMultiService Initialized : {}", count);
		/** 필요 한 지역변수 세팅 */
		this.userAgentSDO = userAgentSDO;
		this.roomReadSDO = roomReadSDO;
		this.count = count;
	}
	
	/**
	 * 멀티쓰레드에서 수행할 작업 (call)
	 */
	@Override
	public RoomReadOutSDO call() {
		logger.debug("[RoomReadMultiCallable-START] {}", Thread.currentThread().getName());
		
		RoomReadOutSDO out = null;
		
		try { 
			if(IS_LOGGING) {
				logger.debug("[START-RoomReadMultiService({})] 객실 정보 조회 멀티 => userAgentSDO : {}, roomReadSDO : {}", count, userAgentSDO, roomReadSDO);
			}
			 
			outsideIFService = (OutsideIFService) LApplicationContext.getBean(outsideIFService, OutsideIFService.class);
			
			userAgentSDO.setMultiThread(true);
			out = outsideIFService.callRoomRead(userAgentSDO, roomReadSDO);
			//제휴사 코드 세팅
			out.setPartnerCd(userAgentSDO.getHttpAgentId());
			out.setGrpFaclCd(roomReadSDO.getGrpFaclCd());
			out.setFaclCd(roomReadSDO.getFaclCd());
			
			if(IS_LOGGING) {
				logger.debug("[END-RoomReadMultiService({})] 객실 정보 조회 멀티 => RoomReadOutSDO : {}", count, out);
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, APIUtil.NVL(Local.commonHeader().getMessage(), "객실 정보 조회 멀티쓰레드 내부 장애 발생"), APIUtil.ONVL(Local.commonHeader().getThrowable(), e));
		}
		finally {
			
			//ThreadLocal 종료
			Local.remove();
		}
		
		return out;
	}
	

}
