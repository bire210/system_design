# Facade Design Pattern

The **Facade Design Pattern** is a **structural design pattern** that provides a **simple interface to a complex subsystem**.

Instead of making the client interact with many different classes directly, the Facade provides a single entry point that coordinates those classes.

> **In simple words:**
> The Facade Pattern hides the complexity of a system behind a simple interface.

---

# Why use the Facade Pattern?

Imagine you're building a **Home Theater System**.

To watch a movie, you need to:

1. Turn on the TV.
2. Turn on the sound system.
3. Turn on the Blu-ray player.
4. Set the input source.
5. Set the volume.
6. Start the movie.

Without a Facade, the client needs to know about every subsystem.

```java
id="f1x8z2"
TV tv = new TV();
SoundSystem soundSystem = new SoundSystem();
DVDPlayer dvdPlayer = new DVDPlayer();

tv.on();
soundSystem.on();
dvdPlayer.on();

tv.setInput("HDMI");
soundSystem.setVolume(20);
dvdPlayer.play();
```

## Problems

* Client needs to know about many classes.
* Client knows the internal workflow.
* Too much complexity.
* Changes to the subsystem can affect the client.
* Difficult to use.

---

# Facade Pattern Solution

Create a `HomeTheaterFacade`.

The client only needs to call:

```java
homeTheater.watchMovie("Avengers");
```

The Facade handles all the internal steps.

---

# Step 1: Create the Subsystem Classes

## TV

```java
class TV {

    public void on() {
        System.out.println("TV is ON");
    }

    public void setInput(String input) {
        System.out.println("TV input set to " + input);
    }

    public void off() {
        System.out.println("TV is OFF");
    }
}
```

---

## Sound System

```java
class SoundSystem {

    public void on() {
        System.out.println("Sound System is ON");
    }

    public void setVolume(int volume) {
        System.out.println("Volume set to " + volume);
    }

    public void off() {
        System.out.println("Sound System is OFF");
    }
}
```

---

## DVD Player

```java
class DVDPlayer {

    public void on() {
        System.out.println("DVD Player is ON");
    }

    public void play(String movie) {
        System.out.println("Playing " + movie);
    }

    public void stop() {
        System.out.println("Movie stopped");
    }

    public void off() {
        System.out.println("DVD Player is OFF");
    }
}
```

---

# Step 2: Create the Facade

```java
class HomeTheaterFacade {

    private TV tv;
    private SoundSystem soundSystem;
    private DVDPlayer dvdPlayer;

    public HomeTheaterFacade(
            TV tv,
            SoundSystem soundSystem,
            DVDPlayer dvdPlayer) {

        this.tv = tv;
        this.soundSystem = soundSystem;
        this.dvdPlayer = dvdPlayer;
    }

    public void watchMovie(String movie) {

        System.out.println("Preparing to watch movie...");

        tv.on();
        tv.setInput("HDMI");

        soundSystem.on();
        soundSystem.setVolume(20);

        dvdPlayer.on();
        dvdPlayer.play(movie);
    }

    public void endMovie() {

        System.out.println("Shutting down...");

        dvdPlayer.stop();
        dvdPlayer.off();

        soundSystem.off();

        tv.off();
    }
}
```

The Facade coordinates all the subsystem classes.

---

# Step 3: Client Code

```java
public class Main {

    public static void main(String[] args) {

        TV tv = new TV();
        SoundSystem soundSystem = new SoundSystem();
        DVDPlayer dvdPlayer = new DVDPlayer();

        HomeTheaterFacade homeTheater =
                new HomeTheaterFacade(
                        tv,
                        soundSystem,
                        dvdPlayer);

        homeTheater.watchMovie("Avengers");

        System.out.println();

        homeTheater.endMovie();
    }
}
```

### Output

```text
Preparing to watch movie...
TV is ON
TV input set to HDMI
Sound System is ON
Volume set to 20
DVD Player is ON
Playing Avengers

Shutting down...
Movie stopped
DVD Player is OFF
Sound System is OFF
TV is OFF
```

---

# How Facade Pattern Works

```text
                    Client
                      |
                      v
             HomeTheaterFacade
                      |
          +-----------+-----------+
          |           |           |
          v           v           v
         TV      SoundSystem   DVDPlayer
```

The client only interacts with the Facade.

The Facade communicates with the underlying subsystem classes.

---

# Class Diagram

```text
                Client
                  |
                  v
        +----------------------+
        | HomeTheaterFacade    |
        +----------------------+
        | watchMovie()         |
        | endMovie()           |
        +----------------------+
             /      |      \
            /       |       \
           v        v        v
          TV   SoundSystem  DVDPlayer
```

---

# Real-World Example: Online Shopping

Suppose a user wants to place an order.

Behind the scenes, many systems are involved:

* Inventory
* Payment
* Shipping
* Notification

Without a Facade:

```java
inventory.checkStock();
payment.processPayment();
shipping.createShipment();
notification.sendConfirmation();
```

The client needs to understand all these services.

With a Facade:

```java
orderFacade.placeOrder(productId, quantity);
```

The Facade handles everything.

```text
Client
   |
   v
OrderFacade
   |
   +--------> Inventory
   |
   +--------> Payment
   |
   +--------> Shipping
   |
   +--------> Notification
```

---

# Another Example: Banking Application

A bank transfer may involve:

```text
Check Account
      |
Validate Balance
      |
Debit Sender
      |
Credit Receiver
      |
Send Notification
```

Instead of exposing all these operations:

```java
accountService.validate();
balanceService.check();
transactionService.debit();
transactionService.credit();
notificationService.send();
```

The client can simply call:

```java
bankFacade.transferMoney(
    sender,
    receiver,
    amount
);
```

---

# Facade in Spring Applications

In a backend application, a service layer often acts like a Facade.

For example:

```java
class OrderService {

    private InventoryService inventoryService;
    private PaymentService paymentService;
    private ShippingService shippingService;

    public void placeOrder(Order order) {

        inventoryService.reserve(order);
        paymentService.process(order);
        shippingService.ship(order);
    }
}
```

The controller doesn't need to know how inventory, payment, and shipping work internally.

```java
orderService.placeOrder(order);
```

Conceptually:

```text
Controller
    |
    v
OrderService (Facade-like layer)
    |
    +---- InventoryService
    |
    +---- PaymentService
    |
    +---- ShippingService
```

> A service class is not automatically a GoF Facade, but application service layers often play a similar role.

---

# Advantages

* Simplifies complex systems.
* Reduces coupling between client and subsystem.
* Provides a clean entry point.
* Makes client code easier to understand.
* Allows subsystem implementations to change internally.
* Improves maintainability.

---

# Disadvantages

* Facade can become too large.
* May become a "God class" if it handles too many responsibilities.
* Some advanced clients may still need direct access to subsystem classes.

---

# When to Use the Facade Pattern

Use the Facade Pattern when:

* A subsystem contains many classes.
* The client doesn't need to know internal details.
* You want to provide a simple API.
* You want to reduce coupling.
* Several operations must be performed together.

### Common Use Cases

* Payment processing
* Order processing
* Banking systems
* Home automation
* Media players
* API service layers
* Database operations
* Complex library wrappers

---

# Facade vs Adapter

| Adapter                                          | Facade                                          |
| ------------------------------------------------ | ----------------------------------------------- |
| Converts one interface into another.             | Provides a simplified interface.                |
| Usually works with one existing class/interface. | Usually coordinates multiple subsystem classes. |
| Solves compatibility problems.                   | Solves complexity problems.                     |
| Client expects one interface.                    | Client gets an easier interface.                |

### Simple Difference

```text
Adapter:

Client ---> Adapter ---> Existing Class
          "Translate"


Facade:

Client ---> Facade ---> Class A
                    ---> Class B
                    ---> Class C
          "Simplify"
```

---

# Facade vs Decorator

| Facade                          | Decorator                     |
| ------------------------------- | ----------------------------- |
| Simplifies a complex subsystem. | Adds behavior to an object.   |
| Usually hides multiple classes. | Usually wraps one component.  |
| Provides a simpler interface.   | Preserves the same interface. |
| Focuses on simplicity.          | Focuses on extension.         |

---

# Facade vs Proxy

| Facade                                           | Proxy                                                     |
| ------------------------------------------------ | --------------------------------------------------------- |
| Simplifies access to multiple subsystem objects. | Controls access to one real object.                       |
| Hides complexity.                                | Controls access, caching, security, lazy loading, etc.    |
| Usually exposes a simpler interface.             | Usually implements the same interface as the real object. |

---

# Facade vs Adapter vs Proxy

This is an important interview comparison.

| Pattern       | Main Question                                                 |
| ------------- | ------------------------------------------------------------- |
| **Adapter**   | "How can I make these incompatible interfaces work together?" |
| **Facade**    | "How can I make this complex subsystem easier to use?"        |
| **Proxy**     | "How can I control access to this object?"                    |
| **Decorator** | "How can I add behavior to this object?"                      |

---

# Summary

The **Facade Design Pattern** provides a simple interface over a complex subsystem.

The Facade does not necessarily replace or hide the subsystem completely. Advanced clients can still access subsystem classes directly if needed.

Its primary goal is to **reduce complexity and coupling** for the common use case.

## Key Components

| Component             | Responsibility                                                      |
| --------------------- | ------------------------------------------------------------------- |
| **Facade**            | Provides a simple interface to the subsystem.                       |
| **Subsystem Classes** | Perform the actual business operations.                             |
| **Client**            | Uses the Facade instead of coordinating subsystem classes directly. |

### Pattern Flow

```text
Client
   |
   v
Facade
   |
   +-------------------+
   |         |         |
   v         v         v
Service A  Service B  Service C
```

---

# Key Takeaway

> **Use the Facade Pattern when a system is complex and you want to provide clients with a simple, unified interface to perform common operations.**

### Easy Way to Remember

```text
Adapter  →  Converts
Facade   →  Simplifies
Decorator → Enhances
Proxy    →  Controls
Composite → Organizes
```
