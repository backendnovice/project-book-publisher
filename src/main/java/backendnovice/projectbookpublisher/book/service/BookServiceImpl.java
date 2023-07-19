/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 책과 관련된 서비스 메소드를 구현하는 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - 책 등록 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 책 검색 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.book.service;

import backendnovice.projectbookpublisher.book.domain.BookEntity;
import backendnovice.projectbookpublisher.book.dto.BookDTO;
import backendnovice.projectbookpublisher.book.repository.BookRepository;
import backendnovice.projectbookpublisher.book.vo.BookType;
import backendnovice.projectbookpublisher.image.domain.ImageEntity;
import backendnovice.projectbookpublisher.image.service.ImageService;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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
    public void register(String email, BookDTO bookDTO, MultipartFile file) {
        MemberEntity memberEntity = memberService.getMemberByEmail(email);
        ImageEntity imageEntity = imageService.saveImageToFile(file);

        BookEntity bookEntity = convertToEntity(bookDTO);
        bookEntity.setMemberEntity(memberEntity);
        bookEntity.setImageEntity(imageEntity);

        bookRepository.save(bookEntity);
    }

    @Override
    public BookDTO getBookOne(Long id) {
        Optional<BookEntity> book = bookRepository.findById(id);

        if(book.isPresent()) {
            return convertToDto(book.get());
        }else {
            throw new IllegalArgumentException("There is no ID in Book table.");
        }
    }

    @Override
    public Page<BookEntity> searchBooks(Pageable pageable) {
        PageRequest request = PageRequest.of(0, 9);

        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<BookEntity> searchBooksByTitle(String title) {
        PageRequest request = PageRequest.of(0, 9);

        return bookRepository.findAllByTitleLikeIgnoreCase(title, request);
    }

    @Override
    public Page<BookEntity> searchBooksByAuthor(String author) {
        PageRequest request = PageRequest.of(0, 9);

        return bookRepository.findAllByAuthorLikeIgnoreCase(author, request);
    }

    @Override
    public Page<BookEntity> searchBooksByContent(String content) {
        PageRequest request = PageRequest.of(0, 9);

        return bookRepository.findAllByContentLikeIgnoreCase(content, request);
    }

    @Override
    public Page<BookEntity> searchBooksByType(BookType type) {
        PageRequest request = PageRequest.of(0, 9);

        return bookRepository.findAllByType(type, request);
    }

    /**
     * BookDTO를 BookEntity로 변환한다.
     * @param bookDTO
     *      BookDTO
     * @return
     *      BookEntity
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
     *      Book entity
     * @return
     *      BookDTO
     */
    private BookDTO convertToDto(BookEntity bookEntity) {
        Long id = bookEntity.getId();

        return BookDTO.builder()
                .id(id)
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .description(bookEntity.getDescription())
                .datePublish(bookEntity.getDatePublish())
                .type(bookEntity.getType())
                .filename(imageService.getImageFullName(id))
                .build();
    }
}
