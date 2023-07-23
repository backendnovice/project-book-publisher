/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-22
 * @desc      : 책과 관련된 POST, GET 요청을 처리하는 컨트롤러 클래스.
 * @changelog :
 * 23-07-15 - backendnovice@gmail.com - 책 등록 요청 핸들링 메소드 추가
 * 23-07-16 - backendnovice@gmail.com - 책 조회 요청 핸들링 메소드 추가
 * 23-07-18 - backendnovice@gmail.com - 책 목록 요청 핸들링 메소드 추가
 * 23-07-19 - backendnovice@gmail.com - 관심사 분리 및 주석 한글화 수정
 * 23-07-21 - backendnovice@gmail.com - 책 목록 요청 핸들링 메소드 수정
 * 23-07-22 - backendnovice@gmail.com - 목록 오버로딩 호출 이슈 픽스
 */

package backendnovice.projectbookpublisher.book.controller;

import backendnovice.projectbookpublisher.book.domain.BookEntity;
import backendnovice.projectbookpublisher.book.dto.BookDTO;
import backendnovice.projectbookpublisher.book.service.BookService;
import backendnovice.projectbookpublisher.common.dto.PaginationDTO;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;

@Controller
@RequestMapping("/books")
public class BookViewController {
    private final BookService bookService;

    public BookViewController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * "/books/register"에 대한 GET 요청을 처리하고, 책 등록 뷰를 반환한다.
     * @return
     *      책 등록 뷰
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "books/register";
    }

    /**
     * "/books/list"에 대한 GET 요청을 처리하고, 책 목록 뷰를 반환한다.
     * @param model
     *      Model 객체
     * @param pageable
     *      Pageable 객체
     * @return
     *      책 목록 뷰
     */
    @GetMapping("/list")
    public String getListPage(Model model,
            @PageableDefault(page = 0, size = 10, sort = "book_id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BookEntity> books = bookService.searchBooks(pageable);

        PaginationDTO paginationDTO = new PaginationDTO(books);

        model.addAttribute("books", books);
        model.addAttribute("dto", paginationDTO);

        return "books/list";
    }

    /**
     * "/books/list"에 대한 GET 요청을 처리하고, 검색 옵션이 적용된 책 목록 뷰를 반환한다.
     * @param bookDTO
     *      BookDTO 객체
     * @param model
     *      Model 객체
     * @param pageable
     *      Pageable 객체
     * @return
     *      책 목록 뷰
     */
    @GetMapping("/list/search")
    public String getListPageWithOption(BookDTO bookDTO, Model model,
            @PageableDefault(page = 0, size = 10, sort = "book_id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BookEntity> books = bookService.searchBooksByAttribute(bookDTO, pageable);

        PaginationDTO paginationDTO = new PaginationDTO(books);

        model.addAttribute("books", books);
        model.addAttribute("pagination", paginationDTO);

        return "books/list";
    }

    /**
     * "/books/read"에 대한 GET 요청을 처리하고, ID에 해당하는 책 조회 뷰를 반환한다.
     * @param id
     *      책 ID
     * @param model
     *      Model 객체
     * @return
     *      책 조회 뷰
     */
    @GetMapping("/read/{id}")
    public String getReadPage(@PathVariable Long id, Model model) {
        BookDTO bookDTO = bookService.getBookOne(id);

        model.addAttribute("book", bookDTO);

        return "books/read";
    }

    /**
     * "/book/register"에 대한 POST 요청을 처리하고, 책 등록 서비스를 호출한다.
     * @param bookDTO
     *      BookDTO 객체
     * @param file
     *      MultipartFile 객체
     * @param principal
     *      로그인 회원 객체
     * @return
     *      책 목록 뷰
     */
    @PostMapping("/register")
    public String registerProcess(BookDTO bookDTO, @RequestParam("image") MultipartFile file, Principal principal) {
        MemberDTO memberDTO = MemberDTO.builder()
                .email(principal.getName())
                .build();

        bookService.register(memberDTO, bookDTO, file);

        return "books/list";
    }

    /**
     * 책 데이터와 이미지, 회원의 이메일을 통해 책을 수정하는 서비스를 호출한다.
     * @param bookDTO
     *      책 데이터
     * @param file
     *      이미지 파일
     * @param principal
     *      로그인 회원 객체
     * @return
     *      책 조회 뷰
     */
    @PostMapping("/modify")
    public String modifyProcess(BookDTO bookDTO, MultipartFile file, Principal principal) {
        bookService.modify(bookDTO, principal.getName(), file);

        return "/books/read/" + bookDTO.getId();
    }

    /**
     * 책 ID와 로그인 회원의 이메일을 받아서 책을 삭제하는 서비스를 호출한다.
     * @param id
     *      책 ID
     * @param principal
     *      로그인 회원 객체
     * @return
     *      책 목록 뷰
     */
    @PostMapping("/delete")
    public String deleteProcess(long id, Principal principal) {
        bookService.delete(id, principal.getName());

        return "books/list";
    }
}
