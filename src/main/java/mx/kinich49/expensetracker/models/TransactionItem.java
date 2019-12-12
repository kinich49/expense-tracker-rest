package mx.kinich49.expensetracker.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity(name = "Transaction_Items")
public class TransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(optional = false)
    private Category category;

    @NotNull
    private String title;

    private String memo;

    @NotNull
    private long amount;

    @NotNull
    private LocalDate dateCreated;

}