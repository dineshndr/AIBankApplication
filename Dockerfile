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
# FROM eclipse-temurin:21-jdk-alpine

# # Set the working directory inside the container
# WORKDIR /usr/app

# # Install curl and wget to download the JAR file
# RUN apk add --no-cache curl wget \
#     && wget --no-check-certificate -q --show-progress "https://github.com/dineshndr/AIBankApplication/releases/download/v0.0.1/BankWebApplication-0.0.1-SNAPSHOT.jar" -O /usr/app/BankWebApplication.jar \
#     && chmod +x /usr/app/BankWebApplication.jar \
#     && ls -lh /usr/app/BankWebApplication.jar

# # Expose the application port
# EXPOSE 8081

# # Command to run the application
# ENTRYPOINT ["java", "-jar", "/usr/app/BankWebApplication.jar"]

version: '3.8'
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=paymentdb'
      - 'POSTGRES_USER=dineshndr'
      - 'POSTGRES_PASSWORD=12345'
    ports:
      - '5432:5432'
    networks:
      - mynetwork

  app:
    build:
      context: .
      dockerfile: Dockerfile
    environment:
      - DB_HOST=postgres
      - DB_PORT=5432
      - DB_NAME=paymentdb
      - DB_USERNAME=dineshndr
      - DB_PASSWORD=12345
      - OPENAI_API_KEY=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
    ports:
      - '8081:8081'
    depends_on:
      - postgres
    networks:
      - mynetwork

networks:
  mynetwork:
    driver: bridge
