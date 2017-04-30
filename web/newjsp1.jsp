<%-- 
    Document   : newjsp1
    Created on : Apr 14, 2016, 8:52:03 PM
    Author     : Sony
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '1239090236118408',
      xfbml      : true,
      version    : 'v2.6'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script>
        <h1>Hello World!</h1>
        <div
  class="fb-like"
  data-share="true"
  data-width="450"
  data-show-faces="true">
</div>
    </body>
</html>
