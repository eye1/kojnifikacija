<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %> 
<%@ page pageEncoding="UTF-8" %>

<t:head pageTitle="Login" login="true" />

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
			<c:when test="${param['msg'] eq 'password-sent'}">
				<h5 class="text-danger" style="line-height: 20px;">
					We sent your password to your email. Check it and sign in!
				</h5>
			</c:when>
			<c:otherwise>
				<h5>Please sign in to get rocking.</h5>
			</c:otherwise>
		</c:choose>

		<form id="login-form" action="login" class="form parsley-form" method="post">

			<div class="form-group">
				<label for="login-username">Email</label>
				<input type="text" class="form-control" id="login-username" placeholder="Email" data-required>
			</div>

			<div class="form-group">
				<label for="login-password">Password</label>
				<input type="password" class="form-control" id="login-password" placeholder="Password" data-required>
			</div>

			<div class="form-group">
				<button type="submit" id="login-btn" class="btn btn-primary btn-block">Signin &nbsp; <i class="fa fa-play-circle"></i></button>
			</div>

		</form>

		<a href="mailto:sumofcoins@gmail.com?Subject=shit, i forgot my pass :(" target="_top" class="btn btn-default">Forgot Password?</a>

	</div> <!-- /#login -->

	<a href="create-account" id="signup-btn" class="btn btn-lg btn-block">
		Create an Account
	</a>


</div> <!-- /#login-container -->

</body>
<t:foot />
</html>