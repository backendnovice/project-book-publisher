package backendnovice.projectbookpublisher.member.repository;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void testInsert() {
        MemberEntity member = MemberEntity.builder()
                .email("username@email.com")
                .password("password")
                .phone("01012345678").build();

        memberRepository.save(member);
    }

    @Test
    void testSelect() {
        Long id = 1L;

        MemberEntity actual = MemberEntity.builder()
                .id(1L)
                .email("username@email.com")
                .password("password")
                .phone("01012345678").build();

        Optional<MemberEntity> expect = memberRepository.findById(id);

        assertEquals(expect.get().getId(), actual.getId());
        assertEquals(expect.get().getEmail(), actual.getEmail());
        assertEquals(expect.get().getPassword(), actual.getPassword());
        assertEquals(expect.get().getPhone(), actual.getPhone());
    }

    @Test
    void testUpdate() {
        MemberEntity member = MemberEntity.builder()
                .id(1L)
                .email("username@email.com")
                .password("new_password")
                .phone("01012345678").build();

        MemberEntity actual = MemberEntity.builder()
                .email("username@email.com")
                .password("new_password")
                .phone("01012345678").build();

        MemberEntity expect = memberRepository.save(member);

        assertEquals(expect.getPassword(), actual.getPassword());
    }

    @Test
    void testDelete() {
        memberRepository.deleteById(1L);

        Optional<MemberEntity> expect = memberRepository.findById(1L);

        assertEquals(expect, Optional.empty());
    }
}