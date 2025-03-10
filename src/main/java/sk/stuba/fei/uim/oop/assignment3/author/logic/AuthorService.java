package sk.stuba.fei.uim.oop.assignment3.author.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.data.AuthorRepository;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class AuthorService implements IAuthorService {

    @Autowired
    AuthorRepository repository;

    @Override
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Author create(AuthorRequest request) {
        return this.repository.save(new Author(request));
    }

    @Override
    public Author getById(Long id) throws NotFoundException {
        Author author = this.repository.findAuthorById(id);
        if (author == null){
            throw new NotFoundException();
        }
        return author;
    }

    @Override
    public void addBookToAuthor(Book book) {
        Author author = this.repository.findAuthorById(book.getAuthor().getId());
        author.getBooks().add(book);
    }

    @Override
    public Author updateAuthor(Long authorId, AuthorRequest body) throws NotFoundException {
        Author author = this.getById(authorId);
        if (body.getName() != null) {
            author.setName(body.getName());
        }
        if (body.getSurname() != null) {
            author.setSurname(body.getSurname());
        }
        return this.repository.save(author);
    }

    @Override
    public void deleteBook(Book book) {
        Author author = this.repository.findAuthorById(book.getAuthor().getId());
        author.getBooks().remove(book);
    }

    @Override
    public void delete(Long authorId) throws NotFoundException {
        this.repository.delete(this.getById(authorId));
    }
}
