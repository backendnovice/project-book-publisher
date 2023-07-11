/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : Defines type of email code.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.email.vo;

public enum CodeType {
    REGISTER("REGISTER"),
    FIND("FIND");

    private String type;

    CodeType(String type) {
        this.type = type;
    }

    public String getValue() {
        return type;
    }
}
