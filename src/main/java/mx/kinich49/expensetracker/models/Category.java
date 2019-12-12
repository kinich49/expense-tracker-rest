package mx.kinich49.expensetracker.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity(name = "Categories")
@SuppressWarnings("unused")
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
    generator = "native")
    @GenericGenerator(name = "native",
    strategy = "native")
    private long id;

    @NotNull
    private String title;

    private String color;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    List<TransactionItem> transactionItems;
    
}