package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.domain.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    MemberDTO memberDTO = MemberDTO.builder()
            .id(3L)
            .email("username@email.com")
            .password("password")
            .phone("01012345678").build();

    MemberEntity memberEntity = MemberEntity.builder()
            .id(3L)
            .email("username@email.com")
            .password("password")
            .phone("01012345678").build();


    @Test
    @Transactional
    void doRegisterTest() {
        MemberDTO testDTO = MemberDTO.builder()
                .email("usertest@email.com")
                .password("password")
                .phone("01012345678").build();

        String caseSuccess = memberService.doRegister(testDTO);
        String caseFailure = memberService.doRegister(memberDTO);

        assertEquals(caseSuccess, "redirect:/member/login");
        assertEquals(caseFailure, "redirect:/member/register");
    }

    @Test
    void validateLoginTest() {
        MemberDTO testDTO = MemberDTO.builder()
                .email("usertest@email.com")
                .password("password").build();

        boolean caseSuccess = memberService.validateLogin(memberDTO);
        boolean caseFailure = memberService.validateLogin(testDTO);

        assertTrue(caseSuccess);
        assertFalse(caseFailure);
    }

    @Test
    void validateRegisterTest() {
        MemberDTO testDTO = MemberDTO.builder().email("usertest@email.com").build();

        boolean caseSuccess = memberService.validateRegister(testDTO);
        boolean caseFailure = memberService.validateRegister(memberDTO);

        assertTrue(caseSuccess);
        assertFalse(caseFailure);
    }

    @Test
    void dtoToEntityTest() {
        MemberEntity testEntity = memberService.dtoToEntity(memberDTO);

        assertEquals(testEntity, memberEntity);
    }

    @Test
    void entityToDTOTest() {
        MemberDTO testDTO = memberService.entityToDTO(memberEntity);

        assertEquals(testDTO, memberEntity);
    }
}