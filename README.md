# Workout management service.

Hercules is a personal pet-project, which is a REST API for a workout management service.

## Features
 - [x] Exercise management
 - [ ] Workout management
 - [ ] Workout reminder
 - [ ] ...

## Usage

You can run the service on a local machine using Docker. See [Dockerfile](Docker/Dockerfile) for details.

### Docker build
```bash
docker build -t hercules:1.0 --platform linux/amd64 -f Docker/Dockerfile .
```

### Configure environment variables
See [example](.env.example).

### Docker run
```bash
docker run --env-file .env -p 8080:8080 hercules:1.0
```

### Swagger
```bash
docker run --env-file .env -p 9080:9080 hercules:1.0
```

### Docker login
```bash
echo YOUR_GITHUB_PAT | docker login ghcr.io -u YOUR_GITHUB_USERNAME --password-stdin
```

### Docker push
```bash
docker tag hercules:1.0 ghcr.io/bellyachx/hercules:1.0
docker push ghcr.io/bellyachx/hercules:1.0
```

http://localhost:9080/actuator/swagger-ui/index.html

## License

All Rights Reserved