package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MonthlyTransactionWebModel {

    private final long transactionId;
    private final String title;
    private final String memo;
    private final String amount;
    private final LocalDateTime dateCreated;

    public static MonthlyTransactionWebModel from(Transaction transaction) {
        if (transaction == null)
            return null;

        String formattedAmount = StringUtils.formatCurrencyNumber(transaction.getAmount(), "MXN");
        return new MonthlyTransactionWebModel(transaction.getId(),
                transaction.getTitle(),
                transaction.getMemo(),
                formattedAmount,
                transaction.getTransactionDate());
    }

    public static List<MonthlyTransactionWebModel> from(Collection<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty())
            return null;

        return transactions.stream()
                .map(MonthlyTransactionWebModel::from)
                .collect(Collectors.toList());
    }
}