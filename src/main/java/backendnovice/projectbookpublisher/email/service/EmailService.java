/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 이메일과 관련된 서비스 메소드를 정의하는 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - MemberService 에서 분리
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.email.service;

import backendnovice.projectbookpublisher.email.vo.CodeType;

public interface EmailService {
    /**
     * 인증 메일 재전송을 처리한다.
     * @param email
     *      수신 이메일
     */
    public void sendVerifyEmail(String email);

    /**
     * 인증 키와 타입을 통해 메일 인증을 처리한다.
     * @param key
     *      이메일 인증 키
     * @param type
     *      이메인 인증 타입
     * @return
     *      인증 결과
     */
    public boolean checkVerifyEmail(String key, CodeType type);
}
