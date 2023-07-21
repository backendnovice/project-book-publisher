/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-21
 * @desc      : 메일과 관련된 POST, GET 요청을 처리하는 컨트롤러 클래스.
 * @changelog :
 * 23-07-12 - backendnovice@gmail.com - MemberViewController 에서 분리
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 및 수정
 * 23-07-21 - backendnovice@gmail.com - 호출 서비스 메소드 변경
 */

package backendnovice.projectbookpublisher.email.controller;

import backendnovice.projectbookpublisher.email.dto.EmailCodeDTO;
import backendnovice.projectbookpublisher.email.service.EmailService;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/email")
public class EmailViewController {
    private final EmailService emailService;

    public EmailViewController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * "/email/verify"에 대한 GET 요청을 받아 인증 서비스를 호출하고, 메일 인증 결과 뷰를 반환한다.
     * @return
     *      메일 인증 결과 뷰
     */
    @GetMapping("/verify")
    public String getVerifyPage(@RequestParam EmailCodeDTO emailCodeDTO) {
        return emailService.checkVerifyEmail(emailCodeDTO)
                ? "email/verify/success" : "email/verify/failure";
    }

    /**
     * "/email/verify/resend"에 대한 GET 요청을 처리하고, 메일 재전송 뷰를 반환한다.
     * @return
     *      메일 재전송 뷰
     */
    @GetMapping("/verify/resend")
    public String getResendPage() {
        return "email/verify/resend";
    }

    /**
     * "/email/verify/resend"에 대한 POST 요청을 받아 재전송 서비스를 호출하고, 메일 전송 결과 뷰를 반환한다.
     * @param memberDTO
     *      MemberDTO 객체
     * @return
     *      메일 재전송 결과 뷰
     */
    @PostMapping("/verify/resend")
    public String resendProcess(MemberDTO memberDTO) {
        emailService.sendVerifyEmail(memberDTO.getEmail());

        return "email/verify/result";
    }
}
