package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.kinich49.expensetracker.models.database.converters.YearMonthDateAttributeConverter;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;

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
    @Convert(
            converter = YearMonthDateAttributeConverter.class
    )
    @NotNull
    private YearMonth beginDate;

    @Column
    @Convert(
            converter = YearMonthDateAttributeConverter.class
    )
    private YearMonth endDate;


    public static MonthlyIncome from(MonthlyIncomeRequest request) {
        MonthlyIncome monthlyIncome = new MonthlyIncome();

        monthlyIncome.setBeginDate(request.getBeginDate());
        monthlyIncome.setEndDate(request.getEndDate());
        monthlyIncome.setUpperIncomeLimit(request.getUpperIncomeLimit());

        return monthlyIncome;
    }
}
