<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%@page import="assignment2.business.Study"%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<script type="text/javascript" src="js/editstudy.js">
</script>
<%-- Code to display Page Name --%>
<h3 id="page_name">Editing a study</h3>
<%-- Code to go back to Main page  --%>
<a href="main.jsp" id="back_to_page">&laquo;Back to the Main Page</a>
<%-- Section to input study details --%>
<section>
    <form class="form-horizontal" action="StudyController?action=update&StudyCode=${requestScope.study.getStudyCode()}" method="POST" enctype="multipart/form-data">
    	<input type="hidden" id ="answers" value="${requestScope.study.getAnswerList()}"/>
        <div class="form-group">
        <label class="col-sm-4 control-label">Study Name *</label>
        <div class="col-sm-4">
        <input type="text" class="form-control" name="study_name" required value="${requestScope.study.studyName}" />
         </div>
            </div>
        
        <div class="form-group">
        <label class="col-sm-4 control-label">Question Text *</label>
        <div class="col-sm-4">
        <input type="text" class="form-control" name="question_text" required value="${requestScope.study.question}"/>
         </div>
            </div>
        
        
        <%-- Img tag is used to import image --%>
        <div class="form-group">
        <label class="col-sm-4 control-label">Image *</label>
        <div class="col-sm-4">
<!--        <img src="${requestScope.study.imageURL}" class="img-responsive" height="50" width="75" alt="Edit"/>-->
        <input type="file" name="imageURL" accept ="image/*"  required>
         </div>
            </div>
        
        
        <div class="form-group">
        <label class="col-sm-4 control-label"># Participants *</label>
         <div class="col-sm-4"> 
        <input type="text" class="form-control" name="participants" required value="${requestScope.study.requestedParticipants}"/>
         </div>
            </div>
        
        <div class="form-group">
        <label class="col-sm-4 control-label"># Answers *</label>
        <div class="col-sm-4">
            <% Study study = (Study)request.getAttribute("study");
               String size = String.valueOf(study.getAnswerList().size());
               String[] answers = study.getAnswerList().toArray(new String[study.getAnswerList().size()]);
            %>
        <select class="form-control" id="edit_study_answers">
            <option value="3" <% if(size.equals("3")) out.print("selected");%>>3</option>
            <option value="4" <% if(size.equals("4")) out.print("selected");%>>4</option>
            <option value="5" <% if(size.equals("5")) out.print("selected");%>>5</option>
        </select> 
         </div>
            </div>
        
        
        <div id="TextBoxContainer1">
    <!--Textboxes will be added here -->
		</div>
       
       
       <div class="form-group">
        <label class="col-sm-4 control-label">Description *</label>
         <div class="col-sm-4"> 
        <textarea name="description" class="form-control" required ><%out.print(study.getDescription());%></textarea>
         </div>
            </div>
        
        <div class="form-group">
        <div class="col-sm-offset-5 col-sm-4">
        <button type="submit"  class="btn btn-primary">Update</button>
         </div>
            </div>
            <br/><br/><br/>
    </form>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>