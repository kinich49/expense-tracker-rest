package mx.kinich49.expensetracker.models.web;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.TransactionItem;

@Data
public class MonthlyTransactionItemWebModel {

    private final long transactionId;
    private final String title;
    private final String memo;
    private final long amount;
    private final LocalDate dateCreated;

    public static MonthlyTransactionItemWebModel from(@NonNull TransactionItem transactionItem) {
        return new MonthlyTransactionItemWebModel(transactionItem.getId(), transactionItem.getTitle(),
                transactionItem.getMemo(), transactionItem.getAmount(), transactionItem.getDateCreated());
    }
}