package com.yb.send.bootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;

@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
@EnableFeignClients("com.yb.send.feignSave")
@ComponentScan(basePackages={"com.yb.send"},excludeFilters=@Filter(type=FilterType.REGEX,
pattern="com\\.yb\\.send\\.feignSave.*"))
public class SendBootMain {

	public static void main(String[] args) {
		System.setProperty("server.connection-timeout", "3000");
		
	   SpringApplication.run(SendBootMain.class, args);
	}
}
