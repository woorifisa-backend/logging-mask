package dev.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    // 1. 로거를 가져옵니다. (가경이의 도구)
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("=== 로그 테스트 시작 ===");

        // 2. 가경이가 보내는 샘플 데이터 (User.toString() 형태)
        // 유정 님의 컨버터가 pw=1234를 지우고, name과 rrn을 마스킹해야 합니다.
        String userInfo = "name=홍길동, rrn=950101-1234567, pw=1234";

        // 3. 로그 출력 (이때 logback.xml에 등록된 유정 님의 컨버터가 작동함)
        logger.info(userInfo);

        System.out.println("=== 로그 테스트 종료 (콘솔과 logs 폴더를 확인하세요) ===");
    }
}