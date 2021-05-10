package com.allimu.zhongkong.entity;

/**
 * @author Administrator
 */
public class SerialInterface {

    private Long id;
    private String mac;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public SerialInterface(String mac) {
        this.mac = mac;
    }
    public SerialInterface() {
    }

    @Override
    public String toString() {
        return "SerialInterface{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                '}';
    }
}
