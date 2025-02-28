# Temporal Spring Boot Initializer

Initializer project for Temporal Spring Boot applications

# Build

       mvn clean install spring-boot:run

# IntelliJ 

1. Create new Project
2. Pick Spring Initializr
3. Change endpoint to http://localhost:8080
4. Keep defaults if you want on first page
5. Select Next
6. Select Temporal and other depends on second page
7. Generate new project (demo)


# New Project

        mvn clean install spring-boot:run

1. Start new workflow exec 

        curl -X GET "http://localhost:8081/hello/path1"

2. View Workflow exec - https://cloud.temporal.io/namespaces/tihomirapikeys.a2dd6/workflows
3. View raw metrics: http://localhost:8081/actuator/prometheus
4. View Prometheus Targets - http://localhost:9090/targets?search=
5. View Grafana - http://localhost:8085/
6. View Jaeger Traces - http://localhost:16686/
7. View Worker Actuator endpoint - http://localhost:8081/actuator/temporalworkerinfo