<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%@page import="assignment2.business.Study"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>


<%-- Code to display Page Name --%>
<h3 id="page_name">My Studies</h3>
 <%-- Code to add new study   --%>
<h3 id="add_new_study"><a href="newstudy.jsp" >Add a new study</a></h3>
 <%-- Code to go Back to the Main Page  --%>
<a href="main.jsp" id="back_to_page">&laquo;Back to the Main Page</a>
<%-- Section to display studies details --%> 
<%-- Clicking on Start, Stop to Participate in that study and  Edit button to display edit page and edit study details in it--%>
<section >

<div class="table-responsive">
    <table class="table" >
        <%if(request.getAttribute("userStudies")!=null)
        {
        %>
            <tr>
            <th>Study Name</th>
            <th>Requested Participants</th>     
            <th>Participations</th>
            <th>Status</th>
            <th>Action</th>
            <th>Social Share</th>
            </tr>
            <%
                ArrayList<Study> studyList = (ArrayList<Study>) request.getAttribute("userStudies");
                for(Study study : studyList){
                    String attIDname = "attr"+study.getStudyCode();
                    session.setAttribute(attIDname, study.getStudyCode());
            %>
            <tr>
                <td><% out.print(study.getStudyName());%></td>
                <td><% out.print(study.getRequestedParticipants());%></td>                            
                <td><% out.print(study.getNumOfParticipants());%></td>
                <td>               
                    <form action="StudyController">
                    <input class="btn btn-primary" name="action" type="submit" value ="<% 
                    if((study.getStatus().equals("Start")))
                    { 
                        out.print("Stop");
                    }
                    else {
                        out.print("Start");
                    }
               %>"/>
                    <input type="hidden" name="StudyCode" value="<%out.print(study.getStudyCode());%>">
                    </form>
                </td>
                <td>
                    <form action="StudyController">
                        <input type="hidden" name="StudyCode" value="<%out.print(study.getStudyCode());%>">
                        <input name="action" type="submit" class="btn btn-primary" value="Edit"> 
                    </form>
                </td>
                <td>
                    <div class="fb-share-button" data-href="http://openshiftdemo-nbhirud.rhcloud.com/NikhilBhirud_Assg4/StudyController?action=share&scode=<%out.print(study.getStudyCode());%>" data-layout="button" data-mobile-iframe="true"></div>
                    <br>
                    <!--
                    <%--
                    http://localhost:8084/NikhilBhirud_Assg4/StudyController?action=share&scode=11
                    <a href="http://openshiftdemo-nbhirud.rhcloud.com/NikhilBhirud_Assg4/StudyController?action=share&scode=<%out.print(study.getStudyCode());%>">link</a>
                    --%>                    
                    -->
                </td>
            </tr>
      
            <% } %>
            <% }else
                {
                %>      
                <h3> No entries found </h3>
                <% }%>
    </table>
</div>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>