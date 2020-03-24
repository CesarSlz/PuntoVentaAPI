package com.fresno.puntoventaapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.fresno.puntoventaapi.dao")
public class WebConfig {

}
