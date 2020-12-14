package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.exceptions.InvalidMonthlyCategoryBudgetException;
import mx.kinich49.expensetracker.models.rest.ApiError;
import mx.kinich49.expensetracker.models.rest.ApiResponse;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetCategoryWebModel;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.services.MonthlyBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/budgets")
@SuppressWarnings("unused")
public class MonthlyBudgetController {

    private final MonthlyBudgetService service;

    @Autowired
    public MonthlyBudgetController(MonthlyBudgetService service) {
        this.service = service;
    }

    @GetMapping(params = {"month", "year"})
    public ResponseEntity<ApiResponse<MonthlyBudgetWebModel>> getTransactionItems(
            @RequestParam(value = "month") int month,
            @RequestParam(value = "year") int year) {
        return service.findMonthlyBudget(month, year)
                .map(ApiResponse::new)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MonthlyBudgetWebModel>> postMonthlyBudget(
            @RequestBody MonthlyBudgetRequest request) {
        return Optional.of(service.insertMonthlyBudget(request))
                .map(ApiResponse::new)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/category")
    public ResponseEntity<?> addMonthlyBudgetCategory(@RequestBody MonthlyBudgetCategoryRequest request) {
        try {
            MonthlyBudgetCategoryWebModel webModel = service.addMonthlyBudgetCategory(request);
            ApiResponse<MonthlyBudgetCategoryWebModel> response = new ApiResponse<>(webModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidMonthlyCategoryBudgetException e) {
            ApiError apiError = new ApiError(e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }
}
