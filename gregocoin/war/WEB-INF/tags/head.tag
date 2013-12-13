<%@tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<%@attribute name="pageTitle" required="true"%>
<%@attribute name="login" required="false"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
    <title>${pageTitle} - Sum of Coins</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="">
<meta name="author" content="" />

<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,800italic,400,600,800" type="text/css">

<link rel="stylesheet" href="resources/css/font-awesome.min.css" type="text/css" />		
<link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css" />	
<link rel="stylesheet" href="resources/js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.css" type="text/css" />
	
<link rel="stylesheet" href="resources/js/plugins/icheck/skins/minimal/blue.css" type="text/css" />
<link rel="stylesheet" href="resources/js/plugins/select2/select2.css" type="text/css" />
<link rel="stylesheet" href="resources/js/plugins/fullcalendar/fullcalendar.css" type="text/css" />

<link rel="stylesheet" href="resources/css/App.css" type="text/css" />
<link rel="stylesheet" href="resources/css/custom.css" type="text/css" />

<c:if test="${login}">
	<link rel="stylesheet" href="resources/css/Login.css" type="text/css" />
</c:if>
	
</head>