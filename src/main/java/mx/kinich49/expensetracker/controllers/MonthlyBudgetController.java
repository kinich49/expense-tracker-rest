package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.ApiResponse;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetCategoryWebModel;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.SimpleMonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.services.MonthlyBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
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

    @GetMapping
    public ResponseEntity<ApiResponse<MonthlyBudgetWebModel>> getMonthlyBudgets(@RequestParam
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       YearMonth date) {
        try {
            return service.findMonthlyBudgets(date)
                    .map(ApiResponse::new)
                    .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SimpleMonthlyBudgetWebModel>> addMonthlyBudget(
            @RequestBody MonthlyBudgetRequest request) {
        try {
            return Optional.of(service.insertMonthlyBudget(request))
                    .map(ApiResponse::new)
                    .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{budgetId}")
    public void removeMonthlyBudget(@PathVariable(name = "budgetId") long budgetId) {
        service.removeMonthlyBudget(budgetId);
    }

    @PostMapping("/category")
    public ResponseEntity<?> addMonthlyBudgetCategory(@RequestBody MonthlyBudgetCategoryRequest request) {
        try {
            var webModel = service.addMonthlyBudgetCategory(request);
            ApiResponse<MonthlyBudgetCategoryWebModel> response = new ApiResponse<>(webModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @ResponseStatus
    public ResponseEntity<ApiResponse<SimpleMonthlyBudgetWebModel>> updateMonthlyBudget(@RequestBody MonthlyBudgetRequest request) {
        try {
            var webModel = service.update(request);
            var apiResponse = new ApiResponse<>(webModel);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping(
            path = "/{budgetId}",
            params = {"categoryId"}
    )
    @ResponseStatus(code = HttpStatus.OK)
    public void removeMonthlyBudgetCategory(@PathVariable("budgetId") long budgetId,
                                            @RequestParam(name = "categoryId") long categoryId) {
        service.removeMonthlyBudgetCategory(budgetId, categoryId);
    }
}
