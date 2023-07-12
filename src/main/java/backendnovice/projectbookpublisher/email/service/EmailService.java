/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-12
 * @desc : Defines member-related feature methods.
 *
 * changelog :
 * 2023-07-12 - backendnovice@gmail.com - Split from MemberService.java
 */

package backendnovice.projectbookpublisher.email.service;

import backendnovice.projectbookpublisher.email.vo.CodeType;

public interface EmailService {
    /**
     * Resend verification email.
     * @param email
     *      To email
     */
    public void sendVerifyEmail(String email);

    /**
     * Check email verification.
     * @param key
     *      Email code key
     * @param type
     *      Email code type
     * @return
     *      Verification result
     */
    public boolean checkVerifyEmail(String key, CodeType type);
}
