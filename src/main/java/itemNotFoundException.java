public class itemNotFoundException extends RuntimeException {
    public itemNotFoundException(String itemName) {
        super(itemName);
    }
}
