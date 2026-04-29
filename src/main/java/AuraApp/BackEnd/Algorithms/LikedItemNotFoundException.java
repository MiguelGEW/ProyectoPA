package AuraApp.BackEnd.Algorithms;

public class LikedItemNotFoundException extends Exception {

    // TODO: Falta incluir un método para recuperar el nombre del ítem no encontrado
    private final String nameLikedItem;

    public LikedItemNotFoundException(String nameLikedItem) {
        super("El elemento que ha gustado al usuario no existe: " + nameLikedItem);
        this.nameLikedItem = nameLikedItem;
    }
}
