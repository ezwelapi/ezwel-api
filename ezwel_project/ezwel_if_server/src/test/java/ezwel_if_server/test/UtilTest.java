package ezwel_if_server.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.ko.morph.MorphException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.sdo.ImageSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.morpheme.en.EnglishAnalyzers;
import com.ezwel.htl.interfaces.server.commons.morpheme.ko.KoreanAnalyzers;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.commons.utils.FileUtil;
import com.ezwel.htl.interfaces.server.commons.utils.UnicodeUtil;

public class UtilTest {

	private static final Logger logger = LoggerFactory.getLogger(UtilTest.class);
	
	public UtilTest() {
		//InterfaceFactory.initLocalTestInterfaceFactory();
	}
	
	@Test
	public void resourceTest() {
		
		
		try {
			URI path = new URL("file:/D:/02.Workspace/eclipse/eclipse-ezwel/tomcat-context/deploy/ezwel_if_server(8282)-v7.0.79/ezwel_if_server/WEB-INF/lib/ezwel_if-0.0.1-SNAPSHOT.jar!/interfaces/interface-configure.xml").toURI();
			path = new URL("file:/D:/02.Workspace/testFile.txt").toURI();
			File test = new File(path);
	    	logger.debug("exists : {}", test.exists());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//@Test
	public void test() {
		
		List<Integer> ezcFacls = new ArrayList<Integer>();
		
		for(int i = 0; i < 2251; i++) {
			ezcFacls.add(i);
			logger.debug("{}", i);
		}
		
		testSubList(ezcFacls, 0);
	}
	
	public void testSubList(List<Integer> ezcFacls, int fromIndex) {
		
		Integer toIndex = fromIndex + 50;
		
		List<Integer> saveFaclRegDatas = null;
		if(toIndex > ezcFacls.size()) {
			toIndex = ezcFacls.size();
		}
		
		
		saveFaclRegDatas = ezcFacls.subList(fromIndex, toIndex);
		
		logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.debug("{} ~ {}", fromIndex, toIndex);
		logger.debug("saveFaclRegDatas : {}",  saveFaclRegDatas.size());
		logger.debug("saveFaclRegDatas : {}",  saveFaclRegDatas);
		
		if(ezcFacls != null && ezcFacls.size() > toIndex) {
			testSubList(ezcFacls/* 제휴사 별 시설 목록 */, toIndex);
		}
		
		if(saveFaclRegDatas != null) {
			saveFaclRegDatas.removeAll(saveFaclRegDatas);
		}
	}
	
	//@Test
	public void imageDownload() {
		FileUtil fileUtil = new FileUtil();
		ImageSDO imageSDO = new ImageSDO();
		/*다운로드 할 이미지 URL*/
		imageSDO.setImageURL("http://tourimage.interpark.com/Product/Housing/Hotel/17000047/17000047_01bb.jpg?ver=20181129");
		ImageSDO out = fileUtil.getImageDownload(imageSDO, true);
		logger.debug("ImageSDO out : {}", out);
	}
	
	


    //@Test
    public void unicodeTest() throws Exception {
    	
        String str = UnicodeUtil.encode("한 글");
        logger.debug(str);
        logger.debug(UnicodeUtil.decode("\u3141 \uc785\uc2e4 \ubc0f \ud1f4\uc2e4\uc2dc\uac04\uc740 \ubc18\ub4dc\uc2dc \uc9c0\ucf1c\uc8fc\uc138\uc694."));
    }

    //@Test
    public void urlTest() throws MorphException, IOException {
    	
    	CommonUtil common = new CommonUtil();
    	
    	String testWord = "이문자열은 반각 문자입니다.";
    	
    	String fullChar = new APIUtil().toFullChar(testWord);
    	
    	logger.debug("{}", fullChar);
    	
        // 형태소 분석
        KoreanAnalyzers aah = new KoreanAnalyzers();
        
        String input0 = "올해 크리스마스에는 눈이 내리지 않고 비교적 포근할 전망이다.";
        List<String> result0 = aah.basicAnalyze(input0);
        logger.debug("기본 분석 : {}", result0);
        
        String input1 = "올해 크리스마스에는 눈이 내리지 않고 비교적 포근할 전망이다.";
        List<String> result1 = aah.morphAnalyze(input0);
        logger.debug("형태소 분석 : {}",  result1);
 
        // 복합명사 분해
        String input2 = "하늘공원";
        List<String> result2 = aah.compoundNounAnalyze(input0);
        logger.debug("복합 명사 분해 : {}", result2);
 
        // 띄워쓰기
        String input3 = "올 해크리스마스 에는 눈이내리지않고 비교적포근할전 망이다.";
        List<String> result3 = aah.wordSpaceAnalyze(input0);
        logger.debug("뛰어 쓰기 : {}", result3);
 
        // 색인어 추출
        String input4 = "입실 및 퇴실시간은 반드시 지켜주세요.";
        List<String> result4 = aah.guideWord(input0);
        logger.debug("색인어 추출 : {}", result4);
 
        // 명사 추출
        logger.debug("명사 추출 : ");
        List<String> nuonList = aah.extractNoun(input0);
        logger.debug("명사 추출 : {}", nuonList);

        // 복합 형태소
        logger.debug("finals : ");
        Set<String> finals = aah.getKoreanMorphologicalAnalysis(input0);
        logger.debug("finals : {}", finals);
    	
        
        EnglishAnalyzers eng = new EnglishAnalyzers();
        String entText = "Currently, there are commercial products and open source projects for Korean Elastic Search Korean morphological analyzer, and it is developed by user if necessary.";
        Set<String> analyzer = eng.getEnglishMorphologicalAnalysis(entText);
        
        logger.debug("{}", analyzer);
        
        String sentence = "보헤미안 랩소디의 흥행으로 또다시 사랑받고 있는 '퀸'의 대표곡이자, 유명한 응원가인 We are the champion의 가사 중 우리가 챔피언이며 끝까지 싸워나갈 것이라는 가사는 많은 사람들에게 용기와 희망을 준다. In an August issue of Monthly Microsource Software, I posted an article about Apache Lucene. Suddenly, the schedule of the project has been abrupt, I am going to try to help you a little bit. I would appreciate it if you pointed out the wrong or missing parts. 언젠가는 챔피언이 될 것이라는 희망, 언젠가는 꿈꿔왔던 목표에 이르게 될 것이라는 희망을. 아티스트 조윤진씨는 테이프를 만나 아프고 무기력했던 그녀의 세상에서 싸워나갔고 이루고자 하는 것들에 다가가고, 이루며, 또다시 꿈을 꾼다. 그녀가 그려내는 반짝이는 눈동자를 지닌 인물들은 강인한 생명력을 가진 테이프와 바로 그녀 자신을, 닮아있다.";
        sentence = "영광 노을연가 펜션,강화 J모텔 펜션,추억이있는바다(유리바다),제주 천제빌 펜션,바다옆히든밸리,태안 오슈 펜션,태안 하늬바람 펜션,사라디펜션,제주바다의향기,행복한家1,(구)신비펜션,제주 2jj 게스트하우스,광주숲속의정원,서귀포 엘리시움 펜션,하늘땅별땅펜션,정선 강과소나무 펜션,유명산장펜션,브라우니펜션,청운골 생태마을,오렌지펜션,양평 삼호 펜션,단양 패러마을 펜션,양평 애플 펜션,단양 풀내음 펜션,통영여행게스트펜션,제주 공항길 펜션,양평 베네 펜션,라파로마휴양펜션,경주그린벨리,제주 소설 펜션,포천 숲속의장발장 스파 펜션,레만펜션,바다빛펜션,하늘바다,비치플레이스,청평호 OK펜션,포천 굿데이 카라반 오토캠핑,천상의 노을,제주산토리니펜션,태안 카밀리아하우스 펜션,홍천 해피데이 펜션,작은뜨락 숲속의향기,제주 제주하늘 펜션,구름위스파 n 산책,가평 연인가족벨리 펜션,여수 프롬갤러리 펜션,행복한집(구),엘린휴버트,평창 더숲 펜션,티엔느펜션,양평 양지 펜션,라고스펜션,경주 그린벨리 펜션,홍천 신영 펜션,홍천 별이빛나는밤펜션,진주빛펜션";
        String division = OperateConstants.STR_COMA;
        common.getMorphologicalAnalysis("eng", sentence, division); 
        
        //logger.debug("韩国弹性搜索韩国形态分析仪有商业产品和开源项目, 現在弾性サーチ用韓国語形態素解析では、商用製品とオープンソースプロジェクトがあり、必要であれば、ユーザーが独自に開発しています。 유명한 응원가인 We are the champion의 가사 중 우리가 챔피언이며 끝까지 ".replaceAll("(?i)([\\p{S}\\p{P}ºㄱ-ㅎㅏ-ㅣ가-힣ァ-ンあ-んー一-齢𠮟\u2e80-\u2eff\u31c0-\u31ef\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fbf\uf900-\ufaff]+)", OperateConstants.STR_BLANK));
        //logger.debug("韩国弹性搜索韩国形态分析仪有商业产品和开源项目, 現在弾性サーチ用韓国語形態素解析では、商用製品とオープンソースプロジェクトがあり、必要であれば、ユーザーが独自に開発しています。 유명한 응원가인 We are the champion의 가사 중 우리가 챔피언이며 끝까지 ".replaceAll("([\\p{S}\\p{P}ºa-zA-Z]+)", OperateConstants.STR_BLANK));
        
    }
    
    //@Test
    public void regexTest() {
    	
        String[] sentenceList = new String[] {
        		"( 주	)신라호텔(검)",
        		"신라호텔(	주)( 검 )"
	    };
        
        for(String text : sentenceList) {
        	logger.debug( "text : {}", text.replaceAll("\\(([	 주]+)\\)|\\(([	 검]+)\\)", "") );
        }
        
        
    }
    
    //@Test
    public void morpTest() {
    	
    	CommonUtil common = new CommonUtil();
        
        String[] faclArray = new String[]{
        		/*"영광 노을연가 펜션", "강화 J모텔 펜션", "추억이있는바다(유리바다)", "제주 천제빌 펜션", "바다옆히든밸리", "태안 오슈 펜션", "태안 하늬바람 펜션", "사라디펜션", "제주바다의향기", "행복한家1", "(구)신비펜션", "제주 2jj 게스트하우스", "광주숲속의정원", "서귀포 엘리시움 펜션", "하늘땅별땅펜션", "정선 강과소나무 펜션", "유명산장펜션", "브라우니펜션", "청운골 생태마을", "오렌지펜션", "양평 삼호 펜션", "단양 패러마을 펜션", "양평 애플 펜션", "단양 풀내음 펜션", "통영여행게스트펜션", "제주 공항길 펜션", "양평 베네 펜션", "라파로마휴양펜션", "경주그린벨리", "제주 소설 펜션", "포천 숲속의장발장 스파 펜션", "레만펜션", "바다빛펜션", "하늘바다", "비치플레이스", "청평호 OK펜션", "포천 굿데이 카라반 오토캠핑", "천상의 노을", "제주산토리니펜션", "태안 카밀리아하우스 펜션"*/
        		
        		"놀러와펜션(B구역)","아라뜰펜션 1호점","에스엔제이(S&J)펜션","선재465","구1","2PM펜션","홍천 OG 펜션","밈(meme)"
        		,"고성 바다여행AJ펜션","청평호 OK펜션","sun펜션","207마일","제주 삼양2022 펜션","강릉 if 게스트하우스","A SOME PLACE"
        		,"그리드1398펜션","횡성 Hello412 펜션","파주 W&J 펜션","양평 미르하우스1157 펜션","산골이야기2","양평 Q 펜션","보은 BLISSVILLA 펜션"
        		,"제주Y리조트(오픈안됨)","파주 J 펜션","완도 SM 펜션","산너머(OLD)","SeeYou(씨유)펜션","구2","구름위스파 n 산책"
        		,"강촌 W 펜션","해운대 스테이7 호텔","씨엘178-Ci동","ANN펜션","양평 SD비버리힐스 펜션","순천만 1박2일 펜션","섬인(in)섬"
        		,"The 설레임","강화 J모텔 펜션","순천만 1번지 펜션","양평 메이플679 펜션","우리피크&#45792;펜션","행복한家1","강화 별헤는집A"
        		,"가평 도원VIP 펜션","제주 2jj 게스트하우스","펜션명사20","강화도스파카라반1호점","제주 cs빌리지 펜션","로드47펜션","러브풀(Love full)"
        		,"홍천 7080 펜션","캠프Y (사용안함)","더빌라 청평2호점","YJ리조트","B612-이전버전","스카이파크 제주 1호점","[펜션]Y리조트 제주"
        		,"JS 호텔 분당","B-STAY 부천호텔","퍼스트70 호텔","밸류호텔 서귀포 JS"
        		
        };
        
        for(String item : faclArray) {
        	logger.debug("[FINAL] {}", common.getMorphologicalAnalysis("kor", item, ",")); 
        }
        
        // 별 > 명사로 안나옴
    }
    
    //@Test
    public void testSyntax() {
    	String testData = null;
    	logger.info( "{}", "abc".equals(testData)  );
    	DecimalFormat df = new DecimalFormat("000.00");
    	BigDecimal calc = ((new BigDecimal(9).divide(new BigDecimal(14), MathContext.DECIMAL32)).multiply(new BigDecimal(100)));
    	logger.debug("==> {}", df.format(calc));
    	
    	logger.debug("## {} / {}", InterfaceFactory.getOptionalApps().getSmsConfig().getRestURI(), InterfaceFactory.getOptionalApps().getSmsConfig().getEncoding());
    	
    }
}
