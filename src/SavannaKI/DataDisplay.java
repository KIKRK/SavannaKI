package SavannaKI;

import SavannaKI.animals.Animal;
import SavannaKI.animals.Sex;
import SavannaKI.statistics.CellStatistics;
import SavannaKI.statistics.SavannaStatistics;

import java.io.PrintWriter;
import java.text.DecimalFormat;

public class DataDisplay {

    // Klasa zawierająca metody pozwalające na przedstawienie w czytelnej formie oraz zapisanie bieżącego stanu sawanny.

    private final String BACKGROUND_RED = "\u001b[48;2;255;0;0m";
    private final String BACKGROUND_GREEN = "\u001b[48;2;0;255;0m";
    private final String BACKGROUND_YELLOW = "\u001b[48;2;255;255;0m";
    private final String BACKGROUND_BRIGHT_RED = "\u001b[48;2;255;128;128m";
    private final String BACKGROUND_BRIGHT_GREEN = "\u001b[48;2;128;255;0m";
    private final String BACKGROUND_BRIGHT_YELLOW = "\u001b[48;2;255;255;128m";
    private final String COLOUR_RESET = "\u001B[0m";

    void updateAnimalInfo(Cell[][] savanna, int turnNo, String[][] animalInfo) {
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                Cell cell = savanna[i][j];
                for (Animal a : cell.getAnimals()){
                    int id = a.getId();
                    int age = a.getAge();
                    if (animalInfo[id][0]==null) {
                        animalInfo[id][0] = a.animalBasicInfo();
                    }
                    animalInfo[id][age] = turnNo + "," + a.animalParameters();
                }
            }
        }
    }

    // Mapa ilustrująca bieżące rozmieszczenie zwierząt na sawannie.
    void populationDensityMap(Cell[][] savanna) {

        CellStatistics cellStat = new CellStatistics();

        System.out.println("Population density (total):");
        System.out.printf("%2d %2d %2d %2d %2d %4s", 0, 1, 2, 3, 4, ">=5");
        System.out.println();
        System.out.print(BACKGROUND_GREEN + "   " + BACKGROUND_BRIGHT_GREEN + "   " + BACKGROUND_BRIGHT_YELLOW + "   " +
                BACKGROUND_YELLOW + "   " + BACKGROUND_BRIGHT_RED + "   " + BACKGROUND_RED + "     ");
        System.out.println(COLOUR_RESET);
        System.out.println();

        for (int j = 0; j < savanna[0].length; j++) {
            for (int i = 0; i < savanna.length; i++) {
                switch (cellStat.animalCount(savanna[i][j])){
                    case 0:
                        System.out.print(BACKGROUND_GREEN + "   ");
                        break;
                    case 1:
                        System.out.print(BACKGROUND_BRIGHT_GREEN + "   ");
                        break;
                    case 2:
                        System.out.print(BACKGROUND_BRIGHT_YELLOW + "   ");
                        break;
                    case 3:
                        System.out.print(BACKGROUND_YELLOW + "   ");
                        break;
                    case 4:
                        System.out.print(BACKGROUND_BRIGHT_RED + "   ");
                        break;
                    default:
                        System.out.print(BACKGROUND_RED + "   ");
                        break;
                }
            }
            System.out.print(COLOUR_RESET);
            System.out.println();
        }
    }

    // Mapa ilustrująca bieżące rozmieszczenie zwierząt konkretnego gatunku na sawannie.
    void populationDensityMap(Cell[][] savanna, String species) {

        CellStatistics cellStat = new CellStatistics();

        System.out.println("Population density (" + species + "):");
        System.out.printf("%2d %2d %2d %2d %2d %4s", 0, 1, 2, 3, 4, ">=5");
        System.out.println();
        System.out.print(BACKGROUND_GREEN + "   " + BACKGROUND_BRIGHT_GREEN + "   " + BACKGROUND_BRIGHT_YELLOW + "   " +
                BACKGROUND_YELLOW + "   " + BACKGROUND_BRIGHT_RED + "   " + BACKGROUND_RED + "     ");
        System.out.println(COLOUR_RESET);
        System.out.println();

        for (int j = 0; j < savanna[0].length; j++) {
            for (int i = 0; i < savanna.length; i++) {
                switch (cellStat.animalCount(savanna[i][j], species)){
                    case 0:
                        System.out.print(BACKGROUND_GREEN + "   ");
                        break;
                    case 1:
                        System.out.print(BACKGROUND_BRIGHT_GREEN + "   ");
                        break;
                    case 2:
                        System.out.print(BACKGROUND_BRIGHT_YELLOW + "   ");
                        break;
                    case 3:
                        System.out.print(BACKGROUND_YELLOW + "   ");
                        break;
                    case 4:
                        System.out.print(BACKGROUND_BRIGHT_RED + "   ");
                        break;
                    default:
                        System.out.print(BACKGROUND_RED + "   ");
                        break;
                }
            }
            System.out.print(COLOUR_RESET);
            System.out.println();
        }
    }

    // Wypisanie informacji dotyczących życia zwierzęcia o danym identyfikatorze.
    void trackAnimalHistory(String[][] animalInfo, int id, PrintWriter printWriter) {

        int[] formatParameters = {6, 5, 5, 6};

        printWriter.println(animalInfo[id][0]);
        printWriter.printf("%-5s %-4s %-4s %-5s", "Turn", "Age", "St.", "Pos.");
        printWriter.println();
        for (int i = 0; i <= 20; i++) {
            printWriter.print("-");
        }
        printWriter.println();

        for (int age = 1; age < 30; age++) {
            String s = animalInfo[id][age];
            if (s != null) {
                int counter = 0;
                int fParam = 0;
                String stringToPrint = "";
                while (counter < s.length()){
                    if(s.charAt(counter)!=',') {
                        stringToPrint += s.charAt(counter);
                    } else{
                        printWriter.printf("%-" + formatParameters[fParam] + "s", stringToPrint);
                        stringToPrint = "";
                        fParam ++;
                    }
                    counter++;
                }
                printWriter.println();
            }
        }
    }

    // Wypisanie podstawowych danych statystycznych opisujących stan sawanny.
    void savannaStatus(Cell[][] savanna, PrintWriter printWriter){

        SavannaStatistics sStat = new SavannaStatistics();
        int totalAnimals = sStat.animalCount(savanna);

        DecimalFormat df1 = new DecimalFormat("#0.0%");
        DecimalFormat df2 = new DecimalFormat("##.0y");
        DecimalFormat df3 = new DecimalFormat("##.0");

        String[] plantSpecies = {"Grass", "Tree"};
        String[] animalSpecies = {"Giraffe", "Zebra", "Lion"};
        Sex[] sexes = {Sex.Female, Sex.Male};

        printWriter.printf("%-8s %-7s %5s %11s %9s %6s %8s %9s %10s %12s",
                "Species", "Sex", "Pop.", "% of total", "Avg. age", "Age 1", "Age 2-5", "Age 6-20", "Age 21-30", "Avg. stamina");
        printWriter.println();
        for (int i = 0; i <= 100; i++) {
            printWriter.print("-");
        }
        printWriter.println();

        int populationAged1All = 0;
        int populationAged2_5All = 0;
        int populationAged6_20All = 0;
        int populationAged21_30All = 0;
        int totalAgeAll = 0;
        int totalStaminaAll = 0;

        for (String sp : animalSpecies){
            for (Sex sex : sexes){

                int populationAged1 = sStat.animalCount(savanna, sp, sex, 0, 1);
                int populationAged2_5 = sStat.animalCount(savanna, sp, sex, 2, 5);
                int populationAged6_20 = sStat.animalCount(savanna, sp, sex, 6, 20);
                int populationAged21_30 = sStat.animalCount(savanna, sp, sex, 21, 30);
                int totalPopulation = populationAged1 + populationAged2_5 + populationAged6_20 + populationAged21_30;

                int totalStamina = sStat.totalStamina(savanna, sp, sex);
                int totalAge = sStat.totalAge(savanna, sp, sex);

                double populationPercentage = (double) totalPopulation / totalAnimals;
                double averageAge = (double) totalAge / totalPopulation;
                double averageStamina = (double) totalStamina / totalPopulation;

                populationAged1All += populationAged1;
                populationAged2_5All += populationAged2_5;
                populationAged6_20All += populationAged6_20;
                populationAged21_30All += populationAged21_30;
                totalAgeAll += totalAge;
                totalStaminaAll += totalStamina;

                printWriter.printf("%-8s %-7s %5s %11s %9s %6s %8s %9s %10s %12s",
                        sp, sex, totalPopulation,
                        df1.format(populationPercentage), df2.format(averageAge),
                        populationAged1, populationAged2_5, populationAged6_20, populationAged21_30,
                        df3.format(averageStamina));

                printWriter.println();
            }
        }

        for (int i = 1; i <= 100; i++) {
            printWriter.print("-");
        }
        printWriter.println();
        printWriter.printf("%-8s %-7s %5s %11s %9s %6s %8s %9s %10s %12s",
                "TOTAL", "-", totalAnimals, "100,00%", df2.format((double) totalAgeAll/totalAnimals),
                populationAged1All, populationAged2_5All, populationAged6_20All, populationAged21_30All,
                df3.format((double) totalStaminaAll/totalAnimals) );
        printWriter.println();

        printWriter.println();

        printWriter.printf("%-9s %-11s %7s %10s %11s %12s %12s",
                "Type", "Total size", "Size 0", "Size 1-5", "Size 6-10", "Size 11-15", "Size 16-20");
        printWriter.println();
        for (int i = 1; i <= 80; i++) {
            printWriter.print("-");
        }
        printWriter.println();

        for (String sp : plantSpecies){
            int populationSized0 = sStat.plantCount(savanna, sp, 0, 0);
            int populationSized1_5 = sStat.plantCount(savanna, sp, 1, 5);
            int populationSized6_10 = sStat.plantCount(savanna, sp, 6, 10);
            int populationSized11_15 = sStat.plantCount(savanna, sp, 11, 15);
            int populationSized16_20 = sStat.plantCount(savanna, sp, 16, 20);

            printWriter.printf("%-9s %-11s %7s %10s %11s %12s %12s",
                    sp, sStat.availableFood(savanna, sp) , populationSized0, populationSized1_5, populationSized6_10, populationSized11_15, populationSized16_20);
            printWriter.println();
        }
        printWriter.println();
        printWriter.println();
    }

    // Mapa zawierająca informacje o liczebności zwierząt i roślin na poszczególnych polach sawanny.
    public void savannaMap(Cell[][] savanna, int xMin, int xMax, int yMin, int yMax){

        CellStatistics cellStat = new CellStatistics();

        for (int y = yMin; y <= yMax; y++){

            System.out.println();

            for (int x = xMin; x <= xMax; x++) {
                System.out.print("|---");
                System.out.printf("%1s %2d %1s %2d %1s", "(", x, "-", y, ")");
                System.out.print("--|");
            }
            System.out.println();

            for (int x = xMin; x <= xMax; x++) {
                System.out.printf("%1s %4s %1d %4s %2d %1s", "|", "GF: ", cellStat.animalCount(savanna[x][y], "Giraffe", Sex.Female),
                        "LF: ", cellStat.animalCount(savanna[x][y], "Lion", Sex.Female), "|");
            }
            System.out.println();

            for (int x = xMin; x <= xMax; x++) {
                System.out.printf("%1s %4s %1d %4s %2d %1s", "|", "GM: ", cellStat.animalCount(savanna[x][y], "Giraffe", Sex.Male),
                        "LM: ", cellStat.animalCount(savanna[x][y], "Lion", Sex.Male), "|");
            }
            System.out.println();

            for (int x = xMin; x <= xMax; x++) {
                System.out.printf("%1s %4s %1d %4s %2d %1s", "|", "ZF: ", cellStat.animalCount(savanna[x][y], "Zebra", Sex.Female),
                        "Gr: ", cellStat.plantSize(savanna[x][y], "Grass") , "|");
            }
            System.out.println();

            for (int x = xMin; x <= xMax; x++) {
                System.out.printf("%1s %4s %1d %4s %2d %1s", "|", "ZM: ", cellStat.animalCount(savanna[x][y], "Zebra", Sex.Male),
                        "Tr: ", cellStat.plantSize(savanna[x][y], "Tree") , "|");
            }
            System.out.println();

            for (int x = xMin; x <= xMax; x++) {
                System.out.print("------------------");
            }

        }
    }

}
