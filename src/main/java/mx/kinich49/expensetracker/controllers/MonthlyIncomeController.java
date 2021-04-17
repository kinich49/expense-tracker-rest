package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.rest.ApiResponse;
import mx.kinich49.expensetracker.models.web.MonthlyIncomeWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.services.MonthlyIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/incomes")
public class MonthlyIncomeController {

    private final MonthlyIncomeService monthlyIncomeService;

    @Autowired
    public MonthlyIncomeController(MonthlyIncomeService monthlyIncomeService) {
        this.monthlyIncomeService = monthlyIncomeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<MonthlyIncomeWebModel>> getMonthlyIncome(@RequestParam
                                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                       YearMonth date) {
        return monthlyIncomeService.findMonthlyIncomeBy(date)
                .map(ApiResponse::new)
                .map(apiResponse -> new ResponseEntity<>(apiResponse, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<MonthlyIncomeWebModel>> getMonthlyIncome(@RequestParam
                                                                                       long id) {
        return monthlyIncomeService.findMonthlyIncomeBy(id)
                .map(ApiResponse::new)
                .map(apiResponse -> new ResponseEntity<>(apiResponse, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> addMonthlyIncome(@RequestBody MonthlyIncomeRequest request) {
        try {
            MonthlyIncomeWebModel webModel = monthlyIncomeService.addMonthlyIncome(request);
            return new ResponseEntity<>(new ApiResponse<>(webModel), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMonthlyIncome(@PathVariable(name = "id") long id) {
        monthlyIncomeService.deleteMonthlyIncome(id);
    }
}
