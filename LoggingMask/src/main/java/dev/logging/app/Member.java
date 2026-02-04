package dev.logging.app;

import java.util.Random;

public class Member {
	private String name;
	private String rrn;
	private String pw;
	private String accountNumber;

	public Member(String name, String rrn, String pw) {
		super();
		this.name = name;
		this.rrn = rrn;
		this.pw = pw;
		this.accountNumber = setAccountNumber();
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
