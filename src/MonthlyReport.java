import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MonthlyReport {
    // Константы для названия файлов которые можно изменить Например увеличить число месяцев
    private final int AMOUNT_MONTH = 3;
    private final String NAME_OF_FORMAT = ".csv";
    private final String NAME_OF_REPORT_FOR_MONTH = "m.";
    private final String YEAR = "20210";
    // Переменная для проверки загружен ли был файл
    public boolean fileLoad = false;

    // Мапа для продаж по месяцам
    HashMap<Integer, ArrayList<MonthlyReportData>> salesOfMonth = new HashMap<>();

    // Считывание месячных отчетов
    void loadMonthlyReport() {
        List<String> content;
        for (int i = 1; i <= AMOUNT_MONTH; i++) {
            //Задание имени файлов для чтения в цикле
            String nameReport = NAME_OF_REPORT_FOR_MONTH + YEAR + i + NAME_OF_FORMAT;
            content = readFileForReport("resources/" + nameReport);
            //Создание список по продажам для одного месяца
            ArrayList<MonthlyReportData> salesByOneMonth = new ArrayList<>();
            //Чтение и сохранения информации в файлах в соответсвующий класс
            for (int j = 1; j < content.size(); j++) {
                String sale = content.get(j);
                String[] parts = sale.split(",");
                String title = parts[0];
                boolean isExpense = Boolean.parseBoolean(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                int price = Integer.parseInt(parts[3]);

                MonthlyReportData monthlyReportData = new MonthlyReportData(title, isExpense, quantity, price);
                //Добавление продажи в список
                salesByOneMonth.add(monthlyReportData);

            }
            //Сохранение месяца и продаж в нем в Мапе
            salesOfMonth.put(i, salesByOneMonth);
        }
        // Сообщаем переменной что файл был прочитан
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
    public HashMap<Integer, Integer> findIncomesByMonthlyReport() {
        HashMap<Integer, Integer> incomes = new HashMap<>();

        for (int i = 1; i <= salesOfMonth.size(); i++) {
            int income = 0;
            ArrayList<MonthlyReportData> sales = salesOfMonth.get(i);
            for (MonthlyReportData oneSale : sales
            ) {
                if (!(oneSale.is_expense)) {
                    income += oneSale.quantity * oneSale.sum_of_one;
                }
            }
            incomes.put(i, income);
        }
        return incomes;
    }

    //Нахождение и запись расхода и сохранение в Мапе вида = Месяц, Общая сумма расхода в месяц
    public HashMap<Integer, Integer> findExpensesByMonthlyReport() {
        HashMap<Integer, Integer> expenses = new HashMap<>();

        for (int i = 1; i <= salesOfMonth.size(); i++) {
            int expense = 0;
            ArrayList<MonthlyReportData> sales = salesOfMonth.get(i);
            for (MonthlyReportData oneSale : sales
            ) {
                if (oneSale.is_expense) {
                    expense += oneSale.quantity * oneSale.sum_of_one;
                }
            }
            expenses.put(i, expense);
        }
        return expenses;
    }

    //Нахождение самого прибыльного и самого расходного товара в месяц
    public void findItem() {

        for (int i = 1; i <= salesOfMonth.size(); i++) {
            int amountItemIncome = 0;
            String itemNameIncome = "";
            int amountItemExpense = 0;
            String itemNameExpense = "";
            ArrayList<MonthlyReportData> sales = salesOfMonth.get(i);

            for (MonthlyReportData oneSale : sales
            ) {
                int income = 0;
                int expense = 0;
                if (!(oneSale.is_expense)) {
                    income = oneSale.quantity * oneSale.sum_of_one;
                } else {
                    expense = oneSale.quantity * oneSale.sum_of_one;
                }
                if (income > amountItemIncome) {
                    amountItemIncome = income;
                    itemNameIncome = oneSale.item_name;
                }
                if (expense > amountItemExpense) {
                    amountItemExpense = expense;
                    itemNameExpense = oneSale.item_name;
                }
            }
            System.out.println(setMonthName(i));
            System.out.println("Самый прибыльный товар: \"" + itemNameIncome + "\" Продано на сумму:" + amountItemIncome);
            System.out.println("Самая большая трата на товар: \"" + itemNameExpense + "\" потрачено на сумму:" + amountItemExpense);
        }


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
