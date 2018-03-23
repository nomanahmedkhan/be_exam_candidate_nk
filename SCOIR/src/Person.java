import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author noman
 * This class is used to create and return the objects that need to be written to the JSON files.
 */

@JsonInclude(Include.NON_EMPTY)
public class Person
{
    private int id;
    private String first;
    private String middle;
    private String last;
    private String phone;
    
    public Person(int id, String firstName, String middleName, String lastName, String phone) {
        
        this.id = id;
        this.first = firstName;
        this.middle = middleName;
        this.last = lastName;
        this.phone = phone;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    
   
    public String getFirst() {
        return first;
    }
    public void setFirst(String firstName) {
        this.first = firstName;
    }
    public String getMiddle() {
    	return middle;
    }
    public void setMiddle(String middleName) {
    	this.middle = middleName;
    }
    public String getLast() {
        return last;
    }
    public void setLast(String lastName) {
        this.last = lastName;
    }
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + first
                + ", middleName=" + middle + ", lastName=" + last + ", phone=" + phone + "]";
    }
}
