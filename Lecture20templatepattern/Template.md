# Template Method Design Pattern

The **Template Method Design Pattern** is a **behavioral design pattern** that defines the **skeleton of an algorithm in a base class**, while allowing subclasses to customize certain steps without changing the overall algorithm.

> **In simple words:**
> The parent class defines **what steps should happen and in what order**, while child classes decide **how specific steps are performed**.

---

# Why use the Template Method Pattern?

Imagine you are building a system that processes different types of documents.

For every document, the overall process is:

```text
1. Open document
2. Read document
3. Process document
4. Close document
```

The overall algorithm is the same, but the implementation of some steps differs.

For example:

```text
PDF:
Open → Read PDF → Process PDF → Close

Word:
Open → Read Word → Process Word → Close

Excel:
Open → Read Excel → Process Excel → Close
```

Without the Template Method Pattern, you might duplicate the same workflow in every class.

---

# Problem Without Template Method

```java
class PDFProcessor {

    public void process() {
        open();
        readPDF();
        processPDF();
        close();
    }

    private void open() {
        System.out.println("Opening PDF");
    }

    private void readPDF() {
        System.out.println("Reading PDF");
    }

    private void processPDF() {
        System.out.println("Processing PDF");
    }

    private void close() {
        System.out.println("Closing PDF");
    }
}
```

Another class:

```java
class WordProcessor {

    public void process() {
        open();
        readWord();
        processWord();
        close();
    }

    // Same workflow repeated...
}
```

## Problems

* Duplicate code.
* Algorithm structure is repeated.
* Changes to the workflow need to be made in multiple classes.
* Easy for subclasses to execute steps in the wrong order.

---

# Template Method Solution

Move the common workflow into a parent class.

```text id="r8y2ka"
DocumentProcessor
        |
        | template method
        v
     process()
        |
   +----+----+----+
   |    |    |    |
 open read process close
        |
   Subclasses customize
   specific steps
```

---

# Step 1: Create the Abstract Class

```java
abstract class DocumentProcessor {

    // Template Method
    public final void processDocument() {

        openDocument();

        readDocument();

        processDocumentData();

        closeDocument();
    }

    protected void openDocument() {
        System.out.println("Opening document");
    }

    protected abstract void readDocument();

    protected abstract void processDocumentData();

    protected void closeDocument() {
        System.out.println("Closing document");
    }
}
```

The important method is:

```java
public final void processDocument()
```

This is the **Template Method**.

It defines the complete algorithm.

---

# Step 2: Create Concrete Classes

## PDF Processor

```java
class PDFProcessor extends DocumentProcessor {

    @Override
    protected void readDocument() {
        System.out.println("Reading PDF");
    }

    @Override
    protected void processDocumentData() {
        System.out.println("Processing PDF data");
    }
}
```

---

## Word Processor

```java
class WordProcessor extends DocumentProcessor {

    @Override
    protected void readDocument() {
        System.out.println("Reading Word document");
    }

    @Override
    protected void processDocumentData() {
        System.out.println("Processing Word data");
    }
}
```

---

# Step 3: Client Code

```java
public class Main {

    public static void main(String[] args) {

        DocumentProcessor pdf =
                new PDFProcessor();

        pdf.processDocument();

        System.out.println();

        DocumentProcessor word =
                new WordProcessor();

        word.processDocument();
    }
}
```

### Output

```text
Opening document
Reading PDF
Processing PDF data
Closing document

Opening document
Reading Word document
Processing Word data
Closing document
```

Notice that the client doesn't control the order of operations.

The parent class controls the workflow.

---

# How Template Method Works

```text
                 DocumentProcessor
                        |
                        v
               processDocument()
                        |
          +-------------+-------------+
          |             |             |
          v             v             v
       Open()        Read()        Process()
          |             |             |
       Common        Variable      Variable
       Step           Step          Step
```

The algorithm remains fixed.

Only specific steps can be overridden.

---

# Template Method Structure

A Template Method usually contains three types of methods.

## 1. Concrete Methods

These are common to all subclasses.

```java
protected void openDocument() {
    System.out.println("Opening document");
}
```

Subclasses normally don't need to override them.

---

## 2. Abstract Methods

These must be implemented by subclasses.

```java
protected abstract void readDocument();
```

Different subclasses provide different implementations.

---

## 3. Hook Methods

A **hook** is an optional method that subclasses can override.

Example:

```java
protected boolean shouldValidate() {
    return true;
}
```

The template can use it:

```java
public final void processDocument() {

    openDocument();

    if (shouldValidate()) {
        validateDocument();
    }

    readDocument();

    processDocumentData();

    closeDocument();
}
```

A subclass can customize the behavior:

```java
@Override
protected boolean shouldValidate() {
    return false;
}
```

Hooks provide optional customization.

---

# Complete Example with Hook

```java
abstract class DataProcessor {

    public final void process() {

        loadData();

        if (shouldValidate()) {
            validateData();
        }

        processData();

        saveData();
    }

    protected void loadData() {
        System.out.println("Loading data");
    }

    protected boolean shouldValidate() {
        return true;
    }

    protected abstract void validateData();

    protected abstract void processData();

    protected void saveData() {
        System.out.println("Saving data");
    }
}
```

Concrete implementation:

```java
class CSVProcessor extends DataProcessor {

    @Override
    protected void validateData() {
        System.out.println("Validating CSV");
    }

    @Override
    protected void processData() {
        System.out.println("Processing CSV");
    }
}
```

Another implementation:

```java
class LogProcessor extends DataProcessor {

    @Override
    protected boolean shouldValidate() {
        return false;
    }

    @Override
    protected void validateData() {
        // Not required
    }

    @Override
    protected void processData() {
        System.out.println("Processing logs");
    }
}
```

---

# The Most Important Concept

The Template Method follows the principle:

> **"Don't call us, we'll call you."**

The parent class controls the algorithm and calls subclass methods when needed.

```text
Parent
  |
  | process()
  |
  +----> step1()
  |
  +----> subclass step2()
  |
  +----> step3()
```

The subclass doesn't decide the overall flow.

---

# Real-World Example: Payment Processing

Imagine different payment methods:

* Credit Card
* UPI
* PayPal

The overall payment process is:

```text
1. Validate Payment
2. Authenticate
3. Process Payment
4. Send Notification
```

The algorithm remains the same.

```java
abstract class PaymentProcessor {

    public final void processPayment() {

        validate();

        authenticate();

        process();

        sendNotification();
    }

    protected abstract void validate();

    protected abstract void authenticate();

    protected abstract void process();

    protected void sendNotification() {
        System.out.println("Payment notification sent");
    }
}
```

Credit Card:

```java
class CreditCardPayment extends PaymentProcessor {

    @Override
    protected void validate() {
        System.out.println("Validating card");
    }

    @Override
    protected void authenticate() {
        System.out.println("Authenticating card");
    }

    @Override
    protected void process() {
        System.out.println("Processing card payment");
    }
}
```

UPI:

```java
class UPIPayment extends PaymentProcessor {

    @Override
    protected void validate() {
        System.out.println("Validating UPI ID");
    }

    @Override
    protected void authenticate() {
        System.out.println("Authenticating UPI PIN");
    }

    @Override
    protected void process() {
        System.out.println("Processing UPI payment");
    }
}
```

Client:

```java
PaymentProcessor payment =
        new CreditCardPayment();

payment.processPayment();
```

---

# Another Real-World Example: Interview Process

An interview process might always follow:

```text
1. Resume Screening
2. Technical Round
3. HR Round
4. Offer
```

But the implementation of each step can vary.

```java
abstract class Interview {

    public final void conductInterview() {

        screenResume();

        technicalRound();

        hrRound();

        makeDecision();
    }

    protected void screenResume() {
        System.out.println("Screening resume");
    }

    protected abstract void technicalRound();

    protected abstract void hrRound();

    protected void makeDecision() {
        System.out.println("Making final decision");
    }
}
```

The overall interview flow cannot be changed by subclasses.

---

# Class Diagram

```text
                 AbstractClass
                +----------------+
                | templateMethod()|
                +----------------+
                        |
             +----------+----------+
             |                     |
             v                     v
       ConcreteClassA       ConcreteClassB
             |                     |
       implement steps       implement steps
```

More specifically:

```text
           DataProcessor
                 |
        process() [final]
                 |
      +----------+----------+
      |                     |
      v                     v
 CSVProcessor          JSONProcessor
      |                     |
 validate()             validate()
 process()              process()
```

---

# Why Make Template Method `final`?

Usually, we make the Template Method `final`.

```java
public final void process() {
    step1();
    step2();
    step3();
}
```

Why?

Because we don't want subclasses to change the algorithm's order.

For example, this should not be allowed:

```text
Process
  |
  +-- Step 3
  +-- Step 1
  +-- Step 2
```

The parent should guarantee:

```text
Step 1
  ↓
Step 2
  ↓
Step 3
```

Subclasses only customize individual steps.

---

# Advantages

* Eliminates duplicate algorithm code.
* Controls the order of operations.
* Allows subclasses to customize specific steps.
* Promotes code reuse.
* Makes the overall workflow consistent.
* Follows the **Hollywood Principle**.
* Easy to add new implementations.

---

# Disadvantages

* Uses inheritance, which creates coupling between parent and child.
* Subclasses may become tightly dependent on the base class.
* Too many hooks can make the base class complicated.
* Can violate the **Liskov Substitution Principle** if subclasses need to disable or heavily alter the template's behavior.
* Less flexible than composition in some situations.

---

# When to Use Template Method

Use the Template Method Pattern when:

* Multiple classes follow the same algorithm.
* The algorithm structure should remain fixed.
* Some steps vary between implementations.
* You want to eliminate duplicate workflow code.
* You want subclasses to customize specific steps.

### Common Use Cases

* Data processing pipelines
* Payment processing
* Document processing
* Report generation
* File parsing
* Authentication workflows
* ETL pipelines
* Test setup/teardown workflows

---

# Template Method vs Strategy

This is a very important interview question.

| Template Method                           | Strategy                                       |
| ----------------------------------------- | ---------------------------------------------- |
| Behavioral pattern.                       | Behavioral pattern.                            |
| Uses inheritance.                         | Uses composition.                              |
| Algorithm structure is defined by parent. | Entire algorithm can be replaced.              |
| Subclasses override specific steps.       | Different strategy objects provide algorithms. |
| Uses "is-a" relationship.                 | Uses "has-a" relationship.                     |

### Template Method

```text
PaymentProcessor
       |
       v
   process()
       |
   +---+---+
   |   |   |
 Step Step Step
```

### Strategy

```text
PaymentService
       |
       v
PaymentStrategy
       |
   +---+---+
   |       |
 Card     UPI
```

### Easy Way to Remember

> **Template Method = Same algorithm, different steps.**

> **Strategy = Different algorithms, choose one.**

---

# Template Method vs Factory Method

These patterns are sometimes used together.

| Template Method                  | Factory Method                    |
| -------------------------------- | --------------------------------- |
| Defines an algorithm's skeleton. | Defines how an object is created. |
| Controls execution flow.         | Controls object creation.         |
| Behavioral pattern.              | Creational pattern.               |

Example:

```java
abstract class ReportGenerator {

    public final void generate() {

        Report report = createReport();

        process(report);

        save(report);
    }

    protected abstract Report createReport();

    protected abstract void process(Report report);

    protected void save(Report report) {
        System.out.println("Saving report");
    }
}
```

Here:

```java
createReport()
```

is a **Factory Method**, while:

```java
generate()
```

is the **Template Method**.

They can work together.

---

# Template Method vs Chain of Responsibility

| Template Method               | Chain of Responsibility                          |
| ----------------------------- | ------------------------------------------------ |
| Defines a fixed sequence.     | Passes a request through handlers.               |
| Parent controls the workflow. | Handlers decide whether to process/pass request. |
| Uses inheritance.             | Usually uses composition.                        |
| Fixed algorithm structure.    | Dynamic chain of handlers.                       |

---

# Key Design Principle

The Template Method follows the **Hollywood Principle**:

> **"Don't call us, we'll call you."**

In other words:

```text
Parent Class
     |
     | Controls flow
     v
Subclass Methods
```

The subclass doesn't control when its methods are called.

The base class does.

---

# Summary

The **Template Method Design Pattern** defines the skeleton of an algorithm in a base class while allowing subclasses to customize specific steps.

The overall algorithm remains fixed, but individual steps can vary.

## Key Components

| Component            | Responsibility                                   |
| -------------------- | ------------------------------------------------ |
| **Abstract Class**   | Defines the algorithm skeleton.                  |
| **Template Method**  | Defines the fixed sequence of operations.        |
| **Concrete Methods** | Provide common behavior.                         |
| **Abstract Methods** | Force subclasses to implement variable behavior. |
| **Hook Methods**     | Provide optional customization.                  |
| **Concrete Classes** | Implement the variable steps.                    |

### Pattern Flow

```text
             Abstract Class
                   |
                   v
          Template Method
                   |
       +-----------+-----------+
       |           |           |
     Step 1      Step 2      Step 3
     Fixed       Variable     Fixed
                   |
                   v
              Subclass
             implements
              Step 2
```

---

# Key Takeaway

> **Use the Template Method Pattern when multiple classes follow the same overall algorithm but need different implementations for some individual steps. The parent class controls the workflow, while subclasses customize the variable parts.**

### Easy Way to Remember

```text
Template Method
        ↓
Same algorithm
        ↓
Different steps
        ↓
Inheritance
        ↓
Parent controls the flow
```

### One-Line Definition

> **Template Method defines "what happens and in what order"; subclasses define "how individual steps happen."**
