package dev.logging.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import dev.logging.util.MaskingUtils;

public class SensitiveDataConverter extends ClassicConverter {
	@Override
	public String convert(ILoggingEvent event) {
		String message = event.getFormattedMessage();
		if (message == null) return "";
		// 비밀번호 삭제
		String result = message.replaceAll(",? ?pw=[^,]*", "");
		
		result = MaskingUtils.maskName(result);
		result = MaskingUtils.maskResidentId(result);
		result = MaskingUtils.maskAccount(result);

		return result;
}
}