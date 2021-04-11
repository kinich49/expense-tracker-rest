package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "Categories")
@EqualsAndHashCode(of = {"id", "name"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @NotNull
    private String name;

    private String color;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL)
    List<Transaction> transactions;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<MonthlyBudgetCategory> monthlyBudgetCategories;

    public void addMonthlyBudgetCategory(MonthlyBudgetCategory monthlyBudgetCategory) {
        monthlyBudgetCategory.setCategory(this);
        monthlyBudgetCategories.add(monthlyBudgetCategory);
    }

    public void removeMonthlyBudgetCategory(MonthlyBudgetCategory monthlyBudgetCategory) {
        monthlyBudgetCategories.remove(monthlyBudgetCategory);
        monthlyBudgetCategory.setCategory(null);
    }
}