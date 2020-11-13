<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
      <style>
         table {
         font-family: arial, sans-serif;
         border-collapse: collapse;
         width: 100%;
         }
         td, th {
         border: 1px solid #dddddd;
         text-align: left;
         padding: 8px;
         }
         tr:nth-child(even) {
         background-color: #dddddd;
         }
      </style>
   </head>
   <div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">
      <a href="/account">Home</a>  | <a href="/logout">Logout</a>
      <hr />
   
   <h3 style="text-align: center">Find Your Account Statement</h3>
   <h4 style="text-align: center">
      Account Number :
      <c:out value="${accountStatement.accountNumber}" />
   </h4>
   <div class="container-text-center" id="tasksDiv">
      <div class="table-responsive">
         <table>
            <thead>
               <tr>
                  <th style="text-align: center; vertical-align: middle;">ID</th>
                  <th style="text-align: center; vertical-align: middle;">Date</th>
                  <th style="text-align: center; vertical-align: middle;">Amount</th>
               </tr>
            </thead>
            <tbody>
               <c:forEach var="statement" items="${accountStatement.statementList}">
                  <tr>
                     <td style="text-align: center; vertical-align: middle;">${statement.statementId}</td>
                     <td style="text-align: center; vertical-align: middle;">${statement.date}</td>
                     <td style="text-align: center; vertical-align: middle;">${statement.amount}</td>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
      </div>
   </div>
   </div>
</html>