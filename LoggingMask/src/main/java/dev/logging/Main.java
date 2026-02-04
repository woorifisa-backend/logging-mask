package dev.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		System.out.println("=== 로그 테스트 시작 ===");

		String userInfo = "name=홍길동, rrn=950101-1234567, pw=1234";
		logger.info(userInfo);

		System.out.println("=== 로그 테스트 종료 (콘솔과 logs 폴더를 확인하세요) ===");
	}
}