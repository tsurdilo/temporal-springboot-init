package com.sample.demo.temporal.config;


import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.temporal.authorization.AuthorizationGrpcMetadataProvider;
import io.temporal.serviceclient.SimpleSslContextBuilder;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.spring.boot.TemporalOptionsCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLException;

@Configuration
public class TemporalOptionsConfig {

    @Value("${tmprl.cloud.key}")
    private String apiKey;

    @Value("${tmprl.cloud.target}")
    private String target;

    @Value("${tmprl.cloud.namespace}")
    private String namespace;

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
                } catch (SSLException e) {
                    return null;
                }
                return optionsBuilder;
            }
        };
    }
}
