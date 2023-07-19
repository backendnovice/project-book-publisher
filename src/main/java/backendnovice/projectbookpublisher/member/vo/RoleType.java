/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 회원 타입을 정의하는 열거형 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.member.vo;

public enum RoleType {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    /**
     * 회원 타입을 반환한다.
     * @return
     *      회원 타입
     */
    public String getName() {
        return role;
    }
}
