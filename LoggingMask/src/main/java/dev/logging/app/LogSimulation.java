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
			System.out.println("[1] 회원가입 [2] 로그인");
			System.out.print("번호를 입력하세요 : ");
			int initChoice = sc.nextInt();
			if (initChoice == 1)
				signUp();
			else if (initChoice == 2) {
				if (signIn())
					break;
				else
					continue;
			} else
				System.out.println("번호를 다시 입력해주세요.");
		}

		// 서비스(입/출금)
		// 로그인 후 화면 로직
		while (true) {
			System.out.println("[1] 입금 [2] 출금");
			System.out.print("번호를 입력하세요 : ");
			int serviceChoice = sc.nextInt();
			if (!(serviceChoice == 1 || serviceChoice == 2)) {
				System.out.println("번호를 다시 입력해주세요.");
				continue;
			}

			System.out.print("금액을 입력하세요 : ");
			long amount = sc.nextInt();

			if (serviceChoice == 1)
				deposit(amount);
			else if (serviceChoice == 2)
				withdraw(amount);

		}
	}

	// 회원가입
	public void signUp() {
		System.out.println("[회원가입 - 정보 입력]");
		System.out.print("이름 : ");
		String name = sc.next();
		System.out.print("주민번호(xxxxxx-xxxxxxx) : ");
		String rrn = sc.next();
		System.out.print("비밀번호 : ");
		String pw = sc.next();

		Member member = new Member(name, rrn, pw);
		memberList.add(member);

		logger.info("{}", member.toString());
		System.out.println(">> 회원가입 성공!");
		System.out.println(">> 계좌번호가 생성되었습니다.");
	}

	// 로그인
	public boolean signIn() {
		System.out.println("[로그인]");
		System.out.print("이름 : ");
		String inputName = sc.next();
		System.out.print("비밀번호 : ");
		String inputPW = sc.next();

		for (Member m : memberList) {
			// 1. 이름 확인
			if (m.getName().equals(inputName)) {
				// 2. 비밀번호 확인
				if (m.getPw().equals(inputPW)) {
					// 비밀번호도 맞음 -> 로그인 성공!
					this.currentMember = m; // 현재 로그인한 사람으로 등록

					// 로그인 성공 로그
					logger.info("{}님 로그인 성공", currentMember.toString());
					System.out.println(">> 로그인 성공! 환영합니다. " + currentMember.getName() + "님");

					return true;
				} else {
					// 이름은 맞는데 비밀번호가 틀림
					logger.warn("로그인 실패: 비밀번호 불일치 (시도한 ID: {})", inputName);
					System.out.println(">> 비밀번호가 일치하지 않습니다.");
				}
				break;
			}
		}
		System.out.println(">> 일치하는 이름이 없습니다.");
		return false;
	}

	// 입금
	public void deposit(long amount) {
		logger.info("[입금 성공] 이름: {}, 계좌번호: {}, 금액: {}원", 
				currentMember.getName(), 
				currentMember.getAccountNumber(), 
				amount);

		System.out.println(">> 입금 처리가 완료되었습니다.");
	}

	// 출금
	public void withdraw(long amount) {
		logger.info("[출금 성공] 이름: {}, 계좌번호: {}, 금액: {}원", 
				currentMember.getName(), 
				currentMember.getAccountNumber(), 
				amount);

		System.out.println(">> 출금 처리가 완료되었습니다.");
	}
}
