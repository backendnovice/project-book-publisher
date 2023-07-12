/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : Defines member-related feature methods.
 *
 * changelog :
 * 2023-06-29 - backendnovice@gmail.com - modify doRegister() return type
 * 2023-06-30 - backendnovice@gmail.com - Modify coding annotations
 * 2023-07-04 - backendnovice@gmail.com - Define member withdraw feature
 * 2023-07-05 - backendnovice@gmail.com - Define change password feature
 * 2023-07-09 - backendnovice@gmail.com - Define verify email feature
 * 2023-07-10 - backendnovice@gmail.com - Define resend email feature
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.email.vo.CodeType;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.Member;

public interface MemberService {
    /**
     * Process member registration with DTO.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      Process result
     */
    boolean register(MemberDTO memberDTO);

    /**
     * Process member registration with email.
     * @param email
     *      Member email
     * @return
     *      Process result
     */
    boolean withdraw(String email);

    /**
     * Process change password with email, password.
     * @param email
     *      Member email
     * @param password
     *      New password
     */
    void changePassword(String email, String password);

    /**
     * Check email, password for login.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      Validate result
     */
    boolean checkLogin(MemberDTO memberDTO);

    /**
     * Check email duplicate for registration.
     * @param memberDTO
     *      Member email
     * @return
     *      Validate result
     */
    boolean checkRegister(MemberDTO memberDTO);

    /**
     * Convert MemberDTO to Member.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      Member
     */
    default Member dtoToEntity(MemberDTO memberDTO) {
        return Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .phone(memberDTO.getPhone()).build();
    }

    /**
     * Convert Member to MemberDTO.
     * @param member
     *      Member
     * @return
     *      MemberDTO
     */
    default MemberDTO entityToDTO(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .phone(member.getPhone()).build();
    }
}
