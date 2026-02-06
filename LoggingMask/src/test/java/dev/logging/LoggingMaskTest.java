package dev.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.FilterReply;
import dev.logging.logback.SecurityTaggingFilter;

class LoggingMaskTest {

	@org.junit.jupiter.api.Test
	void test() {

	}

	@Nested
	class SecurityTaggingFilterTest {

	    private SecurityTaggingFilter filter;

	    @BeforeEach
	    void setUp() {
	        filter = new SecurityTaggingFilter();
	        filter.start();
	    }

	    @Test
	    @DisplayName("1. 로그 이벤트가 null인 경우 → NEUTRAL")
	    void givenNullEvent_whenDecide_thenNeutral() {
	      // Given
	      ILoggingEvent event = null;

	      // When
	      FilterReply result = filter.decide(event);

	      // Then
	      assertEquals(FilterReply.NEUTRAL, result);
	    }

	    @Test
	    @DisplayName("2. 로그 메시지가 null인 경우 → NEUTRAL")
	    void givenNullMessage_whenDecide_thenNeutral() {
	      // Given
	      ILoggingEvent event = mock(ILoggingEvent.class);
	      when(event.getFormattedMessage()).thenReturn(null);

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