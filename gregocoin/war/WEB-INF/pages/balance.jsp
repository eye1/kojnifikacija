<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %> 

<html>
<head>
<title>Sum of Coins</title>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

	<div class="row" style="margin-top: 50px;  margin-left: 0px; margin-right: 0px;">

		<div class="col-md-1"></div>

		<div class="col-md-10">

			<c:if test="${empty btc and empty problem}">
				<div class="jumbotron">
					<h1>Hello there!</h1>
					<p>
						If you are like me and have a bunch of different altcoins, you
						probably wonder from time to time: "How much are
						my coins worth?" <br> <br> I mean, I can see numbers
						going up and down on exchanges, but how much in cash is that? <br> <br> 
						Well, you've come to the right place. And no, you don't need to create an account.
					</p>
				</div>
				
				Currently, only the BTC-e exchange is supported. What you need is BTC-e API key, which you can get if you log in, go to "Profile" and then select "API keys".
				<br><br>
				Then you create it and make sure that you select the "info" checkbox. Please, don't add the "trade" option, just to make sure nobody messes with your coins. 
				<br><br>
				When you have the "key" and "secret" just paste them into this tool. We will do some tiny magic after that.
				<br><br>
				Currently, we're in beta testing so everything may not function 100%. Contact us if you have any comments or suggestions. 
				<br><br>
			</c:if>
			
			<c:if test="${not empty problem}">
				<div class="jumbotron">
					<p>
						Your API key and secret don't seem alright. 
						Make sure it is a valid BTC-e key and it has "info" permission. <br> <br>
					</p>
				</div>
			</c:if>			

			<div style="text-align: center; word-wrap: break-word;">

				<c:if test="${empty btc}">
					<t:keySecretForm />
				</c:if>

				<c:if test="${not empty btc}">
					<h3>And here's your balance...</h3>
					<br>
					
					<div>
					
						<table class="table" style="width: 700px;">
							<tr>
								<th></th>
								<th align="center">quantity</th>
								<th align="center">rate per BTC</th>
								<th align="center">in BTC</th>
								<th align="center">share</th>
							<tr>
								<c:forEach items="${coins}" var="coin">
									<tr>
										<td align="center">${fn:toUpperCase(coin)}</td>
										<td align="center"><fmt:formatNumber
												value="${balance[coin]}" minFractionDigits="5"
												maxFractionDigits="5" /></td>
										<td align="center"><fmt:formatNumber
												value="${rates[coin]}" minFractionDigits="5"
												maxFractionDigits="5" /></td>
										<td align="center"><fmt:formatNumber value="${btc[coin]}"
												minFractionDigits="5" maxFractionDigits="5" /></td>
										<td align="center"><fmt:formatNumber
												value="${btc[coin]/totalBtc*100}" minFractionDigits="0"
												maxFractionDigits="0" /> %</td>
									<tr>
								</c:forEach>
						</table>
						
						<div id="piechart" style="position: absolute; top: 80px; left: 718px; width: 400px; height: 300px;"></div>

					</div>

					<br>
	
					All together that is 
					<strong><fmt:formatNumber value="${totalBtc}"
							minFractionDigits="2" maxFractionDigits="2" /> BTC</strong>. <br>

					<br>
					
					If you sell it on BTC-e you can get <br>
					<strong><fmt:formatNumber value="${totalUsdBtce}"
							minFractionDigits="2" maxFractionDigits="2" /> USD</strong>
					<small>or</small>
					<strong><fmt:formatNumber value="${totalEurBtce}"
							minFractionDigits="2" maxFractionDigits="2" /> EUR</strong>
					<br>
					<br>
					
					You could also sell it on Bitstamp for <br>
					<strong><fmt:formatNumber value="${totalUsdBitstamp}"
							minFractionDigits="2" maxFractionDigits="2" /> USD</strong>
					<small>or</small>
					<strong><fmt:formatNumber value="${totalEurBitstamp}"
							minFractionDigits="2" maxFractionDigits="2" /> EUR</strong>.
							
					<br>
					<br>
					
					MtGox on the other hand offers<br>
					<strong><fmt:formatNumber value="${totalUsdMtgox}"
							minFractionDigits="2" maxFractionDigits="2" /> USD</strong>.
							
					<br>
					<br>					
							
					<button type="button" class="btn btn-info" onclick="goBack();">Let's do another one</button>							
					
					<div style="margin-top: 75px; text-align: center;">
						If you find the tool useful and would like to help expand it, you can make a donation to my Bitcoin wallet. <br> 
						The plan is to cover all major exchanges, save data through time, add analytic tools and make the tool as easy to use as possible. <br>
						<img
							width="160" alt="donate" src="/resources/img/donate.png"> <br>
						<small>or</small> <br> 16MAoY6ZTmqPyrCFzqCmCeUULi7bbFi16K <br>
						<br> Thank you!
					</div>

				</c:if>

			</div>

		
			<div align="center" style="margin-top: 50px;">
				For comments, questions and suggestions, please send us an email to 
				<a href="mailto:sumofcoins@gmail.com?Subject=hey" target="_top">sumofcoins@gmail.com</a>.
			</div>

		</div>

		<div class="col-md-1"></div>

	</div>

</body>

<script src="https://code.jquery.com/jquery.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
<script src="https://www.google.com/jsapi"></script>

<script type="text/javascript">
	
	function getBalance()
	{
		document.location='${pageContext.request.contextPath}/?key=' + $('#input-key').val() + '&secret=' + $('#input-secret').val();
	}
	
	function goBack()
	{
		document.location='${pageContext.request.contextPath}/';
	}	
	
</script>

<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "corechart" ]
	});
	google.setOnLoadCallback(drawChart);
	function drawChart() {
		var data = google.visualization.arrayToDataTable([
				[ 'Coin', 'Share' ],
				
				<c:forEach items="${coins}" var="coin" varStatus="status">
					[ '${fn:toUpperCase(coin)}', ${btc[coin]} ]
					<c:if test="${not status.last}">, </c:if>
				</c:forEach>

				]);

		var options = {
			title : 'Coin Shares'
		};

		var chart = new google.visualization.PieChart(document.getElementById('piechart'));
		chart.draw(data, options);
	}
</script>

</html>