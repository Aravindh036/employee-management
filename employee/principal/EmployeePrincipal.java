package employee.principal;
import java.security.Principal;

public class EmployeePrincipal implements Principal, java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    public EmployeePrincipal(String name) {
        if (name == null)
            throw new NullPointerException("illegal null input");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
	
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof EmployeePrincipal))
            return false;
        EmployeePrincipal that = (EmployeePrincipal)o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    public int hashCode() {
        return name.hashCode();
    }
}
