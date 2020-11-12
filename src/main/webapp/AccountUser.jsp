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

	<a href="/account">Home</a> | <a
		onclick="document.forms['logoutForm'].submit()">Logout</a>

	<hr />

	<h2 style="text-align: center">Account Statement</h2>
	<br />

	<form action="getStatement" method="get">
		Account ID : <input type="text" name="accid" /><br /> <br />
		<input type="submit"
			value="Submit" />
	</form>
	<form id="logoutForm" method="POST" action="/logout"></form>
</div>
