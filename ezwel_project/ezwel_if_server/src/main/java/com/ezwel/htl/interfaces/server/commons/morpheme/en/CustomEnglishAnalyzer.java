package com.ezwel.htl.interfaces.server.commons.morpheme.en;


import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;


public final class CustomEnglishAnalyzer extends StopwordAnalyzerBase
{
	private final Set stemExclusionSet;

	private boolean usePorterStemFilter = true;

    private static class DefaultSetHolder {

        static final CharArraySet DEFAULT_STOP_SET;

        static {
            DEFAULT_STOP_SET = StandardAnalyzer.STOP_WORDS_SET;
        }
    }

    public static Set getDefaultStopSet()
    {
        return DefaultSetHolder.DEFAULT_STOP_SET;
    }

    public CustomEnglishAnalyzer(boolean usePorterStemFilter)
    {
        this(DefaultSetHolder.DEFAULT_STOP_SET, usePorterStemFilter);
    }

    public CustomEnglishAnalyzer(CharArraySet stopwords, boolean usePorterStemFilter)
    {
        this(stopwords, (CharArraySet.EMPTY_SET), usePorterStemFilter);
    }

    public CustomEnglishAnalyzer(CharArraySet stopwords, Set stemExclusionSet, boolean usePorterStemFilter)
    {
        super(stopwords);
        this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(stemExclusionSet));
        this.usePorterStemFilter = usePorterStemFilter;
    }

    @Override
	protected TokenStreamComponents createComponents(String fieldName)
    {
        Tokenizer source = new StandardTokenizer();
        TokenStream result = new StandardFilter(source);
       	result = new EnglishPossessiveFilter(result); //3.6
        result = new LowerCaseFilter(result);
        result = new StopFilter(result, stopwords);
        if(usePorterStemFilter) {
        	result = new PorterStemFilter(result);
        }
        return new TokenStreamComponents(source, result);
    }


}
