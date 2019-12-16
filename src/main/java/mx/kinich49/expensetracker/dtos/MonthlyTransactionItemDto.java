package mx.kinich49.expensetracker.dtos;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

import lombok.Data;
import mx.kinich49.expensetracker.models.TransactionItem;

@Data
public class MonthlyTransactionItemDto {

    private final long transactionId;
    private final String title;
    private final String memo;
    private final long amount;
    private final LocalDate dateCreated;

    public static MonthlyTransactionItemDto from(@NonNull TransactionItem transactionItem) {
        return new MonthlyTransactionItemDto(transactionItem.getId(), transactionItem.getTitle(),
                transactionItem.getMemo(), transactionItem.getAmount(), transactionItem.getDateCreated());
    }
}