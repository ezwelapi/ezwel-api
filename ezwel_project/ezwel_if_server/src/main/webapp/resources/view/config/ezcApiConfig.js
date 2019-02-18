//<![CDATA[

(function( $ ){
	$.extend({
		 fn_config: {
			//시작
			// $.fn_config.cont.selt.id.controls
			cont: {
				selt: {
					id: {
						//  $.fn_config.cont.selt.id.controls
						controls: "#controls"
					}
					//  $.fn_config.cont.selt.editor
					,editor: "editor-container"
				}
				,txURL: {
					 //  $.fn_config.cont.txURL.storeList
					 storeList: $.gf_cont.reqNamespace + "/manager/storeList"
					 //  $.fn_config.cont.txURL.findXML
					,findXML: $.gf_cont.reqNamespace + "/manager/findXML"
				}
				,editor: {
					def: {
						 // $.fn_config.cont.editor.def.theme
						 theme: "ace/theme/monokai"
					     // $.fn_config.cont.editor.def.mode
						,mode: "ace/mode/xml"
					}
				}
			}
			,session: {
				 // $.fn_config.session.editor
				 editor: {}
				 // $.fn_config.session.folder
				,folder: null
			}
			,util: {
				//  $.fn_config.util.getTagName
				getTagName: function( $elem ) {
					$.logger.debug("$.fn_config.util.getTagName : " + (typeof $elem) );
					return $elem.prop("tagName");
				}
				//  $.fn_config.util.errorMessage( URL, managerSDO )
				,errorMessage: function( URL, managerSDO ) {
					
					var contents  = "Code: "+ managerSDO.code + "<br>";
					contents += "Message: " + managerSDO.message;
					
					$.alert(contents, { 
							elemId : $.gf_func.replaceAll(URL, "/", "-"),
							css: {
								div: {width:"500px"},
								p: {height:"40px",overflow:"auto","color":"red", "font-size":"14px"}
							},
							console: {"error": managerSDO}
						}
					);					
				}
				// $.fn_config.util.initEditor( $.fn_config.cont.selt.editor, $.fn_config.cont.editor.def.theme, $.fn_config.cont.editor.def.mode )
				,initEditor: function( selt, theme, mode ) {
					
					var editor = null;
					if( !$.fn_config.session.editor[selt] ) {
					
					    $("[ux-component=mode]").val(mode);
					    $("[ux-component=theme]").val(theme);
					    
						editor = ace.edit( selt );
					    editor.setTheme( theme );
					    editor.session.setMode( mode );
					    
					    $.fn_config.session.editor[selt] = editor;
					}
					else {
						editor = $.fn_config.session.editor[selt]; 
					}
					
				    return editor;
				}
			}
			,tx: {
				//  $.fn_config.tx.selectFileList( storeFileDir, isInitEditor )
				selectFileList: function( editor, $elem, isInitEditor ) {
					
					var storeFileDir = $($elem).val();
					$.fn_config.session.folder = storeFileDir;
					
					$.logger.debug("# selectFileList: " + $($elem).val());
					
					if($($elem).val() == "realConfigXml") {
						$.fn_config.control.doc( editor, $elem, true );
					}
					else {
						
						$.gf_tx.ajax( $.fn_config.cont.txURL.storeList, { storeType: "child", storeFileDir: storeFileDir }
							,{
								fn_success: function( managerSDO ) {
									
									//조회 실패
									if(managerSDO.code !== "1000") {
										$.fn_config.util.errorMessage( $.fn_config.cont.txURL.storeList, managerSDO );
									}
									//조회 성공
									else {
										
										if(managerSDO.fileList == null || managerSDO.fileList.length == 0) {
											//임시저장 사용자 디렉토리가 존재하지 않으면 에디터 초기화
											if(isInitEditor) {
												$.fn_config.bind.editor();
											}
										}
										else {
											
											$("[ux-component='storeFile']").empty();

											$.each(managerSDO.fileList, function(idx, val) {
												$("[ux-component='storeFile']").append($('<option>', {
													value: val.name,
													text : val.name 
												}));
												
												if(idx === 0) {
													//첫번째 파일 내용 조회
													$.fn_config.control.doc( editor, "[ux-component='storeFile']" );
												}
											});
											
											if(isInitEditor) {
												//임시저장 사용자 디렉토리가 존재하지 않으면 에디터 초기화
												$.fn_config.bind.editor();
											}
										}
									}
								}
							}
						);				
					}
				}
				//  $.fn_config.tx.selectDirectoryList()
				,selectDirectoryList: function() {
					
					$.gf_tx.ajax( $.fn_config.cont.txURL.storeList, { storeType: "root" }
					,{
						fn_success: function( managerSDO ) {
							
							$.logger.debug("#Response ManagerSDO", managerSDO);
							
							//조회 실패
							if(managerSDO.code !== "1000") {
								$.fn_config.util.errorMessage( $.fn_config.cont.txURL.storeList, managerSDO );
							}
							//조회 성공
							else {
								
								if(managerSDO.fileList == null || managerSDO.fileList.length == 0) {
									//임시저장 사용자 디렉토리가 존재하지 않으면 에디터 초기화
									$.fn_config.bind.editor();
								}
								else {
									
									$.each(managerSDO.fileList, function(index, value) {
										
										$.logger.debug("- file["+index+"]", value);
										
										/**	
										 * 폴더 정보 세팅
										 */
										$("[ux-component='storeFolder'] optgroup[groupName='storeFolder']").append($('<option>', {
									        value: value.name,
									        text : value.name 
									    }));
										
										if(index === 0 ) {
											
											/**
											 * 첫번째 폴더의 하위 파일을 조회하여 세팅한다.
											 */
											$.fn_config.tx.selectFileList( null, "[ux-component='storeFolder']", true );
										}
									});
								}							
							}
						}
					});					
				}
			}
			// $.fn_config.control
			,control: {
				// $.fn_config.control.doc( editor, $elem )
				doc: function( editor, $elem, isManagedXml ) {
					$.logger.debug("# [START] doc", arguments);
					if( !editor ) {
						
						if( !$.fn_config.session.editor[$.fn_config.cont.selt.editor] ) {
							editor = $.fn_config.bind.editor();
						}
						else {
							editor = $.fn_config.session.editor[$.fn_config.cont.selt.editor];
						}
					}
					
					$.gf_tx.ajax( $.fn_config.cont.txURL.findXML, { 
							 storeFileDir: $.fn_config.session.folder
							,storeFileName: $($elem).val() 
							,managedXml: isManagedXml
						},
						{
							fn_success: function( managerSDO ) {
								$.logger.debug( "#Response", managerSDO );
								
								if(managerSDO.code !== "1000") {
									$.fn_config.util.errorMessage( $.fn_config.cont.txURL.findXML, managerSDO );
								}
								else {
									var xmlContents = managerSDO.xmlCont;
									editor.setValue(xmlContents);
								}
							}
						}
					);
					$.logger.debug("# [END] doc");
				},				
				folder: function( editor, $elem ) {
					$.logger.debug("# [START] folder", arguments);

					$.fn_config.session.folder = $($elem).val();
					
					if($($elem).val() == "realConfigXml") {
						$.fn_config.control.doc( editor, $elem, true );
					}
					else {
						$.fn_config.tx.selectFileList( editor, $elem, false );
					}
					
					$.logger.debug("# [END] folder");
				},
				split: function( editor, $elem ) {
					$.logger.debug("# [START] split", arguments);

					$.logger.debug("# [END] split");
				},
				mode: function( editor, $elem ) {
					$.logger.debug("# [START] mode", arguments);
					editor.session.setMode($($elem).val());
					$.logger.debug("# [END] mode");
				},
				theme: function( editor, $elem ) {
					$.logger.debug("# [START] theme", arguments);
					
					editor.setTheme($($elem).val());
					
					$.logger.debug("# [END] theme");
				},
				keyboardHandler: function( editor, $elem ) {
					$.logger.debug("# [START] keyboardHandler", arguments);
					
					$($elem).parent().find("button").each(function(i, item){
						$(item).attr("ace_selected_button", false);
					});
					
					$($elem).attr("ace_selected_button", true);
					editor.setKeyboardHandler($($elem).attr("value"));
					
					$.logger.debug("# [END] keyboardHandler");
				},
				fontSize: function( editor, $elem ) {
					$.logger.debug("# [START] fontSize", arguments);
					editor.setFontSize($($elem).text());
					$.logger.debug("# [END] fontSize");
				},
				softWrap: function( editor, $elem ) {
					$.logger.debug("# [START] softWrap", arguments);

					$.logger.debug("# [END] softWrap");
				},
				cursorStyle: function( editor, $elem ) {
					$.logger.debug("# [START] cursorStyle", arguments);

					$.logger.debug("# [END] cursorStyle");
				},
				foldStyle: function( editor, $elem ) {
					$.logger.debug("# [START] foldStyle", arguments);

					$.logger.debug("# [END] foldStyle");
				},
				softTabs: function( editor, $elem ) {
					$.logger.debug("# [START] softTabs", arguments);

					$.logger.debug("# [END] softTabs");
				},
				scrollPastEnd: function( editor, $elem ) {
					$.logger.debug("# [START] scrollPastEnd", arguments);

					$.logger.debug("# [END] scrollPastEnd");
				},
				navigateWithinSoftTabs: function( editor, $elem ) {
					$.logger.debug("# [START] navigateWithinSoftTabs", arguments);

					$.logger.debug("# [END] navigateWithinSoftTabs");
				},
				behavioursEnabled: function( editor, $elem ) {
					$.logger.debug("# [START] behavioursEnabled", arguments);

					$.logger.debug("# [END] behavioursEnabled");
				},
				selectionStyle: function( editor, $elem ) {
					$.logger.debug("# [START] selectionStyle", arguments);

					$.logger.debug("# [END] selectionStyle");
				},
				highlightActiveLine: function( editor, $elem ) {
					$.logger.debug("# [START] highlightActiveLine", arguments);

					$.logger.debug("# [END] highlightActiveLine");
				},
				showInvisibles: function( editor, $elem ) {
					$.logger.debug("# [START] showInvisibles", arguments);

					$.logger.debug("# [END] showInvisibles");
				},
				displayIndentGuides: function( editor, $elem ) {
					$.logger.debug("# [START] displayIndentGuides", arguments);

					$.logger.debug("# [END] displayIndentGuides");
				},
				animatedScroll: function( editor, $elem ) {
					$.logger.debug("# [START] animatedScroll", arguments);

					$.logger.debug("# [END] animatedScroll");
				},
				showGutter: function( editor, $elem ) {
					$.logger.debug("# [START] showGutter", arguments);

					$.logger.debug("# [END] showGutter");
				},
				showLineNumbers: function( editor, $elem ) {
					$.logger.debug("# [START] showLineNumbers", arguments);

					$.logger.debug("# [END] showLineNumbers");
				},
				relativeLineNumbers: function( editor, $elem ) {
					$.logger.debug("# [START] relativeLineNumbers", arguments);

					$.logger.debug("# [END] relativeLineNumbers");
				},
				fixedWidthGutter: function( editor, $elem ) {
					$.logger.debug("# [START] fixedWidthGutter", arguments);

					$.logger.debug("# [END] fixedWidthGutter");
				},
				showPrintMargin: function( editor, $elem ) {
					$.logger.debug("# [START] showPrintMargin", arguments);

					$.logger.debug("# [END] showPrintMargin");
				},
				indentedSoftWrap: function( editor, $elem ) {
					$.logger.debug("# [START] indentedSoftWrap", arguments);

					$.logger.debug("# [END] indentedSoftWrap");
				},
				highlightSelectedWord: function( editor, $elem ) {
					$.logger.debug("# [START] highlightSelectedWord", arguments);

					$.logger.debug("# [END] highlightSelectedWord");
				},
				fadeFoldWidgets: function( editor, $elem ) {
					$.logger.debug("# [START] fadeFoldWidgets", arguments);

					$.logger.debug("# [END] fadeFoldWidgets");
				},
				useTextareaForIME: function( editor, $elem ) {
					$.logger.debug("# [START] useTextareaForIME", arguments);

					$.logger.debug("# [END] useTextareaForIME");
				},
				mergeUndoDeltas: function( editor, $elem ) {
					$.logger.debug("# [START] mergeUndoDeltas", arguments);

					$.logger.debug("# [END] mergeUndoDeltas");
				},
				useElasticTabstops: function( editor, $elem ) {
					$.logger.debug("# [START] useElasticTabstops", arguments);

					$.logger.debug("# [END] useElasticTabstops");
				},
				useIncrementalSearch: function( editor, $elem ) {
					$.logger.debug("# [START] useIncrementalSearch", arguments);

					$.logger.debug("# [END] useIncrementalSearch");
				},
				readOnly: function( editor, $elem ) {
					$.logger.debug("# [START] readOnly", arguments);

					$.logger.debug("# [END] readOnly");
				},
				copyWithEmptySelection: function( editor, $elem ) {
					$.logger.debug("# [START] copyWithEmptySelection", arguments);

					$.logger.debug("# [END] copyWithEmptySelection");
				},
				rtlText: function( editor, $elem ) {
					$.logger.debug("# [START] rtlText", arguments);

					$.logger.debug("# [END] rtlText");
				},
				showTokenInfo: function( editor, $elem ) {
					$.logger.debug("# [START] showTokenInfo", arguments);

					$.logger.debug("# [END] showTokenInfo");
				},
				textInputDebugger: function( editor, $elem ) {
					$.logger.debug("# [START] textInputDebugger", arguments);

					$.logger.debug("# [END] textInputDebugger");
				}
			}
			,bind : {
				child: {
					// $.fn_config.bind.child.component( $elem, id )
					component: function( editor, $elem, id ) {
						
						$elem.find("button").each(function(idx, item){
							
							$.logger.warn("#"+$.fn_config.util.getTagName( $elem )+" > BUTTON : " + $(this).text());
							//input type text
							$(item).on("click", function( e ) {
								$.fn_config.control[id]( editor, this );
							});										
						});	
						
						$elem.find("input[type='checkbox']").each(function(idx, item){
							
							$.logger.warn("#"+$.fn_config.util.getTagName( $elem )+" > CHECKBOX : " + $(this).text());
							//input type text
							$(item).on("click", function( e ) {
								$.fn_config.control[id]( editor, this );
							});										
						});
						
					}
				}
				// $.fn_config.bind.component( editor )
				,component : function( editor ) {
					
					$($.fn_config.cont.selt.id.controls).find("[ux-component]").each(function( idx, elem ){
						$.logger.debug("- ux-component("+idx+")", elem);

						var $elem = $(elem);
						var id = $elem.attr("id");
						$.logger.debug("- ux-component("+idx+") jQuery", $elem);
						
						if( $.fn_config.control[id] ) {
							
							/**
							 * 이부분에서 태그에따라 이벤트 바인드한다.
							 */
							var tagName = $.fn_config.util.getTagName( $elem );
							var tagType = $elem.attr("type");
							$.logger.debug("# tagName: " + tagName + ", tagType: " + tagType);
							// 문자열로 조건 지정
							//var camera = 'Nikon D40X';
							var discernment = (tagType ? tagType : tagName).toUpperCase();
							switch (discernment) {
								case 'DIV': 
									$.logger.warn("#DIV discernment : " + discernment);
					
									$.fn_config.bind.child.component( editor, $elem, id );
									break;
								case 'LI': 
									$.logger.warn("#LI discernment : " + discernment);

									$.fn_config.bind.child.component( editor, $elem, id );
									break;									
								case 'SELECT': 
									$.logger.warn("#SELECT discernment : " + discernment);
									$elem.on("change", function( e ) {
										$.fn_config.control[id]( editor, this );
									});
									break;
								case 'CHECKBOX': 
									$.logger.warn("#CHECKBOX discernment : " + discernment);
									$elem.on("change", function( e ) {
										$.fn_config.control[id]( editor, this );
									});
									break;
								default : 
									$.logger.warn("#default discernment : " + discernment); 
							  		break;
							}
						}
					});
				},
				// $.fn_config.bind.editor
				editor: function() {
				    var editor = $.fn_config.util.initEditor( $.fn_config.cont.selt.editor, $.fn_config.cont.editor.def.theme, $.fn_config.cont.editor.def.mode );
					
					$.fn_config.bind.component( editor );	
					
					return editor;
				}
			
			}
			,initialize: function() {
				$.fn_config.tx.selectDirectoryList();
			}			
		}
	});
})( jQuery );

$(document).ready(function() {

    $.fn_config.initialize();
    
});
//]]>