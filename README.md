# Logging Mask Module

Logback 기반의 **로그 마스킹 & 보안 필터 라이브러리**입니다.  
로그에 포함될 수 있는 개인식별정보(PII)를 자동으로 마스킹하고,  
**비밀번호(pw)는 파일 로그에 기록되지 않도록 차단**합니다.

Spring 없이 **순수 Java + Logback 설정만으로 적용**할 수 있도록 설계되었습니다.

---

## ✨ Features

- 개인정보 자동 마스킹 (이름, 주민번호, 계좌번호)
- 비밀번호(pw) 포함 로그 파일 차단
- Logback Filter / Converter 확장 구조
- 애플리케이션 코드 수정 최소화
- 라이브러리 형태로 재사용 가능

---

## 🛠 Tech Stack

- Java 17
- SLF4J
- Logback 1.4.x
- Maven

---

## 👥 Team

<table>
  <tr>
     <td align="center">
      <a href="https://github.com/ktx06155">
        <img src="https://avatars.githubusercontent.com/ktx06155" width="120px;" alt="김가경"/>
        <br />
        <sub><b>김가경</b></sub>
      </a>
      <br />
    </td>
    <td align="center">
      <a href="https://github.com/yujeong">
        <img src="https://avatars.githubusercontent.com/yujung23" width="120px;" alt="김유정"/>
        <br />
        <sub><b>김유정</b></sub>
      </a>
      <br />
    </td>
    <td align="center">
      <a href="https://github.com/je0ngyoung">
        <img src="https://avatars.githubusercontent.com/zerobak13" width="120px;" alt="박제영"/>
        <br />
        <sub><b>박제영</b></sub>
      </a>
      <br />
    </td>
    <td align="center">
      <a href="https://github.com/hyeyoon">
        <img src="https://avatars.githubusercontent.com/hyeyoon23" width="120px;" alt="이혜윤"/>
        <br />
        <sub><b>이혜윤</b></sub>
      </a>
      <br />
    </td>
  </tr>
</table>
