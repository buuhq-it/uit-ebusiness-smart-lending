package lending.splatform.process.config;

import lombok.extern.slf4j.Slf4j;


import org.flowable.dmn.api.DmnDecisionService;
import org.flowable.dmn.engine.DmnEngine;
import org.flowable.dmn.spring.DmnEngineFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.flowable.dmn.spring.SpringDmnEngineConfiguration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DecisionEngineConfig {
    @Bean
    public SpringDmnEngineConfiguration dmnEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        SpringDmnEngineConfiguration config = new SpringDmnEngineConfiguration();
        config.setDataSource(dataSource);
        config.setTransactionManager(transactionManager);
        config.setDatabaseSchemaUpdate("false");
        log.info("[FlowableConfig] Initialized DmnEngineConfiguration");
        return config;
    }

    @Bean
    public DmnEngine dmnEngine(SpringDmnEngineConfiguration config) throws Exception {
        DmnEngineFactoryBean factoryBean = new DmnEngineFactoryBean();
        factoryBean.setDmnEngineConfiguration(config);
        return factoryBean.getObject();
    }

    @Bean
    public DmnDecisionService dmnRuleService(DmnEngine dmnEngine) {

        return dmnEngine.getDmnDecisionService();
    }



}
