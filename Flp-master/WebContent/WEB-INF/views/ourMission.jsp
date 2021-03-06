<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">
<title>iPerform Konnect</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.css" type="text/css" media="screen">
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Abel" />
<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/custom-home.css" rel="stylesheet">
<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="${pageContext.request.contextPath}/resources/js/ie-emulation-modes-warning.js"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/css/carousel.css" rel="stylesheet">

</head>
<body>
<!-- code for forgot password -->
<div class="wrapper3">
<%--   <%@ include file="userRegistration.jsp" %> --%>
<form class="form-signin" action="" method="post">
<div class="close">
<i class="fa fa-times" onClick="closeLogin()"></i>
</div>
<h2 class="form-signin-heading">Forgot Password</h2>
<input type="text" name="username" placeholder="Enter Email-Id"
required class="loginCls form-control" id="forgotEmailId" /> <span
id="errorMsg3" style="color: red;"></span> <span id="successMsg3"
style="color: green;"></span> <a href="javascript:void(0);"
class="btn btn-lg btn-primary btn-block" onclick="forgotPassword()">Forgot Password</a> <br />

		</form>

	</div>
	<!-- end -->

	<!-- code for reset password -->
	<!-- code for forgot password -->
	<div class="wrapper4">

		<%--   <%@ include file="userRegistration.jsp" %> --%>
		<form class="form-signin" action="" method="post">

			<div class="close">
				<i class="fa fa-times" onClick="closeLogin()"></i>
			</div>


			<h2 class="form-signin-heading">Reset Password</h2>
			<input type="hidden" id="huserId" /> <input type="password"
				name="password" placeholder="Enter password" required
				class="loginCls form-control" id="passwordId1" /> <input
				type="password" name="password1" placeholder="confirm Password"
				required class="loginCls form-control" id="passwordId2" /> <span
				id="errorMsg4" style="color: red;"></span> <span id="successMsg4"
				style="color: green;"></span> <a href="javascript:void(0);"
				class="btn btn-lg btn-primary btn-block" onclick="reserPassword()">Reset
				Password</a> <br />

		</form>

	</div>
	<!-- end -->

	<div class="wrapper">

		<%--   <%@ include file="userRegistration.jsp" %> --%>
		<form class="form-signin"
			action="${pageContext.request.contextPath}/userlogin.htm"
			method="post">

			<div class="close">
				<i class="fa fa-times" onClick="closeLogin()"></i>
			</div>


			<h2 class="form-signin-heading">Please login</h2>
			<input type="text" name="username" placeholder="username" required
				class="loginCls form-control" /> <input type="password"
				name="password" placeholder="Password" required
				class="loginCls form-control" /> <span id="errorMsg1"
				style="color: red;"></span> <span id="successMsg1"
				style="color: green;"></span>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
			<div class="col-md-6" style="padding-left:0px;">
<button id="googleCustomBtnSignup" class="btn-google-fb">
<i class="fa fa-facebook"></i>
Login with Facebook 
</button>
</div>
			
			<div class="col-md-6" style="padding-left:0px;">
<button id="googleCustomBtnSignup" class="btn-google-plus">
<i class="fa fa-google-plus"></i>
Login with Google
</button>
</div>
			<br />
			<div align="right" style="padding-right:31px;padding-top:7px; float:left;width:100%;">
				<a href="javascript:void(0)" onclick="forgotPwd()">forgot
					password?</a>
			</div>
		</form>

	</div>

	<div class="wrapper1">


		<form
			action="${pageContext.request.contextPath}/user/retailUserOrParentSignup.htm"
			method="post" id="userSignUpId" class="form-signin" role="form">
			<div class="close">
				<i class="fa fa-times" onClick="closeLogin()"></i>
			</div>


			<h2 class="form-signin-heading">User Sign up</h2>

			<select name="userType" required="required"
				class="loginCls form-control userTypeCls" id="userTypeId">
				<option value="">Select UserType</option>
				<option value="4">Student</option>
				<option value="3">Parent</option>
				
			</select> <input type="text" name="firstName" placeholder="Enter Your Name"
				required class="loginCls form-control" /> <input type="email"
				name="emailId" placeholder="Enter your EmailId" required
				class="loginCls form-control" /> <input type="password"
				name="password" placeholder="Password" required
				class="loginCls form-control" /> <input type="tel" name="mobileNo"
				placeholder="Mobile Number" class="loginCls form-control"
				pattern="[0-9]{10,11}" maxlength="10" />
				<span id="showHideBoardGrade" style="display: none;">
				 <select name="boardId" id="boardId"
			 class="loginCls form-control"
				onchange="getGradeList('#gradeId', $(this).val(), '${pageContext.request.contextPath}/getGradeByBoardId', 'Please Select Grade');">
				<option value="">Select Board</option>
				<c:if test="${not empty boardList }">
					<c:forEach var="board" items="${boardList }">
						<c:if test="${board.id ne 1 }">
							<option value="${board.id }">${board.name }</option>
						</c:if>
					</c:forEach>

				</c:if>
			</select> <select name="gradeId" 
				class="loginCls form-control" id="gradeId">
				<option value="">Select Grade</option>
			</select> 
			</span>
			<span id="errorMsg2" style="color: red;">
			</span> <span
				id="successMsg" style="color: green;"></span>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign Up</button>
			<div class="col-md-6" style="padding-left:0px;">
<button id="googleCustomBtnSignup" class="btn-google-fb">
<i class="fa fa-facebook"></i>
Sign Up with Facebook 
</button>
</div>
			
			<div class="col-md-6" style="padding-left:0px;">
<button id="googleCustomBtnSignup" class="btn-google-plus">
<i class="fa fa-google-plus"></i>
Sign Up with Google
</button>
</div>
		</form>

	</div>

	<!--top-black-->
	<div class="row-fluid">
		<%-- <div class="col-md-3 col-xs-12 logo pull-left"><a href="index.html">
<img src="${pageContext.request.contextPath}/resources/images/logo-home.png" alt="iPerform Konnect" title="iPerform Konnect" /></a>
 </div> --%>
<div class="top-blk">
<div class="container">
<div class="row">
<ul class="pull-left"><li><img src="${pageContext.request.contextPath}/resources/images/mail-icon.png" alt="" /><span><a href="javascript:void(0);">communications@classteacher.com</a></span></li>
 <li><img src="${pageContext.request.contextPath}/resources/images/phone-icon.png" alt="" /><span>Toll Free: 1800-103-6768</span></li>
 </ul>
<ul class="pull-right">
<li><span> Get social with us!</span></li>
<li><img src="${pageContext.request.contextPath}/resources/images/twitter-icon.png" alt="" /></li>
 <li><img src="${pageContext.request.contextPath}/resources/images/fb-icon.png" alt="" /></li>
 <li><img src="${pageContext.request.contextPath}/resources/images/linked-in.png" alt="" /></li>
 <li><img src="${pageContext.request.contextPath}/resources/images/g-plus.png" alt="" /></li>
 </ul>
 </div>
</div>
</div>
		
<div class="col-md-12 col-sm-12 col-xs-12 ">
<!-- Navigation  -->
<div class="row">

<nav class="navbar navbar-default">
<div class="container">
<div class="navbar-header">
<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar ">
<span class="sr-only">Toggle navigation</span> 
<span class="icon-bar"></span> <span class="icon-bar"></span> 
<span class="icon-bar"></span>
</button>
<a class="navbar-brand" href="#"> <img src="${pageContext.request.contextPath}/resources/images/logo-home.png" alt="logo" class="img-responsive" />
</a>
</div>
<div id="navbar" class="navbar-collapse collapse pull-right" style="margin-top: 18px; width:50%;">
<ul class="nav navbar-nav" style="float:right">
<c:if test="${not empty parentReqList }">
<li class="dropdown nav-br-top">
<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-globe"></i> <b class="caret"></b> 
<span class="badge badge-notify" id="notifyCount">${parentReqList.size() }</span> </a>
<ul class="dropdown-menu panel-body2">
<c:forEach var="parentReq" items="${parentReqList }" varStatus="s">
<li class="message-preview" id="msgPreview${s.count }">
<a href="#">
<div class="media">
<span class="pull-left"><img class="media-object" src="${pageContext.request.contextPath}/resources/images/parent.jpg"
alt="" style="width: 50px;"> </span>
<div class="media-body">
<h5 class="media-heading">
<strong>${parentReq.user.firstName }</strong>
</h5>
<p>Do we need to bring the Lab tests tomorrow?</p>
<div class="btn btn-success btn-xs acceptParentCls" id="acceptId${s.count }" dir="${parentReq.id }">Accept</div>
<div class="btn btn-danger btn-xs rejectParentCls" id="rejectId${s.count }" dir="${parentReq.id }">Reject</div>
</div>
</div> </a>
</li>
</c:forEach>
</ul></li>
</c:if>

</ul>
<ul class="nav navbar-nav">
<c:choose>
<c:when test="${sessionScope.user.userType eq 2 && not empty sessionScope.user.schoolId }">
<li id="dashboardId"><a href="${pageContext.request.contextPath}/student/studentHome.htm">
														HOME </a></li>
<li class="dropdown" id="subjectId"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Subject
</a> <c:if test="${not empty sessionScope.user}">
<ul class="dropdown-menu">
<c:forEach items="${sessionScope.user.subjects}" var="subject"> <li>
<a href="${pageContext.request.contextPath}/student/subjectDetail.htm?sid=${subject.id}">
<i class="fa fa-book"></i> ${subject.name}</a></li>
</c:forEach>
</ul>
</c:if></li>
<li id="reportsId" class="dropdown"><a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> <%-- <span> <img src="${pageContext.request.contextPath}/resources/images/reports-new.png" alt="logo" style="max-width:100%;"/> 
</span> --%>Reports</a>
<ul class="dropdown-menu">
<li><a href="${pageContext.request.contextPath}/student/testReports.htm">Assessment Reports</a></li>
<li><a href="${pageContext.request.contextPath}/student/learningReports.htm">Learning Reports</a></li>
</ul></li>
</c:when>
<c:otherwise>
<li id="dashboardId"><a href="${pageContext.request.contextPath}/user/retailUserHome.htm">
HOME </a></li>
<li id="reportsId" class="dropdown"><a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> 
ABOUT US
</a>
<ul class="dropdown-menu">
<li><a href="${pageContext.request.contextPath}/ourVision.htm">OUR VISION</a></li>
<li><a href="${pageContext.request.contextPath}/ourMission.htm">OUR MISSION</a></li>
</ul></li>
<li id="dashboardId"><a href="${pageContext.request.contextPath}/user/retailUserHome.htm">
FEATURE </a></li>
</c:otherwise>
</c:choose>
<li><div class="login-res">
					<button type="button" onClick="loginDiv()"
						class="btn btn-primary login ownbt pull-left">Log In</button>
					<button type="button" onClick="signUpDiv()"
						class="btn btn-danger registration ownbt pull-right">Sign
						Up</button>
				</div></li>
</ul>
</div>
</div>


</nav>
</div>
<!-- Navigation end -->
</div>
</div>
	<!--top-black-->

	<div class="clearfix"></div>


	<!--social-media-->


	<div class="container" style="position: relative;">

		<div class="row">



			<%-- <div class="social-media pull-right text-right">

				<ul>
					<li><a href="#"><img
							src="${pageContext.request.contextPath}/resources/images/fb.png"
							alt="" /></a></li>
					<li><a href="#"><img
							src="${pageContext.request.contextPath}/resources/images/gplus.png"
							alt="" /></a></li>
					<li><a href="#"><img
							src="${pageContext.request.contextPath}/resources/images/gal.png"
							alt="" /></a></li>
					<li><a href="#"><img
							src="${pageContext.request.contextPath}/resources/images/printrest.png"
							alt="" /></a></li>
					<li><a href="#"><img
							src="${pageContext.request.contextPath}/resources/images/twitter.png"
							alt="" /></a></li>
					<li><a href="#"><img
							src="${pageContext.request.contextPath}/resources/images/res-btn.png"
							alt="" /></a></li>
				</ul>

			</div> --%>


		</div>

	</div>
	<!--social-media-->



	<!-- Carousel
    ================================================== -->
	<div id="myCarousel" class="carousel slide" data-ride="carousel">


		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>

		</ol>

		<div class="carousel-inner" role="listbox">

			<!--login-res-->
			<div class="container login-main">

				<div class="freetrial col-md-6">

					<div class="row">

					<!-- 	<div class="btn-group" role="group" aria-label="...">


							<div class="btn-group" role="group">
								<button type="button"
									class="btn btn-default dropbtn dropdown-toggle"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									Select Board <span class="caret"></span>
								</button>
								<ul class="dropdown-menu trial-list">
									<li><a href="#">CBSE</a></li>
									<li><a href="#">ICSE</a></li>
								</ul>
							</div>
							<div class="btn-group " role="group">
								<button type="button"
									class="btn btn-default dropbtn dropdown-toggle"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									Select Grade <span class="caret"></span>
								</button>
								<ul class="dropdown-menu trial-list">
									<li><a href="#">Grade-01</a></li>
									<li><a href="#">Grade-02</a></li>
									<li><a href="#">Grade-03</a></li>
									<li><a href="#">Grade-04</a></li>
									<li><a href="#">Grade-05</a></li>
									<li><a href="#">Grade-06</a></li>
									<li><a href="#">Grade-07</a></li>
									<li><a href="#">Grade-08</a></li>
									<li><a href="#">Grade-09</a></li>
									<li><a href="#">Grade-10</a></li>

								</ul>
							</div>
							<button type="button" class="btn btn-default freebtn">Free
								Trial</button>


						</div> -->
					</div>


				</div>

				<!-- <div class="login-res">

					<button type="button" onClick="loginDiv()"
						class="btn btn-primary login ownbt pull-left">Log In</button>
					<button type="button" onClick="signUpDiv()"
						class="btn btn-danger registration ownbt pull-right">Sign
						Up</button>


				</div> -->

			</div>
			<!--login-res-->


			<div class="item active">
				<img class="first-slide"
					src="${pageContext.request.contextPath}/resources/images/slide1.jpg"
					alt="First slide">

				<div class="container">

					<div class="carousel-caption option-bg col-md-5 col-sm-6 col-xs-12">

						<h1>
							<span>An easy way of Responsive learning</span>
						</h1>
						<p>
							Compatible with  all Devices
						</p>

					</div>


				</div>
			</div>

			<div class="item">
				<img class="second-slide"
					src="${pageContext.request.contextPath}/resources/images/slide2.jpg"
					alt="First slide">

				<div class="container">

					<div
						class="carousel-caption  option-bg col-md-5 col-sm-6 col-xs-12">

						<h1>
							<span> Technology driven  learning</span>
						</h1>
						<p>
							Signup, learn and Score the Marks &nbsp<i class="fa fa-line-chart" aria-hidden="true"></i>
							
						</p>

					</div>


				</div>
			</div>
			
			
<div class="item">
				<img class="second-slide"
					src="${pageContext.request.contextPath}/resources/images/slide3.jpg"
					alt="First slide">

				<div class="container">

					<div
						class="carousel-caption  option-bg col-md-5 col-sm-6 col-xs-12">

						<h1>
							<span> An unique platform for Teachers and Students</span>
						</h1>
						<p>
							Learning through Assessments
							
						</p>

					</div>


				</div>
			</div>
			
		</div>
		<!-- /.carousel -->
	</div>
	<div style="border-bottom: 12px solid #36BDB4; width: 100%;"></div>


	<div></div>


	<div class="clearfix"></div>

	<!-- MAIN TEXT -->
	<div class="main-text">

		<p>
			<span class="red-txt">iPerformKonnect</span> is a <span
				class="gry-txt">Cloud based</span>, Multi platform, Device
			independent learning zone creating customized learning, based on the
			ability of an <span class="blu-txt">individual student.</span>
		</p>


	</div>
	<!-- MAIN TEXT -->

	<div class="col-md-4 pull-left"></div>

	<div class="col-md-4 col-sm-12 col-xs-12 how-add pull-left">
		<h2>How Do You Learn?</h2>
	</div>


	<div class="col-md-4  pull-left"></div>

	<div class="clearfix"></div>

	<!-- HOW DO WE LEARN -->

	<div class="container marketing">

		<div class="row">


			<div
				class="col-md-3 col-sm-6 col-xs-6 text-center select-topic ">

				<img class="img-circle"
					src="${pageContext.request.contextPath}/resources/images/1.png"
					alt="">

				<h3>
					Select Your<br>Topic
				</h3>
				<p>
					Every subject has a huge variety<br> of topics
				</p>

			</div>


			<div class="col-md-3 col-sm-6 col-xs-6 text-center select-topic">
				<img class="img-circle"
					src="${pageContext.request.contextPath}/resources/images/2.png"
					alt="">
				<h3>
					Start Your<br>Assessment
				</h3>
				<p>
					Access to our system generated<br> and teacher created
					assessments
				</p>

			</div>



			<div class="col-md-3 col-sm-6 col-xs-6 text-center select-topic">
				<img class="img-circle"
					src="${pageContext.request.contextPath}/resources/images/4.png"
					alt="">
				<h3>
					Go Through Learning <br> Content
				</h3>
				<p>
					Choose from our exclusive online<br> library with over 50,000
					modules
				</p>

			</div>



			<div
				class="col-md-3 col-sm-6 col-xs-6 text-center select-topic select-border">
				<img class="img-circle"
					src="${pageContext.request.contextPath}/resources/images/3.png"
					alt="">
				<h3>
					Get Instant<br>Report
				</h3>
				<p>
					Instant analytical report can be<br> generated by everyone
				</p>

			</div>


		</div>
	</div>
	<!-- HOW DO WE LEARN -->



	<!-- BLUE BG START -->
	<div class="blue-bg">


		<div class="container">


			<div class="col-md-4 pull-left"></div>

			<div class="col-md-4 col-sm-12 col-xs-12 getstart pull-left">
				<h2>
					User Account - <span>Get Started</span>
				</h2>
			</div>

			<div class="col-md-4  pull-left"></div>

			<div class="clearfix"></div>



			<div class="row">

	<img src="${pageContext.request.contextPath}/resources/images/feature.png" class="pull-left" alt="" title="" style="max-width: 100% !important;"/>

			</div>



		</div>


	</div>
	<!-- BLUE BG END -->


	<div class="clearfix"></div>


	<!--Features-start-here-->
	<%-- <div class="container">

		<div class="col-md-8 features">
			`
			<h2>Features</h2>

			<div class="col-md-6 feature-inner">

				<img
					src="${pageContext.request.contextPath}/resources/images/cloud-icon.png"
					class="pull-left" title="" alt="" />

				<div class="inner-text">
					<h3>Cloud-based product</h3>
					<p>
						Anybody with our assigned username<br> and password can use
						it.<br> <span><a href="javascript:void(0);">&nbsp;&nbsp;</a></span>
					</p>
				</div>

			</div>


			<div class="col-md-6 feature-inner">

				<img
					src="${pageContext.request.contextPath}/resources/images/assessment.png"
					class="pull-left" title="" alt="" />

				<div class="inner-text">
					<h3>Multi User Platform</h3>
					<p>
						Multiple Users (Student,Parent,Teacher)<br/> can used the same platform <br/>in a customized way. 
					</p>
				</div>

			</div>


			<div class="col-md-6 feature-inner">

				<img
					src="${pageContext.request.contextPath}/resources/images/driven.png"
					class="pull-left" title="" alt="" />

				<div class="inner-text">
					<h3>Assessment driven learning</h3>
					<p>
						Assessment driven learning: Learning<br> 
						through practice.<br> <!-- <span><a href="#">Read More</a></span> -->
					</p>
				</div>

			</div>


			<div class="col-md-6 feature-inner">

				<img
					src="${pageContext.request.contextPath}/resources/images/analytic.png"
					class="pull-left" title="" alt="" />

				<div class="inner-text">
					<h3>Built in analytics</h3>
					<p>
						We are offering guided learning based<br> on Spaced which is
						a technique.<br> <!-- <span><a href="#">Read More</a></span> -->
					</p>
				</div>


			</div>


			<div class="col-md-6 feature-inner">

				<img
					src="${pageContext.request.contextPath}/resources/images/reports.png"
					class="pull-left" title="" alt="" />

				<div class="inner-text">
				<h3>Analytics based reports</h3>
					<p>
						Reports: Teacher and Parents can get<br> the reports to
						monitor the performances.<br> <!-- <span><a href="#">Read More</a></span> -->
					</p>
				</div>

			</div>


			<div class="col-md-6 feature-inner">

				<img
					src="${pageContext.request.contextPath}/resources/images/system-icon.png"
					class="pull-left" title="" alt="" />

				<div class="inner-text">
					<h3>Device independent</h3>
					<p>
						Cloud based hosting of product is what<br> makes its immense
						presence.<br> <!-- <span><a href="#">Read More</a></span> -->
					</p>
				</div>


			</div>


		</div>

		<div class="clearboth"></div>

		<div class="col-md-1"></div>

		<div class="clearboth"></div>

		<!--Testimonial-start-here-->
		<div class="col-md-3 testimonial">

			<h2>Testimonial</h2>
			<div id="NewsSlider" class="carousel slide" data-ride="carousel">


				<!-- Indicators -->
				<ol class="news-indicators carousel-indicators">
					<li data-target="#NewsSlider" data-slide-to="0" class="active"></li>
					<!-- <li data-target="#NewsSlider" data-slide-to="1"></li> -->

				</ol>

				<div class="carousel-inner" role="listbox">


					<!--Slider-first-->
					<div class="item active">
						<br>
						<div class="news">
							<p>
								Dear <span>I Perform konnect,</span>
							</p>
							<img
								src="${pageContext.request.contextPath}/resources/images/testi.png"
								class="pull-right">"I would not have asked for better
							experience ,I think using technology on a daily basis is giving
							my child and extra advantage educationally...<br> <br>
							<strong>-----Gunjal Yadav(New delhi)</strong>

						</div>
						<br>
						<div class="clearfix"></div>

						<div class="news">
							<p>
								Dear <span>I Perform konnect,</span>
							</p>
							<img
								src="${pageContext.request.contextPath}/resources/images/testi1.png"
								class="pull-left"> "I have been working with Class Teacher Learning Systems since the past 2 years and it has always been a pleasure working with them.Their creative modules and ...<br> <br>
							<strong>-----Bharti Prashad(Kanpur)</strong>

						</div>

					</div>
					<!--Slider-first-->



					<!--Slider-second-->
					<div class="item">

						<br>
						<div class="news">
							<p>
								Dear <span>Classkonnect,</span>
							</p>
							<img
								src="${pageContext.request.contextPath}/resources/images/testi.png"
								class="pull-right">" I would not have asked for better
							experience ,I think using technology on a daily basis is giving
							my child and extra advantage educationally...<br> <br>
							<strong>-----Gunjal Yadav(New delhi)</strong>

						</div>
						<br>
						<div class="clearfix"></div>

						<div class="news">
							<p>
								Dear <span>Classkonnect,</span>
							</p>
							<img
								src="${pageContext.request.contextPath}/resources/images/testi1.png"
								class="pull-left"> " I would not have asked for better
							experience ,I think using technology on a daily basis is giving
							my child and extra advantage educationally...<br> <br>
							<strong>-----Gunjal Yadav(New delhi)</strong>

						</div>

					</div>
					<!--Slider-second-->


				</div>
				<!-- /.carousel -->
			</div>


		</div>
		<!--Testimonial-end-here-->


	</div> --%>
	
	<!--Features-end-here-->

	<!-- /END THE FEATURETTES -->
<div class="green-bg">
<div class="col-md-4 col-sm-12 col-xs-12 how-add" style="margin:auto; float:none">
<h2>Testimonials</h2>
</div>

	<div class="container content"> 
	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="height:205px; padding-top:15px;"> 
	
	<!-- Indicators --> 
	<ol class="carousel-indicators"> 
	<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li> 
	<li data-target="#carousel-example-generic" data-slide-to="1"></li> 
	<li data-target="#carousel-example-generic" data-slide-to="2"></li> 
	</ol>
	 <!-- Wrapper for slides --> 
	 <div class="carousel-inner"> 
	 <div class="item active" style="height:280px;"> 
	 <div class="row"> 
	 <div class="col-xs-12"> 
	 <div class="thumbnail adjust1"> 
	 <div class="col-md-1 col-sm-2 col-xs-11"> 
	 <img class="img-circle" src="${pageContext.request.contextPath}/resources/images/testi.png" alt=""></div> 
	 <div class="col-md-10 col-sm-10 col-xs-12"> <div class="caption"> 
	 <p style=" color: #2d2d2d;" class="text-info lead adjust2">Dear iPerformKonnect,</p> 
	 <p style=" color: #2d2d2d;font-size: 17px;"><i class="fa fa-thumbs-up" aria-hidden="true"></i>
 "I would not have asked for better experience ,I think using technology on a daily basis is giving my child and extra advantage educationally..."</p> 
	 <blockquote class="adjust2"> 
	 <p>Gunjal Yadav(Teacher, DAV New delhi)</p> 
</blockquote> 
</div> 
</div> 
</div> 
</div> 
</div> 
</div> 
<div class="item" style="height:280px;"> 
<div class="row"> 
<div class="col-xs-12"> 
<div class="thumbnail adjust1"> 
<div class="col-md-1 col-sm-2 col-xs-11">
 <img class="img-circle" src="${pageContext.request.contextPath}/resources/images/testi1.png" alt=""> 
 </div> <div class="col-md-10 col-sm-10 col-xs-12"> 
 <div class="caption"> 
 <p style=" color: #2d2d2d;" class="text-info lead adjust2">Dear iPerformKonnect,</p> 
 <p style=" color: #2d2d2d;font-size: 17px;"><i class="fa fa-thumbs-up" aria-hidden="true"></i>

  "I have been working with Class Teacher Learning Systems since the past 2 years and it has always been a pleasure working with them."</p>
   <blockquote class="adjust2"> <p>Bharti Prashad(Principal, CBP Gurgaon)</p> 
 </blockquote> </div>
  </div> 
  </div> 
  </div> 
  </div> 
  </div> 
  <div class="item" style="height:280px;"> 
  <div class="row"> 
  <div class="col-xs-12"> 
  <div class="thumbnail adjust1"> 
  <div class="col-md-1 col-sm-2 col-xs-11">
  <img class="img-circle" src="${pageContext.request.contextPath}/resources/images/prnt.png" alt=""> </div> 
  <div class="col-md-10 col-sm-10 col-xs-12"> 
  <div class="caption"> 
  <p style=" color: #2d2d2d;" class="text-info lead adjust2">Dear iPerformKonnect,</p> 
  <p style=" color: #2d2d2d;font-size: 17px;"><i class="fa fa-thumbs-up" aria-hidden="true"></i>

  "My Kid have just used your application and I must say that I am delighted. As a parent - your application is easy to use, extremely well thought-through and my kid will continue to use it for learning through assessments."</p> 
   <blockquote class="adjust2"> <p>Arjun Chauhan(Parent)</p>
   </blockquote> 
   </div> 
   </div> 
   </div> 
   </div> 
   </div> 
   </div> 
   </div> 
   <!-- Controls --> 
   </div> </div>
</div>



	<!-- Start Footer Section -->
	<footer>

		<div class="container">
			<div class="row footer-widgets">
				<!-- Start Contact Widget -->
				<div class="col-md-4 col-xs-12 col-sm-3">
					<div class="footer-widget contact-widget">
						<h4>
							Contact Us
							<%-- <img src="${pageContext.request.contextPath}/resources/images/footer-margo.png"
 class="img-responsive" alt="Footer Logo" /> --%>
						</h4>
						<p>
							Mind Shaper Technologies<br> Plot no. 188, Udyog Vihar Phase
							-4,<br> Gurgaon, Haryana, India 122015
						</p>
					</div>
				</div>
				<!-- .col-md-3 -->
				<!-- End Contact Widget -->
				<div class="col-md-5 col-xs-12 col-sm-3">
					<div class="footer-widget flickr-widget">
						<h4></h4>
						<ul>
							<li style="margin-left:8px;">About Us</li>
							<li>Terms & Conditions</li>
							<li style="border: none;">FAQ</li>
						</ul>
					</div>
				</div>

				<div class="col-md-3 col-xs-12 col-sm-3">
					<div class="footer-widget contact-widget">
						<h4 style="margin-bottom: 0px; padding-left: 20px;">Follow Us</h4>
						<div class="footer-widget social-widget">

							<ul class="social-icons">
								<li style="border: none;"><a class="facebook" href="#"><i
										class="fa fa-facebook-square"></i></a></li>
								<li style="border: none;"><a class="twitter" href="#"><i
										class="fa fa-twitter-square"></i></a></li>
								<li style="border: none;"><a class="google" href="#"><i
										class="fa fa-google-plus-square"></i></a></li>
								<li style="border: none;"><a class="linkdin" href="#"><i
										class="fa fa-linkedin-square"></i> </a></li>

							</ul>
						</div>
						<ul>
			<li><i class="fa fa-mobile"></i> : 0124 - 6942225 <br> <i class="fa fa-envelope-o"></i> : communications@classteacher.com
			</li>
			<li><i class="fa fa-globe"></i> : http://www.iperformkonnect.com</li>
						</ul>
						

					</div>
				</div>
			</div>
			<!-- .row -->
			<!-- Start Copyright -->
			<div class="copyright-section">
				<div class="row">
					<div class="col-md-12 col-sm-6 col-xs-12"
						style="text-align: center;">
						<img
							src="${pageContext.request.contextPath}/resources/images/footer-logo.png">
						Copyright 2016, All Right Reserved.
					</div>
				</div>
				<!-- .row -->
			</div>
			<!-- End Copyright -->
		</div>

	</footer>
	<!-- End Footer Section -->
	<script>
	
	$(".userTypeCls").change(function(){
		if($(this).val() == 4 || $(this).val() == '4'){
			$("#showHideBoardGrade").show();
			$("#boardId").attr('required','required');
			$("#gradeId").attr('required','required');
		}else {
			$("#showHideBoardGrade").hide();
			$("#boardId").removeAttr('required');
			$("#gradeId").removeAttr('required');
		}
	});
	
	
		$(document).ready(function()
        {
	        
	        $('[data-toggle="tooltip"]').tooltip();
	        
	        <c:if test="${not empty errMsg1}">
	        $("#errorMsg1").html('${errMsg1}');
	        $(".wrapper").show();
	        <c:remove var="errMsg1" scope="session"/>
	        </c:if>

	        <c:if test="${not empty successMsg1}">
	        $("#successMsg1").html('${successMsg1}');
	        $(".wrapper").show();
	        <c:remove var="successMsg1" scope="session"/>
	        </c:if>

	        <c:if test="${not empty errMsg2}">
	        $("#errorMsg2").html('${errMsg2}');
	        $(".wrapper1").show();
	        <c:remove var="errMsg2" scope="session"/>
	        </c:if>

	        <c:if test="${not empty successMsg}">
	        $("#successMsg").html('${successMsg}');
	        $(".wrapper1").show();
	        <c:remove var="successMsg" scope="session"/>
	        </c:if>

	        <c:if test="${not empty forgotPwdUserId}">
	        $(".wrapper4").show();
	        $("#huserId").val("${forgotPwdUserId}");
	        <c:remove var="forgotPwdUserId" scope="session"/>

	        </c:if>
        });
		
	
		$('[data-toggle="tooltip"]').tooltip(
        {
	        'placement' : function(tt, trigger)
	        {
		        var $trigger = $(trigger);
		        var windowWidth = $(window).width();
		        
		        var xs = $trigger.attr('data-placement-xs');
		        var sm = $trigger.attr('data-placement-sm');
		        var md = $trigger.attr('data-placement-md');
		        var lg = $trigger.attr('data-placement-lg');
		        var general = $trigger.attr('data-placement');
		        
		        return (windowWidth >= 1200 ? lg : undefined) || (windowWidth >= 992 ? md : undefined) || (windowWidth >= 768 ? sm : undefined) || (windowWidth >= 768 ? xs : "top") || general || "top";
	        }
        });
        
        function closeLogin()
        {
	        $(".wrapper").hide();
	        $(".wrapper1").hide();
	        $(".wrapper3").hide();
	        $(".wrapper4").hide();
        }

        function loginDiv()
        {
	        $(".wrapper").show();
	        
        }
        function signUpDiv()
        {
	        document.getElementById("userSignUpId").reset();
	        $(".wrapper1").show();
	        
        }

        function forgotPwd()
        {
	        closeLogin();
	        $("#errorMsg3").html("");
	        $("#successMsg3").html("");
	        $(".wrapper3").show();
        }

        function forgotPassword()
        {
	        var emailId = $("#forgotEmailId").val();
	        $("#errorMsg3").html("");
	        $("#successMsg3").html("");
	        if(emailId.trim() != "")
	        {
		        $.post("${pageContext.request.contextPath}/ajax/rest/forgotPassword/" + emailId, function(data)
		        {
			        if(data == 1 || data == "1")
			        {
				        $("#forgotEmailId").val("");
				        $("#successMsg3").html("For Reset Password click on a link send to your Email-id !!!");
			        }
			        else if(data == 2 || data == "2")
			        {
				        $("#errorMsg3").html("Please check your mail-id,This is not exist in our database !!!");
			        }
			        
		        });
	        }
	        else
	        {
		        $("#errorMsg3").html("Please Enter emilId !!!");
		        return false;
	        }
	        
        }

        function reserPassword()
        {
	        $("#errorMsg4").empty();
	        var pwd = $("#passwordId1").val().trim();
	        var confirmPwd = $("#passwordId2").val().trim();
	        var userId = $("#huserId").val();
	        if(pwd == null || pwd == "")
	        {
		        $("#errorMsg4").html("Please enter password !!!");
	        }
	        else if(pwd != confirmPwd)
	        {
		        $("#errorMsg4").html("Password should be match !!!");
	        }
	        else
	        {
		        $.post("${pageContext.request.contextPath}/ajax/rest/resetPassword/" + pwd + "/" + confirmPwd + "/" + userId, function(data)
		        {
			        if(data == true || data === "true")
			        {
				        $("#successMsg4").html("You have successfully reset your password !!!");
			        }
			        else
			        {
				        $("#errorMsg4").html("Password does not match !!!");
			        }
			        
		        });
	        }
        }
	</script>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<script src="${pageContext.request.contextPath}/resources/js/holder.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="${pageContext.request.contextPath}/resources/js/ie10-viewport-bug-workaround.js"></script>
	<script type="text/javascript">
window.$zopim||(function(d,s){var z=$zopim=function(c){
z._.push(c)},$=z.s=
d.createElement(s),e=d.getElementsByTagName(s)[0];z.set=function(o){z.set.
_.push(o)};z._=[];z.set._=[];$.async=!0;$.setAttribute('charset','utf-8');
$.src='//v2.zopim.com/?3uxBGm2NA9qA6JVw12xrlKRpqra9S2w7';z.t=+new Date;$.
type='text/javascript';e.parentNode.insertBefore($,e)})(document,'script');
</script>
</body>
</html>
