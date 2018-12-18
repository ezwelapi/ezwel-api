package com.ezwel.htl.interfaces.service.data.mock;

import java.util.ArrayList;
import java.util.List;

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
		
		//객실정보
		RoomReadDataOutSDO roomReadDataOutSDO = new RoomReadDataOutSDO();
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
		
		//옵션정보
		RoomReadOptionsOutSDO roomReadOptionsOutSDO = new RoomReadOptionsOutSDO();
		roomReadOptionsOutSDO.setOptNo("1");
		roomReadOptionsOutSDO.setOptName("조식");
		roomReadOptionsOutSDO.setOptPrice(28000);
		roomReadOptionsOutSDO.setOptCountMax(2);
		roomReadOptionsOutSDO.setOptNo("2");
		roomReadOptionsOutSDO.setOptName("Extra bed");
		roomReadOptionsOutSDO.setOptPrice(15000);
		roomReadOptionsOutSDO.setOptCountMax(1);
		
		//취소규정정보
		RoomReadPenaltyOutSDO roomReadPenaltyOutSDO = new RoomReadPenaltyOutSDO();		
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
		
		List<RoomReadOptionsOutSDO> optionList = new ArrayList<>();
		optionList.add(roomReadOptionsOutSDO);
		roomReadDataOutSDO.setOptions(optionList);
		
		List<RoomReadPenaltyOutSDO> penaltyList = new ArrayList<>();
		penaltyList.add(roomReadPenaltyOutSDO);
		roomReadDataOutSDO.setPenalty(penaltyList);
		
		List<RoomReadDataOutSDO> dataList = new ArrayList<>();
		dataList.add(roomReadDataOutSDO);
		
		RoomReadOutSDO out = new RoomReadOutSDO();
		out.setCode("1000");
		out.setMessage("정상적으로 처리 되었습니다.");
		out.setData(dataList);
		
		return out;
	}
	
	//취소규정정보
	public static CancelFeePsrcOutSDO getCancelFeePsrcOut() {
		
		//취소수수료 대체텍스트
		CancelFeePsrcDataOutSDO cancelFeePsrcDataOutSDO = new CancelFeePsrcDataOutSDO();
		
		//취소수수료정보
		CancelFeePsrcPenaltyOutSDO cancelFeePsrcPenaltyOutSDO = new CancelFeePsrcPenaltyOutSDO();		
		
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
		
		//취소수수료정보선언
		List<CancelFeePsrcPenaltyOutSDO> penaltyList = new ArrayList<>();		
		penaltyList.add(cancelFeePsrcPenaltyOutSDO);
		
		cancelFeePsrcDataOutSDO.setPenalty(penaltyList);
		
		//취소수수료전문
		CancelFeePsrcOutSDO out = new CancelFeePsrcOutSDO();
		out.setCode("1000");
		out.setMessage("정상적으로 처리 되었습니다.");
		out.setData(cancelFeePsrcDataOutSDO);
		
		return out;
	}
	
	//결재완료내역전송
	public static RsvHistSendOutSDO getRsvHistSendOut() {
		
		//결제완료내역전문
		RsvHistSendOutSDO rsvHistSendOutSDO = new RsvHistSendOutSDO();		
		rsvHistSendOutSDO.setCode("1000");
		rsvHistSendOutSDO.setMessage("정상적으로 처리 되었습니다.");
		rsvHistSendOutSDO.setRsvNo("E123456789");
		rsvHistSendOutSDO.setOtaRsvNo("O123456789");
		
		return rsvHistSendOutSDO;
	}
	
	//취소수수료계산
	public static CancelFeeAmtOutSDO getCancelFeeAmtOut() {
		
		//취소위약금
		CancelFeeAmtDataOutSDO cancelFeeAmtDataOutSDO = new CancelFeeAmtDataOutSDO();
		
		cancelFeeAmtDataOutSDO.setRsvNo("E123456789");
		cancelFeeAmtDataOutSDO.setCancelFeePrice(0);
		cancelFeeAmtDataOutSDO.setCancelFeeText("17:00 이후 익일 취소규정 적용 되어집니다.");
		
		//취소수수료계산전문
		CancelFeeAmtOutSDO out = new CancelFeeAmtOutSDO();
		out.setCode("1000");
		out.setMessage("정상적으로 처리 되었습니다.");
		out.setData(cancelFeeAmtDataOutSDO);
		
		return out;
	}
	
	//주문취소요청
	public static OrderCancelReqOutSDO getOrderCancelReqOut() {
		
		//주문취소전문
		OrderCancelReqOutSDO out = new OrderCancelReqOutSDO();		
		out.setCode("1000");
		out.setMessage("정상적으로 처리 되었습니다.");
		
		return out;
	}
	
	//누락건확인
	public static OmiNumIdnOutSDO getOmiNumIdnOut() {
		
		//예약정보
		OmiNumIdnReservesOutSDO omiNumIdnReservesOutSDO = new OmiNumIdnReservesOutSDO();
		
		omiNumIdnReservesOutSDO.setRsvNo("E123456789");
		omiNumIdnReservesOutSDO.setRsvStat("r02");
		omiNumIdnReservesOutSDO.setOtaRsvNo("O123456789");
		omiNumIdnReservesOutSDO.setCompareStat("c01");
		
		//누락건확인전문
		OmiNumIdnOutSDO out = new OmiNumIdnOutSDO();		
		out.setCode("1000");
		out.setMessage("정상적으로 처리 되었습니다.");
		out.setReserves(omiNumIdnReservesOutSDO);
		
		return out;
	}
	
	//주문대사(이지웰)
	public static EzwelJobOutSDO getEzwelJobOut() {
		
		//예약정보
		EzwelJobReservesOutSDO ezwelJobReservesOutSDO = new EzwelJobReservesOutSDO();
		
		ezwelJobReservesOutSDO.setRsvNo("E123456789");
		ezwelJobReservesOutSDO.setRsvPdtNo("E000000001");
		ezwelJobReservesOutSDO.setRsvPrice(58000);
		ezwelJobReservesOutSDO.setPdtNo("KRSEL112");
		ezwelJobReservesOutSDO.setOtaRsvNo("O123456789");
		ezwelJobReservesOutSDO.setRsvStat("r02");
		
		//대사정보선언
		List<EzwelJobReservesOutSDO> reservesList = new ArrayList<>();
		reservesList.add(ezwelJobReservesOutSDO);
		
		//주문대사전문
		EzwelJobOutSDO out = new EzwelJobOutSDO();		
		out.setCode("1000");
		out.setMessage("정상적으로 처리 되었습니다.");
		out.setReserves(reservesList);
		
		return out;
		
	}
	
}
