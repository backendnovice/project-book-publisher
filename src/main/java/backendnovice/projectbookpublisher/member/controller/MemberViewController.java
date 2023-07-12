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

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * Handle member registration service.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      Redirect URI (login | register)
     */
    @PostMapping("/register")
    public String registerProcess(MemberDTO memberDTO) {
        return (memberService.register(memberDTO))
                ? "redirect:/email/verify/result" : "redirect:/member/register";
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
        memberService.withdraw(email);

        return "redirect:/member/logout";
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

        memberService.changePassword(email, password);

        return "redirect:/member/logout";
    }
}
