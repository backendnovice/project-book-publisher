/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : Implements member-related feature methods.
 *
 * changelog :
 * 2023-06-29 - backendnovice@gmail.com - modify doRegister() return type
 * 2023-06-30 - backendnovice@gmail.com - Modify coding annotations
 * 2023-07-04 - backendnovice@gmail.com - Implement member withdraw feature
 * 2023-07-05 - backendnovice@gmail.com - Implement change password feature
 * 2023-07-09 - backendnovice@gmail.com - Implement verify email feature
 * 2023-07-10 - backendnovice@gmail.com - Implement resend email feature
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.email.domain.EmailCode;
import backendnovice.projectbookpublisher.email.vo.CodeType;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.Member;
import backendnovice.projectbookpublisher.email.repository.EmailCodeRepository;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final EmailCodeRepository emailCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public MemberServiceImpl(MemberRepository memberRepository, EmailCodeRepository emailCodeRepository
            , PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.memberRepository = memberRepository;
        this.emailCodeRepository = emailCodeRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean doRegister(MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();

        if (checkPasswordPattern(password)) {
            memberDTO.setPassword(passwordEncoder.encode(password));
            Member member = memberRepository.save(dtoToEntity(memberDTO));

            // Send verification email.
            try {
                sendVerifyEmail(member);
            }catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean doWithdraw(String email) {
        // If removed columns count greater than 0, return true.
        return memberRepository.deleteByEmail(email) > 0;
    }

    @Override
    public void doChangePassword(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member Email : " + email));

        member.setPassword(passwordEncoder.encode(password));

        memberRepository.save(member);
    }

    @Override
    public void doResendEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member Email : " + email));

        try {
            sendVerifyEmail(member);
        }catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validateLogin(MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.findByEmail(memberDTO.getEmail());

        return member.filter(memberEntity -> passwordEncoder
                        .matches(memberDTO.getPassword(), memberEntity.getPassword())).isPresent();
    }

    @Override
    public boolean validateRegister(MemberDTO memberDTO) {
        return memberRepository.existsByEmail(memberDTO.getEmail());
    }

    @Override
    public boolean validateEmailVerification(String key, CodeType type) {
        EmailCode code = emailCodeRepository.findByKeyAndType(key, type)
                .orElseThrow(IllegalArgumentException::new);
        Member member = code.getMember();

        // Set code expired.
        if(code.isExpired()) {
            code.setExpired(true);
            emailCodeRepository.save(code);
        }

        // Set member enabled.
        if(!member.isEnabled()) {
            member.setEnabled(true);
            memberRepository.save(member);
            return true;
        }

        return false;
    }

    /**
     * Resend verification email.
     * @param member
     *      Member
     * @throws MessagingException
     *      Message association error
     * @throws UnsupportedEncodingException
     *      Encoding association error
     */
    private void sendVerifyEmail(Member member)
            throws MessagingException, UnsupportedEncodingException {
        EmailCode code = generateVerifyCode(member);
        String email = member.getEmail();
        String from = "backendnovice@gmail.com";
        String sender = "Book's Pub";
        String title = "Please verify your Registration";
        String content = "<h2>Welcome to Book's Publish Project</h2>"
                + "<p>Please click the link below to complete Registration.</p>"
                + "<a href=\"" + "http://localhost:8080/member/verify?value=" + code.getKey() + "&type="
                + code.getType() + "\" target=\"_blank\">Verify</a>"
                + "<p>Thank you.<br>by" + sender + "</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from, sender);
        helper.setTo(email);
        helper.setSubject(title);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    /**
     * Generate random email code.
     * @return
     *      email code
     */
    private EmailCode generateVerifyCode(Member member) {
        String newCode = RandomStringUtils.randomAlphanumeric(64);

        EmailCode code = EmailCode.builder()
                .type(CodeType.REGISTER)
                .key(newCode)
                .expired(true)
                .member(member).build();

        return emailCodeRepository.save(code);
    }

    /**
     * Check password conditions.
     * @param password
     *      Member password
     * @return
     *      Check result
     */
    private boolean checkPasswordPattern(String password) {
        // At least 1 upper, lower, number. minimum count 8.
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = pattern.matcher(password);

        return matcher.find();
    }
}
