import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {

    HashMap<Integer, Integer> expense=new HashMap<>();
    HashMap<Integer, Integer> income=new HashMap<>();

    ArrayList<ItemsByMonth> items;

    HashMap<Integer, Integer> yearlyProfit=new HashMap<>();;
    int expensesAvg, incomeAvg;

    public YearlyReport(ArrayList<ItemsByMonth> items) {
        this.items = items;
    }

    //Нахождение и запись дохода и сохранение в Мапе вида = Месяц, Общая сумма дохода в месяц
    public void findGeneralExpenses() {
        for (ItemsByMonth items : items
        ) {
            if (items.isExpense) {

                expense.put(items.month, items.amount);
            }
        }
    }

    //Нахождение и запись расхода и сохранение в Мапе вида = Месяц, Общая сумма расхода в месяц
    public void findGeneralIncome() {
        for (ItemsByMonth items : items
        ) {
            if (!(items.isExpense)) {
                income.put(items.month, items.amount);
            }
        }
    }

    //Получение и печать прибыли по месяцу
    public void getYearlyProfit() {
        for (int i = 1; i <= income.size(); i++) {
            int profit = income.get(i) - expense.get(i);
            yearlyProfit.put(i, profit);
        }
    }

    //Получение и печать среднего дохода за все месяцы в год
    public void getYearlyIncomeAvg() {
        int sumIncome = 0;
        for (int i = 1; i <= income.size(); i++) {
            sumIncome += income.get(i);
        }
        incomeAvg = sumIncome / income.size();
    }

    //Получение и печать среднего расхода за все месяцы в год
    public void getYearlyExpensesAvg() {
        int sumExpenses = 0;
        for (int i = 1; i <= expense.size(); i++) {
            sumExpenses += expense.get(i);
        }
        expensesAvg = sumExpenses / expense.size();
    }

}
