package com.yb.retreiveandsave.bootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"com.yb.retreiveandsave"},excludeFilters=@Filter(type=FilterType.REGEX,
pattern="com\\.yb\\.retreiveandsave\\.feign.*"))
@EnableMongoRepositories("com.yb.retreiveandsave.mongorepository")
@EnableDiscoveryClient
@EnableCaching
public class RetreivalBootMain {

	public static void main(String[] args) {
       SpringApplication.run(RetreivalBootMain.class, args);
	}

}
