package com.daogen.socialmedia.views;

import com.daogen.socialmedia.core.wechatAuth;
import com.yammer.dropwizard.views.View;

public class simpleView extends View{

	   private final wechatAuth wechat;

	    public simpleView(wechatAuth wechat) {
	        super("simple.ftl");
	        this.wechat = wechat;
	    }

	    public wechatAuth getWechat() {
	        return wechat;
	    }
}
