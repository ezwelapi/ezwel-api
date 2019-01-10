package com.ezwel.htl.interfaces.commons.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobReservesOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendDataInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInSDO;

/**
 * <pre>
 * API Opertation에서 사용하는 유틸 클래스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */

@Component
@APIType(description="IF API CODE 변환 유틸")
public class CodeUtil {

	private static final Logger logger = LoggerFactory.getLogger(CodeUtil.class);
	
	@APIOperation(description="결재완료내역전송 예약상태코드 변환", isExecTest=true)
	public static RsvHistSendInSDO getEzcRsvHistSendInputCode(RsvHistSendInSDO rsvHistSendInSDO) {
		
		//이지웰를 전문코드코드로 변환
		RsvHistSendDataInSDO data = rsvHistSendInSDO.getData();
		
		String rsvStat = data.getRsvStat();		
		logger.debug("YPJEON getRsvStat 1 : {}", rsvStat);
		
		String ezcRsvStat = getInputRsvStatCode(rsvStat);		
		logger.debug("YPJEON getRsvStat 2 : {}", ezcRsvStat);
		
		data.setRsvStat(ezcRsvStat);
		rsvHistSendInSDO.setData(data);
		
		return rsvHistSendInSDO;
	}
	
	@APIOperation(description="예약상태코드 변환", isExecTest=true)
	public static String getInputRsvStatCode(String telgCode) {
		
		String rsvStat = "";
		
		switch(telgCode) {
        	case "R001RW" : rsvStat = "r01"; //예약대기 (전문코드,이지웰코드)
        		break;
        	case "R001OK" : rsvStat = "r02"; //예약완료
        		break;
        	case "R001XW" : rsvStat = "r03"; //취소대기
        		break;
        	case "R001XX" : rsvStat = "r04"; //취소완료
        		break;
		}
		
		return rsvStat;
	}
	
	@APIOperation(description="주문대사(이지웰) 예약상태코드 변환", isExecTest=true)
	public static EzwelJobOutSDO getEzwelJobRsvStat(EzwelJobOutSDO rsvStat) {
		
		//전문코드를 이지웰코드로 변환
		List<EzwelJobReservesOutSDO> dataList = rsvStat.getReserves();
		//코드 데이터 만큼 아래의 set/getOutputRsvStatCode(전문코드) 를 실행한다.
		for(EzwelJobReservesOutSDO data:dataList) {
			data.setRsvStatName(getOutputRsvStatName(data.getRsvStat()));
			data.setRsvStat(getOutputRsvStatCode(data.getRsvStat()));
		}
		rsvStat.setReserves(dataList);
		
		return rsvStat;
	}
	
	@APIOperation(description="예약상태코드 변환", isExecTest=true)
	public static String getOutputRsvStatCode(String telgCode) {
		
		String rsvStat = "";
		
		switch(telgCode) {
        	case "r01" : rsvStat = "R001RW"; //예약대기 (전문코드,이지웰코드)
        		break;
        	case "r02" : rsvStat = "R001OK"; //예약완료
        		break;
        	case "r03" : rsvStat = "R001XW"; //취소대기
        		break;
        	case "r04" : rsvStat = "R001XX"; //취소완료
        		break;
		}
		
		return rsvStat;
	}
	
	@APIOperation(description="예약상태명 변환", isExecTest=true)
	public static String getOutputRsvStatName(String telgCode) {
		
		String rsvStatName = "";
		
		switch(telgCode) {
        	case "r01" : rsvStatName = "예약대기"; //예약대기 (전문코드,이지웰코드)
        	case "r02" : rsvStatName = "예약완료"; //예약완료
        	case "r03" : rsvStatName = "취소대기"; //취소대기
        	case "r04" : rsvStatName = "취소완료"; //취소완료
		}
		
		return rsvStatName;
	}
    
}
