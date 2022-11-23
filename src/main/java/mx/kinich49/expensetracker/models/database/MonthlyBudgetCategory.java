package mx.kinich49.expensetracker.models.database;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Monthly_Budget_Categories")
@Getter
@Setter
public class MonthlyBudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int monthlyLimit;

    @ManyToOne
    @JoinColumn(name = "monthly_budget_id")
    private MonthlyBudget monthlyBudget;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyBudgetCategory that = (MonthlyBudgetCategory) o;
        return monthlyLimit == that.monthlyLimit &&
                Objects.equals(id, that.id) &&
                Objects.equals(monthlyBudget, that.monthlyBudget) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, monthlyLimit, monthlyBudget, category);
    }

    @Override
    public String toString() {
        return "MonthlyBudgetCategory{" +
                "id=" + id +
                ", monthlyLimit=" + monthlyLimit +
                ", category=" + category +
                '}';
    }
}