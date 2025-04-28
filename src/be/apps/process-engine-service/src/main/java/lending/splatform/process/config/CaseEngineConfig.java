package lending.splatform.process.config;

import lombok.extern.slf4j.Slf4j;
import org.flowable.cmmn.api.CmmnRuntimeService;
import org.flowable.cmmn.api.CmmnTaskService;
import org.flowable.cmmn.engine.CmmnEngine;
import org.flowable.cmmn.spring.CmmnEngineFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import org.flowable.cmmn.spring.SpringCmmnEngineConfiguration;

import javax.sql.DataSource;

@Slf4j
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
    public CmmnTaskService cmmnTaskService(CmmnEngine cmmnEngine) {
        return cmmnEngine.getCmmnTaskService();
    }

    @Bean
    public CmmnRuntimeService cmmnRuntimeService(CmmnEngine cmmnEngine) {

        //cmmnEngine.getCmmnRuntimeService();

        return cmmnEngine.getCmmnRuntimeService();
    }


}
