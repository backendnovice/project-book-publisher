/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : 코드 테이블의 타입명을 갖는 Enum.
 *
 * 변경 내역 :
 */

package backendnovice.projectbookpublisher.member.domain;

public enum CodeType {
    REGISTER("REGISTER"),
    FIND("FIND");

    private String type;

    CodeType(String type) {
        this.type = type;
    }

    public String getName() {
        return type;
    }
}
