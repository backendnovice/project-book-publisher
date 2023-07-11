/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : Defines type of member role.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.member.vo;

public enum RoleType {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getName() {
        return role;
    }
}
