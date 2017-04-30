<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%@page import="java.util.ArrayList"%>
<%@page import="assignment2.business.Study"%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to display items in List --%>
<nav id="menu">
    <ul>
        <li>Coins (<span class="count">${theUser.numCoins}</span>) </li>
        <li>Participants (<span class="count">${theUser.participants}</span>) </li>
        <li>Participation (<span class="count">${theUser.participation}</span>) </li>
        <li><br></li>
        <li><a href="home.jsp">Home</a></li>
        <li><a href="StudyController?action=participate">Participate</a></li>
        <li><a href="StudyController?action=studies">My Studies</a></li>
        <li><a href="recommend.jsp">Recommend</a></li>
        <li><a href="contact.jsp">Contact</a></li>
    </ul>
</nav>
<%-- Code to Display Question--%>
<section class="question_section">
    <h3><span class="label label-default" >Question</span></h3>
    <%-- Img tag to display image--%>
    
    <img src="images/${requestScope.study.imageURL}" class="img-responsive" height="250" width="250" alt="No Images"/>
    
<!--    <img src="images/small_tree.jpg" class="img-responsive" height="250" width="250" alt="Tree"/>-->

<%--Code to rating the Question --%>
<p class="text-left">${requestScope.study.question}</p>

        <form action="StudyController?action=answer">
            <%
                Study study = (Study) request.getAttribute("study");
                ArrayList<String> answers = study.getAnswerList();
                for (String ans: answers){
            %>   
        <div class="radio">
            <input type="radio" name="choice" value="<%out.print(ans);%>" required><%out.print(ans);%>
            </div>
        <%-- Code to submit the Rating  --%>
        <% } %>
        <input type="hidden" name="StudyCode" value="<%out.print(study.getStudyCode());%>">
        <input type="hidden" name="QuestionID" value="<%out.print(study.getQuestionID());%>">
         <div class="form-group">
        <div class="col-sm-offset-3 col-sm-4">
        <button name ="action" type="submit" value="answer" class="btn btn-primary">Submit</button>
         </div>
            </div>
            <br/><br/><br/>   
        </form>
        
    
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>