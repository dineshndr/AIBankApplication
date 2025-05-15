# 🏦 AI Bank Application

A full-stack intelligent banking web application that offers a seamless user experience and dynamic interaction with an AI assistant. Built using **Spring Boot** and **React**, this system empowers users to manage their banking operations and get smart, conversational insights powered by **OpenAI GPT**.

---

## 📌 Project Overview

This project simulates a modern, AI-powered digital banking experience. After successful login, users are navigated to a feature-rich **Dashboard** where they can:

- View **User Details**: Account number, name, balance, etc.  
- Use core features:
  - ✅ **Send Money**
  - ✅ **Receive Money**
  - 📊 **Transaction History**
- Chat with a built-in **AI Assistant** to:
  - Check payment status
  - View recent transactions
  - Apply for new loans
  - Track loan application status

---

## 🧠 AI Banking Assistant (Powered by OpenAI)

This application integrates OpenAI's GPT API to provide a responsive, chat-like assistant on the user dashboard.

### 🔍 AI Assistant Features

Users can type questions like:

- "What’s my current balance?"
- "Did I receive payment from John?"
- "What’s the status of my loan?"
- "Show me my last 3 transactions."

### ⚙️ How it Works

1. The frontend sends user input to the backend `/ai/message` endpoint.
2. `AIService.java` uses the OpenAI API Key to query GPT.
3. The AI response is displayed interactively in the chat panel.

API key is securely injected through the `application.properties` file.

---


---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17+
- Node.js 14+
- Docker (optional)
- OpenAI API Key

---

## 🔧 Backend Setup (Spring Boot)

```bash
# Build and run backend
./mvnw clean install
./mvnw spring-boot:run

Ensure you set your OpenAI API key in src/main/resources/application.properties:
openai.api.key=your_openai_api_key
🌐 Frontend Setup (React)
cd bankapp-frontend
npm install
npm start
Runs the app at: http://localhost:3000


🐳 Docker Setup
Build & Run Entire Stack
bash
Copy
Edit
docker compose up --build
Frontend: http://localhost:3000

Backend API: http://localhost:8080

🧪 Testing
Backend tests: ./mvnw test

Frontend tests: npm test

🛡️ Technologies Used
🔧 Spring Boot, Spring Security, Spring Data JPA

⚛️ React, Axios, React Router

🧠 OpenAI GPT API

🐳 Docker, Docker Compose

🧪 GitHub Actions (CI/CD)

🛢️ MySQL

🌐 RESTful API Architecture


