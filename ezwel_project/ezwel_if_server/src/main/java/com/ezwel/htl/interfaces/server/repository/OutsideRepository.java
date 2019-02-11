package com.ezwel.htl.interfaces.server.repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.sdo.EzcFaclMappingSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcCacheDayPrice;
import com.ezwel.htl.interfaces.server.entities.EzcCacheMinAmt;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcFaclAment;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.server.entities.EzcMappingFacl;
import com.ezwel.htl.interfaces.server.entities.EzcMappingGrpFacl;
import com.ezwel.htl.interfaces.server.sdo.TransactionOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataRealtimeImageOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchDataOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchDataOutSDO;
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
	
	private PropertyUtil propertyUtil;
	
	private StackTraceUtil stackTraceUtil;
	

	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public AllRegOutSDO insertAllReg(AllRegOutSDO out, List<EzcFacl> saveFaclRegDatas, Integer fromIndex, Integer toIndex, boolean isErrorPassed) {
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		Integer txCount = 0;
		Integer txSuccess = 0;
		List<EzcFaclImg> ezcFaclImgList = null;
		List<EzcFaclImg> ezcFaclImgDBList = null;
		EzcFacl ezcFacl = null;
		Integer i = 0;
		
		try {
			
			for(i = 0; i < saveFaclRegDatas.size(); i++) {

				/** 시설 정보 전문 데이터 */
				ezcFacl = saveFaclRegDatas.get(i);
				
				/** 1. EZC_FACL 시설 데이터 저장 */
				txSuccess = mergeEzcFacl(ezcFacl, txCount);
				
				if(txSuccess > 0) {
					
					/** 2. EZC_FACL_IMG N건 저장 : 전문 시설 이미지 목록 */
					ezcFaclImgList = ezcFacl.getEzcFaclImgList();
					
					/** 기존 저장된 이미지 정보 조회 */
					ezcFaclImgDBList = getEzcFaclImgList(ezcFacl);
					
					if(ezcFaclImgList != null && ezcFaclImgList.size() > 0) {
						/** 
						 * EZC_FACL_IMG 테이블에는 KEY가 FACL_IMG_SEQ, FACL_CD 두개인데 FACL_CD는 같은 데이터가 N개저장되어있고
						 * FACL_IMG_SEQ는 DB테이블을 조회하기전에 알수 없는 불완전한 설계 상태임으로 merge문을 실행할 수 있는 환경이 되지 못함\
						 * 그럼으로 상단의 faclCd에 해당하는 이미지 데이터를 모두 삭제후 새로 insert함
						 **/

						logger.info("# image db original data size : {}", (ezcFaclImgDBList != null ? ezcFaclImgDBList.size() : 0));
						// 전문 시설 이미지 목록 loop
						for(EzcFaclImg faclImg : ezcFaclImgList) {
							
							/** DB저장 및 다운로드할 이미지 데이터 필터 */
							saveImageDataFilter(out, ezcFacl, faclImg, ezcFaclImgDBList, txCount);
						}
						
						if(ezcFaclImgDBList != null) {
							
							/** 삭제하거나 다운로드 재시도할 이미지 데이터 필터 */
							deleteAndReDownloadImageFilter(out, ezcFacl, ezcFaclImgDBList, txCount);
						}
					}
					else {

						/** 전문의 시설정보에 이미지가 1개도 없을경우모두 삭제 및 파일삭제 대상 경로 세팅 */
						deleteAllFaclImg(out, ezcFacl, ezcFaclImgDBList, txCount);
					}
					
					logger.info("# fianl image filter list : {}", ezcFaclImgDBList);
					
					/** 3. EZC_FACL_AMENT N건 저장 */
					saveFaclAmentList(ezcFacl, txCount);
				}
			}
		}
		catch(Exception e) {
			
			if(isErrorPassed) {
				
				/*****************************
				 * [START] DB LOG DATA SETTING / 
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("insertAllReg"));
				apiBatcLogSDO.setBatcDesc("전체시설일괄등록 인터페이스 데이터 저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_ER);
				apiBatcLogSDO.setErrCont(new StringBuffer()
						.append( "::전체시설일괄등록 인터페이스 데이터 저장 장애 발생시각 : " )
						.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러 발생 구간(index) from : " )
						.append( fromIndex )
						.append( " ~ to : " )
						.append( toIndex )
						.append( "중 " )
						.append( i )
						.append( "번째 인덱스 데이터" )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러발생 시설 : " )
						.append( ezcFacl )
						.toString());
				
				Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
				/*****************************
				 * [END]   DB LOG DATA SETTING 
				 *****************************/
				
				logger.error(APIUtil.formatMessage("- 전체시설일괄등록 인터페이스 데이터 저장 장애발생 =>> 제휴사아이디 : {}, 제휴사에이전트설명 : {}, 에러발생 시설 : {}", out.getHttpAgentId(), out.getHttpAgentDesc(), ezcFacl), e);
			}
			else {
				throw new APIException("전체시설일괄등록 인터페이스 데이터 저장 실패 {}", new Object[] {e.getMessage()}, e) ;
			}
		}
		
		out.setTxCount(out.getTxCount() + txCount);
		
		return out;
	}	
	
	
	
	@SuppressWarnings("finally")
	@APIOperation(description="시설정보 저장")
	private Integer mergeEzcFacl(EzcFacl ezcFacl, Integer txCount) {
		
		Integer txSuccess = 0;
		EzcFacl outEzcFacl = null;
		EzcFacl inEzcFacl = null;
		BigDecimal faclCdSeq = null;
		
		try {
			
			inEzcFacl = new EzcFacl();
			inEzcFacl.setPartnerCd(ezcFacl.getPartnerCd());
			inEzcFacl.setPartnerGoodsCd(ezcFacl.getPartnerGoodsCd());
			
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
			}
		}
		catch(Exception e) {
			logger.error("시설정보 저장 장애발생", e);
			/** Batch Error Report */
			//여기
			
			
			
		}
		finally {
			return txSuccess;
		}
	}
	
	@Transactional(readOnly=true)
	@APIOperation(description="기존 DB테이블 저장된 이미지 정보 조회")
	private List<EzcFaclImg> getEzcFaclImgList(EzcFacl ezcFacl) {
		//기존 저장된 이미지 정보 조회
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		EzcFaclImg inEzcFaclImg = new EzcFaclImg();
		inEzcFaclImg.setFaclCd(ezcFacl.getFaclCd());
		propertyUtil.removeDefaulFieldData(inEzcFaclImg);
		
		//기존 저장된 이미지 정보 조회
		List<EzcFaclImg> out = sqlSession.selectList(getNamespace("FACL_IMG_MAPPER", "selectListPartnerImgInfo"), inEzcFaclImg);
		
		return out;
	}
	
	
	@APIOperation(description="DB저장 및 다운로드할 이미지 데이터 필터")
	private void saveImageDataFilter(AllRegOutSDO out, EzcFacl ezcFacl, EzcFaclImg faclImg, 
			List<EzcFaclImg> ezcFaclImgDBList, Integer txCount) {

		
		EzcFaclImg savedImageDB = null;
		Boolean isSavedImageDB = false;
		AllRegDataRealtimeImageOutSDO realtimeImageIO = null;
		Integer imgIdx = OperateConstants.INTEGER_MINUS_ONE; 
		
		try {
		
			//시설이미지 객체에 시설 코드 세팅
			faclImg.setFaclCd(ezcFacl.getFaclCd());
	
			//이미 동일한 이미지 URL이 저장되어있는 지 체크
			if(ezcFaclImgDBList != null && ezcFaclImgDBList.size() > 0) {
				
				//for(AllRegDataRealtimeImageOutSDO imageDB : ezcFaclImgDBList) {
				for(imgIdx = 0; imgIdx < ezcFaclImgDBList.size(); imgIdx++) {
					//DB에 저장된 이미지 정보
					savedImageDB = ezcFaclImgDBList.get(imgIdx);
					
					//logger.debug("#DB TABLE 이미지정보 : {}", savedImageDB);
					//logger.info("# image check FaclCd : '{}' equals '{}', PartnerImgUrl : '{}' equals '{}'", savedImageDB.getFaclCd(), ezcFacl.getFaclCd(), savedImageDB.getPartnerImgUrl(), faclImg.getPartnerImgUrl());
					//시설코드가 같고 이미지 URL이 같은 정보인지 체크
					if(!savedImageDB.isPassImage() && savedImageDB.getFaclCd().equals(ezcFacl.getFaclCd()) 
						&& savedImageDB.getPartnerImgUrl().equals(faclImg.getPartnerImgUrl())) {
						//DB에 존재하는 이미지정보
						isSavedImageDB = true;
						savedImageDB.setPassImage(true);
						logger.info("[PASS-IMAGE] image db equals image url : {}", faclImg.getPartnerImgUrl());
						break;
					}
				}
			}
			
			//DB에 존재하지 않는 이미지 URL이면 입력
			if(!isSavedImageDB) {
				//저장되지 않은 신규 이미지 이면 입력
				logger.info("- 입력준비 : {}", faclImg.getPartnerImgUrl());
				
				//신규로 다운받아야할 이미지
				realtimeImageIO = new AllRegDataRealtimeImageOutSDO();
				realtimeImageIO.setPartnerImgUrl(faclImg.getPartnerImgUrl());
				realtimeImageIO.setPartnerCd(ezcFacl.getPartnerCd());
				realtimeImageIO.setCityCd(ezcFacl.getCityCd());
				realtimeImageIO.setAreaCd(ezcFacl.getAreaCd());
				realtimeImageIO.setFaclCd(ezcFacl.getFaclCd());
	
				logger.info("- 입력실행 신규 URL : {}", faclImg.getPartnerImgUrl());
				//sequnce
				faclImg.setFaclImgSeq((BigDecimal) sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "selectEzcFaclImgSeq")));
				txCount++; //sequnce transaction 
				//insert ( 신규로 다운받아야할 이미지 )
				txCount += sqlSession.insert(getNamespace("FACL_IMG_MAPPER", "insertEzcFaclImg"), faclImg);
				
				realtimeImageIO.setFaclImgSeq(faclImg.getFaclImgSeq());
				out.addCreateDownloadFileUrlList(realtimeImageIO);
			}
		
		}
		catch(Exception e) {
			logger.error("DB저장 및 다운로드할 이미지 데이터 필터 장애발생", e);
			/** Batch Error Report */
			//여기
			
		}
	}
	
	
	@APIOperation(description="삭제하거나 다운로드 재시도할 이미지 데이터 필터")
	private void deleteAndReDownloadImageFilter(AllRegOutSDO out, EzcFacl ezcFacl, List<EzcFaclImg> ezcFaclImgDBList, Integer txCount) {
		
		EzcFaclImg inEzcFaclImg = null;
		AllRegDataRealtimeImageOutSDO realtimeImageIO = null;
		
		try { 
		
			for(EzcFaclImg dbImage : ezcFaclImgDBList) {
				
				if(!dbImage.isPassImage()) {
					
					logger.debug("- 삭제 실행 dbImage URL : {}", dbImage);
					
					//삭제 대상 이미지
					inEzcFaclImg = new EzcFaclImg();
					inEzcFaclImg.setFaclCd(dbImage.getFaclCd());								
					inEzcFaclImg.setPartnerImgUrl(dbImage.getPartnerImgUrl());
					
					//삭제 대상 이미지 전체 경로
					if(APIUtil.isNotEmpty(dbImage.getImgUrl())) {
						out.addDeleteDownloadFilePathList(APIUtil.getImageCanonicalPath(dbImage.getImgUrl()));
					}
					
					//삭제
					txCount += sqlSession.delete(getNamespace("FACL_IMG_MAPPER", "deleteEzcFaclImg"), inEzcFaclImg);
				}
				else {
					
					//중복 이미지중 다운로드 실패 이력의 이미지일경우 다운로드 재시도 
					if(CodeDataConstants.CD_N.equals(dbImage.getImgCletYn()) || APIUtil.isEmpty(dbImage.getImgCletYn())) {
						logger.info("- 이전 다운로드 실패 이미지로 다운로드 재시도 URL : {}", dbImage.getPartnerImgUrl());
						//DB에 저장은되어있지만 이전 다운로드가 실패한 이미지는 다시 다운로드를 시도한다.
						realtimeImageIO = new AllRegDataRealtimeImageOutSDO();
						realtimeImageIO.setPartnerImgUrl(dbImage.getPartnerImgUrl());
						realtimeImageIO.setPartnerCd(ezcFacl.getPartnerCd());
						realtimeImageIO.setCityCd(ezcFacl.getCityCd());
						realtimeImageIO.setAreaCd(ezcFacl.getAreaCd());
						realtimeImageIO.setFaclCd(dbImage.getFaclCd());
						realtimeImageIO.setFaclImgSeq(dbImage.getFaclImgSeq()); //기존의 시설이미지SEQ
						
						out.addCreateDownloadFileUrlList(realtimeImageIO);
					}
					else {
						//이미 수집에 성공한 이미지는 수정자/일시만 갱신
						//트렌젝션 개수 감소의 이유로 실행하지 않음
						//txCount += sqlSession.update(getNamespace("FACL_IMG_MAPPER", "updateEzcFaclImgIsPass"), faclImg);
						logger.info("- 패스 이미지 파트너코드(에이젼트코드) : {}, 시설코드 : {}, URL : {}", ezcFacl.getPartnerCd(), dbImage.getFaclCd(), dbImage.getPartnerImgUrl());
					}
				}
			}
		}
		catch(Exception e) {
			logger.error("삭제하거나 다운로드 재시도할 이미지 데이터 필터 장애발생", e);
			/** Batch Error Report */
			//여기
			
			
			
		}
	}
	
	
	@APIOperation(description="전문의 시설정보에 이미지가 1개도 없을경우모두 삭제 및 파일삭제 대상 경로 세팅")
	private void deleteAllFaclImg(AllRegOutSDO out, EzcFacl ezcFacl, List<EzcFaclImg> ezcFaclImgDBList, Integer txCount) {
		
		EzcFaclImg inEzcFaclImg = null;
		
		try {
		
			//제휴사 전문에서 전달된 이미지 정보가 없으면 기존 이미지 삭제
			if(ezcFaclImgDBList != null) {
				
				logger.info("- 제휴사 전문에서 전달된 이미지 정보가 없으면 기존 이미지 삭제 : {}", ezcFaclImgDBList);
				
				inEzcFaclImg = new EzcFaclImg();
				inEzcFaclImg.setFaclCd(ezcFacl.getFaclCd());
				txCount += sqlSession.delete(getNamespace("FACL_IMG_MAPPER", "deleteEzcFaclImg"), inEzcFaclImg);
				
				for(EzcFaclImg deleteImage : ezcFaclImgDBList) {
					//삭제 대상 이미지 전체 경로
					if(APIUtil.isNotEmpty(deleteImage.getImgUrl())) {
						out.addDeleteDownloadFilePathList(APIUtil.getImageCanonicalPath(deleteImage.getImgUrl()));
					}
				}
			}
		}
		catch(Exception e) {
			logger.error("전문의 시설정보에 이미지가 1개도 없을경우모두 삭제 및 파일삭제 대상 경로 세팅 장애발생", e);
			/** Batch Error Report */
			//여기
			
			
			
		}
	}
	
	
	@APIOperation(description="부대시설 데이터 저장")
	private void saveFaclAmentList(EzcFacl ezcFacl, Integer txCount) {
		
		EzcFaclAment ezcFaclAment = null;
		
		try {
		
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
		catch(Exception e) {
			logger.error("부대시설 데이터 저장 장애발생", e);
			/** Batch Error Report */
			//여기
			
		}
	}
	
	
	
	
	@APIOperation(description="전문에서 삭제된 시설정보 사용안함 처리")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public Integer updateRemoveFacl(EzcFacl ezcFacl, boolean isErrorPassed) {
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		
		try {
			
			txCount = sqlSession.update(getNamespace("FACL_MAPPER", "updateRemoveFacl"), ezcFacl);
		}
		catch(Exception e) {
			
			if(isErrorPassed) {
				
				/*****************************
				 * [START] DB LOG DATA SETTING 
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("updateRemoveFacl"));
				apiBatcLogSDO.setBatcDesc("전문에서 삭제된 시설정보 사용안함 처리 저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_ER);
				apiBatcLogSDO.setErrCont(new StringBuffer()
						.append( "::전문에서 삭제된 시설정보 사용안함 처리 장애 발생시각 : " )
						.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러발생 시설 : " )
						.append( ezcFacl )
						.toString());
				
				Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
				/*****************************
				 * [END]   DB LOG DATA SETTING 
				 *****************************/
				
				logger.error(APIUtil.formatMessage("- 전문에서 삭제된 시설정보 사용안함 처리 장애발생 =>> 에러발생 시설 : {}", ezcFacl), e);
			}
			else {
				throw new APIException("전문에서 삭제된 시설정보 사용안함 처리 실패 {}", new Object[] {e.getMessage()}, e) ;
			}
		}

		return txCount;
	}
	
	
	@Transactional(readOnly=true)
	@APIOperation(description="시설 이미지 조회")
	public List<EzcFaclImg> selectListBuildImage(EzcFaclImg ezcFaclImg) {
		logger.debug("[START] selectListBuildImage");
		
		List<EzcFaclImg> ezcFaclImgList = null;
		try {
		
			ezcFaclImgList = sqlSession.selectList(getNamespace("FACL_IMG_MAPPER", "selectListEzcFaclImg"), ezcFaclImg);
		}
		catch(Exception e) {
			throw new APIException("이미지 다운르드 경로 DB 저장 실패 {}", new Object[] {e.getMessage()}, e) ; 
		}

		logger.debug("[END] selectListBuildImage");
		return ezcFaclImgList;
	}
	

	
	@APIOperation(description="시설 매핑 데이터 저장")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public Integer mergeFaclMappingGrpData(List<EzcFacl> finalFaclList, Integer fromIndex, Integer toIndex, boolean isErrorPassed, TransactionOutSDO txOut) {
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		EzcMappingGrpFacl ezcMappingGrpFacl = null;
		EzcMappingGrpFacl dbMappingGrpFacl = null;
		BigDecimal grpFaclCdSeq = null;
		EzcFacl ezcFacl = null;
		
		try {
			
			Integer groupRowCnt = (Integer) txOut.getProfileMap("매핑그룹시설 개수 groupRowCnt");
			if(groupRowCnt == null) {
				groupRowCnt = 0;
			}
			
			logger.debug("$finalFaclList size : {}" , finalFaclList.size());

			//그룹 데이터만 저장
			for(int i = 0; i < finalFaclList.size(); i++) {
				ezcFacl = finalFaclList.get(i);
				
				logger.debug("# FaclCd( {} ) : {}, Prnt : {}, 자식 개수 : {}", ezcFacl.isGroupData(), ezcFacl.getFaclCd(), ezcFacl.getPrntFaclCd(), (ezcFacl.getEzcFaclMappingList() != null ? ezcFacl.getEzcFaclMappingList().size() : 0));
				if(!ezcFacl.isGroupData()) {
					logger.debug("#그룹 제외 데이터 {}", ezcFacl);
					continue;
				}
				
				//1. EZC 매핑 그룹 시설 DB 저장
				ezcMappingGrpFacl = (EzcMappingGrpFacl) propertyUtil.copySameProperty(ezcFacl, EzcMappingGrpFacl.class);
				
				logger.debug("::맵핑 그룹 시설 파라메터 : {}", ezcMappingGrpFacl);
				
				//맵핑 그룹 시설 조회
				dbMappingGrpFacl = sqlSession.selectOne(getNamespace("MAPPING_GRP_FACL_MAPPER", "selectEzcGrpFacl"), ezcMappingGrpFacl);
				
				if(dbMappingGrpFacl != null) {
					//맵핑 그룹 시설 갱신
					ezcMappingGrpFacl.setGrpFaclCd(dbMappingGrpFacl.getGrpFaclCd());
					txCount += sqlSession.update(getNamespace("MAPPING_GRP_FACL_MAPPER", "updateEzcMappingGrpFacl"), ezcMappingGrpFacl);
				}
				else {
					//맵핑 그룹 시설 입력
					grpFaclCdSeq = sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "selectEzcMappingGrpFaclSeq"));
					ezcMappingGrpFacl.setGrpFaclCd(grpFaclCdSeq);
					txCount += sqlSession.insert(getNamespace("MAPPING_GRP_FACL_MAPPER", "insertEzcMappingGrpFacl"), ezcMappingGrpFacl);
				}
				
				ezcFacl.setGrpFaclCd(ezcMappingGrpFacl.getGrpFaclCd());
				groupRowCnt++;
			}
			
			logger.debug("매핑그룹시설 개수 groupRowCnt : {}", groupRowCnt);
			txOut.addProfileMap("매핑그룹시설 개수 groupRowCnt", groupRowCnt);
		}
		catch(Exception e) {
			
			//에러 발생 레코드 errorItems에 저장후 runtimeException 없이 로깅후 종료
			if(isErrorPassed) {
				
				if(e.getMessage().indexOf("EZ_CHECKIN.UK_EZC_MAPPING_FACL_01") > -1) {
					
					//EzcMappingFacl
					List<EzcMappingGrpFacl> ezcMappingGrpFaclErrorData = (List<EzcMappingGrpFacl>) txOut.getProfileMap("ezcMappingGrpFaclErrorData");
					if(ezcMappingGrpFaclErrorData == null) {
						ezcMappingGrpFaclErrorData = new ArrayList<EzcMappingGrpFacl>();
					}
					ezcMappingGrpFaclErrorData.add(ezcMappingGrpFacl);				
					
					txOut.addProfileMap("ezcMappingGrpFaclErrorData", ezcMappingGrpFaclErrorData);
				}
				
				/*****************************
				 * [START] DB LOG DATA SETTING 
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("mergeFaclMappingGrpData"));
				apiBatcLogSDO.setBatcDesc("시설 매핑 데이터 저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_ER);
				apiBatcLogSDO.setErrCont(new StringBuffer()
						.append( "::시설 매핑 데이터 저장 장애 발생시각 : " )
						.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러발생 맵핑 그룹 시설 : " )
						.append( ezcMappingGrpFacl )
						.toString());
				
				Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
				/*****************************
				 * [END]   DB LOG DATA SETTING 
				 *****************************/
				
				logger.error(APIUtil.formatMessage("- 시설 매핑 데이터 저장 장애발생 =>> 에러발생 맵핑 그룹 시설 : {}", ezcMappingGrpFacl), e);
				
			}
			else {
				throw new APIException("시설 매핑 데이터 저장 장애발생 : {}", new Object[] {e.getMessage()}, e) ; 
			}
		}

		return txCount;
	}
	
	@APIOperation(description="시설 매핑 데이터 저장")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public Integer mergeFaclMappingData(List<EzcFaclMappingSDO> finalFaclList, Integer fromIndex, Integer toIndex, boolean isErrorPassed, TransactionOutSDO txOut) {
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		EzcFaclMappingSDO ezcFaclMappingSDO = null;
		EzcMappingFacl ezcMappingFacl = null;
		EzcMappingFacl dbMappingFacl = null;
		int i = 0;
		
		try {
			
			logger.debug("$finalFaclList size : {}" , finalFaclList.size());
							
			//2. EZC 매핑 시설 DB 저장
			for(i = 0; i < finalFaclList.size(); i++) {
				ezcFaclMappingSDO = finalFaclList.get(i); 
				
				ezcMappingFacl = (EzcMappingFacl) propertyUtil.copySameProperty(ezcFaclMappingSDO, EzcMappingFacl.class);
				
				logger.debug("::맵핑 시설 파라메터 : {}", ezcMappingFacl);
				
				//맵핑 그룹 시설 조회
				dbMappingFacl = sqlSession.selectOne(getNamespace("MAPPING_FACL_MAPPER", "selectEzcMappingFacl"), ezcMappingFacl);
				
				// 자동 매핑의 경우 N
				ezcMappingFacl.setHandMappingYn(CodeDataConstants.CD_N); //핸드매핑 아님
				
				if(dbMappingFacl != null) {
					
					if(CodeDataConstants.CD_Y.equals(dbMappingFacl.getHandMappingYn())) {
						// 관리자에서 수동 매핑한 정보는 변경하지 않는다.
						continue;
					}
					
					//맵핑 그룹 시설 갱신
					txCount = sqlSession.update(getNamespace("MAPPING_FACL_MAPPER", "updateEzcMappingFacl"), ezcMappingFacl);
				}
				else {
					//맵핑 그룹 시설 입력
					txCount = sqlSession.insert(getNamespace("MAPPING_FACL_MAPPER", "insertEzcMappingFacl"), ezcMappingFacl);
				}
			}
			
			Integer childCount = (Integer) (txOut.getProfileMap("자식시설 개수") != null ? txOut.getProfileMap("자식시설 개수") : 0);
			txOut.addProfileMap("자식시설 개수", finalFaclList.size() + childCount);
		}
		catch(Exception e) {
			
			//에러 발생 레코드 errorItems에 저장후 runtimeException 없이 로깅후 종료
			if(isErrorPassed) {
				
				if(e.getMessage().indexOf("EZ_CHECKIN.UK_EZC_MAPPING_FACL_01") > -1) {
					
					//EzcMappingFacl
					List<EzcMappingFacl> ezcFaclMappingErrorData = (List<EzcMappingFacl>) txOut.getProfileMap("ezcFaclMappingErrorData");
					if(ezcFaclMappingErrorData == null) {
						ezcFaclMappingErrorData = new ArrayList<EzcMappingFacl>();
					}
					ezcFaclMappingErrorData.add(ezcMappingFacl);				
					
					txOut.addProfileMap("ezcFaclMappingErrorData", ezcFaclMappingErrorData);
				}
				
				/*****************************
				 * [START] DB LOG DATA SETTING 
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("mergeFaclMappingData"));
				apiBatcLogSDO.setBatcDesc("시설 매핑 데이터 저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_ER);
				apiBatcLogSDO.setErrCont(new StringBuffer()
						.append( "::시설 매핑 데이터 저장 장애 발생시각 : " )
						.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러 발생 구간(index) from : " )
						.append( fromIndex )
						.append( " ~ to : " )
						.append( toIndex )
						.append( "중 " )
						.append( i )
						.append( "번째 인덱스 데이터" )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러발생 매핑 시설 : " )
						.append( ezcMappingFacl )
						.toString());
				
				Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
				/*****************************
				 * [END]   DB LOG DATA SETTING 
				 *****************************/
				
				logger.error(APIUtil.formatMessage("- 시설 매핑 데이터 저장 장애발생 =>> 에러발생 매핑 시설 : {}", ezcMappingFacl), e);
				
			}
			else {
				throw new APIException("시설 매핑 데이터 저장 장애발생 : {}", new Object[] {e.getMessage()}, e) ; 
			}
		}

		return txCount;
	}
	
	/**
	 * 시설의 매핑 그룹 코드(부모코드)를 찾아 세팅하고 형태소 및 좌표 매칭 정보를 세팅합니다.
	 * @param ezcFacl
	 * @param finalFaclList
	 * @param ezcMappingFacl
	 */
	@APIOperation(description="시설의 매핑 그룹 코드(부모코드)를 찾아 세팅하고 형태소 및 좌표 매칭 정보를 세팅합니다.")
	private void findFaclGroupCd(List<EzcFacl> finalFaclList, EzcMappingFacl ezcMappingFacl, boolean isErrorPassed) {
		
		try {
			
			for(EzcFacl innerEzcFacl : finalFaclList) {
				
				if(innerEzcFacl.isGroupData() && innerEzcFacl.getMatchMorpFaclCdList() != null && innerEzcFacl.getMatchMorpFaclCdList().contains(ezcMappingFacl.getFaclCd())) {
					
					//부모 그룹 코드 세팅
					ezcMappingFacl.setGrpFaclCd(innerEzcFacl.getGrpFaclCd());
					logger.debug("# ezcMappingFacl.getGrpFaclCd : {}", ezcMappingFacl.getGrpFaclCd());
					if(ezcMappingFacl.getGrpFaclCd() != null) {
						logger.debug("# Mapping facl size : {}", innerEzcFacl.getEzcFaclMappingList().size());
						for(EzcFaclMappingSDO faclMappingSDO : innerEzcFacl.getEzcFaclMappingList()) {
							/** 아래 3가지 정보를  innerEzcFacl.getEzcFaclMappingSDO() 에서 찾는다. */
							logger.debug("# Compare Mapping FaclCd ({} == {}) data : {}", ezcMappingFacl.getFaclCd(), faclMappingSDO.getFaclCd(), faclMappingSDO );
							if(faclMappingSDO.getFaclCd().compareTo(ezcMappingFacl.getFaclCd()) == 0) {
								ezcMappingFacl.setKorMorpMatcPcnt(faclMappingSDO.getKorMorpMatcPcnt());
								ezcMappingFacl.setEngMorpMatcPcnt(faclMappingSDO.getEngMorpMatcPcnt());
								ezcMappingFacl.setCordDist(faclMappingSDO.getCordDist());
								break;
							}
						}
					}
					else {
						logger.error(" 그룹 시설 객체에 그룹시설코드가 세팅되지 않았습니다!! : {}", ezcMappingFacl.getFaclCd());
					}
					
					break;
				}
			}
			
			logger.debug("== >> Parent facility FaclCd : {}, GrpFaclCd : {}", ezcMappingFacl.getFaclCd(), ezcMappingFacl.getGrpFaclCd());
		}
		catch(Exception e) {
			
			if(isErrorPassed) {
				
				/*****************************
				 * [START] DB LOG DATA SETTING 
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("findFaclGroupCd"));
				apiBatcLogSDO.setBatcDesc("시설의 매핑 그룹 부모코드 스캔, 형태소 및 좌표 매칭 정보 세팅");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_ER);
				apiBatcLogSDO.setErrCont(new StringBuffer()
						.append( "::시설의 매핑 그룹 부모코드 스캔, 형태소 및 좌표 매칭 정보 세팅 장애 발생시각 : " )
						.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러발생 매핑 시설 : " )
						.append( ezcMappingFacl )
						.toString());
				
				Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
				/*****************************
				 * [END]   DB LOG DATA SETTING 
				 *****************************/
				
				logger.error(APIUtil.formatMessage("- 시설의 매핑 그룹 부모코드 스캔, 형태소 및 좌표 매칭 정보 세팅 장애발생 =>> 에러발생 매핑 시설 : {}", ezcMappingFacl), e);				
			}
			else {
				throw new APIException("시설 그룹 코드(부모코드)검색 도중 장애발생 : {}", new Object[] {e.getMessage()}, e) ;
			}
		}
	}
	
	
	@APIOperation(description="전체시설 이미지 다운로드 경로 단건 저장")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public Integer updateBuildImage(List<EzcFaclImg> finalFaclImgList, boolean isErrorPassed) {
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		EzcFaclImg ezcFaclImg = null;
		try {
			
			for(int i = 0; i < finalFaclImgList.size(); i++) {
				ezcFaclImg = finalFaclImgList.get(i);
				txCount = sqlSession.update(getNamespace("FACL_IMG_MAPPER", "updateEzcFaclImgDownload"), ezcFaclImg);
			}
		}
		catch(Exception e) {
			
			//에러 발생 레코드 errorItems에 저장후 runtimeException 없이 로깅후 종료
			if(isErrorPassed) {
				
				/*****************************
				 * [START] DB LOG DATA SETTING 
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("updateBuildImage"));
				apiBatcLogSDO.setBatcDesc("전체시설 이미지 다운로드 경로 저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_ER);
				apiBatcLogSDO.setErrCont(new StringBuffer()
						.append( "::전체시설 이미지 다운로드 경로 저장 장애 발생시각 : " )
						.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러발생 시설 이미지 : " )
						.append( ezcFaclImg )
						.toString());
				
				Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
				/*****************************
				 * [END]   DB LOG DATA SETTING 
				 *****************************/
				
				logger.error(APIUtil.formatMessage("- 전체시설 이미지 다운로드 경로 저장 장애발생 =>> 에러발생 시설 이미지 : {}", ezcFaclImg), e);
				
				
			}
			else {
				throw new APIException("이미지 다운르드 경로 DB 저장 실패 {}", new Object[] {e.getMessage()}, e) ; 
			}
		}

		return txCount;
	}
	
	@Transactional(readOnly=true)
	@APIOperation(description="시설 타입 그룹 목록 조회")
	public List<EzcFacl> selectFaclCodeGroupList(EzcFacl ezcFacl) {
			
		List<EzcFacl> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("FACL_MAPPER", "selectFaclCodeGroupList"), ezcFacl);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설 타입 그룹 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	@Transactional(readOnly=true)
	@APIOperation(description="타입 그룹 별 시설 형태소 행 목록(형태소 열을 행으로 변환한 목록)")
	public List<EzcFacl> selectFaclMappingMorpDataList(EzcFacl ezcFacl) {
			
		List<EzcFacl> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("FACL_MAPPER", "selectFaclMappingMorpDataList"), ezcFacl);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "타입 그룹 별 시설 형태소 행 목록(형태소 열을 행으로 변환한 목록)조회 장애발생.", e);
		}
			
		return out;
	}
	
	@Transactional(readOnly=true)
	@APIOperation(description="타입 그룹 별 시설 형태소 행 목록(형태소 열을 행으로 변환한 목록)")
	public List<EzcFacl> selectFaclMappingMorpRowData(EzcFacl ezcFacl) {
			
		List<EzcFacl> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("FACL_MAPPER", "selectFaclMappingMorpRowData"), ezcFacl);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "타입 그룹 별 시설 형태소 행 목록(형태소 열을 행으로 변환한 목록)조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	@Transactional(readOnly=true)
	@APIOperation(description="시도/지역 그룹 목록 조회")
	public List<EzcFacl> selectCityAreaGroupList(EzcFacl ezcFacl) {
			
		List<EzcFacl> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("FACL_MAPPER", "selectCityAreaGroupList"), ezcFacl);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	
	/**
	 * 멀티쓰레드
	 * EZC_CACHE_MIN_AMT 데이터 적제
	 * input = 
	 * @param userAgentDTO
	 * @param faclSearchDTO
	 * @return
	 */
	@APIOperation(description="시설검색 인터페이스(최저가 정보)저장")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public Integer callFaclSearch(FaclSearchInSDO faclSearchDTO, FaclSearchOutSDO datas, boolean isErrorPassed) {
			
		EzcCacheMinAmt inCacheMinAmt = null;
		EzcCacheMinAmt outCacheMinAmt = null;
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		
		try {
					
			for(FaclSearchDataOutSDO data : datas.getData()) {
				//logger.debug("$data : {} ", data);
				
				inCacheMinAmt = new EzcCacheMinAmt();
				inCacheMinAmt.setPartnerGoodsCd(data.getPdtNo()); //상품코드
				inCacheMinAmt.setPartnerCd(new BigDecimal(datas.getHttpAgentId())); //제휴사코드
				
				outCacheMinAmt = sqlSession.selectOne(getNamespace("CACHE_MIN_AMT_MAPPER", "selectEzcPartnerGoodsMinAmt"), inCacheMinAmt);
				
				if(outCacheMinAmt != null) {
					outCacheMinAmt.setCheckInDd(faclSearchDTO.getCheckInDate());	//증가
					outCacheMinAmt.setCheckOutDd(faclSearchDTO.getCheckOutDate()); //증가
					outCacheMinAmt.setRoomMinPrice(new BigDecimal(data.getSellPrice())); //객실 최저가 전문
					outCacheMinAmt.setSpRoomMinPrice(new BigDecimal(data.getSpcPrice())); //특가최저가 전문
					outCacheMinAmt.setRoomNetPrice(new BigDecimal(data.getSellNorPrice())); //객실 정상가 전문
					outCacheMinAmt.setSpRoomNetPrice(new BigDecimal(data.getSpcNorPrice())); //특가 정상가 전문
					
					txCount += sqlSession.update(getNamespace("CACHE_MIN_AMT_MAPPER", "mergeEzcCacheMinAmt"), outCacheMinAmt);
				}
				else {
					logger.warn("- 제휴사코드 {}의 상품코드 {}에 해당하는 그룹시설정보가 존재하지 않습니다.", datas.getHttpAgentId(), data.getPdtNo());
				}
			}
		}
		catch(Exception e) {
			
			if(isErrorPassed) {

				/*****************************
				 * [START] DB LOG DATA SETTING 
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("callFaclSearch"));
				apiBatcLogSDO.setBatcDesc("시설검색 인터페이스(최저가 정보)저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_ER);
				apiBatcLogSDO.setErrCont(new StringBuffer()
						.append( "::시설검색 인터페이스(최저가 정보)저장 장애 발생시각 : " )
						.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러발생 최저가 정보 : " )
						.append( outCacheMinAmt )
						.toString());
				
				Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
				/*****************************
				 * [END]   DB LOG DATA SETTING 
				 *****************************/
				
				logger.error(APIUtil.formatMessage("- 시설검색 인터페이스(최저가 정보)저장 장애발생 =>> 에러발생 최저가 정보 : {}", outCacheMinAmt), e);
			}
			else {
				throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색(최저가 정보 저장) 장애발생.", e);
			}
		}
			
		return txCount;
	}
	
	/**
	 * EZC_CACHE_DAY_PRICE 데이터 적제  ( 맵핑시설 테이블에 존재하는 시설의 당일특가만 적제 가능 )
	 * @param userAgentDTO
	 * @return 
	 */
	@APIOperation(description="당일특가검색 저장")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public Integer callSddSearch(SddSearchOutSDO assets, boolean isErrorPassed) {
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		EzcCacheDayPrice inCacheDayPrice = null;
		EzcCacheDayPrice outCacheDayPrice = null;
		
		try {
			
			for(SddSearchDataOutSDO data : assets.getData()) {
				
				// EzcCacheDayPrice
				inCacheDayPrice = new EzcCacheDayPrice();
				inCacheDayPrice.setPartnerCd(new BigDecimal(data.getHttpAgentId())); //제휴사 코드
				inCacheDayPrice.setPartnerGoodsCd(data.getPdtNo()); //제휴사 상품코드
				inCacheDayPrice.setDayPriceDd(data.getApplyDate()); //당일특가 일자
				inCacheDayPrice.setDayPriceNetPrice(data.getSpcTodayNorPrice()); //당일특가 정상가
				inCacheDayPrice.setDayPriceMinPrice(new BigDecimal(data.getSpcTodayPrice())); //당일특가 최저가
				inCacheDayPrice.setSaleEndDt(data.getSpcTypeTime()); // 판매 종료 시분 (12)
				
				outCacheDayPrice = sqlSession.selectOne(getNamespace("CACHE_DAY_PRICE_MAPPER", "selectEzcPartnerGoodsDayPrice"), inCacheDayPrice);
				
				txCount += sqlSession.update(getNamespace("CACHE_DAY_PRICE_MAPPER", "mergeEzcCacheDayPrice"), outCacheDayPrice);
			}
		}
		catch(Exception e) {
			
			if(isErrorPassed) {

				/*****************************
				 * [START] DB LOG DATA SETTING 
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("callSddSearch"));
				apiBatcLogSDO.setBatcDesc("당일특가검색 저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_ER);
				apiBatcLogSDO.setErrCont(new StringBuffer()
						.append( "::당일특가검색 저장 장애 발생시각 : " )
						.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
						.append( OperateConstants.LINE_SEPARATOR )
						.append( "- 에러발생 최저가 정보 : " )
						.append( outCacheDayPrice )
						.toString());
				
				Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
				/*****************************
				 * [END]   DB LOG DATA SETTING 
				 *****************************/
				
				logger.error(APIUtil.formatMessage("- 당일특가검색 저장 장애발생 =>> 에러발생 당일특가검색 저장 : {}", outCacheDayPrice), e);
			}
			else {
				throw new APIException(MessageConstants.RESPONSE_CODE_9100, "당일특가검색 저장 인터페이스 장애발생.", e);
			}
			
		}
		
		return txCount;
	}
	
	@Transactional(readOnly=true)
	@APIOperation(description="그룹시설코드 기준 시설목록검색")
	public List<EzcFacl> selectRoomReadFaclList(EzcFacl inEzcFacl) {
		
		List<EzcFacl> out = null;
		
		try {
			
			out = sqlSession.selectList(getNamespace("FACL_MAPPER", "selectRoomReadFaclList"), inEzcFacl);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "그룹시설코드 기준 시설목록검색 장애발생", e);
		}
		
		return out;
	}
	
	
}
