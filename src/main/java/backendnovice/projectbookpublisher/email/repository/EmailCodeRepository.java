/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : Provides queries to perform I/O for 'email_code' table.
 *
 * changelog :
 * 2023-07-04 - backendnovice@gmail.com - Modify findByKeyAndType() query
 **/

package backendnovice.projectbookpublisher.email.repository;

import backendnovice.projectbookpublisher.email.domain.EmailCode;
import backendnovice.projectbookpublisher.email.vo.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {
    /**
     * Return columns matching with key and type parameters.
     * @param key
     *      Code key
     * @param type
     *      Code type
     * @return
     *      EmailCode
     */
    Optional<EmailCode> findByKeyAndType(@Param("code_key") String key, @Param("code_type") CodeType type);
}
