package dev.logging.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import dev.logging.util.MaskingUtils; // 유틸 연결

public class SensitiveDataConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        String originalMessage = event.getFormattedMessage(); // 원본 텍스트 가져오기
        
        // ",? ?pw=[^,]*" : 'pw=' 뒤에 콤마가 오기 전까지의 모든 글자를 찾아 지웁니다.
        String messageWithoutPw = originalMessage.replaceAll(",? ?pw=[^,]*", "");
        
        return MaskingUtils.mask(messageWithoutPw); // 유틸에 원본 전달, 가공완료
    }
}