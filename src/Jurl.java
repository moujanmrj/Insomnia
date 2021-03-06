import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class is the logic of the program
 * gets commands and makes request
 *
 * @author Moujan Mirjalili
 * @version  2020
 */
public class Jurl {
    public static void main(String[] args)  {
        if(args.length == 0)
        {
            Scanner sc = new Scanner(System.in);
            args = sc.nextLine().split("\\s+");
        }

        if(args[0].toLowerCase().equals("-list"))
        {
            ArrayList<Request> savedRequests;
            try {
                FileInputStream fis = new FileInputStream("Saved_Commands.jurl");
                ObjectInputStream ois=new ObjectInputStream(fis);
                savedRequests = (ArrayList<Request>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                savedRequests = new ArrayList<>();
            }
            int i = 0;
            for(Request request : savedRequests)
            {
                i++;
                System.out.println(i+" : "+request);
            }
            return;
        }

        if(args[0].toLowerCase().equals("-fire"))
        {
            ArrayList<Request> savedRequests;
            try {
                FileInputStream fis = new FileInputStream("Saved_Commands.jurl");
                ObjectInputStream ois=new ObjectInputStream(fis);
                savedRequests = (ArrayList<Request>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                savedRequests = new ArrayList<>();
            }
            for(int i = 1;i < args.length;i++)
            {
                Request request = savedRequests.get(Integer.parseInt(args[i])-1);
                System.out.println("\u001B[33m" + request+":\u001B[0m");
                request.send();
            }
            return;
        }
        if(args[0].toLowerCase().equals("-help"))
        {
            System.out.println("--method -m : set request method in next argument\n" +
                    "--save -s : save request for later\n" +
                    "--output -o : save file (you can choose name in next argument)\n" +
                    "--data -d : set form data in next argument\n" +
                    "--i : show response headers\n" +
                    "--f : set follow redirect true");
            return;
        }

        Request request = new Request();
        boolean save = false;

        if(args[0].toLowerCase().contains("http"))
        {
            request.setUrl(args[0]);
        }
        else
        {
            request.setUrl("http://" + args[0]);
        }

        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            if(isThis(arg,"--method"))
            {
                i++;
                request.setMethod(args[i]);
            }
            else if(isThis(arg,"--output"))
            {
                try{
                    if(args.length == i-1)
                        throw new Exception();
                    request.setOutput(args[i+1]);
                }
                catch (Exception e)
                {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    LocalDateTime now = LocalDateTime.now();
                    request.setOutput("OutPut_"+dtf.format(now));
                }
            }
            else if(isThis(arg,"--data"))
            {
                i++;
                request.setData(args[i]);
            }
            else if(isThis(arg,"--headers"))
            {
                i++;
                request.setHeaders(args[i]);
            }
            else if(isThis(arg,"--save"))
            {
                save = true;
            }
            else if(arg.toLowerCase().equals("-i"))
            {
                request.setShowHeaders(true);
            }
            else if(arg.toLowerCase().equals("-f"))
            {
                request.setFollow(true);
            }
        }
        if(save)
        {
            ArrayList<Request> savedRequests = null;
            try {
                FileInputStream fis = new FileInputStream("Saved_Commands.jurl");
                ObjectInputStream ois=new ObjectInputStream(fis);
                savedRequests = (ArrayList<Request>) ois.readObject();
                fis.close();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                savedRequests = new ArrayList<>();
            }
            savedRequests.add(request);
            try{
                FileOutputStream fop=new FileOutputStream("Saved_Commands.jurl");
                ObjectOutputStream oos=new ObjectOutputStream(fop);
                oos.writeObject(savedRequests);
                oos.close();
                fop.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        request.send();
    }

    /**
     * converts command and arg to their second and third letter and
     * then compares them
     * @param arg arg
     * @param command command
     * @return the comparison
     */
    private static boolean isThis(String arg , String command)
    {
        return arg.toLowerCase().equals(command) || arg.toLowerCase().equals(command.substring(1,3));
    }
}
