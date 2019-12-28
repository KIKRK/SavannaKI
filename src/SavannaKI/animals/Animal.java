package SavannaKI.animals;

import SavannaKI.Cell;

import java.util.Random;

public abstract class Animal {
    Trait trait;
    Sex sex;
    private int age;
    private Cell cell;
    public int stamina;
    public int maxStamina;
    private int id;

    Animal(Sex sex, Cell cell, int stamina, int age) {
        this.sex = sex;
        this.cell = cell;
        this.stamina = stamina;
        this.age = age;
    }

    Animal(){}

    public Trait getTrait() {
        return trait;
    }

    public Sex getSex() {
        return sex;
    }

    Sex getOppositeSex(Sex sex){
        if (sex == Sex.Female){
            return Sex.Male;
        } else{
            return Sex.Female;
        }
    }

    public int getAge() {
        return age;
    }

    public Cell getCell() {
        return cell;
    }
    private void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getStamina() {
        return stamina;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void fatigue(int n) {
        stamina -= n;
    }

    public void ageing(){
        age += 1;
    }


    // Funkcja użyteczności określająca atrakcyjność pola - różna w zależności od gatunku.
    public abstract int fieldDesirability (Cell cell);


    public void move(Animal a, Cell[][] savanna) {         // Ruch zwierząt.
        Random r = new Random();
        Cell currentCell = a.getCell();
        int currentX = currentCell.getX();
        int currentY = currentCell.getY();
        int age = a.getAge();
        int x;
        int y;

        for (int i = 0; i < 5; i++) {                       // Losowanie współrzędnych docelowego pola.
            if (age < 5 || age > 20){
                x = r.nextInt(2) - 1;
                y = r.nextInt(2) - 1;
            } else{
                x = r.nextInt(3) - 2;
                y = r.nextInt(3) - 2;
            }

            int updatedX = currentX + x;
            int updatedY = currentY + y;


            // Możliwość przejścia na drugą krawędź planszy, gdy zaktualizowane współrzędne są poza krawędzią planszy.
            if (updatedX >= savanna.length){
                updatedX -= savanna.length;
            }

            if (updatedX < 0){
                updatedX += savanna.length;
            }

            if (updatedY >= savanna[0].length){
                updatedY -= savanna[0].length;
            }

            if (updatedY < 0){
                updatedY += savanna[0].length;
            }

            Cell possibleCell = savanna[updatedX][updatedY];

            // Atrakcyjność docelowego pola jest porównywana z bieżącą; jeśli jest to korzystne - następuje ruch,
            // jeśli nie - ponowne losowanie (max 5 prób).
            if (a.fieldDesirability(currentCell) < a.fieldDesirability(possibleCell)) {
                a.setCell(possibleCell);
                break;
            }
        }
    }


    // Zjadanie roślin.
    public int eatPlant (Animal a, int availablePlantSize){
        int foodWanted;

        if (a.maxStamina - a.getStamina() <= 4) {
            foodWanted = a.maxStamina - a.getStamina();
        } else {
            foodWanted = 5;
        }

        if (foodWanted <= availablePlantSize) {
            a.stamina += foodWanted;
            return foodWanted;
        } else {
            a.stamina += availablePlantSize;
            return availablePlantSize;
        }
    }

    public String animalBasicInfo(){
        return "#" + id + ": " + sex + " " + getClass().getSimpleName();
    }

    public String animalParameters(){
        return age + "," + stamina + "," + cell.getX() + "-" + cell.getY() + ",";
    }

}
