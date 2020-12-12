package mx.kinich49.expensetracker.models.database;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable 
public class MonthlyBudgetCategoryId implements Serializable{

    @Column
    private long monthlyBudgetId;
    @Column
    private long categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        MonthlyBudgetCategoryId that = (MonthlyBudgetCategoryId) o;
        return Objects.equals(monthlyBudgetId, that.monthlyBudgetId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monthlyBudgetId, categoryId);
    }
}