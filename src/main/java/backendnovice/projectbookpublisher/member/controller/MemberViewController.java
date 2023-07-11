/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : Maps member-related pages and processes requests.
 *
 * changelog :
 * 2023-06-29 - backendnovice@gmail.com - Split from MemberController.java
 * 2023-06-30 - backendnovice@gmail.com - Modify coding annotations
 * 2023-07-04 - backendnovice@gmail.com - Add error, profiles mapping method
 * 2023-07-04 - backendnovice@gmail.com - Add member registration handle method
 * 2023-07-05 - backendnovice@gmail.com - Add change password handle method
 * 2023-07-09 - backendnovice@gmail.com - Add email verify handle method
 * 2023-07-10 - backendnovice@gmail.com - Add email resend handle method
 */

package backendnovice.projectbookpublisher.member.controller;

import backendnovice.projectbookpublisher.email.vo.CodeType;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/member")
public class MemberViewController {
    private final MemberService memberService;

    public MemberViewController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Mapping login page.
     * @return
     *      Login URI
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "member/login";
    }

    /**
     * Mapping registration page.
     * @return
     *      Registration URI
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "member/register";
    }

    /**
     * Mapping profiles page.
     * @return
     *      Profiles URI
     */
    @GetMapping("/profiles")
    public String getProfilesPage() {
        return "member/profiles";
    }

    /**
     * Mapping login-error page.
     * @return
     *      Login-error URI
     */
    @GetMapping("/failure")
    public String getFailurePage() {
        return "member/failure";
    }

    /**
     * Mapping change-password page.
     * @return
     *      Change-password URI
     */
    @GetMapping("/support/change-password")
    public String getChangePasswordPage() {
        return "member/support/change-password";
    }

    /**
     * Mapping email-verify page.
     * @return
     *      Email-verify URI
     */
    @GetMapping("/verify")
    public String getVerifyPage(@RequestParam String value, @RequestParam String type) {
        return memberService.validateEmailVerification(value, CodeType.valueOf(type))
                ? "member/verify/success" : "member/verify/failure";
    }

    /**
     * Mapping email-resend page.
     * @return
     *      Email-resend URI
     */
    @GetMapping("/verify/resend")
    public String getResendPage() {
        return "member/verify/resend";
    }

    /**
     * Handle member registration service.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      Redirect URI (login | register)
     */
    @PostMapping("/register")
    public String registerProcess(MemberDTO memberDTO) {
        return (memberService.doRegister(memberDTO))
                ? "redirect:/member/login" : "redirect:/member/register";
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
        memberService.doResendEmail(memberDTO.getEmail());

        return "member/login";
    }

    /**
     * Handle member withdraw service.
     * @param principal
     *      Logged in member
     * @return
     *      Redirect URI (logout | profiles)
     */
    @PostMapping("/withdraw")
    public String withdrawProcess(Principal principal) {
        String email = principal.getName();

        if (memberService.doWithdraw(email)) {
            return "redirect:/member/logout";
        } else {
            return "redirect:/member/profiles";
        }
    }

    /**
     * Handle change password service.
     * @param principal
     *      Logged in member
     * @param memberDTO
     *      MemberDTO
     * @return
     *      Redirect logout URI
     */
    @PostMapping("/support/change-password")
    public String changePasswordProcess(Principal principal, MemberDTO memberDTO) {
        String email = principal.getName();
        String password = memberDTO.getPassword();

        memberService.doChangePassword(email, password);

        return "redirect:/member/logout";
    }
}
