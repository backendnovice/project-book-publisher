/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Provides queries to perform I/O for 'book' table.
 *
 * changelog :
 **/

package backendnovice.projectbookpublisher.book.repository;

import backendnovice.projectbookpublisher.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
