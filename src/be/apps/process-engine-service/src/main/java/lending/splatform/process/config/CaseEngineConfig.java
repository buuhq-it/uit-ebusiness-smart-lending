package lending.splatform.process.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import org.flowable.cmmn.spring.SpringCmmnEngineConfiguration;

import javax.sql.DataSource;

@Configuration
public class CaseEngineConfig {
    @Bean
    public SpringCmmnEngineConfiguration cmmnEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        SpringCmmnEngineConfiguration config = new SpringCmmnEngineConfiguration();
        config.setDataSource(dataSource);
        config.setTransactionManager(transactionManager);
        config.setDatabaseSchemaUpdate("false");
        log.info("[FlowableConfig] Initialized CmmnEngineConfiguration");
        return config;
    }

    @Bean
    public CmmnEngine cmmnEngine(SpringCmmnEngineConfiguration config) throws Exception {
        CmmnEngineFactoryBean factoryBean = new CmmnEngineFactoryBean();
        factoryBean.setCmmnEngineConfiguration(config);
        return factoryBean.getObject();
    }

    @Bean
    public CmmnRuntimeService cmmnRuntimeService(CmmnEngine cmmnEngine) {
        return cmmnEngine.getCmmnRuntimeService();
    }

    @Bean
    public CmmnTaskService cmmnTaskService(CmmnEngine cmmnEngine) {
        return cmmnEngine.getCmmnTaskService();
    }
}
