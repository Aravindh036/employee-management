package employee.actions;

import java.security.PrivilegedAction;
import java.util.Scanner;

import employee.dblayer.DatabaseActions;
import employee.view.View;

public class EmployeeAction implements PrivilegedAction<EmployeeAction> {
    String[] columnString = { "Name", "ID", "Age", "Designation", "Salary" };
    String tableName = "employee", filePath;
    private String name;

    public EmployeeAction(String name){
        this.name = name;
    }

    @Override
    public EmployeeAction run() {
        int ch;
        Scanner sc = new Scanner(System.in);
        DatabaseActions dbactions = new DatabaseActions();
        View helper = new View();

        while (true) {
            helper.printEmployeeChoices();
            ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
            case 1:
                helper.printHeading();
                helper.parseRecords(dbactions.getColumn(this.name, tableName, "name"), true,
                        true);
                break;
            default:
                sc.close();
                System.exit(0);
                break;
            }
        }
    }

}