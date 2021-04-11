package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity(name = "Monthly_Incomes")
public class MonthlyIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column
    private int upperIncomeLimit;

    @Column
    @NotNull
    private boolean active;

//    @OneToMany(
//            mappedBy = "monthlyIncome",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<MonthlyBudget> monthlyBudgets = new ArrayList<>();
//
//    public void addMonthlyBudget(MonthlyBudget monthlyBudget) {
//        monthlyBudget.setMonthlyIncome(this);
//        monthlyBudgets.add(monthlyBudget);
//    }
//
//    public void removeMonthlyBudgetCategory(MonthlyBudget monthlyBudget) {
//        monthlyBudgets.remove(monthlyBudget);
//        monthlyBudget.setMonthlyIncome(null);
//    }
}
