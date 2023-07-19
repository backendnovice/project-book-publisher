/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 책 타입을 정의하는 열거형 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.book.vo;

public enum BookType {
    NOVEL("NOVEL"),
    ESSAY("ESSAY"),
    POEM("POEM"),
    STUDY("STUDY");

    private String type;

    BookType(String type) {
        this.type = type;
    }

    /**
     * 책 타입명을 반환한다.
     * @return
     *      책 타입명
     */
    public String getType() {
        return type;
    }
}
