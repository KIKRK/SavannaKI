package SavannaKI.statistics;

import SavannaKI.Cell;
import SavannaKI.animals.Animal;
import SavannaKI.animals.Sex;
import SavannaKI.plants.Plant;

public class CellStatistics {

    // Klasa zawiera metody pozwalające na odczytanie rodzaju informacji dotyczących pojedynczego pola.

    int animalCount(Cell cell, String species, Sex sex, int minAge, int maxAge) {
        int counter = 0;
        for (Animal a : cell.getAnimals()) {
            if (a.getClass().getSimpleName().equals(species) && a.getSex().equals(sex) && a.getAge() >= minAge && a.getAge() <= maxAge) {
                counter++;
            }
        }
        return counter;
    }

    public int animalCount(Cell cell, String species, Sex sex) {
        int counter = 0;
        for (Animal a : cell.getAnimals()) {
            if (a.getClass().getSimpleName().equals(species) && a.getSex().equals(sex)) {
                counter++;
            }
        }
        return counter;
    }

    public int animalCount(Cell cell, String species) {
        int counter = 0;
        for (Animal a : cell.getAnimals()) {
            if (a.getClass().getSimpleName().equals(species)) {
                counter++;
            }
        }
        return counter;
    }

    public int animalCount(Cell cell) {
        return cell.getAnimals().size();
    }

    int totalAge(Cell cell, String species, Sex sex){
        int counter = 0;
        for (Animal a : cell.getAnimals()) {
            if (a.getClass().getSimpleName().equals(species)  && a.getSex().equals(sex)) {
                counter += a.getAge();
            }
        }
        return counter;
    }

    int totalAge(Cell cell){
        int counter = 0;
        for (Animal a : cell.getAnimals()) {
            counter += a.getAge();
        }
        return counter;
    }

    int totalStamina(Cell cell, String species, Sex sex) {
        int counter = 0;
        for (Animal a : cell.getAnimals()) {
            if (a.getClass().getSimpleName().equals(species) && a.getSex().equals(sex)) {
                counter+= a.getStamina();
            }
        }
        return counter;
    }

    int plantCount(Cell cell, String species, int minSize, int maxSize) {
        int counter = 0;
        for (Plant p : cell.getPlants()) {
            if (p.getClass().getSimpleName().equals(species) && p.getSize() >= minSize && p.getSize() <= maxSize) {
                counter++;
            }
        }
        return counter;
    }

    public int plantSize(Cell cell, String species) {
        int size = 0;
        for (Plant p : cell.getPlants()) {
            if (p.getClass().getSimpleName().equals(species)) {
                size = p.getSize();
            }
        }
        return size;
    }

    public int availableFood(Cell cell, String species){
        int counter = 0;
        for (Plant p : cell.getPlants()) {
            if (p.getClass().getSimpleName().equals(species)){
                counter += p.getSize();
            }
        }
        return counter;
    }

}
