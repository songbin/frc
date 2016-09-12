package com.frc.redis.domain;

/**
 * @author songbin
 * @version 创建时间：2016年8月24日
 * 
 */
public class RedisState {
    public final static int STATE_UNKOWN = -1;
    public final static int STATE_INIT = 0;
    public final static int STATE_NORMAL = 1;
    public final static int STATE_DEL = 2;
    public final static int STATE_ADD = 3;
    public final static int STATE_DESTROY = 4;
}
