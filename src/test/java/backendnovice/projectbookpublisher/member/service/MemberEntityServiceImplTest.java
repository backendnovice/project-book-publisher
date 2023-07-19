/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-29
 * @desc : 회원 서비스 테스트
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MemberEntityServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @Test
    @DisplayName("Registration Test(Success)")
    void testDoRegisterSuccess() {
        MemberDTO memberDTO = MemberDTO.builder()
                .email("username@email.com")
                .password("password")
                .phone("01012345678").build();

        Mockito.when(memberRepository.save(Mockito.any()))
                .thenReturn(MemberEntity.builder().build());

        boolean result = memberServiceImpl.register(memberDTO);

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Registration Test(Failure)")
    void testDoRegisterFailure() {
        MemberDTO memberDTO = MemberDTO.builder()
                .email("username@email.com")
                .password("password")
                .build();

        boolean result = memberServiceImpl.register(memberDTO);

        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Login Validation Test(Success)")
    void testValidateLoginSuccess() {
        MemberDTO memberDTO = MemberDTO.builder()
                .email("username@email.com")
                .password("password")
                .build();

        MemberEntity memberEntity = MemberEntity.builder()
                .email("username@email.com")
                .password("password")
                .build();

        Mockito.when(memberRepository.findByEmail(Mockito.any()))
                .thenReturn(Optional.of(memberEntity));

        boolean result = memberServiceImpl.checkLogin(memberDTO);

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Login Validation Test(Failure)")
    void testValidateLoginFailure() {
        MemberDTO memberDTO = MemberDTO.builder()
                .email("username@email.com")
                .password("password")
                .build();

        Mockito.when(memberRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        boolean result = memberServiceImpl.checkLogin(memberDTO);

        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Register Validation Test(Success)")
    void testValidateRegisterSuccess() {
        MemberDTO memberDTO = MemberDTO.builder()
                .email("usertest@email.com")
                .build();

        Mockito.when(memberRepository.existsByEmail(Mockito.any())).thenReturn(true);

        boolean result = memberServiceImpl.checkRegister(memberDTO);

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Register Validation Test(Failure)")
    void testValidateRegisterFailure() {
        MemberDTO memberDTO = MemberDTO.builder()
                .email("usertest@email.com")
                .build();

        Mockito.when(memberRepository.existsByEmail(Mockito.any())).thenReturn(false);

        boolean result = memberServiceImpl.checkRegister(memberDTO);

        Assertions.assertFalse(result);
    }
}