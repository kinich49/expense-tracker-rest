package mx.kinich49.expensetracker.exceptions;

public class CategoryNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CategoryNotFoundException(long categoryId) {
        super("Category with id " + categoryId + " not found");
    }

}