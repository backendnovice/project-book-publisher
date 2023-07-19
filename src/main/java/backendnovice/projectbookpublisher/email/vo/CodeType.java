/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 인증 타입을 정의하는 열거형 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.email.vo;

public enum CodeType {
    REGISTER("REGISTER"),
    FIND("FIND");

    private String type;

    CodeType(String type) {
        this.type = type;
    }

    /**
     * 인증 타입명을 반환한다.
     * @return
     *      인증 타입명
     */
    public String getValue() {
        return type;
    }
}
