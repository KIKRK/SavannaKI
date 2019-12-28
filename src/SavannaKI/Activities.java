package SavannaKI;

import SavannaKI.animals.*;
import SavannaKI.plants.Grass;
import SavannaKI.plants.Plant;
import SavannaKI.plants.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

class Activities {

    // Klasa zawierająca metody pozwalające na utworzenie i umieszczenie na sawannie zwierząt i roślin
    // oraz odpowiadające za kolejne aktywności w czasie tury.

    private int currentId = 0;

    private Predicate<Animal> tooYoung = (a) -> a.getAge() < 5;
    private Predicate<Animal> tooOld = (a) -> a.getAge() > 20;
    private Predicate<Animal> reproductiveAge = tooYoung.negate().and(tooOld.negate());
    private Predicate<Animal> needsToHunt = (a) -> a.getStamina() <= 20;

    private BiPredicate<Animal, Animal> sameSpecies = (a1, a2) -> a1.getClass().equals(a2.getClass());
    private BiPredicate<Animal, Animal> sameSex = (a1, a2) -> a1.getSex().equals(a2.getSex());
    private BiPredicate<Animal, Animal> sameTrait = (a1, a2) -> a1.getTrait().equals(a2.getTrait());
    private BiPredicate<Animal, Animal> sufficientStamina = (a1, a2) -> a1.getStamina() >= 5 && a2.getStamina() >= 5;
    private BiPredicate<Animal, Animal> adequateAge = (a1, a2) -> reproductiveAge.test(a1) && reproductiveAge.test(a2);
    private BiPredicate<Animal, Animal> possibleReproduction = sameSpecies.and(sameSex.negate().and(sufficientStamina.and(adequateAge)));

    private Sex randomizeSex() {
        Random r = new Random();
        Sex result;
        if (r.nextInt(2) == 0) {
            result = Sex.Male;
        } else {
            result = Sex.Female;
        }
        return result;
    }

    private int randomizeSize() {
        Random r = new Random();
        return r.nextInt(5) + 5;
    }

    private int randomizeStamina() {
        Random r = new Random();
        return r.nextInt(10) + 10;
    }

    private int randomizeAge() {
        Random r = new Random();
        return r.nextInt(29);
    }

    // Metoda określająca sposób wypełnienia pól sawanny w stanie początkowym.
    private void initialSpawn(Cell[][] savanna) {
        Random r = new Random();

        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                Cell f = savanna[i][j];

                if (r.nextInt(4) < 3) {
                    f.getPlants().add(new Grass(randomizeSize()));
                }

                if (r.nextInt(4) < 3) {
                    f.getPlants().add(new Tree(randomizeSize()));
                }

                // Liczba zwierząt na każdym polu w stanie początkowym.
                int initialPopulationDensity = 2;

                // Ustalenie proporcji między liczbą zwierząt poszczególnych gatunków w stanie początkowym
                while (initialPopulationDensity > 0) {
                    int rnd = r.nextInt(100);
                    if (0 <= rnd && rnd < 42) {
                        Giraffe giraffe = new Giraffe(randomizeSex(), f, randomizeStamina(), randomizeAge());
                        giraffe.setId(currentId);
                        currentId++;
                        f.getAnimals().add(giraffe);
                    } else if (42 <= rnd && rnd < 84) {
                        Zebra zebra = new Zebra(randomizeSex(), f, randomizeStamina(), randomizeAge());
                        zebra.setId(currentId);
                        currentId++;
                        f.getAnimals().add(zebra);
                    }
                    else {
                        Lion lion = new Lion(randomizeSex(), f, randomizeStamina(), randomizeAge());
                        lion.setId(currentId);
                        currentId++;
                        f.getAnimals().add(lion);
                    }
                    initialPopulationDensity--;
                }
            }
        }
    }


    Cell[][] createSavanna(int xSize, int ySize) {

        Cell[][] savanna = new Cell[xSize][ySize];

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                savanna[i][j]= new Cell(i, j, new ArrayList<>(), new ArrayList<>());
            }
        }

        initialSpawn(savanna);

        return savanna;
    }


    private void animalMoves(Cell[][] savanna, Sex sex) {

        List<Animal> animalsToAdvance = new ArrayList<>();

        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {

                ArrayList<Animal> animalsToRemove = new ArrayList<>();

                for (Animal a : savanna[i][j].getAnimals()) {
                    if (a.getSex() == sex) {
                        animalsToRemove.add(a);
                        a.move(a, savanna);
                        animalsToAdvance.add(a);
                    }
                }
                for (Animal a : animalsToRemove) {
                    savanna[i][j].getAnimals().remove(a);
                }
            }
        }
        for (Animal a : animalsToAdvance) {
            a.getCell().getAnimals().add(a);
        }
    }


    private void plantGrowth(Cell[][] savanna) {
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                for (Plant p : savanna[i][j].getPlants()) {
                    p.grow();
                }
            }
        }
    }


    // Oddziaływanie między zwierzętami: rozmnażanie, polowanie, lub brak interakcji, w zależności od tego, jakie zwierzęta się spotkają
    // i czy są spełnione warunki określone w predykatach.

    private void animalInteractions(Cell[][] savanna) {

        List<Animal> animalsToAdvance = new ArrayList<>();

        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                Cell cell = savanna[i][j];

                int counter = cell.getAnimals().size();

                while (counter >= 2) {
                    Random r = new Random(0);
                    Animal a1 = cell.getAnimals().get(0);
                    Animal a2 = cell.getAnimals().get(r.nextInt(counter - 1) + 1);

                    if (possibleReproduction.test(a1, a2)) {
                        reproduction(a1, a2, cell, animalsToAdvance);
                    } else if (!sameTrait.test(a1, a2) && a1.getTrait() == Trait.Carnivore && needsToHunt.test(a1)) {
                        animalsToAdvance.add(hunt(a1, a2));
                    } else if (!sameTrait.test(a1, a2) && a2.getTrait() == Trait.Carnivore && needsToHunt.test(a2)) {
                        animalsToAdvance.add(hunt(a2, a1));
                    } else {
                        a1.fatigue(1);
                        a2.fatigue(1);
                        animalsToAdvance.add(a1);
                        animalsToAdvance.add(a2);
                    }
                    cell.getAnimals().remove(a1);
                    cell.getAnimals().remove(a2);
                    counter -= 2;
                }

                if (counter == 1) {
                    Animal a1 = cell.getAnimals().get(0);
                    a1.fatigue(1);
                    animalsToAdvance.add(a1);
                    cell.getAnimals().remove(a1);
                }
            }
        }

        for (Animal a : animalsToAdvance) {
            a.getCell().getAnimals().add(a);

        }
    }


    private Animal hunt (Animal hunter, Animal prey){
        if (hunter.stamina + 2 * prey.stamina / 3 > hunter.maxStamina) {
            hunter.stamina = hunter.maxStamina;
        } else {
            hunter.stamina += 2 * prey.stamina / 3;
        }
        return hunter;
    }


    private void reproduction(Animal a1, Animal a2, Cell cell, List<Animal> animalsToAdvance){

        if (a1.getClass().equals(Giraffe.class)) {
            Giraffe a3 = new Giraffe(randomizeSex(), cell, (a1.getStamina() + a2.getStamina())/2, 0);
            a3.setId(currentId);
            currentId++;
            animalsToAdvance.add(a3);
            cell.getAnimals().remove(a3);

        } else if (a1.getClass().equals(Lion.class)) {
            Lion a3 = new Lion(randomizeSex(), cell, (a1.getStamina() + a2.getStamina())/2, 0);
            a3.setId(currentId);
            currentId++;
            animalsToAdvance.add(a3);
            cell.getAnimals().remove(a3);

        } else if (a1.getClass().equals(Zebra.class)) {
            Zebra a3 = new Zebra(randomizeSex(), cell, (a1.getStamina() + a2.getStamina())/2, 0);
            a3.setId(currentId);
            currentId++;
            animalsToAdvance.add(a3);
            cell.getAnimals().remove(a3);
        }

        a1.fatigue(3);
        a2.fatigue(3);
        animalsToAdvance.add(a1);
        animalsToAdvance.add(a2);
    }

    // Zjadanie roślin
    private void plantInteractions(Cell[][] savanna) {
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                Cell cell = savanna[i][j];
                if (cell.getAnimals().size() != 0 && cell.getPlants().size() != 0) {
                    for (Animal a : savanna[i][j].getAnimals()) {
                        if (a.getTrait().equals(Trait.Herbivore)) {
                            for (Plant p : savanna[i][j].getPlants()) {
                                if ((p.getClass().equals(Grass.class) && a.getClass().equals(Zebra.class)) ||
                                        (p.getClass().equals(Tree.class) && a.getClass().equals(Giraffe.class)) ) {
                                    int currentPlantSize = p.getSize();
                                    int amountEaten = a.eatPlant(a, currentPlantSize);
                                    p.beEaten(amountEaten);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Starzenie się zwierząt.
    private void ageing(Cell[][] savanna) {
        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                Cell f = savanna[i][j];
                for (Animal a : f.getAnimals()) {
                    a.ageing();
                }
            }
        }
    }

    // Zwierzęta, które osiągnęły określony wiek lub mają za małą żywotność umierają.
    private void staminaAndAgeCheck(Cell[][] savanna) {

        List<Animal> animalsToAdvance = new ArrayList<>();

        for (int i = 0; i < savanna.length; i++) {
            for (int j = 0; j < savanna[0].length; j++) {
                Cell cell = savanna[i][j];

                Animal[] animalsToRemove = new Animal[cell.getAnimals().size()];

                int counter = 0;
                for (Animal a : cell.getAnimals()) {
                    animalsToRemove[counter] = a;
                    counter++;
                    if (a.getAge() < 20){
                        if (a.getStamina() > 0){
                            animalsToAdvance.add(a);
                        }
                    } else{
                        if ((a.getAge() < 30 &&  a.getStamina() > a.getAge() / 2)){
                            animalsToAdvance.add(a);
                        }
                    }
                }
                for (Animal a : animalsToRemove) {
                    cell.getAnimals().remove(a);
                }
            }
        }

        for (Animal a : animalsToAdvance) {
            a.getCell().getAnimals().add(a);
        }
    }

    // Przebieg jednej tury
    void oneTurn(Cell[][] savanna) {
        Random r = new Random();
        if (r.nextInt(2) == 0){
            animalMoves(savanna, Sex.Male);
            animalMoves(savanna, Sex.Female);
        } else {
            animalMoves(savanna, Sex.Female);
            animalMoves(savanna, Sex.Male);
        }
        plantGrowth(savanna);
        animalInteractions(savanna);
        plantInteractions(savanna);
        ageing(savanna);
        staminaAndAgeCheck(savanna);
    }


}
