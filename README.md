# Library Management System

A Java-based library management system that handles media items, events, and patron management through a command-line interface.

## Prerequisites

### Java Development Kit (JDK)
- Version: JDK 8 or higher
- Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- Installation:
  - Windows: Install to default location (`C:\Program Files\Java\jdk-[version]`)
  - macOS: Install to default location (`/Library/Java/JavaVirtualMachines/`)
  - Linux: Install to default location (`/usr/lib/jvm/`)
- Verify installation by running: `java -version` in terminal/command prompt

### MySQL Server
- Version: MySQL 8.0 or higher
- Download: [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
- Installation:
  - Windows: Install to default location (`C:\Program Files\MySQL\MySQL Server 8.0`)
  - macOS: Install via package installer or homebrew (`brew install mysql`)
  - Linux: Install via package manager (`apt-get install mysql-server`)
- Default port: 3306
- Create a user with appropriate permissions for the application

### MySQL Connector/J (JDBC Driver)
- Version: 8.0 or compatible with your MySQL server version
- Download: [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
- Add the connector JAR file to your project's lib directory
- Required for database connectivity

## Project Structure
```
library-management-system/
├── src/
│   ├── Application.java
│   ├── Event.java
│   ├── Media.java
│   ├── Menu.java
│   ├── Patron.java
│   └── database.sql
├── lib/
│   └── mysql-connector-j-8.0.xx.jar
└── README.md
```

## Database Setup
1. Log into MySQL using your credentials:
   ```bash
   mysql -u your_username -p
   ```

2. Create and populate the database:
   ```bash
   source path/to/database.sql
   ```

3. Grant necessary permissions to your application user:
   ```sql
   GRANT ALL PRIVILEGES ON harry_potter_book_v2.* TO 'your_username'@'localhost';
   FLUSH PRIVILEGES;
   ```

## Compilation and Running

### Using Command Line
1. Navigate to the project root directory
2. Compile the Java files:
   ```bash
   javac -cp "lib/*" src/*.java -d bin/
   ```
3. Run the application:
   ```bash
   java -cp "bin:lib/*" Application
   ```
   For Windows, use semicolon instead of colon:
   ```bash
   java -cp "bin;lib/*" Application
   ```

### Using an IDE (e.g., Eclipse, IntelliJ IDEA)
1. Import the project as a Java project
2. Add MySQL Connector/J to the project's build path
3. Set up the project structure to match the above directory layout
4. Run Application.java as the main class

## Configuration
- Database URL: `jdbc:mysql://localhost:3306/harry_potter_book_v2`
- Default port: 3306
- Make sure your MySQL server is running before starting the application

## Application Features
- Media Management
  - Add/remove media items
  - View checked out media
  - Edit media location
- Event Management
  - Add/remove events
  - View event information
  - Edit event dates
- Patron Management
  - View library patrons
  - Add/remove patrons
  - Register patrons for events
  - Check out/return books

## Troubleshooting
1. Database Connection Issues:
   - Verify MySQL server is running
   - Check username and password
   - Confirm database exists and permissions are correct
   - Verify port 3306 is not blocked by firewall

2. Compilation Errors:
   - Ensure JDK is properly installed and PATH is set
   - Verify all source files are present
   - Check MySQL Connector/J is in lib directory and included in classpath

3. Runtime Errors:
   - Check database connection parameters
   - Verify all tables are created correctly
   - Ensure all required libraries are in classpath

## Support
For issues related to:
- Database: Check MySQL documentation at https://dev.mysql.com/doc/
- Java: Refer to Java documentation at https://docs.oracle.com/en/java/
- JDBC: See MySQL Connector/J documentation at https://dev.mysql.com/doc/connector-j/en/

## License
This project is provided for educational purposes. Please ensure you have appropriate licenses for all included software.
