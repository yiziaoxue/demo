package com.example.demo.annotation;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.annotation.exception.RequestLimitException;
import com.example.demo.util.HttpRequestUtil;

@Aspect
@Component
public class RequestLimitContract {
  private static final Logger logger = LoggerFactory.getLogger("RequestLimitLogger");
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
  public void requestLimit(final JoinPoint joinPoint,RequestLimit limit) throws RequestLimitException {

    try {
      Object[] args = joinPoint.getArgs();
      HttpServletRequest request = null;
      for (int i = 0; i < args.length; i++) {
        if (args[i] instanceof HttpServletRequest) {
          request = (HttpServletRequest) args[i];
          break;
        }
      }
      if (request == null) {
        throw new RequestLimitException("方法中缺失HttpServletRequest参数");
      }
      String ip = HttpRequestUtil.getIpAddress(request);
      String url = request.getRequestURL().toString();
      String key = "req_limit_".concat(url).concat(ip);
      long count = redisTemplate.opsForValue().increment(key, 1);
      if (count == 1) {
        redisTemplate.expire(key, limit.time(), TimeUnit.MILLISECONDS);
      }
      if (count > limit.count()) {
        System.out.println("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]");
        throw new RequestLimitException();
      }
      System.out.println("用户IP[" + ip + "]第[" + count + "]次访问地址[" + url + "]");
    } catch (RequestLimitException e) {
      throw e;
    } catch (Exception e) {
      logger.error("发生异常: ", e);
    }
  }
}