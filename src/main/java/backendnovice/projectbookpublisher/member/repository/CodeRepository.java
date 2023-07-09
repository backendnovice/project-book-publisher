/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : DB에 코드 데이터를 입출력하는 인터페이스.
 *
 * 변경 내역 :
 **/

package backendnovice.projectbookpublisher.member.repository;

import backendnovice.projectbookpublisher.member.domain.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    Optional<CodeEntity> findByValueAndType(@Param("code_value") String value, @Param("code_type") String type);
}
