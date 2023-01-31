import java.util.HashMap;

public class ReportChecker {

    public YearlyReport yearlyReport;
    public MonthlyReport monthlyReport;

    public ReportChecker(YearlyReport yearlyReport, MonthlyReport monthlyReport) {
        this.yearlyReport = yearlyReport;
        this.monthlyReport = monthlyReport;
    }

    //Проверка был ли считаны файлы
    public boolean checkLoad() {
        if (!(yearlyReport.fileLoad) || !(monthlyReport.fileLoad)) {
            System.out.println("Считайте отчеты");
            return false;
        } else {
            return true;
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
        HashMap<Integer, Integer> monthlyIncomes = monthlyReport.findIncomesByMonthlyReport();
        HashMap<Integer, Integer> yearlyGeneralIncome = yearlyReport.findGeneralIncome();
        if (monthlyIncomes.size() == yearlyGeneralIncome.size()) {
            for (int i = 1; i < monthlyIncomes.size(); i++) {
                if (monthlyIncomes.get(i).equals(yearlyGeneralIncome.get(i))) {
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
        HashMap<Integer, Integer> monthlyExpenses = monthlyReport.findExpensesByMonthlyReport();
        HashMap<Integer, Integer> yearlyExpenses = yearlyReport.findGeneralExpenses();
        if (monthlyExpenses.size() == yearlyExpenses.size()) {
            for (int i = 1; i < monthlyExpenses.size(); i++) {
                if (monthlyExpenses.get(i).equals(yearlyExpenses.get(i))) {
                    errorMassage = true;
                } else {
                    System.out.println("Ошибока в при сверке отчетов расходов в " + setMonthName(i));
                }
            }
            if (errorMassage) {
                System.out.println("Сверка отчетов по расходам успешна");
            }
        }


    }

    //Задание месяца
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
