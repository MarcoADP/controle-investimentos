package com.github.marcoadp.controle_investimentos.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "brapi")
@Getter
@Setter
public class BrapiProperties {

    private String url;

    private String token;

}
