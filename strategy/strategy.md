# Strategy Design Pattern

The **Strategy Design Pattern** is a **behavioral design pattern** that lets you define a family of algorithms, encapsulate each one in a separate class, and make them interchangeable. Instead of hardcoding behavior inside a class, you can change the behavior at runtime by choosing a different strategy.

## Why use the Strategy Pattern?

Imagine you have an application that supports multiple payment methods:

* Credit Card
* PayPal
* UPI

Without the Strategy Pattern, you might write:

```java
class PaymentService {
    public void pay(String method, double amount) {
        if (method.equals("CreditCard")) {
            System.out.println("Paid " + amount + " using Credit Card");
        } else if (method.equals("PayPal")) {
            System.out.println("Paid " + amount + " using PayPal");
        } else if (method.equals("UPI")) {
            System.out.println("Paid " + amount + " using UPI");
        }
    }
}
```

### Problems

* Lots of `if-else` or `switch` statements.
* Adding a new payment method requires modifying existing code.
* Violates the **Open/Closed Principle** (classes should be open for extension but closed for modification).

---

# Strategy Pattern Solution

## Step 1: Create a Strategy Interface

```java
interface PaymentStrategy {
    void pay(double amount);
}
```

---

## Step 2: Implement Different Strategies

### Credit Card Strategy

```java
class CreditCardPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Credit Card");
    }
}
```

### PayPal Strategy

```java
class PayPalPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using PayPal");
    }
}
```

### UPI Strategy

```java
class UPIPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using UPI");
    }
}
```

---

## Step 3: Context Class

The context uses a strategy instead of implementing the algorithm itself.

```java
class PaymentContext {

    private PaymentStrategy paymentStrategy;

    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void makePayment(double amount) {
        paymentStrategy.pay(amount);
    }
}
```

---

## Step 4: Client Code

```java
public class Main {

    public static void main(String[] args) {

        PaymentContext context =
                new PaymentContext(new CreditCardPayment());

        context.makePayment(1000);

        context.setPaymentStrategy(new PayPalPayment());
        context.makePayment(2000);

        context.setPaymentStrategy(new UPIPayment());
        context.makePayment(500);
    }
}
```

### Output

```text
Paid ₹1000.0 using Credit Card
Paid ₹2000.0 using PayPal
Paid ₹500.0 using UPI
```

---

# Class Diagram

```text
                 PaymentStrategy
                        ^
                        |
       ----------------------------------
       |                |               |
CreditCardPayment  PayPalPayment   UPIPayment
       ^
       |
   PaymentContext ------> uses one strategy
       ^
       |
     Client chooses strategy
```

The **client** decides which strategy to use, and the `PaymentContext` delegates the work to that strategy.

---

# Real-World Example

Imagine you're using Google Maps.

You can choose different routing strategies:

* Fastest Route
* Shortest Route
* Avoid Highways
* Avoid Tolls

Each route calculation is a different algorithm, but the map application simply uses whichever strategy the user selects.

```text
RouteStrategy
    |
    |-- FastestRoute
    |-- ShortestRoute
    |-- AvoidTollRoute

GoogleMaps
    |
    --> Uses RouteStrategy
```

---

# Another Example: Sorting

Suppose you want to sort data using different algorithms.

### Strategy Interface

```java
interface SortStrategy {
    void sort(int[] arr);
}
```

### Bubble Sort Strategy

```java
class BubbleSort implements SortStrategy {
    public void sort(int[] arr) {
        System.out.println("Sorting using Bubble Sort");
    }
}
```

### Quick Sort Strategy

```java
class QuickSort implements SortStrategy {
    public void sort(int[] arr) {
        System.out.println("Sorting using Quick Sort");
    }
}
```

### Context

```java
class SortContext {

    private SortStrategy strategy;

    public SortContext(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void performSort(int[] arr) {
        strategy.sort(arr);
    }
}
```

---

# Advantages

* Eliminates large `if-else` or `switch` statements.
* Easy to add new algorithms without modifying existing code.
* Follows the **Open/Closed Principle**.
* Allows behavior to change at runtime.
* Improves readability, maintainability, and testability.

---

# Disadvantages

* Increases the number of classes.
* The client must know which strategy to choose.
* Can be overkill for very simple use cases.

---

# When to Use the Strategy Pattern

Use the Strategy Pattern when:

* Multiple algorithms perform the same task in different ways.
* You want to switch algorithms at runtime.
* You want to eliminate large conditional statements.
* You want to follow the **Open/Closed Principle**.

### Common Use Cases

* Payment methods (Credit Card, PayPal, UPI)
* Compression algorithms (ZIP, RAR, GZIP)
* Authentication methods (OAuth, JWT, API Key)
* Sorting algorithms (Quick Sort, Merge Sort)
* Route planning (Fastest, Shortest, Avoid Tolls)

---

# Summary

The **Strategy Design Pattern** separates **what needs to be done** from **how it is done** by encapsulating each algorithm inside its own class.

Instead of hardcoding behavior, the **Context** delegates the work to a **Strategy**, making it easy to add new algorithms and switch between them at runtime without modifying existing code.

## Key Components

| Component             | Responsibility                                            |
| --------------------- | --------------------------------------------------------- |
| **Strategy**          | Defines a common interface for all algorithms.            |
| **Concrete Strategy** | Implements a specific algorithm.                          |
| **Context**           | Holds a reference to a strategy and delegates work to it. |
| **Client**            | Chooses which strategy the context should use.            |

### Pattern Flow

```text
Client
   |
   v
Context ----------------------+
   |                          |
   v                          |
Strategy Interface            |
   ^                          |
   |                          |
+---------+-----------+-------+
|         |           |
Credit    PayPal     UPI
Card
```

**Key Takeaway:**

> **Encapsulate interchangeable algorithms behind a common interface so they can be selected and changed at runtime without modifying the code that uses them.**
