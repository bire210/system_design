# Builder Design Pattern

The **Builder Design Pattern** is a **creational design pattern** that constructs **complex objects step by step**. It separates the construction process from the final object, allowing the same construction process to create different representations.

> **In simple words:**
> The Builder Pattern helps create complex objects with many optional parameters in a clean, readable, and flexible way.

---

# Why use the Builder Pattern?

Imagine you're creating a **Computer** object.

A computer has:

* CPU (Required)
* RAM (Required)
* Storage (Required)
* Graphics Card (Optional)
* Wi-Fi (Optional)
* Bluetooth (Optional)
* RGB Lighting (Optional)

Without the Builder Pattern, you might write:

```java id="4f2v1m"
class Computer {

    private String cpu;
    private int ram;
    private int storage;
    private boolean graphicsCard;
    private boolean wifi;
    private boolean bluetooth;
    private boolean rgbLighting;

    public Computer(
            String cpu,
            int ram,
            int storage,
            boolean graphicsCard,
            boolean wifi,
            boolean bluetooth,
            boolean rgbLighting) {

        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.graphicsCard = graphicsCard;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.rgbLighting = rgbLighting;
    }
}
```

Client Code:

```java id="m4w7sd"
Computer computer = new Computer(
        "Intel i7",
        16,
        512,
        true,
        true,
        false,
        true);
```

## Problems

* Constructor becomes very large.
* Hard to remember parameter order.
* Difficult to read.
* Optional parameters make constructors confusing.
* Leads to the **Telescoping Constructor Problem**.

---

# Builder Pattern Solution

Instead of passing everything to a constructor, create the object **step by step** using a Builder.

---

# Step 1: Create the Product

```java id="4v8m5w"
class Computer {

    private String cpu;
    private int ram;
    private int storage;
    private boolean graphicsCard;
    private boolean wifi;
    private boolean bluetooth;

    private Computer(Builder builder) {

        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.wifi = builder.wifi;
        this.bluetooth = builder.bluetooth;
    }

    @Override
    public String toString() {

        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram=" + ram +
                ", storage=" + storage +
                ", graphicsCard=" + graphicsCard +
                ", wifi=" + wifi +
                ", bluetooth=" + bluetooth +
                '}';
    }

    public static class Builder {

        // Required
        private String cpu;
        private int ram;
        private int storage;

        // Optional
        private boolean graphicsCard;
        private boolean wifi;
        private boolean bluetooth;

        public Builder(String cpu, int ram, int storage) {
            this.cpu = cpu;
            this.ram = ram;
            this.storage = storage;
        }

        public Builder graphicsCard(boolean value) {
            this.graphicsCard = value;
            return this;
        }

        public Builder wifi(boolean value) {
            this.wifi = value;
            return this;
        }

        public Builder bluetooth(boolean value) {
            this.bluetooth = value;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}
```

---

# Step 2: Client Code

```java id="98k5au"
public class Main {

    public static void main(String[] args) {

        Computer computer = new Computer.Builder(
                "Intel i7",
                16,
                512)
                .graphicsCard(true)
                .wifi(true)
                .bluetooth(false)
                .build();

        System.out.println(computer);
    }
}
```

### Output

```text id="dfk9n3"
Computer{
cpu='Intel i7',
ram=16,
storage=512,
graphicsCard=true,
wifi=true,
bluetooth=false
}
```

---

# How Builder Pattern Works

```text id="s2jx8w"
Client
   |
   v
Builder
   |
Set Required Fields
   |
Set Optional Fields
   |
build()
   |
   v
Computer Object
```

The object is created only when `build()` is called.

---

# Class Diagram

```text id="9g2rt4"
             +-------------------+
             |    Computer       |
             +-------------------+
             | cpu               |
             | ram               |
             | storage           |
             | wifi              |
             | bluetooth         |
             +-------------------+
                     ^
                     |
             Computer.Builder
             +-------------------+
             | graphicsCard()    |
             | wifi()            |
             | bluetooth()       |
             | build()           |
             +-------------------+
```

The Builder gradually constructs the `Computer` object.

---

# Fluent Interface

Notice each setter returns `Builder`.

```java id="4mt0sv"
public Builder wifi(boolean value) {

    this.wifi = value;

    return this;
}
```

This enables method chaining.

```java id="jlwmab"
Computer computer =
    new Computer.Builder("Intel", 16, 512)
        .wifi(true)
        .bluetooth(true)
        .graphicsCard(true)
        .build();
```

This style is called a **Fluent Interface**.

---

# Real-World Example

## Pizza Builder

A pizza can have:

* Size
* Cheese
* Mushroom
* Onion
* Corn
* Paneer

Instead of having many constructors:

```text id="jlwmac"
Pizza()
Pizza(size)
Pizza(size, cheese)
Pizza(size, cheese, onion)
Pizza(size, cheese, onion, mushroom)
```

Use a builder:

```java id="jlwmad"
Pizza pizza = new Pizza.Builder("Large")
        .cheese(true)
        .onion(true)
        .mushroom(true)
        .build();
```

---

# Another Example: HTTP Request Builder

Many HTTP libraries use builders.

```java id="jlwmae"
HttpRequest request = new HttpRequest.Builder()
        .url("https://example.com")
        .method("POST")
        .header("Authorization", "token")
        .timeout(5000)
        .build();
```

Only the required fields need to be supplied.

---

# Builder with Director (Classic GoF)

The original GoF Builder pattern includes a **Director**.

The Director knows the construction steps.

```text id="jlwmaf"
Client
   |
   v
Director
   |
   v
Builder
   |
   v
Product
```

Example:

```java id="jlwmag"
class ComputerDirector {

    public Computer createGamingComputer() {

        return new Computer.Builder("Intel i9", 32, 1024)
                .graphicsCard(true)
                .wifi(true)
                .bluetooth(true)
                .build();
    }
}
```

In modern Java, the **Director** is often omitted because the client can directly use the Builder.

---

# Advantages

* Eliminates telescoping constructors.
* Makes object creation more readable.
* Supports optional parameters naturally.
* Creates immutable objects easily.
* Separates construction from representation.
* Easy to extend with new optional fields.

---

# Disadvantages

* Requires additional Builder class.
* More code than a simple constructor.
* Can be unnecessary for simple objects.

---

# When to Use the Builder Pattern

Use the Builder Pattern when:

* An object has many optional parameters.
* Constructors become too long.
* Object creation involves multiple steps.
* You want immutable objects.
* Readability is important.

### Common Use Cases

* Computer configuration
* Pizza ordering
* HTTP request builders
* SQL query builders
* Configuration objects
* User profile creation
* Complex DTOs
* Lombok `@Builder`

---

# Builder vs Factory Pattern

| Factory Pattern                    | Builder Pattern                           |
| ---------------------------------- | ----------------------------------------- |
| Creates one object in one step.    | Builds one object step by step.           |
| Focuses on object creation.        | Focuses on object construction.           |
| Returns different implementations. | Configures the same object incrementally. |

---

# Builder vs Prototype Pattern

| Prototype Pattern           | Builder Pattern                               |
| --------------------------- | --------------------------------------------- |
| Creates objects by cloning. | Creates objects from scratch step by step.    |
| Useful for similar objects. | Useful for complex objects with many options. |

---

# Builder vs Abstract Factory

| Abstract Factory                     | Builder Pattern             |
| ------------------------------------ | --------------------------- |
| Creates families of related objects. | Creates one complex object. |
| Multiple products.                   | Single product.             |

---

# Summary

The **Builder Design Pattern** constructs complex objects step by step instead of using large constructors.

It separates the construction logic from the final object, making the code more readable, maintainable, and flexible.

The Builder Pattern is particularly useful when an object has many optional parameters or when different configurations of the same object are needed.

## Key Components

| Component                 | Responsibility                                                        |
| ------------------------- | --------------------------------------------------------------------- |
| **Product**               | The complex object being created.                                     |
| **Builder**               | Provides methods to configure the product.                            |
| **Concrete Builder**      | Implements the construction logic (often the builder itself in Java). |
| **Director** *(Optional)* | Defines predefined construction sequences.                            |
| **Client**                | Uses the builder to create the object.                                |

### Pattern Flow

```text id="jlwmah"
Client
   |
   v
Builder
   |
Configure Fields
   |
build()
   |
   v
Product
```

---

## Key Takeaway

> **Use the Builder Pattern to construct complex objects step by step, especially when they have many optional parameters. It improves readability, avoids telescoping constructors, and makes object creation flexible and maintainable.**
