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
    private String title;

    @OneToMany(
            mappedBy = "monthlyBudget",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MonthlyBudgetCategory> monthlyBudgetCategories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "monthly_income_id")
    private MonthlyIncome monthlyIncome;


    public void addMonthlyBudgetCategory(MonthlyBudgetCategory monthlyBudgetCategory) {
        monthlyBudgetCategory.setMonthlyBudget(this);
        monthlyBudgetCategories.add(monthlyBudgetCategory);
    }

    public void removeMonthlyBudgetCategory(MonthlyBudgetCategory monthlyBudgetCategory) {
        monthlyBudgetCategories.remove(monthlyBudgetCategory);
        monthlyBudgetCategory.setMonthlyBudget(null);
    }

    public int getMonthlyLimit() {
        if (monthlyBudgetCategories.isEmpty())
            return 0;

        return monthlyBudgetCategories.stream()
                .mapToInt(MonthlyBudgetCategory::getMonthlyLimit)
                .reduce(0, Integer::sum);
    }
}