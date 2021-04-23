package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "Monthly_Budget_Categories")
@Data
@ToString(exclude = {"monthlyBudget", "category"})
@EqualsAndHashCode(of = {"category", "monthlyBudget"})
public class MonthlyBudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column
    private int monthlyLimit;

    @ManyToOne
    @JoinColumn(name = "monthly_budget_id")
    private MonthlyBudget monthlyBudget;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}