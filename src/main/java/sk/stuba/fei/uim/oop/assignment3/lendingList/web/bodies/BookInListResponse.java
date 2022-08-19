package sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.BookInList;

@Getter
@Setter
@NoArgsConstructor
public class BookInListResponse {

    private Long id;
    private String name;
    private String description;
    private Long author;
    private int pages;
    private int amount;
    private int lendCount;

    public BookInListResponse(BookInList book){
        this.id = book.getBook().getId();
        this.name = book.getBook().getName();
        this.description = book.getBook().getDescription();
        this.author = book.getBook().getAuthor().getId();
        this.pages = book.getBook().getPages();
        this.amount = book.getBook().getAmount();
        this.lendCount = book.getBook().getLendCount();
    }
}
