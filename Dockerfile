# Start with a base image that includes Go
FROM golang:latest AS builder

# Use LABEL instead of MAINTAINER
LABEL maintainer="applicant@lightspeedhq.com"

# Set environment variable with ARG instead of ENV
ARG NPM_TOKEN=30961fa8-cd15-49e1-96b9-9c8b40caa0e2

WORKDIR /app

COPY . .

# Build for amd64 architecture
RUN CGO_ENABLED=0 GOARCH=amd64 GOOS=linux go build -o dx-test.amd64

# Build for arm64 architecture
RUN CGO_ENABLED=0 GOARCH=arm64 GOOS=linux go build -o dx-test.arm64

# Use a smaller base image for the final image
FROM alpine:latest

# Copy binary files from the builder stage
COPY --from=builder /app/dx-test.* /bin/

# Expose the desired port
EXPOSE 10000

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Set the entry point
ENTRYPOINT ["/entrypoint.sh"]
