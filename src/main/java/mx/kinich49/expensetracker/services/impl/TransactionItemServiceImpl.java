package mx.kinich49.expensetracker.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.kinich49.expensetracker.models.TransactionItem;
import mx.kinich49.expensetracker.repositories.TransactionItemRepository;
import mx.kinich49.expensetracker.services.TransactionItemService;

@Service
public class TransactionItemServiceImpl implements TransactionItemService {

    private TransactionItemRepository repository;

    @Autowired
    public TransactionItemServiceImpl(TransactionItemRepository repository){
        this.repository = repository;
    } 

}