package dev.logging.app;

import java.util.Random;
import java.util.regex.Pattern;

public class Member {
	private String name;
	private String rrn;
	private String pw;
	private String accountNumber;
	
	public static final String NAME_REGEX = "^[가-힣]{2,}$";
	public static final String RRN_REGEX = "^\\d{6}-\\d{7}$";
    public static final String PW_REGEX = "^[a-zA-Z0-9!@#$%^&*]{4,}$";

	public Member(String name, String rrn, String pw) {
		if (!isValidName(name)) {
            throw new IllegalArgumentException("이름 형식이 올바르지 않습니다.");
        }
		if (!isValidRrn(rrn)) {
            throw new IllegalArgumentException("주민번호 형식이 올바르지 않습니다.");
        }
        if (!isValidPw(pw)) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
        
		this.name = name;
		this.rrn = rrn;
		this.pw = pw;
		this.accountNumber = setAccountNumber();
	}
	
	public static boolean isValidName(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }
	
	public static boolean isValidRrn(String rrn) {
        return Pattern.matches(RRN_REGEX, rrn);
    }

    public static boolean isValidPw(String pw) {
        return Pattern.matches(PW_REGEX, pw);
    }

	// 14자리 랜덤 계좌번호 생성 메서드
	// 포맷팅: YYY-ZZZZZZZ-C-XXX
	private static String setAccountNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 14; i++) {
			sb.append(random.nextInt(10)); // 0부터 9까지 랜덤 정수
		}

		sb.insert(3, "-");
		sb.insert(11, "-");
		sb.insert(13, "-");

		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public String getRrn() {
		return rrn;
	}

	public String getPw() {
		return pw;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	@Override
	public String toString() {
		return "name=" + name + ", rrn=" + rrn 
				+ ", pw=" + pw + ", accountNumber=" + accountNumber;
	}

}
