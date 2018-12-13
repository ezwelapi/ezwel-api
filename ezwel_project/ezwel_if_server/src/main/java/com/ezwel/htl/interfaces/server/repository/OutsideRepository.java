package com.ezwel.htl.interfaces.server.repository;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.commons.utils.data.ImageSDO;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcFaclAment;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;

/**
 * <pre>
 * Outside 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Repository
@APIType(description="내부 > 외부 인터페이스 데이터 핸들링 서비스")
public class OutsideRepository extends AbstractDataAccessObject {

	private static final Logger logger = LoggerFactory.getLogger(OutsideRepository.class);
	
	private CommonUtil commonUtil;
	
	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public Integer insertAllReg(List<EzcFacl> saveFaclRegDatas, Integer fromIndex, Integer toIndex) {
		
		Integer txCount = 0;
		Integer txSuccess = 0;
		List<EzcFaclImg> ezcFaclImgList = null;
		EzcFaclAment ezcFaclAment = null;
		BigDecimal faclCdSeq = null;
		EzcFacl inEzcFacl = null;
		EzcFacl outEzcFacl = null;
		EzcFacl ezcFacl = null;
		EzcFaclImg inEzcFaclImg = null;
		Integer i = 0;
		
		try {
			
			for(i = 0; i < saveFaclRegDatas.size(); i++) {
				// 시설 정보
				ezcFacl = saveFaclRegDatas.get(i);
				
				inEzcFacl = new EzcFacl();
				inEzcFacl.setPartnerCd(ezcFacl.getPartnerCd());
				inEzcFacl.setPartnerGoodsCd(ezcFacl.getPartnerGoodsCd());
				txSuccess = 0;
				
				outEzcFacl = sqlSession.selectOne(getNamespace("FACL_MAPPER", "selectEzcFaclMeta"), inEzcFacl);
				txCount++; //selectOne transaction
				
				/** 0. 시설 코드 (Number) Sequnce */
				if(outEzcFacl == null) {
					//sequnce ( 하단의  이미지 저장시 전달되어야 함으로 시퀀스 선행함 )
					faclCdSeq = sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "selectEzcFaclSeq"));
					txCount++; //sequnce transaction 
					ezcFacl.setFaclCd(faclCdSeq);
					/** 1. EZC_FACL 1건 저장 */
					//insert
					txSuccess = sqlSession.insert(getNamespace("FACL_MAPPER", "insertEzcFacl"), ezcFacl);
				}
				else {
					// 이미 존재함
					ezcFacl.setFaclCd(outEzcFacl.getFaclCd());
					/** 1. EZC_FACL 1건 변경 */
					//update
					txSuccess = sqlSession.update(getNamespace("FACL_MAPPER", "updateEzcFacl"), ezcFacl);
				}
				
				if(txSuccess > 0) {
					txCount++; //insert/update transaction 
					
					/** 2. EZC_FACL_IMG N건 저장 */
					ezcFaclImgList = ezcFacl.getEzcFaclImgList();
					if(ezcFaclImgList != null) {
						/** 
						 * EZC_FACL_IMG 테이블에는 KEY가 FACL_IMG_SEQ, FACL_CD 두개인데 FACL_CD는 같은 데이터가 N개저장되어있고
						 * FACL_IMG_SEQ는 DB테이블을 조회하기전에 알수 없는 불완전한 설계 상태임으로 merge문을 실행할 수 있는 환경이 되지 못함\
						 * 그럼으로 상단의 faclCd에 해당하는 이미지 데이터를 모두 삭제후 새로 insert함
						 **/
						inEzcFaclImg = new EzcFaclImg();
						inEzcFaclImg.setFaclCd(ezcFacl.getFaclCd());
						txCount += sqlSession.delete(getNamespace("FACL_IMG_MAPPER", "deleteEzcFaclImg"), inEzcFaclImg);
						
						for(EzcFaclImg faclImg : ezcFaclImgList) {
							//sequnce
							faclImg.setFaclImgSeq((BigDecimal) sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "selectEzcFaclImgSeq")));
							txCount++; //sequnce transaction 
							faclImg.setFaclCd(ezcFacl.getFaclCd());

							//insert
							txCount += sqlSession.insert(getNamespace("FACL_IMG_MAPPER", "insertEzcFaclImg"), faclImg);
						}
					}
					
					/** 3. EZC_FACL_AMENT N건 저장 */
					if(ezcFacl.getEzcFaclAmentList() != null) {

						for(String faclAment : ezcFacl.getEzcFaclAmentList()) {
							
							if(APIUtil.isNotEmpty(faclAment)) {
								
								ezcFaclAment = new EzcFaclAment();
								ezcFaclAment.setFaclCd(ezcFacl.getFaclCd());
								ezcFaclAment.setAmentType(faclAment);

								//merge
								txCount += sqlSession.update(getNamespace("FACL_AMENT_MAPPER", "mergeEzcFaclAment"), ezcFaclAment);
							}
						}
					}
				}
			}
		}
		catch(Exception e) {
			
			commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
			
			StringBuffer txErrorSection = new StringBuffer();
			
			txErrorSection.append(OperateConstants.STR_MAX_BRACKET_R);
			txErrorSection.append(this.getClass().getCanonicalName());
			txErrorSection.append(OperateConstants.STR_AT);
			txErrorSection.append("insertAllReg");
			txErrorSection.append(OperateConstants.STR_MAX_BRACKET_L);
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("[전체시설일괄등록 인터페이스]");
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("에러 발생 구간(index) from : ");
			txErrorSection.append(fromIndex);
			txErrorSection.append(" ~ to : ");
			txErrorSection.append(toIndex);
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("에러 발생 시설 index : ");
			txErrorSection.append(i);
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("에이전트코드 : ");
			txErrorSection.append(ezcFacl.getPartnerCd());
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("시설코드 : ");
			txErrorSection.append(ezcFacl.getPartnerGoodsCd());
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("시설명(한글) : ");
			txErrorSection.append(ezcFacl.getFaclNmKor());			
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("시설명(영문) : ");
			txErrorSection.append(ezcFacl.getFaclNmEng());
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("Exception Message : ");
			txErrorSection.append(e.getMessage());
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);
			txErrorSection.append("Exception Cause : ");
			txErrorSection.append(e.getStackTrace());
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);	
			txErrorSection.append(OperateConstants.LINE_SEPARATOR);	
			
			/** 에러 발생 레코드 interface batch error log file에 저장후 RuntimeException 없이 로깅후 종료 */
			String logFileName = this.getClass().getSimpleName().concat(OperateConstants.STR_AT).concat("insertAllReg-").concat(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT)).concat(".log");
			commonUtil.mkfile(InterfaceFactory.getInterfaceBatchErrorLogPath(), logFileName, txErrorSection.toString(), OperateConstants.DEFAULT_ENCODING, true, true);

			logger.error("Code : {}", MessageConstants.RESPONSE_CODE_9500);
			logger.error("Message : {}", e.getMessage());
			e.getStackTrace();
		}
		
		return txCount;
	}	
	

	@Transactional
	@APIOperation(description="시설 이미지 다운로드 '사용 보류'")
	public Integer downloadBuildImage(EzcFaclImg ezcFaclImg) {
		
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		int txCount = 0;
		String imageURL = null;
		List<EzcFaclImg> ezcFaclImgList = null;
		ImageSDO imageSDO = null;
		try {
		
			ezcFaclImgList = sqlSession.selectList(getNamespace("FACL_IMG_MAPPER", "selectEzcFaclImg"), ezcFaclImg);
			
			if(ezcFaclImgList != null) {
				for(EzcFaclImg item : ezcFaclImgList) {
					
					//시설 이미지 http url
					imageURL = item.getPartnerImgUrl();
					
					imageSDO = new ImageSDO();
					imageSDO.setImageURL(imageURL);
					imageSDO = commonUtil.getImageDownload(imageSDO, true);
					item.setImgUrl(imageSDO.getCanonicalPath());
					item.setRegId(Local.commonHeader().getSystemUserId());
					item.setRegDt(APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis()));	
					
					txCount += sqlSession.update(getNamespace("FACL_IMG_MAPPER", "updateEzcFaclImg"), item);
				}
			}
		}
		catch(Exception e) {
			logger.error("Message : {}", e.getMessage());
			//에러 발생 레코드 errorItems에 저장후 runtimeException 없이 로깅후 종료
			throw new APIException("이미지 다운르드 경로 DB 저장 실패 {}", new Object[] {e.getMessage()}, e) ; 
		}

		return txCount;
	}
	
	/**
	 * 멀티쓰레드
	 * EZC_CACHE_MIN_AMT 데이터 적제
	 * input = 
	 * @param userAgentDTO
	 * @param faclSearchDTO
	 * @return
	 */
	@APIOperation(description="시설검색 인터페이스")
	public FaclSearchOutSDO callFaclSearch(List<FaclSearchOutSDO> assets) {
			
		FaclSearchOutSDO out = null;
		
		try {
			
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	/**
	 * EZC_CACHE_DAY_PRICE 데이터 적제  ( 맵핑시설 테이블에 존재하는 시설의 당일특가만 적제 가능 )
	 * @param userAgentDTO
	 * @return 
	 */
	@APIOperation(description="당일특가검색 인터페이스")
	public SddSearchOutSDO callSddSearch(List<SddSearchOutSDO> assets) {
		
		SddSearchOutSDO out = null;
		
		try {
		
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
}
