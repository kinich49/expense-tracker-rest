package mx.kinich49.expensetracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
}