package strategy;

public class PayPalPaymentStrategy implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid by paypal :" + amount);
    }
}
