<%@tag pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %> 

<%@attribute name="activeMenu" required="true"%>
<%@attribute name="pageTitle" required="true"%>

<t:head pageTitle="${pageTitle}" />

<body>

<div id="wrapper">

	<header id="header">

		<h1 id="site-logo">
			<a href="dashboard">
				<img src="resources/img/logos/logo.png" alt="Site Logo" />
			</a>
		</h1>	

		<a href="javascript:;" data-toggle="collapse" data-target=".top-bar-collapse" id="top-bar-toggle" class="navbar-toggle collapsed">
			<i class="fa fa-cog"></i>
		</a>

		<a href="javascript:;" data-toggle="collapse" data-target=".sidebar-collapse" id="sidebar-toggle" class="navbar-toggle collapsed">
			<i class="fa fa-reorder"></i>
		</a>

	</header> <!-- header -->


	<nav id="top-bar" class="collapse top-bar-collapse">

		<ul class="nav navbar-nav pull-left">
			<li class="">
				<a href="dashboard">
					<i class="fa fa-home"></i> 
					Home
				</a>
			</li>
		</ul>

		<ul class="nav navbar-nav pull-right">
			<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
					<i class="fa fa-user"></i>
		        	Gregor Ambrozic 
		        	<span class="caret"></span>
		    	</a>

		    	<ul class="dropdown-menu" role="menu">
			        <li>
			        	<a href="./page-profile.html">
			        		<i class="fa fa-user"></i> 
			        		&nbsp;&nbsp;My Profile
			        	</a>
			        </li>
			        <li>
			        	<a href="./page-settings.html">
			        		<i class="fa fa-cogs"></i> 
			        		&nbsp;&nbsp;Settings
			        	</a>
			        </li>
			        <li class="divider"></li>
			        <li>
			        	<a href="./page-login.html">
			        		<i class="fa fa-sign-out"></i> 
			        		&nbsp;&nbsp;Logout
			        	</a>
			        </li>
		    	</ul>
		    </li>
		</ul>

	</nav> <!-- /#top-bar -->


	<div id="sidebar-wrapper" class="collapse sidebar-collapse">
	
		<div id="search">
			<form>
			</form>		
		</div> <!-- #search -->
	
		<nav id="sidebar">		
			
			<ul id="main-nav" class="open-active">			

				<li class="${activeMenu eq 'dashboard' ? 'active' : ''}">				
					<a href="dashboard">
						<i class="fa fa-dashboard"></i>
						Dashboard
					</a>				
				</li>
							
				<li class="${activeMenu eq 'exchanges' ? 'active' : ''}">
					<a href="exchanges">
						<i class="fa fa-bar-chart-o"></i>
						Exchanges
					</a>
				</li>
				
				<li class="${activeMenu eq 'wallets' ? 'active' : ''}">
					<a href="wallets">
						<i class="fa fa-money"></i>
						Wallets
					</a>
				</li>

			</ul>
					
		</nav> <!-- #sidebar -->

	</div> <!-- /#sidebar-wrapper -->

	
	<div id="content">		
		
		<div id="content-header">
			<h1>${pageTitle}</h1>
		</div> <!-- #content-header -->	


		<div id="content-container">