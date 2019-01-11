package com.ezwel.htl.interfaces.server.repository;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcReservBase;
import com.ezwel.htl.interfaces.server.entities.EzcReservRoomOpt;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobInSDO;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobOutSDO;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobReservesOutSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordInSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordOutSDO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopInSDO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopOutSDO;
import com.ezwel.htl.interfaces.service.data.view.ViewDataOutSDO;
import com.ezwel.htl.interfaces.service.data.view.ViewInSDO;
import com.ezwel.htl.interfaces.service.data.view.ViewOptionsOutSDO;
import com.ezwel.htl.interfaces.service.data.view.ViewOutSDO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegInSDO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegOutSDO;

/**
 * <pre>
 * Inside 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Repository
@APIType(description="외부 > 내부 인터페이스 데이터 핸들링 서비스")
public class InsideRepository extends AbstractDataAccessObject {

	private static final Logger logger = LoggerFactory.getLogger(InsideRepository.class);

	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutSDO callSaleStop(SaleStopInSDO saleStopSDO) {
		logger.debug("[START] callSaleStop {}", saleStopSDO);
		
		SaleStopOutSDO out = null;

		try {
			
			EzcFacl inEzcFacl = new EzcFacl(); 
			inEzcFacl.setPartnerCd(Local.commonHeader().getHttpConfigSDO().getHttpAgentId());
			inEzcFacl.setPartnerGoodsCd(saleStopSDO.getPdtNo());
			inEzcFacl.setUseYn(saleStopSDO.getSellFlag());
			
			Integer txCount = sqlSession.update(getNamespace("FACL_MAPPER", "updateEzcFaclUseYn"), inEzcFacl);
			
			//output
			out = new SaleStopOutSDO();
			out.setTxCount(txCount);

			if(txCount == 0) {
				out.setCode(Integer.toString(MessageConstants.RESPONSE_CODE_9501));
				out.setMessage("시설 상품 코드 정보가 존재하지 않습니다.");
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "시설판매중지설정 데이터 갱신 장애발생", e);
		}
	
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutSDO callVoucherReg(VoucherRegInSDO voucherRegSDO) {
		logger.debug("[START] callVoucherReg {}", voucherRegSDO);
		
		VoucherRegOutSDO out = null;
		
		try {
			
			EzcReservBase inEzcReservBase = new EzcReservBase();
			inEzcReservBase.setReservNum(new BigDecimal(voucherRegSDO.getRsvNo()));
			inEzcReservBase.setPartnerCd(Local.commonHeader().getHttpConfigSDO().getHttpAgentId());
			inEzcReservBase.setFaclReservNum(voucherRegSDO.getVoucherNo());
			
			Integer txCount = sqlSession.update(getNamespace("RESERV_BASE_MAPPER", "updateEzcReservBaseVoucherNo"), inEzcReservBase);
			
			//output
			out = new VoucherRegOutSDO();
			out.setTxCount(txCount);

			if(txCount == 0) {
				out.setCode(Integer.toString(MessageConstants.RESPONSE_CODE_9500));
				out.setMessage("예약 번호에 해당하는 예약정보가 존재하지 않습니다.");
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "시설바우처번호 갱신 장애발생", e);
		}
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	
	//반완료
	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutSDO callView(ViewInSDO viewSDO) {
		logger.debug("[START] callView {}", viewSDO);
		
		ViewOutSDO out = null;
		
		try {

			EzcReservBase inEzcReservBase = new EzcReservBase();
			inEzcReservBase.setReservNum(new BigDecimal(viewSDO.getRsvNo()));
			inEzcReservBase.setStartDate(viewSDO.getStartDate());
			inEzcReservBase.setEndDate(viewSDO.getEndDate());
			inEzcReservBase.setDateType(viewSDO.getDateType());
			
			List<EzcReservBase> ezcReservBaseList = sqlSession.selectList(getNamespace("RESERV_BASE_MAPPER", "selectListEzcReservBase"), inEzcReservBase);
			List<EzcReservRoomOpt> ezcReservRoomOptList = null; 
			//output
			out = new ViewOutSDO();
			
			ViewDataOutSDO readData = null;
			EzcReservRoomOpt inEzcReservRoomOpt = null;
			ViewOptionsOutSDO options = null;
			
			if(ezcReservBaseList != null && ezcReservBaseList.size() > 0) {
				
				for(EzcReservBase reservBase : ezcReservBaseList) {

					readData = new ViewDataOutSDO();
					//setter/getter
					//전차장님 스타트!ㅋ
					//readData.setXXX(reservBase.getXXX());
					
					// 옵션 조회
					inEzcReservRoomOpt = new EzcReservRoomOpt();
					inEzcReservRoomOpt.setReservRoomNum(reservBase.getReservRoomNum());
					ezcReservRoomOptList = sqlSession.selectList(getNamespace("RESERV_ROOM_OPT_MAPPER", "selectListEzcReservRoomOpt"), inEzcReservRoomOpt);
					
					for(EzcReservRoomOpt reservRoomOpt : ezcReservRoomOptList) {
						
						options = new ViewOptionsOutSDO();
						//setter/getter
						//전차장님 스타트!ㅋ								
						//options.setXXXX(reservRoomOpt.getXXXX());
						
						readData.addOptions(options);
					}
					
					out.addData(readData);
				}
			}
			else {
				out.setMessage("예약정보가 존재하지 않습니다.");
			}
			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "예약내역조회 장애발생", e);
		}
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	
	//반완료
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutSDO callAgentJob(AgentJobInSDO agentJobSDO) {
		logger.debug("[START] callAgentJob {}", agentJobSDO);
		
		AgentJobOutSDO out = null;
		AgentJobReservesOutSDO reserves = null;
		
		try {
			
			EzcReservBase inEzcReservBase = new EzcReservBase();
			inEzcReservBase.setReservNum(new BigDecimal(agentJobSDO.getRsvNo()));
			inEzcReservBase.setStartDate(agentJobSDO.getRsvDateStart());
			inEzcReservBase.setEndDate(agentJobSDO.getRsvDateEnd());
			inEzcReservBase.setDateType("J");
			
			List<EzcReservBase> ezcReservBaseList = sqlSession.selectList(getNamespace("RESERV_BASE_MAPPER", "selectListEzcReservBase"), inEzcReservBase);		
			
			//output
			out = new AgentJobOutSDO();

			if(ezcReservBaseList != null && ezcReservBaseList.size() > 0) {
				
				for(EzcReservBase reservBase : ezcReservBaseList) {

					reserves = new AgentJobReservesOutSDO();
					//setter/getter
					//전차장님 스타트!ㅋ
					//reserves.setXXX(reservBase.getXXX());
					
					out.addReserves(reserves);
				}
			}
			else {
				out.setMessage("예약정보가 존재하지 않습니다.");
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "주문대사(제휴사) 저장 장애발생", e);
		}
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
	
}
