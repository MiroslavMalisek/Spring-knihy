package sk.stuba.fei.uim.oop.assignment3.lendingList.logic;

import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListRequest;

import java.util.List;

public interface ILendingListService {
     LendingList create();
     List<LendingList> getAll();
     LendingList getById(Long id) throws NotFoundException;
     LendingList addToList(Long listId, LendingListRequest request) throws NotFoundException, IllegalOperationException;
     void delete(Long listId) throws NotFoundException;
     void deleteBookFromList(Long listId, Long bookId) throws NotFoundException;
     void lendLendingList(Long listId) throws NotFoundException, IllegalOperationException;
}
