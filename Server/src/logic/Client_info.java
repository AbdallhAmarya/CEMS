package logic;

import java.io.Serializable;

public class Client_info implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String host;
    private String ip;
    private String status;

    public Client_info(String host, String ip, String status) {
    	this.ip = ip;
    	this.host = host;
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
