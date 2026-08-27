# Abstract Factory Design Pattern

The **Abstract Factory Design Pattern** is a **creational design pattern** that provides an interface for creating **families of related or dependent objects** without specifying their concrete classes.

> **In simple words:**
> The Abstract Factory Pattern creates a group of related objects together while hiding their concrete implementations from the client.

---

# Why use the Abstract Factory Pattern?

Imagine you're building a **Cross-Platform UI Application**.

The application should support:

* Windows
* macOS

Each platform requires its own UI components.

| Windows          | macOS        |
| ---------------- | ------------ |
| Windows Button   | Mac Button   |
| Windows Checkbox | Mac Checkbox |

Without the Abstract Factory Pattern, you might write:

```java id="u4b2jg"
class Application {

    public void createUI(String os) {

        if (os.equals("Windows")) {
            WindowsButton button = new WindowsButton();
            WindowsCheckbox checkbox = new WindowsCheckbox();

        } else if (os.equals("Mac")) {
            MacButton button = new MacButton();
            MacCheckbox checkbox = new MacCheckbox();
        }
    }
}
```

## Problems

* Large `if-else` statements.
* Tight coupling with concrete classes.
* Difficult to add a new platform.
* Violates the **Open/Closed Principle**.

---

# Abstract Factory Pattern Solution

Instead of creating objects directly, create a **factory interface**.

Each platform has its own factory that creates the appropriate family of objects.

---

# Step 1: Create Product Interfaces

## Button

```java id="h1d9tw"
interface Button {
    void paint();
}
```

---

## Checkbox

```java id="l6v5xp"
interface Checkbox {
    void paint();
}
```

---

# Step 2: Implement Concrete Products

## Windows Button

```java id="1bnx0h"
class WindowsButton implements Button {

    @Override
    public void paint() {
        System.out.println("Rendering Windows Button");
    }
}
```

---

## Mac Button

```java id="xsyx0s"
class MacButton implements Button {

    @Override
    public void paint() {
        System.out.println("Rendering Mac Button");
    }
}
```

---

## Windows Checkbox

```java id="qgmd8f"
class WindowsCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("Rendering Windows Checkbox");
    }
}
```

---

## Mac Checkbox

```java id="85o4yw"
class MacCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("Rendering Mac Checkbox");
    }
}
```

---

# Step 3: Create the Abstract Factory

```java id="jg2x8x"
interface GUIFactory {

    Button createButton();

    Checkbox createCheckbox();
}
```

---

# Step 4: Implement Concrete Factories

## Windows Factory

```java id="rjlwmw"
class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
```

---

## Mac Factory

```java id="2qv8m1"
class MacFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
```

---

# Step 5: Client Code

```java id="mz95tj"
class Application {

    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void render() {
        button.paint();
        checkbox.paint();
    }
}
```

---

# Step 6: Main Class

```java id="m6d4ga"
public class Main {

    public static void main(String[] args) {

        GUIFactory factory = new WindowsFactory();

        Application app = new Application(factory);

        app.render();
    }
}
```

### Output

```text id="mjlwmr"
Rendering Windows Button
Rendering Windows Checkbox
```

If you replace:

```java id="wjlwmr"
GUIFactory factory = new MacFactory();
```

Output becomes:

```text id="jlwmz1"
Rendering Mac Button
Rendering Mac Checkbox
```

No changes are required in the `Application` class.

---

# How Abstract Factory Works

```text id="jlwmt2"
Client
   |
   v
Abstract Factory
   |
   +---------------------------+
   |                           |
Windows Factory          Mac Factory
   |                           |
   |                           |
   +---------+       +---------+
             |       |
     Windows Products   Mac Products
```

The client interacts only with the factory interface.

---

# Class Diagram

```text id="jlwmt3"
                 GUIFactory
        +------------------------+
        | createButton()         |
        | createCheckbox()       |
        +------------------------+
                ^
                |
      -------------------------
      |                       |
WindowsFactory          MacFactory
      |                       |
      |                       |
      v                       v
 WindowsButton          MacButton
 WindowsCheckbox        MacCheckbox
```

---

# Real-World Example

## Furniture Store

Suppose a furniture company sells two product families.

### Modern Furniture

* Modern Chair
* Modern Sofa
* Modern Table

### Victorian Furniture

* Victorian Chair
* Victorian Sofa
* Victorian Table

Each family should be used together.

```text id="jlwmt4"
Furniture Factory
       |
       +----------------------+
       |                      |
Modern Factory       Victorian Factory
       |                      |
       +----------+-----------+
                  |
        Chair, Sofa, Table
```

---

# Another Example: Database Drivers

Suppose your application supports multiple databases.

## MySQL Factory

* MySQL Connection
* MySQL Command
* MySQL Transaction

## PostgreSQL Factory

* PostgreSQL Connection
* PostgreSQL Command
* PostgreSQL Transaction

The client simply chooses the correct factory.

---

# Advantages

* Creates families of related objects.
* Promotes consistency between related products.
* Removes large `if-else` statements.
* Decouples client from concrete classes.
* Follows the **Open/Closed Principle**.

---

# Disadvantages

* Increases the number of classes.
* Adding a new product type requires changing every factory.
* More complex than the Factory Method Pattern.

---

# When to Use the Abstract Factory Pattern

Use the Abstract Factory Pattern when:

* Objects belong to related families.
* Products should always be used together.
* You want to switch between entire product families.
* You want to hide concrete implementations from clients.

### Common Use Cases

* Cross-platform GUI applications
* Database driver libraries
* Theme engines (Light/Dark)
* Furniture systems
* Cloud provider SDKs
* Payment gateway integrations
* Operating system-specific implementations

---

# Factory Method vs Abstract Factory

| Factory Method           | Abstract Factory                          |
| ------------------------ | ----------------------------------------- |
| Creates **one** product. | Creates **families** of related products. |
| Has one factory method.  | Has multiple factory methods.             |
| Simpler.                 | More flexible but more complex.           |

---

# Abstract Factory vs Builder

| Builder                                 | Abstract Factory                  |
| --------------------------------------- | --------------------------------- |
| Builds one complex object step by step. | Creates multiple related objects. |
| Focuses on object construction.         | Focuses on product families.      |

---

# Abstract Factory vs Prototype

| Prototype                   | Abstract Factory                      |
| --------------------------- | ------------------------------------- |
| Creates objects by cloning. | Creates objects using factories.      |
| Focuses on copying objects. | Focuses on grouping related products. |

---

# Summary

The **Abstract Factory Design Pattern** provides an interface for creating **families of related objects** without exposing their concrete implementations.

The client depends only on the abstract factory and abstract product interfaces. By changing the factory implementation, the entire family of products can be switched without modifying client code.

## Key Components

| Component            | Responsibility                                         |
| -------------------- | ------------------------------------------------------ |
| **Abstract Factory** | Declares methods for creating related products.        |
| **Concrete Factory** | Creates a specific family of products.                 |
| **Abstract Product** | Defines the interface for each product type.           |
| **Concrete Product** | Implements a specific product.                         |
| **Client**           | Uses only the abstract factory and product interfaces. |

### Pattern Flow

```text id="jlwmt5"
Client
   |
   v
Abstract Factory
   |
   +----------------------------+
   |                            |
Windows Factory           Mac Factory
   |                            |
   +-----------+----------------+
               |
      Button + Checkbox
```

---

## Key Takeaway

> **Provide an interface for creating families of related objects without specifying their concrete classes. This allows entire product families to be swapped by changing only the factory implementation.**
