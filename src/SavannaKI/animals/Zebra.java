package SavannaKI.animals;

import SavannaKI.Cell;
import SavannaKI.statistics.CellStatistics;

public class Zebra extends Animal {

    public Zebra(Sex sex, Cell cell, int stamina, int age) {
        super(sex, cell, stamina, age);
        super.trait = Trait.Herbivore;
        super.maxStamina = 20;
    }

    public int fieldDesirability(Cell cell) {
        CellStatistics cStat = new CellStatistics();
        int result = 0;

        result -= cStat.animalCount(cell, "Lion");

        result += cStat.animalCount(cell, "Zebra", getOppositeSex(sex));

        result -= cStat.animalCount(cell, "Zebra", sex);

        if (stamina < 10){
            result += cStat.availableFood(cell, "Zebra");
        }

        return result;
    }
}

