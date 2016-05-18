<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/atm.js" ></script>
</head>
<body>

<script type="text/javascript">
        $(document).ready(function(){
           topicSubs = '8832221';
           urlLocation = 'http://localhost:8081/ferme/account/';
           onData = function(data){
                $('#content').append("Message Received: " + data + "</br>");
           };
           appSubscriber();
        });
 
</script>

<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<div id="content"></div>

</body>
</html>
