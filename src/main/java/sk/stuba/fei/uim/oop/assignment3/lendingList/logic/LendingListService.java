package sk.stuba.fei.uim.oop.assignment3.lendingList.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.BookInList;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.ILendingListRepository;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListRequest;

import java.util.List;

@Service
public class LendingListService implements ILendingListService{

    @Autowired
    private ILendingListRepository repository;

    @Autowired
    private IBookService bookService;

    @Autowired
    private IBookInListService bookInListService;

    @Override
    public LendingList create() {
        return this.repository.save(new LendingList());
    }

    @Override
    public List<LendingList> getAll() {
        return this.repository.findAll();
    }

    @Override
    public LendingList getById(Long id) throws NotFoundException {
        LendingList lendingList = this.repository.findLendingListById(id);
        if (lendingList == null){
            throw new NotFoundException();
        }
        return lendingList;
    }

    @Override
    public LendingList addToList(Long listId, LendingListRequest request) throws NotFoundException, IllegalOperationException {
        LendingList lendingList = this.getById(listId);
        this.bookInList(lendingList, request.getId());
        this.listLended(lendingList);
        BookInList bookInList = bookInListService.create();
        bookInList.setBook(bookService.getById(request.getId()));
        lendingList.getListOfBooks().add(bookInListService.save(bookInList));
        return this.repository.save(lendingList);
    }

    @Override
    public void delete(Long listId) throws NotFoundException {
        LendingList lendingList = this.getById(listId);
        if (lendingList.isLended()){
            returnEveryBook(lendingList);
            for (BookInList book : lendingList.getListOfBooks()){
                deleteBookFromList(lendingList.getId(), book.getBook().getId());
            }
        }
        this.repository.delete(this.getById(listId));
    }

    @Override
    public void deleteBookFromList(Long listId, Long bookId) throws NotFoundException {
        LendingList lendingList = this.getById(listId);
        BookInList book = this.bookNotInList(lendingList, bookId);
        lendingList.getListOfBooks().remove(book);
        this.repository.save(lendingList);
        bookInListService.deleteBook(book);
    }

    @Override
    public void lendLendingList(Long listId) throws NotFoundException, IllegalOperationException {
        LendingList lendingList = this.getById(listId);
        this.listLended(lendingList);
        this.everyBookLendable(lendingList);
        this.lendEveryBook(lendingList);
        lendingList.setLended(true);
        this.repository.save(lendingList);
    }

    private void bookInList(LendingList lendingList, Long bookId) throws IllegalOperationException{
        for (BookInList book : lendingList.getListOfBooks()){
            if (book.getBook().getId().equals(bookId)){
                throw new IllegalOperationException();
            }
        }
    }

    BookInList bookNotInList(LendingList lendingList, Long bookId) throws NotFoundException{
        for (BookInList book : lendingList.getListOfBooks()){
            if (book.getBook().getId().equals(bookId)){
                return book;
            }
        }
        throw new NotFoundException();
    }

    private void listLended(LendingList lendingList) throws IllegalOperationException{
        if (lendingList.isLended()){
            throw new IllegalOperationException();
        }
    }

    private void everyBookLendable(LendingList lendingList) throws IllegalOperationException{
        for (BookInList book : lendingList.getListOfBooks()){
            int amount = book.getBook().getAmount();
            int lendCount = book.getBook().getLendCount();
            if (amount == 0){
                throw new IllegalOperationException();
            }
            if (lendCount >= amount){
                throw new IllegalOperationException();
            }
        }
    }

    private void lendEveryBook(LendingList lendingList){
        for (BookInList book : lendingList.getListOfBooks()){
            book.getBook().setLendCount(book.getBook().getLendCount() + 1);
            this.bookInListService.save(book);
        }
    }

    private void returnEveryBook(LendingList lendingList){
        for (BookInList book : lendingList.getListOfBooks()){
            book.getBook().setLendCount(book.getBook().getLendCount() - 1);
            this.bookInListService.save(book);
        }
    }


}
