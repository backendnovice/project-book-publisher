/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-12
 * @desc : Maps email-related pages and processes requests.
 *
 * changelog :
 * 2023-07-12 - backendnovice@gmail.com - Split from MemberViewController.java
 */

package backendnovice.projectbookpublisher.email.controller;

import backendnovice.projectbookpublisher.email.service.EmailService;
import backendnovice.projectbookpublisher.email.vo.CodeType;
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
     * Mapping email-verify page.
     * @return
     *      Email-verify URI
     */
    @GetMapping("/verify")
    public String getVerifyPage(@RequestParam String value, @RequestParam String type) {
        return emailService.checkVerifyEmail(value, CodeType.valueOf(type))
                ? "email/verify/success" : "email/verify/failure";
    }

    /**
     * Mapping email-resend page.
     * @return
     *      Email-resend URI
     */
    @GetMapping("/verify/resend")
    public String getResendPage() {
        return "email/verify/resend";
    }

    /**
     * Handle email resend service.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      Login URI
     */
    @PostMapping("/verify/resend")
    public String resendProcess(MemberDTO memberDTO) {
        emailService.sendVerifyEmail(memberDTO.getEmail());

        return "email/verify/result";
    }
}
