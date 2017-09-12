package com.frc.redis.domain;

/**
 * @author songbin
 * @version 2016年8月23日
 * 
 */
public class RedisInfo {
    private String redis_name;
    private int    redis_vnode_num;
    /** according to {@linkplain com.frc.redis.domain.RedisState RedisState} */
    private int    redis_state;
    private String redis_host;
    private int    redis_port;
    private String redis_passwd;

    public String getRedis_name() {
        return redis_name;
    }

    public void setRedis_name(String redis_name) {
        this.redis_name = redis_name;
    }

    public int getRedis_vnode_num() {
        return redis_vnode_num;
    }

    public void setRedis_vnode_num(int redis_vnode_num) {
        this.redis_vnode_num = redis_vnode_num;
    }

    public int getRedis_state() {
        return redis_state;
    }

    public void setRedis_state(int redis_state) {
        this.redis_state = redis_state;
    }

    public String getRedis_host() {
        return redis_host;
    }

    public void setRedis_host(String redis_host) {
        this.redis_host = redis_host;
    }

    public int getRedis_port() {
        return redis_port;
    }

    public void setRedis_port(int redis_port) {
        this.redis_port = redis_port;
    }

    public String getRedis_passwd() {
        return redis_passwd;
    }

    public void setRedis_passwd(String redis_passwd) {
        this.redis_passwd = redis_passwd;
    }
}
