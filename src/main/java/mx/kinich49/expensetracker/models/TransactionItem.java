package mx.kinich49.expensetracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity(name = "TransactionItems")
@SuppressWarnings("unused")
public class TransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
    generator = "native")
    @GenericGenerator(name = "native",
    strategy = "native")
    private long id;
    private long categoryId;
    @NotNull
    private String title;
    private String memo;
    @NotNull
    private String amount;
}