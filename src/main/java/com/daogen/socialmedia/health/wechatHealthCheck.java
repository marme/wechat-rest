package com.daogen.socialmedia.health;

import com.daogen.socialmedia.core.wechatToken;
import com.yammer.metrics.core.HealthCheck;

public class wechatHealthCheck  extends HealthCheck{

	public wechatHealthCheck(String name) {
		super(name);
	}

    @Override
    protected Result check() throws Exception {
        String token = wechatToken.getToken();
        if (token == null || token.isEmpty()) {
            return Result.unhealthy("unable to get token");
        }
        return Result.healthy();
    }
}
