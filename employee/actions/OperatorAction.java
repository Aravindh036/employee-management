package employee.actions;

import java.security.PrivilegedAction;
import java.util.Scanner;

import employee.dblayer.DatabaseActions;
import employee.view.View;
import employee.model.Employee;

public class OperatorAction implements PrivilegedAction<OperatorAction> {
    String[] columnString = { "Name", "ID", "Age", "Designation", "Salary" };
    String tableName = "employee", filePath;
    String id;

    public OperatorAction(String id) {
        this.id = id;
    }

    @Override
    public OperatorAction run() {
        int ch;
        Scanner sc = new Scanner(System.in);
        DatabaseActions dbactions = new DatabaseActions();
        View helper = new View();

        while (true) {
            helper.printOperationChoices();
            ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
            case 1:
                if (dbactions.checkRecords(tableName)) {
                    helper.parseRecords(dbactions.getRecords(tableName), false, true);
                } else {
                    System.out.println("No Records found");
                    break;
                }
                Employee emp_view = new Employee();
                System.out.print("Search the Employee record by ID [y/n]:");
                if ((sc.next().charAt(0) == 'y')) {
                    System.out.print("Enter the ID : ");
                    emp_view.setId(sc.nextInt());
                    sc.nextLine();
                    if (dbactions.searchColumn(Integer.toString(emp_view.getId()), tableName, "id")) {
                        helper.printHeading();
                        helper.parseRecords(dbactions.getColumn(Integer.toString(emp_view.getId()), tableName, "id"),
                                true, true);
                        break;
                    } else {
                        System.out.println("Employee ID not found");
                        break;
                    }
                }
                System.out.print("Search the Employee record by Name [y/n]:");
                if ((sc.next().charAt(0) == 'y')) {
                    System.out.print("Enter the Name : ");
                    emp_view.setName(sc.nextLine());
                    if (dbactions.searchColumn(emp_view.getName(), tableName, "name")) {
                        helper.printHeading();
                        emp_view.setId((int) dbactions.getColumnValue(emp_view.getName(), tableName, "name", "id"));
                        helper.parseRecords(dbactions.getColumn(Integer.toString(emp_view.getId()), tableName, "id"),
                                true, true);
                    } else {
                        System.out.println("Employee ID not found");
                    }
                }
                break;
            case 2:
                if (dbactions.checkRecords(tableName)) {
                    helper.printHeading();
                    helper.parseRecords(dbactions.getRecords(tableName), true, true);
                } else {
                    System.out.println("No Records found");
                    break;
                }
                break;
            case 3:
                System.out.print("Enter the new Password : ");
                String password = sc.nextLine();
                dbactions.changePassword(id, password, "operator_auth");
                break;
            default:
                sc.close();
                System.exit(0);
                break;
            }
        }
    }

}