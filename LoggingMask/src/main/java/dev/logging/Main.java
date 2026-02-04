package dev.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        String name = "이혜윤";
        String rrn = "030623-1234567";
        String pw = "1234";
        String account = "1002-345-678901";

        // =========================
        // 1️⃣ 회원가입 성공 (pw 미포함) → 파일 로그 OK
        // =========================
        log.info("회원가입 성공 name={}, rrn={}, account={}", name, rrn, account);
        System.out.println("성공! 계좌번호 생성되었습니다.");

        // =========================
        // 2️⃣ 실수 상황: pw 포함 로그 → FILE에서 DENY
        // =========================
        log.info("회원가입 raw payload name={}, rrn={}, pw={}, account={}",
                name, rrn, pw, account);

        // =========================
        // 3️⃣ 로그인 성공/실패 시나리오
        // =========================
        boolean loginSuccess = pw.equals("1234"); // 테스트용

        if (loginSuccess) {
            log.info("{}님 로그인 성공", name);
            System.out.println("로그인 성공");
        } else {
            log.warn("{}님 로그인 실패", name);
            System.out.println("다시 로그인해주세요");
        }

        // =========================
        // 4️⃣ 또 다른 pw 포함 케이스 → FILE에서 DENY
        // =========================
        log.info("login request pw={}", pw);

        System.out.println("=== 프로그램 종료 ===");
    }
}
