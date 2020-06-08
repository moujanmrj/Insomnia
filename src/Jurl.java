import java.util.Scanner;

public class Jurl {
    public static void main(String[] args) {
        if(args.length == 0)
        {
            Scanner sc = new Scanner(System.in);
            args = sc.nextLine().split("\\s+");
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
                i++;
                request.setOutput(args[i]);
            }
        }
    }
    private static boolean isThis(String arg , String command)
    {
        return arg.toLowerCase().equals(command) || arg.toLowerCase().equals(command.substring(1,3));
    }
}
