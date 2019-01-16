package com.ezwel.htl.interfaces.server.commons.constants;

import java.util.HashMap;
import java.util.Map;

public class NamespaceConstants {
	
	private static Map<String, String> NAMESPACE_MAP;
	
	static {
		NAMESPACE_MAP = new HashMap<String, String>();

		//NAMESPACE_MAP.put("OUTSIDE_MAPPER", "com.ezwel.htl.interfaces.server.repository.outsideMapper");
		//NAMESPACE_MAP.put("IF_COMM_MAPPER", "com.ezwel.htl.interfaces.server.repository.interfaceCommMapper");
		NAMESPACE_MAP.put("SEQUNCE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcSequnceMapper");
		NAMESPACE_MAP.put("API_LOG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcApiLogMapper");
		NAMESPACE_MAP.put("API_REQ_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcApiReqMapper");
		NAMESPACE_MAP.put("API_REQ_TEMPLATE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcApiReqTemplateMapper");
		NAMESPACE_MAP.put("AREA_CD_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcAreaCdMapper");
		NAMESPACE_MAP.put("AUTH_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcAuthMapper");
		NAMESPACE_MAP.put("AUTH_GRP_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcAuthGrpMapper");
		NAMESPACE_MAP.put("AUTH_MAPPING_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcAuthMappingMapper");
		NAMESPACE_MAP.put("CACHE_DAY_PRICE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCacheDayPriceMapper");
		NAMESPACE_MAP.put("CACHE_DAY_PRICE_LOG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCacheDayPriceLogMapper");
		NAMESPACE_MAP.put("CACHE_MIN_AMT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCacheMinAmtMapper");
		NAMESPACE_MAP.put("CACHE_SEARCH_LOG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCacheSearchLogMapper");
		NAMESPACE_MAP.put("CHANNEL_BLACK_FACL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcChannelBlackFaclMapper");
		NAMESPACE_MAP.put("CHANNEL_CITYCD_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcChannelCitycdMapper");
		NAMESPACE_MAP.put("CHANNEL_CLIENT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcChannelClientMapper");
		NAMESPACE_MAP.put("CHANNEL_CLIENT_PARTNER_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcChannelClientPartnerMapper");
		NAMESPACE_MAP.put("CHANNEL_MNG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcChannelMngMapper");
		NAMESPACE_MAP.put("CHANNEL_PARTNER_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcChannelPartnerMapper");
		NAMESPACE_MAP.put("CHANNEL_ROOM_TYPE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcChannelRoomTypeMapper");
		NAMESPACE_MAP.put("CITY_CD_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCityCdMapper");
		NAMESPACE_MAP.put("CLASS_CD_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcClassCdMapper");
		NAMESPACE_MAP.put("CLIENT_INFO_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcClientInfoMapper");
		NAMESPACE_MAP.put("CNCLIENT_BLACK_FACL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCnclientBlackFaclMapper");
		NAMESPACE_MAP.put("CNCLIENT_CITYCD_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCnclientCitycdMapper");
		NAMESPACE_MAP.put("CNCLIENT_FACL_MARK_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCnclientFaclMarkMapper");
		NAMESPACE_MAP.put("CNCLIENT_ROOM_TYPE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCnclientRoomTypeMapper");
		NAMESPACE_MAP.put("CPN_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCpnMapper");
		NAMESPACE_MAP.put("CPN_CHANNEL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCpnChannelMapper");
		NAMESPACE_MAP.put("CPN_CITY_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCpnCityMapper");
		NAMESPACE_MAP.put("CPN_CLIENT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCpnClientMapper");
		NAMESPACE_MAP.put("CPN_FACL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCpnFaclMapper");
		NAMESPACE_MAP.put("CPN_PARTNER_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCpnPartnerMapper");
		NAMESPACE_MAP.put("CPN_ROOM_TYPE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCpnRoomTypeMapper");
		NAMESPACE_MAP.put("CPN_USE_HIST_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcCpnUseHistMapper");
		NAMESPACE_MAP.put("DEPOSIT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDepositMapper");
		NAMESPACE_MAP.put("DEPOSIT_ISSUE_HIST_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDepositIssueHistMapper");
		NAMESPACE_MAP.put("DEPOSIT_USE_HIST_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDepositUseHistMapper");
		NAMESPACE_MAP.put("DETAIL_CD_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDetailCdMapper");
		NAMESPACE_MAP.put("DISP_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDispMapper");
		NAMESPACE_MAP.put("DIST_CANCEL_RULE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDistCancelRuleMapper");
		NAMESPACE_MAP.put("DIST_EVID_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDistEvidMapper");
		NAMESPACE_MAP.put("DIST_FACL_DETAIL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDistFaclDetailMapper");
		NAMESPACE_MAP.put("DIST_ROOM_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDistRoomMapper");
		NAMESPACE_MAP.put("DIST_ROOM_AMT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDistRoomAmtMapper");
		NAMESPACE_MAP.put("DIST_ROOM_OPT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcDistRoomOptMapper");
		NAMESPACE_MAP.put("FACL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcFaclMapper");
		NAMESPACE_MAP.put("FACL_AMENT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcFaclAmentMapper");
		NAMESPACE_MAP.put("FACL_HIST_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcFaclHistMapper");
		NAMESPACE_MAP.put("FACL_IMG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcFaclImgMapper");
		NAMESPACE_MAP.put("FACL_REVIEW_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcFaclReviewMapper");
		NAMESPACE_MAP.put("FACL_REVIEW_GRADE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcFaclReviewGradeMapper");
		NAMESPACE_MAP.put("FACL_REVIEW_IMG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcFaclReviewImgMapper");
		NAMESPACE_MAP.put("FREQ_TXT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcFreqTxtMapper");
		NAMESPACE_MAP.put("HOLIDAY_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcHolidayMapper");
		NAMESPACE_MAP.put("MAPPING_FACL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMappingFaclMapper");
		NAMESPACE_MAP.put("MAPPING_GRP_FACL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMappingGrpFaclMapper");
		NAMESPACE_MAP.put("MEM_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMemMapper");
		NAMESPACE_MAP.put("MENU_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMenuMapper");
		NAMESPACE_MAP.put("MGR_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMgrMapper");
		NAMESPACE_MAP.put("MGR_LOG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMgrLogMapper");
		NAMESPACE_MAP.put("MGR_NOTICE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMgrNoticeMapper");
		NAMESPACE_MAP.put("MGR_NOTICE_ATTACH_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMgrNoticeAttachMapper");
		NAMESPACE_MAP.put("MY_CPN_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcMyCpnMapper");
		NAMESPACE_MAP.put("PARTNER_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPartnerMapper");
		NAMESPACE_MAP.put("PARTNER_EZWEL_MGR_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPartnerEzwelMgrMapper");
		NAMESPACE_MAP.put("POPUL_SEARCH_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPopulSearchMapper");
		NAMESPACE_MAP.put("PRM_CHANNEL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmChannelMapper");
		NAMESPACE_MAP.put("PRM_CLIENT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmClientMapper");
		NAMESPACE_MAP.put("PRM_COMMENT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmCommentMapper");
		NAMESPACE_MAP.put("PRM_CPN_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmCpnMapper");
		NAMESPACE_MAP.put("PRM_GOODS_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmGoodsMapper");
		NAMESPACE_MAP.put("PRM_MNG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmMngMapper");
		NAMESPACE_MAP.put("PRM_SURVEY_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmSurveyMapper");
		NAMESPACE_MAP.put("PRM_SURVEY_ANSWER_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmSurveyAnswerMapper");
		NAMESPACE_MAP.put("PRM_SURVEY_USER_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmSurveyUserMapper");
		NAMESPACE_MAP.put("PRM_TAB_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcPrmTabMapper");
		NAMESPACE_MAP.put("QNA_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcQnaMapper");
		NAMESPACE_MAP.put("RESERV_BASE_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcReservBaseMapper");
		NAMESPACE_MAP.put("RESERV_CANCEL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcReservCancelMapper");
		NAMESPACE_MAP.put("RESERV_MNG_HIS_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcReservMngHisMapper");
		NAMESPACE_MAP.put("RESERV_PAY_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcReservPayMapper");
		NAMESPACE_MAP.put("RESERV_ROOM_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcReservRoomMapper");
		NAMESPACE_MAP.put("RESERV_ROOM_OPT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcReservRoomOptMapper");
		NAMESPACE_MAP.put("RESERV_RULE_PENALTY_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcReservRulePenaltyMapper");
		NAMESPACE_MAP.put("SPOT_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcSpotMapper");
		NAMESPACE_MAP.put("SPOT_MAPPING_FACL_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcSpotMappingFaclMapper");
		NAMESPACE_MAP.put("IF_LOG_MAPPER", "com.ezwel.htl.interfaces.server.repository.EzcIfLogMapper");
	}
	
	public static Map<String, String> getNamespace() {
		return NAMESPACE_MAP;
	}
	
	public static String getNamespace(String key) {
		return NAMESPACE_MAP.get(key);
	}
}
