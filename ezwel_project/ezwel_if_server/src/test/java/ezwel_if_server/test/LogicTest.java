package ezwel_if_server.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.server.repository.OutsideRepository;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataRealtimeImageOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;

public class LogicTest {

	private static final Logger logger = LoggerFactory.getLogger(LogicTest.class);
	
	
	@Test
	public void testAsset01() {
		AllRegOutSDO out = new AllRegOutSDO();
		
		/** 2. EZC_FACL_IMG N건 저장 */
		EzcFaclImg img = null;
		//전문의 이미지 목록
		List<EzcFaclImg> ezcFaclImgList = new ArrayList<EzcFaclImg>();
		img = new EzcFaclImg();
		img.setPartnerImgUrl("http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/000.jpg");
		ezcFaclImgList.add(img);
		img = new EzcFaclImg();
		img.setPartnerImgUrl("http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/111.jpg");
		ezcFaclImgList.add(img);
		img = new EzcFaclImg();
		img.setPartnerImgUrl("http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/555.jpg");
		ezcFaclImgList.add(img);
		img = new EzcFaclImg();
		img.setPartnerImgUrl("http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/666.jpg");
		ezcFaclImgList.add(img);
		
		
		EzcFaclImg ezcFacl = new EzcFaclImg();
		ezcFacl.setFaclCd(new BigDecimal("36148"));
		//기존 저장된 이미지 정보 조회
		List<String> ezcFaclImgDBList = new ArrayList<String>();
		ezcFaclImgDBList.add("http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/111.jpg");
		ezcFaclImgDBList.add("http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/222.jpg");
		ezcFaclImgDBList.add("http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/333.jpg");
		ezcFaclImgDBList.add("http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/444.jpg");
		
		AllRegDataRealtimeImageOutSDO realtimeImageIO = null;
		EzcFaclImg inEzcFaclImg = null;
		
		if(ezcFaclImgList != null && ezcFaclImgList.size() > 0) {
			/** 
			 * EZC_FACL_IMG 테이블에는 KEY가 FACL_IMG_SEQ, FACL_CD 두개인데 FACL_CD는 같은 데이터가 N개저장되어있고
			 * FACL_IMG_SEQ는 DB테이블을 조회하기전에 알수 없는 불완전한 설계 상태임으로 merge문을 실행할 수 있는 환경이 되지 못함\
			 * 그럼으로 상단의 faclCd에 해당하는 이미지 데이터를 모두 삭제후 새로 insert함
			 **/
			int imageIdx = 0;
			int txCount = 0;
			
			//전문 이미지 리스트
			for(EzcFaclImg faclImg : ezcFaclImgList) {
				logger.debug("#image db : {}", ezcFaclImgDBList);
				//이미 동일한 이미지 URL이 저장되어있는 지 체크
				imageIdx = OperateConstants.INTEGER_MINUS_ONE;
				if(ezcFaclImgDBList != null) {
					imageIdx = ezcFaclImgDBList.indexOf(faclImg.getPartnerImgUrl());
					logger.debug("imageIdx indexOf : {}, url : {}", imageIdx, faclImg.getPartnerImgUrl());
				}
				
				if(imageIdx == OperateConstants.INTEGER_MINUS_ONE) {
					//저장되지 않은 신규 이미지 이면 입력
					//sequnce
					//faclImg.setFaclImgSeq((BigDecimal) sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "selectEzcFaclImgSeq")));
					logger.debug("selectEzcFaclImgSeq 시퀀스 조회 : {}", APIUtil.getId());
					txCount++; //sequnce transaction 
					faclImg.setFaclCd(ezcFacl.getFaclCd());
					//insert ( 신규로 다운받아야할 이미지 )
					//txCount += sqlSession.insert(getNamespace("FACL_IMG_MAPPER", "insertEzcFaclImg"), faclImg);
					txCount++;
					logger.debug("insertEzcFaclImg 신규이미지 입력 : {}", faclImg);
					//신규로 다운받아야할 이미지
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
					//pass ( -1이 아닐 경우 )
					ezcFaclImgDBList.set(imageIdx, OperateConstants.STR_RESERVE_IS_SAVED);
				}
			}
			
			if(ezcFaclImgDBList != null) {
				
				for(String deleteImage : ezcFaclImgDBList) {
					
					if(!deleteImage.equals(OperateConstants.STR_RESERVE_IS_SAVED)) {
						
						//삭제 대상 이미지
						inEzcFaclImg = new EzcFaclImg();
						inEzcFaclImg.setFaclCd(ezcFacl.getFaclCd());								
						inEzcFaclImg.setPartnerImgUrl(deleteImage);
						
						ezcFaclImgDBList.set(ezcFaclImgDBList.indexOf(deleteImage), "isDelete");
						
						//삭제 대상 이미지 전체 경로
						//out.addDeleteDownloadFilePathList(APIUtil.getImageCanonicalPath(deleteImage));
						logger.debug("out.addDeleteDownloadFilePathList ", deleteImage);
						//삭제
						//txCount += sqlSession.delete(getNamespace("FACL_IMG_MAPPER", "deleteEzcFaclImg"), inEzcFaclImg);
						logger.debug("sqlSession.delete(deleteEzcFaclImg, {}) ", inEzcFaclImg);
					}
				}
			}
			logger.debug("#final image db : {}", ezcFaclImgDBList);
		}
	}
}
