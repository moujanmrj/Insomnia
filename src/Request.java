import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * this class is the logic of the program
 * for request
 *
 * @author Moujan Mirjalili
 * @version  2020
 */
public class Request implements Serializable {
    private String url = "";
    private RequestMethods method = RequestMethods.GET;
    private String headers = "";
    private String data = "";
    private String output = "";
    private String answer = "";
    private boolean showHeaders = false;
    private boolean follow = false;
    private String receivedHeaders = "";
    private String status = "null";
    private String time = "0:00S";
    private BufferedImage img = null;


    private String query = "";

    /**
     * this method sends request
     */
    public void send()
    {
        try {
            long startTime = System.currentTimeMillis();
            URL url = new URL(this.url + this.query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Set settings:
            connection.setRequestMethod(method.name());
            System.out.println(follow);
            connection.setInstanceFollowRedirects(follow);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            if(!headers.equals(""))
                for(String s : headers.split(";"))
                {
                    try{
                        connection.setRequestProperty(s.split(":")[0],s.split(":")[1]);
                    }
                    catch (Exception ignored)
                    {
                    }
                }
            if(!data.equals(""))
            {
                HashMap<String,String> body = new HashMap<String, String>();
                for(String str : data.split("&")){
                    body.put(str.split("=")[0],str.split("=")[1]);
                }
                String boundary = System.currentTimeMillis() + "";
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
                bufferOutFormData(body, boundary, request);
            }

            status = connection.getResponseCode() + " " + connection.getResponseMessage();
            time = ((System.currentTimeMillis() - startTime)/1000) + ":" + ((System.currentTimeMillis() - startTime)%1000)/10 + "S";
            if(connection.getResponseCode() == 200)
                System.out.print("\u001B[32m");
            else
                System.out.print("\u001B[31m");
            System.out.println(status + " " + time);
            System.out.print("\u001B[0m");


            for(Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                if(showHeaders) System.out.println(entry.getKey() + "::" + entry.getValue());
                receivedHeaders += entry.getKey() + "::" + entry.getValue() + ";;";
            }
            if(receivedHeaders.length()>1)
                receivedHeaders = receivedHeaders.substring(0,receivedHeaders.length()-2);

            try{
                if(connection.getHeaderField("Content-Type").toLowerCase().contains("png"))
                {
                    img = null;
                    try {
                        img = ImageIO.read(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                answer = new String(bufferedInputStream.readAllBytes());
                try
                {
                    if(output.equals(""))
                    {
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
            }
            catch (Exception e)
            {
                System.out.println("No Body!");
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
        this.headers = headers.replace("\"","");
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data.replace("\"","");
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

    public void setMethod(RequestMethods method) {
        this.method = method;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getReceivedHeaders() {
        return receivedHeaders;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setReceivedHeaders(String receivedHeaders) {
        this.receivedHeaders = receivedHeaders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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


    /**
     *it writes form data
     */
    private void bufferOutFormData(HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    public void set(JPanel formPanel,String type) {
        String data = "";
        ArrayList<JTextArea> texts = new ArrayList<>();
        for(Component c : formPanel.getComponents())
        {
            JPanel panel = (JPanel) c;
            for(Component component : panel.getComponents())
            {
                if(component instanceof JTextArea)
                {
                    texts.add((JTextArea) component);
                }
                if(component instanceof JRadioButton)
                {
                    JRadioButton jRadioButton = (JRadioButton) component;
                    if(!jRadioButton.isSelected())
                    {
                        texts.remove(texts.size()-1);
                        texts.remove(texts.size()-1);
                    }
                }
            }
        }
        for (int i = 0; i < texts.size(); i+=2) {
            JTextArea text = texts.get(i);
            if(!text.getText().equals("") && !texts.get(i+1).getText().equals(""))
                data += text.getText() + "=" + texts.get(i+1).getText() + "&";
        }
        if(type.equals("headers"))
        {
            try{
                data = data.replace("=",":").replace("&",";");
                this.headers = data.substring(0,data.length()-1);
            }
            catch (Exception e)
            {
                this.headers = "";
            }
        }
        else if(type.equals("data"))
        {
            try{
                this.data = data.substring(0,data.length()-1);
            }
            catch (Exception e)
            {
                this.data = "";
            }
        }
        else if(type.equals("query"))
        {
            try{
                this.query = "?" + data.substring(0,data.length()-1);
            }
            catch (Exception e)
            {
                this.query = "";
            }
        }
    }
}
