import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Engine {
    // Константы для названия файлов которые можно изменить Например увеличить число месяцев
    private final int AMOUNT_MONTH = 3;
    private final String NAME_OF_FORMAT = ".csv";
    private final String NAME_OF_REPORT_FOR_MONTH = "m.";
    private final String YEAR = "2021";
    // Переменная для проверки загружен ли был файл
    public boolean fileLoadMonthly = false;

    private final String NAME_OF_REPORT_FOR_YEAR = "y.";

    // Переменная для проверки загружен ли был файл
    public boolean fileLoadYearly = false;

    // Мапа по месяцам
    HashMap<Integer, MonthlyReport> monthlyReportHashMap = new HashMap<>();
    HashMap<Integer, YearlyReport> yearlyReportHashMap = new HashMap<>();

    // Считывание месячных отчетов
    void loadMonthlyReport() {
        List<String> content;
        for (int i = 1; i <= AMOUNT_MONTH; i++) {
            //Задание имени файлов для чтения в цикле
            String nameReport = NAME_OF_REPORT_FOR_MONTH + YEAR + "0" + i + NAME_OF_FORMAT;
            content = readFileForReport("resources/" + nameReport);
            //Создание список по продажам для одного месяца
            ArrayList<Item> items = new ArrayList<>();
            //Чтение и сохранения информации в файлах в соответсвующий класс
            for (int j = 1; j < content.size(); j++) {
                String sale = content.get(j);
                String[] parts = sale.split(",");
                String itemName = parts[0];
                boolean isExpense = Boolean.parseBoolean(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                int sumOfOne = Integer.parseInt(parts[3]);

                Item item = new Item(itemName, isExpense, quantity, sumOfOne);
                //Добавление продажи в список
                items.add(item);
            }
            MonthlyReport monthlyReport = new MonthlyReport(items);
            monthlyReport.findItem();
            monthlyReport.findExpenseByMonthlyReport();
            monthlyReport.findIncomeByMonthlyReport();
            //Сохранение месяца и продаж в нем в Мапе
            monthlyReportHashMap.put(i, monthlyReport);
        }

        // Сообщаем переменной что файл был прочитан
        fileLoadMonthly = true;
    }

    // Считывание годовых отчетов
    void loadYearlyReport() {
        //Задание имени файлов для чтения в цикле
        List<String> content;
        String nameReport = NAME_OF_REPORT_FOR_YEAR + YEAR + NAME_OF_FORMAT;
        content = readFileForReport("resources/" + nameReport);
        ArrayList<ItemsByMonth> itemsByMonths = new ArrayList<>();
//Чтение и сохранения информации в файлах в соответсвующий класс
        for (int i = 1; i < content.size(); i++) {
            String sale = content.get(i);
            String[] parts = sale.split(",");
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean is_expense = Boolean.parseBoolean(parts[2]);
            ItemsByMonth itemsByMonth = new ItemsByMonth(month, amount, is_expense);
            itemsByMonths.add(itemsByMonth);
        }
        YearlyReport yearlyReport = new YearlyReport(itemsByMonths);
        yearlyReport.findGeneralExpenses();
        yearlyReport.findGeneralIncome();
        yearlyReport.getYearlyProfit();
        yearlyReport.getYearlyIncomeAvg();
        yearlyReport.getYearlyExpensesAvg();
        yearlyReportHashMap.put(Integer.parseInt(YEAR), yearlyReport);
        fileLoadYearly = true;
    }

    public void printMonthlyInfo() {
        for (int i = 1; i <= monthlyReportHashMap.size(); i++) {
            MonthlyReport monthlyReport = monthlyReportHashMap.get(i);
            System.out.println(setMonthName(i));
            System.out.println("Самый прибыльный товар: \""
                    + monthlyReport.mostProfitableItem
                    + "\" Продано на сумму:" + monthlyReport.mostProfitableItemAmount);
            System.out.println("Самая большая трата на товар: \""
                    + monthlyReport.biggestSpendingItem + "\" потрачено на сумму:"
                    + monthlyReport.biggestSpendingItemAmount);
        }
    }

    public void printYearlyInfo() {
        for (Integer year : yearlyReportHashMap.keySet()
        ) {
            System.out.println(year);
            YearlyReport yearlyReport = yearlyReportHashMap.get(year);
            for (int i = 1; i <= yearlyReport.yearlyProfit.size(); i++) {
                System.out.println(setMonthName(i));
                System.out.println("Прибыль: " + yearlyReport.yearlyProfit.get(i));
            }
            System.out.println("Средний доход за все месяцы в год: " + yearlyReport.incomeAvg);
            System.out.println("Средний расход за все месяцы в году: " + yearlyReport.expensesAvg);
        }

    }

    //Проверка был ли считаны файлы
    public boolean checkLoad() {
        if (!(this.fileLoadYearly) && !(this.fileLoadMonthly)) {
            System.out.println("Считайте отчеты");
            return false;
        } else {
            return true;
        }
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

    public static String setMonthName(int monthNumber) {
        if (monthNumber == 1) {
            return "Январь";
        } else if (monthNumber == 2) {
            return "Февраль";
        } else {
            return "Март";
        }
    }

    //Сверка отчетов
    public void checkReports() {
        checkIncome();
        checkExpenses();
    }

    //Сверка отчетов по доходу
    public void checkIncome() {
        boolean errorMassage = false;
        for (Integer year : yearlyReportHashMap.keySet()
        ) {
            YearlyReport yearlyReport = yearlyReportHashMap.get(year);
            for (int i = 1; i <= monthlyReportHashMap.size(); i++) {
                if (monthlyReportHashMap.get(i).income == (yearlyReport.income.get(i))) {
                    errorMassage = true;
                } else {
                    System.out.println("Ошибока в при сверке отчетов дохода в " + setMonthName(i));
                }
            }
            if (errorMassage) {
                System.out.println("Сверка отчетов по доходам успешна");
            }
        }
    }

    //Сверка отчетов по расходу
    public void checkExpenses() {
        boolean errorMassage = false;
        for (Integer year : yearlyReportHashMap.keySet()
        ) {
            YearlyReport yearlyReport = yearlyReportHashMap.get(year);
            for (int i = 1; i <= monthlyReportHashMap.size(); i++) {
                if (monthlyReportHashMap.get(i).expense == (yearlyReport.expense.get(i))) {
                    errorMassage = true;
                } else {
                    System.out.println("Ошибока в при сверке расходов дохода в " + setMonthName(i));
                }
            }
            if (errorMassage) {
                System.out.println("Сверка отчетов по расходам успешна");
            }
        }
    }
}
