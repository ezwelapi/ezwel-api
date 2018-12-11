/* The following code was generated by JFlex 1.5.1 */

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

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.5.1
 * from the specification file <tt>/Users/isumyeong/Documents/eclipsWorks/workspace/arirang.lucene-analyzer-4.6/src/org/apache/lucene/analysis/ko/KoreanTokenizerImpl.jflex</tt>
 */
class KoreanTokenizerImpl {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\0\1\20\1\0\1\0\1\17\3\0\17\0\1\0\5\0"+
    "\1\3\1\1\4\0\1\7\1\5\1\2\1\7\12\11\6\0\1\4"+
    "\32\10\4\0\1\6\1\0\32\10\12\0\1\21\72\0\27\10\1\0"+
    "\37\10\1\0\u0568\10\12\12\206\10\12\12\u026c\10\12\12\166\10\12\12"+
    "\166\10\12\12\166\10\12\12\166\10\12\12\167\10\11\12\166\10\12\12"+
    "\166\10\12\12\166\10\12\12\340\10\12\12\166\10\12\12\u0166\10\12\12"+
    "\266\10\u0100\14\u0e00\10\50\0\1\21\1\21\u1016\0\u0150\16\140\0\20\16"+
    "\u0100\0\200\16\200\0\u19c0\15\100\0\u5200\15\u0c00\0\u2bb0\13\u2150\0\u0200\15"+
    "\u0465\0\73\16\75\10\43\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\2\1\3\2\2\3\1\4\1\5\1\1\1\6"+
    "\6\0\3\2\1\3\1\0\1\6\3\0\1\2\1\3"+
    "\1\6\1\2\4\3\1\0\2\4\1\3\2\0\1\7"+
    "\3\10\2\11\2\0\2\6\2\0\2\10\1\3\4\6"+
    "\1\0\2\10\2\3\2\0\1\4\2\3\1\0\1\12"+
    "\1\0\1\10\1\13\2\0\2\10\1\3\1\12\1\0"+
    "\2\6\1\3\2\6\1\3\2\13\1\10\2\3\1\6"+
    "\2\3\1\6\2\3\2\12\1\0\2\10\1\0\1\13"+
    "\1\10\2\6\1\13\2\0\1\13\1\3\1\10\1\14"+
    "\1\0\1\3\1\0\1\3\1\13\1\0\1\3\1\0"+
    "\1\3\1\0\1\10\1\3\1\6\1\3\1\6\1\3"+
    "\1\10\2\6\1\13\2\0\2\3\1\0\2\3\1\0"+
    "\2\3\1\13\1\10\1\3\2\0\2\3\1\13\1\0"+
    "\2\10\2\0\1\13\1\0\2\10\1\3\2\0\1\3"+
    "\1\10\1\0\1\13\1\0\1\13\1\3\1\0\3\3";

  private static int [] zzUnpackAction() {
    int [] result = new int[177];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\22\0\44\0\66\0\110\0\132\0\154\0\176"+
    "\0\220\0\242\0\264\0\306\0\330\0\352\0\374\0\u010e"+
    "\0\u0120\0\u0132\0\u0144\0\u0156\0\u0168\0\u017a\0\306\0\u018c"+
    "\0\u019e\0\u01b0\0\u01c2\0\u01d4\0\u01e6\0\u01f8\0\u020a\0\u021c"+
    "\0\u022e\0\u0240\0\u0252\0\u0264\0\u0276\0\u0288\0\u029a\0\u02ac"+
    "\0\u02be\0\u02d0\0\u02e2\0\u02f4\0\u0306\0\374\0\u0318\0\u032a"+
    "\0\u033c\0\u034e\0\u0360\0\u0372\0\u018c\0\u0384\0\u0396\0\u03a8"+
    "\0\u03ba\0\u03cc\0\u03de\0\u03f0\0\u0402\0\u0414\0\u0426\0\u0438"+
    "\0\u044a\0\u045c\0\u046e\0\u0480\0\u0492\0\u04a4\0\u04b6\0\u04c8"+
    "\0\u04da\0\u04ec\0\u04fe\0\u0510\0\u0522\0\u0534\0\u0546\0\u0558"+
    "\0\u056a\0\u057c\0\u058e\0\u05a0\0\u05b2\0\u05c4\0\u05d6\0\u05e8"+
    "\0\u0372\0\u018c\0\u05fa\0\u060c\0\u061e\0\u0630\0\u0642\0\u0654"+
    "\0\u0666\0\u0678\0\u068a\0\352\0\u01f8\0\u069c\0\u06ae\0\u06c0"+
    "\0\u06d2\0\u06e4\0\u06f6\0\u0708\0\u071a\0\u072c\0\u073e\0\u0750"+
    "\0\u0762\0\u0774\0\u0786\0\u0798\0\u07aa\0\u07bc\0\u07ce\0\u07e0"+
    "\0\u0402\0\u07f2\0\u0804\0\u0816\0\u0828\0\u083a\0\u084c\0\u085e"+
    "\0\u0870\0\u0882\0\u0894\0\u08a6\0\u08b8\0\u08ca\0\u08dc\0\u08ee"+
    "\0\u0900\0\u0912\0\u0924\0\u0936\0\u0948\0\u095a\0\u096c\0\u097e"+
    "\0\u0990\0\u09a2\0\u09b4\0\u09c6\0\u09d8\0\u09ea\0\u09fc\0\u0a0e"+
    "\0\u0a20\0\u0a32\0\u0a44\0\u0a56\0\u0a68\0\u0a7a\0\u0a8c\0\u0a9e"+
    "\0\u0ab0\0\u0ac2\0\u0ad4\0\u0ae6\0\u0af8\0\u0b0a\0\u0b1c\0\u0b2e"+
    "\0\u0b40\0\u0b52\0\u0b64\0\u0b76\0\u0b88\0\u0b9a\0\u0bac\0\u0bbe"+
    "\0\u0bd0";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[177];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\2\2\1\3\5\2\1\4\1\5\1\6\1\7\1\10"+
    "\1\11\1\12\1\13\1\2\34\0\2\14\10\0\1\15"+
    "\1\16\1\17\1\20\2\21\1\22\1\23\1\24\1\25"+
    "\1\7\1\26\1\27\6\0\1\30\1\0\1\31\2\32"+
    "\1\33\1\34\2\24\1\7\1\35\1\27\5\0\1\15"+
    "\1\36\1\17\1\20\2\32\1\33\1\37\1\24\1\25"+
    "\1\7\1\40\1\27\14\0\3\41\2\7\6\0\1\15"+
    "\1\16\1\17\1\20\2\21\1\22\1\26\1\42\1\43"+
    "\1\7\1\26\1\27\6\0\1\44\5\0\1\45\2\46"+
    "\1\7\1\47\1\11\22\0\1\12\23\0\1\2\3\0"+
    "\1\44\5\0\1\50\2\51\1\7\1\47\1\27\14\0"+
    "\1\52\1\0\1\52\1\0\1\52\15\0\1\53\1\54"+
    "\1\55\1\0\1\53\15\0\1\56\1\0\1\56\1\0"+
    "\1\56\15\0\1\57\1\60\1\57\1\0\1\57\15\0"+
    "\1\61\2\62\1\0\1\61\15\0\1\22\2\63\1\0"+
    "\1\22\6\0\1\15\1\64\1\17\1\20\2\21\1\22"+
    "\1\23\1\24\1\25\1\7\1\26\1\27\6\0\1\65"+
    "\1\0\1\31\2\32\1\33\1\34\2\24\1\7\1\35"+
    "\1\27\5\0\1\15\1\65\1\17\1\20\2\32\1\33"+
    "\1\37\1\24\1\25\1\7\1\40\1\27\5\0\1\15"+
    "\1\64\1\17\1\20\2\21\1\22\1\26\1\42\1\43"+
    "\1\7\1\26\1\27\6\0\1\44\5\0\1\66\2\67"+
    "\1\7\1\70\1\27\14\0\3\60\1\0\1\60\15\0"+
    "\1\71\2\72\1\0\1\71\15\0\1\73\2\74\1\0"+
    "\1\73\7\0\1\75\1\0\1\31\2\32\1\33\1\34"+
    "\2\24\1\7\1\35\1\27\6\0\1\75\1\0\1\31"+
    "\2\32\1\33\1\35\2\42\1\7\1\35\1\27\6\0"+
    "\1\44\5\0\1\76\1\67\1\77\1\7\1\100\1\27"+
    "\5\0\1\15\1\75\1\17\1\20\2\32\1\33\1\37"+
    "\1\24\1\25\1\7\1\40\1\27\5\0\1\15\1\75"+
    "\1\17\1\20\2\32\1\33\1\40\1\42\1\43\1\7"+
    "\1\40\1\27\14\0\3\41\1\101\1\41\7\0\1\65"+
    "\1\0\1\31\2\32\1\33\1\35\2\42\1\7\1\35"+
    "\1\27\5\0\1\15\1\65\1\17\1\20\2\32\1\33"+
    "\1\40\1\42\1\43\1\7\1\40\1\27\15\0\2\27"+
    "\11\0\1\102\2\0\3\102\1\45\2\46\1\7\1\47"+
    "\1\27\6\0\1\27\2\0\3\103\1\104\2\46\1\7"+
    "\1\105\1\27\6\0\1\102\2\0\3\102\1\47\2\106"+
    "\1\7\1\47\1\27\6\0\1\102\2\0\3\102\1\50"+
    "\2\51\1\7\1\47\1\27\6\0\1\27\2\0\3\103"+
    "\1\107\2\51\1\7\1\105\1\27\5\0\1\15\6\0"+
    "\1\52\1\0\1\52\1\0\1\52\7\0\1\110\1\0"+
    "\1\31\2\111\1\0\1\112\2\54\1\0\1\112\7\0"+
    "\1\113\1\0\1\31\2\114\1\115\1\116\2\117\1\7"+
    "\1\120\1\27\6\0\1\121\1\0\1\31\2\114\1\115"+
    "\1\116\2\117\1\7\1\120\1\27\6\0\1\122\2\0"+
    "\1\122\2\0\1\57\1\60\1\57\1\0\1\57\7\0"+
    "\1\122\2\0\1\122\2\0\3\60\1\0\1\60\7\0"+
    "\1\111\1\0\1\31\2\111\1\0\1\61\2\62\1\0"+
    "\1\61\7\0\1\114\1\0\1\31\2\114\1\115\1\123"+
    "\2\124\1\7\1\125\1\27\6\0\1\115\2\0\3\115"+
    "\1\126\2\127\1\7\1\130\1\27\14\0\1\112\2\54"+
    "\1\0\1\112\7\0\1\131\1\0\1\31\2\21\1\22"+
    "\1\66\2\67\1\7\1\70\1\27\6\0\1\132\1\0"+
    "\1\31\2\32\1\33\1\133\2\67\1\7\1\134\1\27"+
    "\6\0\1\131\1\0\1\31\2\21\1\22\1\70\2\135"+
    "\1\7\1\70\1\27\6\0\1\21\1\0\1\31\2\21"+
    "\1\22\1\71\2\136\1\7\1\137\1\27\6\0\1\32"+
    "\1\0\1\31\2\32\1\33\1\72\2\136\1\7\1\140"+
    "\1\27\6\0\1\22\2\0\3\22\1\73\2\141\1\7"+
    "\1\142\1\27\6\0\1\33\2\0\3\33\1\74\2\141"+
    "\1\7\1\143\1\27\14\0\1\66\2\133\1\0\1\66"+
    "\7\0\1\144\1\0\1\31\2\21\1\22\1\66\2\67"+
    "\1\7\1\70\1\27\6\0\1\145\1\0\1\31\2\32"+
    "\1\33\1\133\2\67\1\7\1\134\1\27\6\0\1\144"+
    "\1\0\1\31\2\21\1\22\1\70\2\135\1\7\1\70"+
    "\1\27\17\0\2\101\15\0\1\102\2\146\1\0\1\102"+
    "\15\0\1\50\2\107\1\0\1\50\7\0\1\103\2\0"+
    "\3\103\1\104\2\46\1\7\1\105\1\27\6\0\1\103"+
    "\2\0\3\103\1\105\2\106\1\7\1\105\1\27\6\0"+
    "\1\27\2\0\3\103\1\105\2\106\1\7\1\105\1\27"+
    "\6\0\1\103\2\0\3\103\1\107\2\51\1\7\1\105"+
    "\1\27\14\0\1\147\1\150\1\147\1\0\1\147\15\0"+
    "\3\151\1\0\1\151\7\0\1\152\1\0\1\31\2\111"+
    "\1\0\1\112\2\54\1\0\1\112\15\0\3\153\1\0"+
    "\1\153\15\0\3\154\1\0\1\154\15\0\3\155\1\0"+
    "\1\155\7\0\1\156\1\0\1\31\2\157\1\160\1\116"+
    "\2\117\1\7\1\120\1\27\6\0\1\161\1\0\1\31"+
    "\2\157\1\160\1\116\2\117\1\7\1\120\1\27\6\0"+
    "\1\156\1\0\1\31\2\157\1\160\1\120\2\162\1\7"+
    "\1\120\1\27\14\0\1\163\1\153\1\163\1\0\1\163"+
    "\15\0\3\164\1\0\1\164\7\0\1\157\1\0\1\31"+
    "\2\157\1\160\1\123\2\124\1\7\1\125\1\27\6\0"+
    "\1\165\1\0\1\31\2\157\1\160\1\123\2\124\1\7"+
    "\1\125\1\27\6\0\1\157\1\0\1\31\2\157\1\160"+
    "\1\125\2\166\1\7\1\125\1\27\6\0\1\160\2\0"+
    "\3\160\1\126\2\127\1\7\1\130\1\27\6\0\1\167"+
    "\2\0\3\160\1\126\2\127\1\7\1\130\1\27\6\0"+
    "\1\160\2\0\3\160\1\130\2\170\1\7\1\130\1\27"+
    "\6\0\1\171\1\0\1\31\2\32\1\33\1\133\2\67"+
    "\1\7\1\134\1\27\6\0\1\171\1\0\1\31\2\32"+
    "\1\33\1\134\2\135\1\7\1\134\1\27\6\0\1\132"+
    "\1\0\1\31\2\32\1\33\1\134\2\135\1\7\1\134"+
    "\1\27\6\0\1\172\1\0\1\31\2\32\1\33\1\72"+
    "\2\136\1\7\1\140\1\27\6\0\1\21\1\0\1\31"+
    "\2\21\1\22\1\137\2\173\1\7\1\137\1\27\6\0"+
    "\1\32\1\0\1\31\2\32\1\33\1\140\2\173\1\7"+
    "\1\140\1\27\6\0\1\174\2\0\3\33\1\74\2\141"+
    "\1\7\1\143\1\27\6\0\1\22\2\0\3\22\1\142"+
    "\2\175\1\7\1\142\1\27\6\0\1\33\2\0\3\33"+
    "\1\143\2\175\1\7\1\143\1\27\6\0\1\176\2\0"+
    "\3\176\1\107\2\51\1\7\1\105\1\27\6\0\1\110"+
    "\1\0\1\31\2\111\1\0\3\150\1\0\1\150\7\0"+
    "\1\152\1\0\1\31\2\111\1\0\3\150\1\0\1\150"+
    "\7\0\1\111\1\0\1\31\2\111\1\0\3\151\1\0"+
    "\1\151\15\0\3\150\1\0\1\150\7\0\1\131\1\0"+
    "\1\31\2\21\1\22\1\153\2\177\1\7\1\200\1\27"+
    "\6\0\1\21\1\0\1\31\2\21\1\22\1\154\2\201"+
    "\1\7\1\202\1\27\6\0\1\22\2\0\3\22\1\155"+
    "\2\203\1\7\1\204\1\27\14\0\1\153\2\205\1\0"+
    "\1\153\15\0\1\154\2\206\1\0\1\154\15\0\1\155"+
    "\2\207\1\0\1\155\7\0\1\44\5\0\1\153\2\177"+
    "\1\7\1\200\1\27\6\0\1\161\1\0\1\31\2\157"+
    "\1\160\1\120\2\162\1\7\1\120\1\27\6\0\1\144"+
    "\1\0\1\31\2\21\1\22\1\153\2\177\1\7\1\200"+
    "\1\27\6\0\1\122\2\0\1\122\2\0\3\164\1\0"+
    "\1\164\7\0\1\44\5\0\1\154\2\201\1\7\1\202"+
    "\1\27\6\0\1\165\1\0\1\31\2\157\1\160\1\125"+
    "\2\166\1\7\1\125\1\27\6\0\1\44\5\0\1\155"+
    "\2\203\1\7\1\204\1\27\6\0\1\167\2\0\3\160"+
    "\1\130\2\170\1\7\1\130\1\27\6\0\1\44\5\0"+
    "\1\71\2\136\1\7\1\137\1\27\6\0\1\172\1\0"+
    "\1\31\2\32\1\33\1\140\2\173\1\7\1\140\1\27"+
    "\6\0\1\44\5\0\1\73\2\141\1\7\1\142\1\27"+
    "\6\0\1\174\2\0\3\33\1\143\2\175\1\7\1\143"+
    "\1\27\14\0\3\50\1\0\1\50\7\0\1\210\1\0"+
    "\1\31\2\211\1\212\1\205\2\177\1\7\1\213\1\27"+
    "\6\0\1\131\1\0\1\31\2\21\1\22\1\200\2\214"+
    "\1\7\1\200\1\27\6\0\1\215\1\0\1\31\2\211"+
    "\1\212\1\206\2\201\1\7\1\216\1\27\6\0\1\21"+
    "\1\0\1\31\2\21\1\22\1\202\2\217\1\7\1\202"+
    "\1\27\6\0\1\220\2\0\3\212\1\207\2\203\1\7"+
    "\1\221\1\27\6\0\1\22\2\0\3\22\1\204\2\222"+
    "\1\7\1\204\1\27\6\0\1\223\1\0\1\31\2\211"+
    "\1\212\1\205\2\177\1\7\1\213\1\27\6\0\1\211"+
    "\1\0\1\31\2\211\1\212\1\206\2\201\1\7\1\216"+
    "\1\27\6\0\1\212\2\0\3\212\1\207\2\203\1\7"+
    "\1\221\1\27\6\0\1\44\5\0\1\224\2\117\1\7"+
    "\1\225\1\27\14\0\1\226\2\123\1\0\1\226\15\0"+
    "\1\227\2\126\1\0\1\227\7\0\1\223\1\0\1\31"+
    "\2\211\1\212\1\213\2\214\1\7\1\213\1\27\6\0"+
    "\1\210\1\0\1\31\2\211\1\212\1\213\2\214\1\7"+
    "\1\213\1\27\6\0\1\44\5\0\1\226\2\124\1\7"+
    "\1\230\1\27\6\0\1\211\1\0\1\31\2\211\1\212"+
    "\1\216\2\217\1\7\1\216\1\27\6\0\1\215\1\0"+
    "\1\31\2\211\1\212\1\216\2\217\1\7\1\216\1\27"+
    "\6\0\1\44\5\0\1\227\2\127\1\7\1\231\1\27"+
    "\6\0\1\212\2\0\3\212\1\221\2\222\1\7\1\221"+
    "\1\27\6\0\1\220\2\0\3\212\1\221\2\222\1\7"+
    "\1\221\1\27\14\0\1\224\2\116\1\0\1\224\7\0"+
    "\1\232\1\0\1\31\2\233\1\102\1\224\2\117\1\7"+
    "\1\225\1\27\6\0\1\232\1\0\1\31\2\233\1\102"+
    "\1\225\2\162\1\7\1\225\1\27\6\0\1\233\1\0"+
    "\1\31\2\233\1\102\1\226\2\124\1\7\1\230\1\27"+
    "\6\0\1\102\2\0\3\102\1\227\2\127\1\7\1\231"+
    "\1\27\6\0\1\233\1\0\1\31\2\233\1\102\1\230"+
    "\2\166\1\7\1\230\1\27\6\0\1\102\2\0\3\102"+
    "\1\231\2\170\1\7\1\231\1\27\14\0\1\234\2\235"+
    "\1\0\1\234\15\0\1\236\2\237\1\0\1\236\7\0"+
    "\1\152\1\0\1\31\2\111\1\0\1\234\2\235\1\0"+
    "\1\234\7\0\1\240\1\0\1\31\2\241\1\176\1\242"+
    "\2\243\1\7\1\244\1\27\6\0\1\111\1\0\1\31"+
    "\2\111\1\0\1\236\2\237\1\0\1\236\7\0\1\241"+
    "\1\0\1\31\2\241\1\176\1\245\2\246\1\7\1\247"+
    "\1\27\14\0\3\250\1\0\1\250\15\0\3\251\1\0"+
    "\1\251\7\0\1\252\1\0\1\31\2\253\1\103\1\242"+
    "\2\243\1\7\1\244\1\27\6\0\1\254\1\0\1\31"+
    "\2\253\1\103\1\242\2\243\1\7\1\244\1\27\6\0"+
    "\1\252\1\0\1\31\2\253\1\103\1\244\2\255\1\7"+
    "\1\244\1\27\6\0\1\253\1\0\1\31\2\253\1\103"+
    "\1\245\2\246\1\7\1\247\1\27\6\0\1\256\1\0"+
    "\1\31\2\253\1\103\1\245\2\246\1\7\1\247\1\27"+
    "\6\0\1\253\1\0\1\31\2\253\1\103\1\247\2\257"+
    "\1\7\1\247\1\27\6\0\1\232\1\0\1\31\2\233"+
    "\1\102\1\250\2\243\1\7\1\260\1\27\6\0\1\233"+
    "\1\0\1\31\2\233\1\102\1\251\2\246\1\7\1\261"+
    "\1\27\14\0\1\250\2\242\1\0\1\250\15\0\1\251"+
    "\2\245\1\0\1\251\7\0\1\44\5\0\1\250\2\243"+
    "\1\7\1\260\1\27\6\0\1\254\1\0\1\31\2\253"+
    "\1\103\1\244\2\255\1\7\1\244\1\27\6\0\1\44"+
    "\5\0\1\251\2\246\1\7\1\261\1\27\6\0\1\256"+
    "\1\0\1\31\2\253\1\103\1\247\2\257\1\7\1\247"+
    "\1\27\6\0\1\232\1\0\1\31\2\233\1\102\1\260"+
    "\2\255\1\7\1\260\1\27\6\0\1\233\1\0\1\31"+
    "\2\233\1\102\1\261\2\257\1\7\1\261\1\27\4\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3042];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\12\1\6\0\4\1\1\0\1\1\3\0"+
    "\10\1\1\0\3\1\2\0\6\1\2\0\2\1\2\0"+
    "\7\1\1\0\4\1\2\0\3\1\1\0\1\1\1\0"+
    "\2\1\2\0\4\1\1\0\23\1\1\0\2\1\1\0"+
    "\5\1\2\0\4\1\1\0\1\1\1\0\2\1\1\0"+
    "\1\1\1\0\1\1\1\0\12\1\2\0\2\1\1\0"+
    "\2\1\1\0\5\1\2\0\3\1\1\0\2\1\2\0"+
    "\1\1\1\0\3\1\2\0\2\1\1\0\1\1\1\0"+
    "\2\1\1\0\3\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[177];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */

public static final int ALPHANUM          = 0;
public static final int APOSTROPHE        = 1;
public static final int ACRONYM           = 2;
public static final int COMPANY           = 3;
public static final int EMAIL             = 4;
public static final int HOST              = 5;
public static final int NUM               = 6;
public static final int CJ                = 7;
/**
 * @deprecated this solves a bug where HOSTs that end with '.' are identified
 *             as ACRONYMs. It is deprecated and will be removed in the next
 *             release.
 */
public static final int ACRONYM_DEP       = 8;
public static final int KOREAN            = 9;
public static final int CHINESE            = 10;

public static final String [] TOKEN_TYPES = new String [] {
    "<ALPHANUM>",
    "<APOSTROPHE>",
    "<ACRONYM>",
    "<COMPANY>",
    "<EMAIL>",
    "<HOST>",
    "<NUM>", 
    "<CJ>",   
    "<ACRONYM_DEP>",
    "<KOREAN>" ,
    "<CHINESE>"     
};

public final int yychar() {
  return yychar;
}

/**
 * Fills Lucene token with the current token text.
 */
final void getText(CharTermAttribute t) {
  t.copyBuffer(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
}


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  KoreanTokenizerImpl(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  KoreanTokenizerImpl(java.io.InputStream in) {
    this(new java.io.InputStreamReader
             (in, java.nio.charset.Charset.forName("UTF-8")));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 168) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

    // numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int getNextToken() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { /* ignore */
          }
        case 13: break;
        case 2: 
          { return ALPHANUM;
          }
        case 14: break;
        case 3: 
          { return KOREAN;
          }
        case 15: break;
        case 4: 
          { return CHINESE;
          }
        case 16: break;
        case 5: 
          { return CJ;
          }
        case 17: break;
        case 6: 
          { return NUM;
          }
        case 18: break;
        case 7: 
          { return APOSTROPHE;
          }
        case 19: break;
        case 8: 
          { return HOST;
          }
        case 20: break;
        case 9: 
          { return COMPANY;
          }
        case 21: break;
        case 10: 
          { return ACRONYM;
          }
        case 22: break;
        case 11: 
          { return ACRONYM_DEP;
          }
        case 23: break;
        case 12: 
          { return EMAIL;
          }
        case 24: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return YYEOF;
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
