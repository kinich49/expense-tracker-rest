package mx.kinich49.expensetracker.models.database;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.kinich49.expensetracker.models.database.converters.YearMonthDateAttributeConverter;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.Objects;

@NoArgsConstructor
@Entity(name = "Monthly_Incomes")
@Getter
@Setter
public class MonthlyIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyIncome that = (MonthlyIncome) o;
        return upperIncomeLimit == that.upperIncomeLimit &&
                Objects.equals(id, that.id) &&
                Objects.equals(beginDate, that.beginDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, upperIncomeLimit, beginDate, endDate);
    }

    @Override
    public String toString() {
        return "MonthlyIncome{" +
                "id=" + id +
                ", upperIncomeLimit=" + upperIncomeLimit +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                '}';
    }
}
