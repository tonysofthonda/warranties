package api.local.managebpcs;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import lombok.extern.java.Log;
import redis.embedded.RedisServer;

@Log
@Component
public class Redis {
	
	private RedisServer redisServer;
	

    @PostConstruct
    public void startRedis() {
        try {
        	redisServer = new RedisServer(6379);
            redisServer.start();
            log.info("Redis start");
		} catch (Exception e) {
			log.info("Redis start fail.");
		}
    }

    @PreDestroy
    public void stopRedis() {
    	try {
            redisServer.stop();
            log.info("Redis stop file.");
		} catch (Exception e) {
			log.info("Redis stop");
		}
    }
}
