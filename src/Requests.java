import javax.swing.*;

/**
 * this class has the requests and their characteristics
 *
 * @author Moujan Mirjalili
 * @version  2020
 */
public class Requests
{
    private String type;
    private String name;

    /**
     * set request type
     * @param type request type
     */
    public void setType(String type) { this.type = type; }

    /**
     * get request type
     * @return request type
     */
    public String getType() { return type; }

    /**
     * set request name
     * @param name request name
     */
    public void setName(String name) { this.name = name; }

    /**
     * get request name
     * @return request name
     */
    public String getName() { return name; }

    /**
     * the constructor
     * @param type request type
     * @param name request name
     */
    public Requests(String type, String name)
    {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return getType() + getName();
    }
}
