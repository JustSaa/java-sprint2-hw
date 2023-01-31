import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class YearlyReport {
    private final String NAME_OF_FORMAT = ".csv";
    private final String NAME_OF_REPORT_FOR_YEAR = "y.";
    private final String YEAR = "2021";
    // Переменная для проверки загружен ли был файл
    public boolean fileLoad = false;
    ArrayList<YearlyReportData> yearlyReport = new ArrayList<>();

    // Считывание годовых отчетов
    void loadYearlyReport() {
        //Задание имени файлов для чтения в цикле
        List<String> content;
        String nameReport = NAME_OF_REPORT_FOR_YEAR + YEAR + NAME_OF_FORMAT;
        content = readFileForReport("resources/" + nameReport);
//Чтение и сохранения информации в файлах в соответсвующий класс
        for (int i = 1; i < content.size(); i++) {
            String sale = content.get(i);
            String[] parts = sale.split(",");
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean is_expense = Boolean.parseBoolean(parts[2]);
            YearlyReportData yearlyReportData = new YearlyReportData(month, amount, is_expense);
            yearlyReport.add(yearlyReportData);
        }
        fileLoad = true;
    }

    //Чтение файла  (дан в ТЗ)
    List<String> readFileForReport(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }

    //Нахождение и запись дохода и сохранение в Мапе вида = Месяц, Общая сумма дохода в месяц
    public HashMap<Integer, Integer> findGeneralExpenses() {
        HashMap<Integer, Integer> expensesByMonth = new HashMap<>();
        for (YearlyReportData salesByMonth : yearlyReport
        ) {
            if (salesByMonth.is_expense) {
                expensesByMonth.put(salesByMonth.month, salesByMonth.amount);
            }
        }
        return expensesByMonth;
    }

    //Нахождение и запись расхода и сохранение в Мапе вида = Месяц, Общая сумма расхода в месяц
    public HashMap<Integer, Integer> findGeneralIncome() {
        HashMap<Integer, Integer> incomesYear = new HashMap<>();
        for (YearlyReportData salesByMonth : yearlyReport
        ) {
            if (!(salesByMonth.is_expense)) {
                incomesYear.put(salesByMonth.month, salesByMonth.amount);
            }
        }
        return incomesYear;
    }

    //Печать информации о годе
    public void printYearlyInfo() {
        System.out.println("Год: " + YEAR);

        HashMap<Integer, Integer> yearlyExpenses = findGeneralExpenses();
        HashMap<Integer, Integer> yearlyIncomes = findGeneralIncome();

        getYearlyProfit(yearlyExpenses, yearlyIncomes);
        getYearlyIncomeAvg(yearlyIncomes);
        getYearlyExpensesAvg(yearlyExpenses);
    }

    //Получение и печать прибыли по месяцу
    public void getYearlyProfit(HashMap<Integer, Integer> yearlyExpenses, HashMap<Integer, Integer> yearlyIncomes) {
        for (int i = 1; i <= yearlyIncomes.size(); i++) {
            int profit = yearlyIncomes.get(i) - yearlyExpenses.get(i);
            System.out.println(setMonthName(i));
            System.out.println("Прибыль: " + profit);
        }
    }

    //Получение и печать среднего дохода за все месяцы в год
    public void getYearlyIncomeAvg(HashMap<Integer, Integer> yearlyIncomes) {
        int sumIncome = 0;
        for (int i = 1; i <= yearlyIncomes.size(); i++) {
            sumIncome += yearlyIncomes.get(i);
        }
        int avg = sumIncome / yearlyIncomes.size();
        System.out.println("Средний доход за все месяцы в год: " + avg);
    }

    //Получение и печать среднего расхода за все месяцы в год
    public void getYearlyExpensesAvg(HashMap<Integer, Integer> yearlyExpenses) {
        int sumExpenses = 0;
        for (int i = 1; i <= yearlyExpenses.size(); i++) {
            sumExpenses += yearlyExpenses.get(i);
        }
        int avg = sumExpenses / yearlyExpenses.size();
        System.out.println("Средний расход за все месяцы в году: " + avg);
    }

    //Запись месяца
    public String setMonthName(int monthNumber) {
        String monthName;
        if (monthNumber == 1) {
            return monthName = "Январь";
        } else if (monthNumber == 2) {
            return monthName = "Февраль";
        } else {
            return monthName = "Март";
        }
    }

}
