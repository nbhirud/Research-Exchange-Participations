<%-- 
    Document   : reporth
    Created on : Feb 5, 2016, 5:20:55 PM
    Author     : Abhishek Banerjee
--%>
<%@page import="assignment2.business.Reported"%>
<%@page import="java.util.ArrayList"%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to go back to Main page  --%>
<br>
<a href="main.jsp" id="back_to_page">&laquo;Back to the Main Page</a>
<br>
 <div class="table-responsive">
<table class="table" >
    <%
        ArrayList<Reported> reportedQuestions = (ArrayList<Reported>)request.getAttribute("reportedQuestions");
        if (reportedQuestions.size() == 0){
            out.println("No reported questions");
        }
        else{
    %>
    <tr>
            <th>Report Date</th>
            <th>Report Question</th>		
            <th>Report Status</th>
            
    </tr>    
    <%       
            for(Reported r : reportedQuestions){
                
    %>
    <tr>
    <td><%out.print(r.getReported_date().toString());%></td>
    <td><%out.print(r.getQuestion());%></td>
    <td><%out.print(r.getStatus());%></td>
    </tr>
    <%
            }
        }
        
        
    %>
        <%--Column Names --%>
    
 
    </table>
    </div>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>