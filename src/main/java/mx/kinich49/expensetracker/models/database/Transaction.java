package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Transactions")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount &&
                Objects.equals(id, that.id) &&
                Objects.equals(category, that.category) &&
                Objects.equals(paymentMethod, that.paymentMethod) &&
                Objects.equals(commercialEstablishment, that.commercialEstablishment) &&
                Objects.equals(title, that.title) &&
                Objects.equals(memo, that.memo) &&
                Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, paymentMethod, commercialEstablishment, title, memo, amount, transactionDate);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", category=" + category +
                ", paymentMethod=" + paymentMethod +
                ", commercialEstablishment=" + commercialEstablishment +
                ", title='" + title + '\'' +
                ", memo='" + memo + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}