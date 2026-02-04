package dev.logging;

import dev.logging.util.MaskingUtils;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("이름: " + MaskingUtils.maskName("박혜윤"));

		System.out.println("전화: " + MaskingUtils.maskPhone("010-1234-5678"));

		System.out.println("주민: " + MaskingUtils.maskResidentId("030623-1234567"));

		System.out.println("계좌: " + MaskingUtils.maskAccount("1002-345-678901"));
	}

        System.out.println("=== 프로그램 종료 ===");
    }
}
