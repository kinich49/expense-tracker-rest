package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "Categories")
@EqualsAndHashCode(of = {"id", "name"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String color;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<MonthlyBudgetCategory> monthlyBudgetCategories;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL)
    List<Transaction> transactions;

    public void addMonthlyBudgetCategory(MonthlyBudgetCategory monthlyBudgetCategory) {
        monthlyBudgetCategory.setCategory(this);
        monthlyBudgetCategories.add(monthlyBudgetCategory);
    }

    public void removeMonthlyBudgetCategory(MonthlyBudgetCategory monthlyBudgetCategory) {
        monthlyBudgetCategories.remove(monthlyBudgetCategory);
        monthlyBudgetCategory.setCategory(null);
    }

    public static Category from(CategoryRequest request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName());
        category.setColor(request.getColor());

        return category;
    }

    public static Category copy(Category category) {
        if (category == null)
            return null;

        Category copy = new Category();
        copy.color = category.color;
        copy.name = category.name;
        copy.id = category.id;

        return copy;
    }
}