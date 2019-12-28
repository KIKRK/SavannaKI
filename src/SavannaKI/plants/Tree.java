package SavannaKI.plants;

public class Tree extends Plant {

    public Tree(int size) {
        super(size);
    }

    @Override
    public void grow() {
        if (size < 20) {
            if (size <= 10) {
                size += 2;
            } else {
                size += 1;
            }
        }
    }

}
