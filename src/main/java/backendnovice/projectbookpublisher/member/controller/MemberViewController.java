/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : 회원 관련 POST, GET 요청을 처리하는 클래스.
 *
 * 변경 내역 :
 * 2023-06-29 - backendnovice@gmail.com - MemberController.java 로부터 분할
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
 */

package backendnovice.projectbookpublisher.member.controller;

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     *      반환할 URI.
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "member/login";
    }

    /**
     * 회원가입 페이지를 매핑하는 메소드.
     * @return
     *      반환할 URI.
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "member/register";
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
        return (memberService.doRegister(memberDTO)) ? "redirect:/member/login" : "redirect:/member/register";
    }

    /**
     * 로그인 성공 후 이동하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      반환할 URI
     */
    @PostMapping("/login")
    public String loginProcess(MemberDTO memberDTO) {
        return "member/loginSuccess";
    }
}
