<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %> 
<%@ page pageEncoding="UTF-8" %>

<t:head pageTitle="Create Account" login="true" />

<body>

<div id="login-container">

	<div id="logo">
		<a href="login">
			<img src="resources/img/logos/logo-login.png" alt="Logo" />
		</a>
	</div>

	<div id="login">

		<h3>Welcome to Sum of Coins.</h3>

		<c:choose>
			<c:when test="${param['msg'] eq 'email-exists'}">
				<h5 class="text-danger" style="line-height: 20px;">
					This email is already used. Maybe it was you :) 
				</h5>
			</c:when>
			<c:otherwise>
				<h5>Please fill in the fields and let's roll.</h5>
			</c:otherwise>
		</c:choose>
		

		<form id="login-form" action="create-account" class="form parsley-form" method="post">

			<div class="form-group">
				<label for="login-usCername">First Name</label>
				<input type="text" value="${param['name']}" name="name" class="form-control" id="login-name" placeholder="First Name" data-required>
			</div>

			<div class="form-group">
				<label for="login-username">Email</label>
				<input type="text" value="${param['email']}" name="email" class="form-control" id="login-username" placeholder="Email" data-required data-type="email">
			</div>

			<div class="form-group">
				<button type="submit" id="login-btn" class="btn btn-primary btn-block">Create account &nbsp; <i class="fa fa-play-circle"></i></button>
			</div>
		</form>
		
	</div> <!-- /#login -->

	<a href="login" id="signup-btn" class="btn btn-lg btn-block">
		Login 
	</a>


</div> <!-- /#login-container -->

</body>
<t:foot />
</html>