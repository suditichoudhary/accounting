<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">

	<a href="/account">Home</a> | <a
		onclick="document.forms['logoutForm'].submit()">Logout</a>
		<form id="logoutForm" method="POST" action="/logout"></form>

	<hr />

<h1 style="text-align:center">Welcome to Nagarro Account</h1>
<h3 style="text-align:center">Please visit <a href="http://localhost:8080/account" target="_self">here</a></h3>
<a href="/logout">logout</a>
</div>
</html>