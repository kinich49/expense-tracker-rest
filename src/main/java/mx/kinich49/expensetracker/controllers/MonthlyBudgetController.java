package mx.kinich49.expensetracker.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.kinich49.expensetracker.models.web.WMonthlyBudgetWebModel;
import mx.kinich49.expensetracker.exceptions.MonthlyBudgetNotFoundException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.services.MonthlyBudgetService;

@RestController
@RequestMapping("/api/budgets")
public class MonthlyBudgetController {

    private final MonthlyBudgetRepository repository;
    private final MonthlyBudgetService service;

    @Autowired
    private MonthlyBudgetController(MonthlyBudgetRepository repository,
                                    MonthlyBudgetService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(params = { "month","year" })
    public ResponseEntity<?> getMonthlyBudget(@RequestParam("month") int month,
                                              @RequestParam("year") int year) {
        try {
            WMonthlyBudgetWebModel dto = service.findMonthlyBudgetBy(month, year);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (MonthlyBudgetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getMonthlyBudgets() {
        List<MonthlyBudget> monthlyBudgets = Optional.of(repository.findAll())
                .orElseGet(Collections::emptyList);
        return new ResponseEntity<>(monthlyBudgets, HttpStatus.OK);
    }
}