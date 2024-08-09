package org.qqbot.webot.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;

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
    private static final Log log = LogFactory.get();

    //apiKey
    @Value("${jwt.apiKey}")
    public String apiKey;

    //签名秘钥
    @Value("${jwt.secret}")
    public String secret;

    //有效期
    @Value("${jwt.expireTime}")
    public long expireTime;

    //存入客户端token的key名
    public static final String TOKEN = "Bearer";

    /**
     * 创建token
     */
    public String createToken(){
        HashMap<String,Object> header = new HashMap<>();
        Date expireDate = new Date(System.currentTimeMillis() + expireTime * 1000);
        Date date = new Date();
        header.put("alg", "HS256");
        header.put("typ", "SIGN");
        return JWT.create()
                //header
                .withHeader(header)
                //payload
                .withClaim("api_key",apiKey)
                .withClaim("exp",expireTime)
                .withClaim("timestamp",date.getTime())
                //过期日期
                .withExpiresAt(expireDate)
                //签发时间
                .withIssuedAt(new Date())
                //秘钥
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 验证token
     */
    public String validateToken(String token) throws Exception{
        try {
            return JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (TokenExpiredException e){
            return "";
        } catch (Exception e){
            return e.toString();
        }
    }

    /**
     * 检查token是否需要更新
     */
    public boolean isNeedUpdate(String token){
        //获取token过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC256(secret))
                    .build().verify(token).getExpiresAt();
        } catch (TokenExpiredException e){
            return true;
        } catch (Exception e){
            log.error(String.valueOf(e));
        }
        //如果剩余过期时间少于过期时常的一般时 需要更新
        return ((expiresAt != null ? expiresAt.getTime() : 0) -System.currentTimeMillis()) < (expireTime>>1);
    }
}