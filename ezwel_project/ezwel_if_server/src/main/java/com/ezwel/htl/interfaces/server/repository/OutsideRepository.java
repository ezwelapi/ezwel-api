package com.ezwel.htl.interfaces.server.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.sdo.EzcFaclMappingSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.ExceptionUtil;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcFaclAment;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.server.entities.EzcMappingFacl;
import com.ezwel.htl.interfaces.server.entities.EzcMappingGrpFacl;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataRealtimeImageOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
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
	
	private PropertyUtil propertyUtil;
	
	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public AllRegOutSDO insertAllReg(AllRegOutSDO out, List<EzcFacl> saveFaclRegDatas, Integer fromIndex, Integer toIndex, boolean isErrorPassed) {
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		Integer txCount = 0;
		Integer txSuccess = 0;
		List<EzcFaclImg> ezcFaclImgList = null;
		List<EzcFaclImg> ezcFaclImgDBList = null;
		EzcFaclAment ezcFaclAment = null;
		BigDecimal faclCdSeq = null;
		EzcFacl inEzcFacl = null;
		EzcFacl outEzcFacl = null;
		EzcFacl ezcFacl = null;
		EzcFaclImg inEzcFaclImg = null;
		Integer i = 0;
		EzcFaclImg savedImageDB = null;
		AllRegDataRealtimeImageOutSDO realtimeImageIO = null;
		Integer imgIdx = OperateConstants.INTEGER_MINUS_ONE; 
		
		try {
			
			for(i = 0; i < saveFaclRegDatas.size(); i++) {
				// 시설 정보 전문 데이터
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
					
					/** 2. EZC_FACL_IMG N건 저장 : 전문 시설 이미지 목록 */
					ezcFaclImgList = ezcFacl.getEzcFaclImgList();
					
					inEzcFaclImg = new EzcFaclImg();
					inEzcFaclImg.setFaclCd(ezcFacl.getFaclCd());
					propertyUtil.removeDefaulFieldData(inEzcFaclImg);
					
					//기존 저장된 이미지 정보 조회
					ezcFaclImgDBList = sqlSession.selectList(getNamespace("FACL_IMG_MAPPER", "selectListPartnerImgInfo"), inEzcFaclImg);
					
					if(ezcFaclImgList != null && ezcFaclImgList.size() > 0) {
						/** 
						 * EZC_FACL_IMG 테이블에는 KEY가 FACL_IMG_SEQ, FACL_CD 두개인데 FACL_CD는 같은 데이터가 N개저장되어있고
						 * FACL_IMG_SEQ는 DB테이블을 조회하기전에 알수 없는 불완전한 설계 상태임으로 merge문을 실행할 수 있는 환경이 되지 못함\
						 * 그럼으로 상단의 faclCd에 해당하는 이미지 데이터를 모두 삭제후 새로 insert함
						 **/

						logger.info("# image db original data size : {}", (ezcFaclImgDBList != null ? ezcFaclImgDBList.size() : 0));
						// 전문 시설 이미지 목록 loop
						for(EzcFaclImg faclImg : ezcFaclImgList) {
							//시설이미지 객체에 시설 코드 세팅
							faclImg.setFaclCd(ezcFacl.getFaclCd());
							
							savedImageDB = null;
							imgIdx = OperateConstants.INTEGER_MINUS_ONE; 
							//이미 동일한 이미지 URL이 저장되어있는 지 체크
							if(ezcFaclImgDBList != null) {
								//for(AllRegDataRealtimeImageOutSDO imageDB : ezcFaclImgDBList) {
								for(imgIdx = 0; imgIdx < ezcFaclImgDBList.size(); imgIdx++) {
									savedImageDB = ezcFaclImgDBList.get(imgIdx);
									//logger.info("# image check FaclCd : '{}' equals '{}', PartnerImgUrl : '{}' equals '{}'", savedImageDB.getFaclCd(), ezcFacl.getFaclCd(), savedImageDB.getPartnerImgUrl(), faclImg.getPartnerImgUrl());
									if(savedImageDB.getFaclCd().equals(ezcFacl.getFaclCd()) 
										&& savedImageDB.getPartnerImgUrl().equals(faclImg.getPartnerImgUrl())) {
										
										logger.info("# image db equals image url : {}", faclImg.getPartnerImgUrl());
										break;
									}
								}
							}
							
							//DB에 존재하지 않는 이미지 URL이면 입력
							if(savedImageDB == null) {
								//저장되지 않은 신규 이미지 이면 입력
								logger.info("- 입력준비 : {}", faclImg.getPartnerImgUrl());
								
								//신규로 다운받아야할 이미지
								realtimeImageIO = new AllRegDataRealtimeImageOutSDO();
								realtimeImageIO.setPartnerImgUrl(faclImg.getPartnerImgUrl());
								realtimeImageIO.setPartnerCd(ezcFacl.getPartnerCd());
								realtimeImageIO.setCityCd(ezcFacl.getCityCd());
								realtimeImageIO.setAreaCd(ezcFacl.getAreaCd());
								realtimeImageIO.setFaclCd(ezcFacl.getFaclCd());

								//중복 체크
								//if(out.isNewDownloadFile(faclImg.getFaclCd(), faclImg.getPartnerImgUrl())) {
									
									logger.info("- 입력실행 신규 URL : {}", faclImg.getPartnerImgUrl());
									//sequnce
									faclImg.setFaclImgSeq((BigDecimal) sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "selectEzcFaclImgSeq")));
									txCount++; //sequnce transaction 
									//insert ( 신규로 다운받아야할 이미지 )
									txCount += sqlSession.insert(getNamespace("FACL_IMG_MAPPER", "insertEzcFaclImg"), faclImg);
									
									realtimeImageIO.setFaclImgSeq(faclImg.getFaclImgSeq());
									out.addCreateDownloadFileUrlList(realtimeImageIO);
								//}
							}
							else {
								
								savedImageDB.setPassImage(true);
								ezcFaclImgDBList.set(imgIdx, savedImageDB);
								
								if(CodeDataConstants.CD_N.equals(savedImageDB.getImgCletYn()) || APIUtil.isEmpty(savedImageDB.getImgCletYn())) {
									logger.info("- 이전 다운로드 실패 이미지로 다운로드 재시도 URL : {}", faclImg.getPartnerImgUrl());
									//DB에 저장은되어있지만 이전 다운로드가 실패한 이미지는 다시 다운로드를 시도한다.
									realtimeImageIO = new AllRegDataRealtimeImageOutSDO();
									realtimeImageIO.setPartnerImgUrl(faclImg.getPartnerImgUrl());
									realtimeImageIO.setPartnerCd(ezcFacl.getPartnerCd());
									realtimeImageIO.setCityCd(ezcFacl.getCityCd());
									realtimeImageIO.setAreaCd(ezcFacl.getAreaCd());
									realtimeImageIO.setFaclImgSeq(faclImg.getFaclImgSeq());
									realtimeImageIO.setFaclCd(faclImg.getFaclCd());
									
									out.addCreateDownloadFileUrlList(realtimeImageIO);
								}
								else {
									//이미 수집에 성공한 이미지는 수정자/일시만 갱신
									//트렌젝션 개수 감소의 이유로 실행하지 않음
									//txCount += sqlSession.update(getNamespace("FACL_IMG_MAPPER", "updateEzcFaclImgIsPass"), faclImg);
									logger.info("- 패스 이미지 파트너코드(에이젼트코드) : {}, 시설코드 : {}, URL : {}", ezcFacl.getPartnerCd(), faclImg.getFaclCd(), faclImg.getPartnerImgUrl());
								}
							}
						}
						
						if(ezcFaclImgDBList != null) {
							
							for(EzcFaclImg deleteImage : ezcFaclImgDBList) {
								
								if(!deleteImage.isPassImage()) {
									
									logger.info("- 삭제 실행 deleteImage URL : {}", deleteImage);
									
									//삭제 대상 이미지
									inEzcFaclImg = new EzcFaclImg();
									inEzcFaclImg.setFaclCd(deleteImage.getFaclCd());								
									inEzcFaclImg.setPartnerImgUrl(deleteImage.getPartnerImgUrl());
									
									//삭제 대상 이미지 전체 경로
									if(APIUtil.isNotEmpty(deleteImage.getImgUrl())) {
										out.addDeleteDownloadFilePathList(APIUtil.getImageCanonicalPath(deleteImage.getImgUrl()));
									}
									
									//삭제
									txCount += sqlSession.delete(getNamespace("FACL_IMG_MAPPER", "deleteEzcFaclImg"), inEzcFaclImg);
								}
							}
						}
					}
					else {

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
					
					logger.info("# fianl image filter list : {}", ezcFaclImgDBList);
					
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
			
			if(isErrorPassed) {
				
				exceptionUtil = (ExceptionUtil) LApplicationContext.getBean(exceptionUtil, ExceptionUtil.class);
				
				/** 에러 발생 레코드 interface batch error log file에 저장후 RuntimeException 없이 로깅후 종료 */
				exceptionUtil.writeBatchErrorLog("{}\n{}@{}\n에러 발생 구간(index) from : {} ~ to : {}\n에러 발생 시설 index : {}\n에이전트코드 : {}\n시설코드 : {}\n시설명(한글) : {}\n시설명(영문) : {}", 
						new Object[] {"[전체시설일괄등록 인터페이스 데이터 저장 장애발생]", this.getClass().getCanonicalName(), "insertAllReg", fromIndex, toIndex, i, ezcFacl.getPartnerCd(), ezcFacl.getPartnerGoodsCd(), ezcFacl.getFaclNmKor(), ezcFacl.getFaclNmEng()},
						new StringBuffer().append(this.getClass().getSimpleName()).append(OperateConstants.STR_AT).append("insertAllReg-").append(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT)).toString(), 
						e);
				
				logger.error("Code : {}", MessageConstants.RESPONSE_CODE_9500);
				logger.error("Message : {}", e.getMessage());
				e.getStackTrace();
			}
			else {
				throw new APIException("시설정보 DB 저장 실패 {}", new Object[] {e.getMessage()}, e) ;
			}
		}
		
		
		out.setTxCount(out.getTxCount() + txCount);
		
		return out;
	}	

	
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	@APIOperation(description="전문에서 삭제된 시설정보 사용안함 처리")
	public Integer updateRemoveFacl(EzcFacl ezcFacl, boolean isErrorPassed) {
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		
		try {
			
			txCount = sqlSession.update(getNamespace("FACL_MAPPER", "updateRemoveFacl"), ezcFacl);
		}
		catch(Exception e) {
			
			if(isErrorPassed) {
				
				exceptionUtil = (ExceptionUtil) LApplicationContext.getBean(exceptionUtil, ExceptionUtil.class);
				
				/** 에러 발생 레코드 interface batch error log file에 저장후 RuntimeException 없이 로깅후 종료 */
				exceptionUtil.writeBatchErrorLog("{}\n{}@{}\n에러 발생 객체 : {}", 
						new Object[] {"[전문에서 삭제된 시설정보 사용안함 처리 장애발생]", this.getClass().getCanonicalName(), "updateRemoveFacl", ezcFacl},
						new StringBuffer().append(this.getClass().getSimpleName()).append(OperateConstants.STR_AT).append("updateRemoveFacl-").append(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT)).toString(), 
						e);
				
				logger.error("[전문에서 삭제된 시설정보 사용안함 처리 장애 발생]\nCode : {}\nMessage : {}", MessageConstants.RESPONSE_CODE_9500, e.getMessage());
				e.getStackTrace();
				
			}
			else {
				throw new APIException("전문에서 삭제된 시설정보 사용안함 처리 실패 {}", new Object[] {e.getMessage()}, e) ;
			}
		}

		return txCount;
	}
	

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
	
	@Transactional(propagation=Propagation.REQUIRED)
	@APIOperation(description="전체시설 이미지 다운로드 경로 다건 저장")
	public Integer updateMultiBuildImage(List<EzcFaclImg> finalFaclImgList, boolean isErrorPassed) {
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		Integer dataIndex = OperateConstants.INTEGER_ZERO_VALUE;
		EzcFaclImg ezcFaclImg = null;
		List<Future<?>> futures = null;
		CallableExecutor executor = null;
		Callable<Integer> callable = null;
		
		try {
			
			if(finalFaclImgList != null) {
			
				executor = new CallableExecutor();
				executor.initThreadPool(10);				
				
				for(dataIndex = 0; dataIndex < finalFaclImgList.size(); dataIndex++) {
					ezcFaclImg = finalFaclImgList.get(dataIndex);
					//txCount += updateBuildImage(ezcFaclImg, isErrorPassed);
					
					callable = new MultiBuildImageUpdateRepository(ezcFaclImg, isErrorPassed);
					//설정된 멀티쓰레드 개수만큼 반복 실행 
					executor.addCall(callable);
				}
				
				futures = executor.getResult();
				if(futures != null) {
					
					for(Future<?> future : futures) {
						if(future.get() != null) {
							txCount += (Integer) future.get();
						}
					}
				}
			}
		}
		catch(Exception e) {
			if(isErrorPassed) {
				logger.error("전체시설 이미지 다운로드 경로 다건 저장 장애 : {}", e.getMessage());
			}
			else {
				throw new APIException("이미지 다운르드 경로 DB 다건 저장 실패 {}", new Object[] {e.getMessage()}, e) ;
			}
		}

		return txCount;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	@APIOperation(description="시설 매핑 데이터 저장")
	public Integer mergeFaclMappingData(List<EzcFacl> finalFaclList, Integer fromIndex, Integer toIndex, boolean isErrorPassed) {
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		EzcFacl ezcFacl = null;
		EzcMappingGrpFacl ezcMappingGrpFacl = null;
		EzcMappingGrpFacl dbMappingGrpFacl = null;
		
		EzcMappingFacl ezcMappingFacl = null;
		EzcMappingFacl dbMappingFacl = null;
		
		BigDecimal grpFaclCdSeq = null;
		
		try {
			
			for(int i = 0; i < finalFaclList.size(); i++) {
				ezcFacl = finalFaclList.get(i);
				
				//1. EZC 매핑 그룹 시설 DB 저장
				if(ezcFacl.isGroupData()) {
					ezcMappingGrpFacl = (EzcMappingGrpFacl) propertyUtil.copySameProperty(ezcFacl, EzcMappingGrpFacl.class);
					
					//맴핑 그룹 시설 조회
					dbMappingGrpFacl = sqlSession.selectOne(getNamespace("MAPPING_GRP_FACL_MAPPER", "selectEzcMappingGrpFacl"), ezcMappingGrpFacl);
					
					if(dbMappingGrpFacl != null) {
						//맴핑 그룹 시설 갱신
						ezcMappingGrpFacl.setGrpFaclCd(dbMappingGrpFacl.getGrpFaclCd());
						txCount = sqlSession.update(getNamespace("MAPPING_GRP_FACL_MAPPER", "updateEzcMappingGrpFacl"), ezcMappingGrpFacl);
					}
					else {
						//맴핑 그룹 시설 입력
						grpFaclCdSeq = sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "selectEzcMappingGrpFaclSeq"));
						ezcMappingGrpFacl.setGrpFaclCd(grpFaclCdSeq);
						txCount = sqlSession.insert(getNamespace("MAPPING_GRP_FACL_MAPPER", "insertEzcMappingGrpFacl"), ezcMappingGrpFacl);
					}
					
					ezcFacl.setGrpFaclCd(ezcMappingGrpFacl.getGrpFaclCd());
				}
				
				//2. EZC 매핑 시설 DB 저장
				ezcMappingFacl = (EzcMappingFacl) propertyUtil.copySameProperty(ezcFacl, EzcMappingFacl.class);
				// 그룹 코드를 찾음
				findFaclGroupCd(ezcFacl, finalFaclList, ezcMappingFacl);
				
				if(ezcMappingFacl.getGrpFaclCd() == null) {
					throw new APIException(" 시설 코드({})의 그룹 시설 코드를 찾을수 없습니다. !! : {}", ezcMappingFacl.getFaclCd()) ;
				}
				
				//맴핑 그룹 시설 조회
				dbMappingFacl = sqlSession.selectOne(getNamespace("MAPPING_FACL_MAPPER", "selectEzcMappingFacl"), ezcMappingFacl);
				
				// 자동 매핑의 경우 N
				ezcMappingFacl.setHandMappingYn(CodeDataConstants.CD_N); //핸드매핑 아님
				
				if(dbMappingFacl != null) {
					
					if(CodeDataConstants.CD_Y.equals(dbMappingFacl.getHandMappingYn())) {
						// 관리자에서 수동 매핑한 정보는 변경하지 않는다.
						continue;
					}
					
					//맴핑 그룹 시설 갱신
					txCount = sqlSession.update(getNamespace("MAPPING_FACL_MAPPER", "updateEzcMappingFacl"), ezcMappingFacl);
				}
				else {
					//맴핑 그룹 시설 입력
					txCount = sqlSession.insert(getNamespace("MAPPING_FACL_MAPPER", "insertEzcMappingFacl"), ezcMappingFacl);
				}
			}
		}
		catch(Exception e) {
			
			//에러 발생 레코드 errorItems에 저장후 runtimeException 없이 로깅후 종료
			if(isErrorPassed) {
				
				exceptionUtil = (ExceptionUtil) LApplicationContext.getBean(exceptionUtil, ExceptionUtil.class);
				
				/** 에러 발생 레코드 interface batch error log file에 저장후 RuntimeException 없이 로깅후 종료 */
				exceptionUtil.writeBatchErrorLog("{}\n{}@{}\n에러 발생 객체 : {}", 
						new Object[] {"[시설 매핑 데이터 저장 장애발생]", this.getClass().getCanonicalName(), "mergeFaclMappingData", ezcFacl},
						new StringBuffer().append(this.getClass().getSimpleName()).append(OperateConstants.STR_AT).append("mergeFaclMappingData-").append(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT)).toString(), 
						e);
				
				logger.error("Code : {}", MessageConstants.RESPONSE_CODE_9600);
				logger.error("Message : {}", e.getMessage());
				e.getStackTrace();
				
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
	private void findFaclGroupCd(EzcFacl ezcFacl, List<EzcFacl> finalFaclList, EzcMappingFacl ezcMappingFacl) {
		
		for(EzcFacl innerEzcFacl : finalFaclList) {
			
			if(ezcFacl.isGroupData() && innerEzcFacl.getMatchMorpFaclCdList().contains(ezcFacl.getFaclCd())) {
				
				if(innerEzcFacl.getGrpFaclCd() == null) {
					throw new APIException(" 그룹 시설 객체에 그룹시설코드가 세팅되지 않았습니다!! : {}", ezcFacl.getFaclCd());
				}
				//부모 그룹 코드 세팅
				ezcMappingFacl.setGrpFaclCd(innerEzcFacl.getGrpFaclCd());
				
				for(EzcFaclMappingSDO faclMappingSDO : innerEzcFacl.getEzcFaclMappingSDO()) {
					/** 아래 3가지 정보를  innerEzcFacl.getEzcFaclMappingSDO() 에서 찾는다. */
					if(faclMappingSDO.getFaclCd().compareTo(ezcFacl.getFaclCd()) == 0) {
						ezcMappingFacl.setKorMorpMatcPcnt(faclMappingSDO.getKorMorpEqualsPer());
						ezcMappingFacl.setEngMorpMatcPcnt(faclMappingSDO.getEngMorpEqualsPer());
						ezcMappingFacl.setCordDist(faclMappingSDO.getCordDist());
					}
				}

				break;
			}
		}
		
		logger.debug("== >> Parent facility {} a {}", ezcFacl.getFaclCd(), ezcMappingFacl.getGrpFaclCd());
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	@APIOperation(description="전체시설 이미지 다운로드 경로 단건 저장")
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
				
				exceptionUtil = (ExceptionUtil) LApplicationContext.getBean(exceptionUtil, ExceptionUtil.class);
				
				/** 에러 발생 레코드 interface batch error log file에 저장후 RuntimeException 없이 로깅후 종료 */
				exceptionUtil.writeBatchErrorLog("{}\n{}@{}\n에러 발생 객체 : {}", 
						new Object[] {"[전체시설 이미지 다운로드 경로 저장 장애발생]", this.getClass().getCanonicalName(), "updateBuildImage", ezcFaclImg},
						new StringBuffer().append(this.getClass().getSimpleName()).append(OperateConstants.STR_AT).append("updateBuildImage-").append(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT)).toString(), 
						e);
				
				logger.error("Code : {}", MessageConstants.RESPONSE_CODE_9500);
				logger.error("Message : {}", e.getMessage());
				e.getStackTrace();
				
			}
			else {
				throw new APIException("이미지 다운르드 경로 DB 저장 실패 {}", new Object[] {e.getMessage()}, e) ; 
			}
		}

		return txCount;
	}
	
	@APIOperation(description="시설 타입 그룹 목록 조회")
	public List<EzcFacl> selectFaclCodeGroupList(EzcFacl ezcFacl) {
			
		List<EzcFacl> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("FACL_MAPPER", "selectFaclCodeGroupList"), ezcFacl);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	@APIOperation(description="타입 그룹 별 시설 형태소 행 목록(형태소 열을 행으로 변환한 목록)")
	public List<EzcFacl> selectFaclMappingMorpDataList(EzcFacl ezcFacl) {
			
		List<EzcFacl> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("FACL_MAPPER", "selectFaclMappingMorpDataList"), ezcFacl);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	
	@APIOperation(description="타입 그룹 별 시설 형태소 행 목록(형태소 열을 행으로 변환한 목록)")
	public List<EzcFacl> selectFaclMappingMorpRowData(EzcFacl ezcFacl) {
			
		List<EzcFacl> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("FACL_MAPPER", "selectFaclMappingMorpRowData"), ezcFacl);
		}
		catch(APIException e) {
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
