package com.ezwel.htl.interfaces.server.commons.morpheme.ko;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.morph.AnalysisOutput;
import org.apache.lucene.analysis.ko.morph.CompoundEntry;
import org.apache.lucene.analysis.ko.morph.CompoundNounAnalyzer;
import org.apache.lucene.analysis.ko.morph.MorphAnalyzer;
import org.apache.lucene.analysis.ko.morph.MorphException;
import org.apache.lucene.analysis.ko.morph.WordSegmentAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

/**
 * A Korean Analyzer
 */
@Component
@APIType(description="한글 형태소 분석기")
public class KoreanAnalyzers {
	
	private static final Logger logger = LoggerFactory.getLogger(KoreanAnalyzers.class);

	public static final boolean IS_LOGGING = false;
	
	private KoreanAnalyzer koreanAnalyzer;

	private APIUtil apiUtil;
	

	@APIOperation(description="한글 형태소 분석 - Full") 
	public Set<String> getKoreanMorphologicalAnalysis(String sentence) {
		if(IS_LOGGING) {
			logger.debug("[START-KOREAN] getKoreanMorphologicalAnalysis input : {}", sentence);
		}
		
		//각 형태소 분석 결과내 중복 제거를 위한 Set
		Set<String> out = new LinkedHashSet<String>();

		if(APIUtil.isEmpty(sentence)) {
			return out; 
		}
		
		//WARN : getBeanInstance is test getBean
		apiUtil = (APIUtil) LApplicationContext.getBeanInstance(apiUtil, APIUtil.class);
		
		try {
			
			out.addAll(extractNoun(sentence));
			out.addAll(guideWord(sentence));
			out.addAll(compoundNounAnalyze(sentence));
			out.addAll(wordSpaceAnalyze(sentence));
			out.addAll(morphAnalyze(sentence));
			out.addAll(basicAnalyze(sentence));
			
		} catch (MorphException e) {
			logger.debug("[MorphException] 한글 형태소 전체 기능 분석 장애 발생", e);
		} catch (IOException e) {
			logger.debug("[IOException] 한글 형태소 전체 기능 분석 장애 발생", e);
		}
		
		if(IS_LOGGING) {
			logger.debug("[END-KOREAN] getKoreanMorphologicalAnalysis output : {}", out);
		}
		return out;
	}
	

	@APIOperation(description="한글 명사/복합명사 형태소 분석") 
	public Set<String> getKoreanNounAllAnalysis(String sentence) {
		if(IS_LOGGING) {
			logger.debug("[START-KOREAN] getKoreanMorphologicalAnalysis input : {}", sentence);
		}
		
		//각 형태소 분석 결과내 중복 제거를 위한 Set
		Set<String> out = new LinkedHashSet<String>();

		if(APIUtil.isEmpty(sentence)) {
			return out; 
		}
		
		//WARN : getBeanInstance is test getBean
		apiUtil = (APIUtil) LApplicationContext.getBeanInstance(apiUtil, APIUtil.class);
		
		try {
			
			out.addAll(extractNoun(sentence));
			out.addAll(compoundNounAnalyze(sentence));
			out.addAll(basicAnalyze(sentence));
			
		} catch (MorphException e) {
			logger.debug("[MorphException] 한글 형태소 전체 기능 분석 장애 발생", e);
		} catch (IOException e) {
			logger.debug("[IOException] 한글 형태소 전체 기능 분석 장애 발생", e);
		}
		
		if(IS_LOGGING) {
			logger.debug("[END-KOREAN] getKoreanMorphologicalAnalysis output : {}", out);
		}
		return out;
	}
	
	
    @APIOperation(description="한글 형태소 분석")
    public List<String> basicAnalyze(String sentence) throws IOException {
    	if(IS_LOGGING) {
    		logger.debug("[START-KOREAN] basicAnalyze input : {}", sentence);
    	}
    	
    	List<String> out = new ArrayList<String>();
		
    	if(APIUtil.isEmpty(sentence)) {
    		return out;
		}    	
    	
    	koreanAnalyzer = (KoreanAnalyzer) LApplicationContext.getBeanInstance(koreanAnalyzer, KoreanAnalyzer.class);
    	
		TokenStream ts = null;
		CharTermAttribute termAtt = null;
		
		try {
			koreanAnalyzer = new KoreanAnalyzer();
	    	koreanAnalyzer.setQueryMode(false);
	    	
			ts = koreanAnalyzer.tokenStream("bogus", sentence);
		    termAtt = ts.addAttribute(CharTermAttribute.class);
		    ts.reset();
		    
		    while (ts.incrementToken()) {
		    	if(APIUtil.isNotEmpty(termAtt.toString().trim())) {
		    		out.add(termAtt.toString());
		    	}
		    }
		}
		finally {
			if(ts != null) {
			    try {
					ts.end();
					ts.close();
				} catch (IOException e) {
					logger.error("[IOException] 한글 형태소 분석기 tokenStream을 닫는도중 장애 발생", e);
				}
			}
			
			if(koreanAnalyzer != null) {
				koreanAnalyzer.close();
			}
		}
		
		if(IS_LOGGING) {
			logger.debug("[END-KOREAN] basicAnalyze output : {}", out);
		}
		return out;
    }

	/**
	 * @Method Name : morphAnalyze
	 * @변경이력 :
	 * @Method 설명 : 형태소 분석
	 * @param source
	 * @return
	 * @throws MorphException
	 */
    @APIOperation(description="한글 형태소 분석")
	public List<String> morphAnalyze(String sentence) throws MorphException {
    	if(IS_LOGGING) {
    		logger.debug("[START-KOREAN] morphAnalyze input : {}", sentence);
    	}
		MorphAnalyzer maAnal = new MorphAnalyzer(); // 형태소 분석기

		List<String> out = new ArrayList<String>();

		StringTokenizer stok = new StringTokenizer(sentence, " ");
		while (stok.hasMoreTokens()) {
			String token = stok.nextToken();
			List<AnalysisOutput> outList = maAnal.analyze(token);
			for (AnalysisOutput o : outList) {
				out.addAll(buildOutputString(o, false));
			}
		}
		if(IS_LOGGING) {
			logger.debug("[END-KOREAN] morphAnalyze output : {}", out);
		}
		return out;
	}

	/**
	 * @Method Name : wordSpaceAnalyze
	 * @변경이력 :
	 * @Method 설명 : 뛰어 쓰기 분석
	 * @param source
	 * @param force
	 * @return
	 * @throws MorphException
	 */
    @APIOperation(description="뛰어 쓰기 분석")
	public List<String> wordSpaceAnalyze(String sentence, boolean force) throws MorphException {
    	if(IS_LOGGING) {
    		logger.debug("[START-KOREAN] wordSpaceAnalyze input : {}, remove whitespace : {}", sentence, force);
    	}
    	
		WordSegmentAnalyzer wsAnal = new WordSegmentAnalyzer();

		List<String> out = new ArrayList<String>();

		String s;
		if (force) {
			s = sentence.replace(" ", "");
		}
		else {
			s = sentence;
		}
		
		List<List<AnalysisOutput>> outList = wsAnal.analyze(s);
		
		for (List<AnalysisOutput> o : outList) {
			for (AnalysisOutput analysisOutput : o) {
				if(APIUtil.isNotEmpty(analysisOutput.getSource().trim())) {
					out.add(analysisOutput.getSource().trim()); 
				}				
			}
		}

		if(IS_LOGGING) {
			logger.debug("[END-KOREAN] wordSpaceAnalyze output : {}", out);
		}
		return out;
	}

	/**
	 * @Method Name : wordSpaceAnalyze
	 * @변경이력 :
	 * @Method 설명 : 뛰어 쓰기
	 * @param source
	 * @return
	 * @throws MorphException
	 */
    @APIOperation(description="한글 형태소 분석 - 뛰어 쓰기")
	public List<String> wordSpaceAnalyze(String source) throws MorphException {
		return wordSpaceAnalyze(source, false);
	}

	/**
	 * @Method Name : compoundNounAnalyze
	 * @변경이력 :
	 * @Method 설명 : 복합 명사 형태소 분석
	 * @param source
	 * @return
	 * @throws MorphException
	 */
    @APIOperation(description="복합 명사 형태소 분석")
	public List<String> compoundNounAnalyze(String sentence) throws MorphException {
    	if(IS_LOGGING) {
    		logger.debug("[START-KOREAN] compoundNounAnalyze input : {}", sentence);
    	}
    	
		CompoundNounAnalyzer cnAnal = new CompoundNounAnalyzer(); // 복합어 분석기

		List<String> out = new ArrayList<String>();

		List<CompoundEntry> outList = cnAnal.analyze(sentence);
		for (CompoundEntry o : outList) {
			if(APIUtil.isNotEmpty(o.getWord().trim())) {
				out.add(o.getWord().trim()); 
			}			
		}

		if(IS_LOGGING) {
			logger.debug("[END-KOREAN] compoundNounAnalyze output : {}", out);
		}
		return out;
	}

	/**
	 * @Method Name : guideWord
	 * @변경이력 :
	 * @Method 설명 : 색인어 추출
	 * @param source
	 * @return
	 * @throws MorphException
	 */
    @APIOperation(description="한글 색인어 분석")
	public List<String> guideWord(String sentence) throws MorphException {
    	if(IS_LOGGING) {
    		logger.debug("[START-KOREAN] guideWord input : {}", sentence);
    	}
    	
		MorphAnalyzer maAnal = new MorphAnalyzer(); // 형태소 분석기

		StringTokenizer stok = new StringTokenizer(sentence, " "); // 쿼리문을 뛰어쓰기 기준으로 토큰화

		List<String> out = new ArrayList<String>();

		while (stok.hasMoreTokens()) {

			String token = stok.nextToken();

			List<AnalysisOutput> outList = maAnal.analyze(token);
			for (AnalysisOutput o : outList) {

				out.add(o.getStem());
				for (CompoundEntry s : o.getCNounList()) {
					if(APIUtil.isNotEmpty(s.getWord().trim())) {
						out.add(s.getWord().trim()); 
					}					
				}
			}
		}
		
		if(IS_LOGGING) {
			logger.debug("[END-KOREAN] guideWord output : {}", out);
		}
		return out;
	}

	/**
	 * @Method Name : extractNoun
	 * @변경이력 :
	 * @Method 설명 : 명사 추출
	 * @param searchQuery
	 * @return
	 * @throws MorphException
	 */
    @APIOperation(description="한글 명사 형태소 분석")
	public List<String> extractNoun(String sentence) throws MorphException {
    	if(IS_LOGGING) {
    		logger.debug("[START-KOREAN] extractNoun input : {}", sentence);
    	}
    	
		List<String> out = new ArrayList<String>();

		MorphAnalyzer maAnal = new MorphAnalyzer(); // 형태소 분석기
		StringTokenizer stok = new StringTokenizer(sentence, " "); // 쿼리문을 뛰어쓰기 기준으로 토큰화

		// 색인어 분석기를 통해 토큰에서 색인어 추출
		while (stok.hasMoreTokens()) {
			String token = stok.nextToken();

			// 형태소 분석
			List<AnalysisOutput> indexList = maAnal.analyze(token);

			for (AnalysisOutput morpheme : indexList)
				// 명사 추출
				if (morpheme.getPos() == 'N') {
					if(APIUtil.isNotEmpty(morpheme.getStem().trim())) {
						out.add(morpheme.getStem().trim()); 
					}
				}
		}

		if(IS_LOGGING) {
			logger.debug("[END-KOREAN] extractNoun output : {}", out);
		}
		return out;
	}

    @APIOperation(description="한글 형태소 분석 결과 생성 유틸")
    private List<String> buildOutputString(AnalysisOutput o, boolean isMorpType) {
    	
        List<String> out = new ArrayList<String>();
        
        out.add(buildTypeString(o.getStem(), o.getPos(), isMorpType));
        if (o.getNsfx() != null) {
        	out.add(buildTypeString(o.getNsfx(), 's', isMorpType));
        }
        if ((o.getPatn() == 2) || (o.getPatn() == 22))
        {
        	out.add(buildTypeString(o.getJosa(), 'j', isMorpType));
        }
        else if (o.getPatn() == 3)
        {
        	out.add(buildTypeString(o.getVsfx(), 't', isMorpType));
          if (o.getPomi() != null) {
        	  out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString(o.getEomi(), 'e', isMorpType));
        }
        else if (o.getPatn() == 4)
        {
        	out.add(buildTypeString(o.getVsfx(), 't', isMorpType));
          if (o.getPomi() != null) {
        	  out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString((String)o.getElist().get(0), 'n', isMorpType));
          out.add(buildTypeString(o.getJosa(), 'j', isMorpType));
        }
        else if (o.getPatn() == 5)
        {
        	out.add(buildTypeString(o.getVsfx(), 't', isMorpType));
          out.add(buildTypeString((String)o.getElist().get(0), 'c', isMorpType));
          out.add(buildTypeString(o.getXverb(), 'W', isMorpType));
          if (o.getPomi() != null) {
            out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString(o.getEomi(), 'e', isMorpType));
        }
        else if (o.getPatn() == 6)
        {
          out.add(buildTypeString(o.getJosa(), 'j', isMorpType));
          out.add(buildTypeString((String)o.getElist().get(0), 't', isMorpType));
          if (o.getPomi() != null) {
            out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString(o.getEomi(), 'e', isMorpType));
        }
        else if (o.getPatn() == 7)
        {
          out.add(buildTypeString(o.getVsfx(), 't', isMorpType));
          out.add(buildTypeString((String)o.getElist().get(1), 'c', isMorpType));
          out.add(buildTypeString(o.getXverb(), 'W', isMorpType));
          if (o.getPomi() != null) {
            out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString((String)o.getElist().get(0), 'n', isMorpType));
          out.add(buildTypeString(o.getJosa(), 'j', isMorpType));
        }
        else if (o.getPatn() == 11)
        {
          if (o.getPomi() != null) {
            out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString(o.getEomi(), 'e', isMorpType));
        }
        else if (o.getPatn() == 12)
        {
          out.add(buildTypeString((String)o.getElist().get(0), 'n', isMorpType));
          out.add(buildTypeString(o.getJosa(), 'j', isMorpType));
        }
        else if (o.getPatn() == 13)
        {
          out.add(buildTypeString((String)o.getElist().get(0), 'n', isMorpType));
          out.add(buildTypeString((String)o.getElist().get(1), 's', isMorpType));
          if (o.getPomi() != null) {
            out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString(o.getEomi(), 'e', isMorpType));
        }
        else if (o.getPatn() == 14)
        {
          out.add(buildTypeString((String)o.getElist().get(0), 'c', isMorpType));
          out.add(buildTypeString(o.getXverb(), 'W', isMorpType));
          if (o.getPomi() != null) {
            out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString(o.getEomi(), 'e', isMorpType));
        }
        else if (o.getPatn() == 15)
        {
          out.add(buildTypeString((String)o.getElist().get(1), 'c', isMorpType));
          out.add(buildTypeString(o.getXverb(), 'W', isMorpType));
          if (o.getPomi() != null) {
            out.add(buildTypeString(o.getPomi(), 'f', isMorpType));
          }
          out.add(buildTypeString((String)o.getElist().get(0), 'n', isMorpType));
          out.add(buildTypeString(o.getJosa(), 'j', isMorpType));
        }
        
        return out;
    }
    
    private String buildTypeString(String word, char type, boolean isMorpType)
    {
      StringBuffer sb = new StringBuffer();
      sb.append(word);
      
      if(isMorpType) {
	      sb.append("(");
	      sb.append(type);
	      sb.append(")");
      }
      
      return sb.toString();
    }
}
