package cor;

public class Main {

    public static void main(String[] args) {
        System.out.println("COR");

        MoneyHandler fiveHundredHandler = new FiveHundredHandler(5);
        MoneyHandler towHundredHandler = new TwoHundredHandler(10);
        MoneyHandler hundredHandler = new OneHundredHandler(50);

        fiveHundredHandler.setNextMoneyHandler(towHundredHandler);
        towHundredHandler.setNextMoneyHandler(hundredHandler);

        int amount = 1650;
        System.out.println("Please wait and collect you cash");
        fiveHundredHandler.meneyDespense(amount);

    }
}