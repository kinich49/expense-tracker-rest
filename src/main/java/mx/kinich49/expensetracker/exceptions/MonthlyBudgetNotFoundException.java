package mx.kinich49.expensetracker.exceptions;

public class MonthlyBudgetNotFoundException extends Exception {

    public MonthlyBudgetNotFoundException(int month, int year) {
        super("Budget not found for " + getMonthName(month) + " " + year);
    }

    public MonthlyBudgetNotFoundException(long id) {
        super("Budget with id " + id + " not found");
    }

    /**
     * @param month the Month of the year, from 1 to 12
     * @return the name of the month
     */
    public static String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }
}