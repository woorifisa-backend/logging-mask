package dev.logging.util;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class MaskingUtils {
	public static String maskName(String log) {
	    if (log == null) return null;

	    Pattern pattern = Pattern.compile("(name=)([^,\\s]+)");
	    Matcher matcher = pattern.matcher(log);

	    StringBuffer sb = new StringBuffer();

	    while (matcher.find()) {
	        String prefix = matcher.group(1); // name=
	        String realName = matcher.group(2); // 실제 이름

	        String masked = prefix + maskByLength(realName);
	        matcher.appendReplacement(sb, masked);
	    }
	    matcher.appendTail(sb);

	    return sb.toString();
	}
	
	
	
	private static String maskByLength(String name) {
	    int len = name.length();

	    if (len <= 1) {
	        return name;                 // 1글자: 그대로
	    }
	    if (len == 2) {
	        return name.charAt(0) + "*"; // 홍길 → 홍*
	    }
	    if (len == 3) {
	        return name.charAt(0) + "*" + name.charAt(2); // 홍길동 → 홍*동
	    }

	    // 4글자 이상
	    return name.charAt(0)
	         + "*".repeat(len - 2)
	         + name.charAt(len - 1);
	}
	
    public static String maskPhone(String phone) {
        if (phone == null) return phone;
        return phone.replaceAll(
            "(01[016789])-(\\d{4})-(\\d{4})",
            "$1-****-$3"
        );
    }

    public static String maskResidentId(String rrn) {
        if (rrn == null) return rrn;
        return rrn.replaceAll(
            "(\\d{6})-(\\d{7})",
            "$1-*******"
        );
    }

    public static String maskAccount(String account) {
        if (account == null) return account;
        return account.replaceAll(
//            "(\\d+)-(\\d+)-(\\d{2})(\\d{4})",
//            "****-***-**$4"
        	"\\d{3}-\\d{7}-(\\d{1}-\\d{3})", 
            "***-*******-$1"
        );
    }
}
