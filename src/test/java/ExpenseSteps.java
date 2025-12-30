import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseSteps {

    private final List<Expense> expenses = new ArrayList<>();
    private final ExpenseReport report = new ExpenseReport();
    private String printedOutput;

    // Store the input values for verification
    private int breakfastAmount;
    private int dinnerAmount;
    private int carAmount;


    @When("I print the expense report")
    public void printExpense() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        report.printReport(expenses);

        System.setOut(original);
        printedOutput = out.toString();
        System.out.println(printedOutput);
    }

    @Given("I have a breakfast of {int} and a dinner of {int} and a car rental of {int}")
    public void createFullExpanses(int breakfastAmt, int dinnerAmt, int carAmt) {
        // Store for later verification
        this.breakfastAmount = breakfastAmt;
        this.dinnerAmount = dinnerAmt;
        this.carAmount = carAmt;

        Expense breakfast = new Expense();
        breakfast.type = ExpenseType.BREAKFAST;
        breakfast.amount = breakfastAmt;

        Expense dinner = new Expense();
        dinner.type = ExpenseType.DINNER;
        dinner.amount = dinnerAmt;

        Expense car = new Expense();
        car.type = ExpenseType.CAR_RENTAL;
        car.amount = carAmt;

        expenses.clear();
        expenses.add(breakfast);
        expenses.add(dinner);
        expenses.add(car);
    }

    @Then("the report should contain all expenses")
    public void verifyAllExpenses() {
        verifyReportStructure();

        assertTrue(printedOutput.contains("Breakfast\t" + breakfastAmount + "\t "),
                "Expected breakfast: " + breakfastAmount);
        assertTrue(printedOutput.contains("Dinner\t" + dinnerAmount + "\t "),
                "Expected dinner: " + dinnerAmount);
        assertTrue(printedOutput.contains("Car Rental\t" + carAmount + "\t "),
                "Expected car rental: " + carAmount);

        int expectedMealExpenses = breakfastAmount + dinnerAmount;
        assertTrue(printedOutput.contains("Meal expenses: " + expectedMealExpenses),
                "Expected meal expenses: " + expectedMealExpenses);

        int expectedTotal = breakfastAmount + dinnerAmount + carAmount;
        assertTrue(printedOutput.contains("Total expenses: " + expectedTotal),
                "Expected total expenses: " + expectedTotal);
    }

    @Given("I have a breakfast of {int}")
    public void createBreakfastOnly(int breakfastAmt) {
        this.breakfastAmount = breakfastAmt;
        this.dinnerAmount = 0;
        this.carAmount = 0;

        Expense breakfast = new Expense();
        breakfast.type = ExpenseType.BREAKFAST;
        breakfast.amount = breakfastAmt;

        expenses.clear();
        expenses.add(breakfast);
    }

    @Then("the report should contain only breakfast")
    public void verifyBreakfastOnly() {
        verifyReportStructure();

        assertTrue(printedOutput.contains("Breakfast\t" + breakfastAmount + "\t "),
                "Expected breakfast: " + breakfastAmount);
        assertFalse(printedOutput.contains("Dinner\t"),
                "Should not contain dinner");
        assertFalse(printedOutput.contains("Car Rental\t"),
                "Should not contain car rental");

        assertTrue(printedOutput.contains("Meal expenses: " + breakfastAmount),
                "Meal expenses should equal breakfast amount");
        assertTrue(printedOutput.contains("Total expenses: " + breakfastAmount),
                "Total expenses should equal breakfast amount");
    }

    // Common verification helpers
    private void verifyReportStructure() {
        assertNotNull(printedOutput, "Output should not be null");
        assertFalse(printedOutput.isEmpty(), "Output should not be empty");
        assertTrue(printedOutput.startsWith("Expenses "), "Report should start with 'Expenses'");
    }

}
