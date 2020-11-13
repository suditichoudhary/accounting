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

	<a href="/account">Home</a> |  <a href="/logout">Logout</a>

	<hr />

	<h2 style="text-align: center">Account Statement</h2>
	<br />

	<form action="getStatement" method="get">
		Account ID : <input type="text" name="accid" /><br /> <br />
		<input type="submit"
			value="Submit" />
	</form>
</div>
