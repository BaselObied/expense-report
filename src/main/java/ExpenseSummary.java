import java.util.List;

public class ExpenseSummary {
    final int mealExpenses;
    final int totalExpenses;

    ExpenseSummary(int mealExpenses, int totalExpenses) {
        this.mealExpenses = mealExpenses;
        this.totalExpenses = totalExpenses;
    }

    private static ExpenseSummary summarize(List<Expense> expenses) {
        return new ExpenseSummary(
                getMealExpenses(expenses),
                getTotalExpenses(expenses)
        );
    }

    private static int getTotalExpenses(List<Expense> expenses) {
        return expenses.stream()
                .mapToInt(expense -> expense.amount)
                .sum();
    }

    private static int getMealExpenses(List<Expense> expenses) {
        return expenses.stream()
                .filter(ExpenseSummary::isExpenseMeal)
                .mapToInt(expense -> expense.amount)
                .sum();
    }

    private static boolean isExpenseMeal(Expense expense) {
        return expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST;
    }
}
