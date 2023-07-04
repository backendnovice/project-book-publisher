/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : DB에 회원 데이터를 입출력하는 인터페이스.
 *
 * 변경 내역 :
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
 */

package backendnovice.projectbookpublisher.member.repository;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    /**
     * 파라미터의 이메일과 일치하는 엔티티를 반환하는 메소드.
     * @param email
     *      회원 이메일.
     * @return
     *      회원 엔티티.
     */
    Optional<MemberEntity> findByEmail(@Param("member_email") String email);

    /**
     * 파라미터의 이메일의 존재 여부를 반환하는 메소드.
     * @param email
     *      회원 이메일.
     * @return
     *      이메일 존재 여부.
     */
    boolean existsByEmail(@Param("member_email") String email);

    Long deleteByEmail(@Param("member_email") String email);
}
