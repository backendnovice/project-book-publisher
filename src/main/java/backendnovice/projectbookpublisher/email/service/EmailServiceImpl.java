/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 이메일과 관련된 서비스 메소드를 구현하는 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - MemberServiceImpl 에서 분리
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.email.service;

import backendnovice.projectbookpublisher.email.domain.EmailCodeEntity;
import backendnovice.projectbookpublisher.email.repository.EmailCodeRepository;
import backendnovice.projectbookpublisher.email.vo.CodeType;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {
    private final MemberRepository memberRepository;
    private final EmailCodeRepository emailCodeRepository;
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(EmailCodeRepository emailCodeRepository, MemberRepository memberRepository
            , JavaMailSender javaMailSender) {
        this.emailCodeRepository = emailCodeRepository;
        this.memberRepository = memberRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendVerifyEmail(String email) {
        try {
            makeVerifyEmail(email);
        }catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkVerifyEmail(String key, CodeType type) {
        EmailCodeEntity code = emailCodeRepository.findByKeyAndType(key, type)
                .orElseThrow(IllegalArgumentException::new);
        MemberEntity memberEntity = code.getMemberEntity();

        if(!code.isExpired()) {
            code.setExpired(true);
            emailCodeRepository.save(code);
        }

        if(!memberEntity.isEnabled()) {
            memberEntity.setEnabled(true);
            memberRepository.save(memberEntity);
            return true;
        }

        return false;
    }

    /**
     * 인증 메일 재전송을 수행한다.
     * @param email
     *      수신 이메일
     * @throws MessagingException
     *      메시지 연관 에러
     * @throws UnsupportedEncodingException
     *      인코딩 연관 에러
     */
    private void makeVerifyEmail(String email)
            throws MessagingException, UnsupportedEncodingException {
        EmailCodeEntity code = makeVerifyCode(email);
        String to = email;
        String from = "backendnovice@gmail.com";
        String sender = "Book's Pub";
        String title = "Please verify your Registration";
        String content = "<h2>Welcome to Book's Publish Project</h2>"
                + "<p>Please click the link below to complete Registration.</p>"
                + "<a href=\"" + "http://localhost:8080/email/verify?value=" + code.getKey() + "&type="
                + code.getType() + "\" target=\"_blank\">Verify</a>"
                + "<p>Thank you.<br>by" + sender + "</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from, sender);
        helper.setTo(to);
        helper.setSubject(title);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    /**
     * 무작위 인증 키를 생성한다.
     * @return
     *      이메일 키
     */
    private EmailCodeEntity makeVerifyCode(String email) {
        String newCode = RandomStringUtils.randomAlphanumeric(64);
        MemberEntity memberEntity = memberRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);

        EmailCodeEntity code = EmailCodeEntity.builder()
                .type(CodeType.REGISTER)
                .key(newCode)
                .expired(false)
                .memberEntity(memberEntity).build();

        return emailCodeRepository.save(code);
    }
}
