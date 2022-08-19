package sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingList;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LendingListResponse {

    private Long id;
    private List<BookInListResponse> lendingList;
    private boolean lended;

    public LendingListResponse(LendingList lendingList) {
        this.id = lendingList.getId();
        this.lendingList = lendingList.getListOfBooks().stream().map(BookInListResponse::new).collect(Collectors.toList());
        this.lended = lendingList.isLended();
    }

}
