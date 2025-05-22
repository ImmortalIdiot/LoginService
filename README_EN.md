# 🚪 User login enrichment service

A service that enriches user login events with data from a sharded PostgreSQL database.

---

## 🧱 Architecture

- 🛠 **Kotlin + Ktor + Exposed** — for the REST API, core logic, and database access  
- 🧵 **Apache Kafka** — for event streaming and processing  
- 🗃 **PostgreSQL** — sharded by `user_id` (even/odd)  
- 🐳 **Docker Compose** — to deploy the entire infrastructure  

---

## ⚙️ Required Tools

1. 🐳 **Docker Desktop** — to download images and run containers  
2. 💡 **IDE** (IntelliJ IDEA by JetBrains recommended) — to run the application  
3. 📬 **Postman** — to send API requests  
4. 🌐 **Browser** — to access **Kafdrop** and inspect Kafka topics  

---

## 🚀 Launch

1. Make sure Docker Desktop and Docker Compose are installed.

2. Start Docker Desktop, then run all services:
```bash
docker-compose up -d
```

3. Check that all services are running:
```bash
docker-compose ps
```
Or check Docker Desktop (you should see 5 containers with green indicators — meaning `Running` status).

**Expected Containers:**
- `shard1`  
- `shard2`  
- `zookeeper`  
- `kafka`  
- `kafdrop`  

---

4. Launch the application in your IDE. (Program enter point - file `Application.kt` method `main()`)

---

## 🧪 Testing

1. Open **Postman**  
2. Choose the `POST` method  
3. Enter the URL: `http://localhost:8282/api/user/login`  
4. Go to the `Body` tab → Select `raw` and choose `JSON`  
5. Paste the following example:
```json
{
    "userId": 5,
    "ipAddress": "192.168.4.4",
    "timestamp": "2024-01-01T12:00:00Z"
}
```
6. Click `Send`  
7. You should see a response: `Login event sent to Kafka`

---

8. Open your browser and go to: [http://localhost:9000](http://localhost:9000)  
You’ll see **Kafdrop** — the Kafka web UI.  
Scroll to the bottom — you should see the topics:
- `user-logins`
- `enriched-user-logins`
If you don't see this topics - reload page.
Click on a topic → Click `View Messages` → Choose `All messages` to inspect them.

---

## 🧩 Sharding Logic

- Users with **odd IDs** are stored in `users_shard1`  
- Users with **even IDs** are stored in `users_shard2`  

---

## 🛑 Stopping

1. Stop the application from your IDE  
2. Stop the containers:
```bash
docker-compose down -v
```

---

## 📁 Project Structure

- `src/main/kotlin/` — application source code  
- `docker-compose.yml` — Docker Compose configuration  
- `shard1/init.sql`, `shard2/init.sql` — database initialization scripts  
