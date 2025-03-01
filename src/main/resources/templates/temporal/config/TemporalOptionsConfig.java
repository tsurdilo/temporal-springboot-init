package com.sample.demo.temporal.config;

import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.opentelemetry.api.OpenTelemetry;
import io.temporal.authorization.AuthorizationGrpcMetadataProvider;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.SimpleSslContextBuilder;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.spring.boot.TemporalOptionsCustomizer;
import io.temporal.spring.boot.WorkerOptionsCustomizer;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLException;
import java.time.Duration;

@Configuration
public class TemporalOptionsConfig {

    @Value("${tmprl.cloud.key}")
    private String apiKey;

    @Value("${tmprl.cloud.target}")
    private String target;

    @Value("${tmprl.cloud.namespace}")
    private String namespace;

    @Autowired
    OpenTelemetry openTelemetry;

    @Bean
    public TemporalOptionsCustomizer<WorkflowClientOptions.Builder> customClientOptions() {
        return new TemporalOptionsCustomizer<WorkflowClientOptions.Builder>() {
            @Nonnull
            @Override
            public WorkflowClientOptions.Builder customize(
                    @Nonnull WorkflowClientOptions.Builder optionsBuilder) {
                return optionsBuilder;
            }
        };
    }

    @Bean
    public TemporalOptionsCustomizer<WorkerFactoryOptions.Builder> customWorkerFactoryOptions() {
        return new TemporalOptionsCustomizer<WorkerFactoryOptions.Builder>() {
            @Nonnull
            @Override
            public WorkerFactoryOptions.Builder customize(
                    @Nonnull WorkerFactoryOptions.Builder optionsBuilder) {
                return optionsBuilder;
            }
        };
    }

    @Bean
    public WorkerOptionsCustomizer customWorkerOptions() {
        return new WorkerOptionsCustomizer() {
            @Nonnull
            @Override
            public WorkerOptions.Builder customize(
                    @Nonnull WorkerOptions.Builder optionsBuilder,
                    @Nonnull String workerName,
                    @Nonnull String taskQueue) {
                optionsBuilder.setStickyTaskQueueDrainTimeout(Duration.ofSeconds(15));
                optionsBuilder.setDisableEagerExecution(true);
                optionsBuilder.setMaxConcurrentActivityExecutionSize(5);
                optionsBuilder.setMaxConcurrentActivityTaskPollers(5);
                optionsBuilder.setMaxTaskQueueActivitiesPerSecond(5);
                return optionsBuilder;
            }
        };
    }

    @Bean
    public TemporalOptionsCustomizer<WorkflowServiceStubsOptions.Builder>
    customServiceStubsOptions() {
        return new TemporalOptionsCustomizer<WorkflowServiceStubsOptions.Builder>() {
            @Nonnull
            @Override
            public WorkflowServiceStubsOptions.Builder customize(
                    @Nonnull WorkflowServiceStubsOptions.Builder optionsBuilder) {
                try {
                    Metadata.Key<String> TEMPORAL_NAMESPACE_HEADER_KEY =
                            Metadata.Key.of("temporal-namespace", Metadata.ASCII_STRING_MARSHALLER);
                    Metadata metadata = new Metadata();
                    metadata.put(TEMPORAL_NAMESPACE_HEADER_KEY, namespace);

                    optionsBuilder.setChannelInitializer(
                                    (channel) -> {
                                        channel.intercept(MetadataUtils.newAttachHeadersInterceptor(metadata));
                                    })
                            .addGrpcMetadataProvider(
                                    new AuthorizationGrpcMetadataProvider(() -> "Bearer " + apiKey))
                            .setTarget(target);
                    optionsBuilder.setSslContext(SimpleSslContextBuilder.noKeyOrCertChain().setUseInsecureTrustManager(false).build());


                    optionsBuilder.setRpcLongPollTimeout(Duration.ofSeconds(10));
                } catch (SSLException e) {
                    return null;
                }
                return optionsBuilder;
            }
        };
    }
}
