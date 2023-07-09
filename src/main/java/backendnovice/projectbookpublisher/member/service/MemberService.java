/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : 회원 관련 메소드를 정의하는 인터페이스.
 *
 * 변경 내역 :
 * 2023-06-29 - backendnovice@gmail.com - doRegister() 리턴 타입 수정
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
 * 2023-07-04 - backendnovice@gmail.com - 회원가입 탈퇴 메소드 정의
 * 2023-07-05 - backendnovice@gmail.com - 비밀번호 수정 메소드 정의
 * 2023-07-09 - backendnovice@gmail.com - 이메일 인증 메소드 정의
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;

public interface MemberService {
    /**
     * 회원 가입을 처리하고 처리 여부를 반환하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      처리 여부
     */
    boolean doRegister(MemberDTO memberDTO);

    /**
     * 회원 탈퇴를 처리하고 처리 여부를 반환하는 메소드.
     * @param email
     *      회원 이메일
     * @return
     *      처리 여부
     */
    boolean doWithdraw(String email);

    /**
     * 비밀번호 변경을 처리하고 처리 여부를 반환하는 메소드.
     * @param email
     *      회원 이메일
     * @param password
     *      변경할 비밀번호
     */
    void doChangePassword(String email, String password);
    
    /**
     * 이메일과 비밀번호를 검증하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      검증 성공 여부
     */
    boolean validateLogin(MemberDTO memberDTO);

    /**
     * 이메일 중복 여부를 검증하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      검증 성공 여부
     */
    boolean validateRegister(MemberDTO memberDTO);

    /**
     * 이메일 인증을 검증하는 메소드.
     * @param code
     *      인증 코드
     * @return
     *      검증 성공 여부
     */
    boolean validateEmailVerification(String code, String type);

    /**
     * DTO를 엔티티 객체로 변환하는 메소드.
     * @param memberDTO
     *      회원 데이터 전송 객체
     * @return
     *      회원 엔티티 객체
     */
    default MemberEntity dtoToEntity(MemberDTO memberDTO) {
        return MemberEntity.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .phone(memberDTO.getPhone()).build();
    }

    /**
     * 엔티티를 DTO 객체로 변환하는 메소드.
     * @param memberEntity
     *      회원 엔티티 객체
     * @return
     *      회원 데이터 전송 객체
     */
    default MemberDTO entityToDTO(MemberEntity memberEntity) {
        return MemberDTO.builder()
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .phone(memberEntity.getPhone()).build();
    }
}
