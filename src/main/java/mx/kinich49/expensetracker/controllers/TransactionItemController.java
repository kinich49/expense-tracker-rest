package mx.kinich49.expensetracker.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.kinich49.expensetracker.models.TransactionItem;
import mx.kinich49.expensetracker.requests.TransactionItemsRequest;
import mx.kinich49.expensetracker.services.TransactionItemService;

@RestController
@RequestMapping("api/transactions")
public class TransactionItemController {

    private TransactionItemService transactionService;

    @Autowired
    public TransactionItemController(TransactionItemService transactionService) {
        this.transactionService = transactionService;
    }

    public ResponseEntity<List<TransactionItem>> getTransactionItems(@RequestBody TransactionItemsRequest request) {
        List<TransactionItem> transactionItems = Optional
                .ofNullable(transactionService.findTransactions(request.getCategory(), request.getLocalDate()))
                .orElseGet(() -> Collections.emptyList());
        return new ResponseEntity<List<TransactionItem>>(transactionItems, HttpStatus.OK);
    }
}