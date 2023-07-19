/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-29
 * @desc : 회원 레포지토리 테스트
 */

package backendnovice.projectbookpublisher.member.repository;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

@SpringBootTest
public class MemberEntityRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @DisplayName("Member Create Test")
    void testMemberInsert() {
        MemberEntity memberEntity = MemberEntity.builder()
                .email("username@email.com")
                .password("password")
                .phone("01012345678").build();

        MemberEntity memberEntitySaved = memberRepository.save(memberEntity);

        Assertions.assertThat(memberEntity).isSameAs(memberEntitySaved);
        Assertions.assertThat(memberEntitySaved.getId()).isNotNull();
        Assertions.assertThat(memberEntity.getEmail()).isEqualTo(memberEntitySaved.getEmail());
        Assertions.assertThat(memberEntity.getPassword()).isEqualTo(memberEntitySaved.getPassword());
    }

    @Test
    @DisplayName("Member Read Test")
    void testMemberSelect() {
        Long id = 3L;

        MemberEntity memberEntity = MemberEntity.builder()
                .email("username@email.com")
                .password("password")
                .phone("01012345678").build();

        MemberEntity memberEntityFound = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member ID : " + id));

        Assertions.assertThat(memberEntityFound.getId()).isNotNull();
        Assertions.assertThat(memberEntityFound.getEmail()).isEqualTo(memberEntity.getEmail());
        Assertions.assertThat(memberEntityFound.getPassword()).isEqualTo(memberEntity.getPassword());
        Assertions.assertThat(memberEntityFound.getPhone()).isEqualTo(memberEntity.getPhone());
    }

    @Test
    @Transactional
    @DisplayName("Member Update Test")
    void testMemberUpdate() {
        Long id = 3L;

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member ID : " + id));

        memberEntity.setEmail("updated@email.com");
        memberEntity.setPassword("updated_password");
        memberEntity.setPhone("updated_phone");

        MemberEntity memberEntityUpdated = memberRepository.save(memberEntity);

        Assertions.assertThat(memberEntityUpdated.getId()).isNotNull();
        Assertions.assertThat(memberEntityUpdated.getEmail()).isEqualTo(memberEntity.getEmail());
        Assertions.assertThat(memberEntityUpdated.getPassword()).isEqualTo(memberEntity.getPassword());
        Assertions.assertThat(memberEntityUpdated.getPhone()).isEqualTo(memberEntity.getPhone());
    }

    @Test
    @Transactional
    @DisplayName("Member Delete Test")
    void testMemberDelete() {
        memberRepository.deleteById(1L);

        Optional<MemberEntity> memberDeleted = memberRepository.findById(1L);

        Assertions.assertThat(memberDeleted).isEqualTo(Optional.empty());
    }
}