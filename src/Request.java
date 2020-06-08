import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request implements Serializable {
    private String url = "";
    private RequestMethods method = RequestMethods.GET;
    private String headers = "";
    private String data = "";
    private String output = "";
    private boolean showHeaders = false;
    private boolean follow = false;
    private String receivedHeaders = "";
    private String status = "null";

    public void send()
    {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Set settings:
            connection.setRequestMethod(method.name());
            connection.setInstanceFollowRedirects(follow);
            connection.setDoOutput(true);

            if(!data.equals(""))
            {
                byte[] postData = data.getBytes( StandardCharsets.UTF_8 );
                int postDataLength = postData.length;
                connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty( "charset", "utf-8");
                connection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                try( DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                    wr.write( postData );
                }
            }

            for(String s : headers.split(";"))
            {
                try{
                    connection.setRequestProperty(s.split(":")[0],s.split(":")[1]);
                }
                catch (Exception ignored)
                {
                }
            }

            status = connection.getResponseCode() + " " + connection.getResponseMessage();
            System.out.println(status);

            for(Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                if(showHeaders) System.out.println(entry.getKey() + ":" + entry.getValue());
                receivedHeaders += entry.getKey() + ":" + entry.getValue() + ";";
            }
            if(receivedHeaders.length()>0)
                receivedHeaders = receivedHeaders.substring(0,receivedHeaders.length()-1);


            if(connection.getResponseCode() == 200)
            {

            }


            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public String toString() {
        return
                "url='" + url + '\'' +
                        ", method=" + method +
                        ", headers='" + headers + '\'' +
                        ", data='" + data + '\'' +
                        ", output='" + output + '\'' +
                        ", showHeaders=" + showHeaders +
                        ", follow=" + follow;
    }
}
