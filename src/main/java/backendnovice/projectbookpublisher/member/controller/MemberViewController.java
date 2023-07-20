/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-20
 * @desc      : 회원과 관련된 POST, GET 요청을 처리하는 컨트롤러 클래스.
 * @changelog :
 * 23-06-29 - backendnovice@gmail.com - MemberController 에서 분리
 * 23-07-04 - backendnovice@gmail.com - 프로필, 로그인 에러 요청 핸들링 메소드 추가
 * 23-07-04 - backendnovice@gmail.com - 회원가입 요청 핸들링 메소드 추가
 * 23-07-05 - backendnovice@gmail.com - 비밀번호 변경 핸들링 메소드 추가
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 * 23-07-20 - backendnovice@gmail.com - 회원탈퇴 핸들링 메소드 수정
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
     * "/member/login"에 대한 GET 요청을 처리하고, 로그인 뷰를 반환한다.
     * @return
     *      로그인 뷰
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "member/login";
    }

    /**
     * "/member/register"에 대한 GET 요청을 처리하고, 회원가입 뷰를 반환한다.
     * @return
     *      회원가입 뷰
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "member/register";
    }

    /**
     * "/member/profiles"에 대한 GET 요청을 처리하고, 프로필 뷰를 반환한다.
     * @return
     *      프로필 뷰
     */
    @GetMapping("/profiles")
    public String getProfilesPage() {
        return "member/profiles";
    }

    /**
     * "/member/failure"에 대한 GET 요청을 처리하고, 로그인 에러 뷰를 반환한다.
     * @return
     *      로그인 에러 뷰
     */
    @GetMapping("/failure")
    public String getFailurePage() {
        return "member/failure";
    }

    /**
     * "/member/support/change-password"에 대한 GET 요청을 처리하고, 비밀번호 변경 뷰를 반환한다.
     * @return
     *      비밀번호 변경 뷰
     */
    @GetMapping("/support/change-password")
    public String getChangePasswordPage() {
        return "member/support/change-password";
    }

    /**
     * "/member/register"에 대한 POST 요청을 처리하고, 회원 등록 서비스를 호출한다.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      결과 뷰 (로그인 | 회원가입)
     */
    @PostMapping("/register")
    public String registerProcess(MemberDTO memberDTO) {
        return (memberService.register(memberDTO))
                ? "redirect:/email/verify/result" : "redirect:/member/register";
    }

    /**
     * "/member/withdraw"에 대한 POST 요청을 처리하고, 회원 탈퇴 서비스를 호출한다.
     * @param principal
     *      로그인 회원 객체
     * @return
     *      로그아웃 뷰
     */
    @PostMapping("/withdraw")
    public String withdrawProcess(Principal principal) {
        MemberDTO memberDTO = MemberDTO.builder().email(principal.getName()).build();

        memberService.withdraw(memberDTO);

        return "redirect:/member/logout";
    }

    /**
     * "/member/support/change-password"에 대한 POST 요청을 처리하고, 비밀번호 변경 서비스를 호출한다.
     * @param principal
     *      로그인 회원 객체
     * @param memberDTO
     *      MemberDTO
     * @return
     *      로그아웃 뷰
     */
    @PostMapping("/support/change-password")
    public String changePasswordProcess(Principal principal, MemberDTO memberDTO) {
        memberDTO.setEmail(principal.getName());

        memberService.changePassword(memberDTO);

        return "redirect:/member/logout";
    }
}
