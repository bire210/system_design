# Chain of Responsibility Design Pattern

The **Chain of Responsibility (CoR) Design Pattern** is a **behavioral design pattern** that allows a request to pass through a chain of handlers. Each handler decides whether it can process the request or pass it to the next handler in the chain.

> **In simple words:**
> The Chain of Responsibility Pattern lets multiple objects get a chance to handle a request without the sender knowing which object will handle it.

---

# Why use the Chain of Responsibility Pattern?

Imagine you're building a **Customer Support System**.

A customer's issue can be handled by:

* Level 1 Support
* Level 2 Support
* Manager

Without the Chain of Responsibility Pattern, you might write:

```java
class CustomerSupport {

    public void handleRequest(int severity) {

        if (severity == 1) {
            System.out.println("Handled by Level 1 Support");
        } else if (severity == 2) {
            System.out.println("Handled by Level 2 Support");
        } else if (severity == 3) {
            System.out.println("Handled by Manager");
        } else {
            System.out.println("No one can handle this request.");
        }
    }
}
```

## Problems

* Large `if-else` or `switch` statements.
* Tight coupling between sender and receivers.
* Difficult to add new handlers.
* Violates the **Open/Closed Principle**.

---

# Chain of Responsibility Pattern Solution

Instead of checking all conditions in one class, each handler is responsible for processing the request or forwarding it to the next handler.

The sender only sends the request to the first handler.

---

# Step 1: Create the Handler

```java
abstract class SupportHandler {

    protected SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(int severity);
}
```

---

# Step 2: Implement Concrete Handlers

## Level 1 Support

```java
class LevelOneSupport extends SupportHandler {

    @Override
    public void handleRequest(int severity) {

        if (severity == 1) {
            System.out.println("Handled by Level 1 Support");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(severity);
        }
    }
}
```

---

## Level 2 Support

```java
class LevelTwoSupport extends SupportHandler {

    @Override
    public void handleRequest(int severity) {

        if (severity == 2) {
            System.out.println("Handled by Level 2 Support");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(severity);
        }
    }
}
```

---

## Manager

```java
class ManagerSupport extends SupportHandler {

    @Override
    public void handleRequest(int severity) {

        if (severity == 3) {
            System.out.println("Handled by Manager");
        } else {
            System.out.println("No one can handle this request.");
        }
    }
}
```

---

# Step 3: Build the Chain

```java
public class Main {

    public static void main(String[] args) {

        SupportHandler level1 = new LevelOneSupport();
        SupportHandler level2 = new LevelTwoSupport();
        SupportHandler manager = new ManagerSupport();

        level1.setNextHandler(level2);
        level2.setNextHandler(manager);

        level1.handleRequest(1);
        level1.handleRequest(2);
        level1.handleRequest(3);
        level1.handleRequest(5);
    }
}
```

### Output

```text
Handled by Level 1 Support
Handled by Level 2 Support
Handled by Manager
No one can handle this request.
```

---

# Class Diagram

```text
                SupportHandler
                ----------------
                nextHandler
                handleRequest()
                      ^
                      |
      -----------------------------------------
      |                  |                    |
LevelOneSupport   LevelTwoSupport    ManagerSupport
```

Each handler either:

* Handles the request.
* Passes it to the next handler.

---

# Request Flow

```text
Client
   |
   v
Level 1
   |
   | Cannot handle
   v
Level 2
   |
   | Cannot handle
   v
Manager
   |
   | Handles request
   v
 End
```

---

# Real-World Example

## ATM Cash Withdrawal

Suppose an ATM stores money in:

* ₹2000 notes
* ₹500 notes
* ₹100 notes

A request for **₹4600** flows through the chain.

```text
ATM
  |
  v
₹2000 Dispenser
  |
  v
₹500 Dispenser
  |
  v
₹100 Dispenser
```

Processing:

```text
₹4600

₹2000 Dispenser -> 2 notes
Remaining = ₹600

₹500 Dispenser -> 1 note
Remaining = ₹100

₹100 Dispenser -> 1 note

Done
```

Each dispenser handles only the denomination it knows.

---

# Another Example: Authentication System

A web request passes through several handlers:

```text
Request
   |
Authentication Filter
   |
Authorization Filter
   |
Logging Filter
   |
Business Logic
```

Each handler performs one responsibility and forwards the request.

---

# Advantages

* Removes large `if-else` or `switch` statements.
* Reduces coupling between sender and receiver.
* Easy to add or remove handlers.
* Follows the **Open/Closed Principle**.
* Supports flexible request-processing pipelines.

---

# Disadvantages

* Request may pass through many handlers, affecting performance.
* No guarantee that a handler will process the request.
* Debugging the chain can be more difficult.

---

# When to Use the Chain of Responsibility Pattern

Use the Chain of Responsibility Pattern when:

* Multiple objects can handle a request.
* The exact handler should be decided at runtime.
* You want to decouple the sender from the receiver.
* Handlers should be easy to add, remove, or reorder.

### Common Use Cases

* Customer support escalation
* Logging frameworks
* Servlet filters
* Spring Security filter chain
* HTTP request middleware
* Event processing pipelines
* ATM cash dispensers
* Exception handling

---

# Chain of Responsibility vs Strategy Pattern

| Strategy Pattern                       | Chain of Responsibility Pattern                        |
| -------------------------------------- | ------------------------------------------------------ |
| Selects one algorithm.                 | Passes a request through multiple handlers.            |
| Client chooses the strategy.           | Client sends the request to the first handler.         |
| One strategy processes the request.    | Multiple handlers get a chance to process the request. |
| Focuses on interchangeable algorithms. | Focuses on flexible request handling.                  |

---

# Chain of Responsibility vs State Pattern

| State Pattern                       | Chain of Responsibility Pattern                          |
| ----------------------------------- | -------------------------------------------------------- |
| Behavior depends on current state.  | Processing depends on which handler accepts the request. |
| Context maintains one active state. | Chain maintains multiple handlers.                       |
| State changes over time.            | Request moves through the chain.                         |

---

# Summary

The **Chain of Responsibility Design Pattern** allows a request to travel through a sequence of handlers until one of them processes it.

Each handler is responsible for either:

1. Handling the request.
2. Passing it to the next handler.

This removes tight coupling between the sender and receivers and makes it easy to extend the processing pipeline.

## Key Components

| Component            | Responsibility                                                            |
| -------------------- | ------------------------------------------------------------------------- |
| **Handler**          | Defines the interface for handling requests and storing the next handler. |
| **Concrete Handler** | Processes the request or forwards it.                                     |
| **Client**           | Builds the chain and sends requests to the first handler.                 |

### Pattern Flow

```text
Client
   |
   v
Handler 1
   |
   v
Handler 2
   |
   v
Handler 3
   |
   v
Request Handled
```

---

## Key Takeaway

> **Pass a request through a chain of handlers, where each handler decides whether to process the request or forward it to the next handler, reducing coupling and improving extensibility.**
