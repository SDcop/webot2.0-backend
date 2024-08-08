package org.qqbot.webot.config;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

public class HttpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        //http的header中获得token
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        String token = request.getHeader(JWTConfig.TOKEN);
        //token不存在
        if (token == null || token.isEmpty()){
            response.setStatus(401);
            return false;
        }
        //先把token存入 方便更新
        response.addHeader(JWTConfig.TOKEN,token);
        response.setHeader("Authorization",JWTConfig.TOKEN);
        //验证token
        String sub = JWTConfig.validateToken(token);
        if (sub == null || sub.isEmpty()){
            System.out.print("token已过期");
            //返回403
            response.setStatus(403);
            //更新token有效时间 (如果需要更新其实就是产生一个新的token)
            if (JWTConfig.isNeedUpdate(token)){
                String newToken = JWTConfig.createToken(sub);
                response.setHeader(JWTConfig.TOKEN,newToken);
            }
        }
        return true;
    }
}