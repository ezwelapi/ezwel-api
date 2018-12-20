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
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.ko.morph.AnalysisOutput;
import org.apache.lucene.analysis.ko.morph.CompoundEntry;
import org.apache.lucene.analysis.ko.morph.CompoundNounAnalyzer;
import org.apache.lucene.analysis.ko.morph.MorphAnalyzer;
import org.apache.lucene.analysis.ko.morph.MorphException;
import org.apache.lucene.analysis.ko.morph.WordSegmentAnalyzer;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

/**
 * A Korean Analyzer
 */
@Component
@APIType(description="한글 형태소 분석기")
public class KoreanAnalyzer extends StopwordAnalyzerBase {
	
	private static final Logger logger = LoggerFactory.getLogger(KoreanAnalyzer.class);
	
	/** Default maximum allowed token length */
	public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;

	private APIUtil apiUtil;
	
	private boolean bigrammable = false;
	private boolean hasOrigin = false;
	private boolean exactMatch = false;
	private boolean originCNoun = true;
	private boolean queryMode = false;
	private boolean wordSegment = false;

	/**
	 * An unmodifiable set containing some common words that are usually not useful
	 * for searching.
	 */
	
	public static final CharArraySet STOP_WORDS_SET;
	
	static {
		try {
			STOP_WORDS_SET = loadStopwordSet(false, KoreanAnalyzer.class, "stopwords.txt", "#");
		} catch (IOException ioe) {
			throw new Error("Cannot load stop words", ioe);
		}

	}

	
	public KoreanAnalyzer() {
		this(STOP_WORDS_SET);
		if(apiUtil == null) {
			apiUtil = new APIUtil();
		}
	}

	public KoreanAnalyzer(boolean exactMatch) {
		this(STOP_WORDS_SET);
		this.exactMatch = exactMatch;
	}

	public KoreanAnalyzer(String[] stopWords) {
		this(StopFilter.makeStopSet(stopWords));
	}

	public KoreanAnalyzer(Path stopwords) throws IOException {
		this(loadStopwordSet(stopwords));
	}

	public KoreanAnalyzer(Path stopwords, String encoding) throws IOException {
		this(loadStopwordSet(stopwords));
	}

	public KoreanAnalyzer(Reader stopwords) throws IOException {
		this(loadStopwordSet(stopwords));
	}

	public KoreanAnalyzer(CharArraySet stopWords) {
		super(stopWords);
	}

	@Override
	@APIOperation
	protected TokenStreamComponents createComponents(String s) {
		final KoreanTokenizer src = new KoreanTokenizer();
		TokenStream tok = new LowerCaseFilter(src);
		tok = new ClassicFilter(tok);
		tok = new KoreanFilter(tok, bigrammable, hasOrigin, exactMatch, originCNoun, queryMode);
		if (wordSegment)
			tok = new WordSegmentFilter(tok, hasOrigin);
		tok = new HanjaMappingFilter(tok);
		tok = new PunctuationDelimitFilter(tok);
		tok = new StopFilter(tok, stopwords);
		return new TokenStreamComponents(src, tok) {
			@Override
			protected void setReader(final Reader reader) throws IOException {
//        src.setMaxTokenLength(KoreanAnalyzer.this.maxTokenLength);
				super.setReader(reader);
			}
		};
	}

	/**
	 * determine whether the bigram index term is returned or not if a input word is
	 * failed to analysis If true is set, the bigram index term is returned. If
	 * false is set, the bigram index term is not returned.
	 */
	@APIOperation
	public void setBigrammable(boolean is) {
		bigrammable = is;
	}

	/**
	 * determin whether the original term is returned or not if a input word is
	 * analyzed morphically.
	 */
	@APIOperation(description="")
	public void setHasOrigin(boolean has) {
		hasOrigin = has;
	}

	/**
	 * determin whether the original compound noun is returned or not if a input
	 * word is analyzed morphically.
	 */
	@APIOperation(description="")
	public void setOriginCNoun(boolean cnoun) {
		originCNoun = cnoun;
	}

	/**
	 * determin whether the original compound noun is returned or not if a input
	 * word is analyzed morphically.
	 */
	@APIOperation(description="")
	public void setExactMatch(boolean exact) {
		exactMatch = exact;
	}

	/**
	 * determin whether the analyzer is running for a query processing
	 */
	@APIOperation(description="")
	public void setQueryMode(boolean mode) {
		queryMode = mode;
	}

	/**
	 * determin whether word segment analyzer is processing
	 */
	@APIOperation(description="")
	public boolean isWordSegment() {
		return wordSegment;
	}

	@APIOperation(description="")
	public void setWordSegment(boolean wordSegment) {
		this.wordSegment = wordSegment;
	}

	@APIOperation(description="한글 형태소 분석 - Full") 
	public Set<String> getKoreanMorphologicalAnalysis(String sentence) {
		Set<String> out = new LinkedHashSet<String>();

		if(APIUtil.isEmpty(sentence)) {
			return out; 
		}
		
		apiUtil = (APIUtil) LApplicationContext.getBean(apiUtil, APIUtil.class);
		
		//영문삭제
		sentence = apiUtil.toHalfChar(sentence).replaceAll("(?i)([\\p{S}\\p{P}ºa-zA-Z]+)", OperateConstants.STR_BLANK);
		
		try {
			out.addAll(extractNoun(sentence));
			out.addAll(guideWord(sentence));
			out.addAll(compoundNounAnalyze(sentence));
			out.addAll(wordSpaceAnalyze(sentence));
			out.addAll(morphAnalyze(sentence));
			out.addAll(basicAnalyze(sentence));
		} catch (MorphException e) {
			logger.debug("한글 형태소 전체 기능 분석 장애 발생", e);
		}
		
		return out;
	}
	

    @APIOperation(description="한글 형태소 분석")
    public List<String> basicAnalyze(String sentence) {
    	logger.debug("[START] KoreanMorphologicalAnalysis INPUT : {}", sentence);
    	List<String> out = new ArrayList<String>();
		
    	if(APIUtil.isEmpty(sentence)) {
    		return out;
		}    	
    	
    	KoreanAnalyzer korean = null;
		TokenStream ts = null;
		CharTermAttribute termAtt = null;
		
		
		try {
	    	korean = new KoreanAnalyzer();
	    	korean.setQueryMode(false);
	    	
			ts = korean.tokenStream("bogus", sentence);
		    termAtt = ts.addAttribute(CharTermAttribute.class);
		    ts.reset();
		    
		    while (ts.incrementToken()) {
		    	if(APIUtil.isNotEmpty(termAtt.toString().trim())) {
		    		out.add(termAtt.toString());
		    	}
		    }
		}
		catch(IOException e) {
			logger.error("[IOException] 한글 형태소 분석도중 장애 발생", e);
		}
		catch(Exception e) {
			logger.error("[Exception] 한글 형태소 분석도중 장애 발생", e);
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
		}
		
		logger.debug("[END] KoreanMorphologicalAnalysis OUTPUT : {}", out);
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
	public List<String> morphAnalyze(String source) throws MorphException {
		MorphAnalyzer maAnal = new MorphAnalyzer(); // 형태소 분석기

		List<String> out = new ArrayList<String>();

		StringTokenizer stok = new StringTokenizer(source, " ");
		while (stok.hasMoreTokens()) {
			String token = stok.nextToken();
			List<AnalysisOutput> outList = maAnal.analyze(token);
			for (AnalysisOutput o : outList) {
				out.addAll(buildOutputString(o, false));
			}
		}
		return out;
	}

	/**
	 * @Method Name : wordSpaceAnalyze
	 * @변경이력 :
	 * @Method 설명 : 뛰어 쓰기
	 * @param source
	 * @param force
	 * @return
	 * @throws MorphException
	 */
    @APIOperation(description="한글 형태소 분석 - 뛰어 쓰기")
	public List<String> wordSpaceAnalyze(String source, boolean force) throws MorphException {
		WordSegmentAnalyzer wsAnal = new WordSegmentAnalyzer();

		List<String> out = new ArrayList<String>();

		String s;
		if (force) {
			s = source.replace(" ", "");
		}
		else {
			s = source;
		}
		
		List<List<AnalysisOutput>> outList = wsAnal.analyze(s);
		
		for (List<AnalysisOutput> o : outList) {
			for (AnalysisOutput analysisOutput : o) {
				if(APIUtil.isNotEmpty(analysisOutput.getSource().trim())) {
					out.add(analysisOutput.getSource().trim()); 
				}				
			}
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
	 * @Method 설명 : 복합 명사 분해
	 * @param source
	 * @return
	 * @throws MorphException
	 */
    @APIOperation(description="한글 형태소 분석")
	public List<String> compoundNounAnalyze(String source) throws MorphException {
		CompoundNounAnalyzer cnAnal = new CompoundNounAnalyzer(); // 복합어 분석기

		List<String> out = new ArrayList<String>();

		List<CompoundEntry> outList = cnAnal.analyze(source);
		for (CompoundEntry o : outList) {
			if(APIUtil.isNotEmpty(o.getWord().trim())) {
				out.add(o.getWord().trim()); 
			}			
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
    @APIOperation(description="한글 형태소 분석")
	public List<String> guideWord(String source) throws MorphException {
		MorphAnalyzer maAnal = new MorphAnalyzer(); // 형태소 분석기

		StringTokenizer stok = new StringTokenizer(source, " "); // 쿼리문을 뛰어쓰기 기준으로 토큰화

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
    @APIOperation(description="한글 형태소 분석")
	public List<String> extractNoun(String searchQuery) throws MorphException {
		List<String> nounList = new ArrayList<String>();

		MorphAnalyzer maAnal = new MorphAnalyzer(); // 형태소 분석기
		StringTokenizer stok = new StringTokenizer(searchQuery, " "); // 쿼리문을 뛰어쓰기 기준으로 토큰화

		// 색인어 분석기를 통해 토큰에서 색인어 추출
		while (stok.hasMoreTokens()) {
			String token = stok.nextToken();

			// 형태소 분석
			List<AnalysisOutput> indexList = maAnal.analyze(token);

			for (AnalysisOutput morpheme : indexList)
				// 명사 추출
				if (morpheme.getPos() == 'N') {
					if(APIUtil.isNotEmpty(morpheme.getStem().trim())) {
						nounList.add(morpheme.getStem().trim()); 
					}
				}
		}

		return nounList;
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
