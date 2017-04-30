<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%@page import="assignment2.business.Reported"%>
<%@page import="java.util.ArrayList"%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to go back to Main page  --%>
<br>
<h3><span id="studies">Reported Questions</span></h3><br/>
<a href="admin.jsp" id="back_to_page">&laquo;Back to the Main Page</a><br/>
<br/><br/><br/>


<!-- TODO: Add more code to get the table here.
  -->
  <div class="table-responsive">
  <table class="table" >
        <%--Column Names --%>
        
        <%
        ArrayList<Reported> reportedQuestions = (ArrayList<Reported>)request.getAttribute("reportedQuestions");
        if (reportedQuestions.size() == 0){
            out.println("No reported questions");
        }
        else{
    %>
        <tr>
            <th>Question</th>
            <th>Action</th>		
        </tr>
        <%       
            for(Reported s : reportedQuestions){
                
    %>
    <tr>
    <td><%out.print(s.getQuestion());%></td>
    <td>
            <form action="StudyController" method="post">
                <input type='hidden' name=StudyCode value=<%out.println(s.getStudyID());%>/>
                <input type='hidden' name=QuestionID value=<%out.println(s.getQuestionID());%>/>
            <input type="submit" class="btn btn-primary" formaction="StudyController?action=approve" value="Approve">
            <input type="submit" class="btn btn-primary" formaction="StudyController?action=disapprove"  value="Dispprove">
            </form>
        
    </td></tr>
    <%
            
            }
        }
        
        
    %>
        
<!--        <tr>
            <%-- First study details --%>
            <td>I enjoy outdoor activities.</td>
            <td>
            <form action="request.jsp" method="post">
            <input type="submit" class="btn btn-primary" formaction="StudyController?action=approve" value="Approve">
            <input type="submit" class="btn btn-primary" formaction="StudyController?action=disapprove"  value="Dispprove">
            </form>
           </td>
            

        </tr>
        
         TODO Add one more for removal and not re 
        
        <tr> 
            <%-- Second study details --%>
            <td></td>
            <td></td>

        </tr>-->
        </table>
        </div>
  
  
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>