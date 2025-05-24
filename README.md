# 🚀 Java HTTP Server From Scratch

A high-performance, multithreaded HTTP server built entirely from scratch using **Java (no frameworks)** — to deeply understand how real servers like **Apache, Tomcat, or Spring Boot** work under the hood.

> ⚙️ **Status**: Work in Progress  
> 🧠 **Goal**: Rebuild a complete HTTP server from first principles, one feature at a time

---

## ✅ Features Completed

| Phase | Description | Status |
|-------|-------------|--------|
| 1     | Basic server socket, return Hello World | ✅ Done |
| 2     | Parse request line (method, path, version) | ✅ Done |
| 3     | Add multithreading to handle concurrent requests | ✅ Done |
| 4     | Parse headers and query parameters | ✅ Done |
| 5     | Support JSON responses with dynamic Content-Type | ✅ Done |
| 6     | Handle POST requests & read request body | ✅ Done |

---

## 🏗️ Upcoming Phases

| Phase | Description | Status |
|-------|-------------|--------|
| 7     | Status codes & error handling (400, 404, 405, 500) | 🚧 Next |
| 8     | Static file server (HTML, CSS, JS) | ⏳ Planned |
| 9     | Basic routing system (GET/POST path mappings) | ⏳ Planned |
| 10    | Simple templating engine (`{{variable}}` syntax) | ⏳ Planned |
| 11    | JSON parsing into Map or Java object | ⏳ Planned |
| 12    | Cookie handling & basic session management | ⏳ Planned |
| 13    | Middleware support (logging, timing, etc.) | ⏳ Planned |
| 14    | Graceful shutdown with thread cleanup | ⏳ Planned |
| 15    | Add HTTPS (TLS) support using `SSLSocket` | ⏳ Planned |

---

## 📚 What You'll Learn

- Low-level socket programming with Java
- Understanding of the HTTP protocol (1.1)
- Request parsing, headers, query strings, and body decoding
- Serving static + dynamic content
- Multithreading, concurrency, and performance tuning
- How modern frameworks (like Spring Boot) work internally

---

## 🔧 Tech Stack

- Java 17+
- Raw sockets (`ServerSocket`, `InputStream`)
- No external libraries (yet)
- Manual routing, JSON handling, file serving

---

## 👷 Contributing

This is a personal learning project. Contributions, ideas, and PRs are welcome — just keep it framework-free and educational!

---

## 📝 License

MIT

