package com.miguelbarrios.daytrader.accountservice.utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

public class JWTUtil {

    public static boolean validJWT(String token){
        return false;
    }

    public static String getUsernameFromHeader(HttpServletRequest request){
        DecodedJWT decodedJWT = getDecodedJWT(request);
        Map<String, Claim> claims = decodedJWT.getClaims();
        return claims.get("sub").toString();
    }
    public static DecodedJWT getDecodedJWT(HttpServletRequest request) {
        String token = null;
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            if (header.startsWith("Bearer ")) {
                token = header.substring(7);
                break;
            }
        }

        return JWT.decode(token);
    }
}
