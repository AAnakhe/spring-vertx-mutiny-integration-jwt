package com.codingplayground.springvertx.config;

import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.auth.jwt.JWTAuth;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.handler.JWTAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@PropertySources({
        @PropertySource("classpath:application-prod.properties"),
        @PropertySource("classpath:application-dev.properties")
})
@Configuration
public class JwtAuthConfiguration {

    @Value("${smallrye.jwt.new-token.signature-algorithm}")
    private String algorithm;

    @Bean
    @Autowired
    public JWTAuth jwtAuth(Vertx vertx, Router router) {
        JWTAuthOptions options = new JWTAuthOptions()
                // add public key
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm(algorithm)
                        .setBuffer("-----BEGIN PUBLIC KEY-----\n" +
                                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5j8B7f4jf7KzzVi9jYGn\n" +
                                "ZNKMcE0Xlj5jll0rgrpMFPD3Slczxva/j3ZRG7+fj+4uYam/g9x6ltNNGjB544Ns\n" +
                                "M7QYKixLGa5hKl0txSEW9f9o4hY7DrfI0wSntbtb/WhNtuUY+liaE2xU3iuslg+e\n" +
                                "PbKiITc0I1TbaZnvxacTD31+j+h070MnbnujeMcl2J0nE2L0RIUaJT0DjKvTwuxw\n" +
                                "5oel2NxUB9PVcxazh3t2u9eiGPPlOu8Xr4HPFExrI/ImWidIfGt/8NmybXPsKRta\n" +
                                "yTHUW59/h3/7tzEK/IE7xKGtk/k9YecADRvjtPP31gwk6ULKZK7qzG+CiLRNjQWV\n" +
                                "ZwIDAQAB\n" +
                                "-----END PUBLIC KEY-----"))
                // add private key
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm(algorithm)
                        .setBuffer("-----BEGIN PRIVATE KEY-----\n" +
                                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDmPwHt/iN/srPN\n" +
                                "WL2Ngadk0oxwTReWPmOWXSuCukwU8PdKVzPG9r+PdlEbv5+P7i5hqb+D3HqW000a\n" +
                                "MHnjg2wztBgqLEsZrmEqXS3FIRb1/2jiFjsOt8jTBKe1u1v9aE225Rj6WJoTbFTe\n" +
                                "K6yWD549sqIhNzQjVNtpme/FpxMPfX6P6HTvQydue6N4xyXYnScTYvREhRolPQOM\n" +
                                "q9PC7HDmh6XY3FQH09VzFrOHe3a716IY8+U67xevgc8UTGsj8iZaJ0h8a3/w2bJt\n" +
                                "c+wpG1rJMdRbn3+Hf/u3MQr8gTvEoa2T+T1h5wANG+O08/fWDCTpQspkrurMb4KI\n" +
                                "tE2NBZVnAgMBAAECggEADlAYZJ0Jw+Rt/PPpRKSsrT3RcSG5t6NFOZGtJMFD/YFF\n" +
                                "Ph/Rniq0mcx3Gkx9NMtPos6UkzPfgwS7pEcjLJt83HHzlpuOR5hpfeKw97Io5T3t\n" +
                                "XTCYlz+hgkylyExPWZ9kRVxUgyuC2HZBiFYasnFiGt/B6GKwIQojYZsqJpVOQAYk\n" +
                                "52Ovmin3Fw7n3ukiz1Q04TfNJOgke8Vw+IaRqmDpjoY1cViqplh/+a9zYoIYx8cY\n" +
                                "11OKgSi3RaIPdc1uJMFkLOrhXGcWjInAuARTVoM8/difbttAfB+dlTpW9SYEzvRN\n" +
                                "q4UM6o5MdgBvQmgC0d3Y1NxixPpdI99kURKmZzV7kQKBgQDry5BGcuLMU35UwnYJ\n" +
                                "Dc91n7l9oV8q9blHFGSo7JHbC/1VVjiozC+YRIvHSfNLQ3rKkW00kGVrDZSDdoAb\n" +
                                "rZ5iYKpXIZRPDr6ci5rulhssDM7B5jY7ScR3L+avuFMrHQjxW9BAO5AGqD5YrAiT\n" +
                                "AjoBBq/YAH5K8JLs79eYy1HLmwKBgQD5+bgijmAGlmcRRMTgcGEYyvaa6bJaA8jP\n" +
                                "kxNgkRITL+NrjvLYKTiD7NnHi7v+G//NszytmyD1wOKLV52QDCk6l6WEsOZWe8mR\n" +
                                "r8NqOv3zmbmU8Sh1q7Ea6JFZ6oMmVdaHMhUa1XRReSshimkeFIBoVV1OjRa6XIT6\n" +
                                "G6ELGq/4JQKBgG4XilJKO7hY12gtrzzE9PMfU1Tj7nI5bntQezsf0W3rqCma6zNs\n" +
                                "umAmyPiQf4bJh+BijBF5xdB487l5r5C8vHGEXqP16rAK21ZuHDV/Ykpr5HFF0lZW\n" +
                                "N+PKOrQapMJBeTZvvsjuxm1FSm7OS+aPqJNoEtwQRucocgEsboZJj/Q3AoGBANfC\n" +
                                "8mtpRnsJIKcoG2K5vSVAMoDdy+KkN5TrFqOYM+i5wesZtIRdd/lu2z7AXqBjnHT5\n" +
                                "CxuxtU5JkYDCPC6UQJXhRK12XyowqHgGJH6xsnarriJf0RNYAYL/UE4y+8Z/+q4w\n" +
                                "zUSfD0ovcsn9hOPAwGDNpb7iSsbKisCyTlueSlq9AoGBANSMUmE5/oDQA1fkz3k9\n" +
                                "fCnrkjodFIIQO+yE1JRgsuftct9ldk3tDRtvu//WRiSB3ULb6YMxXlB9Xf3pQGtV\n" +
                                "rdpkTdllqSIhUXZ6PXVTUMxp7uYAq7uSj/xDMbD3McPALRD6Sa/kYyMFkuhwP5Q6\n" +
                                "0KejcXiIgkt+3zw/ym0kBhjf\n" +
                                "-----END PRIVATE KEY-----"));

        JWTAuth jwt = JWTAuth.create(vertx, options);


        router.route("/api/*").handler(JWTAuthHandler.create(jwt));
        return jwt;
    }

}
