/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-21
 * @desc      : 이메일과 관련된 서비스 메소드를 정의하는 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - MemberService 에서 분리
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 * 23-07-21 - backendnovice@gmail.com - 인증 처리 메소드 파라미터 변경
 */

package backendnovice.projectbookpublisher.email.service;

import backendnovice.projectbookpublisher.email.dto.EmailCodeDTO;

public interface EmailService {
    /**
     * 인증 메일 재전송을 처리한다.
     * @param email
     *      수신 이메일
     */
    public void sendVerifyEmail(String email);

    /**
     * 인증 키와 타입을 통해 메일 인증을 처리한다.
     * @param emailCodeDTO
     *      EmailCodeDTO 객체
     * @return
     *      인증 결과
     */
    public boolean checkVerifyEmail(EmailCodeDTO emailCodeDTO);
}
