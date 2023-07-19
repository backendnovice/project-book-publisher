/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 회원과 관련된 서비스 메소드를 정의하는 클래스.
 * @changelog :
 * 23-06-29 - backendnovice@gmail.com - 회원가입 반환 타입 변경 (String -> boolean)
 * 23-07-04 - backendnovice@gmail.com - 회원탈퇴 메소드 정의
 * 23-07-05 - backendnovice@gmail.com - 비밀번호 변경 메소드 정의
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;

public interface MemberService {
    /**
     * 회원가입을 처리한다.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      회원가입 성공여부
     */
    boolean register(MemberDTO memberDTO);

    /**
     * 이메일과 일치하는 회원탈퇴를 처리한다.
     * @param email
     *      회원 이메일
     * @return
     *      회원탈퇴 성공여부
     */
    boolean withdraw(String email);

    /**
     * 이메일과 일치하는 회원의 비밀번호 변경을 처리한다.
     * @param memberDTO
     *      MemberDTO
     */
    void changePassword(MemberDTO memberDTO);

    /**
     * 로그인에 필요한 이메일과 비밀번호를 확인한다.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      확인결과
     */
    boolean checkLogin(MemberDTO memberDTO);

    /**
     * 회원가입에 필요한 이메일의 중복여부를 확인한다.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      중복여부
     */
    boolean checkRegister(MemberDTO memberDTO);

    /**
     * 이메일과 일치하는 회원 엔티티를 반환한다.
     * @param email
     *      회원 이메일
     * @return
     *      MemberEntity
     */
    MemberEntity getMemberByEmail(String email);
}
