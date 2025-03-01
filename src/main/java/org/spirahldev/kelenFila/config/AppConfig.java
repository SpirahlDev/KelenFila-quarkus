package org.spirahldev.kelenFila.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppConfig {

    @ConfigProperty(name="mp.jwt.verify.issuer")
    private String issuer;

    public String getIssuer(){
        return this.issuer;
    }
}
