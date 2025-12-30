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

    @Given("I have a dinner of {int}")
    public void createDinnerOnly(int dinnerAmount) {
        this.dinnerAmount = dinnerAmount;
        this.breakfastAmount = 0;
        this.carAmount = 0;

        Expense dinner = new Expense();
        dinner.type = ExpenseType.DINNER;
        dinner.amount = dinnerAmount;

        expenses.clear();
        expenses.add(dinner);
    }

    @Given("I have only dinner over expenses {int}")
    public void createDinnerOverExpenses(int dinnerAmt) {
        createDinnerOnly(dinnerAmt);
    }

    @Given("I have only breakfast over expenses {int}")
    public void createBreakfastOverExpenses(int breakfastAmt) {
        createBreakfastOnly(breakfastAmt);
    }

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

    @Then("the report should contain only dinner")
    public void verifyDinnerOnly() {
        verifyReportStructure();

        assertTrue(printedOutput.contains("Dinner\t" + dinnerAmount + "\t "),
                "Expected dinner: " + dinnerAmount);
        assertFalse(printedOutput.contains("Breakfast\t"),
                "Should not contain breakfast");
        assertFalse(printedOutput.contains("Car Rental\t"),
                "Should not contain car rental");

        assertTrue(printedOutput.contains("Meal expenses: " + dinnerAmount),
                "Meal expenses should equal dinner amount");
        assertTrue(printedOutput.contains("Total expenses: " + dinnerAmount),
                "Total expenses should equal dinner amount");
    }

    @Then("the report should contain only dinner Over expenses")
    public void verifyDinnerOverExpenses() {
        verifyReportStructure();

        assertTrue(printedOutput.contains("Dinner\t" + dinnerAmount + "\t" + "X"),
                "Expected dinner: " + dinnerAmount + "with X");
        assertFalse(printedOutput.contains("Breakfast\t"),
                "Should not contain breakfast");
        assertFalse(printedOutput.contains("Car Rental\t"),
                "Should not contain car rental");

        assertTrue(printedOutput.contains("Meal expenses: " + dinnerAmount),
                "Meal expenses should equal dinner amount");
        assertTrue(printedOutput.contains("Total expenses: " + dinnerAmount),
                "Total expenses should equal dinner amount");
    }

    @Then("the report should contain only breakfast Over expenses")
    public void verifyBreakfastOverExpenses() {
        verifyReportStructure();

        assertTrue(printedOutput.contains("Breakfast\t" + breakfastAmount + "\t" + "X"),
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

    @Then("the report should contain only dinner at expense limit")
    public void verifyDinnerAtLimit() {
        verifyReportStructure();

        assertTrue(printedOutput.contains("Dinner\t" + dinnerAmount + "\t "),
                "Dinner at exactly 5000 should NOT have X marker");

        assertTrue(printedOutput.contains("Meal expenses: " + dinnerAmount));
        assertTrue(printedOutput.contains("Total expenses: " + dinnerAmount));
    }

    @Then("the report should contain only breakfast at expense limit")
    public void verifyBreakfastAtLimit() {
        verifyReportStructure();

        assertTrue(printedOutput.contains("Breakfast\t" + breakfastAmount + "\t "),
                "Breakfast at exactly 1000 should NOT have X marker");

        assertTrue(printedOutput.contains("Meal expenses: " + breakfastAmount));
        assertTrue(printedOutput.contains("Total expenses: " + breakfastAmount));
    }

    // Common verification helpers
    private void verifyReportStructure() {
        assertNotNull(printedOutput, "Output should not be null");
        assertFalse(printedOutput.isEmpty(), "Output should not be empty");
        assertTrue(printedOutput.startsWith("Expenses "), "Report should start with 'Expenses'");
    }

}
