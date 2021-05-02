package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.rest.ApiResponse;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.repositories.TransactionRepository;
import mx.kinich49.expensetracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/transactions")
@SuppressWarnings("unused")
public class TransactionController {

    private final TransactionRepository repository;
    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionRepository repository,
                                 TransactionService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(params = {"categoryId", "startDate", "endDate"})
    public ResponseEntity<?> getTransactionItems(@RequestParam
                                                         long categoryId,
                                                 @RequestParam
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                         LocalDateTime startDate,
                                                 @RequestParam
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                         LocalDateTime endDate) {
        try {
            return Optional.ofNullable(service.findTransactionsByCategory(categoryId, startDate, endDate))
                    .map(ApiResponse::new)
                    .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(params = {"startDate", "endDate"})
    public ResponseEntity<?> getTransactionItems(@RequestParam
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                         LocalDateTime startDate,
                                                 @RequestParam
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                         LocalDateTime endDate) {
        return Optional.ofNullable(service.findTransactions(startDate, endDate))
                .map(ApiResponse::new)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/payment/{id}", params = {"startDate", "endDate"})
    public ResponseEntity<?> getTransactionsByPaymentMethod(@PathVariable(name = "id")
                                                                    long paymentMethodId,
                                                            @RequestParam
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                    LocalDateTime startDate,
                                                            @RequestParam
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                    LocalDateTime endDate) {
        try {
            return Optional.ofNullable(service.findTransactionsByPaymentMethod(paymentMethodId, startDate, endDate))
                    .map(ApiResponse::new)
                    .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> postTransaction(@RequestBody TransactionRequest request) {
        try {
            TransactionWebModel webModel = service.addTransaction(request);
            ApiResponse<TransactionWebModel> response = new ApiResponse<>(webModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable(value = "id") long transactionId) {
        repository.deleteById(transactionId);
    }
}