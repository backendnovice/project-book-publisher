/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-28
 * @desc : 회원 관련 POST, GET 요청을 처리하는 클래스.
 */

package backendnovice.projectbookpublisher.member.controller;

import backendnovice.projectbookpublisher.member.domain.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
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
        return memberService.doRegister(memberDTO);
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

    /**
     * 로그인에 필요한 데이터를 요청 및 전달하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      응답 데이터
     */

    @PostMapping("/api/v1/login")
    public ResponseEntity<Map<String, Boolean>> provideLoginAPI(@RequestBody MemberDTO memberDTO) {
        Map<String, Boolean> response = new HashMap<>();

        boolean isValid = memberService.validateLogin(memberDTO);

        response.put("isValid", isValid);

        return ResponseEntity.ok(response);
    }

    /**
     * 회원가입에 필요한 데이터를 요청 및 전달하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      응답 데이터
     */
    @PostMapping("/api/v1/register")
    public ResponseEntity<Map<String, Boolean>> provideRegisterAPI(@RequestBody MemberDTO memberDTO) {
        Map<String, Boolean> response = new HashMap<>();

        boolean isExists = memberService.validateRegister(memberDTO);

        response.put("isExists", isExists);

        return ResponseEntity.ok(response);
    }
}
