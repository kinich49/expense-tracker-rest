package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne(optional = false)
    @JoinColumn(name = "commercial_establishment_id")
    private CommercialEstablishment commercialEstablishment;

    @NotNull
    private String title;

    private String memo;

    @NotNull
    private int amount;

    @NotNull
    private LocalDateTime transactionDate;

    public static Transaction copy(Transaction transaction) {
        if (transaction == null)
            return null;

        Transaction copy = new Transaction();
        copy.amount = transaction.amount;
        copy.id = transaction.id;
        copy.transactionDate = transaction.transactionDate;
        copy.memo = transaction.memo;
        copy.title = transaction.title;

        copy.category = Category.copy(transaction.category);
        copy.commercialEstablishment = CommercialEstablishment.copy(transaction.commercialEstablishment);
        copy.paymentMethod = PaymentMethod.copy(transaction.paymentMethod);

        return copy;
    }
}