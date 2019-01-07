package com.ezwel.htl.interfaces.server.commons.morpheme.ko;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;

/**
 * Created by SooMyung(soomyung.lee@gmail.com) on 2014. 7. 30.
 */

@APIType(description="한자 매핑 필터 생성기")
public class HanjaMappingFilterFactory extends TokenFilterFactory {
    /**
     * Initialize this factory via a set of key-value pairs.
     *
     * @param args
     */
    public HanjaMappingFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public TokenStream create(TokenStream input) {
        return new HanjaMappingFilter(input);
    }
}
