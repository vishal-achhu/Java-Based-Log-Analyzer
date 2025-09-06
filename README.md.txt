# LogAnalyzer (Java)

A simple Java-based log analyzer that supports **rule-based detection**, **real-time tailing**, and **webhook alerts**.  
This tool is useful for **SOC Analysts, Developers, and Security Researchers** to quickly detect suspicious activities from logs.

---

##Features
- Parse and analyze log files (e.g., Apache/Nginx combined logs)
- Define rules in **YAML format** (keywords, regex, IDs, names)
- Real-time monitoring (`--tail`)
- Webhook alerts (Slack/Teams/Discord compatible)
- Console alerts with rule details
- Extensible for custom log formats

---

## Prerequisites
- **Java 17+**
- **Maven 3.9+**
- VS Code (or IntelliJ/Eclipse) recommended with Java support

---

## 🔧 Build Instructions

Clone/download the project and build with Maven:

```bash
mvn package



After build, the runnable JAR will be created at:

target/loganalyzer-0.2.0-jar-with-dependencies.jar


Analyze a static log file
java -jar target/loganalyzer-0.2.0-jar-with-dependencies.jar analyze \
  --file samples/logs/apache_combined.log \
  --rules samples/rules/rules.yml

Real-time tail monitoring
java -jar target/loganalyzer-0.2.0-jar-with-dependencies.jar analyze \
  --file samples/logs/apache_combined.log \
  --rules samples/rules/rules.yml --tail

Send webhook alerts
java -jar target/loganalyzer-0.2.0-jar-with-dependencies.jar analyze \
  --file samples/logs/apache_combined.log \
  --rules samples/rules/rules.yml --tail \
  --webhook https://hooks.slack.com/services/XXX/YYY/ZZZ


Rule Format

Rules are defined in YAML files like this:

- id: R001
  name: Suspicious Admin Access
  keyword: /admin

- id: R002
  name: SQL Injection Attempt
  keyword: "UNION SELECT"


Development in VS Code

Open project folder in VS Code.

Ensure you have extensions installed:

Java Extension Pack

Maven for Java

Build:

mvn package


Run:

Via terminal: java -jar ...

Or configure .vscode/launch.json to pass arguments.

Example Test

Append this log line to trigger detection:

echo '192.168.1.5 - - [10/Oct/2025:14:05:36 -0700] "GET /admin HTTP/1.1" 200 333 "-" "curl/8.0"' >> samples/logs/apache_combined.log


➡ LogAnalyzer will immediately generate:

[ALERT] R001 - Suspicious Admin Access matched: 192.168.1.5 - - [10/Oct/2025...]

 Use Cases

Detect brute-force, XSS, SQLi in web server logs

Monitor real-time logs for SOC operations

Send alerts to Slack/Teams/Discord channels

Extend rules for custom application logs


# Detailed Report (Full Documentation)

## 1. Introduction
The **Java LogAnalyzer** project is a lightweight tool for analyzing system or application logs.  
It allows users to define **rules** in YAML format and automatically detect suspicious activities in log files.  
It can run both in **batch mode (static files)** and **real-time tail mode**.

---

## 2. Project Architecture

### Components
1. **App.java**  
   - Entry point of the application.  
   - Parses CLI arguments (`analyze`, `--file`, `--rules`, `--tail`, `--webhook`).  

2. **Rule Engine**  
   - Loads rules from YAML.  
   - Each rule has:
     - `id` → Unique identifier (e.g., R001)  
     - `name` → Description of detection  
     - `keyword` → String/regex to search in logs  

3. **Log Reader**  
   - Reads log files line by line.  
   - In `--tail` mode, it keeps watching the file for new log entries.  

4. **Detection Module**  
   - Compares each log line against loaded rules.  
   - Generates alerts on matches.  

5. **Alerting Module**  
   - Prints to console.  
   - If `--webhook` is enabled, sends POST request with JSON payload.  

---

## 3. Working Process

1. **User provides inputs:**
   - Log file (`--file`)  
   - Rules (`--rules`)  
   - Optional: Tail mode, Webhook  

2. **Program flow:**
   1. Load rules from YAML  
   2. Open the log file  
   3. If in tail mode → start monitoring continuously  
   4. For each line:
      - Check if any rule’s keyword/regex matches  
      - If yes → generate alert  
      - If webhook is set → send alert to Slack  

3. **Outputs:**
   - Console alerts with `[ALERT]` prefix  
   - Webhook notifications  

---

## 4. User Modifications

Users can easily modify:
- **Rules file (`rules.yml`)**  
  - Add rules for SQLi, XSS, brute force, DoS, malware logs, etc.  
- **Input file**  
  - Change to Nginx, syslog, application logs.  
- **Webhook URL**  
  - Send alerts to different platforms (Slack, Teams, Discord).  
- **Codebase**  
  - Extend for JSON logs, log rotation, or multi-file monitoring.  

---

## 5. Example Rules
```yaml
- id: R001
  name: Suspicious Admin Access
  keyword: /admin

- id: R002
  name: SQL Injection Attempt
  keyword: "UNION SELECT"

- id: R003
  name: XSS Attack
  keyword: "<script>"

- id: R004
  name: Brute Force Login
  keyword: "401 Unauthorized"
6. How to Run (Step-by-Step)
Step 1: Install Java & Maven
Install Java 17 (JDK) → verify with java -version

Install Maven 3.9+ → verify with mvn -version

Step 2: Build the project
bash

cd loganalyzer-java-final
mvn package
Step 3: Run in batch mode
bash

java -jar target/loganalyzer-0.2.0-jar-with-dependencies.jar analyze \
  --file samples/logs/apache_combined.log \
  --rules samples/rules/rules.yml
Step 4: Run in real-time mode

java -jar target/loganalyzer-0.2.0-jar-with-dependencies.jar analyze \
  --file samples/logs/apache_combined.log \
  --rules samples/rules/rules.yml --tail
Step 5: Run with webhook alerts

java -jar target/loganalyzer-0.2.0-jar-with-dependencies.jar analyze \
  --file samples/logs/apache_combined.log \
  --rules samples/rules/rules.yml --tail \
  --webhook https://hooks.slack.com/services/XXX/YYY/ZZZ
7. Limitations
Works best with plain-text logs (Apache/Nginx/syslog).

Regex rules are basic (can be extended).

Only supports single log file at a time.

8. Future Enhancements
Support for JSON logs

Multi-file monitoring

Advanced detection (ML-based anomaly detection)

GUI Dashboard for alerts

With this documentation, anyone can:

Build the project

Understand how it works internally

Modify rules & extend features

Run it with detailed steps

