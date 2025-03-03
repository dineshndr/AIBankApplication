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

# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /usr/app

# Set environment variables for the download URL and JAR file name
ENV JAR_URL="https://github.com/dineshndr/AIBankApplication/releases/download/v0.0.1/BankWebApplication-0.0.1-SNAPSHOT.jar"
ENV JAR_FILE="BankWebApplication.jar"

# Set environment variables for Spring properties
ENV SPRING_DATASOURCE_URL="jdbc:postgresql://postgres:5432/paymentdb"
ENV SPRING_DATASOURCE_USERNAME="dineshndr"
ENV SPRING_DATASOURCE_PASSWORD="12345"
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME="org.postgresql.Driver"
ENV SPRING_JPA_DATABASE_PLATFORM="org.hibernate.dialect.PostgreSQLDialect"
ENV SPRING_JPA_HIBERNATE_DDL_AUTO="update"
ENV SPRING_AI_OPENAI_API_KEY="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"
ENV SPRING_APPLICATION_NAME="BankWebApplication"
ENV SPRING_CLOUD_DISCOVERY_ENABLED="false"
ENV EUREKA_CLIENT_ENABLED="false"
ENV SERVER_PORT="8081"

# Install curl and wget to download the JAR file
RUN apk add --no-cache curl wget \
    && wget --no-check-certificate -q --show-progress "$JAR_URL" -O /usr/app/$JAR_FILE \
    && chmod +x /usr/app/$JAR_FILE \
    && ls -lh /usr/app/$JAR_FILE

# Expose the application port
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "/usr/app/BankWebApplication.jar"]
