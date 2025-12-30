import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER, BREAKFAST, CAR_RENTAL
}

class Expense {
    ExpenseType type;
    int amount;
}

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
        String mealOverExpensesMarker;
        for (Expense expense : expenses) {
            String expenseName = getExpenseName(expense);
            mealOverExpensesMarker = isOverExpensesAmountMeal(expense) ? "X" : " ";
            printOut(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);
        }
    }

    private static boolean isOverExpensesAmountMeal(Expense expense) {
        return expense.type == ExpenseType.DINNER && expense.amount > 5000 || expense.type == ExpenseType.BREAKFAST && expense.amount > 1000;
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