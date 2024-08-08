package org.qqbot.webot.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * @author shq
 * @date 2022/9/6
 * @annotation JWT生成工具类
 * JWT token的格式：header.payload.signature
 *  * header的格式（算法、token的类型）,默认：{"alg": "HS512","typ": "JWT"}
 *  * payload的格式 设置：（用户信息、创建时间、生成时间）
 *  * signature的生成算法：
 *  * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */

@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {
    private static final Logger log = LoggerFactory.getLogger(JWTConfig.class);
    //token 头部
    @Value("${jwt.header}")
    public static String header;

    //token 前缀
    public static String tokenPrefix = "";

    //签名秘钥
    @Value("${jwt.secret}")
    public static  String secret;

    //有效期
    @Value("${jwt.expireTime}")
    public static  long expireTime;

    //存入客户端token的key名
    public static final String TOKEN = "Authorization";

    public void setHeader(String header){
        JWTConfig.header = header;
    }

    public static void setTokenPrefix(String tokenPrefix) {
        JWTConfig.tokenPrefix = tokenPrefix;
    }

    public static void setSecret(String secret) {
        JWTConfig.secret = secret;
    }

    public static void setExpireTime(long expireTime) {
        JWTConfig.expireTime = expireTime;
    }

    /**
     * 创建token
     * @param sub
     */
    public static String createToken(String sub){
        return tokenPrefix + JWT.create()
                .withSubject(sub)
                .withExpiresAt(new Date(System.currentTimeMillis()+expireTime))
                .sign(Algorithm.HMAC512(secret));
    }

    /**
     * 验证token
     * @param token
     */
    public static String validateToken(String token) throws Exception{
        try {
            return JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getSubject();
        } catch (TokenExpiredException e){
            return "";
        } catch (Exception e){
            return e.toString();
        }
    }

    /**
     * 检查token是否需要更新
     * @param token
     */
    public static boolean isNeedUpdate(String token){
        //获取token过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getExpiresAt();
        } catch (TokenExpiredException e){
            return true;
        } catch (Exception e){
            log.error(String.valueOf(e));
        }
        //如果剩余过期时间少于过期时常的一般时 需要更新
        return ((expiresAt != null ? expiresAt.getTime() : 0) -System.currentTimeMillis()) < (expireTime>>1);
    }
}