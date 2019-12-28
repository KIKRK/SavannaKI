package SavannaKI;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
Savanna - XII 2019
Created by KI

     Obecna wersja programu oferuje możliwość:
     (1) wypisywania na ekranie informacji o stanie sawanny w kolejnych turach,
     (2) zapisania wyników całej symulacji do pliku.

     W pierwszym przypadku zalecane są niewielkie wartości parametrów w drugim - mogą być większe.
     Czas obliczeń dla sawanny o wymiarach 100x100 i 1 000 tur wynosi ok. 3 minut.

     Program posiada też możliwość zapisania do pliku informacji o wszystkich zwierzętach,
     które kiedykolwiek pojawiły się na sawannie. Została ona jednak została zakomentowana,
     ponieważ w przypadku dużych wymiarów sawanny lub liczby tur plik ten osiągał bardzo duże rozmiary.

 */

public class Main {

//    Klasa zawierająca metodę główną.
//    Podstawowe parametry: wymiary sawanny oraz liczba tur są wprowadzane przez użytkownika.

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserInput userInput = new UserInput();
        Activities act = new Activities();
        DataDisplay dataDisplay = new DataDisplay();

        File savannaResultFile = new File("C:\\Users\\Public\\Documents\\SavannaInfo.txt");
        File animalResultFile = new File("C:\\Users\\Public\\Documents\\AnimalInfo.txt");

        System.out.println("Witamy w programie symulującym życie zwierząt na sawannie.");
        System.out.println("Program dostępny jest w dwóch wersjach:");
        System.out.println("1. Obserwacja przebiegu symulacji na ekranie.");
        System.out.println("2. Zapis przebiegu symulacji do pliku.");

        System.out.print("Podaj numer wybranej wersji (1 lub 2): ");
        int selectedVersion = userInput.selectOption();

        if (selectedVersion == 1){
            System.out.println("Podaj szerokość sawanny.");
            int xSize = userInput.setParameter(5, 20);
            System.out.println("Podaj długość sawanny.");
            int ySize = userInput.setParameter(5, 20);
            System.out.println("Podaj liczbę tur.");
            int numberOfTurns = userInput.setParameter(0, 15);
            String[][] animalInfo = new String[xSize * ySize * 2 + xSize * ySize * numberOfTurns / 5 + 1][30];
            Cell[][] savanna = act.createSavanna(xSize, ySize);

            int t = 0;
            PrintWriter printWriter = new PrintWriter(System.out, true);
            while (t <= numberOfTurns){
                System.out.println();
                System.out.println("Turn: " + t);
                dataDisplay.savannaStatus(savanna, printWriter);
                dataDisplay.populationDensityMap(savanna);
                System.out.println();
                dataDisplay.populationDensityMap(savanna, "Giraffe");
                System.out.println();
                dataDisplay.populationDensityMap(savanna, "Zebra");
                System.out.println();
                dataDisplay.populationDensityMap(savanna, "Lion");
                dataDisplay.updateAnimalInfo(savanna, t, animalInfo);
                act.oneTurn(savanna);
                System.out.print("Wprowadź dowolny znak i naciśnij enter, aby kontynuować: ");
                scanner.next();
                t++;
            }
            System.out.println();
            System.out.println("Dane zwierząt, które w trakcie symulacji żyły na sawannie:");
            int id = 0;
            while (animalInfo[id][0] != null) {
                dataDisplay.trackAnimalHistory(animalInfo, id, printWriter);
                printWriter.println();
                id++;
            }
        } else{
            System.out.println("Podaj szerokość sawanny.");
            int xSize = userInput.setParameter(5, 100);
            System.out.println("Podaj długość sawanny.");
            int ySize = userInput.setParameter(5, 100);
            System.out.println("Podaj liczbę tur.");
            int numberOfTurns = userInput.setParameter(0, 1_000);
            String[][] animalInfo = new String[xSize * ySize * 2 + xSize * ySize * numberOfTurns / 5][30];
            Cell[][] savanna = act.createSavanna(xSize, ySize);

            try (PrintWriter printWriter = new PrintWriter(savannaResultFile)) {
                for (int t = 0; t <= numberOfTurns; t++) {
                    dataDisplay.updateAnimalInfo(savanna, t, animalInfo);
                    printWriter.println("Tura: " + t);
                    dataDisplay.savannaStatus(savanna, printWriter);
                    act.oneTurn(savanna);
                    printWriter.println();
                }
                System.out.println("Przebieg ewolucji sawanny został zapisany do pliku: " + savannaResultFile.getPath());
            } catch (IOException e) {
                System.out.println("Zapis nie powiódł się.");
                e.printStackTrace();
            }

            // Zapis do pliku informacji o zwierzętach.
//            try {
//                printWriter = new PrintWriter(animalResultFile);
//                int id = 0;
//                while (animalInfo[id][0] != null){
//                    dataDisplay.trackAnimalHistory(animalInfo, id, printWriter);
//                    printWriter.println();
//                    id++;
//                }
//                System.out.println("Dane zwierząt żyjących na sawannie zostały zapisane do pliku: " + animalResultFile.getPath());
//            } catch (
//                    IOException e) {
//                System.out.println("Zapis nie powiódł się.");
//                e.printStackTrace();
//            } finally {
//                if (printWriter != null) {
//                    printWriter.close();
//                }
//            }
//

        }

        System.out.println();
        System.out.println("Dziękujemy za skorzystanie z programu.");

    }
}
