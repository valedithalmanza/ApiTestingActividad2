
package client;

public class RequestInfo {

    private final String url;
    private final String body;
    private final String token;

    public RequestInfo(String url, String body) {
        this(url, body, null);
    }

    public RequestInfo(String url, String body, String token) {
        this.url = url;
        this.body = body;
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public String getToken() {
        return token;
    }
}
