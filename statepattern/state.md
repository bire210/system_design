# State Design Pattern

The **State Design Pattern** is a **behavioral design pattern** that allows an object to change its behavior when its internal state changes. Instead of using large `if-else` or `switch` statements to determine behavior, the object delegates the behavior to a **State** object.

> **In simple words:**
> The State Pattern lets an object behave differently depending on its current state, as if it changes its class at runtime.

---

# Why use the State Pattern?

Imagine you're building a **Media Player**.

A media player can be in one of these states:

* Stopped
* Playing
* Paused

Without the State Pattern, you might write:

```java
class MediaPlayer {

    private String state = "STOPPED";

    public void pressPlay() {
        if (state.equals("STOPPED")) {
            System.out.println("Starting music...");
            state = "PLAYING";
        } else if (state.equals("PAUSED")) {
            System.out.println("Resuming music...");
            state = "PLAYING";
        } else {
            System.out.println("Already playing.");
        }
    }

    public void pressPause() {
        if (state.equals("PLAYING")) {
            System.out.println("Pausing music...");
            state = "PAUSED";
        } else {
            System.out.println("Cannot pause.");
        }
    }

    public void pressStop() {
        if (state.equals("PLAYING") || state.equals("PAUSED")) {
            System.out.println("Stopping music...");
            state = "STOPPED";
        } else {
            System.out.println("Already stopped.");
        }
    }
}
```

## Problems

* Too many `if-else` statements.
* Difficult to add new states.
* State transition logic is scattered.
* Violates the **Open/Closed Principle**.

---

# State Pattern Solution

Instead of storing a string like `"PLAYING"` or `"PAUSED"`, each state becomes its own class.

The `MediaPlayer` delegates behavior to the current state object.

---

# Step 1: Create the State Interface

```java
interface PlayerState {

    void play(MediaPlayer player);

    void pause(MediaPlayer player);

    void stop(MediaPlayer player);
}
```

---

# Step 2: Implement Concrete States

## Stopped State

```java
class StoppedState implements PlayerState {

    @Override
    public void play(MediaPlayer player) {
        System.out.println("Starting music...");
        player.setState(new PlayingState());
    }

    @Override
    public void pause(MediaPlayer player) {
        System.out.println("Cannot pause. Music is stopped.");
    }

    @Override
    public void stop(MediaPlayer player) {
        System.out.println("Already stopped.");
    }
}
```

---

## Playing State

```java
class PlayingState implements PlayerState {

    @Override
    public void play(MediaPlayer player) {
        System.out.println("Already playing.");
    }

    @Override
    public void pause(MediaPlayer player) {
        System.out.println("Pausing music...");
        player.setState(new PausedState());
    }

    @Override
    public void stop(MediaPlayer player) {
        System.out.println("Stopping music...");
        player.setState(new StoppedState());
    }
}
```

---

## Paused State

```java
class PausedState implements PlayerState {

    @Override
    public void play(MediaPlayer player) {
        System.out.println("Resuming music...");
        player.setState(new PlayingState());
    }

    @Override
    public void pause(MediaPlayer player) {
        System.out.println("Already paused.");
    }

    @Override
    public void stop(MediaPlayer player) {
        System.out.println("Stopping music...");
        player.setState(new StoppedState());
    }
}
```

---

# Step 3: Context Class

The context maintains the current state and delegates actions to it.

```java
class MediaPlayer {

    private PlayerState state;

    public MediaPlayer() {
        state = new StoppedState();
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public void pressPlay() {
        state.play(this);
    }

    public void pressPause() {
        state.pause(this);
    }

    public void pressStop() {
        state.stop(this);
    }
}
```

---

# Step 4: Client Code

```java
public class Main {

    public static void main(String[] args) {

        MediaPlayer player = new MediaPlayer();

        player.pressPlay();
        player.pressPause();
        player.pressPlay();
        player.pressStop();
        player.pressStop();
    }
}
```

### Output

```text
Starting music...
Pausing music...
Resuming music...
Stopping music...
Already stopped.
```

---

# Class Diagram

```text
                  PlayerState
                       ^
                       |
        ---------------------------------
        |               |               |
 PlayingState     PausedState     StoppedState
        ^
        |
   MediaPlayer (Context)
        |
        +------ delegates actions
```

The **MediaPlayer** does not know how to behave in every situation. It simply forwards requests to the current state object.

---

# State Transitions

```text
           Play
 STOPPED ---------> PLAYING
    ^                  |
    |                  |
    | Stop             | Pause
    |                  |
    +------------- PAUSED
             Play |
                  |
                  +-----> PLAYING
```

---

# Real-World Example

## Vending Machine

A vending machine can have different states:

* No Coin
* Coin Inserted
* Item Dispensed
* Out of Stock

Behavior changes depending on the current state.

Example:

* Press **Dispense** without inserting a coin → "Insert coin first."
* Insert a coin → State changes to **Coin Inserted**.
* Press **Dispense** → Product is dispensed and state returns to **No Coin**.

Each state implements its own behavior.

---

# Another Example: Document Workflow

A document may have the following states:

* Draft
* Review
* Published

```text
Draft
   |
Submit
   |
Review
   |
Approve
   |
Published
```

Behavior differs by state:

| State     | Allowed Actions |
| --------- | --------------- |
| Draft     | Edit, Submit    |
| Review    | Approve, Reject |
| Published | View            |

Instead of checking the state everywhere, each state class decides which operations are allowed.

---

# Advantages

* Eliminates complex `if-else` or `switch` statements.
* Makes state transitions explicit.
* Easy to add new states.
* Follows the **Open/Closed Principle**.
* Improves maintainability and readability.

---

# Disadvantages

* Creates more classes.
* Can be overkill if there are only one or two simple states.
* State transition logic may become distributed across state classes.

---

# When to Use the State Pattern

Use the State Pattern when:

* An object's behavior changes based on its current state.
* You have many conditional statements checking the object's state.
* State transitions are well-defined.
* New states may be added in the future.

### Common Use Cases

* Media players
* Vending machines
* Document approval workflows
* Order processing systems
* ATM machines
* Traffic lights
* Game characters (Idle, Running, Jumping, Attacking)
* TCP connection states

---

# State Pattern vs Strategy Pattern

| Strategy Pattern                                          | State Pattern                                        |
| --------------------------------------------------------- | ---------------------------------------------------- |
| Chooses **which algorithm** to use.                       | Chooses **behavior based on current state**.         |
| Client selects the strategy.                              | State transitions itself or is managed internally.   |
| Focuses on interchangeable algorithms.                    | Focuses on state-dependent behavior.                 |
| Behavior changes because the client changes the strategy. | Behavior changes because the object's state changes. |

---

# Summary

The **State Design Pattern** encapsulates state-specific behavior into separate classes. The **Context** maintains the current state and delegates operations to it.

When the state changes, the object's behavior changes automatically without modifying the context or using complex conditional logic.

## Key Components

| Component          | Responsibility                                                       |
| ------------------ | -------------------------------------------------------------------- |
| **State**          | Defines the interface for state-specific behavior.                   |
| **Concrete State** | Implements behavior for a particular state.                          |
| **Context**        | Maintains the current state and delegates requests.                  |
| **Client**         | Interacts with the context without worrying about the current state. |

### Pattern Flow

```text
Client
   |
   v
MediaPlayer (Context)
   |
   v
PlayerState
   ^
   |
+----------+------------+-------------+
|          |            |
Stopped   Playing     Paused
```

---

## Key Takeaway

> **Encapsulate state-specific behavior into separate classes so an object can change its behavior automatically as its internal state changes, without relying on large conditional statements.**
