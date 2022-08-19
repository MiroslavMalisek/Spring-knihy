package sk.stuba.fei.uim.oop.assignment3.lendingList.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.BookInList;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.IBookInListRepository;

@Service
public class BookInListService implements IBookInListService{

    @Autowired
    private IBookInListRepository repository;

    @Override
    public BookInList create() {
        return this.repository.save(new BookInList());
    }

    @Override
    public BookInList save(BookInList bookInList) {
        return this.repository.save(bookInList);
    }

    @Override
    public BookInList getById(Long id) throws NotFoundException {
        BookInList book = this.repository.findBookInListById(id);
        if (book == null){
            throw new NotFoundException();
        }
        return book;
    }

    @Override
    public void deleteBook(BookInList bookInList) {
        this.repository.delete(bookInList);
    }
}
