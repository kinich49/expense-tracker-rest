package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.kinich49.expensetracker.models.database.converters.YearMonthDateAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Monthly_Budgets")
@EqualsAndHashCode(of = {"id", "beginDate", "endDate"})
@ToString(exclude = {"monthlyBudgetCategories"})
/*
  The monthly limit is a dynamic value, calculated
  by the sum of all the budget's category limit plus
  the budget's base limit.
 */
public class MonthlyBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column
    @Convert(
            converter = YearMonthDateAttributeConverter.class
    )
    private YearMonth beginDate;

    @Column
    @Convert(
            converter = YearMonthDateAttributeConverter.class
    )
    private YearMonth endDate;

    @Column
    private String title;

    @Column
    @NotNull
    private int baseLimit = 0;

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

    public int getMonthlyLimit() {
        if (monthlyBudgetCategories.isEmpty())
            return baseLimit;

        return monthlyBudgetCategories.stream()
                .mapToInt(MonthlyBudgetCategory::getMonthlyLimit)
                .reduce(0, Integer::sum) + baseLimit;
    }
}