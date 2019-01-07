package ezwel_if_server.morpheme.v5;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.server.commons.morpheme.en.EnglishAnalyzers;
import com.ezwel.htl.interfaces.server.commons.morpheme.ko.KoreanAnalyzer;

import junit.framework.TestCase;

public class TestKoreanAnalyzer extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(TestKoreanAnalyzer.class);
	
	public void testKoreanAnalyzer() throws Exception {
		
		String[] inputs = new String[] {
			"정보화용역사업"
			,"신라호텔(주)"
			,"실라스테이서초"
			,"켄싱턴리조트제주"
			,"더플라자호텔"
			,"신라호텔(주)"
			,"하이원리조트"
			,"인터컨티넨탈호텔"
			,"세종호텔"
			,"오라카이 인사동스위츠"
			,"더케이호텔서울"
			,"CS Premier Hotel"
			,"쉐라톤 서울 디큐브시티"
			,"신림 호텔 어반(Sillim Hotel Urban)"
			,"베스트 웨스턴 프리미어 구로 호텔"
			,"라마다 서울"
			,"서울해군호텔"
			,"강남아르누보씨티"
			,"베스트 웨스턴 프리미어 서울가든호텔"
			,"알티호텔"
			,"新罗首尔"
		};

		KoreanAnalyzer korean = new KoreanAnalyzer();
		logger.debug("korean : {}", korean);
		korean.setQueryMode(false);
		StringBuilder actual = null;

		for(String input : inputs) {
		
			actual = new StringBuilder();
			TokenStream ts = korean.tokenStream("bogus", input);
		    CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
		    ts.reset();
		    while (ts.incrementToken()) {
		      actual.append(termAtt.toString());
		      actual.append('/');
		    }
		    logger.debug("{}", actual);
		    ts.end();
		    ts.close();
		}
		
		
		
		EnglishAnalyzers englishAnalyzers = new EnglishAnalyzers();
		
		List<String> eng = null;
		String word = null;
		
		word = "Namhae Alhambra Pension";
		eng = new ArrayList<String>(englishAnalyzers.getEnglishMorphologicalAnalysis(word));
		for(String test : eng) {
			logger.debug("{} : {}", word, test);
		}
		
		logger.debug("-----------------------------");
		
		word = "Namhae Soulmate Pension";
		eng = new ArrayList<String>(englishAnalyzers.getEnglishMorphologicalAnalysis(word));
		for(String test : eng) {
			logger.debug("{} : {}", word, test);
		}
	}
	
	public void testConvertUnicode() throws Exception {
		char c = 0x772C;
		logger.debug("{}", c);
		
		int code = '領';
		logger.debug("{}", code);
		
		logger.debug("{}", Character.getType('&'));
	}
	
	
}
