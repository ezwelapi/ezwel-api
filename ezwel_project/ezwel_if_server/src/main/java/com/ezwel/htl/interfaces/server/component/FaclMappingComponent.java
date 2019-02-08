package com.ezwel.htl.interfaces.server.component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.configure.data.FaclMappingConfig;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.morpheme.cm.MorphemeUtil;
import com.ezwel.htl.interfaces.server.commons.sdo.EzcFaclMappingSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.commons.utils.CoordinateUtil;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;

@Component
@APIType(description="시설 매핑 컴포넌트")
public class FaclMappingComponent {

	private static final Logger logger = LoggerFactory.getLogger(FaclMappingComponent.class);
	
	private CoordinateUtil coordinateUtil;
	
	private PropertyUtil propertyUtil;
	
	private CommonUtil commonUtil;
	
	@APIOperation(description="시설 정보 분석 매핑")
	public List<EzcFacl> getSearchForSameFacility(List<EzcFacl> faclMorpSearchList, boolean verbose) throws Exception {
		
		coordinateUtil = (CoordinateUtil) LApplicationContext.getBean(coordinateUtil, CoordinateUtil.class);
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
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
		BigDecimal korMorpMatcPer = OperateConstants.BIGDECIMAL_ZERO_VALUE;
		BigDecimal engMorpMatcPer = OperateConstants.BIGDECIMAL_ZERO_VALUE;
		EzcFaclMappingSDO ezcFaclMappingSDO = null;
		boolean isExcpetion = false;
		
		SearchMorpVO korMorpVo = null;
		SearchMorpVO engMorpVo = null;
		
		/**
		 * 1. 이미 그룹(부모) grpFaclCd가 없는 null인 신규 시설인경우의 매핑 처리
		 * 2. 이미 그룹(부모) grpFaclCd가 있는 기존 시설의 매핑처리
		 * 3. 이유는 기존 매핑된 grpFaclCd가 변경되면 안됨으로 기존 grpFaclCd에 대한 유지 처리를 해야함
		 */
		
		try {
			
			//결과 목록
			out = new ArrayList<EzcFacl>();
			
			if(faclMorpSearchList != null && faclMorpSearchList.size() > 0) {
				
				//앞전 매핑된 데이터의 그룹시설 여부와 부모(그룹)정보를 찾음 (DB설계상 데이터를 적제할곳이 없음으로 프로그램에서 처리함)
				findPrntGroup(faclMorpSearchList);
				
				//비교기준 시설 정보
				for(int i = 0; i < faclMorpSearchList.size(); i++) {
					
					//비교기준
					faclMorp = faclMorpSearchList.get(i);
					logger.debug("■■■■ 기준데이터 FaclCd : {}, GroupData : {}, PrntFaclCd : {}", faclMorp.getFaclCd(), faclMorp.isGroupData(), faclMorp.getPrntFaclCd());
					
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
						
						if(verbose) {
							logger.debug(" ### == >> [START FIND PARENT FACILITY] Name is {}", faclMorp.getFaclNmKor());
						}
						
						//중복필터 초기화
						//childfaclCdFilter = new HashSet<BigDecimal>();
						
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
								if(verbose) {
									logger.debug("[PASS] 동일한 시설은 패스합니다. => FaclCd : {}.compareTo({})", faclMorp.getFaclCd(), faclCompMorp.getFaclCd());
								}
								continue;
							}
							
							if(faclCompMorp.getPrntFaclCd() != null) {
								if(verbose) {
									logger.debug("[PASS] 이미 그룹에 속한 시설은 패스합니다. PrntFaclCd => faclMorp '{}', faclCompMorp '{}'", faclMorp.getPrntFaclCd());
								}
								continue;
							}

							if(faclCompMorp.isGroupData()) {
								if(verbose) {
									logger.debug("이미 그룹데이터로 선정된 시설 입니다. => faclCompMorp.ezcFaclMappingList size : {}", (faclCompMorp.getEzcFaclMappingList() != null ? faclCompMorp.getEzcFaclMappingList().size() : 0));
								}
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
							
							//좌표 유형별 설정된 미터(m)이내 인 업장인지 계산
							if(APIUtil.isNotEmpty(faclMorp.getCoordY()) && APIUtil.isNotEmpty(faclMorp.getCoordX())
							  && APIUtil.isNotEmpty(faclCompMorp.getCoordY()) && APIUtil.isNotEmpty(faclCompMorp.getCoordY()) ) {
								//좌표 (M)변환 및 좌표간 거리를 구함 
								cordDist = new BigDecimal(Double.toString( coordinateUtil.getCoordDistance(Double.parseDouble(faclMorp.getCoordY()), Double.parseDouble(faclMorp.getCoordX()), Double.parseDouble(faclCompMorp.getCoordY()), Double.parseDouble(faclCompMorp.getCoordX()))) );
							}
							
							//국문 형태소 탐색 및 일치율 계산
							korMorpVo = getMorpMatchData(MorphemeUtil.MORP_LANG_EN, faclKorRootMorpArray, faclKorCompareMorpArray, verbose);
							//영문 형태소 탐색 및 일치율 계산
							engMorpVo = getMorpMatchData(MorphemeUtil.MORP_LANG_EN, faclEngRootMorpArray, faclEngCompareMorpArray, verbose);
							
							//국문 형태소 일치개수
							korMorpEqualsCount = korMorpVo.matchCount;
							//영문 형태소 일치개수
							engMorpEqualsCount = engMorpVo.matchCount;
							
							//국문 매치 확율 
							korMorpMatcPer = korMorpVo.matchPcnt; 
							//영문 매치 확율 
							engMorpMatcPer = engMorpVo.matchPcnt;
							
							//국문 일치 판정 실행
							if(korMorpMatcPer.compareTo(InterfaceFactory.getFaclMapping().getFaclMorpMappingPersent()) >= 0) {
								
								if((korMorpVo.standardMorpArray.length - korMorpVo.matchCount) == 1) {
									//기준형태소 배열 개수와 매치 개수의 차가 1개 일때는 실패
									morpMatch = false;
								}
								else {
									//매치
									morpMatch = true;
								}
							}
							
							//영문 일치 판정 실행  
							if(engMorpMatcPer.compareTo(InterfaceFactory.getFaclMapping().getFaclMorpMappingPersent()) >= 0) {
								
								if((engMorpVo.standardMorpArray.length - engMorpVo.matchCount) == 1) {
									//기준형태소 배열 개수와 매치 개수의 차가 1개 일때는 실패
									morpMatch = false;
								}
								else {
									//매치
									morpMatch = true;
								}
							}
							
							//좌표 비교 값이 있고 한글 또는 영문 형태소가 기준치 이상일때
							if(morpMatch && cordDist != null) {
								
								//좌표 거리검증 : 좌표 계산 데이터가 존재할때 거리가 설정된 미터(m)이하이면 일치 
								if(cordDist.compareTo(getCordMatchCriteria(faclMorp.getRoomType(), faclKorRootMorpArray, faclEngRootMorpArray, verbose)) <= 0) {
									if(verbose) {
										logger.debug("=== >>> [COORD] Distance : {}", cordDist);
									}
									morpMatch = true;
								}
								else {
									//설정된 미터(m)를 초과하면 불일치
									morpMatch = false;
								}
							}
							
							if( morpMatch ) {
								
								faclMorp.addMatchMorpFaclCdList(faclCompMorp.getFaclCd());
								
								if(verbose) {
									logger.debug("== >> [FINAL] Matched > {}({}) and {}({})", faclMorp.getFaclNmKor(), faclMorp.getFaclCd(), faclCompMorp.getFaclNmKor(), faclCompMorp.getFaclCd());
									logger.debug("== >> faclCompMorp : {}", faclCompMorp);
								}
								
								//매핑된 하위 시설 정보
								ezcFaclMappingSDO = (EzcFaclMappingSDO) propertyUtil.copySameProperty(faclCompMorp, EzcFaclMappingSDO.class);
								ezcFaclMappingSDO.setHandMappingYn(CodeDataConstants.CD_N);
								ezcFaclMappingSDO.setDispOrder(OperateConstants.INTEGER_ZERO_VALUE);
								//한글형태소 검증값
								ezcFaclMappingSDO.setKorMorpTotlCount(faclKorCompareMorpArray.length); //비교대상값의 한글 형태소 개수
								ezcFaclMappingSDO.setKorMorpEqualsCount(korMorpEqualsCount); //비교대상 한글 형태소가 기준형태소와 일치하는 개수
								ezcFaclMappingSDO.setKorMorpMatcPcnt(korMorpMatcPer.setScale(2, RoundingMode.HALF_UP)); //한글 형태소 일치율
								//영문형태소 검증값
								ezcFaclMappingSDO.setEngMorpTotlCount((faclEngCompareMorpArray != null ? faclEngCompareMorpArray.length : 0)); //비교대상값의 영문 형태소 개수								
								ezcFaclMappingSDO.setEngMorpEqualsCount(engMorpEqualsCount); //비교대상 영문 형태소가 기준형태소와 일치하는 개수
								ezcFaclMappingSDO.setEngMorpMatcPcnt(engMorpMatcPer.setScale(2, RoundingMode.HALF_UP)); //영문 형태소 일치율
								//좌표계산  => 기준 시설과 비교대상 시설의 좌표간 거리 
								ezcFaclMappingSDO.setCordDist(cordDist);
								
								//부모 그룹 시설 코드
								faclCompMorp.setPrntFaclCd(faclMorp.getFaclCd());
								
								//부모가 존재하는 시설코드 세팅(필터용)
								//childfaclCdFilter.add(faclCompMorp.getFaclCd());
								
								if(verbose) {
									logger.debug("==>> 일치 판정된 비교대상 저장 : {}", ezcFaclMappingSDO);
								}
								//일치 판정된 비교대상 그룹시설의 자식으로 저장
								faclMorp.addEzcFaclMappingList(ezcFaclMappingSDO);
								//그룹 데이터 확정 (자식 데이터가 존재함)
								if(!faclMorp.isGroupData()) {
									if(verbose) {
										logger.debug("그룹 데이터 확정 {}", faclMorp);
									}
									setConfirmGroupData(faclMorp, faclKorRootMorpArray, faclEngRootMorpArray, verbose);
								}
							}
						}//# end j for
						
						//그룹판정이 아닐경우 자기 자신정보 세팅 (self group)
						if(!faclMorp.isGroupData()) {
							
							setConfirmGroupData(faclMorp, faclKorRootMorpArray, faclEngRootMorpArray, verbose);						
						}
					}
					else {
						if(verbose) {
							logger.debug(" ### == >> [PASS] '{}' use parent facility", faclMorp.getFaclCd());
						}
					}
					
					//fileUtil.mkfile("D:/ezwel-repository", "compareMorp-finder"+Local.commonHeader().getStartTimeMillis()+".txt", faclMorp.toString(), "UTF-8", true, true);
					//결과 세팅
					out.add(faclMorp);
					
					clearArrays(faclKorRootMorpArray, faclEngRootMorpArray, faclKorCompareMorpArray, faclEngCompareMorpArray, childfaclCdFilter);
					
				}//# end i for
			}
		}
		catch(Exception e) {
			isExcpetion = true;
			throw new APIException(MessageConstants.RESPONSE_CODE_9600, "시설 데이터 매핑 중 장애 발생", e);
		}
		finally {
			//Exception 발생시 finally 에서 clear
			if(isExcpetion) {
				clearArrays(faclKorRootMorpArray, faclEngRootMorpArray, faclKorCompareMorpArray, faclEngCompareMorpArray, childfaclCdFilter);
			}
		}
		
		return out;
	}
	
	
	/**
	 * 앞전 매핑된 데이터의 그룹시설 여부와 부모(그룹)정보를 찾음 (DB설계상 데이터를 적제할곳이 없음으로 프로그램에서 처리함)
	 * @param faclMorpSearchList
	 */
	@APIOperation(description="앞전 매핑된 데이터의 그룹시설 여부와 부모(그룹)정보를 찾음 (DB설계상 데이터를 적제할곳이 없음으로 프로그램에서 처리함)")
	private void findPrntGroup(List<EzcFacl> faclMorpSearchList) {
		
		EzcFacl faclMorp = null;
		EzcFacl faclGrpMorp = null;
		
		//비교기준 시설 정보
		for(int i = 0; i < faclMorpSearchList.size(); i++) {
			
			//비교기준
			faclMorp = faclMorpSearchList.get(i);

			//이전 매핑된 데이터중 비교기준이된 그룹 데이터의 실제 데이터는 cordDist에 값 -1을 저장하고있다. 즉 비교기준 부모 시설 데이터이다.
			if(faclMorp.getCordDist() != null && faclMorp.getGrpFaclCd() != null) {
				logger.debug("*** faclMorp.getCordDist() : {}", faclMorp.getCordDist());
				
				if(faclMorp.getCordDist().compareTo(OperateConstants.BIGDECIMAL_MINUS_ONE) == 0) {
					//부모인 데이터
					faclMorp.setGroupData(true);
				}
				else {
					//부모 탐색
					for(int x = 0; x < faclMorpSearchList.size(); x++) {
						faclGrpMorp = faclMorpSearchList.get(x);
						//cordDist가 -1(그룹시설)인것들중 같은 그룹시설코드를 찾는다.
						if(faclGrpMorp.getCordDist() != null 
							&& faclGrpMorp.getCordDist().compareTo(OperateConstants.BIGDECIMAL_MINUS_ONE) == 0
							&& faclMorp.getGrpFaclCd().compareTo(faclGrpMorp.getGrpFaclCd()) == 0) {
							//그룹이된 부모 시설 코드 세팅
							faclMorp.setPrntFaclCd(faclGrpMorp.getFaclCd());
							break;
						}
					}
				}
			}
		}
	}
	
	
	@APIOperation(description="그룹 시설정보 데이터 세팅")
	private void setConfirmGroupData(EzcFacl faclMorp, String[] faclKorRootMorpArray, String[] faclEngRootMorpArray, boolean verbose) {
		
		//매핑된 하위 시설 정보
		EzcFaclMappingSDO ezcFaclMappingSDO = (EzcFaclMappingSDO) propertyUtil.copySameProperty(faclMorp, EzcFaclMappingSDO.class);
		ezcFaclMappingSDO.setHandMappingYn(CodeDataConstants.CD_N);
		ezcFaclMappingSDO.setDispOrder(OperateConstants.INTEGER_ZERO_VALUE);
		
		//한글형태소 검증값
		ezcFaclMappingSDO.setKorMorpTotlCount(faclKorRootMorpArray.length); //비교대상값의 한글 형태소 개수
		ezcFaclMappingSDO.setKorMorpEqualsCount(faclKorRootMorpArray.length); //비교대상 한글 형태소가 기준형태소와 일치하는 개수
		ezcFaclMappingSDO.setKorMorpMatcPcnt(OperateConstants.BIGDECIMAL_ONE_HUNDRED_VALUE); //한글 형태소 일치율
		
		//영문형태소 검증값
		if(faclEngRootMorpArray != null) {
			ezcFaclMappingSDO.setEngMorpTotlCount(faclEngRootMorpArray.length); //비교대상값의 영문 형태소 개수								
			ezcFaclMappingSDO.setEngMorpEqualsCount(faclEngRootMorpArray.length); //비교대상 영문 형태소가 기준형태소와 일치하는 개수
			ezcFaclMappingSDO.setEngMorpMatcPcnt(OperateConstants.BIGDECIMAL_ONE_HUNDRED_VALUE); //영문 형태소 일치율
		}
		//좌표계산 ( 그룹 데이터일 경우 -1 데입 )
		ezcFaclMappingSDO.setCordDist(OperateConstants.BIGDECIMAL_MINUS_ONE); 
		//부모 그룹 시설 코드 (자신)
		ezcFaclMappingSDO.setPrntFaclCd(faclMorp.getFaclCd());
		//부모 시설 코드 (자신)
		//faclMorp.setPrntFaclCd(faclMorp.getFaclCd());
		faclMorp.setGroupData(true);
		if(verbose) {
			logger.debug("==>> 그룹판정일경우 자기 자신정보 세팅 : {}", ezcFaclMappingSDO);
		}
		//일치 판정된 비교대상 저장
		faclMorp.addEzcFaclMappingList(ezcFaclMappingSDO);	
	}
	
	@APIOperation(description="사용하였던 배열 및 목록 Clear")
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
	
	@APIOperation(description="분석된 형태소 일치율 판단")
	private SearchMorpVO getMorpMatchData(String language, String[] firstArray, String[] secondArray, boolean verbose) {
		SearchMorpVO out = new SearchMorpVO();
		
		if(firstArray == null || secondArray == null || firstArray.length == OperateConstants.INTEGER_ZERO_VALUE || secondArray.length == OperateConstants.INTEGER_ZERO_VALUE) {
			//둘중 하나라도 null이거나 길이가 0이면 일치개수 0 리턴
			return out;
		}
		
		//길이에 따른 기준 대상 배열
		out.language = language;
		out.firstCount = firstArray.length;
		out.secondCount = secondArray.length;
		//out.equalsIndex = OperateConstants.INTEGER_MINUS_ONE;
		
		//갯수가 짧은 형태소 배열을 기준으로 기준/대상 선정
		if(out.firstCount > out.secondCount) {
			out.standardMorpArray = secondArray;
			out.comparedMorpArray = firstArray;
		}
		else {
			out.standardMorpArray = firstArray;
			out.comparedMorpArray = secondArray;			
		}
		
		for(String rootMorp : out.standardMorpArray) {
			out.equalsIndex = Arrays.binarySearch(out.comparedMorpArray, rootMorp);
			if(verbose) {
				logger.debug("[형태소 탐색 standard length: {}] index : {} / {} : {}", out.standardMorpArray.length, out.equalsIndex, rootMorp, out.comparedMorpArray);
			}
			
			if(out.equalsIndex > -1) {
				out.matchCount++;
			}
		}
		
		out.matchPcnt = ((new BigDecimal(out.matchCount).divide(new BigDecimal(out.comparedMorpArray.length), MathContext.DECIMAL32)).multiply(OperateConstants.BIGDECIMAL_ONE_HUNDRED_VALUE));
		
		if(verbose) {
			StringBuffer message = new StringBuffer();
			message.append(APIUtil.formatMessage("== > [Matched] {} Calc : (({} / {}) * 100) = {}", out.language, out.matchCount, out.comparedMorpArray.length, out.matchPcnt));
			message.append(APIUtil.formatMessage("== > {} MatchCount : {}, MatchPcnt : {}, StandardMorpArray : {}, ComparedMorpArray : {}", out.language ,out.matchCount, out.matchPcnt, out.standardMorpArray, out.comparedMorpArray));
			message.append(APIUtil.formatMessage("== > SearchMorpVO : {}", out));
			logger.debug("{}", message.toString());
		}
		return out;
	}
	
	@APIOperation(description="좌표간 거리(m)를 구함")
	private BigDecimal getCordMatchCriteria(String roomType, String[] faclKorRootMorpArray, String[] faclEngRootMorpArray, boolean verbose) {
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		BigDecimal out = OperateConstants.BIGDECIMAL_ZERO_VALUE;
		
		FaclMappingConfig config = InterfaceFactory.getFaclMapping();

		out = config.getTypeCordMatchCriteriaData(roomType, commonUtil.mergeStringArray(faclKorRootMorpArray, faclEngRootMorpArray));
		if(verbose) {
			logger.debug("[END] getCordMatchCriteria value : {}", out); 
		}
		
		return out;
	}
	
	@APIModel(description="형태소 검색 정보 VO")
	private class SearchMorpVO extends APIObject {
		
		String language = null;
		String[] standardMorpArray = null;
		String[] comparedMorpArray = null;
		Integer firstCount = OperateConstants.INTEGER_MINUS_ONE;
		Integer secondCount = OperateConstants.INTEGER_MINUS_ONE;	
		Integer equalsIndex = OperateConstants.INTEGER_MINUS_ONE;
		Integer matchCount  = OperateConstants.INTEGER_ZERO_VALUE;
		BigDecimal matchPcnt  = OperateConstants.BIGDECIMAL_ZERO_VALUE;
	}
}
