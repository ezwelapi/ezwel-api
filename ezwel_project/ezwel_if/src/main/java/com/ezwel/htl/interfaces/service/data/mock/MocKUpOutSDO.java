package com.ezwel.htl.interfaces.service.data.mock;

import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtDataOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcDataOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcPenaltyOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobReservesOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnReservesOutSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadDataOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOptionsOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadPenaltyOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;

public class MocKUpOutSDO {

	//객실정보
	public static RoomReadOutSDO getRoomReadOut() {
		
		RoomReadOutSDO roomReadOutSDO = new RoomReadOutSDO();
		
		//객실정보
		RoomReadDataOutSDO roomReadDataOutSDO = new RoomReadDataOutSDO();
		
		//옵션정보
		RoomReadOptionsOutSDO roomReadOptionsOutSDO = new RoomReadOptionsOutSDO();
		
		//취소규정정보
		RoomReadPenaltyOutSDO roomReadPenaltyOutSDO = new RoomReadPenaltyOutSDO();
		
		/** 데이터 세팅 */        
		roomReadOutSDO.setCode("1000");
		roomReadOutSDO.setMessage("정상적으로 처리 되었습니다.");
		roomReadDataOutSDO.setRoomNo("Yob1daQcPbOlVncEHz/Q62kMjcnsyRrO0QH/U/qJzoi^JE^t1pCAhgNe80PkfWL7seEbLG22^1yF65eQCg9UALt1KK7eTF/XQ^Q53nLhuVGd0lTKdaS9IK/PECb37ksVU9o4QzaTuwBV6lz3IIxb/BBSKZioaVq/uQPIpaCiz6rbRGGFFBalAziFGnZP2g^YYjZPp4cfKWn0dW^iVtNuwe0GuSwsRYYTuBFKnY/5abk=");
		roomReadDataOutSDO.setRoomName("Standard Double Room(1 double bed request)");
		roomReadDataOutSDO.setPriceForList(58000);
		roomReadDataOutSDO.setPriceForSale(58000);
		roomReadDataOutSDO.setAdtCntMin(2);
		roomReadDataOutSDO.setAdtCntMax(2);
		roomReadDataOutSDO.setRoomInfo("Standard Double Room(1 double bed request)");
		roomReadDataOutSDO.setRoomAvailCount(1);
		roomReadDataOutSDO.setRsvTypeCode("0");
		roomReadDataOutSDO.setBreakfast("0");
		roomReadDataOutSDO.setSpcType("0");
		roomReadDataOutSDO.setSpcTypeTime("");
		roomReadOptionsOutSDO.setOptNo("1");
		roomReadOptionsOutSDO.setOptName("조식");
		roomReadOptionsOutSDO.setOptPrice(28000);
		roomReadOptionsOutSDO.setOptCountMax(2);
		roomReadOptionsOutSDO.setOptNo("2");
		roomReadOptionsOutSDO.setOptName("Extra bed");
		roomReadOptionsOutSDO.setOptPrice(15000);
		roomReadOptionsOutSDO.setOptCountMax(1);
		roomReadPenaltyOutSDO.setApplyBgnDate("20181229");
		roomReadPenaltyOutSDO.setCancelFeeRate(100);
		roomReadPenaltyOutSDO.setCancelFeePrice(58000);
		roomReadPenaltyOutSDO.setApplyBgnDate("20181230");
		roomReadPenaltyOutSDO.setCancelFeeRate(100);
		roomReadPenaltyOutSDO.setCancelFeePrice(58000);
		roomReadPenaltyOutSDO.setApplyBgnDate("20181231");
		roomReadPenaltyOutSDO.setCancelFeeRate(100);
		roomReadPenaltyOutSDO.setCancelFeePrice(58000);
		roomReadPenaltyOutSDO.setApplyBgnDate("20190101");
		roomReadPenaltyOutSDO.setCancelFeeRate(100);
		roomReadPenaltyOutSDO.setCancelFeePrice(58000);		
		
		return roomReadOutSDO;
	}
	
	//취소규정정보
	public static CancelFeePsrcOutSDO getCancelFeePsrcOut() {
		
		CancelFeePsrcOutSDO cancelFeePsrcOutSDO = new CancelFeePsrcOutSDO();
		
		//취소수수료 대체텍스트
		CancelFeePsrcDataOutSDO cancelFeePsrcDataOutSDO = new CancelFeePsrcDataOutSDO();
		
		//취소수수료정보
		CancelFeePsrcPenaltyOutSDO cancelFeePsrcPenaltyOutSDO = new CancelFeePsrcPenaltyOutSDO();
		
		cancelFeePsrcOutSDO.setCode("1000");
		cancelFeePsrcOutSDO.setMessage("정상적으로 처리 되었습니다.");
		cancelFeePsrcDataOutSDO.setCancelFeeText("17:00 이후 익일 취소규정 적용 되어집니다.");
		cancelFeePsrcPenaltyOutSDO.setApplyBgnDate("20181229");
		cancelFeePsrcPenaltyOutSDO.setCancelFeeRate(100);
		cancelFeePsrcPenaltyOutSDO.setCancelFeePrice(58000);
		cancelFeePsrcPenaltyOutSDO.setApplyBgnDate("20181230");
		cancelFeePsrcPenaltyOutSDO.setCancelFeeRate(100);
		cancelFeePsrcPenaltyOutSDO.setCancelFeePrice(58000);
		cancelFeePsrcPenaltyOutSDO.setApplyBgnDate("20181231");
		cancelFeePsrcPenaltyOutSDO.setCancelFeeRate(100);
		cancelFeePsrcPenaltyOutSDO.setCancelFeePrice(58000);
		cancelFeePsrcPenaltyOutSDO.setApplyBgnDate("20190101");
		cancelFeePsrcPenaltyOutSDO.setCancelFeeRate(100);
		cancelFeePsrcPenaltyOutSDO.setCancelFeePrice(58000);		
		
		return cancelFeePsrcOutSDO;
	}
	
	//결재완료내역전송
	public static RsvHistSendOutSDO getRsvHistSendOut() {
			
		RsvHistSendOutSDO rsvHistSendOutSDO = new RsvHistSendOutSDO();
		
		rsvHistSendOutSDO.setCode("1000");
		rsvHistSendOutSDO.setMessage("정상적으로 처리 되었습니다.");
		rsvHistSendOutSDO.setRsvNo("E123456789");
		rsvHistSendOutSDO.setOtaRsvNo("O123456789");
		
		return rsvHistSendOutSDO;
	}
	
	//취소수수료계산
	public static CancelFeeAmtOutSDO getCancelFeeAmtOut() {
				
		CancelFeeAmtOutSDO cancelFeeAmtOutSDO = new CancelFeeAmtOutSDO();
		
		//취소위약금
		CancelFeeAmtDataOutSDO cancelFeeAmtDataOutSDO = new CancelFeeAmtDataOutSDO();
		
		cancelFeeAmtOutSDO.setCode("1000");
		cancelFeeAmtOutSDO.setMessage("정상적으로 처리 되었습니다.");
		cancelFeeAmtDataOutSDO.setRsvNo("E123456789");
		cancelFeeAmtDataOutSDO.setCancelFeePrice(0);
		cancelFeeAmtDataOutSDO.setCancelFeeText("17:00 이후 익일 취소규정 적용 되어집니다.");
			
		return cancelFeeAmtOutSDO;
	}
	
	//주문취소요청
	public static OrderCancelReqOutSDO getOrderCancelReqOut() {
		
		OrderCancelReqOutSDO orderCancelReqOutSDO = new OrderCancelReqOutSDO();
		
		orderCancelReqOutSDO.setCode("1000");
		orderCancelReqOutSDO.setMessage("정상적으로 처리 되었습니다.");
		
		return orderCancelReqOutSDO;
	}
	
	//누락건확인
	public static OmiNumIdnOutSDO getOmiNumIdnOut() {
		
		OmiNumIdnOutSDO omiNumIdnOutSDO = new OmiNumIdnOutSDO();
		
		//예약정보
		OmiNumIdnReservesOutSDO omiNumIdnReservesOutSDO = new OmiNumIdnReservesOutSDO();
		
		omiNumIdnOutSDO.setCode("1000");
		omiNumIdnOutSDO.setMessage("정상적으로 처리 되었습니다.");
		omiNumIdnReservesOutSDO.setRsvNo("E123456789");
		omiNumIdnReservesOutSDO.setRsvStat("r02");
		omiNumIdnReservesOutSDO.setOtaRsvNo("O123456789");
		omiNumIdnReservesOutSDO.setCompareStat("c01");
		
		return omiNumIdnOutSDO;
	}
	
	//주문대사(이지웰)
	public static EzwelJobOutSDO getEzwelJobOut() {
		
		EzwelJobOutSDO ezwelJobOutSDO = new EzwelJobOutSDO();
		
		EzwelJobReservesOutSDO ezwelJobReservesOutSDO = new EzwelJobReservesOutSDO();
		
		ezwelJobOutSDO.setCode("1000");
		ezwelJobOutSDO.setMessage("정상적으로 처리 되었습니다.");
		ezwelJobReservesOutSDO.setRsvNo("E123456789");
		ezwelJobReservesOutSDO.setRsvPdtNo("E000000001");
		ezwelJobReservesOutSDO.setRsvPrice(58000);
		ezwelJobReservesOutSDO.setPdtNo("KRSEL112");
		ezwelJobReservesOutSDO.setOtaRsvNo("O123456789");
		ezwelJobReservesOutSDO.setRsvStat("r02");
		
		return ezwelJobOutSDO;
		
	}
	
}
