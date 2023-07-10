/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : DB에 코드 데이터를 입출력하는 인터페이스.
 *
 * 변경 내역 :
 * 2023-07-04 - backendnovice@gmail.com - 코드 검색 쿼리 수정
 **/

package backendnovice.projectbookpublisher.member.repository;

import backendnovice.projectbookpublisher.member.domain.CodeEntity;
import backendnovice.projectbookpublisher.member.domain.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    /**
     * 파라미터의 값과 타입에 일치하는 엔티티를 반환하는 메소드.
     * @param value
     *      코드 값
     * @param type
     *      코드 타입
     * @return
     *      코드 엔티티
     */
    Optional<CodeEntity> findByValueAndType(@Param("code_value") String value, @Param("code_type") CodeType type);
}
