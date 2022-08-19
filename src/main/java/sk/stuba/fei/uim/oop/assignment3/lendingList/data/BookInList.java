package sk.stuba.fei.uim.oop.assignment3.lendingList.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class BookInList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

}
