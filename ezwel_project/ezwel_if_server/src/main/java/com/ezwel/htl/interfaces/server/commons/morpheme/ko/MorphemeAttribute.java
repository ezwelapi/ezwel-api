package com.ezwel.htl.interfaces.server.commons.morpheme.ko;

import org.apache.lucene.util.Attribute;

import com.ezwel.htl.interfaces.commons.annotation.APIType;

/**
 * Created by SooMyung(soomyung.lee@gmail.com) on 2014. 7. 29.
 */
@APIType(description="형태소 속성 인터페이스")
public interface MorphemeAttribute extends Attribute {
    public void setToken(KoreanToken token);
    public KoreanToken getToken();
}
