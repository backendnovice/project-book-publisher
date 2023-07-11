/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : Handles member-related requests and responses.
 *
 * changelog :
 * 2023-06-29 - backendnovice@gmail.com - Split from MemberController.java
 * 2023-06-30 - backendnovice@gmail.com - Apply springdoc swagger annotations
 * 2023-06-30 - backendnovice@gmail.com - Modify coding annotations
 */

package backendnovice.projectbookpublisher.member.controller;

import backendnovice.projectbookpublisher.global.dto.ResponseDTO;
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
     * Check email, password used for login.
     * @param memberDTO
     *      MemberDTO with email, password
     * @return
     *      ResponseDTO
     */
    @PostMapping("/login")
    @Operation(summary = "Login API", description = "Check email, password matches from DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A matching tuple exists in DB."),
            @ApiResponse(responseCode = "404", description = "A matching tuple doesn't exists in DB.")
    })
    public ResponseDTO<String> provideLoginAPI(@RequestBody MemberDTO memberDTO) {
        if(memberService.validateLogin(memberDTO)) {
            return ResponseDTO.onSuccess("Correct email and password", null);
        }
        return ResponseDTO.onFailure("Incorrect email and password");
    }

    /**
     * Check email used for registration.
     * @param memberDTO
     *      MemberDTO with email
     * @return
     *      ResponseEntity containing existence
     */
    @PostMapping("/register")
    @Operation(summary = "Registration API", description = "Email duplicate check from DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A matching tuple doesn't exists in DB."),
            @ApiResponse(responseCode = "404", description = "A matching tuple exists in DB.")
    })
    public ResponseDTO<String> provideRegisterAPI(@RequestBody MemberDTO memberDTO) {
        if(memberService.validateRegister(memberDTO)) {
            return ResponseDTO.onSuccess("Email is available", null);
        }
        return ResponseDTO.onFailure("Email is not available.");
    }
}
