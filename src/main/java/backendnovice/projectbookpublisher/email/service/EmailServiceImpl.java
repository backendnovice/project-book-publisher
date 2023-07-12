/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-12
 * @desc : Implements member-related feature methods.
 *
 * changelog :
 * 2023-07-12 - backendnovice@gmail.com - Split from MemberServiceImpl.java
 */

package backendnovice.projectbookpublisher.email.service;

import backendnovice.projectbookpublisher.email.domain.EmailCode;
import backendnovice.projectbookpublisher.email.repository.EmailCodeRepository;
import backendnovice.projectbookpublisher.email.vo.CodeType;
import backendnovice.projectbookpublisher.member.domain.Member;
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
        EmailCode code = emailCodeRepository.findByKeyAndType(key, type)
                .orElseThrow(IllegalArgumentException::new);
        Member member = code.getMember();

        if(!code.isExpired()) {
            code.setExpired(true);
            emailCodeRepository.save(code);
        }

        if(!member.isEnabled()) {
            member.setEnabled(true);
            memberRepository.save(member);
            return true;
        }

        return false;
    }

    /**
     * Resend verification email.
     * @param email
     *      To email
     * @throws MessagingException
     *      Message association error
     * @throws UnsupportedEncodingException
     *      Encoding association error
     */
    private void makeVerifyEmail(String email)
            throws MessagingException, UnsupportedEncodingException {
        EmailCode code = makeVerifyCode(email);
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
     * Generate random email code.
     * @return
     *      email code
     */
    private EmailCode makeVerifyCode(String email) {
        String newCode = RandomStringUtils.randomAlphanumeric(64);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);

        EmailCode code = EmailCode.builder()
                .type(CodeType.REGISTER)
                .key(newCode)
                .expired(false)
                .member(member).build();

        return emailCodeRepository.save(code);
    }
}
