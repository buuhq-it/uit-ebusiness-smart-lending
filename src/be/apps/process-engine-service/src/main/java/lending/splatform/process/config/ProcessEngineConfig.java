package lending.splatform.process.config;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class ProcessEngineConfig {
    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(dataSource);
        config.setTransactionManager(transactionManager);
        config.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        config.setAsyncExecutorActivate(false);
        log.info("[FlowableConfig] Initialized ProcessEngineConfiguration {}", config.toString());
        return config;
    }

    @Bean
    public ProcessEngine processEngine(ProcessEngineConfiguration config) {
        var rt = config.buildProcessEngine();
        log.info("[FlowableConfig] Building ProcessEngine...{}", rt.toString());
        return rt;
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        var rt = processEngine.getRepositoryService();
        log.info("[FlowableConfig] Creating RepositoryService...for process engine {}", rt.toString());
        return rt;
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        var rt =  processEngine.getRuntimeService();
        log.info("[FlowableConfig] Creating RuntimeService...for process engine {}", rt.toString());
        return rt;
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        var rt = processEngine.getTaskService();

        log.info("[FlowableConfig] Creating TaskService...for process engine {}", rt.toString());
        return rt;
    }
}
