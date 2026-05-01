package AuraApp.BackEnd.Algorithms;

public class LikedItemNotFoundException extends Exception {

    private final String nameLikedItem;

    public LikedItemNotFoundException(String nameLikedItem) {
        super("El elemento que ha gustado al usuario no existe: " + nameLikedItem);
        this.nameLikedItem = nameLikedItem;
    }

    public String getNameLikedItem() {
        return nameLikedItem;
    }
}
