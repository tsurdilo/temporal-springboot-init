# Temporal Spring Boot Demo Application

# Build

        mvn clean install spring-boot:run

# How to use
1. Start new workflow exec 

        curl -X GET "http://localhost:8081/hello/path1"

2. View Workflow exec - https://cloud.temporal.io/namespaces/tihomirapikeys.a2dd6/workflows
3. View raw metrics: http://localhost:8081/actuator/prometheus
4. View Prometheus Targets - http://localhost:9090/targets?search=
5. View Grafana - http://localhost:8085/
6. View Jaeger Traces - http://localhost:16686/
7. View Worker Actuator endpoint - http://localhost:8081/actuator/temporalworkerinfo