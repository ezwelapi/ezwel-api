package com.ezwel.htl.interfaces.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.interfaces.RequestNamespace;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
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
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadDataOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
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

	private OutsideService outsideService = (OutsideService) LApplicationContext.getBean(OutsideService.class);

	private OutsideIFService outsideIFService = (OutsideIFService) LApplicationContext.getBean(OutsideIFService.class);
	
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
	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/allReg")
	public Object callAllReg(UserAgentSDO userAgentSDO) {

		if (OutsideService.isCallAllRegRunning()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9700, "전체시설일괄등록 인터페이스가 이미 실행중입니다.");
		}

		AllRegOutSDO out = outsideService.callAllReg(userAgentSDO);

		return out;
	}

	@APIOperation(description = "전체시설 이미지 다운로드 인터페이스", isOutputJsonMarshall = true, returnType = AllRegFaclImgOutSDO.class)
	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/allReg/imageDownload")
	public Object callAllRegImageDownload() {

		/** 데이터 저장이 모두 끝난후 제휴사 별 별도 멀티쓰레드 이미지 다운로드 실행 */
		AllRegFaclImgOutSDO out = outsideService.downloadMultiImage();

		return out;
	}

	@APIOperation(description = "시설 매핑", isOutputJsonMarshall = true, returnType = FaclSDO.class)
	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/execFaclMapping")
	public Object execFaclMapping(FaclSDO faclSDO) {

		if (OutsideService.isFaclMappingRunning()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9700, "시설 매핑 프로세스가 이미 실행중입니다.");
		}

		TransactionOutSDO out = outsideService.execFaclMapping(faclSDO);

		return out;
	}

	@APIOperation(description = "시설검색(최저가 정보) 인터페이스", isOutputJsonMarshall = true, returnType = FaclSearchOutSDO.class)
	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callFaclSearch")
	public Object callFaclSearch(UserAgentSDO userAgentSDO, FaclSearchInSDO faclSearchSDO) {

		FaclSearchOutSDO out = outsideService.callFaclSearch(userAgentSDO, faclSearchSDO);

		return out;
	}

	@APIOperation(description = "당일특가검색 인터페이스", isOutputJsonMarshall = true, returnType = SddSearchOutSDO.class)
	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callSddSearch")
	public Object callSddSearch(UserAgentSDO userAgentSDO) {

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
	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callRoomRead")
	@APIOperation(description = "객실정보단건 및 시설 별 최저가조회 인터페이스", isOutputJsonMarshall = true, returnType = RoomReadOutSDO.class)
	public Object callRoomRead(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		logger.debug("[START] callRoomRead {} {}", userAgentSDO, roomReadSDO);
		
		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		
		List<EzcFacl> faclList = null;
		List<RoomReadOutSDO> roomReadList = null;
		RoomReadOutSDO out = null;
		//최저가 객실 정보
		RoomReadDataOutSDO minAmtRoom = null;
		RoomReadOutSDO minAmtRoomOut = null;
		
		//그룹시설코드가 존재할경우
		if(roomReadSDO.getGrpFaclCd() != null) {
			/** 그릅코드 이용 */ 

			out = new RoomReadOutSDO();
			EzcFacl inEzcFacl = new EzcFacl();
			inEzcFacl.setGrpFaclCd(roomReadSDO.getGrpFaclCd());
			faclList = outsideService.selectRoomReadFaclList(inEzcFacl);
			
			
			//그룹시설코드에 대한 목록이 있으면...
			if(faclList != null && faclList.size() > 0) {
				
				// 지지고 복고 
				roomReadList = new ArrayList<RoomReadOutSDO>();
				for(EzcFacl faclitem : faclList) {
					//제휴사 상품 코드
					roomReadSDO.setPdtNo(faclitem.getPartnerGoodsCd());
					//제휴사 코드 (에이젼트ID)
					userAgentSDO.setHttpAgentId(faclitem.getPartnerCd());
					
					//1개 상품에 대한 객실 목록
					minAmtRoomOut = (RoomReadOutSDO) callRoomReadInterface(userAgentSDO, roomReadSDO);
					minAmtRoomOut.setPartnerCd(faclitem.getPartnerCd());
					roomReadList.add(minAmtRoomOut);
				}
				
				logger.debug("# roomReadList size : {}", roomReadList.size());
				
				//각 제휴사 상품 객실 목록에서 최저가를 뽑는다.
				for(RoomReadOutSDO item : roomReadList) {
					
					logger.debug("- RoomReadOutSDO : {}", item);
					
					//최저가 정보를 담을 객체
					minAmtRoom = null;
					//인터페이스 정상 성공인경우
					if(item.getCode().equals(Integer.toString(MessageConstants.RESPONSE_CODE_1000)) && item.getData() != null) {
						
						for(RoomReadDataOutSDO roomItem : item.getData()) {
							//최저가 정보를 담는다.
							//minAmtRoom.set ...
							if(minAmtRoom == null) {
								minAmtRoom = roomItem;
								minAmtRoom.setPartnerCd(item.getPartnerCd());
							}
							else if(roomItem.getPriceForSale() < minAmtRoom.getPriceForSale()) {
								minAmtRoom = roomItem;
								minAmtRoom.setPartnerCd(item.getPartnerCd());
							}
						}
					}
					
					out.setCode(new StringBuffer().append(APIUtil.NVL(out.getCode())).append(OperateConstants.STR_TAB).append(item.getCode()).toString().trim());
					out.setMessage(new StringBuffer().append(APIUtil.NVL(out.getMessage())).append(OperateConstants.STR_TAB).append(item.getMessage()).toString().trim());
					out.setRestURI(new StringBuffer().append(APIUtil.NVL(out.getRestURI())).append(OperateConstants.STR_TAB).append(item.getRestURI()).toString().trim());

					if(minAmtRoom != null) {
						out.addData(minAmtRoom);
					}
				}
			}
		}
		else {
			out = (RoomReadOutSDO) callRoomReadInterface(userAgentSDO, roomReadSDO);
		}
		
		return out;
	}

	@APIOperation(description = "객실정보조회 인터페이스", isOutputJsonMarshall = true, returnType = RoomReadOutSDO.class)
	private Object callRoomReadInterface(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		logger.debug("[START] callRoomRead {} {}", userAgentSDO, roomReadSDO);
		
		RoomReadOutSDO out = outsideIFService.callRoomRead(userAgentSDO, roomReadSDO);
		
		return out;
	}

	
	
	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callCancelFeePsrc")
	@APIOperation(description = "취소수수규정 인터페이스", isOutputJsonMarshall = true, returnType = CancelFeePsrcOutSDO.class)
	public Object callCancelFeePsrc(UserAgentSDO userAgentSDO, CancelFeePsrcInSDO cancelFeePsrcSDO) {

		CancelFeePsrcOutSDO out = outsideIFService.callCancelFeePsrc(userAgentSDO, cancelFeePsrcSDO);
		
		return out;
	}

	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callRsvHistSend")
	@APIOperation(description = "결재완료내역전송 인터페이스", isOutputJsonMarshall = true, returnType = RsvHistSendOutSDO.class)
	public Object callRsvHistSend(UserAgentSDO userAgentSDO, RsvHistSendInSDO rsvHistSendSDO) {

		RsvHistSendOutSDO out = outsideIFService.callRsvHistSend(userAgentSDO, rsvHistSendSDO);

		return out;
	}

	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callCancelFeeAmt")
	@APIOperation(description = "취소수수료계산 인터페이스", isOutputJsonMarshall = true, returnType = CancelFeeAmtOutSDO.class)
	public Object callCancelFeeAmt(UserAgentSDO userAgentSDO, CancelFeeAmtInSDO cancelFeeAmtSDO) {

		CancelFeeAmtOutSDO out = outsideIFService.callCancelFeeAmt(userAgentSDO, cancelFeeAmtSDO);

		return out;
	}

	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callOrderCancelReq")
	@APIOperation(description = "주문취소요청 인터페이스", isOutputJsonMarshall = true, returnType = OrderCancelReqOutSDO.class)
	public Object callOrderCancelReq(UserAgentSDO userAgentSDO, OrderCancelReqInSDO orderCancelReqSDO) {

		OrderCancelReqOutSDO out = outsideIFService.callOrderCancelReq(userAgentSDO, orderCancelReqSDO);

		return out;
	}

	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callOmiNumIdn")
	@APIOperation(description = "누락건확인 인터페이스", isOutputJsonMarshall = true, returnType = OmiNumIdnOutSDO.class)
	public Object callOmiNumIdn(UserAgentSDO userAgentSDO, OmiNumIdnInSDO omiNumIdnSDO) {

		OmiNumIdnOutSDO out = outsideIFService.callOmiNumIdn(userAgentSDO, omiNumIdnSDO);
		
		return out;
	}

	@RequestMapping(value = RequestNamespace.NAME_SPACE + "/callEzwelJob")
	@APIOperation(description = "주문대사(이지웰) 인터페이스", isOutputJsonMarshall = true, returnType = EzwelJobOutSDO.class)
	public Object callEzwelJob(UserAgentSDO userAgentSDO, EzwelJobInSDO ezwelJobSDO) {
		
		EzwelJobOutSDO out = outsideIFService.callEzwelJob(userAgentSDO, ezwelJobSDO);
		
		return out;
	}

}
