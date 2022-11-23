package mx.kinich49.expensetracker.models.database;

import lombok.*;
import mx.kinich49.expensetracker.models.database.converters.YearMonthDateAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@Entity(name = "Monthly_Budgets")
@Getter
@Setter
/*
  The monthly limit is a dynamic value, calculated
  by the sum of all the budget's category limit plus
  the budget's base limit.
 */
public class MonthlyBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyBudget that = (MonthlyBudget) o;
        return baseLimit == that.baseLimit &&
                Objects.equals(id, that.id) &&
                Objects.equals(beginDate, that.beginDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginDate, endDate, title, baseLimit);
    }

    @Override
    public String toString() {
        return "MonthlyBudget{" +
                "id=" + id +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", baseLimit=" + baseLimit +
                '}';
    }
}