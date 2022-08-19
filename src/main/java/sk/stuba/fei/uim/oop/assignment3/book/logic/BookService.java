package sk.stuba.fei.uim.oop.assignment3.book.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.logic.IAuthorService;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.IBookRepository;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class BookService implements IBookService{

    @Autowired
    private IBookRepository repository;

    @Autowired
    private IAuthorService authorService;

    @Override
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Book create(BookRequest request) throws NotFoundException {
        try {
            Author author = this.authorService.getById(request.getAuthor());
            Book book = new Book(request, author);
            this.authorService.addBookToAuthor(book);
            return this.repository.save(book);
        }catch (NotFoundException exception){
            throw new NotFoundException();
        }
    }

    @Override
    public Book getById(Long id) throws NotFoundException {
        Book book = this.repository.findBookById(id);
        if (book == null){
            throw new NotFoundException();
        }
        return book;
    }

    @Override
    public Book updateBook(Long bookId, BookUpdateRequest body) throws NotFoundException {
        Book book = this.getById(bookId);
        if ((book.getAuthor() != null) && (body.getAuthor() != 0)) {
            Author author = this.authorService.getById(body.getAuthor());
            this.authorService.deleteBook(book);
            book.setAuthor(author);
            this.authorService.addBookToAuthor(book);
        }
        if (body.getName() != null) {
            book.setName(body.getName());
        }
        if (body.getDescription() != null) {
            book.setDescription(body.getDescription());
        }
        if (body.getPages() != 0){
            book.setPages(body.getPages());
        }
        return this.repository.save(book);
    }

    @Override
    public void delete(Long bookId) throws NotFoundException {
        Book book = this.getById(bookId);
        this.authorService.deleteBook(book);
        this.repository.delete(this.getById(bookId));
    }

    @Override
    public int getAmount(long bookId) throws NotFoundException {
        return this.getById(bookId).getAmount();
    }

    @Override
    public int addAmount(long bookId, int increment) throws NotFoundException {
        Book book = this.getById(bookId);
        book.setAmount(book.getAmount() + increment);
        this.repository.save(book);
        return book.getAmount();
    }

    @Override
    public int getLendCount(long bookId) throws NotFoundException {
        return this.getById(bookId).getLendCount();
    }
}
