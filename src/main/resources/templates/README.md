# Temporal Spring Boot Demo Application

# Build

        mvn clean install spring-boot:run

# How to use
1. Start new workflow executions - http://localhost:8081
2. Prometheus Metrics: http://localhost:8081/actuator/prometheus
3. Prometheus Targets - http://localhost:9090/targets?search=
4. Grafana Dashboard - http://localhost:8085/
5. Jaeger Traces - http://localhost:16686/
6. Worker Actuator endpoint - http://localhost:8081/actuator/temporalworkerinfo
7. Actuator Scheduled Tasks (Backlog count custom metric) - http://localhost:8081/actuator/scheduledtasks

# Graceful shutdown

      curl 'http://localhost:8081/actuator/shutdown' -i -X POST
