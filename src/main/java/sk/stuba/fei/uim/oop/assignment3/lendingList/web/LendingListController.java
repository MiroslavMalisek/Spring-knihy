package sk.stuba.fei.uim.oop.assignment3.lendingList.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.logic.ILendingListService;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListRequest;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class LendingListController {

    @Autowired
    private ILendingListService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LendingListResponse> addList() {
        return new ResponseEntity<>(new LendingListResponse(this.service.create()), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LendingListResponse> getAllLists(){
        return this.service.getAll().stream().map(LendingListResponse::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LendingListResponse getLendingList(@PathVariable("id") Long listId) throws NotFoundException {
        return new LendingListResponse(this.service.getById(listId));
    }

    @PostMapping(value = "/{id}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LendingListResponse addBookToList(@PathVariable("id") Long listId, @RequestBody LendingListRequest request) throws NotFoundException, IllegalOperationException {
        return new LendingListResponse(this.service.addToList(listId, request));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long listId) throws NotFoundException {
        this.service.delete(listId);
    }

    @DeleteMapping(value = "/{id}/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBookFromList(@PathVariable("id") Long listId, @RequestBody LendingListRequest request) throws NotFoundException {
        this.service.deleteBookFromList(listId, request.getId());
    }

    @GetMapping(value = "/{id}/lend")
    public ResponseEntity<Void> lendLendingList(@PathVariable("id") Long listId) throws NotFoundException, IllegalOperationException {
        this.service.lendLendingList(listId);
        return ResponseEntity.ok().build();
    }
}
