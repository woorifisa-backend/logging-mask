package dev.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.FilterReply;
import dev.logging.logback.SensitiveDataConverter;
import dev.logging.logback.SecurityTaggingFilter;

import org.junit.jupiter.api.function.Executable;

import dev.logging.util.MaskingUtils;

@ExtendWith(MockitoExtension.class)

class LoggingMaskTest {

	@Nested
	class Util_RRN {

		@Test
	    @DisplayName("주민등록번호가 null일 경우, NullPointerException이 발생한다.")
	    void testMaskResidentIdNull() {
	        
			// Given
			String rrn = null;
			
			// When		
			// Then
			assertThrows(NullPointerException.class, () -> 
		        MaskingUtils.maskResidentId(rrn));
	    }

		@Test
		@DisplayName("주민등록번호가 빈 문자열일 경우, IllegalArgumentException이 발생한다.")
		void testMaskResidentIdBlank() {
			// Given
			String rrn = "  "; // 공백 문자열

			// When & Then
			assertThrows(IllegalArgumentException.class, () -> MaskingUtils.maskResidentId(rrn));
		}
		
		@Test
		@DisplayName("주민등록번호가 정상 입력일 경우, 뒷 자리가 마스킹되어 반환된다.")
		void testMaskingRRN() {
			// Given
			String rrn = "021229-0101010"; // 정상 입력
			String expected = "021229-*******";

			// When 
			String actual = MaskingUtils.maskResidentId(rrn);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	@Nested
	class Util_Account {
		@Test
	    @DisplayName("계좌번호가 null일 경우, NullPointerException이 발생한다.")
	    void testMaskAccountNull() {
	        
			// Given
			String account = null;
			
			// When		
			// Then
			assertThrows(NullPointerException.class, () -> 
		        MaskingUtils.maskAccount(account));
	    }

		@Test
		@DisplayName("계좌번호가 빈 문자열일 경우, IllegalArgumentException이 발생한다.")
		void testMaskAccountBlank() {
			// Given
			String account = "  "; // 공백 문자열

			// When & Then
			assertThrows(IllegalArgumentException.class, () -> MaskingUtils.maskAccount(account));
		}
		
		@Test
		@DisplayName("계좌번호가 정상 입력일 경우, 뒷 자리를 제외하고 마스킹되어 반환된다.")
		void testMaskingAccouont() {
			// Given YYY-ZZZZZZZ-C-XXX
			String account = "123-1234567-8-987"; // 정상 입력
			String expected = "***-*******-8-987";

			// When 
			String actual = MaskingUtils.maskAccount(account);
			
			// Then
			assertEquals(expected, actual);
		}
    
	}
  
  private final SensitiveDataConverter converter = new SensitiveDataConverter();

    @Mock
    private ILoggingEvent event;
  
  @Nested
	class ConverterTest {
		
		@Test
		@DisplayName("1. 비밀번호 삭제 확인")
		void test1() {
			// Given: pw가 포함된 메시지
	        given(event.getFormattedMessage())
	        	.willReturn("user=admin, pw=secret123, access=true");

	        // When
	        String result = converter.convert(event);
	        String expected = "user=admin, access=true";

	        // Then: "pw=secret123"과 앞의 콤마, 공백이 사라졌는지 확인
	        assertEquals(expected, result);
		}
		
		@Test
	    @DisplayName("2. 메시지가 null일 때 에러 없이 공백 반환 확인")
	    void test2() {
	        // Given
	        given(event.getFormattedMessage())
	        	.willReturn(null);

	        // When
	        String result = converter.convert(event);
	        String expected = "";

	        // Then
	        assertEquals(expected, result);
	    }
		
		@Test
	    @DisplayName("3. 유틸 함수 실행: 각 정보가 마스킹 처리되는지 확인")
	    void test3() {
	        // Given: 모든 민감 정보가 포함된 메시지
	        String rawLog = "name=김우리, rrn=123456-7890123, accountNumber=012-3456789-4-924";
	        given(event.getFormattedMessage()).willReturn(rawLog);

	        // When
	        String result = converter.convert(event);

	        // Then: 원본 텍스트가 포함되어 있지 않아야 함 (마스킹 유틸이 작동했다는 증거)
	        org.junit.jupiter.api.Assertions.assertAll(
	                () -> assertFalse(result.contains("김우리")),
	                () -> assertFalse(result.contains("7890123")),
	                () -> assertFalse(result.contains("012-3456789"))
	            );
	        
	        
	    }
		
		 @Nested
		    @DisplayName("maskName - 이름 마스킹")
		    class MaskNameTest {

		        @Test
		        @DisplayName("입력값이 null이면 null을 반환한다")
		        void returnNull_whenInputIsNull() {
		            String log = null;
		            String result = MaskingUtils.maskName(log);
		            assertNull(result);
		        }

		        @Test
		        @DisplayName("2글자 한글 이름이면 첫 글자만 남기고 마스킹한다")
		        void maskTwoCharName() {
		            String log = "name=홍길";
		            String result = MaskingUtils.maskName(log);
		            assertEquals("name=홍*", result);
		        }

		        @Test
		        @DisplayName("3글자 한글 이름이면 가운데 글자를 마스킹한다")
		        void maskThreeCharName() {
		            String log = "name=홍길동";
		            String result = MaskingUtils.maskName(log);
		            assertEquals("name=홍*동", result);
		        }

		        @Test
		        @DisplayName("4글자 한글 이름이면 가운데 두 글자를 마스킹한다")
		        void maskFourCharName() {
		            String log = "name=홍길동김";
		            String result = MaskingUtils.maskName(log);
		            assertEquals("name=홍**김", result);
		        }

		        @Test
		        @DisplayName("name 키가 없으면 문자열은 변경되지 않는다")
		        void doNothing_whenNameNotExists() {
		            String log = "age=20";
		            String result = MaskingUtils.maskName(log);
		            assertEquals("age=20", result);
		        }

		        @Test
		        @DisplayName("영문 이름도 길이 기준에 따라 마스킹된다")
		        void maskEnglishNameByLength() {
		            String log = "name=John";
		            String result = MaskingUtils.maskName(log);
		            assertEquals("name=J**n", result);
		        }
		    }
  }

	      // When
	      FilterReply result = filter.decide(event);

	      // Then
	      assertEquals(FilterReply.NEUTRAL, result);
	    }

	    @Test
	    @DisplayName("3. 로그 메시지가 빈 문자열 또는 공백인 경우 → NEUTRAL")
	    void givenBlankMessage_whenDecide_thenNeutral() {
	      // Given
	      ILoggingEvent emptyEvent = mock(ILoggingEvent.class);
	      when(emptyEvent.getFormattedMessage()).thenReturn("");

	      ILoggingEvent blankEvent = mock(ILoggingEvent.class);
	      when(blankEvent.getFormattedMessage()).thenReturn("   ");

	      // When
	      FilterReply emptyResult = filter.decide(emptyEvent);
	      FilterReply blankResult = filter.decide(blankEvent);

	      // Then
	      assertEquals(FilterReply.NEUTRAL, emptyResult);
	      assertEquals(FilterReply.NEUTRAL, blankResult);
	    }

	    @Test
	    @DisplayName("4. pw 키가 포함된 로그 → DENY")
	    void givenPwKeyword_whenDecide_thenDeny() {
	      // Given
	      ILoggingEvent event = mock(ILoggingEvent.class);
	      when(event.getFormattedMessage()).thenReturn("login failed: pw=abcd1234");

	      // When
	      FilterReply result = filter.decide(event);

	      // Then
	      assertEquals(FilterReply.DENY, result);
	    }
	}
}
