# Full-Stack Notes & Inventory Management System ☕⚛️

Welcome to the Full-Stack Notes and Inventory Management System. This project is implemented as a **Monorepo**, cleanly decoupling the Frontend and Backend into independent architectural layers following industry best practices for modern Single Page Applications (SPA).

---

## 📁 Repository Structure

The project is structured into two main directories at the root level:
* **`backend/`**: A RESTful API built with **Java and Spring Boot**, strictly adhering to the *Service Layer* pattern to isolate business logic from controllers and data access.
* **`frontend/`**: A responsive and reactive client-side Single Page Application (SPA) built using **React, Vite, and Tailwind CSS**.

---

## 🛠️ Tech Stack

### Backend ☕
* **Language:** Java 11 / 17
* **Framework:** Spring Boot (Spring Web, Spring Data JPA)
* **Build Tool:** Maven
* **Database & ORM:** MySQL / Hibernate ORM (Mandatory relational persistence)

### Frontend ⚛️
* **Library:** React (SPA Architecture)
* **Build Tool & Bundler:** Vite (Optimized for fast development and asset bundling)
* **Styling:** Tailwind CSS / PostCSS
* **HTTP Client:** Axios (For seamless REST API consumption)

---

## 🚀 Prerequisites & Local Setup

To run this project locally, ensure you have the following software installed:
1. **Java JDK** (Version 11 or higher)
2. **Node.js** (Version 18 or higher) & `npm` 
3. **MySQL Server** running locally

### Backend Setup ⚙️
1. Navigate to the backend directory:
    ```bash
    cd backend
    ```
2. Configure your local database credentials (username, password, and database schema URL) in `src/main/resources/application.properties`.
3. Run the Spring Boot application using Maven:
    ```bash
    ./mvnw spring-boot:run
    ```

### Frontend Setup 💻
1. Navigate to the frontend directory:
    ```bash
    cd frontend
    ```
2. Install the project dependencies:
    ```bash
    npm install
    ```
3. Start the local development server:
    ```bash
    npm run dev
    ```

---

## 📝 Architectural Notes & Considerations

* **Layer Separation:** The backend application enforces a strict separation of concerns using the *Service Layer* pattern. This guarantees that business logic is completely decoupled from REST controllers and persistent data models.
* **Data Persistence:** All domain data and relationship structures are handled natively via Object-Relational Mapping (ORM) to a relational MySQL database. Temporary in-memory structures or mocks are discarded to meet production-ready standards.

---
_Maintained by [AmadeoGabriel96](https://github.com/AmadeoGabriel96)_
