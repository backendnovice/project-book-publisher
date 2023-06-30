/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : 회원 관련 데이터 요청 및 응답을 처리하는 클래스.
 *
 * 변경 내역 :
 * 2023-06-29 - backendnovice@gmail.com - MemberController.java 로부터 분할
 * 2023-06-30 - backendnovice@gmail.com - Springdoc Swagger 적용
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
 */

package backendnovice.projectbookpublisher.member.controller;

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vi/member")
@Tag(name = "회원 API", description = "회원 관련 데이터를 제공하는 API.")
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
    @Operation(summary = "로그인 지원 메소드", description = "이메일과 비밀번호가 DB와 일치하는지 검사하고 결과를 반환한다.")
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
    @Operation(summary = "회원가입 지원 메소드", description = "이메일 중복여부를 검사하고 결과를 반환한다.")
    public ResponseEntity<Map<String, Boolean>> provideRegisterAPI(@RequestBody MemberDTO memberDTO) {
        Map<String, Boolean> response = new HashMap<>();

        boolean isExists = memberService.validateRegister(memberDTO);

        response.put("isExists", isExists);

        return ResponseEntity.ok(response);
    }
}
