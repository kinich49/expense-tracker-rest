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
import java.util.List;
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

    @GetMapping(params = {"category_id", "month", "year"})
    public ResponseEntity<ApiResponse<List<TransactionWebModel>>> getTransactionItems(
            @RequestParam(value = "category_id") long categoryId,
            @RequestParam(value = "month") int month,
            @RequestParam(value = "year") int year) {
        //TODO change two ints for YearMonth
        return Optional.ofNullable(service.findTransactionsByCategory(categoryId, month, year))
                .map(ApiResponse::new)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping(params = {"month", "year"})
    public ResponseEntity<ApiResponse<List<TransactionWebModel>>> getTransactionItems(
            @RequestParam(value = "month") int month,
            @RequestParam(value = "year") int year) {
        return Optional.ofNullable(service.findTransactions(month, year))
                .map(ApiResponse::new)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(params = "month")
    public ResponseEntity<ApiResponse<?>> getTransactions(@RequestParam(value = "month") int month) {
        return new ResponseEntity<>(
                new ApiResponse<>("Parameter year is missing"),
                null,
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<?> getTransactionsByPaymentMethod(@PathVariable(name = "id")
                                                                    long paymentMethodId,
                                                            @DateTimeFormat
                                                            @RequestParam
                                                                    LocalDateTime beginDate,
                                                            @DateTimeFormat
                                                            @RequestParam
                                                                    LocalDateTime endDate) {
        try {
            List<TransactionWebModel> webModels = service.findTransactions(paymentMethodId, beginDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(webModels), HttpStatus.OK);
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