package mx.kinich49.expensetracker.exceptions;

public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException(long categoryId){
        super("Category with id " + categoryId + " not found");
    }

}