/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Spring Boot Starter Mail 을 설정하는 클래스.
 * @changelog :
 * 23-07-10 - backendnovice@gmail.com - JavaMailSender 설정 주입
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 및 수정
 */

package backendnovice.projectbookpublisher.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;

    /**
     * Register JavaMailSender as bean.
     * @return
     *      JavaMailSender
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    /**
     * Provide properties to JavaMailSender
     * @return
     *      Properties
     */
    private Properties getMailProperties() {
        Properties properties = new Properties();

        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", host);
        properties.setProperty("mail.smtp.ssl.enable", "true");

        return properties;
    }
}
