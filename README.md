# 📘 Course Search Application (Spring Boot + Elasticsearch)

This project is a Spring Boot application that uses Elasticsearch to store and search through course documents. It supports:

- ✅ Full-text search on `title`, `description`, `instructor`
- ✅ Filtering on `age`, `price`, `category`, `type`, `date`
- ✅ Sorting and Pagination
- ✅ 🔍 Autocomplete Suggestions
- ✅ 🔎 Fuzzy Matching (for typos like "Phyics" ➝ "Physics")

---

## 🏗️ Tech Stack

- Java 22
- Spring Boot 2.7.x
- Spring Data Elasticsearch
- Docker + Elasticsearch 7.17
- Maven

---

## 📂 Project Structure
src/
└── main/
├── java/com/example/elasticsearch/
│ ├── controller/ # REST Controllers
│ ├── model/ # Course document class
│ ├── repository/ # Spring Data Elasticsearch repository
│ ├── service/ # Business logic
│ └── ElasticsearchApplication.java
└── resources/
├── application.properties
└── sample-courses.json # 50 sample course documents
docker-compose.yml
README.md
pom.xml


---

## 🚀 Quick Start Guide

### 1. 🔧 Prerequisites

- Docker installed
- Java 21/22 installed
- Maven installed

---

## 🐳 Step 1: Launch Elasticsearch using Docker and Build and Run Spring Boot App

```bash
docker-compose up -d
mvn clean install
mvn spring-boot:run
```
## Step 2: Sample Data Ingestion
```bash
curl  GET "http://localhost:8080/courses/search?keyword=math"

[
  {
    "id": "course-003",
    "title": "Math Genius Workshop",
    "category": "Math",
    "price": 99.99,
    "nextSessionDate": "2025-07-12T10:00:00Z"
  },
  ...
]
```
#🔎 Step 3: Search API Usage
#✅ Basic Search with Pagination + Sorting
```bash
GET /courses/search

| Param     | Description                         |
| --------- | ----------------------------------- |
| keyword   | Search text                         |
| page      | Page number (default = 0)           |
| size      | Page size (default = 10)            |
| sortField | Field to sort by (`title`, `price`) |

curl "http://localhost:8080/courses/search?keyword=spring&page=0&size=5&sortField=title"
```
#Step 4: Autocomplete API
```bash
GET /courses/suggest?prefix=phy

Example Request:
curl "http://localhost:8080/courses/suggest?prefix=phy"
Expected Output:
json
[
  "Physics Fundamentals",
  "Physical Chemistry for Kids"
]
```
#🐛 Step 6: Fuzzy Search API
```bash
GET /courses/fuzzy?title=phyics
Example Request:

curl "http://localhost:8080/courses/fuzzy?title=phyics"
Expected Output:

json

[
  {
    "id": "course-004",
    "title": "Physics Fundamentals",
    ...
  }
]
```
#Try Them
```bash
curl -X DELETE http://localhost:9200/courses

http://localhost:8080/courses/search?keyword=java&page=0&size=5&sortField=title

```
📘 API Usage Guide
✅ 1. Create Courses
Endpoint:
POST /courses

URL:
```bash
http://localhost:8080/courses
Headers:

Content-Type: application/json
Request Body (JSON):

json
{
  "id": "1",
  "title": "Java Programming",
  "instructor": "John Doe",
  "description": "A complete Java course for beginners",
  "duration": 40
}
```
📌 Instructions to Test:

Open Postman or any REST client.

Set method to POST

Set URL to http://localhost:8080/courses

Go to Body > raw > JSON

Paste the above sample JSON.

Click Send

📋 Expected Output:
```bash
Status Code: 200 OK

Response Body: Echo of the submitted course object:

{
  "id": "1",
  "title": "Java Programming",
  "instructor": "John Doe",
  "description": "A complete Java course for beginners",
  "duration": 40
}
```
✅ 2. Search Courses
Endpoint:
GET /courses/search

Example URL:

```bash

http://localhost:8080/courses/search?keyword=java&page=0&size=5&sortField=title
```
📌 Instructions to Test:
```
Use method: GET

Paste the URL into browser or Postman.

Press Send

📋 Expected Output:

Returns a paginated list of courses containing the keyword java

Sample Response:

json
[
  {
    "id": "1",
    "title": "Java Programming",
    "instructor": "John Doe",
    "description": "A complete Java course for beginners",
    "duration": 40
  }
]
```
✅ 3. Fuzzy Search
Endpoint:
GET /courses/fuzzy

Example URL:

```bash
http://localhost:8080/courses/fuzzy?title=jav
📌 Instructions to Test:

Use method: GET

Paste the URL into Postman/browser.

Press Send

📋 Expected Output:

Returns results for fuzzy matches like jav, even if the exact title is Java Programming.
[
  {
    "id": "1",
    "title": "Java Programming",
    "instructor": "John Doe",
    "description": "A complete Java course for beginners",
    "duration": 40
  }
]
```
✅ 4. Autocomplete Suggestions
Endpoint:
GET /courses/suggest

Example URL:

```bash
http://localhost:8080/courses/suggest?prefix=jav
📌 Instructions to Test:

Use method: GET

Enter the URL into Postman or browser.

Click Send

📋 Expected Output:

["Java Programming"]
✅ Success Criteria:

Returns suggestions matching the prefix.

Useful for real-time autocomplete features.
```
