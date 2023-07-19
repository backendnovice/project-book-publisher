/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 책과 관련된 서비스 메소드를 정의하는 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - 책 등록 메소드 정의
 * 23-07-19 - backendnovice@gmail.com - 책 검색 메소드 정의
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.book.service;

import backendnovice.projectbookpublisher.book.domain.BookEntity;
import backendnovice.projectbookpublisher.book.dto.BookDTO;
import backendnovice.projectbookpublisher.book.vo.BookType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    /**
     * 새로운 책을 DB에 등록한다.
     * @param email
     *      회원 이메일
     * @param bookDTO
     *      BookDTO 객체
     * @param file
     *      이미지 파일
     *
     */
    public void register(String email, BookDTO bookDTO, MultipartFile file);

    /**
     * ID와 일치하는 책을 검색하고 BookDTO 로 반환한다.
     * @param id
     *      Book id
     */
    public BookDTO getBookOne(Long id);

    /**
     * 책을 조건없이 검색하고 Page 객체로 반환한다.
     * @return
     *      Page 객체
     */
    public Page<BookEntity> searchBooks(Pageable pageable);

    /**
     * 책을 제목을 조건으로 검색하고 Page 객체로 반환한다.
     * @param title
     *      책 제목
     * @return
     *      Page 객체
     */
    public Page<BookEntity> searchBooksByTitle(String title);

    /**
     * 책을 저자를 조건으로 검색하고 Page 객체로 반환한다.
     * @param author
     *      책 저자명
     * @return
     *      Page 객체
     */
    public Page<BookEntity> searchBooksByAuthor(String author);

    /**
     * 책을 설명을 조건으로 검색하고 Page 객체로 반환한다.
     * @param content
     *      책 설명
     * @return
     *      Page 객체
     */
    public Page<BookEntity> searchBooksByContent(String content);

    /**
     * 타입과 일치하는 책을 검색하고 Page 객체로 반환한다.
     * @param type
     *      책 타입
     * @return
     *      Page 객체
     */
    public Page<BookEntity> searchBooksByType(BookType type);
}
