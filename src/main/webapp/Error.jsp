<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">

	<a href="/account">Home</a> | <a
		onclick="document.forms['logoutForm'].submit()">Logout</a>
		<form id="logoutForm" method="POST" action="/logout"></form>

	<hr />

<h2 style="text-align:center"> Error Occurred : <%=request.getAttribute("Error")%></h2>
</div>
</html>