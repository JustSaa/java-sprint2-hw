import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Engine engine = new Engine();


        while (true) {
            //Печать меню
            printMenu();
            //Считываем команду
            int userInput = sc.nextInt();
            switch (userInput) {
                case 1:
                    engine.loadMonthlyReport();
                    break;
                case 2:
                    engine.loadYearlyReport();
                    break;
                case 3:
                    if (engine.checkLoad()) {
                        engine.checkReports();
                    }
                    break;
                case 4:
                    if (engine.checkLoad()) {
                        engine.printMonthlyInfo();
                    }
                    break;
                case 5:
                    if (engine.checkLoad()) {
                        engine.printYearlyInfo();
                    }
                    break;
                case 0:
                    System.out.println("Пока!");
                    sc.close();
                    return;
                default:
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
