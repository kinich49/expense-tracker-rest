package mx.kinich49.expensetracker.controllers;

import java.time.LocalDate;
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

import mx.kinich49.expensetracker.models.MonthlyBudget;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;

@RestController
@RequestMapping("/api/budgets")
public class MonthlyBudgetController {

    private MonthlyBudgetRepository repository;

    @Autowired
    private MonthlyBudgetController(MonthlyBudgetRepository repository) {
        this.repository = repository;
    }

    @GetMapping(params = { "date" })
    public ResponseEntity<?> getMonthlyBudget(@RequestParam("date") LocalDate date) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getMonthlyBudgets() {
        List<MonthlyBudget> monthlyBudgets = Optional.ofNullable(repository.findAll())
                .orElseGet(() -> Collections.emptyList());
        return new ResponseEntity<>(monthlyBudgets, HttpStatus.OK);
    }
}