package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "Monthly_Budgets")
@EqualsAndHashCode(of = {"id", "budgetDate"})
@ToString(exclude = {"monthlyBudgetCategories"})
public class MonthlyBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column
    private LocalDate budgetDate;

    @Column
    private int monthlyLimit;

    @OneToMany(
            mappedBy = "monthlyBudget",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MonthlyBudgetCategory> monthlyBudgetCategories = new ArrayList<>();

    public void addMonthlyBudgetCategory(MonthlyBudgetCategory monthlyBudgetCategory) {
        monthlyBudgetCategory.setMonthlyBudget(this);
        monthlyBudgetCategories.add(monthlyBudgetCategory);
    }

    public void removeMonthlyBudgetCategory(MonthlyBudgetCategory monthlyBudgetCategory) {
        monthlyBudgetCategories.remove(monthlyBudgetCategory);
        monthlyBudgetCategory.setMonthlyBudget(null);
    }
}