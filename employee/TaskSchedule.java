package employee;
import java.util.TimerTask;

import employee.dblayer.DatabaseActions;

public class TaskSchedule extends TimerTask{
	private String fileName;
	private String tableName;
	TaskSchedule(String fileName,String tableName){
		this.fileName = fileName;
		this.tableName = tableName;
	}
	@Override
	public void run(){
		DatabaseActions dbaction = new DatabaseActions();
		dbaction.importCSV(fileName,tableName);
		System.out.println("CSV file import successful");
	}
}