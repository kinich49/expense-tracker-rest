package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.kinich49.expensetracker.models.database.converters.YearMonthDateAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Monthly_Budgets")
@EqualsAndHashCode(of = {"id", "beginDate", "endDate"})
@ToString(exclude = {"monthlyBudgetCategories"})
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

    @OneToMany(
            mappedBy = "monthlyBudget",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MonthlyBudgetCategory> monthlyBudgetCategories = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "monthly_income_id")
//    private MonthlyIncome monthlyIncome;

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