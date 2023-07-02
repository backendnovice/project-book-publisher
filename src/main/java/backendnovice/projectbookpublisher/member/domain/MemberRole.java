/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : 회원 테이블의 역할명을 갖는 Enum.
 *
 * 변경 내역 :
 */

package backendnovice.projectbookpublisher.member.domain;

public enum MemberRole {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }

    public String get() {
        return role;
    }
}
