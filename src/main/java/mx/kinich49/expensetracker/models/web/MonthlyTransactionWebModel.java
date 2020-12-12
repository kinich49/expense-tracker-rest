package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Transaction;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MonthlyTransactionWebModel {

    private final long transactionId;
    private final String title;
    private final String memo;
    private final long amount;
    private final LocalDate dateCreated;

    public static MonthlyTransactionWebModel from(Transaction transaction) {
        if (transaction == null)
            return null;

        return new MonthlyTransactionWebModel(transaction.getId(),
                transaction.getTitle(),
                transaction.getMemo(),
                transaction.getAmount(),
                transaction.getDateCreated());
    }

    public static List<MonthlyTransactionWebModel> from(Collection<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty())
            return null;

        return transactions.stream()
                .map(MonthlyTransactionWebModel::from)
                .collect(Collectors.toList());
    }
}