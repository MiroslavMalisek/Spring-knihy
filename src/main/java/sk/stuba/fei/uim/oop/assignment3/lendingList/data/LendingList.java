package sk.stuba.fei.uim.oop.assignment3.lendingList.data;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class LendingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BookInList> listOfBooks;

    private boolean lended;

    public LendingList() {
        this.listOfBooks = new ArrayList<>();
    }
}
