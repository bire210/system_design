# Bridge Design Pattern

The **Bridge Design Pattern** is a **structural design pattern** that **separates an abstraction from its implementation**, allowing both to evolve independently.

> **In simple words:**
> The Bridge Pattern uses **composition instead of inheritance** to connect an abstraction with an implementation.

---

# Why use the Bridge Pattern?

Imagine you're building a system for different types of **Remote Controls** and different types of **Devices**.

You have:

### Remotes

* Basic Remote
* Advanced Remote

### Devices

* TV
* Radio

Without the Bridge Pattern, you might create:

```text
BasicTVRemote
BasicRadioRemote
AdvancedTVRemote
AdvancedRadioRemote
```

If you add more remotes and devices, the number of classes grows rapidly.

For example:

```text
2 Remotes × 2 Devices = 4 classes

3 Remotes × 4 Devices = 12 classes

5 Remotes × 10 Devices = 50 classes
```

This is called **class explosion**.

---

# Problems with Inheritance

You could try:

```text
          Remote
            |
     +------+------+
     |             |
 Basic           Advanced
     |             |
   +---+         +---+
   |   |         |   |
  TV Radio      TV Radio
```

But now the abstraction and implementation are tightly coupled.

If you add:

* New remote type
* New device type

you need additional subclasses.

---

# Bridge Pattern Solution

Separate the two dimensions:

```text
Remote Control       Device
      |                 |
      +------ Bridge ---+
```

The `Remote` contains a reference to a `Device`.

This is **composition**.

---

# Step 1: Create the Implementation Interface

The `Device` represents the implementation side.

```java id="b1a2c3"
interface Device {

    void turnOn();

    void turnOff();

    void setVolume(int volume);
}
```

---

# Step 2: Create Concrete Implementations

## TV

```java id="d4e5f6"
class TV implements Device {

    @Override
    public void turnOn() {
        System.out.println("TV is ON");
    }

    @Override
    public void turnOff() {
        System.out.println("TV is OFF");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("TV volume: " + volume);
    }
}
```

---

## Radio

```java id="g7h8i9"
class Radio implements Device {

    @Override
    public void turnOn() {
        System.out.println("Radio is ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Radio is OFF");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("Radio volume: " + volume);
    }
}
```

---

# Step 3: Create the Abstraction

The `Remote` represents the abstraction side.

```java id="j1k2l3"
abstract class Remote {

    protected Device device;

    public Remote(Device device) {
        this.device = device;
    }

    public void turnOn() {
        device.turnOn();
    }

    public void turnOff() {
        device.turnOff();
    }

    public void setVolume(int volume) {
        device.setVolume(volume);
    }
}
```

Notice:

```java
protected Device device;
```

The Remote **has a** Device.

This is the Bridge.

---

# Step 4: Create Refined Abstractions

## Basic Remote

```java id="m4n5o6"
class BasicRemote extends Remote {

    public BasicRemote(Device device) {
        super(device);
    }
}
```

---

## Advanced Remote

```java id="p7q8r9"
class AdvancedRemote extends Remote {

    public AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        device.setVolume(0);
        System.out.println("Device muted");
    }
}
```

---

# Step 5: Client Code

```java id="s1t2u3"
public class Main {

    public static void main(String[] args) {

        Device tv = new TV();

        Remote basicRemote =
                new BasicRemote(tv);

        basicRemote.turnOn();
        basicRemote.setVolume(20);
        basicRemote.turnOff();

        System.out.println();

        Device radio = new Radio();

        AdvancedRemote advancedRemote =
                new AdvancedRemote(radio);

        advancedRemote.turnOn();
        advancedRemote.setVolume(30);
        advancedRemote.mute();
        advancedRemote.turnOff();
    }
}
```

### Output

```text
TV is ON
TV volume: 20
TV is OFF

Radio is ON
Radio volume: 30
Device muted
Radio is OFF
```

---

# The Important Part

Notice that we can combine implementations freely.

```java
new BasicRemote(new TV());
```

```java
new BasicRemote(new Radio());
```

```java
new AdvancedRemote(new TV());
```

```java
new AdvancedRemote(new Radio());
```

We don't need four separate classes.

---

# How Bridge Pattern Works

```text
                    Abstraction
                  Remote
                     |
                     | has-a
                     v
                Implementation
                  Device
                     |
            +--------+--------+
            |                 |
            v                 v
           TV               Radio
```

And the abstraction can also have multiple variations:

```text
Remote
  |
  +-- BasicRemote
  |
  +-- AdvancedRemote
```

So we have **two independent hierarchies**.

---

# Class Diagram

```text
                    Abstraction
                  +-------------+
                  |   Remote    |
                  +-------------+
                  | - device    |
                  +-------------+
                  | turnOn()    |
                  | turnOff()   |
                  +-------------+
                         |
                         |
                +--------+--------+
                |                 |
                v                 v
          BasicRemote      AdvancedRemote


                    Bridge
                      |
                      v

                 Implementation
                  +----------+
                  | Device   |
                  +----------+
                  | turnOn() |
                  | turnOff()|
                  +----------+
                       ^
                       |
              +--------+--------+
              |                 |
              v                 v
             TV               Radio
```

---

# The Core Idea

The Bridge Pattern separates two dimensions that would otherwise create a large inheritance hierarchy.

```text
Dimension 1:
Remote Types

Basic
Advanced
Smart
Gaming


Dimension 2:
Device Types

TV
Radio
Projector
Speaker
```

Instead of:

```text
BasicTV
BasicRadio
BasicProjector
BasicSpeaker

AdvancedTV
AdvancedRadio
AdvancedProjector
AdvancedSpeaker

SmartTV
SmartRadio
...
```

We use:

```text
Remote -----------------> Device
  |                         |
  +-- Basic                 +-- TV
  +-- Advanced              +-- Radio
  +-- Smart                 +-- Projector
                            +-- Speaker
```

Now both dimensions can evolve independently.

---

# Real-World Example

## Payment System

Suppose your application supports different:

### Payment Types

* Online Payment
* Subscription Payment
* Refund Payment

### Payment Providers

* Stripe
* Razorpay
* PayPal

Without Bridge:

```text
OnlineStripePayment
OnlineRazorpayPayment
OnlinePayPalPayment

SubscriptionStripePayment
SubscriptionRazorpayPayment
SubscriptionPayPalPayment

RefundStripePayment
RefundRazorpayPayment
RefundPayPalPayment
```

That's:

```text
3 × 3 = 9 classes
```

With Bridge:

```text
Payment
   |
   +-- OnlinePayment
   +-- SubscriptionPayment
   +-- RefundPayment
            |
            v
      PaymentProvider
            |
       +----+----+--------+
       |         |        |
    Stripe    Razorpay  PayPal
```

Now you have separate hierarchies.

---

# Another Example: Notification System

### Notification Types

```text
EmailNotification
SMSNotification
PushNotification
```

### Providers

```text
Twilio
AWS SNS
Firebase
```

Bridge allows:

```text
Notification
     |
     +-- Email
     +-- SMS
     +-- Push
           |
           v
     NotificationProvider
           |
       +---+---+------+
       |       |      |
     Twilio   AWS   Firebase
```

You can add a new notification type without modifying providers.

---

# Advantages

* Reduces class explosion.
* Separates abstraction from implementation.
* Allows both hierarchies to evolve independently.
* Follows **Composition over Inheritance**.
* Makes code easier to extend.
* Reduces coupling.
* Improves maintainability.

---

# Disadvantages

* Adds additional abstraction.
* Can make simple systems more complicated.
* Requires careful identification of independent dimensions.

---

# When to Use Bridge Pattern

Use the Bridge Pattern when:

* You have two independent dimensions that can change.
* Inheritance is creating too many subclasses.
* You want abstraction and implementation to evolve independently.
* You want to use composition instead of inheritance.
* You anticipate new implementations or abstractions being added.

### Common Use Cases

* Remote controls and devices
* Payment systems
* Notification systems
* Database abstraction layers
* Cross-platform UI
* Graphics/rendering systems
* Cloud provider integrations
* Messaging systems

---

# Bridge vs Adapter

These two patterns are often confused.

| Bridge                                    | Adapter                                        |
| ----------------------------------------- | ---------------------------------------------- |
| Designed **before** the system grows.     | Usually introduced to integrate existing code. |
| Separates abstraction and implementation. | Converts an existing interface.                |
| Prevents class explosion.                 | Solves interface incompatibility.              |
| Uses composition intentionally.           | Often wraps an existing class.                 |

### Easy Way to Remember

```text
Bridge  → Separate two dimensions

Adapter → Make two incompatible interfaces work
```

---

# Bridge vs Decorator

| Bridge                                     | Decorator                           |
| ------------------------------------------ | ----------------------------------- |
| Separates abstraction from implementation. | Adds responsibilities to an object. |
| Creates independent hierarchies.           | Creates wrapper layers.             |
| Focuses on structural flexibility.         | Focuses on dynamic behavior.        |

---

# Bridge vs Strategy

Bridge and Strategy can look similar because both use composition.

| Bridge                                       | Strategy                                 |
| -------------------------------------------- | ---------------------------------------- |
| Structural pattern.                          | Behavioral pattern.                      |
| Separates abstraction from implementation.   | Allows interchangeable algorithms.       |
| Usually manages two independent hierarchies. | Usually focuses on one varying behavior. |

Example:

```text
Bridge:

Remote ---> Device
  |           |
Basic       TV
Advanced    Radio
```

Strategy:

```text
Payment ---> PaymentStrategy
                |
          +-----+------+
          |            |
        Card          UPI
```

The structure may look similar, but the **intent is different**.

---

# Bridge vs Inheritance

Without Bridge:

```text
Remote
  |
  +-- TVRemote
  |     |
  |     +-- BasicTVRemote
  |     +-- AdvancedTVRemote
  |
  +-- RadioRemote
        |
        +-- BasicRadioRemote
        +-- AdvancedRadioRemote
```

With Bridge:

```text
Remote                 Device
  |                       |
  +-- Basic              +-- TV
  +-- Advanced            +-- Radio
```

This dramatically reduces coupling.

---

# Summary

The **Bridge Design Pattern** separates an abstraction from its implementation so that both can change independently.

The key idea is to replace inheritance between the two dimensions with **composition**.

## Key Components

| Component                   | Responsibility                                    |
| --------------------------- | ------------------------------------------------- |
| **Abstraction**             | Defines the high-level interface.                 |
| **Refined Abstraction**     | Extends the abstraction with additional behavior. |
| **Implementation**          | Defines the low-level implementation interface.   |
| **Concrete Implementation** | Provides specific implementations.                |
| **Client**                  | Uses the abstraction.                             |

### Pattern Flow

```text
                 Abstraction
                      |
            +---------+---------+
            |                   |
       BasicRemote       AdvancedRemote
            |                   |
            +---------+---------+
                      |
                    Bridge
                      |
                      v
                Implementation
                      |
             +--------+--------+
             |                 |
            TV               Radio
```

---

# Key Takeaway

> **Use the Bridge Pattern when you have two dimensions of variation that should evolve independently. Separate the abstraction from its implementation using composition instead of creating a large inheritance hierarchy.**

### Easy Way to Remember

```text
Adapter   → Converts
Facade    → Simplifies
Decorator → Enhances
Proxy     → Controls
Composite → Organizes
Bridge    → Separates
```
