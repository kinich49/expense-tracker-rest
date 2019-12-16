package mx.kinich49.expensetracker.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Monthly_Budget_Categories")
@Data
@EqualsAndHashCode(of = {"monthlyBudget", "category", "monthOfYear", "year"})
public class MonthlyBudgetCategory {

    @EmbeddedId
    private MonthlyBudgetCategoryId pk;

    @ManyToOne
    @MapsId("monthlyBudgetId")
    private MonthlyBudget monthlyBudget;

    @ManyToOne
    @MapsId("categoryId")
    private Category category;

    @Column
    private long monthlyLimit;

    @Column
    private LocalDate dateCreated;

    @Column
    private int monthOfYear;

    @Column
    private int year;

}