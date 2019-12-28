package SavannaKI.statistics;

import SavannaKI.Cell;
import SavannaKI.animals.Sex;
import SavannaKI.plants.Plant;

public class SavannaStatistics {

    CellStatistics cStat = new CellStatistics();

    // Klasa zawiera metody pozwalające na odczytanie zagregowanych informacji w odniesieniu do całej sawanny.

    public int animalCount(Cell[][] savanna) {
        int counter = 0;

        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                counter += cStat.animalCount(savanna[i][j]);
            }
        }
        return counter;
    }

    int animalCount(Cell[][] savanna, String species, Sex sex) {
        int counter = 0;
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                counter+= cStat.animalCount(savanna[i][j], species, sex);
            }
        }
        return counter;
    }

    public int animalCount(Cell[][] savanna, String species, Sex sex, int minAge, int maxAge) {
        int counter = 0;
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                counter+= cStat.animalCount(savanna[i][j], species, sex, minAge, maxAge);
            }
        }
        return counter;
    }

    public int plantCount(Cell[][] savanna, String species, int minSize, int maxSize) {
        int counter = 0;
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                Cell f = savanna[i][j];
                counter+= cStat.plantCount(savanna[i][j], species, minSize, maxSize);
            }
        }
        return counter;
    }

    public int totalAge(Cell[][] savanna, String species, Sex sex){
        int totalAge = 0;
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                totalAge+= cStat.totalAge(savanna[i][j], species, sex);
            }
        }
        return totalAge;
    }

    int totalAge(Cell[][] savanna){
        int totalAge = 0;
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                totalAge+= cStat.totalAge(savanna[i][j]);
            }
        }
        return totalAge;
    }

    public int totalStamina(Cell[][] savanna, String species, Sex sex){
        int totalStamina = 0;
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                totalStamina+= cStat.totalStamina(savanna[i][j], species, sex);
            }
        }
        return totalStamina;
    }

    public int availableFood(Cell[][] savanna, String species){
        int counter = 0;
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                for (Plant p : savanna[i][j].getPlants()) {
                    if (p.getClass().getSimpleName().equals(species)){
                        counter += cStat.availableFood(savanna[i][j], species);
                    }
                }
            }
        }
        return counter;
    }
}
