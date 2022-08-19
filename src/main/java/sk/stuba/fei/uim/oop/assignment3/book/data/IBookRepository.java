package sk.stuba.fei.uim.oop.assignment3.book.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book, Long> {

    List<Book> findAll();
    Book findBookById(Long id);
}
