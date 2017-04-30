<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%@page import="assignment2.business.Study"%>
<%@page import="java.util.ArrayList"%>
<%-- Include tag is used to import header page --%>
<%@include file="header.jsp" %>

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<%--Code to signup form --%>
<section>

    <div class="table-responsive">
    <table class="table" >
        <%if(request.getAttribute("studyshare")!=null)
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
                
                Study study = (Study) request.getAttribute("studyshare");
            %>
            <tr>
                <td><% out.print(study.getStudyName());%></td>
                <td><% out.print(study.getRequestedParticipants());%></td>                            
                <td><% out.print(study.getNumOfParticipants());%></td>
                <td>               
                    <form action="StudyController">
                    <input class="btn btn-primary" name="action" type="submit" value ="<% 
                    if((study.getStatus().equals("Start"))) {
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
                    <div class="fb-share-button" data-href="http://openshiftdemo-nbhirud.rhcloud.com/Assignment3/" data-layout="button" data-mobile-iframe="true"></div>
                    <!--
                    <br>
                    <a href="http://localhost:8084/NikhilBhirud_Assg4/StudyController?action=share&scode=<%out.print(study.getQuestionID());%>">link</a>
                    -->
                </td>
            </tr>
      
            <% }

else {
%>
<h1>No study found</h1>
<%

}
            %>
            
            
            
            
    </table>
    </div>
</section>
<%@ include file="footer.jsp" %>