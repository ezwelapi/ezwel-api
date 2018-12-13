package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.adapter.OutsideIFAdapter;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.server.service.OutsideService;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnInSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqInSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendDataInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class InterfaceDemoService {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceDemoService.class);
	
	private OutsideIFAdapter outIfService; // if
	private OutsideService outIfServerService;  // if_server
	
	public InterfaceDemoService() {
		outIfService = new OutsideIFAdapter();
		outIfServerService = new OutsideService();
	}
	
	public AllRegOutSDO callAllReg() {		
		logger.debug("[START] callAllReg");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		//User agent set
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//interface api call
		AllRegOutSDO out = outIfServerService.callAllReg(userAgentDTO);
		
		logger.debug("[END] callAllReg");
		return out;
	}
	
	// 객실정보조회
	public RoomReadOutSDO callRoomRead() {		
		logger.debug("[START] callRoomRead");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		RoomReadInSDO roomReadSDO = new RoomReadInSDO();
		
		roomReadSDO.setOtaId("10000496");
		roomReadSDO.setPdtNo("1");
		roomReadSDO.setCheckInDate("20190101");
		roomReadSDO.setCheckOutDate("20190102");
		roomReadSDO.setRoomCnt(1);
		roomReadSDO.setAdultCnt(1);
		roomReadSDO.setChildCnt(1);
		
		//interface api call
		RoomReadOutSDO out = outIfService.callRoomRead(userAgentDTO, roomReadSDO);
		
		logger.debug("[END] callRoomRead");
		return out;
	}
	
	// 취소수수료규정
	public CancelFeePsrcOutSDO callCancelFeePsrc() {
		logger.debug("[START] callCancelFeePsrc");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		CancelFeePsrcInSDO sdo = new CancelFeePsrcInSDO();
		
		sdo.setOtaId("10000496");
		sdo.setPdtNo("1");
		sdo.setCheckInDate("20190101");
		sdo.setCheckOutDate("20190102");
		sdo.setRoomNo("1");
		sdo.setRoomCnt(1);
		
		//interface api call
		CancelFeePsrcOutSDO out = outIfService.callCancelFeePsrc(userAgentDTO, sdo);
		
		logger.debug("[END] callCancelFeePsrc");
		return out;
	}
	
	// 결제완료내역전송
	public RsvHistSendOutSDO callRsvHistSend() {		
		logger.debug("[START] callRsvHistSend");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		RsvHistSendInSDO rsvHistSendInSDO = new RsvHistSendInSDO();
		
		RsvHistSendDataInSDO data = rsvHistSendInSDO.getData();
		
		data.setRsvNo("123456789");		
		data.setRsvDatetime("20181113152332");
		data.setRsvPrice(200000);
		data.setRsvStat("r02");
		data.setRsvPdtName("");
		data.setRsvPdtNo("");
		data.setPdtNo("1");
		data.setPdtName("서울 프라자 호텔");
		data.setRoomNo("1");
		data.setRoomName("디럭스");
		data.setRoomCnt(1);
		data.setCheckInDate("20181201");
		data.setCheckOutDate("20181202");
		data.setMemKey("EZ0001");
		data.setMemName("홍길동");
		data.setMemPhone("01012341234");
		data.setMemEmail("test@test.com");
		data.setUserName("홍길동");
		data.setUserMobile("01012341234");
		data.setUserEmail("test@test.com");
		data.setUserCmt("전망 좋은 방으로 요청 드립니다");
		data.setAdultCnt(2);
		data.setChildCnt(1);
		
		rsvHistSendInSDO.setData(data);
		
		//interface api call
		RsvHistSendOutSDO out = outIfService.callRsvHistSend(userAgentDTO, rsvHistSendInSDO);
		
		logger.debug("[END] callRsvHistSend");
		return out;
	}
	
	// 취소수수료계산
	public CancelFeeAmtOutSDO callCancelFeeAmt() {
		logger.debug("[START] callCancelFeeAmt");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		CancelFeeAmtInSDO sdo = new CancelFeeAmtInSDO();
		
		sdo.setOtaId("10000496");
		sdo.setRsvNo("12345678");
		
		//interface api call
		CancelFeeAmtOutSDO out = outIfService.callCancelFeeAmt(userAgentDTO, sdo);
		
		logger.debug("[END] callCancelFeeAmt");
		return out;
	}
	
	// 주문취소요청
	public OrderCancelReqOutSDO callOrderCancelReq() {		
		logger.debug("[START] callOrderCancelReq");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		OrderCancelReqInSDO orderCancelReqInSDO = new OrderCancelReqInSDO();
		
		orderCancelReqInSDO.setOtaId("10000496");
		orderCancelReqInSDO.setRsvNo("12345678");
		orderCancelReqInSDO.setOtaRsvNo("12345678");
		orderCancelReqInSDO.setRsvPrice(100000);
		orderCancelReqInSDO.setCancelCharge(0);
		
		//interface api call
		OrderCancelReqOutSDO out = outIfService.callOrderCancelReq(userAgentDTO, orderCancelReqInSDO);
		
		logger.debug("[END] callOrderCancelReq");
		return out;
	}
	
	// 누락건확인
	public OmiNumIdnOutSDO callOmiNumIdn() {		
		logger.debug("[START] callOmiNumIdn");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		OmiNumIdnInSDO omiNumIdnInSDO = new OmiNumIdnInSDO();
		
		omiNumIdnInSDO.setOtaId("10000496");
		omiNumIdnInSDO.setRsvNo("12345678");
		omiNumIdnInSDO.setRsvStat("Y");
		
		//interface api call
		OmiNumIdnOutSDO out = outIfService.callOmiNumIdn(userAgentDTO, omiNumIdnInSDO);
		
		logger.debug("[END] callOmiNumIdn");
		return out;
	}
	
	// 주문대사(이지웰)
	public EzwelJobOutSDO callEzwelJob() {		
		logger.debug("[START] callEzwelJob");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		EzwelJobInSDO ezwelJobInSDO = new EzwelJobInSDO();
		
		ezwelJobInSDO.setOtaId("10000496");
		ezwelJobInSDO.setRsvDateStart("20181201");
		ezwelJobInSDO.setRsvDateEnd("20181211");
		
		//interface api call
		EzwelJobOutSDO out = outIfService.callEzwelJob(userAgentDTO, ezwelJobInSDO);
		
		logger.debug("[END] callEzwelJob");
		return out;
	}
	
}
