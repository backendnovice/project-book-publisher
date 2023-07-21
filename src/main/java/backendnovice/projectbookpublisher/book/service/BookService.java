/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-21
 * @desc      : 책과 관련된 서비스 메소드를 정의하는 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - 책 등록 메소드 정의
 * 23-07-19 - backendnovice@gmail.com - 책 검색 메소드 정의
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 * 23-07-21 - backendnovice@gmail.com - 접근지정자 제거 및 리팩토링 수행
 */

package backendnovice.projectbookpublisher.book.service;

import backendnovice.projectbookpublisher.book.domain.BookEntity;
import backendnovice.projectbookpublisher.book.dto.BookDTO;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    /**
     * 새로운 책을 DB에 등록한다.
     * @param memberDTO
     *      MemberDTO 객체
     * @param bookDTO
     *      BookDTO 객체
     * @param file
     *      이미지 파일
     *
     */
    void register(MemberDTO memberDTO, BookDTO bookDTO, MultipartFile file);

    /**
     * ID와 일치하는 책을 검색하고 BookDTO 로 반환한다.
     * @param bookDTO
     *      BookDTO 객체
     */
    BookDTO getBookOne(BookDTO bookDTO);

    /**
     * 책을 조건없이 검색하고 Page 객체로 반환한다.
     * @return
     *      Page 객체
     */
    Page<BookEntity> searchBooks(Pageable pageable);

    /**
     * 책을 조건으로 검색하고 Page 객체로 반환한다.
     * @param bookDTO
     *      BookDTO 객체
     * @param pageable
     *      Pageable 객체
     * @return
     *      Page 객체
     */
    Page<BookEntity> searchBooksByAttribute(BookDTO bookDTO, Pageable pageable);
}
