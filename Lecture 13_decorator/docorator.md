# Decorator Design Pattern

The **Decorator Design Pattern** is a **structural design pattern** that allows you to **add new behavior to an object dynamically** without modifying its existing code.

Instead of changing the original class or creating many subclasses, you **wrap** the object with one or more decorator objects that add extra functionality.

> **In simple words:**
> The Decorator Pattern lets you attach additional features to an object at runtime by wrapping it inside another object.

---

# Why use the Decorator Pattern?

Imagine you're building a **Coffee Ordering System**.

Customers can order:

* Plain Coffee
* Coffee + Milk
* Coffee + Sugar
* Coffee + Milk + Sugar
* Coffee + Milk + Whipped Cream
* Coffee + Sugar + Chocolate

Without the Decorator Pattern, you might create a separate class for every combination.

```text id="5xv8kq"
Coffee
CoffeeWithMilk
CoffeeWithSugar
CoffeeWithMilkAndSugar
CoffeeWithMilkAndChocolate
CoffeeWithSugarAndCream
CoffeeWithMilkSugarCream
...
```

## Problems

* Too many classes.
* Difficult to maintain.
* Adding a new topping creates many new combinations.
* Violates the **Open/Closed Principle**.

---

# Decorator Pattern Solution

Instead of creating subclasses for every combination, create **decorators**.

Each decorator adds one feature and wraps another `Coffee` object.

---

# Step 1: Create the Component Interface

```java id="0m3xva"
interface Coffee {

    String getDescription();

    double getCost();
}
```

---

# Step 2: Create the Concrete Component

```java id="29g5kc"
class SimpleCoffee implements Coffee {

    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return 100;
    }
}
```

---

# Step 3: Create the Decorator

```java id="jlwmdk"
abstract class CoffeeDecorator implements Coffee {

    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}
```

The decorator stores another `Coffee` object and delegates work to it.

---

# Step 4: Create Concrete Decorators

## Milk Decorator

```java id="jlwmdl"
class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 20;
    }
}
```

---

## Sugar Decorator

```java id="jlwmdm"
class SugarDecorator extends CoffeeDecorator {

    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 10;
    }
}
```

---

## Whipped Cream Decorator

```java id="jlwmdn"
class WhippedCreamDecorator extends CoffeeDecorator {

    public WhippedCreamDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Whipped Cream";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 30;
    }
}
```

---

# Step 5: Client Code

```java id="jlwmdo"
public class Main {

    public static void main(String[] args) {

        Coffee coffee = new SimpleCoffee();

        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        coffee = new WhippedCreamDecorator(coffee);

        System.out.println(coffee.getDescription());
        System.out.println("Cost = ₹" + coffee.getCost());
    }
}
```

### Output

```text id="jlwmdp"
Simple Coffee, Milk, Sugar, Whipped Cream
Cost = ₹160.0
```

---

# How Decorator Pattern Works

```text id="jmp1ha"
SimpleCoffee
      |
      v
MilkDecorator
      |
      v
SugarDecorator
      |
      v
WhippedCreamDecorator
```

Each decorator wraps the previous object and adds its own behavior.

---

# Class Diagram

```text id="jlwmdq"
                  Coffee
             +----------------+
             | getCost()      |
             | getDescription()|
             +----------------+
                     ^
                     |
             SimpleCoffee
                     ^
                     |
             CoffeeDecorator
                     ^
        ----------------------------
        |            |             |
 MilkDecorator SugarDecorator WhippedCreamDecorator
```

All decorators implement the same interface as the object they decorate.

---

# Real-World Example

## Pizza Toppings

A pizza can have:

* Cheese
* Onion
* Mushroom
* Paneer

Instead of creating:

```text id="jlwmdr"
CheesePizza
CheeseOnionPizza
CheeseOnionPaneerPizza
PaneerPizza
...
```

Use decorators:

```java id="jlwmds"
Pizza pizza = new PlainPizza();

pizza = new CheeseDecorator(pizza);
pizza = new OnionDecorator(pizza);
pizza = new PaneerDecorator(pizza);
```

---

# Another Example: Java I/O Streams

The Java I/O library uses the Decorator Pattern extensively.

```java id="jlwmdt"
InputStream input =
    new BufferedInputStream(
        new FileInputStream("data.txt"));
```

Or with multiple decorators:

```java id="jlwmdu"
InputStream input =
    new DataInputStream(
        new BufferedInputStream(
            new FileInputStream("data.txt")));
```

Each stream adds new functionality without changing the original `FileInputStream`.

---

# Advantages

* Add behavior dynamically at runtime.
* Avoid subclass explosion.
* Follows the **Open/Closed Principle**.
* Flexible combination of features.
* Promotes composition over inheritance.

---

# Disadvantages

* Creates many small classes.
* Debugging can be difficult because objects are wrapped.
* Decorator order can affect behavior.

---

# When to Use the Decorator Pattern

Use the Decorator Pattern when:

* Features should be added dynamically.
* Creating many subclasses is impractical.
* You want flexible combinations of behavior.
* You want to extend objects without modifying their code.

### Common Use Cases

* Coffee ordering systems
* Pizza toppings
* Java I/O Streams
* Logging frameworks
* Data compression
* Encryption layers
* GUI component enhancements
* Spring Security filters

---

# Decorator vs Inheritance

| Inheritance                    | Decorator                  |
| ------------------------------ | -------------------------- |
| Adds behavior at compile time. | Adds behavior at runtime.  |
| Creates many subclasses.       | Wraps objects dynamically. |
| Less flexible.                 | Highly flexible.           |

---

# Decorator vs Adapter

| Adapter Pattern                           | Decorator Pattern          |
| ----------------------------------------- | -------------------------- |
| Changes an interface.                     | Adds new behavior.         |
| Makes incompatible classes work together. | Enhances existing objects. |
| Focuses on compatibility.                 | Focuses on extensibility.  |

---

# Decorator vs Proxy

| Proxy Pattern                      | Decorator Pattern                |
| ---------------------------------- | -------------------------------- |
| Controls access to an object.      | Adds functionality to an object. |
| Same behavior with access control. | Enhanced behavior.               |
| Focuses on access.                 | Focuses on responsibilities.     |

---

# Decorator vs Composite

| Composite Pattern                               | Decorator Pattern                    |
| ----------------------------------------------- | ------------------------------------ |
| Represents a tree structure.                    | Wraps a single object.               |
| Treats groups and individual objects uniformly. | Adds responsibilities to one object. |

---

# Summary

The **Decorator Design Pattern** dynamically adds new behavior to an object by wrapping it inside one or more decorator objects.

Instead of creating numerous subclasses for every possible feature combination, decorators allow features to be mixed and matched at runtime.

The decorated object and all decorators implement the same interface, making them interchangeable.

## Key Components

| Component              | Responsibility                                |
| ---------------------- | --------------------------------------------- |
| **Component**          | Defines the common interface.                 |
| **Concrete Component** | The original object being decorated.          |
| **Decorator**          | Wraps a component and delegates requests.     |
| **Concrete Decorator** | Adds new behavior before or after delegation. |
| **Client**             | Wraps objects with decorators as needed.      |

### Pattern Flow

```text id="jlwmdv"
Client
   |
   v
WhippedCreamDecorator
        |
SugarDecorator
        |
MilkDecorator
        |
SimpleCoffee
```

Each decorator enhances the object while preserving the same interface.

---

## Key Takeaway

> **Use the Decorator Pattern to add responsibilities to objects dynamically without modifying their existing code. It provides a flexible alternative to subclassing by using object composition.**
| Pattern       | Purpose                                      | Example                       |
| ------------- | -------------------------------------------- | ----------------------------- |
| **Adapter**   | Convert one interface into another           | Legacy API Integration        |
| **Bridge**    | Separate abstraction from implementation     | Remote & TV                   |
| **Composite** | Treat individual and group objects uniformly | File System                   |
| **Decorator** | Add behavior dynamically                     | Coffee, Java I/O Streams      |
| **Facade**    | Simplify a complex subsystem                 | Home Theater                  |
| **Flyweight** | Share objects to reduce memory usage         | Text Editor Characters        |
| **Proxy**     | Control access to an object                  | Virtual Proxy, Security Proxy |
