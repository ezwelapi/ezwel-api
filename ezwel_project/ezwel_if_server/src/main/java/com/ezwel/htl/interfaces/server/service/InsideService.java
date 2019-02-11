package com.ezwel.htl.interfaces.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcGuestRoom;
import com.ezwel.htl.interfaces.server.entities.EzcRoomSearch;
import com.ezwel.htl.interfaces.server.repository.InsideRepository;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobInSDO;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchDataOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordInSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadDataOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopInSDO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchDataOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchInSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.view.ViewInSDO;
import com.ezwel.htl.interfaces.service.data.view.ViewOutSDO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegInSDO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegOutSDO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Service
@APIType(description="외부 > 내부 인터페이스 서비스")
public class InsideService {

	private static final Logger logger = LoggerFactory.getLogger(InsideService.class);
	
	private InsideRepository insideRepository;
	
	private OutsideService outsideService;
	
	private HttpInterfaceExecutor inteface;
	
	private PropertyUtil propertyUtil;
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutSDO callRecord(RecordInSDO recordSDO) throws APIException, Exception {
		logger.debug("[START] callRecord {}", recordSDO);
		
		RecordOutSDO out = null;
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		inteface = (HttpInterfaceExecutor) LApplicationContext.getBean(inteface, HttpInterfaceExecutor.class);
		
		HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("record", Local.commonHeader().getHttpConfigSDO().getHttpAgentId());
		httpConfigSDO.setHttpApiSignature(APIUtil.getHttpSignature(httpConfigSDO.getHttpAgentId(), httpConfigSDO.getHttpApiKey(), httpConfigSDO.getHttpApiTimestamp()));
		httpConfigSDO.setRestURI(recordSDO.getDataUrl());
		httpConfigSDO.setEncoding(OperateConstants.DEFAULT_ENCODING);
		httpConfigSDO.setDoInput(true);
		
		// 인터페이스 결과 1개 시설에 대한 데이터 저장/삭제 실행
		List<AllRegOutSDO> assets = new ArrayList<AllRegOutSDO>();
		AllRegOutSDO recordData = (AllRegOutSDO) inteface.sendJSON(httpConfigSDO, AllRegOutSDO.class);
		assets.add(recordData);
		AllRegOutSDO result = outsideService.saveAllReg(assets, true);
		
		out = new RecordOutSDO();
		out.setCode(result.getCode());
		out.setMessage(result.getMessage());
		out.setTxCount(result.getTxCount());
		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutSDO callSaleStop(SaleStopInSDO saleStopSDO) throws APIException, Exception {
		logger.debug("[START] callSaleStop {}", saleStopSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		
		SaleStopOutSDO out = insideRepository.callSaleStop(saleStopSDO);
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	

	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutSDO callView(ViewInSDO viewSDO) throws APIException, Exception {
		logger.debug("[START] callView {}", viewSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		
		ViewOutSDO out = insideRepository.callView(viewSDO);
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutSDO callVoucherReg(VoucherRegInSDO voucherRegSDO) throws APIException, Exception {
		logger.debug("[START] callVoucherReg {}", voucherRegSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		
		VoucherRegOutSDO out = insideRepository.callVoucherReg(voucherRegSDO);
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	

	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutSDO callAgentJob(AgentJobInSDO agentJobSDO) throws APIException, Exception {
		logger.debug("[START] callAgentJob {}", agentJobSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		
		AgentJobOutSDO out = insideRepository.callAgentJob(agentJobSDO);
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
	

	@APIOperation(description = "직영숙박 DB조회")
	public RoomReadOutSDO findGuestRoomList(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		logger.debug("[START] findGuestRoomList {} {}", userAgentSDO, roomReadSDO);
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		
		RoomReadOutSDO out = null;
		RoomReadDataOutSDO roomReadData = null; 
		EzcGuestRoom inGuestRoom = null;
		List<EzcGuestRoom> outGuestRoomList = null;
		
		try {
			out = new RoomReadOutSDO();
			
			inGuestRoom = (EzcGuestRoom) propertyUtil.copySameProperty(roomReadSDO, EzcGuestRoom.class);
			outGuestRoomList = insideRepository.selectGuestRoomList(inGuestRoom);
			
			if(outGuestRoomList != null && outGuestRoomList.size() > 0) {
				for(EzcGuestRoom data : outGuestRoomList) {
					roomReadData = (RoomReadDataOutSDO) propertyUtil.copySameProperty(data, RoomReadDataOutSDO.class); 
					out.addData(roomReadData);
				}
			}
			else {
				out.setMessage("객실 정보가 존재하지 않습니다.");
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "직영숙박 객실정보조회 장애발생.", e);
		}
		
		logger.debug("[END] findGuestRoomList data size : {}", (out != null && out.getData() != null ? out.getData().size() : 0));
		return out;
	}
	


	@APIOperation(description = "시설검색(최저가 정보)-직영/숙박 조회")
	public FaclSearchOutSDO findFaclSearch(UserAgentSDO userAgentSDO, FaclSearchInSDO faclSearchInSDO) {
		logger.debug("[START] findFaclSearch {} {}", userAgentSDO, faclSearchInSDO);
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		
		EzcRoomSearch inEzcRoomSearch = null;
		FaclSearchOutSDO out = null;
		List<EzcRoomSearch> selectData = null;
		
		try {
			out = new FaclSearchOutSDO();
			
			inEzcRoomSearch = (EzcRoomSearch) propertyUtil.copySameProperty(faclSearchInSDO, EzcRoomSearch.class);
			//조회
			selectData = insideRepository.selectFaclSearchList(inEzcRoomSearch);
			
			if(selectData != null && selectData.size() > 0) {
				for(EzcRoomSearch item : selectData) {
					out.addData((FaclSearchDataOutSDO) propertyUtil.copySameProperty(item, FaclSearchDataOutSDO.class));
				}
			}
			else {
				out.setMessage("최저가 직영/숙박 시설 정보가 존재하지 않습니다.");	
			}
			
		} catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "시설검색(최저가 정보)-직영/숙박 조회 장애발생.", e);
		}
		logger.debug("[END] findFaclSearch out : {}", out);
		return out;
	}
	
	

	@APIOperation(description = "당일특가검색-직영/숙박 조회")
	public SddSearchOutSDO findSddSearch(UserAgentSDO userAgentSDO) {
		logger.debug("[START] findSddSearch {} {}", userAgentSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		EzcRoomSearch inEzcRoomSearch = null;
		SddSearchOutSDO out = null;
		List<EzcRoomSearch> selectData = null;
		
		try {
			out = new SddSearchOutSDO();
			
			inEzcRoomSearch = new EzcRoomSearch();
			inEzcRoomSearch.setCheckInDate(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT));
			inEzcRoomSearch.setCheckOutDate(APIUtil.getDateHandler(inEzcRoomSearch.getCheckInDate(), OperateConstants.DEF_DAY_FORMAT, null, null, 1));
			//조회 
			selectData = insideRepository.selectSddSearchList(inEzcRoomSearch);
		
			if(selectData != null && selectData.size() > 0) {
				for(EzcRoomSearch item : selectData) {
					out.addData((SddSearchDataOutSDO) propertyUtil.copySameProperty(item, SddSearchDataOutSDO.class));
				}
			}
			else {
				out.setMessage("당일특가 직영/숙박 시설 정보가 존재하지 않습니다.");	
			}			
			
		} catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "당일특가검색-직영/숙박 조회 장애발생.", e);
		}
		logger.debug("[END] findSddSearch out : {}", out);
		return out;
	}	
}
