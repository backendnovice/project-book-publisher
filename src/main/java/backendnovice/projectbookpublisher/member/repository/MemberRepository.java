/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-04
 * @desc : Provides queries to perform I/O for 'member' table.
 *
 * changelog :
 * 2023-06-30 - backendnovice@gmail.com - Modify coding annotations
 * 2023-07-04 - backendnovice@gmail.com - Add withdraw query
 **/

package backendnovice.projectbookpublisher.member.repository;

import backendnovice.projectbookpublisher.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Return columns matching with email parameter.
     * @param email
     *      Member email
     * @return
     *      Member
     */
    Optional<Member> findByEmail(@Param("member_email") String email);

    /**
     * Return existence matching with email parameter.
     * @param email
     *      Member email
     * @return
     *      Email existence
     */
    boolean existsByEmail(@Param("member_email") String email);

    /**
     * Delete columns matching with email parameter.
     * @param email
     *      Member email
     * @return
     *      Deletions counts
     */
    Long deleteByEmail(@Param("member_email") String email);
}
