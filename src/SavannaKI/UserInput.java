package SavannaKI;

import java.util.Scanner;

class UserInput {

    // Klasa zawiera metody komunikujące się z użytkownikiem w trakcie działania programu.

    int selectOption() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int selectedOption = scanner.nextInt();
                if (selectedOption == 1 || selectedOption == 2) {
                    return selectedOption;
                } else {
                    System.out.print("Nieprawidłowy wybór opcji. Wybierz ponownie (1 lub 2): ");
                }
            } catch (Exception e) {
                System.out.print("Nieprawidłowy wybór opcji. Wybierz ponownie (1 lub 2): ");
                scanner.next();
            }
        }
    }


    int setParameter(int minValue, int maxValue) {
        System.out.print("Wprowadź wartość z przedziału od " + minValue + " do " + maxValue + ": ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int value = scanner.nextInt();
                if (value >= minValue && value <= maxValue) {
                    return value;
                } else {
                    System.out.print("Podano wartość spoza zakresu. Wprowadź liczbę z przedziału od " + minValue + " do " + maxValue + ": ");
                }
            } catch (Exception e) {
                System.out.print("Niewłaściwy format danych. Podaj liczbę naturalną z przedziału od " + minValue + " do " + maxValue + ": ");
                scanner.next();
            }
        }
    }

}
