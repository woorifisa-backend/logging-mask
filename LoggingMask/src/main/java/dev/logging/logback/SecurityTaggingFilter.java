package dev.logging.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import java.util.regex.Pattern;

public class SecurityTaggingFilter extends Filter<ILoggingEvent> {

    private static final Pattern PW_KEY_PATTERN = Pattern.compile(
            "(?i)(\\bpw\\b\\s*[=:]|\"pw\"\\s*:|[?&]pw=)"
    );

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!isStarted() || event == null) return FilterReply.NEUTRAL;

        String msg = event.getFormattedMessage();
        if (msg == null || msg.isBlank()) return FilterReply.NEUTRAL;

        return PW_KEY_PATTERN.matcher(msg).find()
                ? FilterReply.DENY
                : FilterReply.NEUTRAL;
    }
}
