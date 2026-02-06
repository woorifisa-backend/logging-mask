package dev.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.qos.logback.classic.spi.ILoggingEvent;
import dev.logging.logback.SensitiveDataConverter;

@ExtendWith(MockitoExtension.class)
public class LoggingMaskTest {

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
	}

}
