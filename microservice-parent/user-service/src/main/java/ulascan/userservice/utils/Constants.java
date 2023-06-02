package ulascan.userservice.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constants {
    @Getter
    private static String secretKey;

    @Getter
    private static String jwtExpiration;


    @Getter
    private static int refreshExpiration;


    @Value("${SECRET_KEY}")
    public void setSecretKey(String secretKey){Constants.secretKey = secretKey; }

    @Value("${EXPIRATION}")
    public void setJwtExpiration(String expiration){Constants.jwtExpiration = expiration;}

    @Value("${REFRESH_TOKEN_EXPIRATION}")
    public void setMaxUserCount(int maxUserCount){Constants.refreshExpiration = maxUserCount;}


}
