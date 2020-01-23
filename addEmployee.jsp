<html>
	<head>
		<title>Add Employee</title>
		 <link href="main.css" rel="stylesheet"/>
		<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>
	<head>
	<body>
		<nav class='nav-container'><h4>Employee Management</h4></nav>
		<div class="topic"><span>Add Employee Details</span></div>
		<form class="add-form" action="add" method="POST">
			
			<div class="name">
				<span>Name</span>
				<input type="text" name="name" id="emp-name">
			</div>
			<div class="mobile-number">
				<span>Mobile Number</span>
				<input type="number" name="mobile" id="emp-mobile">
			</div>
			<div class="age">
				<span>Age</span>
				<input type="number" name="age" id="emp-age">
			</div>
			<div class="designation">
				<span>Designation</span>
				<input type="text" name="designation" id="emp-designation">
			</div>
			<div class="salary">
				<span>Salary</span>
				<input type="number" name="salary" id="emp-salary">
			</div>
			<input type="submit" value="Add Employee">
		</form>
		<a href='/sampleServlet/'>Go back</a>
	</body>
</html>