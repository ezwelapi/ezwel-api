package ezwel_if;

import java.rmi.dgc.VMID;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.CryptUtil;
import com.ezwel.htl.interfaces.commons.utils.crypt.Base64Codec;
import com.ezwel.htl.interfaces.commons.utils.crypt.MD5;
/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 20.
 */
@APIType
public class APIUtilTest {

	private static final Logger logger = LoggerFactory.getLogger(APIUtilTest.class);
	
	private APIUtil apiUtil;
	
	public APIUtilTest() {
		apiUtil = new APIUtil();
	}
	
	@Test
	public void test() {
		logger.debug(
				apiUtil.getTimeMillisToDate(System.currentTimeMillis(), "yyyyMMddkkmmss")
				);
		 


		logger.debug("{}", APIUtil.getTimeMillisToSecond(4036939));
		logger.debug("{}", APIUtil.getTimeMillisToMinute(4036939));		
	}
	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 inside/outside API KEY 생성
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 20.
	 */
	//@Test
	public void createApiKey() {
		String out = null;
		// inside api key
		out = apiUtil.createApiKey("i", "호텔엔조이_메이트아이", "10000495");
		logger.debug("호텔엔조이({}) : {}", out.length(), out);

		out = apiUtil.createApiKey("o", "호텔엔조이_메이트아이", "10000495");
		logger.debug("호텔엔조이({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("i", "펜션라이프_플레이스엠", "10000496");
		logger.debug("펜션라이프({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("o", "펜션라이프_플레이스엠", "10000496");
		logger.debug("펜션라이프({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("i", "인터파크투어_인터파크INT", "10002654");
		logger.debug("인터파크투어({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("o", "인터파크투어_인터파크INT", "10002654");
		logger.debug("인터파크투어({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("i", "펀앤비즈_샬레코리아", "10008365");
		logger.debug("펀앤비즈({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("o", "펀앤비즈_샬레코리아", "10008365");
		logger.debug("펀앤비즈({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("i", "우리펜션(옐로트래블랩스)", "10030653");
		logger.debug("우리펜션({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("o", "우리펜션(옐로트래블랩스)", "10030653");
		logger.debug("우리펜션({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("i", "인터치투어", "10053461");
		logger.debug("인터치투어({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("o", "인터치투어", "10053461");
		logger.debug("인터치투어({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("i", "웹투어", "10054233");
		logger.debug("웹투어({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("o", "웹투어", "10054233");
		logger.debug("웹투어({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("i", "호텔패스글로벌", "10055550");
		logger.debug("호텔패스글로벌({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("o", "호텔패스글로벌", "10055550");
		logger.debug("호텔패스글로벌({}) : {}", out.length(), out);
		
		out = apiUtil.createApiKey("i", "(주)위드이노베이션_여기어때", "10056562");
		logger.debug("위드이노베이션({}) : {}", out.length(), out);

		out = apiUtil.createApiKey("o", "(주)위드이노베이션_여기어때", "10056562");
		logger.debug("위드이노베이션({}) : {}", out.length(), out);
		
		String apiKey = "d099b5ed2d8d352d6bb539febb4b46aai";
		String sect = "10000495".concat("-").concat(APIUtil.getRandomUUID().concat("-").concat(APIUtil.getVMID()));
		double time = (System.currentTimeMillis() / 1000.);
		
		StringBuffer strbf = new StringBuffer();
		strbf.append(apiKey);
		strbf.append("-");
		strbf.append(sect);
		strbf.append("-");
		strbf.append(Double.toString(time));
		
		logger.debug("final : {}", strbf.toString());
		
		logger.debug("final last : {}", DigestUtils.sha256Hex(strbf.toString()));
		
		logger.debug("vm : {}", APIUtil.getVMID());
		
		
		//createApiSignature();
		String newSign = apiUtil.getHttpSignature("10000495", "d099b5ed2d8d352d6bb539febb4b46aai", Long.toString(System.currentTimeMillis()));
		logger.debug("new signature({}) : {}", newSign.length(), newSign);
		
		logger.debug("decode : {}", CryptUtil.getDecodeBase64(newSign));
	}
	
	/**
	 * 코드 언어 : 자바
	 * 필요 API
	 * import java.util.UUID;
	 * import java.rmi.dgc.VMID;
	 * import org.apache.commons.codec.digest.DigestUtils;
	 * 메서드 : createApiSignature
	 */
	public void createApiSignature() {

		//API 키
		String httpApiKey = "d099b5ed2d8d352d6bb539febb4b46aai";
		
		//공유 비밀 키 : 에이전트 아이디 + "-" + UUID + "-" + VMID 
		String shardSecret = new StringBuffer().append("10000495").append("-").append(UUID.randomUUID().toString()).append("-").append(new VMID().toString()).toString();
		
		//타임 스탬프
		double timestamp = (System.currentTimeMillis() / 1000.);
		
		//API 싸인
		String httpApiSignature = DigestUtils.sha256Hex(new StringBuffer().append(httpApiKey).append("-").append(shardSecret).append("-").append(Double.toString(timestamp)).toString());
		
		logger.debug("httpApiSignature : {}", httpApiSignature);
	}
	
	
	@Test
	public void getTimeStamp() {
		Timestamp original = new Timestamp(System.currentTimeMillis());
		logger.debug("1 : {}", original);
		//new Timestamp(System.currentTimeMillis());
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", Locale.KOREAN).format(new Date());
		logger.debug("timeStamp : {}", timeStamp);
		//String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
		//return timeStamp;
	}
	
}

