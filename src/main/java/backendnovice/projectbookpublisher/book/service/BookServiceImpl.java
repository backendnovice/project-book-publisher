/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Implements book-related feature methods.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.book.service;

import backendnovice.projectbookpublisher.book.domain.Book;
import backendnovice.projectbookpublisher.book.dto.BookDTO;
import backendnovice.projectbookpublisher.book.repository.BookRepository;
import backendnovice.projectbookpublisher.image.domain.Image;
import backendnovice.projectbookpublisher.member.domain.Member;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BookServiceImpl(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void register(BookDTO bookDTO, Image image, String email) {
        Member member = memberRepository.findByEmail(email).get();

        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .content(bookDTO.getContent())
                .type(bookDTO.getType())
                .datePublish(bookDTO.getDatePublish())
                .member(member)
                .image(image)
                .build();

        bookRepository.save(book);
    }
}
