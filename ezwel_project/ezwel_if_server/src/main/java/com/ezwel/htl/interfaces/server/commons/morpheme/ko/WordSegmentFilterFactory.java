package com.ezwel.htl.interfaces.server.commons.morpheme.ko;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;

@APIType(description="WordSegmentFilterFactory")
public class WordSegmentFilterFactory extends TokenFilterFactory {

	private static final String HAS_ORIGIN_PARAM = "hasOrigin";
	
	private final boolean hasOrigin;
	  
	public WordSegmentFilterFactory(Map<String, String> args) {
		super(args);
		hasOrigin = getBoolean(args, HAS_ORIGIN_PARAM, true);
	}

	@Override
	public TokenStream create(TokenStream input) {
		return new WordSegmentFilter(input, hasOrigin);
	}

}
