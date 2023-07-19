/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : EmailCode 테이블 입출력을 담당하는 레포지토리 인터페이스.
 * @changelog :
 * 23-07-10 - backendnovice@gmail.com - findByKeyAndType() 쿼리 메소드 수정
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 및 수정
 **/

package backendnovice.projectbookpublisher.email.repository;

import backendnovice.projectbookpublisher.email.domain.EmailCodeEntity;
import backendnovice.projectbookpublisher.email.vo.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailCodeRepository extends JpaRepository<EmailCodeEntity, Long> {
    /**
     * 인증 키, 타입과 일치하는 튜플을 검색한다.
     * @param key
     *      이메일 인증 키
     * @param type
     *      이메일 인증 타입
     * @return
     *      검색 결과
     */
    Optional<EmailCodeEntity> findByKeyAndType(@Param("code_key") String key, @Param("code_type") CodeType type);
}
