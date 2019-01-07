package com.ezwel.htl.interfaces.server.commons.morpheme.ko;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;


@APIType(description="PunctuationDelimitFilterFactory")
public class PunctuationDelimitFilterFactory extends TokenFilterFactory {
    /**
     * Initialize this factory via a set of key-value pairs.
     *
     * @param args
     */
    public PunctuationDelimitFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public TokenStream create(TokenStream input) {
        return new PunctuationDelimitFilter(input);
    }
}
