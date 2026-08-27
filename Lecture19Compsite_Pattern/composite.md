# Composite Design Pattern

The **Composite Design Pattern** is a **structural design pattern** that lets you **treat individual objects and groups of objects uniformly**.

It organizes objects into a **tree structure** where both **leaf objects** (individual items) and **composite objects** (groups) implement the same interface.

> **In simple words:**
> The Composite Pattern allows you to work with a single object and a collection of objects in exactly the same way.

---

# Why use the Composite Pattern?

Imagine you're building a **File System**.

A file system contains:

* Files
* Folders

A folder can contain:

* Files
* Other folders

Without the Composite Pattern, you might write:

```java id="k3v9pj"
class File {

    public void show() {
        System.out.println("Displaying File");
    }
}

class Folder {

    private List<File> files;
    private List<Folder> folders;

    public void show() {
        // Display files
        // Display folders
    }
}
```

## Problems

* Different logic for files and folders.
* Client needs to know whether an object is a file or folder.
* Difficult to add nested folders.
* Complicated recursive code.

---

# Composite Pattern Solution

Create a common interface called `FileSystemComponent`.

Both:

* File (Leaf)
* Folder (Composite)

implement the same interface.

---

# Step 1: Create the Component Interface

```java id="jlwm11"
interface FileSystemComponent {

    void show();
}
```

---

# Step 2: Create the Leaf

```java id="jlwm12"
class File implements FileSystemComponent {

    private String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void show() {
        System.out.println(name);
    }
}
```

A **Leaf** cannot contain children.

---

# Step 3: Create the Composite

```java id="jlwm13"
import java.util.ArrayList;
import java.util.List;

class Folder implements FileSystemComponent {

    private String name;

    private List<FileSystemComponent> components = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        components.add(component);
    }

    public void remove(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public void show() {

        System.out.println(name);

        for (FileSystemComponent component : components) {
            component.show();
        }
    }
}
```

A **Composite** can contain both files and folders.

---

# Step 4: Client Code

```java id="jlwm14"
public class Main {

    public static void main(String[] args) {

        File file1 = new File("Resume.pdf");
        File file2 = new File("Photo.jpg");
        File file3 = new File("Notes.txt");

        Folder documents = new Folder("Documents");
        documents.add(file1);
        documents.add(file3);

        Folder images = new Folder("Images");
        images.add(file2);

        Folder root = new Folder("Root");

        root.add(documents);
        root.add(images);

        root.show();
    }
}
```

### Output

```text id="jlwm15"
Root
Documents
Resume.pdf
Notes.txt
Images
Photo.jpg
```

---

# Nested Composite Example

Folders can contain other folders.

```java id="jlwm16"
Folder java = new Folder("Java");
Folder designPatterns = new Folder("DesignPatterns");

designPatterns.add(new File("Singleton.md"));
designPatterns.add(new File("Factory.md"));

java.add(designPatterns);

root.add(java);
```

Tree Structure:

```text id="jlwm17"
Root
 |
 +-- Java
 |      |
 |      +-- DesignPatterns
 |               |
 |               +-- Singleton.md
 |               +-- Factory.md
 |
 +-- Images
```

The client treats every node the same way.

---

# How Composite Pattern Works

```text id="jlwm18"
Client
   |
   v
Component
   |
   +--------------------+
   |                    |
 Leaf               Composite
(File)             (Folder)
                        |
             Contains Components
```

The composite delegates operations to its children recursively.

---

# Class Diagram

```text id="jlwm19"
               FileSystemComponent
              +--------------------+
              | show()             |
              +--------------------+
                       ^
          -----------------------------
          |                           |
        File                      Folder
                                   |
                         List<FileSystemComponent>
```

Both `File` and `Folder` implement the same interface.

---

# Real-World Example

## Organization Hierarchy

A company has:

* CEO
* Managers
* Employees

Managers can manage both employees and other managers.

```text id="’wini20"
CEO
 |
 +-- Manager A
 |      |
 |      +-- Employee 1
 |      +-- Employee 2
 |
 +-- Manager B
        |
        +-- Employee 3
```

Calling `display()` on the CEO recursively displays the entire hierarchy.

---

# Another Example: GUI Components

A GUI contains:

* Window
* Panel
* Button
* TextField

A `Panel` can contain:

* Buttons
* Labels
* Other Panels

```text id="’wini21"
Window
 |
 +-- Panel
 |      |
 |      +-- Button
 |      +-- TextField
 |
 +-- Panel
        |
        +-- Label
```

---

# Advantages

* Treats individual objects and groups uniformly.
* Simplifies client code.
* Easy to add new component types.
* Supports recursive tree structures.
* Follows the **Open/Closed Principle**.

---

# Disadvantages

* Can make the design overly generic.
* Difficult to restrict what a composite can contain.
* More classes compared to a simple solution.

---

# When to Use the Composite Pattern

Use the Composite Pattern when:

* Objects form a tree hierarchy.
* Individual objects and groups should be treated the same.
* Recursive structures are required.
* You want to simplify client code.

### Common Use Cases

* File systems
* Organization hierarchies
* GUI component trees
* HTML/XML DOM
* Menu systems
* Product categories
* Comment threads
* Scene graphs in games

---

# Composite vs Decorator

| Composite Pattern                    | Decorator Pattern                 |
| ------------------------------------ | --------------------------------- |
| Represents a tree structure.         | Wraps a single object.            |
| Parent contains children.            | Decorator contains one component. |
| Focuses on part-whole relationships. | Focuses on adding behavior.       |

---

# Composite vs Adapter

| Adapter Pattern                    | Composite Pattern                   |
| ---------------------------------- | ----------------------------------- |
| Converts one interface to another. | Represents hierarchical structures. |
| Solves compatibility problems.     | Solves part-whole relationships.    |

---

# Composite vs Proxy

| Proxy Pattern                 | Composite Pattern                   |
| ----------------------------- | ----------------------------------- |
| Controls access to an object. | Organizes objects into trees.       |
| One proxy → one real object.  | One composite → many child objects. |

---

# Composite vs Facade

| Facade Pattern                    | Composite Pattern                          |
| --------------------------------- | ------------------------------------------ |
| Simplifies access to a subsystem. | Represents hierarchical object structures. |
| Hides complexity.                 | Models tree relationships.                 |

---

# Summary

The **Composite Design Pattern** allows individual objects and groups of objects to be treated uniformly.

By defining a common interface for both **Leaf** and **Composite** objects, clients can perform operations on an entire tree structure without caring whether a node is a single object or a collection.

The Composite Pattern is ideal for representing **part-whole hierarchies** such as file systems, GUI component trees, and organization structures.

## Key Components

| Component     | Responsibility                                                         |
| ------------- | ---------------------------------------------------------------------- |
| **Component** | Defines the common interface.                                          |
| **Leaf**      | Represents an individual object with no children.                      |
| **Composite** | Stores child components and delegates operations to them.              |
| **Client**    | Uses the common interface to interact with both leaves and composites. |

### Pattern Flow

```text id="’wini22"
Client
   |
   v
Component
   |
   +------------------+
   |                  |
 Leaf            Composite
                    |
          +---------+---------+
          |                   |
       Leaf              Composite
                              |
                         +-----+-----+
                         |           |
                      Leaf        Leaf
```

Operations invoked on the root composite are propagated recursively to all child components.

---

## Key Takeaway

> **Use the Composite Pattern when you need to represent part-whole hierarchies and want clients to treat individual objects and groups of objects uniformly. It simplifies working with recursive tree structures by providing a common interface for both leaves and composites.**
