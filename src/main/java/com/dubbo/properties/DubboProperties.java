package com.dubbo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc:
 * Author:
 * Date: 2016/11/8
 */

@ConfigurationProperties(prefix = "dubbo")
public class DubboProperties {

    // 模块名称
    private String module;

    // 协议
    private String regProtocol = "zookeeper";

    // 注册地址 host:port
    private String registryAddress = "127.0.0.1:2181";

    // 是否检查provider存在
    private Boolean check = true;

    // 服务端口(-1为随机端口)
    private Integer port = -1;

    // 默认调用超时时间(毫秒)
    private Integer timeout = 10000;

    // 线程数
    private Integer threads = 100;

    // 心跳(单位:毫秒)
    private Integer heartBeats = 10000;

    // 服务IP地址(多网卡时使用)
    private String host;

    // 序列化方式(hession2/nativejava)
    private String serialization = "nativejava";

    // 版本
    private String version = "1.0.0";

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Integer getHeartBeats() {
        return heartBeats;
    }

    public void setHeartBeats(Integer heartBeats) {
        this.heartBeats = heartBeats;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getRegProtocol() {
        return regProtocol;
    }

    public void setRegProtocol(String regProtocol) {
        this.regProtocol = regProtocol;
    }

    public String getSerialization() {
        return serialization;
    }

    public void setSerialization(String serialization) {
        this.serialization = serialization;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
