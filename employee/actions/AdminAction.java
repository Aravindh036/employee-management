package employee.actions;

import java.io.File;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.security.auth.Subject;

import employee.dblayer.DatabaseActions;
import employee.Driver;
import employee.view.View;
import employee.model.Employee;
import employee.schedule.TaskSchedule;

public class AdminAction implements PrivilegedAction<AdminAction> {
    String[] columnString = { "Name", "ID", "Age", "Designation", "Salary" };
    String tableName = "employee", filePath;
	
    @Override
    public AdminAction run() {
        int ch;
        Scanner sc = new Scanner(System.in);
        DatabaseActions dbactions = new DatabaseActions();
        View helper = new View();

        while (true) {
            helper.printChoices();
            ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
            case 1:
                Employee emp = new Employee();
                System.out.println("Employee Mobile Number : ");
                String number = sc.nextLine();
                emp.setNumber(number);
                System.out.println(emp.getNumber());
                if (dbactions.searchColumn(emp.getNumber(), tableName, "mobile_number")) {
                    System.out.println("An Employee with this ID already exist : " + emp.getNumber());
                    break;
                }
                System.out.print("Employee Name : ");
                emp.setName(sc.nextLine());
                System.out.print("Employee's Age : ");
                emp.setAge(sc.nextInt());
                sc.nextLine();
                System.out.print("Designation : ");
                emp.setDesignation(sc.nextLine());
                System.out.print("Salary : ");
                emp.setSalary(sc.nextInt());
                sc.nextLine();
                dbactions.saveToDb(emp, tableName);
                break;
            case 2:
                if (dbactions.checkRecords(tableName)) {
                    helper.parseRecords(dbactions.getRecords(tableName), false, true);
                } else {
                    System.out.println("No Records found");
                    break;
                }
                Employee emp_edit = new Employee();
                System.out.print("Employee ID :");
                emp_edit.setId(sc.nextInt());
                sc.nextLine();
                if (dbactions.searchColumn(Integer.toString(emp_edit.getId()), tableName, "id")) {
                    System.out.print("Edit Employee Name [y/n]: ");
                    if ((sc.next().charAt(0) == 'y')) {
                        System.out.print(columnString[0] + " :");
                        sc.nextLine();
                        emp_edit.setName(sc.nextLine());
                        dbactions.updateRecord(emp_edit.getName(), columnString[0], emp_edit.getId(), tableName);
                    }
                    System.out.print("Edit Employee Mobile Number [y/n]: ");
                    if ((sc.next().charAt(0) == 'y')) {
                        System.out.print(columnString[1] + " :");
                        emp_edit.setNumber(sc.nextLine());
                        dbactions.updateRecord(emp_edit.getNumber(), columnString[1], emp_edit.getId(), tableName);
                    }
                    System.out.print("Edit Employee Age [y/n]: ");
                    if ((sc.next().charAt(0) == 'y')) {
                        System.out.print(columnString[2] + " :");
                        emp_edit.setAge(sc.nextInt());
                        sc.nextLine();
                        dbactions.updateRecord(Integer.toString(emp_edit.getAge()), columnString[2], emp_edit.getId(),
                                tableName);
                    }
                    System.out.print("Edit Employee Designation [y/n]: ");
                    if ((sc.next().charAt(0) == 'y')) {
                        System.out.print(columnString[3] + " :");
                        sc.nextLine();
                        emp_edit.setDesignation(sc.nextLine());
                        dbactions.updateRecord(emp_edit.getDesignation(), columnString[3], emp_edit.getId(), tableName);

                    }
                    System.out.print("Edit Employee Salary [y/n]: ");
                    if ((sc.next().charAt(0) == 'y')) {
                        System.out.print(columnString[4] + " :");
                        emp_edit.setSalary(sc.nextInt());
                        sc.nextLine();
                        dbactions.updateRecord(Integer.toString(emp_edit.getSalary()), columnString[4],
                                emp_edit.getId(), tableName);
                    }
                    System.out.println("Record updated successfully");
                } else {
                    System.out.println("Employee ID not found");
                }
                break;
            case 3:
                if (dbactions.checkRecords(tableName)) {
                    helper.parseRecords(dbactions.getRecords(tableName), false, true);
                } else {
                    System.out.println("No Records found");
                    break;
                }
                Employee emp_delete = new Employee();
                System.out.println("Employee ID :");
                emp_delete.setId(sc.nextInt());
                sc.nextLine();
                if (dbactions.searchColumn(Integer.toString(emp_delete.getId()), tableName, "id")) {
                    dbactions.deleteRecord(emp_delete.getId(), tableName);
                    System.out.println("Deleted Successfully");
                } else {
                    System.out.println("Employee ID not found");
                }
                break;
            case 4:
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
            case 5:
                if (dbactions.checkRecords(tableName)) {
                    helper.printHeading();
                    helper.parseRecords(dbactions.getRecords(tableName), true, true);
                } else {
                    System.out.println("No Records found");
                    break;
                }
                break;
            case 6:
                System.out.print("Enter the CSV filename : ");
                String filename = sc.nextLine();
                try {
                    filePath = new File(Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                            .getPath();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                filePath = filePath + "/employee/lib/" + filename;
                System.out.println(filePath);
                System.out.print("Start time for regular import from now (in min) :");
                int interval = sc.nextInt();
                TimerTask importCSVTask = new TaskSchedule(filePath, tableName);
                Timer timer = new Timer();
                timer.schedule(importCSVTask, interval * 60000, 60 * 60000);
                System.out.println("Scheduler has been configured successfully, to do the import to the DB!");
                break;
            default:
                sc.close();
                System.exit(0);
                break;
            }
        }
    }

}