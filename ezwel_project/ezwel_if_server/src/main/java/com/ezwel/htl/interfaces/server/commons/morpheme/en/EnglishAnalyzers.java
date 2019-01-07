package com.ezwel.htl.interfaces.server.commons.morpheme.en;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

@Component
@APIType(description="영문 형태소 분석기")
public class EnglishAnalyzers {

	private static final Logger logger = LoggerFactory.getLogger(EnglishAnalyzers.class);
	
	private APIUtil apiUtil;
	
	private static List<String> ENGLISH_STOP_WORDS;
	
	private static List<String> STOP_EXCLUSION_WORDS;

	private static final Analyzer[] ENGLISH_ANALYZERS;
	
	private static final CharArraySet STOP_SET; 
			
	private static final CharArraySet EXCLUSION_ENGLISH_STOP_WORDS;
	
	private static final CharArraySet STOP_EXCLUSION_SET; 
			
	private static boolean IS_LOGGING;
	
	static {
		
		IS_LOGGING = false;
		
		ENGLISH_STOP_WORDS = Arrays.asList(new String[] {
    		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",",",
    		"about","above described","abovementioned above said","abrupt","according","accordingly","all",
    		"also","although","among","an","and","another","any","anyway","are","aren't","became","become","becomes",
    		"becoming","been","being","below-mentioned","besides","but","by","c'mon","c's","came","can't","cannot",
    		"certainly","claim","claimed","claims","comprise","comprised","comprises","could","couldn't","described",
    		"did","do","does","doesn't","doing","don't","done","during","e.g.","each","eg","either","etc","ever","for",
    		"from","further","furthermore","generally","goes","got","gotten","had","hadn't","has","hasn't","have",
    		"haven't","having","hence","her","hereafter","hereby","herein","hereinafter","hereinbefore","hereinbelow",
    		"hereof","hereon","hereto","heretofore","herewith","him","his","however","i.e.","ie","if","in","include",
    		"included","includes","including","into","is","isn't","it's","its","itself","let","let's","made","make",
    		"makes","making","may","me","means","meant","meanwhile","merely","more","moreover","most","mostly","must",
    		"my","myself","namely","nor","not","now","of","often","on","or","ought","our","ours","ourselves","particularly",
    		"really","relatively","respectively","said","same","she","should","shouldn't","since","so","some","than",
    		"that","that's","thats","the","their","theirs","them","then","thereby","therefore","therein","thereof",
    		"thereon","thereto","therewith","these","they","this","those","through","thus","to","too","un",
    		"unfortunately","unless","unto","upon","used","useful","usefully","using","usually","was","wasn't","we","we'd",
    		"we'll","were","what","what's","when","where","where's","whereafter","whereas","whereby","wherein","whereupon",
    		"which","while","as","at","be","it","no","such","there","will","with"
        });
		
		STOP_EXCLUSION_WORDS = Arrays.asList(new String[] {
        		//"priority","use"
        });
        
        STOP_EXCLUSION_SET = new CharArraySet(STOP_EXCLUSION_WORDS.size(), false);
        STOP_EXCLUSION_SET.addAll(STOP_EXCLUSION_WORDS);
        
        EXCLUSION_ENGLISH_STOP_WORDS = CharArraySet.unmodifiableSet(STOP_EXCLUSION_SET);		
		
		STOP_SET = new CharArraySet(ENGLISH_STOP_WORDS, false);
		
		ENGLISH_ANALYZERS = new Analyzer[]{
				new CustomEnglishAnalyzer(STOP_SET,/* EXCLUSION_ENGLISH_STOP_WORDS,*/ true),
				new StandardAnalyzer(STOP_SET),
				/*new WhitespaceAnalyzer(),*/
				/*new SimpleAnalyzer(),*/
				/*new StopAnalyzer(STOP_SET),*/
				/*new org.apache.lucene.analysis.en.EnglishAnalyzer(STOP_SET, EXCLUSION_ENGLISH_STOP_WORDS),*/
				//new SnowballAnalyzer("English", StopAnalyzer.ENGLISH_STOP_WORDS_SET),
				//new SnowballAnalyzer("English", ENGLISH_STOP_WORDS),
			 };
	}
	// org.apache.lucene.analysis.StopwordAnalyzerBase
	
	public EnglishAnalyzers() {
		if(apiUtil == null) {
			apiUtil = new APIUtil();
		}
	}
	/**
	 * 상단에 등록된 Analyzer 로 형태소분석을 수행합니다.
	 * @param text
	 * @return
	 * @throws IOException
	 */
	@APIOperation(description="영문 형태소 복합 분석")
	public Set<String> getEnglishMorphologicalAnalysis(String sentence)  {
		if(IS_LOGGING) {
			logger.debug("[START-ENGLISH] getEnglishMorphologicalAnalysis input : {}", sentence);
		}
		
		//각 형태소 분석 결과내 중복 제거를 위한 Set
		Set<String> out = new LinkedHashSet<String>();

		if(APIUtil.isEmpty(sentence)) {
			return out; 
		}
		
		apiUtil = (APIUtil) LApplicationContext.getBean(apiUtil, APIUtil.class);
		
		TokenStream ts = null;
		Analyzer analyzer = null;
		CharTermAttribute termAtt = null;

		try {

			//설정된 영문 형태소 분석기 개수만큼 분석한다.
			for (int i = 0; i < ENGLISH_ANALYZERS.length; i++) {
				
				try {
					analyzer = ENGLISH_ANALYZERS[i];
					
					if(IS_LOGGING) {
						logger.debug("- Analyzer Name : {}", analyzer.getClass().getName());
					}
				
					//문장당 형태소 분석
					ts = analyzer.tokenStream("contents", new StringReader(sentence));
					termAtt = ts.getAttribute(CharTermAttribute.class);
					ts.reset();
			       
					while(ts.incrementToken()) {
						if(IS_LOGGING) {
							logger.debug("- English Morpheme : {}", termAtt.toString());
						}
						if(APIUtil.isNotEmpty(termAtt.toString().trim())) {
							out.add(termAtt.toString().trim().toLowerCase()); 
						}
					}
				
				} catch (Exception e) {
					logger.debug("[LOOP-Exception] 영문 형태소 전체 기능 분석 장애 발생", e);
				} finally {
					
					if(ts != null) {
						try {
							ts.end();
							ts.close();
						} catch (IOException e) {
							logger.error("[IOException] 영문 형태소 분석기 tokenStream을 닫는도중 장애 발생", e);
						}
					}
				}
			}
		}
		catch(Exception e) {
			logger.error("[Exception] 영문 형태소 분석기 장애 발생", e);
		}
		
		if(IS_LOGGING) {
			logger.debug("[END-ENGLISH] getEnglishMorphologicalAnalysis output : {}", out);
		}
		return out;
	}
}
