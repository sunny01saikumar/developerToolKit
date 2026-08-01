package com.devtoolkit.pro.data.repository

import com.devtoolkit.pro.data.local.LocalStorage
import com.devtoolkit.pro.domain.model.*
import com.devtoolkit.pro.domain.repository.DevToolkitRepository
import kotlinx.coroutines.flow.Flow

class DevToolkitRepositoryImpl(
    private val localStorage: LocalStorage
) : DevToolkitRepository {

    override val themeMode: Flow<String> = localStorage.themeMode
    override suspend fun setThemeMode(mode: String) = localStorage.setThemeMode(mode)

    override val dynamicColors: Flow<Boolean> = localStorage.dynamicColors
    override suspend fun setDynamicColors(enabled: Boolean) = localStorage.setDynamicColors(enabled)

    override val bookmarkedTools: Flow<Set<String>> = localStorage.bookmarkedTools
    override suspend fun toggleBookmark(toolId: String) = localStorage.toggleBookmark(toolId)

    override val historyItems: Flow<List<HistoryItem>> = localStorage.historyItems
    override suspend fun addHistory(toolId: String) = localStorage.addHistory(toolId)
    override suspend fun clearHistory() = localStorage.clearHistory()

    override val notes: Flow<List<Note>> = localStorage.notes
    override suspend fun addOrUpdateNote(note: Note) = localStorage.addOrUpdateNote(note)
    override suspend fun deleteNote(noteId: String) = localStorage.deleteNote(noteId)

    override fun getTools(): List<Tool> {
        return listOf(
            Tool("json_formatter", "JSON Formatter", "Validate, format, and minify JSON data", "JSON Tools", "json_formatter", "brackets", listOf("#8A2387", "#E94057")),
            Tool("sql_formatter", "SQL Formatter", "Beautify and minify SQL queries", "SQL Tools", "sql_formatter", "database", listOf("#F27121", "#E94057")),
            Tool("jwt_decoder", "JWT Decoder", "Decode JSON Web Tokens and inspect payload", "JWT Tools", "jwt_decoder", "shield", listOf("#11998e", "#38ef7d")),
            Tool("base64_tool", "Base64 Encoder/Decoder", "Encode and decode Base64 strings", "Encoding", "base64", "code", listOf("#3a7bd5", "#3a6073")),
            Tool("url_tool", "URL Encoder/Decoder", "Safe URL component encoding and decoding", "Encoding", "url_encode", "link", listOf("#f857a6", "#ff5858")),
            Tool("hash_gen", "Hash Generator", "Generate MD5, SHA1, SHA256, and SHA512 hashes", "Generators", "hash_gen", "key", listOf("#ff9966", "#ff5e62")),
            Tool("uuid_gen", "UUID Generator", "Generate single or batch Version 4 UUIDs", "Generators", "uuid_gen", "layers", listOf("#00c6ff", "#0072ff")),
            Tool("password_gen", "Password Generator", "Generate secure random passwords", "Generators", "password_gen", "lock", listOf("#11998e", "#38ef7d")),
            Tool("regex_tester", "Regex Tester", "Test regular expressions with matching highlights", "Regex", "regex_tester", "search", listOf("#F27121", "#E94057")),
            Tool("color_tools", "Color Tools", "HEX, RGB converters, Picker, and Gradient builders", "Colors", "color_tools", "palette", listOf("#8A2387", "#E94057")),
            Tool("http_status", "HTTP Status Codes", "Explore HTTP response status codes and descriptions", "HTTP", "http_status", "help_circle", listOf("#3a7bd5", "#3a6073")),
            Tool("http_headers", "HTTP Headers", "Explore common HTTP headers and their usage", "HTTP", "http_headers", "info", listOf("#00c6ff", "#0072ff")),
            Tool("curl_gen", "Curl Generator", "Construct curl command requests visually", "HTTP", "curl_gen", "terminal", listOf("#ff9966", "#ff5e62")),
            Tool("linux_commands", "Linux Commands", "Categorized Linux cheat-sheet and examples", "Linux", "linux_commands", "terminal", listOf("#4568DC", "#B06AB8")),
            Tool("git_commands", "Git Commands", "Git version control reference and commands", "Git", "git_commands", "git_branch", listOf("#e96443", "#904e95")),
            Tool("docker_commands", "Docker Commands", "Containers, Images, and Compose cheat-sheet", "Docker", "docker_commands", "box", listOf("#00c6ff", "#0072ff")),
            Tool("unix_timestamp", "Unix Timestamp", "Convert date-times to epoch timestamps and vice-versa", "Networking", "unix_timestamp", "clock", listOf("#00b09b", "#96c93d")),
            Tool("dev_calc", "Developer Calculator", "Convert between Hex, Dec, Oct, and Bin bases", "Calculator", "dev_calc", "calculator", listOf("#f857a6", "#ff5858")),
            Tool("qr_tool", "QR Code Tool", "Generate custom QR Codes and scan offline using camera", "Networking", "qr_tool", "qr_code", listOf("#11998e", "#38ef7d")),
            Tool("markdown_preview", "Markdown Preview", "Write Markdown documentation and preview output", "Notes", "markdown_preview", "file_text", listOf("#3a7bd5", "#3a6073")),
            Tool("notes_tool", "Offline Notes", "Manage simple development notes locally", "Notes", "notes", "edit_3", listOf("#8A2387", "#E94057"))
        )
    }

    override fun getLinuxCommands(): List<CommandItem> {
        return listOf(
            CommandItem("ls -la", "List all files and folders in long format including hidden", "ls -la /var/log", "File"),
            CommandItem("cd <dir>", "Change directory", "cd /etc/nginx", "File"),
            CommandItem("pwd", "Print current working directory path", "pwd", "File"),
            CommandItem("cp -r <src> <dest>", "Copy files or directories recursively", "cp -r ./project /backup/project", "File"),
            CommandItem("mv <src> <dest>", "Move or rename files/directories", "mv old_name.txt new_name.txt", "File"),
            CommandItem("rm -rf <path>", "Forcefully remove files or directories recursively", "rm -rf ./tmp_cache", "File"),
            CommandItem("mkdir -p <dir>", "Create directory path including parents if missing", "mkdir -p /data/web/html", "File"),
            CommandItem("find <dir> -name <pattern>", "Find files matching pattern", "find /var/log -name \"*.log\"", "File"),
            CommandItem("chmod 755 <file>", "Set owner read-write-execute, others read-execute", "chmod 755 run.sh", "Permissions"),
            CommandItem("chown <user>:<group> <file>", "Change file owner and group", "chown www-data:www-data index.php", "Permissions"),
            CommandItem("ps aux", "Show all running processes with user details", "ps aux", "Process"),
            CommandItem("top", "Real-time process viewer and system resource stats", "top", "Process"),
            CommandItem("kill -9 <pid>", "Force terminate a process by ID", "kill -9 1234", "Process"),
            CommandItem("pkill -f <name>", "Terminate process by matching process name", "pkill -f node", "Process"),
            CommandItem("ping -c 4 <host>", "Send 4 ICMP ECHO requests to check connection", "ping -c 4 google.com", "Network"),
            CommandItem("curl -i <url>", "Fetch headers and content from URL", "curl -i https://httpbin.org/get", "Network"),
            CommandItem("wget <url>", "Download content from URL", "wget https://example.com/file.zip", "Network"),
            CommandItem("netstat -tuln", "List listening TCP/UDP ports", "netstat -tuln", "Network"),
            CommandItem("df -h", "Show disk space usage in human-readable format", "df -h", "Disk"),
            CommandItem("du -sh <dir>", "Show total size of a directory", "du -sh /var/log", "Disk"),
            CommandItem("free -h", "Show free and used physical memory and swap", "free -h", "Disk"),
            CommandItem("whoami", "Print current user login name", "whoami", "Users"),
            CommandItem("passwd", "Change current user password", "passwd", "Users")
        )
    }

    override fun getGitCommands(): List<CommandItem> {
        return listOf(
            CommandItem("git init", "Initialize a new local git repository", "git init", "Basic"),
            CommandItem("git clone <url>", "Clone an existing repository from URL", "git clone https://github.com/user/repo.git", "Basic"),
            CommandItem("git add .", "Stage all changes in directory for next commit", "git add .", "Basic"),
            CommandItem("git commit -m \"<msg>\"", "Commit staged snapshot with message", "git commit -m \"feat: add dark theme\"", "Basic"),
            CommandItem("git status", "Show status of working directory and staging area", "git status", "Basic"),
            CommandItem("git branch -a", "List all local and remote branches", "git branch -a", "Branch"),
            CommandItem("git checkout -b <name>", "Create and switch to new branch", "git checkout -b feature/auth", "Branch"),
            CommandItem("git switch <branch>", "Switch to branch (modern syntax)", "git switch main", "Branch"),
            CommandItem("git merge <branch>", "Merge specified branch into current branch", "git merge feature/auth", "Merge"),
            CommandItem("git rebase <branch>", "Rebase current branch onto base branch", "git rebase main", "Rebase"),
            CommandItem("git remote add <name> <url>", "Add new remote origin repo", "git remote add origin https://github.com/u/r.git", "Remote"),
            CommandItem("git fetch <remote>", "Download objects/refs from remote without merging", "git fetch origin", "Remote"),
            CommandItem("git pull <remote> <branch>", "Fetch and integrate remote changes with local branch", "git pull origin main", "Remote"),
            CommandItem("git push <remote> <branch>", "Upload local commits to remote branch", "git push origin main", "Remote"),
            CommandItem("git reset --hard <commit>", "Reset index and working tree (destructive)", "git reset --hard HEAD~1", "Reset"),
            CommandItem("git stash push -m \"<label>\"", "Stash current modified files with custom message", "git stash push -m \"wip-login\"", "Stash"),
            CommandItem("git stash pop", "Apply top stashed state and remove from stash list", "git stash pop", "Stash"),
            CommandItem("git tag -a v1.0.0 -m \"<msg>\"", "Create annotated release tag", "git tag -a v1.0.0 -m \"Release 1.0\"", "Tag")
        )
    }

    override fun getDockerCommands(): List<CommandItem> {
        return listOf(
            CommandItem("docker run -d -p <host>:<cont> --name <name> <image>", "Run a container in detached mode with port mapping", "docker run -d -p 8080:80 --name web-server nginx", "Container"),
            CommandItem("docker ps", "List running containers", "docker ps", "Container"),
            CommandItem("docker ps -a", "List all containers (running and stopped)", "docker ps -a", "Container"),
            CommandItem("docker stop <id>", "Stop a running container", "docker stop web-server", "Container"),
            CommandItem("docker rm <id>", "Remove a stopped container", "docker rm web-server", "Container"),
            CommandItem("docker logs -f <id>", "Follow container logs in real time", "docker logs -f web-server", "Container"),
            CommandItem("docker exec -it <id> <shell>", "Run interactive terminal inside container", "docker exec -it web-server bash", "Container"),
            CommandItem("docker build -t <name>:<tag> .", "Build container image from local Dockerfile", "docker build -t myapp:1.0 .", "Images"),
            CommandItem("docker images", "List all local docker images", "docker images", "Images"),
            CommandItem("docker rmi <id>", "Remove local docker image by ID", "docker rmi myapp:1.0", "Images"),
            CommandItem("docker volume ls", "List all volumes", "docker volume ls", "Volumes"),
            CommandItem("docker volume create <name>", "Create a named storage volume", "docker volume create app_data", "Volumes"),
            CommandItem("docker-compose up -d", "Create and start resources defined in compose in detached mode", "docker-compose up -d", "Compose"),
            CommandItem("docker-compose down", "Stop and remove containers, networks, and volumes", "docker-compose down", "Compose"),
            CommandItem("docker network ls", "List all networks", "docker network ls", "Networks")
        )
    }

    override fun getHttpStatusCodes(): List<HttpStatusItem> {
        return listOf(
            HttpStatusItem(100, "Continue", "Informational", "Initial response indicating server received headers", "HTTP/1.1 100 Continue"),
            HttpStatusItem(200, "OK", "Success", "Request succeeded. Details depend on HTTP method", "HTTP/1.1 200 OK"),
            HttpStatusItem(201, "Created", "Success", "Request fulfilled, resulting in new resource creation", "HTTP/1.1 201 Created"),
            HttpStatusItem(202, "Accepted", "Success", "Request accepted for processing, but not yet complete", "HTTP/1.1 202 Accepted"),
            HttpStatusItem(204, "No Content", "Success", "Request processed successfully, returning no payload", "HTTP/1.1 204 No Content"),
            HttpStatusItem(301, "Moved Permanently", "Redirection", "Requested URI changed permanently. New URI given in response", "HTTP/1.1 301 Moved Permanently\nLocation: /new-path"),
            HttpStatusItem(302, "Found", "Redirection", "URI target resides temporarily under different location", "HTTP/1.1 302 Found\nLocation: /temp-path"),
            HttpStatusItem(304, "Not Modified", "Redirection", "Resource has not been modified. Client can use cached copy", "HTTP/1.1 304 Not Modified"),
            HttpStatusItem(400, "Bad Request", "Client Error", "Server cannot process request due to client side syntax error", "HTTP/1.1 400 Bad Request"),
            HttpStatusItem(401, "Unauthorized", "Client Error", "Client must authenticate itself to receive response", "HTTP/1.1 401 Unauthorized\nWWW-Authenticate: Basic"),
            HttpStatusItem(403, "Forbidden", "Client Error", "Client does not have access rights to the content", "HTTP/1.1 403 Forbidden"),
            HttpStatusItem(404, "Not Found", "Client Error", "Server cannot find the requested resource path", "HTTP/1.1 404 Not Found"),
            HttpStatusItem(405, "Method Not Allowed", "Client Error", "Request method recognized but disabled for resource", "HTTP/1.1 405 Method Not Allowed\nAllow: GET, POST"),
            HttpStatusItem(409, "Conflict", "Client Error", "Request conflicts with current state of server resource", "HTTP/1.1 409 Conflict"),
            HttpStatusItem(429, "Too Many Requests", "Client Error", "User sent too many requests in given timeframe", "HTTP/1.1 429 Too Many Requests\nRetry-After: 3600"),
            HttpStatusItem(500, "Internal Server Error", "Server Error", "Server encountered unexpected situation preventing request execution", "HTTP/1.1 500 Internal Server Error"),
            HttpStatusItem(502, "Bad Gateway", "Server Error", "Server acting as gateway received invalid response from upstream", "HTTP/1.1 502 Bad Gateway"),
            HttpStatusItem(503, "Service Unavailable", "Server Error", "Server is not ready to handle request (overloaded or down for maintenance)", "HTTP/1.1 503 Service Unavailable"),
            HttpStatusItem(504, "Gateway Timeout", "Server Error", "Server acting as gateway did not get timely response from upstream", "HTTP/1.1 504 Gateway Timeout")
        )
    }

    override fun getHttpHeaders(): List<HttpHeaderItem> {
        return listOf(
            HttpHeaderItem("Accept", "Specifies acceptable media types for response", "Accept: text/html, application/json", "Request"),
            HttpHeaderItem("Authorization", "Contains credentials to authenticate user agent with server", "Authorization: Bearer eyJhbGciOi...", "Request"),
            HttpHeaderItem("Content-Type", "Indicates resource media type (request and response)", "Content-Type: application/json; charset=utf-8", "Request/Response"),
            HttpHeaderItem("User-Agent", "Identifies user agent software name, version, and OS", "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)", "Request"),
            HttpHeaderItem("Cookie", "Contains stored HTTP cookies previously sent by server", "Cookie: session_id=abc123xyz", "Request"),
            HttpHeaderItem("Host", "Specifies domain name of server and port number", "Host: api.example.com:443", "Request"),
            HttpHeaderItem("Origin", "Indicates where resource request initiated (CORS security)", "Origin: https://www.example.com", "Request"),
            HttpHeaderItem("Referer", "Address of previous page that linked to requested page", "Referer: https://google.com/", "Request"),
            HttpHeaderItem("Cache-Control", "Directives for caching mechanisms in requests and responses", "Cache-Control: no-cache, no-store, must-revalidate", "Request/Response"),
            HttpHeaderItem("Content-Length", "Decimal number of octets in resource payload", "Content-Length: 348", "Request/Response"),
            HttpHeaderItem("Set-Cookie", "Sends cookie from server to user agent storage", "Set-Cookie: user_id=9876; Max-Age=3600; Secure; HttpOnly", "Response"),
            HttpHeaderItem("Server", "Information about software serving the request", "Server: nginx/1.24.0", "Response"),
            HttpHeaderItem("Access-Control-Allow-Origin", "Indicates whether response can be shared with origin domain", "Access-Control-Allow-Origin: *", "Response"),
            HttpHeaderItem("Location", "Used in redirection response to point to new page URI", "Location: https://example.com/home", "Response"),
            HttpHeaderItem("ETag", "Identifier for specific version of resource at URL", "ETag: \"33a64df551425fcc55e4d42a148795d9f25f89d4\"", "Response")
        )
    }
}
