# 🚦 Traffic Light Controller

---

## 📌 Project Overview

The Traffic Light Controller is a desktop-based application developed using **Java Swing** and **Spring Boot**.

The system simulates a single-junction traffic light controller with automatic traffic-light sequencing, countdown timing, manual control, pause and resume functionality, and emergency handling.

The application follows a client-server architecture. The Java Swing application acts as the frontend client, while the Spring Boot application manages the traffic-light operations through RESTful APIs.

The backend maintains the traffic-light state in memory without using a database or file-based persistence.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Java Swing | Desktop graphical user interface |
| Spring Boot | Backend application |
| Spring Web | REST API development |
| Maven | Project and dependency management |
| HTTP Client | Communication between frontend and backend |
| Jackson | JSON data processing |
| IntelliJ IDEA | Development environment |

## 🏗️ System Architecture

The application consists of two independent Java projects:

```text
Traffic Light Controller
│
├── traffic-light-client
│   └── Java Swing Frontend
│       ├── Traffic Light Display
│       ├── Countdown Display
│       └── Control Buttons
│
└── traffic-light-backend
    └── Spring Boot Backend
        ├── REST Controller
        ├── Service Layer
        ├── Model Layer
        └── In-Memory State Management
```

The frontend communicates with the backend using HTTP requests and JSON responses.

## 🚦 Traffic Light Sequence

The automatic traffic-light sequence follows this order:

| Traffic Light | Duration | Meaning |
|---|---:|---|
| 🔴 RED | 10 seconds | Stop |
| 🟢 GREEN | 8 seconds | Go |
| 🟡 YELLOW | 3 seconds | Prepare to stop |

The sequence repeats continuously while automatic mode is running.

## ✨ Main Features

### 1. Automatic Traffic-Light Control
Automatically switches between RED, GREEN and YELLOW according to the specified durations.

### 2. Countdown Timer
Displays the remaining time for the currently active traffic light.

### 3. Pause and Resume
Pauses the automatic sequence and continues from the remaining time when resumed.

### 4. Manual Traffic-Light Control
Allows users to manually select RED, YELLOW or GREEN when automatic operation is stopped.

### 5. Emergency Override
Immediately forces the traffic light to RED and prevents automatic operation until the system is reset.

### 6. Stop and Reset
- **Stop:** Returns the traffic light to RED and sets the mode to STOPPED.
- **Reset:** Clears emergency status and restores the initial system state.

### 7. Error and Connection Handling
Handles invalid operations and backend connection failures while keeping the GUI responsive.

## 🔗 REST API Endpoints

Base URL:

```text
http://localhost:8080/api/traffic
```

| HTTP Method | Endpoint | Description |
|---|---|---|
| GET | `/status` | Retrieve current traffic-light status |
| POST | `/start` | Start automatic operation |
| POST | `/pause` | Pause automatic operation |
| POST | `/resume` | Resume automatic operation |
| POST | `/stop` | Stop the traffic-light sequence |
| POST | `/reset` | Reset the system |
| POST | `/emergency` | Activate emergency mode |
| POST | `/manual/{light}` | Select a light manually |

## 📂 Project Structure

```text
Traffic-Light-Controller-IS7111/
│
├── traffic-light-backend/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/trafficlight/
│   │               ├── controller/
│   │               ├── model/
│   │               ├── service/
│   │               └── TrafficLightBackendApplication.java
│   └── pom.xml
│
├── traffic-light-client/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/trafficlight/client/
│   │               ├── TrafficLightState.java
│   │               ├── TrafficLightApiClient.java
│   │               ├── TrafficLightPanel.java
│   │               ├── TrafficLightGuiController.java
│   │               └── TrafficLightClientApplication.java
│   └── pom.xml
│
├── screenshots/
│
├── .gitignore
└── README.md
```

## ▶️ How to Run the Application

### Prerequisites

- Java Development Kit (JDK) 21
- Apache Maven
- IntelliJ IDEA

### Step 1 – Run the Backend

1. Open the `traffic-light-backend` project in IntelliJ IDEA.
2. Open `TrafficLightBackendApplication.java`.
3. Run the application.
4. Wait until the Spring Boot server starts successfully.

The backend runs at:

```text
http://localhost:8080
```

### Step 2 – Run the Swing Client

1. Open the `traffic-light-client` project in IntelliJ IDEA.
2. Open `TrafficLightClientApplication.java`.
3. Run the application.
4. Ensure that the backend is running before starting the client.

The Swing application will connect to the backend and retrieve the current traffic-light status.

## 📸 Screenshots

Application screenshots and testing evidence are provided in the [`screenshots`](screenshots/) directory.

## 💾 Data Storage

The application uses in-memory data storage to maintain the traffic-light state.

No external database or file-based persistence is used.

## 👨‍💻 Development

**Programming Language:** Java  
**Backend Framework:** Spring Boot  
**Frontend Technology:** Java Swing  
**Build Tool:** Maven  
**IDE:** IntelliJ IDEA

---
