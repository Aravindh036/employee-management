package employee;
import java.sql.*;
import java.util.LinkedList;
import java.io.*;

public class DatabaseActions {
    Connection conn;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    DatabaseActions() {
        createEmployeeTable(getConnectionObject());
		createAdminTable(getConnectionObject());
    }

    public Connection getConnectionObject() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres",
                        "6279and77@$");
            } catch (SQLException e) {
                throw new DataAccessException("Unable to connect to the database!"+e);
            }
        }
        return conn;
    }

    public void createEmployeeTable(Connection conn) {
        String query = "CREATE TABLE IF NOT EXISTS  employee(Id SERIAL not null, Name VARCHAR(15),Mobile_Number VARCHAR(15) UNIQUE, Age INT, Designation VARCHAR(15), Salary INT)";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Unable to create the table!");
        }
    }
	
	public void createAdminTable(Connection conn) {
		
		try {
			DatabaseMetaData dbm = conn.getMetaData();
			Statement statement  = conn.createStatement();
			ResultSet tables = dbm.getTables(null, null, "admin_auth", null);
			boolean result = tables.next();
			if (!result) {
				String createTableQuery = "CREATE TABLE admin_auth(id VARCHAR(15) PRIMARY KEY,password VARCHAR(15))";
				String insertAdminCredQuery = "INSERT INTO admin_auth(id,password) VALUES('admin-001','est@2224')";
				conn.setAutoCommit(false);
				statement.addBatch(createTableQuery);
				statement.addBatch(insertAdminCredQuery);
				statement.executeBatch();
				conn.commit();
				tables.close();
				statement.close();
			}
			
        } catch (SQLException e) {
            throw new DataAccessException("Unable to create the table!"+e);
        }
    }

    public void closeConnection() {
		try {
            if (conn != null) {
                resultSet.close();
                preparedStatement.close();
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error in closing the database connections");
        }
    }

    protected PreparedStatement constructStatement(String query) {
        conn = getConnectionObject();
        preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(query);

        } catch (SQLException e) {
            throw new DataAccessException("Unable to construct the query string");
        }
        return preparedStatement;
    }

    protected ResultSet executeStatement(PreparedStatement preparedStatement) {
        resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DataAccessException("Unable to execute the query!");
        }
        return resultSet;
    }
	protected boolean checkAdmin(String id, String password, String tableName){
		String authQuery = "SELECT * FROM "+tableName+" WHERE id=? and password=?";
		try{
			preparedStatement = constructStatement(authQuery);
			preparedStatement.setString(1,id);
			preparedStatement.setString(2,password);
			resultSet = executeStatement(preparedStatement);
			while(resultSet.next()){
				return true;
			}
		}
		catch(SQLException e){
			throw new DataAccessException("Unable to fetch admin creds !");
		}
		closeConnection();
		return false;
	}
	protected void changePassword(String id, String password, String tableName){
		String changePasswordQuery = "UPDATE "+tableName+" SET password=? WHERE id=?";
		//String changePasswordQuery = "UPDATE admin_auth SET password='hello world' WHERE id='admin-001'";
		try{
			preparedStatement = constructStatement(changePasswordQuery);
			preparedStatement.setString(1,password);
			preparedStatement.setString(2,id);
			preparedStatement.executeUpdate();
		}
		catch(SQLException e){
			throw new DataAccessException("Unable to change password !"+e);
		}
		finally{
			closeConnection();
		}
	}
    protected void importCSV(String filePath,String tableName){
		String line = "";
		 try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] employee = line.split(",");
				if(!searchColumn(employee[1],tableName,"mobile_number")){
					Employee emp = new Employee();
					emp.setNumber(employee[1]);
					emp.setName(employee[0]);
					emp.setAge(Integer.parseInt(employee[2]));
					emp.setDesignation(employee[3]);
					emp.setSalary(Integer.parseInt(employee[4]));
					saveToDb(emp,tableName);
				}
				else{
					String SQL_QUERY = "UPDATE "+tableName+" SET name=?, age=?, designation=?, salary=? where mobile_number=?";
					preparedStatement = constructStatement(SQL_QUERY);
					preparedStatement.setString(1,employee[0]);
					preparedStatement.setInt(2,Integer.parseInt(employee[2]));
					preparedStatement.setString(3,employee[3]);
					preparedStatement.setInt(4,Integer.parseInt(employee[4]));
					preparedStatement.setString(5,employee[1]);
					preparedStatement.executeUpdate();
				}
            }

        } catch (IOException e) {
            throw new DataAccessException("Unable to open the provided CSV file!");
        }
		catch(SQLException e){
			throw new DataAccessException("Unable to import Records from CSV file"+e);
		}
		finally{
			closeConnection();
		}
    }

    protected boolean checkRecords(String tableName) {
        String SQL_SELECT = "Select * from " + tableName;
        resultSet = executeStatement(constructStatement(SQL_SELECT));
        boolean found = false;
        try {
            while (resultSet.next()) {
                found = true;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to fetch the records from database!");
        }
        closeConnection();
        return found;
    }

    protected LinkedList<Employee> getRecords(String tableName) {
        String SQL_SELECT = "Select * from " + tableName;
        resultSet = executeStatement(constructStatement(SQL_SELECT));
        LinkedList<Employee> employeeList = new LinkedList<Employee>();
        try {
            while (resultSet.next()) {
                Employee emp = new Employee();
				emp.setId(resultSet.getInt("id"));
                emp.setNumber(resultSet.getString("mobile_number"));
                emp.setName(resultSet.getString("name"));
                emp.setAge(resultSet.getInt("age"));
                emp.setDesignation(resultSet.getString("designation"));
                emp.setSalary(resultSet.getInt("salary"));
                employeeList.add(emp);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to fetch the records from database!");
        } finally {
            closeConnection();
        }
        return employeeList;
    }

    protected Object getColumnValue(String string, String tableName, String searchcolumName, String columnName) {
        String SQL_SELECT = "Select * from " + tableName + " where " + searchcolumName + "=?";
        Object result = new Object();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = constructStatement(SQL_SELECT);
            preparedStatement.setObject(1, string);
            resultSet = executeStatement(preparedStatement);
            while (resultSet.next()) {
                result = resultSet.getObject(columnName);
                break;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to fetch the data from the table");
        } finally {
            closeConnection();
        }

        return result;
    }

    protected void deleteRecord(int id, String tableName) {
        String SQL_SELECT = "DELETE FROM " + tableName + " WHERE id=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = constructStatement(SQL_SELECT);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error in deleting the Employee's Details for the ID :" + id);
        } finally {
            closeConnection();
        }
    }

    protected void updateRecord(String name, String string, int id, String tableName) {
        String SQL_SELECT = "UPDATE " + tableName + " SET " + string + "= ? " + "WHERE id= ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = constructStatement(SQL_SELECT);
            preparedStatement.setObject(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error in updating the Employee's" + string);
        } finally {
            closeConnection();
        }
    }

    protected String constructQueryString(String name, String tableName, String columnName) {
        String SQL_SELECT = null;
        if (columnName == "mobile_number") {
            SQL_SELECT = "SELECT * FROM " + tableName + " WHERE "+columnName+"='" + name + "';";
        } else if (columnName == "id") {
            SQL_SELECT = "select * from " + tableName + " where id=" + name;
        }
        return SQL_SELECT;
    }

    protected boolean searchColumn(String name, String tableName, String columnName) {
        resultSet = executeStatement(constructStatement(constructQueryString(name, tableName, columnName)));
        boolean found = false;
        try {
            while (resultSet.next()) {
                found = true;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to fetch the records from database!");
        } finally {
            closeConnection();
        }
        return found;
    }

    protected LinkedList<Employee> getColumn(String name, String tableName, String columnName) {
        resultSet = executeStatement(constructStatement(constructQueryString(name, tableName, columnName)));
        LinkedList<Employee> employeeList = new LinkedList<Employee>();
        try {
            while (resultSet.next()) {
                Employee emp = new Employee();
				emp.setId(resultSet.getInt("id"));
                emp.setNumber(resultSet.getString("mobile_number"));
                emp.setName(resultSet.getString("name"));
                emp.setAge(resultSet.getInt("age"));
                emp.setDesignation(resultSet.getString("designation"));
                emp.setSalary(resultSet.getInt("salary"));
                employeeList.add(emp);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to fetch the records from database!");
        } finally {
            closeConnection();
        }
        return employeeList;
    }

    protected void saveToDb(Employee emp, String tableName) {
        String SQL_SELECT = "INSERT INTO " + tableName + "(name,mobile_number,age,designation,salary) VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = constructStatement(SQL_SELECT);
            preparedStatement.setString(1, emp.getName());
			preparedStatement.setString(2, emp.getNumber());
            preparedStatement.setInt(3, emp.getAge());
            preparedStatement.setString(4, emp.getDesignation());
            preparedStatement.setInt(5, emp.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error in inserting the new Employee Details into the database!");
        }
        closeConnection();
    }
}
