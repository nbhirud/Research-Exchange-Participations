<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to display items in List --%>
<p>${msg}</p>
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
<%-- Section to display studies and participate in that study--%>
<div>
   
     <h3 class="text-left"><span class="label label-default ">Studies</span>
     <span ><a class="label label-default" href='StudyController?action=report'>Report History</a></span></h3>
     </div>
     
    <%-- Display the studies in the table --%>
    <%-- Clicking on Participate button displays Question.jsp page where 
            you can rate the question--%>
     <div class="table-responsive">
    <table>
        <c:if test="${not empty allStudies}">
        <%--Column Names --%>
        <tr>
            <th>Study Name</th>
            <th>Image</th>      
            <th>Question</th>
            <th>Action</th>
            <th>Report</th>
        </tr>
        <c:forEach var="asd" items="${requestScope.allStudies}">
                         <tr>               
                            <td><c:out value="${asd.studyName}"/></td>
                            <td>
                                <img alt="tree" src="images/${asd.imageURL}"/> 
                            </td>
                            <td><c:out value="${asd.question}"/></td>
                            <td>
                                <form action='StudyController?action=participate'>
                                <input class="participate" type="submit" name="action" value="Participate"/>
                                <input type='hidden' name='studyCode' value="<c:out value="${asd.studyCode}"/>">
                                </form>
                            </td>
                            <td>
                                <form action='StudyController?action=report'>
                                <input class="participate" type="submit" name="action" value="Report" />                    
                                <input type='hidden' name='studyCode' value="<c:out value="${asd.studyCode}"/>">
                                <input type='hidden' name='ReporterEmail' value="<c:out value="${theUser.email}"/>">
                                <input type='hidden' name='QuestionID' value="<c:out value="${asd.questionID}"/>">
                                </form>
                            </td>
                        </tr>           
                        <tr>           
                            <td colspan="5"><input type="hidden"  name="studyCode" value="<c:out value="${asd.studyCode}"/>"></td>
                        </tr>
                       </c:forEach>      
                 </c:if>
                 <c:if test="${empty allStudies}">
                            <h3> no entries found </h3>
                 </c:if>
    </table>
    </div>

<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>