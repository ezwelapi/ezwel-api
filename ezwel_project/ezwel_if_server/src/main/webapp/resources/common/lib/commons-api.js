//<![CDATA[

(function( $, $window ){
	$.extend({
		gf_cont : {
			 ctxp : "/API1.0" // $.gf_cont.ctxp
			,reqNamespace : "/service"   // $.gf_cont.reqNamespace	
			,logger: {
				level: 0	// $.gf_cont.logger.level
			}
		}
		,logger: {
			level: {
				 debug: 0	// $.logger.level.debug
				,info: 1
				,warn: 2
				,error: 3
			}			
			,debug: function(message, arg){
				if($.logger.level.debug >= $.gf_cont.logger.level && $window.console && $window.console.debug) {
					$window.console.debug(message);
					if(arg) {
						$window.console.debug(arg);
					}
				}
			}
			,info: function(message, arg){
				if($.logger.level.info >= $.gf_cont.logger.level && $window.console && $window.console.info) {
					$window.console.info(message);
					if(arg) {
						$window.console.info(arg);
					}
				}
			}
			,warn: function(message, arg){
				if($.logger.level.warn >= $.gf_cont.logger.level && $window.console && $window.console.warn) {
					$window.console.warn(message);
					if(arg) {
						$window.console.warn(arg);
					}
				}
			}
			,error: function(message, arg){
				if($.logger.level.error >= $.gf_cont.logger.level && $window.console && $window.console.error) {
					$window.console.error(message);
					if(arg) {
						$window.console.error(arg);
					}
				}
			}
		}
		,alert: function( message, options ) {
			
			var id = null;
			if( options && options.elemId ) {
				id = options.elemId;
			}
			else {
				id = $.gf_func.uuid();
			}
			
			if($("#" + id).length > 0) {
				return false;
			}
			
			var $div = $("<div>");
			$div.attr("id", id);
			$div.addClass("modal");
			
			var $p = $("<p>");
			$p.html(message);
			
			if(options && options.css) {
				
				if(options.css.p) {
					$p.css(options.css.p);
				}
				else {
					$p.css({height:"200px",overflow:"auto","color":"#666666"});
				}
				
				if(options.css.div) {
					$div.css(options.css.div);
				}
			}
			
			$div.append($p);
			
			$("body").append($div);
			
			$("#" + id).modal({
				fadeDuration: 100
			});
			
			if(options && options.console) {
				for(var i in options.console) {
					$.logger[i](options.console[i]);
				}
			}
			
		}		
		,gf_func: {
			// $.gf_func.callback(callback, arrayArgument)
			callback: function(callback, arrayArgument){

				if($.gf_func.isNotNull(callback) && typeof callback === 'function') {

					if( arrayArgument ) {
						$.logger.debug("-> arrayArgument type : " + Object.prototype.toString.apply(arrayArgument));
						
						if( Object.prototype.toString.apply(arrayArgument).indexOf("Array") > -1) {
							$.logger.info("[COMMONS-CALLBACK] execute Use Argument Callback function ");
							callback.apply(callback, arrayArgument);
						}
						else {
							alert(" Arguments to the callback function must be an array ");
						}
					}
					else {
						$.logger.info("[COMMONS-CALLBACK] execute None Argument Callback function ");
						callback.call(callback);
					}
				}
			},
    		uuid: function( isShort ){
				var hyphen = ( isShort === true ? "" : "-" );
    			var uuid = ("xxxxxxxx" + hyphen + "xxxx" + hyphen + "4xxx" + hyphen + "yxxx" + hyphen + "xxxxxxxxxxxx").replace(/[xy]/g, function (c) {
			        //noinspection NonShortCircuitBooleanExpressionJS
			        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
					//$.logger.error(v.toString(16));
			        return v.toString(16);
			    });
			    //$.logger.debug(" uuid : " + uuid);
				return uuid;
    		}			
			,isNull: function(sValue) {
				var jsRes = false;
				var jsVal = sValue;
				try {
					if (jsVal === null || jsVal === undefined) {
						jsRes = true;
					}else{
						var newString = new String(jsVal);
						if (newString === null || newString.valueOf() === "undefined") {
							jsRes = true;
						}
					}
				}catch( e ){
					$.logger.error( "gf_func.isNull : ", e);
				}
				return jsRes;
			},			
			isNotNull: function(sValue) {
				var jsRes = false;
				var jsVal = sValue;
				try {
					if(!this.isNull(jsVal)) {
						jsRes = true;
					}
				}catch( e ){
					$.logger.error( "gf_func.isNotNull : ", e);
				}
				return jsRes;
			},
			replaceAll: function(sValue, sOrg, sRep, sDef) {
				var jsRes = sValue;
				try {
					jsRes = new String( (jsRes ? jsRes : "") );

					if (jsRes.valueOf() == "undefined" || jsRes == "") {
						jsRes = (sDef ? sDef : "");
					}
					else{
						if(typeof jsRes != 'string'){
							jsRes = String(jsRes);
						}
						if(typeof jsRes != 'string'){
							$.logger.debug( jsRes + " 는 문자가 아닙니다.");
						}
						else {
							sOrg = (sOrg ? sOrg : "");
							sRep = (sRep ? sRep : "");
							jsRes = jsRes.split(sOrg).join(sRep);
						}
					}
				}catch(e){
					$.logger.error( "[COMMONS CORE REPLACE ALL] error", e );
				}

				return jsRes;
			}			
		}
		,gf_tx: {
			// $.gf_tx.ajax( URL, input, ajaxOption )
			ajax: function( URL, input, ajaxOption ) {
				//add browser que
				setTimeout(function(){
					$.ajax({ 
						url : $.gf_cont.ctxp + URL, 
						data: input,
						type: (!ajaxOption.method ? "POST" : ajaxOption.method), 
						headers : (!ajaxOption.headerJson ? null : ajaxOption.headerJson),
						dataType: (!ajaxOption.dataType ? "json" : ajaxOption.dataType), 
						async: true,
						cache: false,
						processData: true,
						beforeSend: function ( xhr ) {
							$.logger.info( "[TRANSACTION BEFORESEND]");
							$.gf_func.callback(ajaxOption.fn_beforeSend, [xhr]);
						},			
						timeout : function(){
							$.logger.error( "[TRANSACTION TIMEOUT]");
							$.gf_func.callback(ajaxOption.fn_timeout);
						},
						success : function(data, status, xhr) {
							$.logger.info("[TRANSACTION SUCCESS]");
							$.gf_func.callback(ajaxOption.fn_success, [data]);
						}, 
						error: function(jqXHR, textStatus, errorThrown) {
							$.logger.error("[TRANSACTION ERROR]");
							$.gf_func.callback(ajaxOption.fn_error, [xhr, textStatus, errorThrown]);
						},
						statusCode : {
							404 : function() {
								$.logger.warn("*- $.gf_transaction.ajax.statusCode 404 page not found 페이지가 존재하지않습니다.");
							},
							403 : function(){
								$.logger.warn("*- $.gf_transaction.ajax.statusCode 403 Forbidden 접근권한없습니다.");
							},
							500 : function(){
								$.logger.error("*- $.gf_transaction.ajax.statusCode 500 Internal Server Error 소프트웨어 장애가 발생하였습니다.");
							},
							503 : function(){
								$.logger.warn("*- $.gf_transaction.ajax.statusCode 503 Overhead Service Maintenance 접속량초과입니다 잠시후 다시 시도하세요.");
							},
							400 : function(){
								$.logger.warn("*- $.gf_transaction.ajax.statusCode 400 Bad Request, 요청실패 문법상 오류가 있어, 서버가 요청사항을 이해하지 못하였습니다. 클라이언트는 수정후 다시 시도하세요.");
							},
							405 : function(){
								$.logger.warn("*- $.gf_transaction.ajax.statusCode 405 Method not allowed, 메쏘드 허용안됨 Request 라인에 명시된 메쏘드를 수행하기 위해 해당 자원의 이용이 허용되지 않았습니다. 요청한 자원에 적절한 MIME 타입을 갖고 있는지 확인해 보세요.");
							}
						},
						complete : function(){
							$.logger.debug( "[TRANSACTION COMPLETE]" );
							$.gf_func.callback(ajaxOption.fn_complete);
						}			
					}); 
				}, 1);
			}
		}
	});
})( jQuery, window );

//]]>