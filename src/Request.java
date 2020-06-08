import java.io.*;
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
    private String answer = "";
    private String time = "0:00S";

    public void send()
    {
        try {
            long startTime = System.currentTimeMillis();
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
                connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
            time = ((System.currentTimeMillis() - startTime)/1000) + ":" + ((System.currentTimeMillis() - startTime)%1000)/10 + "S";
            if(connection.getResponseCode() == 200)
                System.out.print("\u001B[32m");
            System.out.println(status + " " + time);
            System.out.print("\u001B[0m");


            for(Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                if(showHeaders) System.out.println(entry.getKey() + ":" + entry.getValue());
                receivedHeaders += entry.getKey() + ":" + entry.getValue() + ";";
            }
            if(receivedHeaders.length()>0)
                receivedHeaders = receivedHeaders.substring(0,receivedHeaders.length()-1);


            try
            {
                if(output.equals(""))
                {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    answer = "";
                    while (true) {
                        String temp = bufferedReader.readLine();
                        if(temp == null) break;
                        answer += temp + "\n";
                    }
                    System.out.println(answer);
                }
                else
                {
                    if (!output.contains(".") && output.toLowerCase().contains("output_"))
                        output += connection.getHeaderField("Content-Type").toLowerCase().contains("png") ? ".png" : ".html";

                    FileOutputStream file = new FileOutputStream(new File(output));
                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[1024];
                    int bufferLength;
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        file.write(buffer , 0 , bufferLength);
                    }
                    file.close();
                }
            }
            catch (Exception ignored)
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
