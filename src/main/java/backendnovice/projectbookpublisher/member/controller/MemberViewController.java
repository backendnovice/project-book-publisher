/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-04
 * @desc : 회원 관련 POST, GET 요청을 처리하는 클래스.
 *
 * 변경 내역 :
 * 2023-06-29 - backendnovice@gmail.com - MemberController.java 로부터 분할
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
 * 2023-07-04 - backendnovice@gmail.com - 에러페이지, 프로필 페이지 매핑
 * 2023-07-04 - backendnovice@gmail.com - 회원탈퇴 요청 메소드 추가
 */

package backendnovice.projectbookpublisher.member.controller;

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/member")
public class MemberViewController {
    private final MemberService memberService;

    public MemberViewController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 로그인 페이지를 매핑하는 메소드.
     * @return
     *      반환할 URI
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "member/login";
    }

    /**
     * 회원가입 페이지를 매핑하는 메소드.
     * @return
     *      반환할 URI
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "member/register";
    }

    /**
     * 프로필 페이지를 매핑하는 메소드.
     * @return
     *      반환할 URI
     */
    @GetMapping("/profiles")
    public String getProfilesPage() {
        return "member/profiles";
    }

    /**
     * 에러 페이지를 매핑하는 메소드. (임시)
     * @return
     *      반환할 URI
     */
    @GetMapping("/failure")
    public String getFailurePage() {
        return "member/failure";
    }

    /**
     * 회원가입 서비스를 요청하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      반환할 URI
     */
    @PostMapping("/register")
    public String registerProcess(MemberDTO memberDTO) {
        return (memberService.doRegister(memberDTO))
                ? "redirect:/member/login" : "redirect:/member/register";
    }

    /**
     * 회원탈퇴 서비스를 요청하는 메소드.
     * @param principal
     *      회원 정보 객체
     * @return
     *      반환할 URI
     */
    @PostMapping("/withdraw")
    public String withdrawProcess(Principal principal, RedirectAttributes redirectAttributes) {
        String email = principal.getName();

        if (memberService.doWithdraw(email)) {
            redirectAttributes.addFlashAttribute("msg", "회원정보 탈퇴를 성공했습니다.");
            return "redirect:/member/logout";
        } else {
            redirectAttributes.addFlashAttribute("msg", "회원정보 탈퇴를 실패했습니다.");
            return "redirect:/member/profiles";
        }
    }
}
