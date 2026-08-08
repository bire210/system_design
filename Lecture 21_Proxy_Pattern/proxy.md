# Proxy Design Pattern

The **Proxy Design Pattern** is a **structural design pattern** that provides a **placeholder (proxy)** for another object to **control access** to it.

Instead of interacting with the real object directly, the client communicates with the proxy, which decides whether and when to forward the request to the real object.

> **In simple words:**
> The Proxy Pattern places another object in front of the real object to control access, add security, cache results, or delay object creation.

---

# Why use the Proxy Pattern?

Imagine you're building an application that loads **high-resolution images**.

Loading an image from disk takes several seconds.

Without the Proxy Pattern:

```java id="k2r8jh"
class Image {

    private String fileName;

    public Image(String fileName) {
        this.fileName = fileName;
        loadImage();
    }

    private void loadImage() {
        System.out.println("Loading image from disk...");
    }

    public void display() {
        System.out.println("Displaying " + fileName);
    }
}
```

Client Code:

```java id="r6xw0m"
public class Main {

    public static void main(String[] args) {

        Image image = new Image("photo.jpg");

        image.display();
    }
}
```

### Output

```text id="4yw6np"
Loading image from disk...
Displaying photo.jpg
```

## Problems

* Expensive objects are created immediately.
* Slow startup.
* No access control.
* No caching.
* Client is tightly coupled to the real object.

---

# Proxy Pattern Solution

Instead of creating the real object immediately, create a **Proxy**.

The proxy decides:

* When to create the real object.
* Whether access is allowed.
* Whether cached data can be returned.

---

# Step 1: Create the Subject Interface

```java id="x3tm0n"
interface Image {

    void display();
}
```

---

# Step 2: Create the Real Subject

```java id="6gf1pb"
class RealImage implements Image {

    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadImage();
    }

    private void loadImage() {
        System.out.println("Loading image from disk...");
    }

    @Override
    public void display() {
        System.out.println("Displaying " + fileName);
    }
}
```

---

# Step 3: Create the Proxy

```java id="0hv4mj"
class ProxyImage implements Image {

    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {

        if (realImage == null) {
            realImage = new RealImage(fileName);
        }

        realImage.display();
    }
}
```

The proxy delays object creation until it is actually needed.

---

# Step 4: Client Code

```java id="7c8m4w"
public class Main {

    public static void main(String[] args) {

        Image image = new ProxyImage("photo.jpg");

        System.out.println("Image object created.");

        image.display();

        image.display();
    }
}
```

### Output

```text id="z5gx1v"
Image object created.

Loading image from disk...
Displaying photo.jpg

Displaying photo.jpg
```

Notice that the image is loaded **only once**.

---

# How Proxy Pattern Works

```text id="yw6vh0"
Client
   |
   v
Proxy
   |
Checks Access
   |
Creates Real Object (if needed)
   |
   v
Real Object
```

The client never communicates directly with the real object.

---

# Class Diagram

```text id="7wgbpk"
                 Image
            +--------------+
            | display()    |
            +--------------+
                  ^
                  |
      -------------------------
      |                       |
 RealImage             ProxyImage
                             |
                             |
                     Holds reference
                             |
                             v
                       RealImage
```

The proxy implements the same interface as the real object.

---

# Types of Proxy

## 1. Virtual Proxy

Creates expensive objects only when required.

Example:

* High-resolution images
* Large files
* Database connections

---

## 2. Protection Proxy

Controls access based on permissions.

Example:

```text id="5l7yjd"
Client
   |
   v
Security Proxy
   |
Is Admin?
   |
Yes -----> Real Service

No -----> Access Denied
```

---

## 3. Remote Proxy

Represents an object located on another machine.

Example:

```text id="wjlwm0"
Client
   |
Remote Proxy
   |
Internet
   |
Remote Server
```

Java RMI is an example.

---

## 4. Caching Proxy

Stores previously computed results.

Example:

```text id="jlwm01"
Client
   |
Cache Proxy
   |
Already Cached?
   |
Yes ---> Return Cached Data

No ---> Call Real Service
```

Used in API gateways and database layers.

---

## 5. Logging Proxy

Logs requests before forwarding them.

```text id="jlwm02"
Client
   |
Logging Proxy
   |
Logs Request
   |
Real Service
```

---

# Real-World Example

## ATM Card

You never interact directly with your bank account.

Instead:

```text id="jlwm03"
Customer
    |
ATM Card (Proxy)
    |
Bank Account
```

The ATM card:

* Authenticates you.
* Checks permissions.
* Forwards requests.

The bank account performs the real work.

---

# Another Example: Spring AOP

Spring creates proxy objects to add:

* Logging
* Security
* Transactions
* Performance monitoring

Without modifying the original service class.

```text id="jlwm04"
Controller
      |
Spring Proxy
      |
UserService
```

---

# Advantages

* Controls access to objects.
* Supports lazy initialization.
* Improves security.
* Enables caching.
* Adds logging and monitoring.
* Follows the **Open/Closed Principle**.

---

# Disadvantages

* Adds extra classes.
* Slight performance overhead.
* Makes the design more complex.

---

# When to Use the Proxy Pattern

Use the Proxy Pattern when:

* Objects are expensive to create.
* Access should be controlled.
* You need lazy loading.
* You want caching.
* You want logging or monitoring.

### Common Use Cases

* Spring AOP proxies
* Hibernate lazy loading
* API gateways
* Database connection proxies
* Remote services (RMI)
* Security checks
* Caching layers
* Image loading

---

# Proxy vs Decorator

| Decorator Pattern                     | Proxy Pattern                             |
| ------------------------------------- | ----------------------------------------- |
| Adds new behavior.                    | Controls access.                          |
| Enhances functionality.               | Protects or manages access.               |
| Client adds decorators intentionally. | Proxy is often transparent to the client. |

---

# Proxy vs Adapter

| Adapter Pattern              | Proxy Pattern                       |
| ---------------------------- | ----------------------------------- |
| Changes an interface.        | Keeps the same interface.           |
| Solves compatibility issues. | Controls access to the real object. |

---

# Proxy vs Facade

| Facade Pattern            | Proxy Pattern                |
| ------------------------- | ---------------------------- |
| Simplifies a subsystem.   | Represents a single object.  |
| Provides a new interface. | Provides the same interface. |

---

# Summary

The **Proxy Design Pattern** provides a placeholder object that controls access to another object.

The proxy implements the same interface as the real object, making it transparent to the client. Depending on the use case, the proxy can perform authentication, caching, logging, lazy loading, or remote communication before delegating the request to the real object.

## Key Components

| Component        | Responsibility                                          |
| ---------------- | ------------------------------------------------------- |
| **Subject**      | Defines the common interface.                           |
| **Real Subject** | Performs the actual work.                               |
| **Proxy**        | Controls access to the real subject.                    |
| **Client**       | Interacts with the proxy through the subject interface. |

### Pattern Flow

```text id="jlwm05"
Client
   |
   v
Proxy
   |
Access Check / Cache / Logging
   |
   v
Real Object
```

---

## Key Takeaway

> **Use the Proxy Pattern when you need to control access to an object, delay its creation, add caching, logging, security, or remote communication, while keeping the same interface as the real object.**
