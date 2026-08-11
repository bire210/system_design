package strategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("Statergy");

        PaymentContext paymentContext = new PaymentContext(new CreditCardPaymentStrategy());

        paymentContext.makePayment(200);
    }
}
