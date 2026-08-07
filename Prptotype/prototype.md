# Prototype Design Pattern

The **Prototype Design Pattern** is a **creational design pattern** that creates new objects by **cloning an existing object (prototype)** instead of creating them from scratch using the `new` keyword.

> **In simple words:**
> The Prototype Pattern creates new objects by copying an existing object, making object creation faster and more flexible.

---

# Why use the Prototype Pattern?

Imagine you're building a **Game** where enemies have many properties:

* Name
* Health
* Weapon
* Armor
* Skills
* Position

Without the Prototype Pattern, every new enemy requires creating and initializing all fields.

```java id="3xw0yb"
class Enemy {

    String name;
    int health;
    String weapon;
    String armor;

    public Enemy(String name, int health, String weapon, String armor) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.armor = armor;
    }
}
```

Client Code:

```java id="tm4x4g"
Enemy enemy1 = new Enemy("Orc", 100, "Sword", "Iron Armor");
Enemy enemy2 = new Enemy("Orc", 100, "Sword", "Iron Armor");
Enemy enemy3 = new Enemy("Orc", 100, "Sword", "Iron Armor");
```

## Problems

* Repeated object creation.
* Expensive initialization if objects are complex.
* Duplicate configuration code.
* Difficult to maintain.

---

# Prototype Pattern Solution

Instead of creating objects from scratch, create one fully initialized object (prototype) and clone it whenever a new object is needed.

---

# Step 1: Create the Prototype Interface

```java id="0c2b6x"
interface Prototype {

    Prototype clone();
}
```

---

# Step 2: Implement the Prototype

```java id="i9a89f"
class Enemy implements Prototype {

    private String name;
    private int health;
    private String weapon;

    public Enemy(String name, int health, String weapon) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
    }

    @Override
    public Prototype clone() {
        return new Enemy(name, health, weapon);
    }

    @Override
    public String toString() {
        return name + " | " + health + " HP | " + weapon;
    }
}
```

---

# Step 3: Client Code

```java id="v9krdx"
public class Main {

    public static void main(String[] args) {

        Enemy original = new Enemy("Orc", 100, "Sword");

        Enemy enemy1 = (Enemy) original.clone();
        Enemy enemy2 = (Enemy) original.clone();
        Enemy enemy3 = (Enemy) original.clone();

        System.out.println(original);
        System.out.println(enemy1);
        System.out.println(enemy2);
        System.out.println(enemy3);
    }
}
```

### Output

```text id="psd3na"
Orc | 100 HP | Sword
Orc | 100 HP | Sword
Orc | 100 HP | Sword
Orc | 100 HP | Sword
```

Each cloned object is a separate instance with the same initial state.

---

# How Prototype Works

```text id="x0hrf6"
Original Object
      |
      | clone()
      |
      +------------+
      |            |
      v            v
 Clone 1       Clone 2
```

The prototype serves as a template for creating new objects.

---

# Class Diagram

```text id="3mpjlwm"
           Prototype
        +---------------+
        | clone()       |
        +---------------+
               ^
               |
            Enemy
               |
          clone()
```

The client clones the prototype instead of calling `new`.

---

# Shallow Copy vs Deep Copy

## Shallow Copy

Copies primitive fields and object references.

```java id="6zdbzh"
class Address {
    String city;
}

class Employee {

    String name;
    Address address;

    public Employee clone() {
        return new Employee(name, address);
    }
}
```

Both objects share the same `Address`.

```text id="ujpvry"
Employee 1
     |
     +------> Address

Employee 2
     |
     +------> Same Address
```

Changing the address in one object affects the other.

---

## Deep Copy

Copies the entire object graph.

```java id="jlwmnh"
class Address {

    String city;

    public Address(String city) {
        this.city = city;
    }

    public Address clone() {
        return new Address(city);
    }
}
```

```java id="ohhwwd"
class Employee {

    String name;
    Address address;

    public Employee clone() {
        return new Employee(name, address.clone());
    }
}
```

Each object has its own copy.

```text id="mzkhig"
Employee 1
     |
     +------> Address A

Employee 2
     |
     +------> Address B
```

Deep copy avoids shared mutable state.

---

# Java's Cloneable Interface

Java provides the `Cloneable` interface and `Object.clone()` method.

Example:

```java id="8b3tnm"
class Employee implements Cloneable {

    private String name;

    public Employee(String name) {
        this.name = name;
    }

    @Override
    protected Employee clone() throws CloneNotSupportedException {
        return (Employee) super.clone();
    }
}
```

> **Note:** In modern Java, many developers avoid `Cloneable` because it has several design issues. Copy constructors, factory methods, or custom `clone()` methods are generally preferred.

---

# Real-World Example

## PowerPoint Templates

You create one slide template with:

* Company logo
* Footer
* Background
* Font

Instead of creating every slide from scratch, you duplicate the template.

```text id="wim0gz"
Template Slide
      |
      +------------+
      |            |
      v            v
 Slide 1      Slide 2
```

---

# Another Example: Document Templates

A document editor provides templates:

* Resume
* Invoice
* Letter

When you create a new document, the application clones the selected template.

```text id="jlwm4j"
Resume Template
      |
      +------------+
      |            |
      v            v
 Resume A     Resume B
```

---

# Advantages

* Reduces expensive object creation.
* Avoids repeated initialization code.
* Easy to create many similar objects.
* Hides object creation complexity.
* Supports runtime object configuration.

---

# Disadvantages

* Deep copying can be difficult.
* Circular object references require careful handling.
* Cloning complex object graphs may be expensive.
* Improper shallow copying can introduce bugs.

---

# When to Use the Prototype Pattern

Use the Prototype Pattern when:

* Object creation is expensive.
* Many similar objects are needed.
* Objects should be created dynamically at runtime.
* You want to avoid repeating initialization logic.

### Common Use Cases

* Game characters
* Document templates
* Presentation templates
* Graphics editors
* Shape duplication
* Configuration objects
* Workflow templates
* UI component cloning

---

# Prototype vs Singleton Pattern

| Singleton Pattern         | Prototype Pattern                  |
| ------------------------- | ---------------------------------- |
| Only one instance exists. | Creates many instances by cloning. |
| Shared object.            | Independent copied objects.        |
| Controls object count.    | Optimizes object creation.         |

---

# Prototype vs Factory Pattern

| Factory Pattern                         | Prototype Pattern                             |
| --------------------------------------- | --------------------------------------------- |
| Creates new objects using constructors. | Creates new objects by cloning existing ones. |
| Factory decides how to create objects.  | Prototype object knows how to clone itself.   |
| Good for different object types.        | Good for similar objects with shared state.   |

---

# Summary

The **Prototype Design Pattern** creates new objects by cloning an existing object instead of constructing a new one.

This is especially useful when object creation is expensive or when many similar objects are needed.

Choosing between **shallow copy** and **deep copy** is an important design decision depending on whether referenced objects should be shared.

## Key Components

| Component              | Responsibility                             |
| ---------------------- | ------------------------------------------ |
| **Prototype**          | Declares the cloning method.               |
| **Concrete Prototype** | Implements the cloning logic.              |
| **Client**             | Creates new objects by cloning prototypes. |

### Pattern Flow

```text id="i0gszm"
Client
   |
   v
Prototype
   |
clone()
   |
   +----------------------+
   |                      |
   v                      v
Clone 1               Clone 2
```

---

## Key Takeaway

> **Create new objects by copying an existing prototype instead of creating them from scratch, improving performance and reducing duplicate initialization logic. Prefer deep copies when objects contain mutable references that should not be shared.**
