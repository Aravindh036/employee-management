package employee.view;

import java.util.LinkedList;
import employee.model.Employee;

public class View {
    public void printAllNode(Employee emp) {
        System.out.println(emp.getId() + "\t\t" + emp.getName() + "\t\t" + emp.getNumber() + "\t\t" + emp.getAge()
                + "\t\t" + emp.getDesignation() + "\t\t" + emp.getSalary());
    }

    public void printNode(Employee emp) {
        System.out.println("ID : " + emp.getId() + "\t" + "Name : " + emp.getName());
    }

    public void parseRecords(LinkedList<Employee> employeeList, boolean flag, boolean print) {
        for (Employee emp : employeeList) {
            if (print && flag) {
                printAllNode(emp);
            } else {
                printNode(emp);
            }
        }
    }

    public void printHeading() {
        System.out.println("Id\t\tName\t\tMobile Number\t\tAge\t\tDesignation\t\tSalary");
    }

    public void printChoices() {
        System.out.println(
                "\n1.Add an Employee\n2.Edit Employee Details\n3.Delete Employee Details\n4.View an Employee's Details\n5.View all Employee's Details\n6.Import Records from CSV file\n7.Change Admin Password\n8.Exit\n");
    }

    public void userChoices() {
        System.out.println("\n1.Login\n2.Change Password\n3.Exit\n");
    }

    public void homeChoices() {
        System.out.println("\n1.Admin\n2.Operator\n3.Employee\n");
    }

    public void printOperationChoices() {
        System.out
                .println("\n1.View an Employee's Details\n2.View all Employee's Details\n3.Change Password\n4.Exit\n");
    }

    public void printEmployeeChoices() {
        System.out.println("\n1.View an Employee's Details\n2.Exit\n");
    }
}