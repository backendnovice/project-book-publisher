/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-29
 * @desc : 회원 레포지토리 테스트
 */

package backendnovice.projectbookpublisher.member.repository;

import backendnovice.projectbookpublisher.member.domain.Member;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @DisplayName("Member Create Test")
    void testMemberInsert() {
        Member member = Member.builder()
                .email("username@email.com")
                .password("password")
                .phone("01012345678").build();

        Member memberSaved = memberRepository.save(member);

        Assertions.assertThat(member).isSameAs(memberSaved);
        Assertions.assertThat(memberSaved.getId()).isNotNull();
        Assertions.assertThat(member.getEmail()).isEqualTo(memberSaved.getEmail());
        Assertions.assertThat(member.getPassword()).isEqualTo(memberSaved.getPassword());
    }

    @Test
    @DisplayName("Member Read Test")
    void testMemberSelect() {
        Long id = 3L;

        Member member = Member.builder()
                .email("username@email.com")
                .password("password")
                .phone("01012345678").build();

        Member memberFound = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member ID : " + id));

        Assertions.assertThat(memberFound.getId()).isNotNull();
        Assertions.assertThat(memberFound.getEmail()).isEqualTo(member.getEmail());
        Assertions.assertThat(memberFound.getPassword()).isEqualTo(member.getPassword());
        Assertions.assertThat(memberFound.getPhone()).isEqualTo(member.getPhone());
    }

    @Test
    @Transactional
    @DisplayName("Member Update Test")
    void testMemberUpdate() {
        Long id = 3L;

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member ID : " + id));

        member.setEmail("updated@email.com");
        member.setPassword("updated_password");
        member.setPhone("updated_phone");

        Member memberUpdated = memberRepository.save(member);

        Assertions.assertThat(memberUpdated.getId()).isNotNull();
        Assertions.assertThat(memberUpdated.getEmail()).isEqualTo(member.getEmail());
        Assertions.assertThat(memberUpdated.getPassword()).isEqualTo(member.getPassword());
        Assertions.assertThat(memberUpdated.getPhone()).isEqualTo(member.getPhone());
    }

    @Test
    @Transactional
    @DisplayName("Member Delete Test")
    void testMemberDelete() {
        memberRepository.deleteById(1L);

        Optional<Member> memberDeleted = memberRepository.findById(1L);

        Assertions.assertThat(memberDeleted).isEqualTo(Optional.empty());
    }
}