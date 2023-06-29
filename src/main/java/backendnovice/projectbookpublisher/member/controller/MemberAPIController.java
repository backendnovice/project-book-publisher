/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-29
 * @desc : 회원 관련 데이터 요청 및 응답을 처리하는 컨트롤러 클래스.
 */

package backendnovice.projectbookpublisher.member.controller;

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vi/member")
public class MemberAPIController {
    private final MemberService memberService;

    public MemberAPIController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 로그인에 필요한 데이터를 요청 및 전달하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      응답 데이터
     */
    @PostMapping("/login")
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
    @PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>> provideRegisterAPI(@RequestBody MemberDTO memberDTO) {
        Map<String, Boolean> response = new HashMap<>();

        boolean isExists = memberService.validateRegister(memberDTO);

        response.put("isExists", isExists);

        return ResponseEntity.ok(response);
    }
}
