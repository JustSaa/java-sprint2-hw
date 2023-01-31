import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        ReportChecker checker = new ReportChecker(yearlyReport, monthlyReport);


        while (true) {
            //Печать меню
            printMenu();
            //Считываем команду
            int userInput = sc.nextInt();
            if (userInput == 1) {
                monthlyReport.loadMonthlyReport();
            } else if (userInput == 2) {
                yearlyReport.loadYearlyReport();
            } else if (userInput == 3) {
                if (checker.checkLoad()) {
                    checker.checkReports();
                }
            } else if (userInput == 4) {
                if (checker.checkLoad()) {
                    monthlyReport.findItem();
                }
            } else if (userInput == 5) {
                if (checker.checkLoad()) {
                    yearlyReport.printYearlyInfo();
                }
            } else if (userInput == 0) {
                System.out.println("Пока!");
                sc.close();
                return;
            } else {
                System.out.println("Такой команды нет");
            }

        }
    }

    static void printMenu() {
        System.out.println("Что Вы хотите сделать?:");
        System.out.println("1-Считать все месячные отчёты");
        System.out.println("2-Считать годовой отчёт");
        System.out.println("3-Сверить отчёты");
        System.out.println("4-Вывести информацию о всех месячных отчётах");
        System.out.println("5-Вывести информацию о годовом отчёте");
        System.out.println("0-Выход");
    }
}
