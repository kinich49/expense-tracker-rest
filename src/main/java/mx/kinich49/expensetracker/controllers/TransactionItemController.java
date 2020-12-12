package mx.kinich49.expensetracker.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.kinich49.expensetracker.base.RestError;
import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.models.database.TransactionItem;
import mx.kinich49.expensetracker.repositories.TransactionItemRepository;
import mx.kinich49.expensetracker.requests.TransactionItemRequest;
import mx.kinich49.expensetracker.services.TransactionItemService;

@RestController
@RequestMapping("api/transactions")
public class TransactionItemController {

    private final TransactionItemRepository repository;
    private final TransactionItemService service;

    @Autowired
    public TransactionItemController(TransactionItemRepository repository, TransactionItemService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(params = "category_id")
    public ResponseEntity<List<TransactionItem>> getTransactionItems(
            @RequestParam(value = "category_id") long categoryId) {
        List<TransactionItem> transactionItems = Optional.ofNullable(repository.findByCategoryId(categoryId))
                .orElseGet(Collections::emptyList);
        return new ResponseEntity<>(transactionItems, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransactionItem>> getTransactionItems() {
        List<TransactionItem> transactionItems = Optional.of(repository.findAll())
                .orElseGet(Collections::emptyList);
        return new ResponseEntity<>(transactionItems, HttpStatus.OK);
    }

    @GetMapping(params = {"month", "year"})
    public ResponseEntity<List<TransactionItem>> getTransactionItems(@RequestParam(value = "month") int month,
            @RequestParam(value = "year") int year) {
        List<TransactionItem> transactionItems = Optional.ofNullable(repository.findByMonthAndYear(month, year))
                .orElseGet(Collections::emptyList);
        return new ResponseEntity<>(transactionItems, HttpStatus.OK);
    }

    @GetMapping(params = "month")
    public ResponseEntity<RestError> getTransactionItems(@RequestParam(value = "month") int month) {
        return new ResponseEntity<>(
                new RestError("Parameter year is missing", 422),
                null,
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping
    public ResponseEntity<?> postTransactionItem(@RequestBody TransactionItemRequest request){
        try {
            TransactionItem transactionItem = service.addTransactionItem(request);
            return new ResponseEntity<>(transactionItem, HttpStatus.OK);
        } catch (CategoryNotFoundException e){
            RestError restError = new RestError(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value());
            return new ResponseEntity<>(restError, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransactionItem(@PathVariable(value= "id") long transactionId) {
        repository.deleteById(transactionId);
    }
}