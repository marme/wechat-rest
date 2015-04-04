package com.daogen.socialmedia;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

public class SMConfiguration extends Configuration {
	
    @NotEmpty
    @JsonProperty
    private String template;

    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";
    
    @NotEmpty
    @JsonProperty
    private String token;
    
    @NotEmpty
    @JsonProperty
    private String appid;
    
    @NotEmpty
    @JsonProperty
    private String appsecret;

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }
    
    public String getToken(){
    	return token;
    }
    
    public String getAppid(){
    	return appid;
    }
    
    public String getAppsecret(){
    	return appsecret;
    }
    
    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }
}
