<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
.content {
	max-width: 500px;
	margin: auto;
}

form {
	text-align: center;
}
</style>
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;"> 
	<a href="/account">Home</a> | <a href="/logout">Logout</a>

	<hr />

	<h2 style="text-align: center">Account Statement</h2>
	<br />

	<form action="getStatement" method="get">
		Account ID : <input type="text" name="accid" /><br /> <br />
		Date From* : <input type="text" name="datefrom" /><br /> <br /> 
		Date To* : <input type="text" name="dateto" /><br /> <br /> Amount From :
		<input type="text" name="amountfrom" /><br /> <br /> Amount To : <input
			type="text" name="amountto" /><br /> <br /> <input type="submit"
			value="Submit" /><br /> <br />
			* Date Format should be dd.mm.yyyy
	</form>
</div>
