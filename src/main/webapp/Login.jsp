<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>

<html>
<style>
.content {
  max-width: 500px;
  margin: auto;
}
form {
  text-align: center;
}
</style>
<hr/>  
<c:set var="contextPath" value=""/>
<h2 style="text-align:center">Login Into Your Account</h2>  
 <br/>  
 
<form action="@{contextPath}/login" method="post">  
User Name  :   <input type="text" name="username"/><br/><br/>  
Password   :  <input type="password" name="password"/><br/><br/>  

        <c:out value="${errorMsg}" />
<input type="submit" value="Submit"/> 

</form>  
</html>