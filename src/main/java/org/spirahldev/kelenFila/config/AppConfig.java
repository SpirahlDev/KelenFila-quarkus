package org.spirahldev.kelenFila.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppConfig {

    @ConfigProperty(name="mp.jwt.verify.issuer")
    private String issuer;

    @ConfigProperty(name="kelen-fila-app.token.lifespan")
    private long tokenLifeSpan;

    @ConfigProperty(name = "mp.jwt.signer.key-location")
    String keyLocation;

    public String getIssuer(){
        return this.issuer;
    }

    public long getTokenLife(){
        return this.tokenLifeSpan;
    }

   public String getPrivateKey(){
    return this.keyLocation;
   }
}
