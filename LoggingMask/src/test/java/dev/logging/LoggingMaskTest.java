package dev.logging;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import dev.logging.util.MaskingUtils;

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

}
