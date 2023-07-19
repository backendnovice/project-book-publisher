/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Book 테이블 입출력을 담당하는 레포지토리 인터페이스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 책 검색 쿼리 메소드 추가
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 **/

package backendnovice.projectbookpublisher.book.repository;

import backendnovice.projectbookpublisher.book.domain.BookEntity;
import backendnovice.projectbookpublisher.book.vo.BookType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    /**
     * 책 제목을 포함하는 튜플들을 대소문자 구분없이 검색한다.
     * @param title
     *      책 제목
     * @return
     *      Page 객체
     */
    Page<BookEntity> findAllByTitleLikeIgnoreCase(@Param("book_title") String title, Pageable pageable);

    /**
     * 책 저자명을 포함하는 튜플들을 대소문자 구분없이 검색한다.
     * @param author
     *      책 저자
     * @return
     *      Page 객체
     */
    Page<BookEntity> findAllByAuthorLikeIgnoreCase(@Param("book_author") String author, Pageable pageable);

    /**
     * 책 내용을 포함하는 튜플들을 대소문자 구분없이 검색한다.
     * @param content
     *      책 내용
     * @return
     *      Page 객체
     */
    Page<BookEntity> findAllByContentLikeIgnoreCase(@Param("book_content") String content, Pageable pageable);

    /**
     * 책 타입과 일치하는 튜플들을 검색한다.
     * @param bookType
     *      책 타입
     * @return
     *      Page 객체
     */
    Page<BookEntity> findAllByType(@Param("book_type")BookType bookType, Pageable pageable);
}
