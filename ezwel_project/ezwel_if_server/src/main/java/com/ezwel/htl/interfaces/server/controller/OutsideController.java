package com.ezwel.htl.interfaces.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.interfaces.RequestNamespace;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CryptoUtil;
import com.ezwel.htl.interfaces.server.sdo.FaclSDO;
import com.ezwel.htl.interfaces.server.sdo.TransactionOutSDO;
import com.ezwel.htl.interfaces.server.service.OutsideService;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegFaclImgOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnInSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqInSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadSeachMinInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;

/**
 * <pre>
 *  http://localhost:8282/ezwel_if_server/API1.0/inside-03/facl/record
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 15.
 */
@Controller
@RequestMapping(value = RequestNamespace.NAME_SPACE)
@APIType(description = "Outside Caller Interface Controller")
public class OutsideController {

	private static final Logger logger = LoggerFactory.getLogger(OutsideController.class);

	private OutsideService outsideService;

	private OutsideIFService outsideIFService;

	private CryptoUtil cryptoUtil;
	/**************************************
	 * [START] ezwel_if_server API
	 **************************************/

	/**
	 * <pre>
	 * [메서드 설명]
	 * URL : /API1.0/test/facl/record
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * 
	 * @param httpAgentId
	 * @param in
	 * @param request
	 * @param response
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since 2018. 11. 21.
	 */
	@APIOperation(description = "전체시설일괄등록 인터페이스", isOutputJsonMarshall = true, returnType = AllRegOutSDO.class)
	@RequestMapping(value = "/allReg")
	public Object callAllReg(UserAgentSDO userAgentSDO) {

		if (OutsideService.isCallAllRegRunning()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9700, "전체시설일괄등록 인터페이스가 이미 실행중입니다.");
		}
		
		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		AllRegOutSDO out = outsideService.callAllReg(userAgentSDO);

		return out;
	}

	@APIOperation(description = "전체시설 이미지 다운로드 인터페이스", isOutputJsonMarshall = true, returnType = AllRegFaclImgOutSDO.class)
	@RequestMapping(value = "/allReg/imageDownload")
	public Object callAllRegImageDownload() {

		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		/** 데이터 저장이 모두 끝난후 제휴사 별 별도 멀티쓰레드 이미지 다운로드 실행 */
		AllRegFaclImgOutSDO out = outsideService.downloadMultiImage();

		return out;
	}

	@APIOperation(description = "시설 매핑", isOutputJsonMarshall = true, returnType = FaclSDO.class)
	@RequestMapping(value = "/execFaclMapping")
	public Object execFaclMapping(FaclSDO faclSDO) {

		if (OutsideService.isFaclMappingRunning()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9700, "시설 매핑 프로세스가 이미 실행중입니다.");
		}

		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		TransactionOutSDO out = outsideService.execFaclMapping(faclSDO);

		return out;
	}

	@APIOperation(description = "시설검색(최저가 정보) 인터페이스", isOutputJsonMarshall = true, returnType = FaclSearchOutSDO.class)
	@RequestMapping(value = "/callFaclSearch")
	public Object callFaclSearch(UserAgentSDO userAgentSDO, FaclSearchInSDO faclSearchSDO) {

		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		FaclSearchOutSDO out = outsideService.callFaclSearch(userAgentSDO, faclSearchSDO);

		return out;
	}

	@APIOperation(description = "당일특가검색 인터페이스", isOutputJsonMarshall = true, returnType = SddSearchOutSDO.class)
	@RequestMapping(value = "/callSddSearch")
	public Object callSddSearch(UserAgentSDO userAgentSDO) {

		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		SddSearchOutSDO out = outsideService.callSddSearch(userAgentSDO);

		return out;
	}

	/**************************************
	 * [START] ezwel_if API
	 **************************************/

	/**
	 * 객실정보단건 및 시설 별 최저가목록조회 인터페이스
	 * roomReadSDO 입력 시그니처에 grpFaclCd가 있으면  시설 별 최저가을 조회 하고
	 * 없으면 상품코드에 대한 객실 정보를 조회한다.
	 * @param userAgentSDO
	 * @param roomReadSDO
	 * @return
	 */
	@RequestMapping(value = "/callRoomRead")
	@APIOperation(description = "객실정보단건 및 시설 별 최저가조회 인터페이스", isOutputJsonMarshall = true, returnType = RoomReadOutSDO.class)
	public Object callRoomRead(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		logger.debug("[START] callRoomRead {} {}", userAgentSDO, roomReadSDO);
		
		RoomReadOutSDO out = null;
		
		//그룹시설코드가 존재할경우
		if(roomReadSDO.getGrpFaclCd() != null || (roomReadSDO.getSeachMinRoomInList() != null && roomReadSDO.getSeachMinRoomInList().size() > 0)) {
			
			/** 그릅코드 이용 */
			outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
			
			//상품목록 유효성 검증
			if(roomReadSDO.getSeachMinRoomInList() != null && roomReadSDO.getSeachMinRoomInList().size() > 0) {
				
				List<String> confirmPartnerCds = new ArrayList<String>(); 
				for(RoomReadSeachMinInSDO searchParam : roomReadSDO.getSeachMinRoomInList()) {
					if(APIUtil.isEmpty(searchParam.getPdtNo())) {
						throw new APIException(MessageConstants.RESPONSE_CODE_2000, "상품코드는 필수 입력사항입니다.");
					}
					if(APIUtil.isEmpty(searchParam.getPartnerCd())) {
						throw new APIException(MessageConstants.RESPONSE_CODE_2000, "제휴사코드는 필수 입력사항입니다.");
					}
					if(confirmPartnerCds.contains(searchParam.getPartnerCd())) {
						throw new APIException(MessageConstants.RESPONSE_CODE_2000, "같은 제휴사코드로 중복 검색 할 수 없습니다. 제휴사코드 : {}", searchParam.getPartnerCd());
					}
					
					confirmPartnerCds.add(searchParam.getPartnerCd());
				}
			}
			
			out = outsideService.callRoomRead(userAgentSDO, roomReadSDO);
			out.setGrpFaclCd(roomReadSDO.getGrpFaclCd());
		}
		else {
			
			if(APIUtil.isEmpty(roomReadSDO.getPdtNo())) {
				throw new APIException(MessageConstants.RESPONSE_CODE_2000, "상품코드는 필수 입력사항입니다.");
			}
			
			//single thread
			outsideIFService = (OutsideIFService) LApplicationContext.getBean(outsideIFService, OutsideIFService.class);
			out = outsideIFService.callRoomRead(userAgentSDO, roomReadSDO);
		}
		
		return out;
	}
	

	@RequestMapping(value = "/callCancelFeePsrc")
	@APIOperation(description = "취소수수규정 인터페이스", isOutputJsonMarshall = true, returnType = CancelFeePsrcOutSDO.class)
	public Object callCancelFeePsrc(UserAgentSDO userAgentSDO, CancelFeePsrcInSDO cancelFeePsrcSDO) {

		outsideIFService = (OutsideIFService) LApplicationContext.getBean(outsideIFService, OutsideIFService.class);
		CancelFeePsrcOutSDO out = outsideIFService.callCancelFeePsrc(userAgentSDO, cancelFeePsrcSDO);
		
		return out;
	}

	@RequestMapping(value = "/callRsvHistSend")
	@APIOperation(description = "예약결재완료내역전송 인터페이스", isOutputJsonMarshall = true, returnType = RsvHistSendOutSDO.class)
	public Object callRsvHistSend(UserAgentSDO userAgentSDO, RsvHistSendInSDO rsvHistSendSDO) {

		outsideIFService = (OutsideIFService) LApplicationContext.getBean(outsideIFService, OutsideIFService.class);
		cryptoUtil = (CryptoUtil) LApplicationContext.getBean(cryptoUtil, CryptoUtil.class);
		
		/**********************************************************************
		 * 사용자 정보 인코딩 (인터페이스 서버 타는 경우에만 적용되는 부분)
		 * memKey, memName, memPhone, memEmail, userName, userMobile, userEmail
		 **********************************************************************/
		AgentInfoSDO agentInfo = InterfaceFactory.getInterfaceAgents(userAgentSDO.getHttpAgentId());
		
		if(rsvHistSendSDO.isEncPrvtInfo()) {
			cryptoUtil.encodeAPIModel(agentInfo.getCryptKey(), rsvHistSendSDO.getData());
			logger.debug("# rsvHistSendSDO.getData() : {}", rsvHistSendSDO.getData());
			//암호화 완료후 false
			rsvHistSendSDO.setEncPrvtInfo(false);
		}
		
		RsvHistSendOutSDO out = outsideIFService.callRsvHistSend(userAgentSDO, rsvHistSendSDO);

		return out;
	}

	@RequestMapping(value = "/callCancelFeeAmt")
	@APIOperation(description = "취소수수료계산 인터페이스", isOutputJsonMarshall = true, returnType = CancelFeeAmtOutSDO.class)
	public Object callCancelFeeAmt(UserAgentSDO userAgentSDO, CancelFeeAmtInSDO cancelFeeAmtSDO) {

		outsideIFService = (OutsideIFService) LApplicationContext.getBean(outsideIFService, OutsideIFService.class);
		CancelFeeAmtOutSDO out = outsideIFService.callCancelFeeAmt(userAgentSDO, cancelFeeAmtSDO);

		return out;
	}

	@RequestMapping(value = "/callOrderCancelReq")
	@APIOperation(description = "주문취소요청 인터페이스", isOutputJsonMarshall = true, returnType = OrderCancelReqOutSDO.class)
	public Object callOrderCancelReq(UserAgentSDO userAgentSDO, OrderCancelReqInSDO orderCancelReqSDO) {

		outsideIFService = (OutsideIFService) LApplicationContext.getBean(outsideIFService, OutsideIFService.class);
		OrderCancelReqOutSDO out = outsideIFService.callOrderCancelReq(userAgentSDO, orderCancelReqSDO);

		return out;
	}

	@RequestMapping(value = "/callOmiNumIdn")
	@APIOperation(description = "누락건확인 인터페이스", isOutputJsonMarshall = true, returnType = OmiNumIdnOutSDO.class)
	public Object callOmiNumIdn(UserAgentSDO userAgentSDO, OmiNumIdnInSDO omiNumIdnSDO) {

		outsideIFService = (OutsideIFService) LApplicationContext.getBean(outsideIFService, OutsideIFService.class);
		OmiNumIdnOutSDO out = outsideIFService.callOmiNumIdn(userAgentSDO, omiNumIdnSDO);
		
		return out;
	}

	@RequestMapping(value = "/callEzwelJob")
	@APIOperation(description = "주문대사(이지웰) 인터페이스", isOutputJsonMarshall = true, returnType = EzwelJobOutSDO.class)
	public Object callEzwelJob(UserAgentSDO userAgentSDO, EzwelJobInSDO ezwelJobSDO) {
		
		outsideIFService = (OutsideIFService) LApplicationContext.getBean(outsideIFService, OutsideIFService.class);
		EzwelJobOutSDO out = outsideIFService.callEzwelJob(userAgentSDO, ezwelJobSDO);
		
		return out;
	}

}
