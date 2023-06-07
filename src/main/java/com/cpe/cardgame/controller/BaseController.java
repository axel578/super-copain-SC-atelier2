package com.cpe.cardgame.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Key;

public class BaseController {

    public static Key DataKey;
    //@RequestMapping(value = "/check-auth", method = RequestMethod.GET)
    public ResponseEntity<String> checkAuth(HttpServletRequest httprequest) {
        Cookie[] cookies = httprequest.getCookies();

        if (cookies != null) {
            String jwtToken = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }

            if (jwtToken != null) {
                try {
                    if (DataKey == null) {
                        BaseController.DataKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                    }
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(BaseController.DataKey)
                            .build()
                            .parseClaimsJws(jwtToken)
                            .getBody();

                    // Extract user ID from claims
                    String userId = claims.getSubject();
                    // ...

                    return ResponseEntity.ok().body(userId);
                } catch (Exception e) {
                    // Handle invalid/expired tokens
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        }

        // Handle unauthenticated requests
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public int GetByUser(HttpServletRequest httprequest)
    {
        var result = checkAuth(httprequest);
        if(!result.getStatusCode().equals(HttpStatus.OK))
        {
            return 0;
        }
        else
        {
            return Integer.parseInt(result.getBody());
        }
        /*var data = httprequest.getSession().getAttribute("USER");
        if(data == null)
        {
            return 0;
        }
        var id = (Integer)data;
        if(id == null)
        {
            return 0;
        }
        return id;*/
    }
}