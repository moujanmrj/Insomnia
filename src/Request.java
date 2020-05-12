import javax.swing.*;

public class Request
{

    private String type;
    private String name;

    public void setType(String type) { this.type = type; }
    public String getType() { return type; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public Request(String type, String name)
    {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return getType() + getName();
    }
}
