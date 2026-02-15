# Task Tracker CLI

A simple command-line task management application built with Java and Maven.

**Project URL:** https://roadmap.sh/projects/task-tracker

**GitHub Repository:** https://github.com/aposalik/Task-Tracker

## Features

- ✅ Add new tasks
- ✅ List all tasks
- ✅ JSON file storage
- ✅ Task status tracking (todo, in-progress, done)

## Requirements

- Java 25 or higher
- Maven

## Installation

1. Clone the repository:
```bash
git clone https://github.com/aposalik/Task-Tracker.git
cd Task-Tracker
```

2. Build the project:
```bash
mvn clean package
```

## Usage

### Add a task
```bash
java -jar target/task-cli.jar add "Buy groceries"
```

### List all tasks
```bash
java -jar target/task-cli.jar list
```

## Project Structure

```
Task-Tracker/
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── example/
│                   ├── Main.java
│                   ├── Task.java
│                   └── LocalDateTimeAdapter.java
├── pom.xml
└── tasks.json (generated)
```

## Technologies Used

- Java 25
- Gson (JSON serialization)
- Maven

## Future Enhancements

- [ ] Update task description
- [ ] Delete tasks
- [ ] Mark tasks as in-progress
- [ ] Mark tasks as done
- [ ] Filter tasks by status

## Author

**Abdullah Salik** ([@aposalik](https://github.com/aposalik))

---

<p><img align="left" src="https://github-readme-stats.vercel.app/api/top-langs?username=aposalik&show_icons=true&locale=en&layout=compact" alt="aposalik" /></p>
<p>&nbsp;<img align="center" src="https://github-readme-stats.vercel.app/api?username=aposalik&show_icons=true&locale=en" alt="aposalik" /></p>
<p><img align="center" src="https://github-readme-streak-stats.herokuapp.com/?user=aposalik&" alt="aposalik" /></p>
