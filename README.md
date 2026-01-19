# Architecture Application

## Application Launch Guide

This README provides an overview of how to launch the Architecture Application. It covers IDE configuration, Docker Compose setup, services deployment, testing application endpoints, and authentication.

### IDE Configuration
1. **Install the IDE**: Ensure you have an IDE such as Visual Studio Code, IntelliJ, or Eclipse installed.
2. **Clone the Repository**: Clone the repository using:
   ```bash
   git clone https://github.com/LiberiBg/ArchitectureApplication.git
   ```
3. **Open the Project**: Open the downloaded project folder in your IDE.
4. **Install Dependencies**: Make sure to install all required dependencies specified in the `requirements.txt` or similar files. 

### Docker Compose
1. **Install Docker**: Ensure that Docker and Docker Compose are installed on your machine.
2. **Build the Containers**: Navigate to the project directory and run:
   ```bash
   docker-compose build
   ```
3. **Start the Application**: Once built, start the application using:
   ```bash
   docker-compose up
   ```

### Services Deployment
- The application comprises multiple services that are deployed using the Docker Compose file. Ensure that each service is correctly defined in the `docker-compose.yml` file.
- Deployment settings for each service can be customized in the same file.

### Testing Application Endpoints
1. **Use Postman or Curl**: You can test the application endpoints using Postman or Curl.
2. **Check API Documentation**: Refer to the API documentation at `/docs` for a list of available endpoints and what they do.
3. **Example Test**: To test a specific endpoint, use:
   ```bash
   curl -X GET http://localhost:8000/api/example
   ```

### Authentication
1. **User Registration**: Before accessing secured endpoints, users must register via the registration endpoint.
2. **Login**: After registration, authenticate using the login endpoint to receive a JWT token.
3. **Using JWT Token**: Include the token in the Authorization header for subsequent requests:
   ```text
   Authorization: Bearer <your_token_here>
   ```

For any questions or issues, feel free to reach out to the maintainers of this repository.