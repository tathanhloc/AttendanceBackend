# Face Attendance System - Backend

## Overview

The **Face Attendance System** is a backend application designed to manage attendance using facial recognition technology. It provides APIs for user management, attendance tracking, and facial data processing. This system is part of a larger project that integrates with a frontend interface for seamless attendance management.

## Features

- Facial recognition for attendance marking.
- User management (add, update, delete users).
- Attendance tracking and reporting.
- RESTful API for integration with frontend systems.
- Secure and scalable backend architecture.

## Technologies Used

- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL
- **Server**: Apache Tomcat (embedded in Spring Boot)
- **Other Tools**: OpenCV (for facial recognition)

## Installation

Follow these steps to set up the backend locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/face-attendance-system.git
   ```

2. Navigate to the backend directory:
   ```bash
   cd face-attendance-system/backend/face-attendance
   ```

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

4. Set up the `application.properties` file:
   - Copy the `application.properties.example` file to `application.properties`.
   - Update the database credentials and other environment variables.

5. Run database migrations (if applicable):
   ```bash
   mvn flyway:migrate
   ```

6. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

The backend exposes the following key API endpoints:

- **User Management**:
  - `POST /api/users` - Add a new user.
  - `GET /api/users` - Retrieve all users.
  - `PUT /api/users/{id}` - Update user details.
  - `DELETE /api/users/{id}` - Delete a user.

- **Attendance**:
  - `POST /api/attendance` - Mark attendance.
  - `GET /api/attendance` - Retrieve attendance records.

- **Facial Recognition**:
  - `POST /api/recognize` - Process facial data for recognition.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push them to your fork.
4. Submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For any questions or support, please contact the project maintainer at `your-email@example.com`.
