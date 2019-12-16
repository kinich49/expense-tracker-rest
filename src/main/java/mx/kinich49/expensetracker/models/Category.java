package mx.kinich49.expensetracker.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "Categories")
@EqualsAndHashCode(of = {"id", "title"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @NotNull
    private String title;

    private String color;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<TransactionItem> transactionItems;

    @JsonIgnore
    @OneToMany(mappedBy = "category",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<MonthlyBudgetCategory> monthlyBudgetCategories;
}