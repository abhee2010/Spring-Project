package com.abhee.login.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("basic")
public class BasicConfiguration {

	private boolean value;
	private String message;
	private int number;

}
