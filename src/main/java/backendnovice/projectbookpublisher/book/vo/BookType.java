/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Defines type of book role.
 *
 * changelog :
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

    public String getType() {
        return type;
    }
}
