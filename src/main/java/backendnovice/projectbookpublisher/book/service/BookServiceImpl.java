/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-22
 * @desc      : 책과 관련된 서비스 메소드를 구현하는 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - 책 등록 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 책 검색 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 * 23-07-21 - backendnovice@gmail.com - 잘못된 코드 수정 및 리팩토링 수행
 * 23-07-22 - backendnovice@gmail.com - 잘못된 쿼리 메소드 호출 수정
 */

package backendnovice.projectbookpublisher.book.service;

import backendnovice.projectbookpublisher.book.domain.BookEntity;
import backendnovice.projectbookpublisher.book.dto.BookDTO;
import backendnovice.projectbookpublisher.book.repository.BookRepository;
import backendnovice.projectbookpublisher.image.domain.ImageEntity;
import backendnovice.projectbookpublisher.image.dto.ImageDTO;
import backendnovice.projectbookpublisher.image.service.ImageService;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MemberService memberService;
    private final ImageService imageService;

    public BookServiceImpl(BookRepository bookRepository, MemberService memberService, ImageService imageService) {
        this.bookRepository = bookRepository;
        this.memberService = memberService;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public void register(MemberDTO memberDTO, BookDTO bookDTO, MultipartFile file) {
        MemberEntity member = memberService.getMemberByEmail(memberDTO);
        ImageEntity image = imageService.saveImageToFile(file);

        BookEntity book = convertToEntity(bookDTO);
        book.setMemberEntity(member);
        book.setImageEntity(image);

        bookRepository.save(book);
    }

    @Override
    public BookDTO getBookOne(BookDTO bookDTO) {
        try {
            BookEntity book = bookRepository.findById(bookDTO.getId())
                    .orElseThrow(NoSuchElementException::new);

            return convertToDto(book);
        }catch (NoSuchElementException e) {
            log.error("ID와 일치하는 이미지가 존재하지 않습니다 : {}", bookDTO.getId());

            return null;
        }
    }

    @Override
    public Page<BookEntity> searchBooks(Pageable pageable) {
        Page<BookEntity> pages = bookRepository.findAll(pageable);

        if(pages.isEmpty()) {
            log.warn("테이블에 데이터가 존재하지 않습니다.");
        }

        return pages;
    }

    @Override
    public Page<BookEntity> searchBooksByAttribute(BookDTO bookDTO, Pageable pageable) {
        Page<BookEntity> pages = null;
        String attribute = "";

        if(bookDTO.getTitle() != null) {
            attribute = "제목";
            pages = searchBooksByTitle(bookDTO, pageable);
        }else if(bookDTO.getAuthor() != null) {
            attribute = "저자";
            pages = searchBooksByAuthor(bookDTO, pageable);
        }else if(bookDTO.getDescription() != null) {
            attribute = "설명";
            pages = searchBooksByDesc(bookDTO, pageable);
        }else if(bookDTO.getType() != null) {
            attribute = "종류";
            pages = searchBooksByType(bookDTO, pageable);
        }
        
        if(pages.isEmpty()) {
            log.warn("해당 책이 존재하지 않습니다: {}", attribute);
        }

        return pages;
    }

    /**
     * 책을 제목을 조건으로 검색하고 Page 객체로 반환한다.
     * @param bookDTO
     *      BookDTO
     * @return
     *      Page 객체
     */
    private Page<BookEntity> searchBooksByTitle(BookDTO bookDTO, Pageable pageable) {
        return bookRepository.findAllByTitleLikeIgnoreCase(bookDTO.getTitle(), pageable);
    }

    /**
     * 책을 저자를 조건으로 검색하고 Page 객체로 반환한다.
     * @param bookDTO
     *      BookDTO
     * @return
     *      Page 객체
     */
    private Page<BookEntity> searchBooksByAuthor(BookDTO bookDTO, Pageable pageable) {
        return bookRepository.findAllByAuthorLikeIgnoreCase(bookDTO.getAuthor(), pageable);
    }

    /**
     * 책을 설명을 조건으로 검색하고 Page 객체로 반환한다.
     * @param bookDTO
     *      BookDTO
     * @return
     *      Page 객체
     */
    private Page<BookEntity> searchBooksByDesc(BookDTO bookDTO, Pageable pageable) {
        return bookRepository.findAllByDescriptionLikeIgnoreCase(bookDTO.getDescription(), pageable);
    }

    /**
     * 책을 종류를 조건으로 검색하고 Page 객체로 반환한다.
     * @param bookDTO
     *      BookDTO
     * @return
     *      Page 객체
     */
    private Page<BookEntity> searchBooksByType(BookDTO bookDTO, Pageable pageable) {
        return bookRepository.findAllByType(bookDTO.getType(), pageable);
    }

    /**
     * BookDTO를 BookEntity로 변환한다.
     * @param bookDTO
     *      BookDTO 객체
     * @return
     *      BookEntity 객체
     */
    private BookEntity convertToEntity(BookDTO bookDTO) {
        return BookEntity.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .description(bookDTO.getDescription())
                .type(bookDTO.getType())
                .datePublish(bookDTO.getDatePublish())
                .type(bookDTO.getType())
                .build();
    }

    /**
     * BookEntity를 BookDTO로 변환한다.
     * @param bookEntity
     *      BookEntity 객체
     * @return
     *      BookDTO 객체
     */
    private BookDTO convertToDto(BookEntity bookEntity) {
        ImageDTO imageDTO = ImageDTO.builder()
                .id(bookEntity.getImage().getId())
                .build();

        String filename = imageService.getFullNameById(imageDTO);

        return BookDTO.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .description(bookEntity.getDescription())
                .datePublish(bookEntity.getDatePublish())
                .type(bookEntity.getType())
                .filename(filename)
                .build();
    }
}
