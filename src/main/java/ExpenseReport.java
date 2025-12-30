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
        int total = 0;
        for (Expense expense : expenses) {
            total += expense.amount;
        }
        return total;
    }

    private static int getMealExpenses(List<Expense> expenses) {
        int mealExpenses = 0;
        for (Expense expense : expenses) {
            mealExpenses = calculateExpenseMeal(expense, mealExpenses);
        }
        return mealExpenses;
    }

    private static void print(List<Expense> expenses, int mealExpenses, int total) {
        System.out.println("Expenses " + new Date());
        printExpensesDetails(expenses);
        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);
    }

    private static void printExpensesDetails(List<Expense> expenses) {
        String mealOverExpensesMarker;
        for (Expense expense : expenses) {
            String expenseName = getExpenseName(expense);
            mealOverExpensesMarker = isOverExpensesAmountMeal(expense) ? "X" : " ";
            System.out.println(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);
        }
    }

    private static boolean isOverExpensesAmountMeal(Expense expense) {
        return expense.type == ExpenseType.DINNER && expense.amount > 5000 || expense.type == ExpenseType.BREAKFAST && expense.amount > 1000;
    }

    private static int calculateExpenseMeal(Expense expense, int mealExpenses) {
        if (isExpenseMeal(expense)) {
            mealExpenses += expense.amount;
        }
        return mealExpenses;
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
}