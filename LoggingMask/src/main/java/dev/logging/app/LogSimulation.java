package dev.logging.app;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogSimulation {
	// 이 클래스에서 발생한 로그를 수집할 로거 생성
	private static final Logger logger = LoggerFactory.getLogger(LogSimulation.class);
	private Member currentMember = null;
	private List<Member> memberList = new ArrayList<>();
	private Scanner sc = new Scanner(System.in);

//	logger.info("{}", member);

	public void start() {
		System.out.println("=== LoggingMask 서비스 시작 ===");

		// 회원가입/로그인
		// 로그인 전 화면 로직
		while (true) {
			System.out.println("=========== 회원가입/로그인 =========");
			System.out.println("[1] 회원가입 [2] 로그인");
			System.out.print("번호를 입력하세요 : ");
			int initChoice = getValidIntInput();
			if (initChoice == 1)
				signUp();
			else if (initChoice == 2) {
				if (signIn())
					break;
				else
					continue;
			} else
				System.out.println("번호를 다시 입력해주세요." + "\n");
		}

		// 서비스(입/출금)
		// 로그인 후 화면 로직
		while (true) {
			System.out.println("=========== 서비스 선택 =========");
			System.out.println("[1] 입금 [2] 출금 [3] 종료");
			System.out.print("번호를 입력하세요 : ");

			int serviceChoice = getValidIntInput();

			if (serviceChoice == 3) {
				System.out.println("시스템을 종료합니다.");
				return;
			}

			if (!(serviceChoice == 1 || serviceChoice == 2)) {
				System.out.println("번호를 다시 입력해주세요." + "\n");
				continue;
			}
			System.out.print("금액을 입력하세요 : ");
			long amount = getValidLongInput();

			if (serviceChoice == 1)
				deposit(amount);
			else if (serviceChoice == 2)
				withdraw(amount);

		}
	}

	private int getValidIntInput() {
		while (true) {
			try {
				return sc.nextInt(); // 정상적으로 입력하면 바로 숫자 반환
			} catch (InputMismatchException e) {
				// 문자가 입력되면 에러가 발생
				sc.nextLine();
				System.out.println(">> 숫자만 입력해주세요.");
				System.out.print("다시 입력하세요 : ");
			}
		}
	}

	private long getValidLongInput() {
		while (true) {
			try {
				return sc.nextLong();
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println(">> 올바른 금액(숫자)을 입력해주세요.");
				System.out.print("금액을 다시 입력하세요 : ");
			}
		}
	}

	// 회원가입
	public void signUp() {
		System.out.println("\n=========== [회원가입 - 정보 입력] ===========");
		sc.nextLine(); 

	    String name = "";
	    while (true) {
	        System.out.print("이름 : ");
	        name = sc.nextLine(); 

	        if (Member.isValidName(name)) {
	            break;
	        } else {
	            System.out.println(">> 이름은 한글만 입력 가능하며, 공백이 두 글자 이상 작성해주세요.");
	        }
	    }

		String rrn = "";
		while (true) {
			System.out.print("주민번호(xxxxxx-xxxxxxx) : ");
			rrn = sc.nextLine();

			if (Member.isValidRrn(rrn)) {
				break;
			} else {
				System.out.println(">> 주민번호 형식이 올바르지 않습니다.");
			}
		}

		String pw = "";
		while (true) {
			System.out.print("비밀번호(영문/숫자 4자리 이상) : ");
			pw = sc.nextLine();

			if (Member.isValidPw(pw)) {
				break;
			} else {
				System.out.println(">> 비밀번호는 영문/숫자 포함 4글자 이상이어야 합니다.");
			}
		}

		try {
			Member member = new Member(name, rrn, pw);
			memberList.add(member);

			logger.info("신규 회원가입: {}", member.toString());
			System.out.println(">> 회원가입 성공!");
			System.out.println(">> 계좌번호가 생성되었습니다." + "\n");

		} catch (IllegalArgumentException e) {
			System.out.println(">> 회원가입 실패: " + e.getMessage());
		}
	}

	// 로그인
	public boolean signIn() {
		System.out.println("\n============= [로그인] =============");
		sc.nextLine();
		System.out.print("이름 : ");
		String inputName = sc.nextLine();
		System.out.print("비밀번호 : ");
		String inputPW = sc.nextLine();

		boolean isFound = false;
		for (Member m : memberList) {
			// 1. 이름 확인
			if (m.getName().equals(inputName)) {
				isFound = true;
				// 2. 비밀번호 확인
				if (m.getPw().equals(inputPW)) {
					// 비밀번호도 맞음 -> 로그인 성공!
					this.currentMember = m; // 현재 로그인한 사람으로 등록

					// 로그인 성공 로그
					logger.info("{}님 로그인 성공", currentMember.toString());
					System.out.println(">> 로그인 성공! 환영합니다. " + currentMember.getName() + "님" + "\n");

					return true;
				} else {
					// 이름은 맞는데 비밀번호가 틀림
					logger.warn("로그인 실패: 비밀번호 불일치 // name={}", inputName);
					System.out.println(">> 비밀번호가 일치하지 않습니다." + "\n");
				}
				break;
			}
			isFound = false;
		}
		if (!isFound)
			System.out.println(">> 일치하는 이름이 없습니다." + "\n");
		return false;
	}

	// 입금
	public void deposit(long amount) {
		logger.info("[입금 성공] name={}, accountNumber={}, amount={}원", currentMember.getName(),
				currentMember.getAccountNumber(), amount);

		System.out.println(">> 입금 처리가 완료되었습니다." + "\n");
	}

	// 출금
	public void withdraw(long amount) {
		logger.info("[출금 성공] name={}, accountNumber={}, amount={}원", currentMember.getName(),
				currentMember.getAccountNumber(), amount);

		System.out.println(">> 출금 처리가 완료되었습니다." + "\n");
	}
}
