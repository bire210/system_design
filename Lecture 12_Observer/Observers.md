# Observer Design Pattern

The **Observer Design Pattern** is a **behavioral design pattern** that defines a **one-to-many dependency** between objects. When one object (called the **Subject**) changes its state, all of its dependent objects (called **Observers**) are automatically notified and updated.

> **In simple words:**
> The Observer Pattern allows one object to notify multiple objects automatically whenever its state changes.

---

# Why use the Observer Pattern?

Imagine you're building a **Weather Monitoring System**.

Whenever the weather changes:

* Mobile App should update.
* TV Display should update.
* Website should update.

Without the Observer Pattern, you might write:

```java
class WeatherStation {

    public void setTemperature(int temperature) {

        System.out.println("Temperature changed to " + temperature);

        // Notify everyone manually
        MobileDisplay.update(temperature);
        TVDisplay.update(temperature);
        WebsiteDisplay.update(temperature);
    }
}
```

## Problems

* `WeatherStation` is tightly coupled to all display classes.
* Every new display requires modifying `WeatherStation`.
* Difficult to maintain.
* Violates the **Open/Closed Principle**.

---

# Observer Pattern Solution

Instead of directly calling each display, the **Subject** maintains a list of observers.

Whenever its state changes, it automatically notifies all registered observers.

---

# Step 1: Create the Observer Interface

```java
interface Observer {
    void update(int temperature);
}
```

---

# Step 2: Create the Subject Interface

```java
interface Subject {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
```

---

# Step 3: Implement the Subject

```java
import java.util.ArrayList;
import java.util.List;

class WeatherStation implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {

        for (Observer observer : observers) {
            observer.update(temperature);
        }
    }

    public void setTemperature(int temperature) {

        this.temperature = temperature;

        System.out.println("Temperature changed to " + temperature);

        notifyObservers();
    }
}
```

---

# Step 4: Implement Concrete Observers

## Mobile Display

```java
class MobileDisplay implements Observer {

    @Override
    public void update(int temperature) {
        System.out.println("Mobile Display: " + temperature + "°C");
    }
}
```

---

## TV Display

```java
class TVDisplay implements Observer {

    @Override
    public void update(int temperature) {
        System.out.println("TV Display: " + temperature + "°C");
    }
}
```

---

## Website Display

```java
class WebsiteDisplay implements Observer {

    @Override
    public void update(int temperature) {
        System.out.println("Website Display: " + temperature + "°C");
    }
}
```

---

# Step 5: Client Code

```java
public class Main {

    public static void main(String[] args) {

        WeatherStation station = new WeatherStation();

        Observer mobile = new MobileDisplay();
        Observer tv = new TVDisplay();
        Observer website = new WebsiteDisplay();

        station.addObserver(mobile);
        station.addObserver(tv);
        station.addObserver(website);

        station.setTemperature(30);

        System.out.println();

        station.setTemperature(35);
    }
}
```

### Output

```text
Temperature changed to 30
Mobile Display: 30°C
TV Display: 30°C
Website Display: 30°C

Temperature changed to 35
Mobile Display: 35°C
TV Display: 35°C
Website Display: 35°C
```

---

# Class Diagram

```text
                    Subject
        +-----------------------------+
        | addObserver()               |
        | removeObserver()            |
        | notifyObservers()           |
        +-----------------------------+
                   ^
                   |
            WeatherStation
                   |
      -----------------------------
      |             |             |
      v             v             v
 MobileDisplay   TVDisplay   WebsiteDisplay
      ^             ^             ^
      |             |             |
            Observer
```

The **WeatherStation** maintains a list of observers and notifies all of them whenever the temperature changes.

---

# Notification Flow

```text
Temperature Changes
        |
        v
 WeatherStation
        |
 notifyObservers()
        |
        +--------------------------+
        |            |             |
        v            v             v
 Mobile App     TV Display    Website
```

All registered observers receive the update automatically.

---

# Real-World Example

## YouTube Channel Subscription

Suppose you subscribe to a YouTube channel.

When the creator uploads a new video:

* Subscriber A gets notified.
* Subscriber B gets notified.
* Subscriber C gets notified.

The YouTube channel doesn't know how each subscriber reacts. It simply sends notifications to all subscribers.

```text
YouTube Channel
      |
      +----------------------+
      |          |           |
      v          v           v
 Subscriber1 Subscriber2 Subscriber3
```

---

# Another Example: Stock Price Notification

Suppose users subscribe to stock updates.

Whenever the stock price changes:

* Mobile App receives an update.
* Email Service sends an email.
* SMS Service sends a text message.

```text
Stock Market
      |
 notifyObservers()
      |
      +-------------------------+
      |            |            |
      v            v            v
 Mobile App     Email       SMS
```

Each observer reacts independently.

---

# Advantages

* Loose coupling between Subject and Observers.
* Easy to add new observers without modifying the subject.
* Supports one-to-many relationships.
* Follows the **Open/Closed Principle**.
* Promotes event-driven programming.

---

# Disadvantages

* Large numbers of observers may impact performance.
* Notification order is generally not guaranteed.
* Debugging can be difficult when many observers are involved.

---

# When to Use the Observer Pattern

Use the Observer Pattern when:

* One object's state changes should automatically update multiple objects.
* You want loose coupling between the publisher and subscribers.
* You are building an event-driven system.
* Observers should be added or removed dynamically.

### Common Use Cases

* Weather monitoring systems
* YouTube subscriptions
* Stock market notifications
* Chat applications
* News feeds
* Event listeners in GUI frameworks
* Java Swing listeners
* Spring Application Events

---

# Observer vs Strategy Pattern

| Strategy Pattern                       | Observer Pattern                |
| -------------------------------------- | ------------------------------- |
| Chooses one algorithm.                 | Notifies multiple observers.    |
| Client selects the strategy.           | Observers subscribe themselves. |
| One strategy executes.                 | All observers receive updates.  |
| Focuses on interchangeable algorithms. | Focuses on event notification.  |

---

# Observer vs Chain of Responsibility

| Chain of Responsibility                    | Observer Pattern                       |
| ------------------------------------------ | -------------------------------------- |
| Request moves through handlers.            | Event is broadcast to all observers.   |
| Usually one handler processes the request. | Every registered observer is notified. |
| Processing stops when handled.             | Notification goes to all subscribers.  |

---

# Summary

The **Observer Design Pattern** establishes a **one-to-many relationship** between a **Subject** and multiple **Observers**.

Whenever the subject's state changes, it automatically notifies all registered observers without knowing their concrete implementations.

This creates a flexible, loosely coupled, and extensible event-notification mechanism.

## Key Components

| Component             | Responsibility                                         |
| --------------------- | ------------------------------------------------------ |
| **Subject**           | Maintains a list of observers and sends notifications. |
| **Observer**          | Defines the update method.                             |
| **Concrete Subject**  | Stores state and notifies observers.                   |
| **Concrete Observer** | Receives updates and reacts accordingly.               |
| **Client**            | Creates observers and registers them with the subject. |

### Pattern Flow

```text
Client
   |
   v
WeatherStation (Subject)
   |
notifyObservers()
   |
   +-----------------------------+
   |             |               |
   v             v               v
Mobile       TV Display      Website
 (Observer)   (Observer)     (Observer)
```

---

## Key Takeaway

> **Define a one-to-many dependency between objects so that when one object's state changes, all dependent objects are automatically notified and updated.**
