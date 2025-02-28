# # Use an official OpenJDK runtime as a parent image
# FROM eclipse-temurin:21-jdk-alpine

# # Set the working directory inside the container
# WORKDIR /usr/app

# # Download the JAR from Google Drive (modify link accordingly)
# RUN apk add --no-cache curl \
#     && curl -L -o BankWebApplication.jar "https://drive.google.com/uc?export=download&id=1xGEyvGjp73kMSYuK0WW7mOw5SZTDH3Au"

# # Expose the application port
# EXPOSE 8081

# # Command to run the application
# ENTRYPOINT ["java", "-jar", "/usr/app/BankWebApplication.jar"]

# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /usr/app

# Install curl and wget to download the JAR file
RUN apk add --no-cache curl wget \
    && wget --no-check-certificate -q --show-progress "https://github.com/dineshndr/AIBankApplication/releases/download/v0.0.1/BankWebApplication-0.0.1-SNAPSHOT.jar" -O /usr/app/BankWebApplication.jar \
    && chmod +x /usr/app/BankWebApplication.jar \
    && ls -lh /usr/app/BankWebApplication.jar

# Expose the application port
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "/usr/app/BankWebApplication.jar"]
