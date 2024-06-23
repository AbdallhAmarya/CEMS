package logic;

import java.io.Serializable;
import java.net.InetAddress;

public class Client_info implements Serializable {
    private String host;
    private InetAddress ip;
    private String status;

    public Client_info(String host, InetAddress ip, String status) {
        this.host = host;
        this.ip = ip;
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
