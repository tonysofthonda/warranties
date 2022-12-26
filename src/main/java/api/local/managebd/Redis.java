package api.local.managebd;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import redis.embedded.RedisServer;

@Component
public class Redis {
	
	private static final Logger logger = LogManager.getLogger(Redis.class);

	private RedisServer redisServer;
	

    @PostConstruct
    public void startRedis() {
        try {
        	redisServer = new RedisServer(6379);
            redisServer.start();
            logger.info("Redis start");
		} catch (Exception e) {
			logger.info("Redis start fail.");
		}
    }

    @PreDestroy
    public void stopRedis() {
    	try {
            redisServer.stop();
            logger.info("Redis stop file.");
		} catch (Exception e) {
			logger.info("Redis stop");
		}
    }
}
