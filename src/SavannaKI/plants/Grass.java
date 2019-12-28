package SavannaKI.plants;

public class Grass extends Plant {

    public Grass(int size) {
        super(size);
    }

    @Override
    public void grow() {
        if (size < 20) {
            if (size <= 5) {
                size += 2;
            } else{
                size +=1;
            }
        }
    }

}
