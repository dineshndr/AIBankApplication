# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container (optional, can be the root directory)
WORKDIR /usr/app

# Copy the built JAR file from the target directory into the container
COPY BankWebApplication.jar /usr/app/BankWebApplication.jar

# Expose the application port (modify as per your app's port)
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "/usr/app/BankWebApplication.jar"]
