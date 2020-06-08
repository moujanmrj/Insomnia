import java.io.Serializable;

public class Request implements Serializable {
    private String url = "";
    private RequestMethods method = RequestMethods.GET;
    private String headers = "";
    private String data = "";
    private String output = "";
    private boolean showHeaders = false;
    private boolean follow = false;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public boolean isShowHeaders() {
        return showHeaders;
    }

    public void setShowHeaders(boolean showHeaders) {
        this.showHeaders = showHeaders;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public RequestMethods getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = RequestMethods.valueOf(method.toUpperCase());
    }
}
