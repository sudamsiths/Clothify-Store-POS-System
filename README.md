# Clothify Store POS System

Clothify Store POS System is a JavaFX desktop application, structured with a layered architecture for maintainability and scalability. The system includes database interaction, business logic, and a responsive UI for managing clothing store operations.

## Features

- **Product Management:** Add, update, delete, and categorize clothing items.
- **Inventory Tracking:** Real-time stock updates and alerts for low inventory.
- **Sales Processing:** Process purchases and print receipts.
- **Customer Management:** Manage customer profiles and purchase history.
- **Reports:** View sales, inventory, and customer reports.
- **User Authentication:** Basic login screen for users.

## Technologies Used

- **Java 11+**
- **JavaFX** (UI framework)
- **JDBC** (Database connection)
- **SQL** (Tested with MySQL; can adapt for others)
- **Scene Builder** (recommended for FXML editing)


## Project Structure

Below is the current project structure as reflected in your repository:

![Clothify Home Page](src/main/resources/img/Readmeimages.png)

```
Clothify-Store-POS-System/
│
├── src/
│   └── main/
│       ├── java/
│       │   ├── config/           # Configuration classes (e.g., Hibernate)
│       │   ├── controller/       # JavaFX controllers (UI logic)
│       │   ├── db/               # Database utilities/configuration
│       │   ├── DTO/              # Data Transfer Objects
│       │   ├── Entity/           # Domain models/entities
│       │   ├── repository/       # Repository layer (data access logic)
│       │   ├── Service/          # Business logic/services
│       │   ├── util/             # Utility/helper classes
│       │   ├── Main              # Main application entry point
│       │   └── Starter           # Starter class (if applicable)
│       ├── resources/
│       │   ├── img/              # Images for UI
│       │   ├── report/           # JasperReports JRXML files and generated PDFs
│       │   └── view/             # JavaFX FXML layout files
│       │   └── hibernate.cfg.xml # Hibernate configuration file
│
├── target/                       # Build output directory
├── .gitignore
├── EmployeeReport.pdf
├── ProductReports.pdf
├── SupplierReportss.pdf
├── pom.xml                       # Maven build file
├── README.md
└── External Libraries/
```

### Key Layers

- **Controller Layer (`controller/`)**: Handles UI events and user interaction.
- **Service Layer (`Service/`)**: Implements business logic, coordinates between controller and repository.
- **Repository Layer (`repository/`)**: Contains classes for CRUD/database operations, acting as the direct interface to the DB.
- **Entity Layer (`Entity/`)**: Represents database entities/domain models.
- **DTO Layer (`DTO/`)**: Data Transfer Objects for moving data between layers.
- **DB Layer (`db/` and `hibernate.cfg.xml`)**: Handles database connections and configuration (Hibernate).
- **Resources**: FXML layouts, reports, images.

## Database Setup

**Database Name:** `clothify_store`

Place the following SQL in your project (e.g., `src/main/resources/db/schema.sql`) and execute it to set up your database:
```sql
CREATE DATABASE IF NOT EXISTS clothify_store;
USE clothify_store;

CREATE TABLE customer (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nic BIGINT NOT NULL UNIQUE,
    address VARCHAR(255),
    dob DATE,
    contactno BIGINT
);

CREATE TABLE order_details (
    id VARCHAR(50) NOT NULL,
    item_code VARCHAR(50) NOT NULL,
    category VARCHAR(100),
    size VARCHAR(20),
    qty INT,
    unit_price DOUBLE,
    total DOUBLE
);

CREATE TABLE orders (
    id VARCHAR(6) NOT NULL PRIMARY KEY,
    date DATE,
    customerId VARCHAR(6) NOT NULL
);

CREATE TABLE products (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    supplier_id VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    size VARCHAR(50),
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(500),
    qty INT DEFAULT 0
);

CREATE TABLE suppliers (
    supplier_id VARCHAR(255) NOT NULL PRIMARY KEY,
    supplier_name VARCHAR(100) NOT NULL,
    company_name VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    item VARCHAR(255)
);

CREATE TABLE user (
    email VARCHAR(35) NOT NULL,
    password VARCHAR(30) NOT NULL
);
```

## Notes

- The **repository layer** is implemented in the `repository/` package (e.g., `CustomerRepository.java`, `ProductRepository.java`), separating DB logic from business logic.
- Hibernate is used for ORM, configured via `hibernate.cfg.xml` in `resources/`.
- Reports are generated and stored in the `report/` directory under `resources/`.
- FXML layouts are organized under `resources/view/`.

---

*For any questions, contact [sudamsiths](https://github.com/sudamsiths).*
