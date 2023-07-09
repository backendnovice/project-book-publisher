/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : 메일을 설정하는 클래스.
 *
 * 변경 내역 :
 */

package backendnovice.projectbookpublisher.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
