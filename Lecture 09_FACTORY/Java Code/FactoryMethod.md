# Factory Method Design Pattern

The **Factory Method Design Pattern** (commonly called the **Factory Pattern**) is a **creational design pattern** that provides an interface for creating objects without exposing the object creation logic to the client.

Instead of using the `new` keyword directly, the client asks a **Factory** to create the required object.

> **In simple words:**
> The Factory Pattern delegates object creation to a factory class, allowing the client to work with interfaces rather than concrete implementations.

---

# Why use the Factory Pattern?

Imagine you're building a **Notification Service**.

The application supports multiple notification types:

* Email
* SMS
* Push Notification

Without the Factory Pattern, you might write:

```java id="zv6t0j"
class NotificationService {

    public void sendNotification(String type) {

        if (type.equals("EMAIL")) {
            EmailNotification notification = new EmailNotification();
            notification.send();

        } else if (type.equals("SMS")) {
            SMSNotification notification = new SMSNotification();
            notification.send();

        } else if (type.equals("PUSH")) {
            PushNotification notification = new PushNotification();
            notification.send();
        }
    }
}
```

## Problems

* Large `if-else` or `switch` statements.
* Tight coupling with concrete classes.
* Difficult to add new notification types.
* Violates the **Open/Closed Principle**.

---

# Factory Pattern Solution

Move the object creation logic into a **Factory**.

The client requests an object from the factory instead of creating it directly.

---

# Step 1: Create the Product Interface

```java id="j2phnq"
interface Notification {

    void send();
}
```

---

# Step 2: Create Concrete Products

## Email Notification

```java id="2vvaxd"
class EmailNotification implements Notification {

    @Override
    public void send() {
        System.out.println("Sending Email Notification");
    }
}
```

---

## SMS Notification

```java id="v2wkl7"
class SMSNotification implements Notification {

    @Override
    public void send() {
        System.out.println("Sending SMS Notification");
    }
}
```

---

## Push Notification

```java id="g1n6fd"
class PushNotification implements Notification {

    @Override
    public void send() {
        System.out.println("Sending Push Notification");
    }
}
```

---

# Step 3: Create the Factory

```java id="q7knyg"
class NotificationFactory {

    public static Notification createNotification(String type) {

        if (type.equalsIgnoreCase("EMAIL")) {
            return new EmailNotification();

        } else if (type.equalsIgnoreCase("SMS")) {
            return new SMSNotification();

        } else if (type.equalsIgnoreCase("PUSH")) {
            return new PushNotification();
        }

        throw new IllegalArgumentException("Invalid Notification Type");
    }
}
```

---

# Step 4: Client Code

```java id="jlwm9h"
public class Main {

    public static void main(String[] args) {

        Notification notification =
                NotificationFactory.createNotification("EMAIL");

        notification.send();

        notification =
                NotificationFactory.createNotification("SMS");

        notification.send();
    }
}
```

### Output

```text id="e8rpl6"
Sending Email Notification
Sending SMS Notification
```

---

# How Factory Pattern Works

```text id="i9mb5v"
Client
   |
Requests Object
   |
   v
NotificationFactory
   |
Chooses Object
   |
   +------------------------------+
   |              |               |
Email         SMS          Push Notification
```

The client doesn't know how the object is created—it only knows that it gets a `Notification`.

---

# Class Diagram

```text id="jlwm9i"
              Notification
           +---------------+
           | send()        |
           +---------------+
                  ^
                  |
      -----------------------------
      |             |             |
EmailNotification SMSNotification PushNotification

                  ^
                  |
        NotificationFactory
                  ^
                  |
                Client
```

---

# Real-World Example

## Payment Gateway

Suppose an application supports:

* Credit Card
* PayPal
* UPI

The client asks the factory for the correct payment processor.

```text id="jlwm9j"
PaymentFactory
      |
      +----------------------+
      |          |           |
 CreditCard   PayPal       UPI
```

The client doesn't know which implementation is returned.

---

# Another Example: Database Connections

Suppose your application supports:

* MySQL
* PostgreSQL
* Oracle

```text id="jlwm9k"
DatabaseFactory
      |
      +---------------------------+
      |            |              |
   MySQL      PostgreSQL      Oracle
```

The application simply requests a database connection from the factory.

---

# Advantages

* Encapsulates object creation logic.
* Reduces coupling between client and concrete classes.
* Makes code easier to maintain.
* Follows the **Open/Closed Principle** (with proper extensions).
* Improves readability.

---

# Disadvantages

* Introduces an extra factory class.
* Simple factories often use `if-else` or `switch`.
* Adding many product types can make the factory large.
* More classes compared to direct object creation.

---

# When to Use the Factory Pattern

Use the Factory Pattern when:

* The client shouldn't know how objects are created.
* Different implementations of the same interface exist.
* Object creation logic is complex.
* You want to centralize object creation.

### Common Use Cases

* Notification systems
* Database connections
* Payment gateways
* Logging frameworks
* Parser creation
* Cloud provider SDKs
* File readers
* Report generators

---

# Factory Pattern vs Abstract Factory

| Factory Pattern          | Abstract Factory                          |
| ------------------------ | ----------------------------------------- |
| Creates **one** product. | Creates **families** of related products. |
| One factory method.      | Multiple factory methods.                 |
| Simpler.                 | More flexible but more complex.           |

---

# Factory Pattern vs Builder

| Builder Pattern                         | Factory Pattern                      |
| --------------------------------------- | ------------------------------------ |
| Builds one complex object step by step. | Creates one object in a single step. |
| Focuses on object construction.         | Focuses on object creation.          |

---

# Factory Pattern vs Prototype

| Prototype Pattern                    | Factory Pattern                     |
| ------------------------------------ | ----------------------------------- |
| Creates objects by cloning.          | Creates objects using constructors. |
| Optimizes expensive object creation. | Encapsulates object creation logic. |

---

# Factory Pattern vs Singleton

| Singleton Pattern                 | Factory Pattern                |
| --------------------------------- | ------------------------------ |
| Ensures only one instance exists. | Creates new objects on demand. |
| Controls object count.            | Controls object creation.      |

---

# Summary

The **Factory Method Design Pattern** encapsulates object creation inside a factory class.

Instead of creating objects directly with the `new` keyword, the client requests the required object from the factory.

This reduces coupling, centralizes creation logic, and makes it easier to add new implementations without changing client code.

## Key Components

| Component            | Responsibility                                            |
| -------------------- | --------------------------------------------------------- |
| **Product**          | Defines the common interface.                             |
| **Concrete Product** | Implements the product interface.                         |
| **Factory**          | Creates and returns the appropriate product.              |
| **Client**           | Requests objects from the factory and uses the interface. |

### Pattern Flow

```text id="jlwm9l"
Client
   |
Requests Product
   |
   v
Factory
   |
Chooses Product
   |
   +--------------------------+
   |            |             |
 Email         SMS         Push
```

---

## Factory Method vs Simple Factory

Many developers refer to the example above as the **Factory Pattern** or **Simple Factory**.

The **GoF Factory Method Pattern** is slightly different:

* The factory is typically an **abstract class or interface**.
* Subclasses decide which concrete product to create.

Example:

```java id="jlwm9m"
abstract class NotificationFactory {

    abstract Notification createNotification();
}

class EmailFactory extends NotificationFactory {

    @Override
    Notification createNotification() {
        return new EmailNotification();
    }
}
```

This follows the original **Gang of Four (GoF)** Factory Method pattern more closely.

---

## Key Takeaway

> **Encapsulate object creation inside a factory so the client depends on abstractions instead of concrete classes. This makes the code easier to extend, maintain, and test.**
