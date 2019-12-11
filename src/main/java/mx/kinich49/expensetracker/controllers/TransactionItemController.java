package mx.kinich49.expensetracker.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.kinich49.expensetracker.base.RestError;
import mx.kinich49.expensetracker.models.TransactionItem;
import mx.kinich49.expensetracker.repositories.TransactionItemRepository;

@RestController
@RequestMapping("api/transactions")
public class TransactionItemController {

    private TransactionItemRepository repository;

    @Autowired
    public TransactionItemController(TransactionItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping(params = "category_id")
    public ResponseEntity<List<TransactionItem>> getTransactionItems(
            @RequestParam(value = "category_id") long categoryId) {
        List<TransactionItem> transactionItems = Optional.ofNullable(repository.findByCategoryId(categoryId))
                .orElseGet(() -> Collections.emptyList());
        return new ResponseEntity<List<TransactionItem>>(transactionItems, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransactionItem>> getTransactionItems() {
        List<TransactionItem> transactionItems = Optional.ofNullable(repository.findAll())
                .orElseGet(() -> Collections.emptyList());
        return new ResponseEntity<List<TransactionItem>>(transactionItems, HttpStatus.OK);
    }

    @GetMapping(params = {"month", "year"})
    public ResponseEntity<List<TransactionItem>> getTransactionItems(@RequestParam(value = "month") int month,
            @RequestParam(value = "year") int year) {
        List<TransactionItem> transactionItems = Optional.ofNullable(repository.findByMonthAndYear(month, year))
                .orElseGet(() -> Collections.emptyList());
        return new ResponseEntity<List<TransactionItem>>(transactionItems, HttpStatus.OK);
    }

    @GetMapping(params = "month")
    public ResponseEntity<RestError> getTransactionItems(@RequestParam(value = "month") int month) {
        return new ResponseEntity<RestError>(new RestError("Parameter year is missing", 422), null, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}