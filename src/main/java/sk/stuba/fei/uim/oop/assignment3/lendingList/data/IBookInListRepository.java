package sk.stuba.fei.uim.oop.assignment3.lendingList.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookInListRepository extends JpaRepository<BookInList, Long> {

    BookInList findBookInListById(Long id);

}
