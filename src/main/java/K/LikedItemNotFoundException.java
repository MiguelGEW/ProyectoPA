package K;

public class LikedItemNotFoundException extends Exception {

    private final String nameLikedItem;

    public LikedItemNotFoundException(String nameLikedItem) {
        super("El elemento que ha gustado al usuario no existe: " + nameLikedItem);
        this.nameLikedItem = nameLikedItem;
    }

    // Method para recuperar el nombre inválido
    // TODO: Eliminar todo el código temporal o que no se usa
    // Method para recuperar el nombre inválido
    public String getNameLikedItem() {
        return nameLikedItem;
    }
}
