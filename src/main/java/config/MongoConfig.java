package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;

@Configuration
@EnableMongoRepositories(basePackages = "com.hyunkyoic.yisunsin.repository")
public class MongoConfig extends AbstractMongoConfiguration {

	// ---------------------------------------------------- mongodb config

    @Override
    protected String getDatabaseName() {
        return "yisunsin";
    }

    @Override
    @Bean
    public MongoClient mongo() throws Exception {
        MongoClient client = new MongoClient("localhost", mongoOptions());
        client.setWriteConcern(WriteConcern.SAFE);
        return client;
    }
    
    /**
     * MongoDB 연결을 위한 옵션을 설정합니다.
     * 특히 WriteConcern 은 성능 및 Validation에 큰 영항을 미치므로 꼭 테스트 후 변경하시기 바랍니다.
     */
    @Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions.builder()
                                 .connectionsPerHost(16)
                                 .autoConnectRetry(true)
                                 .threadsAllowedToBlockForConnectionMultiplier(4)
                                 .connectTimeout(1000)
                                 .maxWaitTime(1500)
                                 .socketKeepAlive(true)
                                 .writeConcern(WriteConcern.SAFE)
                                 .build();
    }

    /*
    @Override
    protected String getMappingBasePackage() {
        return "com.hyunkyoic.yisunsin.domain";
    }
    */
    
    // ---------------------------------------------------- MongoTemplate
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }

}
