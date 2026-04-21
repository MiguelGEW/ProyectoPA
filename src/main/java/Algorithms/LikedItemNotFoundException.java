package Algorithms;

public class LikedItemNotFoundException extends Exception {

    private final String nameLikedItem;

    public LikedItemNotFoundException(String nameLikedItem) {
        super("El elemento que ha gustado al usuario no existe: " + nameLikedItem);
        this.nameLikedItem = nameLikedItem;
    }
}
