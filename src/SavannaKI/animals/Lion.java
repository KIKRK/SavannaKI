package SavannaKI.animals;

import SavannaKI.Cell;
import SavannaKI.statistics.CellStatistics;

public class Lion extends Animal {

    public Lion(Sex sex, Cell cell, int stamina, int age) {
        super(sex, cell, stamina, age);
        super.trait = Trait.Carnivore;
        super.maxStamina = 30;
    }

    public int fieldDesirability (Cell cell){
        CellStatistics cStat = new CellStatistics();
        int result = 0;

        if (cStat.animalCount(cell) > 3){
            result -= 1;
        }

        if (stamina <= 20){
            if (cStat.animalCount(cell, "Zebra") + cStat.animalCount(cell, "Giraffe") > 0) {
                result += 1;
            }
        } else{
            result += 2 * cStat.animalCount(cell, "Lion", getOppositeSex(sex));
            result -= cStat.animalCount(cell, "Lion", sex);
        }

        return result;
    }

}

