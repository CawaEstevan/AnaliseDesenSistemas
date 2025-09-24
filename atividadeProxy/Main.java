public class Main {
    public static void main(String[] args) {
        IDatabaseOperation operation = new DatabaseProxy();
        operation.execute();
    }
}
