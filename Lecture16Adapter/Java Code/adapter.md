# Adapter Design Pattern

The **Adapter Design Pattern** is a **structural design pattern** that allows **two incompatible interfaces to work together**.

It acts as a **bridge** between an existing class (Adaptee) and the class that the client expects (Target).

> **In simple words:**
> The Adapter Pattern converts the interface of one class into another interface that the client understands, allowing incompatible classes to collaborate without modifying their source code.

---

# Why use the Adapter Pattern?

Imagine you're building an application that processes payments.

Your application expects every payment provider to implement the following interface:

```java id="h7r3qw"
interface PaymentProcessor {

    void pay(double amount);
}
```

Your application currently supports:

* Stripe
* PayPal

Later, you need to integrate a third-party payment gateway that provides a different API.

```java id="4k2vta"
class LegacyBankGateway {

    public void makePayment(double amount) {
        System.out.println("Payment of ₹" + amount + " processed.");
    }
}
```

## Problems

* The third-party API doesn't implement `PaymentProcessor`.
* You cannot modify the third-party library.
* Existing client code expects the `pay()` method.

---

# Adapter Pattern Solution

Instead of changing the third-party class, create an **Adapter** that converts the expected interface into the existing interface.

---

# Step 1: Create the Target Interface

```java id="jlwm31"
interface PaymentProcessor {

    void pay(double amount);
}
```

The client works only with this interface.

---

# Step 2: Create the Adaptee

```java id="jlwm32"
class LegacyBankGateway {

    public void makePayment(double amount) {
        System.out.println("Bank payment of ₹" + amount + " processed.");
    }
}
```

This is an existing class that cannot be modified.

---

# Step 3: Create the Adapter

```java id="’wini33"
class BankAdapter implements PaymentProcessor {

    private LegacyBankGateway bankGateway;

    public BankAdapter(LegacyBankGateway bankGateway) {
        this.bankGateway = bankGateway;
    }

    @Override
    public void pay(double amount) {
        bankGateway.makePayment(amount);
    }
}
```

The adapter converts `pay()` into `makePayment()`.

---

# Step 4: Client Code

```java id="’wini34"
public class Main {

    public static void main(String[] args) {

        PaymentProcessor paymentProcessor =
                new BankAdapter(new LegacyBankGateway());

        paymentProcessor.pay(1000);
    }
}
```

### Output

```text id="’wini35"
Bank payment of ₹1000.0 processed.
```

The client doesn't know it's using a legacy gateway.

---

# How Adapter Pattern Works

```text id="’wini36"
Client
   |
   v
Target Interface
   |
   v
Adapter
   |
Converts Request
   |
   v
Adaptee
```

The adapter translates the client's request into a format understood by the adaptee.

---

# Class Diagram

```text id="’wini37"
              PaymentProcessor
             +-----------------+
             | pay()           |
             +-----------------+
                     ^
                     |
               BankAdapter
                     |
                     |
             LegacyBankGateway
             +-----------------+
             | makePayment()   |
             +-----------------+
```

The adapter implements the target interface while internally using the adaptee.

---

# Object Adapter vs Class Adapter

## 1. Object Adapter (Recommended)

Uses **composition**.

```text id="’wini38"
Adapter
   |
Has-A
   |
Adaptee
```

Example:

```java id="’wini39"
class BankAdapter implements PaymentProcessor {

    private LegacyBankGateway bankGateway;

    public BankAdapter(LegacyBankGateway bankGateway) {
        this.bankGateway = bankGateway;
    }
}
```

### Advantages

* More flexible.
* Can adapt multiple subclasses.
* Follows the **Composition over Inheritance** principle.

---

## 2. Class Adapter

Uses **inheritance** (not possible if the adaptee is `final` and not supported well because Java allows only single inheritance).

```java id="’wini40"
class BankAdapter extends LegacyBankGateway
        implements PaymentProcessor {

    @Override
    public void pay(double amount) {
        makePayment(amount);
    }
}
```

### Advantages

* Simpler in some cases.
* Slightly fewer objects.

### Disadvantages

* Limited by single inheritance.
* Less flexible than object adapters.

---

# Real-World Example

## Power Plug Adapter

Suppose:

* Laptop charger → 2-pin plug
* Wall socket → 3-pin socket

Without an adapter, the charger cannot connect.

```text id="’wini41"
Laptop Charger
      |
Power Adapter
      |
Wall Socket
```

The adapter converts one interface into another.

---

# Another Example: Java Collections

Java provides adapters like:

```java id="’wini42"
String[] names = {"A", "B", "C"};

List<String> list = Arrays.asList(names);
```

`Arrays.asList()` adapts an array into a `List`.

---

# Another Example: Java I/O

```java id="’wini43"
Reader reader =
    new InputStreamReader(System.in);
```

`InputStreamReader` adapts an `InputStream` into a `Reader`.

---

# Advantages

* Allows incompatible classes to work together.
* Reuses existing code.
* Avoids modifying third-party libraries.
* Promotes loose coupling.
* Follows the **Open/Closed Principle**.

---

# Disadvantages

* Adds an extra layer of abstraction.
* Too many adapters can make the design harder to understand.
* May introduce slight performance overhead.

---

# When to Use the Adapter Pattern

Use the Adapter Pattern when:

* You need to integrate third-party libraries.
* Existing classes have incompatible interfaces.
* You want to reuse legacy code.
* You cannot modify the adaptee.

### Common Use Cases

* Third-party API integration
* Legacy system integration
* Payment gateway integration
* Database driver wrappers
* Java I/O (`InputStreamReader`)
* `Arrays.asList()`
* Logging framework adapters
* Cloud SDK wrappers

---

# Adapter vs Decorator

| Adapter Pattern                        | Decorator Pattern                   |
| -------------------------------------- | ----------------------------------- |
| Changes an interface.                  | Adds new behavior.                  |
| Makes incompatible classes compatible. | Enhances an object's functionality. |
| Focuses on compatibility.              | Focuses on extensibility.           |

---

# Adapter vs Proxy

| Adapter Pattern                      | Proxy Pattern                              |
| ------------------------------------ | ------------------------------------------ |
| Converts one interface into another. | Controls access to an object.              |
| Solves compatibility issues.         | Adds security, caching, lazy loading, etc. |
| Client sees a different interface.   | Client sees the same interface.            |

---

# Adapter vs Facade

| Adapter Pattern                | Facade Pattern                |
| ------------------------------ | ----------------------------- |
| Converts interfaces.           | Simplifies a subsystem.       |
| Works with one existing class. | Coordinates multiple classes. |
| Solves incompatibility.        | Reduces complexity.           |

---

# Adapter vs Bridge

| Adapter Pattern                   | Bridge Pattern                                                   |
| --------------------------------- | ---------------------------------------------------------------- |
| Used after classes already exist. | Designed before implementation.                                  |
| Solves compatibility.             | Separates abstraction from implementation.                       |
| Integrates existing code.         | Enables independent evolution of abstraction and implementation. |

---

# Summary

The **Adapter Design Pattern** converts the interface of an existing class into another interface expected by the client.

Instead of modifying existing or third-party code, the adapter wraps the adaptee and translates requests, allowing incompatible classes to work together seamlessly.

The **Object Adapter**, which uses composition, is the preferred implementation in Java.

## Key Components

| Component   | Responsibility                                              |
| ----------- | ----------------------------------------------------------- |
| **Target**  | The interface expected by the client.                       |
| **Adaptee** | The existing class with an incompatible interface.          |
| **Adapter** | Converts the target interface into the adaptee's interface. |
| **Client**  | Uses only the target interface.                             |

### Pattern Flow

```text id="’wini44"
Client
   |
   v
Target Interface
   |
   v
Adapter
   |
Converts Request
   |
   v
Adaptee
```

---

## Key Takeaway

> **Use the Adapter Pattern when you need to make incompatible classes work together without modifying their source code. It acts as a translator between the client and an existing class by converting one interface into another.**
