# InnoShare - Academic Paper Sharing Platform

## Overview
InnoShare is an academic paper sharing platform designed to facilitate the sharing and discovery of academic literature. The application allows users to interact with various functionalities related to academic results, user interactions, literature searches, and auxiliary functions.

## Project Structure
The project is structured as follows:

```
InnoShare
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── innoshare
│   │   │           ├── controller        # Handles incoming requests
│   │   │           ├── service           # Contains business logic
│   │   │           ├── mapper            # Defines database operations
│   │   │           └── model             # Represents data structures
│   │   └── resources
│   │       └── application.properties     # Configuration properties
│   └── test
│       └── java
│           └── com
│               └── innoshare             # Contains test classes
├── pom.xml                                 # Maven configuration file
└── README.md                               # Project documentation
```

## Setup Instructions
1. **Clone the repository:**
   ```
   git clone <repository-url>
   cd InnoShare
   ```

2. **Build the project:**
   ```
   mvn clean install
   ```

3. **Run the application:**
   ```
   mvn spring-boot:run
   ```

## Usage
- Access the application through the web interface at `http://localhost:8080`.
- Explore the functionalities for sharing and discovering academic papers.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for details.