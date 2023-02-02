import java.util.ArrayList;

public class MonthlyReport {
    int expense;
    int income;
    String mostProfitableItem;
    String biggestSpendingItem;
    int mostProfitableItemAmount;
    int biggestSpendingItemAmount;

    ArrayList<Item> report;

    public MonthlyReport(ArrayList<Item> report) {
        this.report = report;
    }

    //Нахождение и запись дохода и сохранение в обеъте
    void findIncomeByMonthlyReport() {
        int income = 0;
        for (Item item : report
        ) {
            if (!(item.isExpense)) {
                income += item.sumOfOne * item.quantity;
            }
        }
        this.income = income;
    }

    //Нахождение и запись расхода и сохранение в обеъте
    void findExpenseByMonthlyReport() {
        int expense = 0;
        for (Item item : report
        ) {
            if (item.isExpense) {
                expense += item.sumOfOne * item.quantity;
            }
        }
        this.expense = expense;
    }

    //Нахождение самого прибыльного и самого расходного товара в месяц
    public void findItem() {
        int amountItemIncome = 0;
        String itemNameIncome = null;
        int amountItemExpense = 0;
        String itemNameExpense = null;
        for (Item item : report
        ) {
            int income = 0;
            int expense = 0;
            if (!(item.isExpense)) {
                income = item.quantity * item.sumOfOne;
            } else {
                expense = item.quantity * item.sumOfOne;
            }
            if (income > amountItemIncome) {
                amountItemIncome = income;
                itemNameIncome = item.itemName;
            }
            if (expense > amountItemExpense) {
                amountItemExpense = expense;
                itemNameExpense = item.itemName;
            }
        }
        this.biggestSpendingItem = itemNameExpense;
        this.mostProfitableItem = itemNameIncome;
        this.biggestSpendingItemAmount = amountItemExpense;
        this.mostProfitableItemAmount = amountItemIncome;

    }
}
