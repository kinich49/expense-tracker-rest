package mx.kinich49.expensetracker.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "Monthly_Budgets")
@EqualsAndHashCode(of = {"id", "title"})
public class MonthlyBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @Column
    private String title;

    @Column
    private LocalDate budgetDate;
    
    @OneToMany(
        mappedBy = "monthlyBudget",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonIgnore
    private Set<MonthlyBudgetCategory> monthlyBudgetCategories = new HashSet<>();

    public void addCategory(Category category) {
        MonthlyBudgetCategory monthlyBudgetCategory = new MonthlyBudgetCategory();
        monthlyBudgetCategory.setMonthlyBudget(this);
        monthlyBudgetCategory.setCategory(category);
        monthlyBudgetCategories.add(monthlyBudgetCategory);
        category.getMonthlyBudgetCategories().add(monthlyBudgetCategory);
    }
 
    public void removeCategory(Category category) {
        for (Iterator<MonthlyBudgetCategory> iterator = monthlyBudgetCategories.iterator();
             iterator.hasNext(); ) {
            MonthlyBudgetCategory  monthlyBudgetCategory = iterator.next();
 
            if (monthlyBudgetCategory.getMonthlyBudget().equals(this) &&
                    monthlyBudgetCategory.getCategory().equals(category)) {
                iterator.remove();
                monthlyBudgetCategory.getCategory().getMonthlyBudgetCategories().remove(monthlyBudgetCategory);
                monthlyBudgetCategory.setMonthlyBudget(null);
                monthlyBudgetCategory.setCategory(null);
            }
        }
    }
}