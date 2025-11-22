# Accounts Manager

Accounts Manager is a desktop application developed in Java with JavaFX, designed to help users securely manage all their accounts and passwords in one place.

## 🏃 How to Run

1. **Clone the repository:**
   ```sh
   git clone <repository-url>
   ```

2. **Navigate to the project directory:**
   ```sh
   cd Accounts_Manager
   ```

3. **Compile and run the application with Maven:**
   The `pom.xml` is configured with the JavaFX plugin, so you can start the application with the following command:
   ```sh
   mvn clean javafx:run
   ```

## 📂 Project Structure

The project follows a standard Maven structure:

```
src/
├── main/
│   ├── java/com/juanfran/accountsmanager/  # Application source code
│   │   ├── daos/         # Data Access Objects (DB interaction)
│   │   ├── di/           # Dependency Injection (Spring)
│   │   ├── fxmlcontrollers/ # Controllers for FXML views
│   │   ├── interfaces/   # Interfaces for DAOs and services
│   │   ├── managers/     # Main business logic
│   │   ├── models/       # Model classes (entities)
│   │   └── services/     # Services (encryption, generators, etc.)
│   └── resources/com/juanfran/accountsmanager/ # Application resources
│       ├── images/       # Icons and images
│       ├── properties/   # Configuration files (beans.xml, log4j.properties)
│       ├── styleSheet/   # CSS stylesheets
│       └── views/        # FXML files for the GUI
pom.xml                   # Maven configuration file
```

## 🚀 Features

- **Account Management:** Securely store and organize your account details (username, password, website).
- **Password Generator:** Create strong and secure passwords with different complexity levels.
- **Login and Registration:** User authentication system to protect data access.
- **Password Recovery:** "Forgot my password" feature that sends a recovery code to the user's email.
- **Recycle Bin:** Deleted accounts are moved to a recycle bin where they can be restored or permanently deleted.
- **Custom Search:** Easily search and filter your accounts.
- **Website Icons:** Automatically fetches and displays the favicon of the website associated with each account for easy identification.

## 🛠️ Technologies Used

- **Language:** Java 17
- **UI Framework:** JavaFX
- **Dependency Management:** Maven
- **Database:** Microsoft SQL Server
- **Additional Libraries:**
  - Spring Framework (for dependency injection)
  - Log4j (for logging)
  - JavaMail (for sending emails)
  - Jsoup (for web scraping icons)
  - Ikonli & BootstrapFX (for icons and styles)

## ✅ Prerequisites

Make sure you have the following installed before running the project:

- **JDK 17** (Java Development Kit)
- **Apache Maven**
- **Microsoft SQL Server:** A running instance of SQL Server is required. The connection settings are managed within the application.

## 📄 License

This project is licensed under the Apache License 2.0. See the `LICENSE` file for more details.
