/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Member 테이블 입출력을 담당하는 레포지토리 인터페이스.
 * @changelog :
 * 23-07-04 - backendnovice@gmail.com - 회원 탈퇴 쿼리 메소드 추가
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 **/

package backendnovice.projectbookpublisher.member.repository;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    /**
     * 이메일과 일치하는 튜플을 검색한다.
     * @param email
     *      회원 이메일
     * @return
     *      MemberEntity
     */
    Optional<MemberEntity> findByEmail(@Param("member_email") String email);

    /**
     * 이메일과 일치하는 튜플의 존재여부를 반환한다.
     * @param email
     *      회원 이메일
     * @return
     *      이메일 존재여부
     */
    boolean existsByEmail(@Param("member_email") String email);

    /**
     * 이메일과 일치하는 튜플들을 삭제한다.
     * @param email
     *      회원 이메일
     * @return
     *      삭제 횟수
     */
    Long deleteByEmail(@Param("member_email") String email);
}
