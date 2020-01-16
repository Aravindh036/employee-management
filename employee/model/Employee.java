package employee.model;

public class Employee {
    private int id, age, salary;
    private String name, designation, number;

    public Employee() {
    }

    public Employee(String number, int age, int salary, String name, String designation,int id) {
        this.number = number;
        this.age = age;
        this.salary = salary;
        this.name = name;
        this.designation = designation;
		this.id = id;
    }
	public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return this.salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

}