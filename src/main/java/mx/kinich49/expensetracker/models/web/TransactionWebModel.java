package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Transaction;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TransactionWebModel {

    private final Long id;
    private final String title;
    private final String memo;
    private final int amount;
    private final LocalDateTime transactionDate;
    private final PaymentMethodWebModel paymentMethod;
    private final CategoryWebModel category;

    public static TransactionWebModel from(Transaction transaction) {
        if (transaction == null)
            return null;

        CategoryWebModel category = CategoryWebModel.from(transaction.getCategory());
        PaymentMethodWebModel paymentMethod = PaymentMethodWebModel.from(transaction.getPaymentMethod());
        return new TransactionWebModel(
                transaction.getId(),
                transaction.getTitle(),
                transaction.getMemo(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                paymentMethod,
                category
        );
    }

    public static List<TransactionWebModel> from(Collection<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty())
            return null;

        return transactions.stream()
                .map(TransactionWebModel::from)
                .collect(Collectors.toList());
    }
}
