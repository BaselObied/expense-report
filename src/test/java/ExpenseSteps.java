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

    @Given("I have a breakfast of {int} and a dinner of {int} and a car rental of {int}")
    public void createExpense(int breakfastAmt, int dinnerAmt, int carAmt) {
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

    @When("I print the expense report")
    public void printExpense() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        report.printReport(expenses);

        System.setOut(original);
        printedOutput = out.toString();
    }

    @Then("the report should be printed")
    public void verifyPrinted() {
        assertEquals("", printedOutput);
    }
}
