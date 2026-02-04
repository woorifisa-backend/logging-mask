package dev.logging.util;

public class MaskingUtils {
	public static String maskName(String name) {
	    if (name == null) return null;
	    return name.replaceAll(
	        "(name=)([^,\\s])[^,\\s]*",
	        "$1$2**"
	    );
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
