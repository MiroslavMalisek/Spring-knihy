package sk.stuba.fei.uim.oop.assignment3.lendingList.logic;

import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.BookInList;

public interface IBookInListService {

    BookInList create();
    BookInList save(BookInList bookInList);
    BookInList getById(Long id) throws NotFoundException;
    void deleteBook(BookInList bookInList);

}
