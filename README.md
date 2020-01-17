# Employee Management

## Creating class files
```
javac employee\Driver.java
```
```
javac employee\module\LoginModuleClass.java
```
## Creating required jar files.
#### employee.jar
```
creating jar file - jar -cvf employee.jar 
employee\Driver.class 
employee\CallbackHandlerClass.class 
employee\DataAccessException.class 
employee\view\View.class 
employee\schedule\TaskSchedule.class 
employee\principal\EmployeePrincipal.class 
employee\module\LoginModuleClass.class 
employee\model\Employee.class 
employee\dblayer\DatabaseActions.class 
employee\actions\AdminAction.class 
employee\actions\EmployeeAction.class 
employee\actions\OperatorAction.class
```
#### employeeAction.jar
```
jar -cvf employeeAction.jar  
employee\actions\AdminAction.class 
employee\actions\EmployeeAction.class 
employee\actions\OperatorAction.class 
```

## RUN
#### Replace __dirname with the system directory of the files.
```
java -cp "__dirname\employee\lib\jdbc.jar;__dirname\employee.jar;__dirname\employeeAction.jar"  
-Djava.security.policy==Management.policy 
-Djava.security.auth.login.config==JaasConfigfile.config  employee.Driver
```
