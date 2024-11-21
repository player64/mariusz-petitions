# Mariusz's Petitions app

This project is a petition management web application built using Spring Boot and deployed
with Jenkins, Docker, and Amazon EC2. The application enables users to create, view, search for, and sign petitions, 
ensuring accessibility through web browsers and facilitating smooth interaction among its components
The project aims to enhance application development, testing, and deployment automation skills while integrating 
web technologies with DevOps practices. It utilises a Continuous Integration (CI) pipeline, where Jenkins
automates the code integration, testing, and packaging processes. The pipeline includes several stages, such
as code checkout, application building, testing, packaging the application into a WAR file, and preparing it for
deployment. The application runs on a Dockerised Tomcat server hosted on an Amazon EC2 instance.


## Organisation of the application


### Core functionalities

#### Petition creation
- Users can create petitions by submitting a title and its details (name and email address) through a dedicated form.
- The application includes strict input validation to ensure the quality of data:
- Titles must be unique; duplicate submissions are rejected.
- Input sanitisation checks prevent invalid entries, such as empty strings or excessive whitespace
- Preloaded petitions are provided to showcase the application's functionality.

#### Petition Management
- The main page displays all petitions along with their titles. 
- Each petition can be clicked to view detailed information, including signatures and the option to sign it using a form.

#### Petition Signing
- Users can sign petitions by providing their name and email address.
- Validation ensures:
   - Names cannot be empty or consist solely of whitespace.
   - Email addresses must follow a proper format.
   - Duplicate signatures (based on email) are not allowed for the same petition, preventing misuse and preserving data accuracy.


#### Search Functionality
- A search bar enables users to filter petitions by title.
- The system supports partial matches and ignores leading/trailing whitespace in the search query.
- If no results are found, a user-friendly message informs the user.

#### Error Handling
- Customised error pages, such as a 404 page or error prompts, guide users when they encounter invalid URLs or enter the wrong data into forms.

#### Data Management
- The application stores petitions and signatures in the in-memory Java collection _ArrayList_. This setup facilitates quick data retrieval and demonstrates the application. 

### Tests
Different test cases are used to verify the application’s reliability in various scenarios:
    
- Petition Creation:
  - Verifies that petitions with duplicate titles are rejected.
  - Confirms that titles aren’t empty or contain only whitespace are flagged as errors.
    
  - Petition Signing:
    - Ensures that users with the same email cannot sign a petition multiple times.
    - Validates that the name field cannot contain only whitespace.
    - Checks that invalid email formats (e.g., missing “@” or domain) are rejected.
    
  - Search Functionality:
   - Tests for accurate search results when partial or full titles are provided.
   - Ensures that searches with only whitespace return all petitions.

### Deployment Process
- Continuous Integration (CI) Pipeline:
  - The application employs a Jenkins-based CI pipeline automatically triggered by a configured GitHub webhook. Any push to the repository initiates the pipeline.
  - The pipeline stages include:
    - Code Checkout: Retrieves the latest code from the repository.
    - Build: Compiles the project and resolves dependencies.
    - Test: Executes unit tests to verify code functionality.
    - Packaging: Packages the application into a WAR file for deployment.
    - Archive Artifacts: Stores the generated WAR file in Jenkins as an artefact for reuse or manual download if needed.
    - Manual Approval for Deployment: A manual approval stage allows a user to review and approve the deployment process before updating the production environment.
    - Deployment to Amazon EC2: The Dockerised application is deployed to an EC2 instance for public accessibility. The deployment process involves building the Docker image, removing any existing container, and running the new container to expose the application on port 9090.

### Technologies Used
- **Spring Boot**: Backend framework for application logic and API development.
- **Thymeleaf**: Template engine for rendering dynamic HTML views.
- **Maven**: Dependency management and build tool.
- **JUnit**: For unit and integration testing to ensure functionality.
- **GitHub**: Version control system with structured commits and feature tracking.
- **Jenkins**: CI/CD tool to automate code integration, testing, and deployment.
- **Docker**: Containerisation to simplify application deployment.
- **Amazon EC2**: Hosting platform for public accessibility.
- **Bootstrap**: Frontend CSS framework for creating responsive and visually appealing user interfaces.
