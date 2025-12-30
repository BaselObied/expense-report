import java.util.Date;
import java.util.List;

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        print(expenses, getMealExpenses(expenses), getTotalExpenses(expenses));
    }

    private static int getTotalExpenses(List<Expense> expenses) {
        return expenses.stream()
                .mapToInt(expense -> expense.amount)
                .sum();
    }

    private static int getMealExpenses(List<Expense> expenses) {
        return expenses.stream()
                .filter(ExpenseReport::isExpenseMeal)
                .mapToInt(expense -> expense.amount)
                .sum();
    }

    private static void print(List<Expense> expenses, int mealExpenses, int total) {
        printOut("Expenses " + new Date());
        printExpensesDetails(expenses);
        printOut("Meal expenses: " + mealExpenses);
        printOut("Total expenses: " + total);
    }

    private static void printExpensesDetails(List<Expense> expenses) {
        expenses.forEach( expense -> {
            String expenseName = getExpenseName(expense);
            printOut(expenseName + "\t" + expense.amount + "\t" + getMealOverExpensesMarker(expense));
        });
    }

    private static String getMealOverExpensesMarker(Expense expense) {
        return isOverExpensesAmountMeal(expense) ? "X" : " ";
    }

    private static boolean isOverExpensesAmountMeal(Expense expense) {
        return isDinnerAndOverExpense(expense) || isBreakfastAndOverExpanse(expense);
    }

    private static boolean isBreakfastAndOverExpanse(Expense expense) {
        return expense.type == ExpenseType.BREAKFAST && expense.amount > 1000;
    }

    private static boolean isDinnerAndOverExpense(Expense expense) {
        return expense.type == ExpenseType.DINNER && expense.amount > 5000;
    }

    private static String getExpenseName(Expense expense) {
        return switch (expense.type) {
            case DINNER -> "Dinner";
            case BREAKFAST -> "Breakfast";
            case CAR_RENTAL -> "Car Rental";
        };
    }

    private static boolean isExpenseMeal(Expense expense) {
        return expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST;
    }

    private static void printOut(String s) {
        System.out.println(s);
    }
}