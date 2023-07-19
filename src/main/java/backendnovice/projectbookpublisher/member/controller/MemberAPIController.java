/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 회원과 관련된 REST 요청을 처리하는 컨트롤러 클래스.
 * @changelog :
 * 23-06-29 - backendnovice@gmail.com - MemberController 에서 분리
 * 23-06-30 - backendnovice@gmail.com - Springdoc Swagger 주석 적용
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.member.controller;

import backendnovice.projectbookpublisher.common.dto.ResponseDTO;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@Tag(name = "Member API", description = "Provides member-related APIs.")
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 로그인에 필요한 이메일과 비밀번호를 검사한다.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      ResponseDTO
     */
    @PostMapping("/login")
    @Operation(summary = "Member Login API", description = "이메일과 비밀번호가 일치하는 튜플이 있는지 검사한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DB에 일치하는 회원이 존재한다."),
            @ApiResponse(responseCode = "404", description = "DB에 일치하지 않는 회원이 존재한다.")
    })
    public ResponseDTO<?> provideLoginAPI(@RequestBody MemberDTO memberDTO) {
        if(memberService.checkLogin(memberDTO)) {
            return ResponseDTO.onSuccess("올바른 이메일과 비밀번호입니다.", null);
        }
        return ResponseDTO.onFailure("이메일, 비밀번호가 일치하지 않습니다.");
    }

    /**
     * 회원가입에 필요한 이메일을 검사한다.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      ResponseDTO
     */
    @PostMapping("/register")
    @Operation(summary = "Member Registration API", description = "이메일과 일치하는 튜플이 있는지 검사한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DB에 일치하는 이메일이 존재하지 않다."),
            @ApiResponse(responseCode = "404", description = "DB에 일치하는 이메일이 존재한다.")
    })
    public ResponseDTO<?> provideRegisterAPI(@RequestBody MemberDTO memberDTO) {
        if(memberService.checkRegister(memberDTO)) {
            return ResponseDTO.onSuccess("이메일을 사용할 수 있습니다.", null);
        }
        return ResponseDTO.onFailure("이메일을 사용할 수 없습니다.");
    }
}
