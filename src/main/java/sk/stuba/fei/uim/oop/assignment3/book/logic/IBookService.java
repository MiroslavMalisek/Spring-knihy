package sk.stuba.fei.uim.oop.assignment3.book.logic;

import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface IBookService {

    List<Book> getAll();
    Book create(BookRequest request) throws NotFoundException;
    Book getById(Long id) throws NotFoundException;
    Book updateBook(Long bookId, BookUpdateRequest body) throws NotFoundException;
    void delete(Long bookId) throws NotFoundException;
    int getAmount(long bookId) throws NotFoundException;
    int addAmount(long bookId, int increment) throws NotFoundException;
    int getLendCount(long bookId) throws NotFoundException;

}
