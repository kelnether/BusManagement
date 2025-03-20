# Bus Management Information System

## 1. Project Overview
This project aims to build a Bus Management Information System that enhances the efficiency of managing bus routes through informatization. The system provides users with convenient route queries and transfer options, promoting the development of intelligent urban public transportation.

## 2. System Requirements Analysis
### Information Requirements
- **Bus Route Information**: Includes route ID, route name, and other basic details.
- **Bus Stop Information**: Contains stop ID and stop name.
- **Operating Company Information**: Stores details such as company name and website.

### Functional Requirements
- **User Management**  
  - **Registration**: Users can create an account via the registration page.  
  - **Login/Logout**: After logging in, users can access protected resources.
- **Route Management**  
  - **View Routes**: Display all bus route details, including route names and operating companies.
  - **Add Route**: Administrators can add new bus routes by setting the route name, selecting an operating company, and adding stop information.
  - **Edit Route**: Modify existing bus route details.
  - **Delete Route**: Remove bus routes that are no longer in use.
- **Stop Management**  
  - **View Stops**: Display detailed information about all bus stops.
  - **Add Stop**: Add stops to new bus routes.
- **Company Management**  
  - **View Companies**: Display details of all operating companies.
  - **Add Company**: Input new operating company details, including name and website.
  - **Edit Company**: Modify details of existing companies.
  - **Delete Company**: Remove companies no longer in use.
  - **Assign Company**: Assign or change the operating company for a bus route.
- **Route Query**  
  - **Name-Based Search**: Query routes or stops by name.
  - **Sorting**: Sort query results by different criteria for easier information retrieval.
- **Transfer Query**  
  - **Transfer Route Query**: Enter the start and end stops to obtain the optimal transfer route, including intermediate stops and the total distance.
- **User Interface**  
  - **Home Page**: Provides quick access to transfer queries, route management, stop management, and company management.
  - **Design**: Uses Bootstrap and custom CSS to ensure a visually appealing and well-organized layout.

## 3. Database Design
### Conceptual Design
The system's database includes the following entities and their attributes:
- **Companies**  
  - `id` (Primary Key)  
  - `name`  
  - `website`
- **Bus Route**  
  - `id` (Primary Key)  
  - `line_name`  
  - `company_id` (Foreign Key referencing Companies)
- **Bus Stop**  
  - `id` (Primary Key)  
  - `stop_name`
- **Bus Stop Line**  
  - `line_id` (Foreign Key referencing Bus Route, part of a composite key)  
  - `stop_id` (Foreign Key referencing Bus Stop, part of a composite key)  
  - `sequence` (Indicates the order of stops on a route)
- **Bus Stop Distance**  
  - `id` (Primary Key)  
  - `start_stop_id` (Foreign Key referencing Bus Stop)  
  - `end_stop_id` (Foreign Key referencing Bus Stop)  
  - `distance`
- **Users**  
  - `id` (Primary Key)  
  - `username`  
  - `password`

#### Entity Relationships
- **Companies** and **Bus Route**: One-to-many relationship (one company can manage multiple routes).
- **Bus Route** and **Bus Stop Line**: One-to-many relationship (a route can include multiple stops).
- **Bus Stop** and **Bus Stop Line**: One-to-many relationship (a stop may belong to multiple routes).
- **Bus Stop** and **Bus Stop Distance**: Self-referencing relationship that connects two stops to represent the distance between them.

### Logical and Physical Design
- **Logical Design**: Converts the E-R diagram into relational models, using foreign keys to enforce relationships.
- **Physical Design**:  
  - **Database**: MySQL  
  - **Storage Engine**: InnoDB (to support transactions)  
  - **Character Set**: UTF-8 (to support multiple languages)  
  - **Indexes**: Created on primary keys and frequently queried fields to enhance performance.

> **Example SQL Statements:**
> ```sql
> CREATE DATABASE Bus;
> USE Bus;
> 
> -- Create companies table
> CREATE TABLE companies (
>     id INT AUTO_INCREMENT PRIMARY KEY,
>     name VARCHAR(255) NOT NULL,
>     website VARCHAR(255)
> );
> 
> -- Create bus_line table
> CREATE TABLE bus_line (
>     id INT AUTO_INCREMENT PRIMARY KEY,
>     line_name VARCHAR(255) NOT NULL,
>     company_id INT,
>     FOREIGN KEY (company_id) REFERENCES companies (id)
> );
> 
> -- Create bus_stop table
> CREATE TABLE bus_stop (
>     id INT AUTO_INCREMENT PRIMARY KEY,
>     stop_name VARCHAR(255) NOT NULL
> );
> 
> -- Create bus_stop_line table
> CREATE TABLE bus_stop_line (
>     line_id INT,
>     stop_id INT,
>     sequence INT,
>     PRIMARY KEY (line_id, stop_id),
>     FOREIGN KEY (line_id) REFERENCES bus_line (id) ON DELETE CASCADE,
>     FOREIGN KEY (stop_id) REFERENCES bus_stop (id) ON DELETE CASCADE
> );
> 
> -- Create bus_stop_distance table
> CREATE TABLE bus_stop_distance (
>     id INT AUTO_INCREMENT PRIMARY KEY,
>     start_stop_id INT,
>     end_stop_id INT,
>     distance DOUBLE,
>     FOREIGN KEY (start_stop_id) REFERENCES bus_stop (id),
>     FOREIGN KEY (end_stop_id) REFERENCES bus_stop (id)
> );
> 
> CREATE DATABASE Users;
> USE Users;
> 
> -- Create users table
> CREATE TABLE users (
>     id INT AUTO_INCREMENT PRIMARY KEY,
>     username VARCHAR(255) NOT NULL UNIQUE,
>     password VARCHAR(255) NOT NULL
> );
> ```

## 4. System Design
### Architectural Overview
The system is divided into two main parts:
- **Front-End**:  
  Implements the user interface using HTML, CSS, JavaScript, and JSP. It uses Bootstrap along with custom CSS to ensure an attractive and organized layout.
- **Back-End**:  
  Developed in Java with the Spring MVC framework to handle business logic. Data is stored in a MySQL database.
- **Layered Design**:  
  - **Controller**: Handles incoming user requests and delegates tasks to the service layer.  
  - **Service**: Contains business logic such as data validation and processing (CRUD operations).  
  - **DAO (Data Access Object)**: Responsible for interacting with the database by executing SQL statements.  
  - **Model**: Defines the Java classes that correspond to database tables.

## 5. Implementation and Testing
### Core Functionality
- **Transfer Route Algorithm**:  
  - Utilizes Breadth-First Search (BFS) to traverse the bus stop network and find all possible transfer routes.
- **Total Distance Calculation**:  
  - Inspired by Dijkstra’s algorithm, calculates the total distance of each transfer route and sorts the routes by distance.
- **Login Verification and Access Control**:  
  - Ensures that users are authenticated before accessing protected resources by checking login status and redirecting to the login page if needed.

### Testing Strategy
- **Unit Testing**: Each module is tested individually to ensure correct functionality.
- **Integration Testing**: Ensures that the front-end and back-end interact seamlessly and that data is correctly transmitted.
- **User Testing**: Selected users test the system and provide feedback for further improvements.

## 6. Functional Modules and Usage Instructions
### Main Functional Modules
- **User Management**  
  - **Registration**: Users register by providing a username and password.  
  - **Login/Logout**: Upon logging in, users can access system features; unauthenticated users are redirected to the login page.
- **Route Management**  
  - **View Routes**: Displays all bus routes with associated operating companies and stops.  
  - **Add/Edit/Delete Routes**: Provides interfaces for managing routes and assigning operating companies.
- **Stop Management**  
  - **View Stops**: Displays all bus stop information.  
  - **Add/Delete Stops**: Allows administrators to manage bus stops.
- **Company Management**  
  - **View, Add, Edit, Delete Companies**: Manages operating company information.
- **Query Module**  
  - **Route Query**: Enables search by route or stop name with sorting options.  
  - **Transfer Query**: Users input start and end stops to view optimal transfer routes, including the stops along the route and the total distance.

### How to Use the System
1. **Registration and Login**  
   - New users register with a username and password, then log in to access system functions.
2. **Managing Routes**  
   - After logging in, navigate to the route management page to add, edit, or delete routes.
3. **Transfer Query**  
   - On the home or transfer query page, enter the start and end stops to display computed transfer routes sorted by total distance.
4. **Stop and Company Management**  
   - Use the respective management pages to add, modify, or remove bus stops and company details.

## 7. Conclusion
This project demonstrates the design and implementation of a Bus Management Information System that improves the efficiency of managing bus routes, stops, and operating companies while providing a convenient transfer query feature. Built on a robust Java Web architecture (Spring MVC + MySQL), the system is designed for maintainability and scalability. Future enhancements could further optimize transfer algorithms and refine the user interface to meet evolving needs in intelligent public transportation management.
