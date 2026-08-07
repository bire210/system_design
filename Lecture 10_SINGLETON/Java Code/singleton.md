# Singleton Design Pattern

The **Singleton Design Pattern** is a **creational design pattern** that ensures a class has **only one instance** throughout the application and provides a **global access point** to that instance.

> **In simple words:**
> The Singleton Pattern guarantees that only one object of a class is ever created, and everyone uses that same object.

---

# Why use the Singleton Pattern?

Imagine you're building an application with a **Logger**.

Every part of the application wants to write logs.

Without the Singleton Pattern, you might write:

```java id="d2x8wq"
class Logger {

    public void log(String message) {
        System.out.println(message);
    }
}
```

Client Code:

```java id="2v0c5p"
public class Main {

    public static void main(String[] args) {

        Logger logger1 = new Logger();
        Logger logger2 = new Logger();

        System.out.println(logger1 == logger2);

        logger1.log("Application Started");
    }
}
```

### Output

```text id="9nxmju"
false
Application Started
```

## Problems

* Multiple logger objects are created.
* Unnecessary memory usage.
* Difficult to manage shared resources.
* Different parts of the application may maintain inconsistent state.

---

# Singleton Pattern Solution

Instead of allowing anyone to create objects using `new`, the class controls object creation itself.

The class:

* Makes its constructor **private**.
* Creates only one instance.
* Provides a public method to access that instance.

---

# Step 1: Create the Singleton Class

```java id="d73n4m"
class Logger {

    private static Logger instance;

    private Logger() {
    }

    public static Logger getInstance() {

        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    public void log(String message) {
        System.out.println(message);
    }
}
```

---

# Step 2: Client Code

```java id="7cjlwm"
public class Main {

    public static void main(String[] args) {

        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        System.out.println(logger1 == logger2);

        logger1.log("Application Started");
    }
}
```

### Output

```text id="e0kz6n"
true
Application Started
```

Only **one** `Logger` object exists.

---

# How Singleton Works

```text id="g2m09x"
Client
   |
   v
Logger.getInstance()
   |
instance == null ?
   |
Yes -------------> Create Object
 |
No
 |
Return Existing Object
```

The first call creates the object. Every subsequent call returns the same object.

---

# Class Diagram

```text id="x5vylw"
             +----------------------+
             |      Logger          |
             +----------------------+
             | - instance : Logger  |
             +----------------------+
             | - Logger()           |
             | + getInstance()      |
             | + log()              |
             +----------------------+
```

---

# Why is the Constructor Private?

If the constructor were public:

```java id="x2g6zb"
Logger logger = new Logger();
```

Anyone could create multiple objects.

Making the constructor **private** prevents object creation from outside the class.

Only the class itself can create its instance.

---

# Different Ways to Implement Singleton

## 1. Lazy Initialization

The object is created only when it is first needed.

```java id="v0h9p2"
class Singleton {

    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {

        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```

### Pros

* Saves memory.
* Object created only when required.

### Cons

* Not thread-safe.

---

## 2. Eager Initialization

The object is created when the class is loaded.

```java id="8e8xsi"
class Singleton {

    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

### Pros

* Simple.
* Thread-safe because class loading is thread-safe.

### Cons

* Object created even if never used.

---

## 3. Thread-Safe Singleton

```java id="m2jjfo"
class Singleton {

    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {

        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```

### Pros

* Thread-safe.

### Cons

* Synchronization adds performance overhead.

---

## 4. Double-Checked Locking (Recommended)

```java id="4g6dhn"
class Singleton {

    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {

        if (instance == null) {

            synchronized (Singleton.class) {

                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
```

### Why `volatile`?

Without `volatile`, one thread may see a partially initialized object because of instruction reordering.

`volatile` prevents this and guarantees visibility across threads.

### Pros

* Thread-safe.
* Better performance than synchronizing every call.
* Recommended for lazy initialization in multithreaded applications.

---

## 5. Bill Pugh Singleton (Best Practice)

Uses a static inner helper class.

```java id="1wscji"
class Singleton {

    private Singleton() {}

    private static class SingletonHelper {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```

### Why is it good?

* Lazy initialization.
* Thread-safe.
* No synchronization overhead.
* Relies on JVM class loading guarantees.

This is one of the most commonly recommended implementations in Java.

---

## 6. Enum Singleton (Safest)

```java id="icr0zw"
enum Singleton {

    INSTANCE;

    public void display() {
        System.out.println("Singleton Instance");
    }
}
```

Usage:

```java id="pgtl78"
public class Main {

    public static void main(String[] args) {

        Singleton.INSTANCE.display();
    }
}
```

### Pros

* Thread-safe.
* Protects against serialization issues.
* Protects against reflection attacks.
* Very simple.

Joshua Bloch recommends this approach in **Effective Java**.

---

# Real-World Examples

## Database Connection Manager

Only one object manages database connections.

```text id="rwgt0d"
Application
     |
     v
DatabaseConnectionManager (Singleton)
     |
     v
Database
```

---

## Configuration Manager

Configuration is loaded once and shared across the application.

```text id="8b6xlb"
Application
      |
      v
ConfigurationManager
      |
      +---- Reads config.properties once
```

---

## Logger

Every component writes logs using the same logger instance.

```text id="1a4iy5"
Service A
      |
Service B
      |
Service C
      |
      v
Shared Logger
```

---

# Advantages

* Ensures only one instance exists.
* Saves memory.
* Provides a global access point.
* Useful for managing shared resources.
* Centralizes configuration and state.

---

# Disadvantages

* Introduces global state.
* Difficult to unit test because of shared state.
* Can become a bottleneck in concurrent applications.
* Violates the **Single Responsibility Principle** if it manages too much.
* Overuse can make code tightly coupled.

---

# When to Use the Singleton Pattern

Use the Singleton Pattern when:

* Exactly one instance of a class is required.
* The instance needs to be shared across the application.
* You need centralized management of a resource.

### Common Use Cases

* Logger
* Configuration Manager
* Cache Manager
* Database Connection Manager
* Thread Pool Manager
* Application Settings
* Printer Spooler
* Runtime Environment

---

# Singleton vs Factory Pattern

| Singleton Pattern                   | Factory Pattern                          |
| ----------------------------------- | ---------------------------------------- |
| Ensures one instance.               | Creates objects.                         |
| Controls object count.              | Controls object creation.                |
| Same object is returned every time. | May return different objects.            |
| Focuses on uniqueness.              | Focuses on encapsulating creation logic. |

---

# Singleton vs Prototype Pattern

| Singleton Pattern         | Prototype Pattern                   |
| ------------------------- | ----------------------------------- |
| Only one instance exists. | New objects are created by cloning. |
| Shared object.            | Multiple independent copies.        |

---

# Summary

The **Singleton Design Pattern** ensures that only one instance of a class exists and provides a global access point to that instance.

The class controls its own object creation by making the constructor private and exposing a static method that returns the single instance.

## Key Components

| Component                | Responsibility                                            |
| ------------------------ | --------------------------------------------------------- |
| **Singleton Class**      | Maintains the single instance and provides global access. |
| **Private Constructor**  | Prevents external object creation.                        |
| **Static Instance**      | Stores the only object.                                   |
| **Static getInstance()** | Returns the shared instance.                              |
| **Client**               | Retrieves the singleton using `getInstance()`.            |

### Pattern Flow

```text id="shq85h"
Client
   |
   v
Singleton.getInstance()
   |
instance exists?
   |
+---------------------+
|                     |
No                    Yes
|                     |
Create Object         Return Existing Object
        \             /
         \           /
          +---------+
          |
          v
     Singleton Instance
```

---

## Key Takeaway

> **Ensure a class has only one instance and provide a global access point to it. In Java, the preferred implementations are the Bill Pugh Singleton for most cases and the Enum Singleton when maximum safety against serialization and reflection is required.**


| Pattern                     | Category   | Primary Purpose                              |
| --------------------------- | ---------- | -------------------------------------------- |
| **Singleton**               | Creational | Ensure only one instance exists              |
| **Strategy**                | Behavioral | Select one algorithm at runtime              |
| **State**                   | Behavioral | Change behavior based on internal state      |
| **Chain of Responsibility** | Behavioral | Pass a request through multiple handlers     |
| **Observer**                | Behavioral | Notify multiple subscribers of state changes |
| **Command**                 | Behavioral | Encapsulate a request as an object           |
