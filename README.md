# Video-Streaming-Backend-SpringBoot

A Java Spring Boot backend project for efficient video streaming with rate limiting and range-based streaming support.

## Features

- **Video Upload**: Upload videos with metadata (title, description, content type).
- **Range-based Streaming**: Stream video files in chunks, allowing clients to request only specific byte ranges, which improves performance and enables seamless seeking.
- **Rate Limiting**: Protects the streaming endpoint from abuse using resilience4j's `@RateLimiter` annotation, limiting the number of requests per user.
- **Video Download**: Download complete video files directly.
- **Video Metadata Management**: Store and retrieve metadata for each video.

## Implementation Details

### Range-Based Video Streaming

The `/video/stream/range/{id}` endpoint streams video data to the client using HTTP range requests:

- Supports the `Range` header to deliver partial video content.
- Efficiently sends only the requested byte range, enabling video seeking.
- Returns the appropriate content type and partial content status.

**Relevant Code:**
- [`VideoService.java`](https://github.com/DHRUVILPATEL2303/Spring_Stream_Video/blob/master/src/main/java/com/example/springstreambackend/services/VideoService.java)
- [`StreamController.java`](https://github.com/DHRUVILPATEL2303/Spring_Stream_Video/blob/master/src/main/java/com/example/springstreambackend/controllers/StreamController.java)

### Rate Limiting

The streaming endpoint is rate-limited to prevent excessive requests from a single client:

- Uses resilience4j's `@RateLimiter` on the `/video/stream/range/{id}` endpoint.
- Returns HTTP 429 ("Too Many Requests") with a `Retry-After` header if the limit is exceeded.
- Demonstrates both annotation-based and potential filter-based rate limiting approaches (see `RateLimitingFilter.java`).

**Relevant Code:**
- [`StreamController.java`](https://github.com/DHRUVILPATEL2303/Spring_Stream_Video/blob/master/src/main/java/com/example/springstreambackend/controllers/StreamController.java)
- [`RateLimitingFilter.java`](https://github.com/DHRUVILPATEL2303/Spring_Stream_Video/blob/master/src/main/java/com/example/springstreambackend/config/RateLimitingFilter.java)

### Video Management

- Videos are uploaded as files and stored on the server with associated metadata in a database (using JPA).
- All videos and metadata can be listed and queried via REST endpoints.

## How to Run

1. **Clone the repository**

    ```sh
    git clone https://github.com/DHRUVILPATEL2303/Spring_Stream_Video.git
    cd Spring_Stream_Video
    ```

2. **Build the project**

    ```sh
    ./gradlew build
    ```

3. **Run the application**

    ```sh
    ./gradlew bootRun
    ```

4. **API Endpoints**

    - `POST /video/upload`: Upload a video (multipart form).
    - `GET /video/stream/range/{id}`: Stream video with range support (rate-limited).
    - `GET /video/download/{id}`: Download a video.
    - `GET /video/all`: List all videos.

## Dependencies

- Spring Boot
- Spring Web
- Spring Data JPA
- Resilience4j (for rate limiting)
- Lombok

## Notes

- This project demonstrates backend logic and does not include a frontend player.
- Ensure that the server has access to a database for storing video metadata and a file system directory for video storage.

## License

This project is licensed under the MIT License.  
See the [LICENSE](./LICENSE) file for details.

---

> **Note:** This README is based on the current implementation. See the [project source](https://github.com/DHRUVILPATEL2303/Spring_Stream_Video) for more details.