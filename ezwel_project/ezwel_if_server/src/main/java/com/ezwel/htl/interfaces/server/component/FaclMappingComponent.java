package com.ezwel.htl.interfaces.server.component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.sdo.EzcFaclMappingSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CoordinateUtil;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;

@Component
@APIType(description="시설 매핑 컴포넌트")
public class FaclMappingComponent {

	private static final Logger logger = LoggerFactory.getLogger(FaclMappingComponent.class);
	
	private CoordinateUtil coordinateUtil;
	
	private PropertyUtil propertyUtil;
	
	@APIOperation(description="시설 정보 분석 매핑")
	public List<EzcFacl> getSearchForSameFacility(List<EzcFacl> faclMorpSearchList) throws Exception {
		
		coordinateUtil = (CoordinateUtil) LApplicationContext.getBean(coordinateUtil, CoordinateUtil.class);
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		List<EzcFacl> out = null;
		//비교 기준 객체
		EzcFacl faclMorp = null;
		String[] faclKorRootMorpArray = null;
		String[] faclEngRootMorpArray = null;
		Set<BigDecimal> childfaclCdFilter = null;

		//비교 대상 객체
		EzcFacl faclCompMorp = null;
		String[] faclKorCompareMorpArray = null;
		String[] faclEngCompareMorpArray = null;
		
		//비교 결과 객체
		int korMorpEqualsCount = OperateConstants.INTEGER_ZERO_VALUE;
		int engMorpEqualsCount = OperateConstants.INTEGER_ZERO_VALUE;
		boolean morpMatch = false;
		BigDecimal cordDist = null; //거리 차이 (m)
		int korEqualsIndex = OperateConstants.INTEGER_ZERO_VALUE;
		int engEqualsIndex = OperateConstants.INTEGER_ZERO_VALUE;
		BigDecimal korMorpMatcPer = OperateConstants.BIGDECIMAL_ZERO_VALUE;
		BigDecimal engMorpMatcPer = OperateConstants.BIGDECIMAL_ZERO_VALUE;
		EzcFaclMappingSDO ezcFaclMappingSDO = null;

		try {
			
			//결과 목록
			out = new ArrayList<EzcFacl>();
			
			if(faclMorpSearchList != null && faclMorpSearchList.size() > 0) {
				
				//비교기준 시설 정보
				for(int i = 0; i < faclMorpSearchList.size(); i++) {
					
					//비교기준
					faclMorp = faclMorpSearchList.get(i);
					// NOT NULL 데이터 NVL 처리 (전문 설계 문제, 테이블 설계도 문제로 들어가는 코드)
					faclMorp.setAddrType(APIUtil.NVL(faclMorp.getAddrType(), OperateConstants.STR_EMPTY));
					faclMorp.setAddr(APIUtil.NVL(faclMorp.getAddr(), OperateConstants.STR_EMPTY));
					faclMorp.setPost(APIUtil.NVL(faclMorp.getPost(), OperateConstants.STR_EMPTY));
					faclMorp.setFaclNmEng(APIUtil.NVL(faclMorp.getFaclNmEng(), OperateConstants.STR_EMPTY));
					faclMorp.setTelNum(APIUtil.NVL(faclMorp.getTelNum(), OperateConstants.STR_EMPTY));
					
					//국문
					faclKorRootMorpArray = faclMorp.getKorMorpArray();
					Arrays.sort(faclKorRootMorpArray);
					//영문
					faclEngRootMorpArray = faclMorp.getEngMorpArray();
					if(faclEngRootMorpArray != null) {
						Arrays.sort(faclEngRootMorpArray);
					}
					
					//logger.debug(" ### == >> PrntFaclCd : {}", faclMorp.getPrntFaclCd());
					
					if(faclMorp.getPrntFaclCd() == null) {
						
						logger.debug(" ### == >> [START FIND PARENT FACILITY] Name is {}", faclMorp.getFaclNmKor());
						
						//중복필터 초기화
						childfaclCdFilter = new HashSet<BigDecimal>();
						
						//비교대상 시설 정보
						for(int j = 0; j < faclMorpSearchList.size(); j++) {
							//초기화
							korMorpEqualsCount = OperateConstants.INTEGER_ZERO_VALUE;
							engMorpEqualsCount = OperateConstants.INTEGER_ZERO_VALUE;
							morpMatch = false;
							cordDist = null;
							
							//비교대상 값
							faclCompMorp = faclMorpSearchList.get(j);
							
							/*
							if((faclMorp.getPartnerCd().equals(faclCompMorp.getPartnerCd()))) {
								logger.debug("[PASS] 동일한 제휴사는 패스합니다. PartnerCd : {}.equals({})", faclMorp.getPartnerCd(), faclCompMorp.getPartnerCd());
								continue;
							}
							*/
							
							//같은 제휴사 또는 시설일경우 패스 (제휴사 잠시 주석)
							if((faclMorp.getFaclCd().compareTo(faclCompMorp.getFaclCd()) == 0)) {
								logger.debug("[PASS] 동일한 시설은 패스합니다. FaclCd : {}.compareTo({})", faclMorp.getFaclCd(), faclCompMorp.getFaclCd());
								continue;
							}

							if(faclCompMorp.isGroupData()) {
								logger.debug("이미 그룹데이터로 선정되고 자식시설이있는 시설입니다. EzcFaclMappingSDO \n=> faclMorp {}\n=> faclCompMorp {}", faclMorp.getEzcFaclMappingSDO(), faclCompMorp.getEzcFaclMappingSDO());
								continue;
							}
							
							if(faclCompMorp.getPrntFaclCd() != null) {
								logger.debug("[PASS] 이미 그룹에 속한 시설은 패스합니다. PrntFaclCd => faclMorp '{}', faclCompMorp '{}'", faclMorp.getPrntFaclCd(), faclCompMorp.getPrntFaclCd());
								continue;
							}
							
							
							//국문
							faclKorCompareMorpArray = faclCompMorp.getKorMorpArray(); 
							Arrays.sort(faclKorCompareMorpArray);
							//영문
							faclEngCompareMorpArray = faclCompMorp.getEngMorpArray(); 
							if(faclEngCompareMorpArray != null && faclEngRootMorpArray != null) {
								Arrays.sort(faclEngCompareMorpArray);
							}
							
							//국문 형태소 탐색
							for(String rootMorp : faclKorRootMorpArray) {
								korEqualsIndex = Arrays.binarySearch(faclKorCompareMorpArray, rootMorp);
								//logger.debug("[국문 형태소 탐색:{}, length: {}] index : {} / {} : {}", faclMorp.getFaclCd(), faclKorRootMorpArray.length, korEqualsIndex, rootMorp, faclKorCompareMorpArray);
								if(korEqualsIndex > -1) {
									korMorpEqualsCount++;
								}
							} 
							
							if(faclEngCompareMorpArray != null && faclEngRootMorpArray != null) {
								//영문 형태소 탐색
								for(String rootMorp : faclEngRootMorpArray) {
									engEqualsIndex = Arrays.binarySearch(faclEngCompareMorpArray, rootMorp);
									//logger.debug("[영문 형태소 탐색:{}, length: {}] index : {} / {} : {}", faclMorp.getFaclCd(), faclEngRootMorpArray.length, engEqualsIndex, rootMorp, faclEngRootMorpArray);
									if(engEqualsIndex > -1) {
										engMorpEqualsCount++;
									}
								} 
							}
							
							//좌표 50m이내 인 업장인지 계산
							if(APIUtil.isNotEmpty(faclMorp.getCoordY()) && APIUtil.isNotEmpty(faclMorp.getCoordX())
							  && APIUtil.isNotEmpty(faclCompMorp.getCoordY()) && APIUtil.isNotEmpty(faclCompMorp.getCoordY()) ) {
								//좌표 (M)변환 및 좌표간 거리를 구함 
								cordDist = new BigDecimal(Double.toString( coordinateUtil.getCoordDistance(Double.parseDouble(faclMorp.getCoordY()), Double.parseDouble(faclMorp.getCoordX()), Double.parseDouble(faclCompMorp.getCoordY()), Double.parseDouble(faclCompMorp.getCoordX()))) );
							}
							
							//국문 매치 확율 계산
							korMorpMatcPer = ((new BigDecimal(korMorpEqualsCount).divide(new BigDecimal(faclKorRootMorpArray.length), MathContext.DECIMAL32)).multiply(OperateConstants.BIGDECIMAL_ONE_HUNDRED_VALUE));
							engMorpMatcPer = OperateConstants.BIGDECIMAL_ZERO_VALUE;
							
							//국문 일치 확율 체크  
							if(korMorpMatcPer.compareTo(InterfaceFactory.getFaclMapping().getFaclMorpMappingPersent()) >= 0) {
								logger.debug("== > [Matched] KOR Calc : (({} / {}) * 100) = {}", korMorpEqualsCount, faclKorRootMorpArray.length, korMorpMatcPer);
								logger.debug("== > KOR FaclCd : {}({}) Target : {}({}), korMorpEqualsCount : {}, korMorpMatcPer : {}\n- RootMorp : {}\n- CompMorp : {}"
										, faclMorp.getFaclNmKor(), faclMorp.getFaclCd(), faclCompMorp.getFaclNmKor(), faclCompMorp.getFaclCd(), korMorpEqualsCount, korMorpMatcPer, faclMorp.getFaclKorMorp(), faclCompMorp.getFaclKorMorp());
								//매치
								faclMorp.addMatchMorpFaclCdList(faclCompMorp.getFaclCd());
								morpMatch = true;
							}
							
							if(faclEngCompareMorpArray != null && faclEngRootMorpArray != null) {
								
								//영문 매치 확율 계산
								engMorpMatcPer = ((new BigDecimal(engMorpEqualsCount).divide(new BigDecimal(faclEngRootMorpArray.length), MathContext.DECIMAL32)).multiply(OperateConstants.BIGDECIMAL_ONE_HUNDRED_VALUE));
								
								//영문 일치 확율 체크  
								if(engMorpMatcPer.compareTo(InterfaceFactory.getFaclMapping().getFaclMorpMappingPersent()) >= 0) {
									logger.debug("== > [Matched] ENG Calc : (({} / {}) * 100) = {}", engMorpEqualsCount, faclEngRootMorpArray.length, engMorpMatcPer);
									logger.debug("== > ENG FaclCd : {}({}) Target : {}({}), engMorpEqualsCount : {}, engMorpMatcPer : {}\n- RootMorp : {}\n- CompMorp : {}"
											, faclMorp.getFaclNmEng(), faclMorp.getFaclCd(), faclCompMorp.getFaclNmEng(), faclCompMorp.getFaclCd(), engMorpEqualsCount, engMorpMatcPer, faclMorp.getFaclEngMorp(), faclCompMorp.getFaclEngMorp());
									//매치
									faclMorp.addMatchMorpFaclCdList(faclCompMorp.getFaclCd());
									morpMatch = true;
								}
							}
							
							//좌표 비교 값이 있고 한글 또는 영문 형태소가 기준치 이상일때
							if(cordDist != null && morpMatch) {
								
								//좌표 거리검증 : 좌표 계산 데이터가 존재할때 거리가 50m이하이면 일치 
								if(cordDist.compareTo(InterfaceFactory.getFaclMapping().getCordMatchCriteria()) <= 0) {
									logger.debug("=== >>> [COORD] Distance : {}", cordDist);
			
									faclMorp.addMatchMorpFaclCdList(faclCompMorp.getFaclCd());
									morpMatch = true;
								}
								else {
									//50M를 초과하면 불일치
									morpMatch = false;
								}
							}
							
							if( morpMatch ) {
								
								logger.debug("== >> [FINAL] Matched > {}({}) and {}({})", faclMorp.getFaclNmKor(), faclMorp.getFaclCd(), faclCompMorp.getFaclNmKor(), faclCompMorp.getFaclCd());
								logger.debug("== >> faclCompMorp : {}", faclCompMorp);
								
								//매핑된 하위 시설 정보
								ezcFaclMappingSDO = (EzcFaclMappingSDO) propertyUtil.copySameProperty(faclCompMorp, EzcFaclMappingSDO.class);
								ezcFaclMappingSDO.setHandMappingYn(CodeDataConstants.CD_N);
								ezcFaclMappingSDO.setDispOrder(OperateConstants.INTEGER_ONE_VALUE);
								//한글형태소 검증값
								ezcFaclMappingSDO.setKorMorpTotlCount(faclKorCompareMorpArray.length); //비교대상값의 한글 형태소 개수
								ezcFaclMappingSDO.setKorMorpEqualsCount(korMorpEqualsCount); //비교대상 한글 형태소가 기준형태소와 일치하는 개수
								ezcFaclMappingSDO.setKorMorpMatcPcnt(korMorpMatcPer.setScale(2, RoundingMode.HALF_UP)); //한글 형태소 일치율
								//영문형태소 검증값
								ezcFaclMappingSDO.setEngMorpTotlCount((faclEngCompareMorpArray != null ? faclEngCompareMorpArray.length : 0)); //비교대상값의 영문 형태소 개수								
								ezcFaclMappingSDO.setEngMorpEqualsCount(engMorpEqualsCount); //비교대상 영문 형태소가 기준형태소와 일치하는 개수
								ezcFaclMappingSDO.setEngMorpMatcPcnt(engMorpMatcPer.setScale(2, RoundingMode.HALF_UP)); //영문 형태소 일치율
								//좌표계산
								ezcFaclMappingSDO.setCordDist(cordDist); // 기준 시설과 비교대상 시설의 좌표간 거리
								
								//부모 그룹 시설 코드
								faclCompMorp.setPrntFaclCd(faclMorp.getFaclCd());
								
								//부모가 존재하는 시설코드 세팅(필터용)
								childfaclCdFilter.add(faclCompMorp.getFaclCd());
								
								logger.debug("==>> 일치 판정된 비교대상 저장 : {}", ezcFaclMappingSDO);
								//일치 판정된 비교대상 그룹시설의 자식으로 저장
								faclMorp.addEzcFaclMappingSDO(ezcFaclMappingSDO);
							}
						}//# end j for
						
						if(faclMorp.getEzcFaclMappingSDO() == null || faclMorp.getEzcFaclMappingSDO().size() == 0) {
							//매칭되는 시설이 없을 경우 싱글 그룹 데이터임
							faclMorp.setGroupData(true);
						}
						else {
							//매칭되는 시설이 있고 자식 데이터로 설정된 이력이 없으면 그룹 데이터임
							if(!childfaclCdFilter.contains(faclMorp.getFaclCd())) {
								faclMorp.setGroupData(true);
							}
						}
						
						//그룹판정일경우 자기 자신정보 세팅 
						if(faclMorp.isGroupData()) {
							
							//매핑된 하위 시설 정보
							ezcFaclMappingSDO = (EzcFaclMappingSDO) propertyUtil.copySameProperty(faclMorp, EzcFaclMappingSDO.class);
							ezcFaclMappingSDO.setHandMappingYn(CodeDataConstants.CD_N);
							ezcFaclMappingSDO.setDispOrder(OperateConstants.INTEGER_ZERO_VALUE);
							//한글형태소 검증값
							ezcFaclMappingSDO.setKorMorpTotlCount(faclKorRootMorpArray.length); //비교대상값의 한글 형태소 개수
							ezcFaclMappingSDO.setKorMorpEqualsCount(faclKorRootMorpArray.length); //비교대상 한글 형태소가 기준형태소와 일치하는 개수
							ezcFaclMappingSDO.setKorMorpMatcPcnt(OperateConstants.BIGDECIMAL_ONE_HUNDRED_VALUE); //한글 형태소 일치율
							//영문형태소 검증값
							ezcFaclMappingSDO.setEngMorpTotlCount((faclEngRootMorpArray != null ? faclEngRootMorpArray.length : 0)); //비교대상값의 영문 형태소 개수								
							ezcFaclMappingSDO.setEngMorpEqualsCount((faclEngRootMorpArray != null ? faclEngRootMorpArray.length : 0)); //비교대상 영문 형태소가 기준형태소와 일치하는 개수
							ezcFaclMappingSDO.setEngMorpMatcPcnt(OperateConstants.BIGDECIMAL_ONE_HUNDRED_VALUE); //영문 형태소 일치율
							//좌표계산
							ezcFaclMappingSDO.setCordDist(OperateConstants.BIGDECIMAL_ZERO_VALUE); // 기준 시설과 비교대상 시설의 좌표간 거리
							//부모 그룹 시설 코드 (자신)
							ezcFaclMappingSDO.setPrntFaclCd(faclMorp.getFaclCd());
							//부모 시설 코드 (자신)
							faclMorp.setPrntFaclCd(faclMorp.getFaclCd());
							
							logger.debug("==>> 그룹판정일경우 자기 자신정보 세팅 : {}", ezcFaclMappingSDO);
							//일치 판정된 비교대상 저장
							faclMorp.addEzcFaclMappingSDO(ezcFaclMappingSDO);						
						}
					}
					else {
						logger.debug(" ### == >> [PASS] '{}' use parent facility", faclMorp.getFaclCd());
					}
					
					//fileUtil.mkfile("D:/ezwel-repository", "compareMorp-finder"+Local.commonHeader().getStartTimeMillis()+".txt", faclMorp.toString(), "UTF-8", true, true);
					//결과 세팅
					out.add(faclMorp);
					
					clearArrays(faclKorRootMorpArray, faclEngRootMorpArray, faclKorCompareMorpArray, faclEngCompareMorpArray, childfaclCdFilter);
					
				}//# end i for
			}
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9600, "시설 데이터 매핑 중 장애 발생", e);
		}
		finally {
			
			clearArrays(faclKorRootMorpArray, faclEngRootMorpArray, faclKorCompareMorpArray, faclEngCompareMorpArray, childfaclCdFilter);
		}
		
		return out;
	}
	
	
	private void clearArrays(String[] faclKorRootMorpArray , String[] faclEngRootMorpArray , String[] faclKorCompareMorpArray , String[] faclEngCompareMorpArray , Set<BigDecimal> childfaclCdFilter) {
		
		if(faclKorRootMorpArray != null) {
			Arrays.fill(faclKorRootMorpArray, null);
		}
		
		if(faclEngRootMorpArray != null) {
			Arrays.fill(faclEngRootMorpArray, null);
		}
		
		if(faclKorCompareMorpArray != null) {
			
			Arrays.fill(faclKorCompareMorpArray, null);
		}
		
		if(faclEngCompareMorpArray != null) {
			Arrays.fill(faclEngCompareMorpArray, null);
		}
		
		if(childfaclCdFilter != null) {
			childfaclCdFilter.clear();
		}
	}
}
