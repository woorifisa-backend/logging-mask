package dev.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.logging.util.MaskingUtils;

class LoggingMaskTest {

	
	 @Test
	    @DisplayName("입력값이 null이면 null을 반환한다")
	    void returnNull_whenInputIsNull() {

	        // Given
	        String log = null;

	        // When
	        String result = MaskingUtils.maskName(log);

	        // Then
	        assertNull(result);
	    }

	    @Test
	    @DisplayName("2글자 한글 이름이면 첫 글자만 남기고 마스킹한다")
	    void maskTwoCharName() {

	        // Given
	        String log = "name=홍길";

	        // When
	        String result = MaskingUtils.maskName(log);

	        // Then
	        assertEquals("name=홍*", result);
	    }

	    @Test
	    @DisplayName("3글자 한글 이름이면 가운데 글자를 마스킹한다")
	    void maskThreeCharName() {

	        // Given
	        String log = "name=홍길동";

	        // When
	        String result = MaskingUtils.maskName(log);

	        // Then
	        assertEquals("name=홍*동", result);
	    }

	    @Test
	    @DisplayName("4글자 한글 이름이면 가운데 두 글자를 마스킹한다")
	    void maskFourCharName() {

	        // Given
	        String log = "name=홍길동김";

	        // When
	        String result = MaskingUtils.maskName(log);

	        // Then
	        assertEquals("name=홍**김", result);
	    }

	    @Test
	    @DisplayName("name 키가 없으면 문자열은 변경되지 않는다")
	    void doNothing_whenNameNotExists() {

	        // Given
	        String log = "age=20";

	        // When
	        String result = MaskingUtils.maskName(log);

	        // Then
	        assertEquals("age=20", result);
	    }

	    @Test
	    @DisplayName("영문 이름도 길이 기준에 따라 마스킹된다")
	    void maskEnglishNameByLength() {

	        // Given
	        String log = "name=John";

	        // When
	        String result = MaskingUtils.maskName(log);

	        // Then
	        assertEquals("name=J**n", result);
	    }
	

}
