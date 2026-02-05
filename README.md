# Logging Mask Module

Logback 기반의 **로그 마스킹 & 보안 필터 라이브러리**입니다.  
로그에 포함될 수 있는 개인식별정보(PII)를 자동으로 마스킹하고,  
**비밀번호(pw)는 파일 로그에 기록되지 않도록 차단**합니다.

---

## ✨ Features

### 1️⃣ PII 자동 마스킹

`SensitiveDataConverter`를 통해 로그 메시지에 포함된 개인식별정보(PII)를 **출력 직전에 자동으로 마스킹**합니다.

- 콘솔 로그 / 파일 로그 **공통 적용**
- 정규식 기반 마스킹
- 출력 포맷(`%masking`)에 끼워 넣는 방식

#### 🔍 마스킹 대상 예시

| 항목 | Before | After |
|-----|--------|-------|
| 이름 | `name=김우리` | `name=김**` |
| 주민등록번호 | `010101-1234567` | `010101-*******` |
| 휴대폰번호 | `010-1234-5678` | `010-****-5678` |
| 계좌번호 (특정 포맷) | `000-0000000-0-000` | `***-*******-0-000` |

> 마스킹 로직은 `MaskingUtils`에 정의된 **정규식 규칙**을 사용합니다.  
> 마스킹 대상이 추가되거나 포맷이 변경될 경우  
> `MaskingUtils`만 수정하면 됩니다.

---

### 2️⃣ Password(pw) 포함 로그 차단

비밀번호는 **마스킹이 아닌 “기록 금지”**가 보안상 안전하다고 판단하여,  
`SecurityTaggingFilter`를 통해 **파일 로그에서 완전히 차단**합니다.

#### 동작 방식

- 로그 메시지에 `pw` 키가 포함되면
- 해당 로그 이벤트는 `FilterReply.DENY`
- **파일에 기록되지 않음**

#### 감지 패턴 예시

- `pw=...`, `pw:...`
- `"pw": "..."`
- URL Query String  
  - `?pw=...`  
  - `&pw=...`

> 권장 설정  
> - 콘솔 로그: 필터 미적용 (개발 편의성)  
> - 파일 로그: 필터 적용 (영구 저장 / 외부 전송 위험)

---

## 🧱 Architecture (구성 요소 & 책임)

이 라이브러리는 **역할이 명확히 분리된 3가지 컴포넌트**로 구성됩니다.

---

### 1️⃣ Filter — `SecurityTaggingFilter`

**역할**  
- 로그 이벤트를 *기록하기 전에* 검사하여, 보안상 남기면 안 되는 로그를 **차단(DENY)** 하는 보안 게이트

**특징**
- `Filter<ILoggingEvent>` 구현
- `event.getFormattedMessage()` 검사
- `pw` 키 포함 시 `FilterReply.DENY` 반환
- 일반적으로 **FILE appender에만 적용**

**핵심 개념**  
> `pw`는 절대 남기지 않는다 → **Filter로 차단**

---

### 2️⃣ Converter — `SensitiveDataConverter`

**역할**  
로그 메시지를 *출력 직전에 가공*하여  
민감 정보를 마스킹 / 제거하는 변환기

**특징**
- `ClassicConverter` 구현
- `logback.xml`의 `conversionRule`로 `%masking` 등록
- 패턴에 `%masking`이 포함되면 `convert()` 호출

**처리 흐름**
1. `pw=...` 형태 문자열 1차 제거 (방어적 처리)
2. `MaskingUtils`를 통한 PII 마스킹
3. 마스킹된 문자열 반환

**핵심 개념**  
> 출력 포맷에 끼워 넣어 **전역 마스킹**을 가능하게 한다

---

### 3️⃣ Masking Utils — `MaskingUtils`

**역할**  
정규식 기반의 **마스킹 규칙을 전담 관리하는 유틸리티**

**특징**
- 이름 / 전화번호 / 주민번호 / 계좌번호 등
- 마스킹 규칙을 **함수 단위로 분리**
- Converter는 적용만 담당, 규칙은 Utils에 집중

**핵심 개념**  
> 마스킹 규칙은 한 곳에 모으고, 적용 로직은 분리한다

---

## 🛠 Tech Stack

- Java 17
- SLF4J
- Logback 1.4.x
- Maven

---

### 👥 Team

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
      <a href="https://github.com/yujung23">
        <img src="https://avatars.githubusercontent.com/yujung23" width="120px;" alt="김유정"/>
        <br />
        <sub><b>김유정</b></sub>
      </a>
      <br />
    </td>
    <td align="center">
      <a href="https://github.com/zerobak13">
        <img src="https://avatars.githubusercontent.com/zerobak13" width="120px;" alt="박제영"/>
        <br />
        <sub><b>박제영</b></sub>
      </a>
      <br />
    </td>
    <td align="center">
      <a href="https://github.com/hyeyoon23">
        <img src="https://avatars.githubusercontent.com/hyeyoon23" width="120px;" alt="이혜윤"/>
        <br />
        <sub><b>이혜윤</b></sub>
      </a>
      <br />
    </td>
  </tr>
</table>
