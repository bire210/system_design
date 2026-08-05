# Command Design Pattern

The **Command Design Pattern** is a **behavioral design pattern** that encapsulates a request as an object. This allows you to parameterize objects with requests, queue or log requests, and support operations like **undo** and **redo**.

> **In simple words:**
> The Command Pattern turns a request into an object, allowing the sender of the request to be completely decoupled from the object that performs the action.

---

# Why use the Command Pattern?

Imagine you're building a **Smart Home Remote Control**.

The remote can control different devices:

* Light
* Fan
* TV

Without the Command Pattern, you might write:

```java id="5ynp6k"
class RemoteControl {

    private Light light = new Light();
    private Fan fan = new Fan();

    public void pressButton(String device) {

        if (device.equals("LIGHT")) {
            light.turnOn();
        } else if (device.equals("FAN")) {
            fan.turnOn();
        }
    }
}
```

## Problems

* Lots of `if-else` or `switch` statements.
* Remote is tightly coupled to all devices.
* Difficult to add new devices.
* Cannot easily support undo, redo, or command history.
* Violates the **Open/Closed Principle**.

---

# Command Pattern Solution

Instead of the remote directly controlling devices, every action is represented by a **Command** object.

The remote simply executes commands without knowing what they do.

---

# Step 1: Create the Command Interface

```java id="9y0rj8"
interface Command {
    void execute();
}
```

---

# Step 2: Create Receiver Classes

## Light

```java id="d54qzt"
class Light {

    public void turnOn() {
        System.out.println("Light is ON");
    }

    public void turnOff() {
        System.out.println("Light is OFF");
    }
}
```

---

## Fan

```java id="wlrql0"
class Fan {

    public void turnOn() {
        System.out.println("Fan is ON");
    }

    public void turnOff() {
        System.out.println("Fan is OFF");
    }
}
```

---

# Step 3: Implement Concrete Commands

## Light On Command

```java id="6mpz4j"
class LightOnCommand implements Command {

    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }
}
```

---

## Fan On Command

```java id="s6vn9r"
class FanOnCommand implements Command {

    private Fan fan;

    public FanOnCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.turnOn();
    }
}
```

---

# Step 4: Create the Invoker

The invoker knows **when** to execute a command, but not **how** it works.

```java id="j9lq2x"
class RemoteControl {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}
```

---

# Step 5: Client Code

```java id="w73nk6"
public class Main {

    public static void main(String[] args) {

        Light light = new Light();
        Fan fan = new Fan();

        Command lightCommand = new LightOnCommand(light);
        Command fanCommand = new FanOnCommand(fan);

        RemoteControl remote = new RemoteControl();

        remote.setCommand(lightCommand);
        remote.pressButton();

        remote.setCommand(fanCommand);
        remote.pressButton();
    }
}
```

### Output

```text id="y5q82o"
Light is ON
Fan is ON
```

---

# Class Diagram

```text id="ev8brt"
                Command
              +-----------+
              | execute() |
              +-----------+
                   ^
                   |
      -----------------------------
      |                           |
LightOnCommand             FanOnCommand
      |                           |
      v                           v
    Light                       Fan

             ^
             |
      RemoteControl
      (Invoker)
```

The **RemoteControl** only executes commands. The actual work is done by the receiver classes (`Light`, `Fan`).

---

# Command Flow

```text id="7k9r5u"
Client
   |
Creates Command
   |
   v
Remote Control
   |
execute()
   |
   v
Concrete Command
   |
Calls Receiver
   |
   v
Light / Fan
```

---

# Real-World Example

## Restaurant Ordering System

When a customer places an order:

* Waiter takes the order.
* Chef prepares the food.
* Kitchen equipment cooks it.

The waiter doesn't cook the food.

```text id="l9gm2g"
Customer
    |
    v
Waiter (Invoker)
    |
Order Command
    |
    v
Chef (Receiver)
```

The order acts as the **Command** object.

---

# Another Example: Text Editor

A text editor supports operations like:

* Copy
* Paste
* Cut
* Undo
* Redo

Each operation can be represented as a command.

```text id="hzj54r"
Editor
   |
CopyCommand
PasteCommand
CutCommand
UndoCommand
RedoCommand
```

Because commands are objects, they can be:

* Stored in history.
* Undone.
* Replayed.
* Logged.

---

# Supporting Undo

The Command Pattern naturally supports undo.

```java id="i0ykqv"
interface Command {

    void execute();

    void undo();
}
```

Example:

```java id="jn77s2"
class LightOnCommand implements Command {

    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOn();
    }

    public void undo() {
        light.turnOff();
    }
}
```

The invoker can maintain a stack of executed commands to implement **Undo/Redo**.

---

# Advantages

* Removes large `if-else` or `switch` statements.
* Decouples sender from receiver.
* Easy to add new commands.
* Supports undo, redo, logging, and queuing.
* Follows the **Open/Closed Principle**.

---

# Disadvantages

* Increases the number of classes.
* Can be overkill for very simple applications.
* Managing command history for undo/redo adds complexity.

---

# When to Use the Command Pattern

Use the Command Pattern when:

* You want to decouple the sender from the receiver.
* Requests should be represented as objects.
* You need undo/redo functionality.
* Commands should be queued, scheduled, or logged.

### Common Use Cases

* GUI button actions
* Smart home remote controls
* Text editors
* Transaction processing
* Job scheduling
* Macro recording
* Queue-based task execution
* Undo/Redo systems

---

# Command vs Strategy Pattern

| Strategy Pattern                       | Command Pattern                 |
| -------------------------------------- | ------------------------------- |
| Encapsulates an algorithm.             | Encapsulates a request/action.  |
| Client chooses which algorithm to use. | Invoker executes a command.     |
| Focuses on interchangeable algorithms. | Focuses on executable requests. |

---

# Command vs Chain of Responsibility

| Chain of Responsibility                      | Command Pattern                                   |
| -------------------------------------------- | ------------------------------------------------- |
| Request passes through multiple handlers.    | Request is wrapped into a command object.         |
| One handler eventually processes it.         | Receiver executes the command directly.           |
| Focuses on deciding who handles the request. | Focuses on representing the request as an object. |

---

# Command vs Observer

| Observer Pattern                     | Command Pattern                    |
| ------------------------------------ | ---------------------------------- |
| Subject notifies multiple observers. | Invoker executes one command.      |
| Event-driven communication.          | Action-driven execution.           |
| One event can notify many listeners. | One command represents one action. |

---

# Summary

The **Command Design Pattern** encapsulates a request as an object.

Instead of directly invoking operations on receivers, the **Invoker** executes a **Command**, which delegates the work to the appropriate **Receiver**.

This decouples the sender from the receiver and enables advanced features such as **undo**, **redo**, **logging**, **queuing**, and **macro commands**.

## Key Components

| Component            | Responsibility                                             |
| -------------------- | ---------------------------------------------------------- |
| **Command**          | Declares the interface for executing a request.            |
| **Concrete Command** | Implements the command and delegates work to the receiver. |
| **Receiver**         | Performs the actual business logic.                        |
| **Invoker**          | Stores and executes commands.                              |
| **Client**           | Creates commands and associates them with receivers.       |

### Pattern Flow

```text id="kldk1m"
Client
   |
Creates Command
   |
   v
Invoker (Remote Control)
   |
execute()
   |
   v
Concrete Command
   |
Calls
   |
   v
Receiver (Light/Fan)
```

---

## Key Takeaway

> **Encapsulate a request as an object so that requests can be parameterized, queued, logged, and undone, while keeping the sender and receiver loosely coupled.**


## A common interview comparison of the behavioral patterns you've covered is:


| Pattern                     | Purpose                            | Communication Style          |
| --------------------------- | ---------------------------------- | ---------------------------- |
| **Strategy**                | Select one algorithm               | Context → Strategy           |
| **State**                   | Change behavior based on state     | Context ↔ State              |
| **Chain of Responsibility** | Pass request until handled         | Handler → Next Handler       |
| **Observer**                | Notify subscribers of changes      | Subject → Many Observers     |
| **Command**                 | Encapsulate a request as an object | Invoker → Command → Receiver |

