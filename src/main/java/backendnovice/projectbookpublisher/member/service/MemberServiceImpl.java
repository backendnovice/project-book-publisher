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

import backendnovice.projectbookpublisher.email.service.EmailService;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.Member;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder
            , EmailService emailService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public boolean register(MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();

        if (checkPasswordPattern(password)) {
            memberDTO.setPassword(passwordEncoder.encode(password));
            memberRepository.save(dtoToEntity(memberDTO));
            emailService.sendVerifyEmail(email);

            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean withdraw(String email) {
        // If removed columns count greater than 0, return true.
        return memberRepository.deleteByEmail(email) > 0;
    }

    @Override
    public void changePassword(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member Email : " + email));

        member.setPassword(passwordEncoder.encode(password));

        memberRepository.save(member);
    }

    @Override
    public boolean checkLogin(MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.findByEmail(memberDTO.getEmail());

        return member.filter(memberEntity -> passwordEncoder
                        .matches(memberDTO.getPassword(), memberEntity.getPassword())).isPresent();
    }

    @Override
    public boolean checkRegister(MemberDTO memberDTO) {
        return memberRepository.existsByEmail(memberDTO.getEmail());
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
