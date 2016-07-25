<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp" />
<c:set var="selectItem" scope="request"/>
<title><sitemesh:write property='title' /></title>
<sitemesh:write property='head' />
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span>Lumino</span>Admin</a>
				<ul class="user-menu">
					<li class="dropdown pull-right"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"><span
							class="glyphicon glyphicon-user"></span> User <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><span class="glyphicon glyphicon-user"></span>
									Profile</a></li>
							<li><a href="#"><span class="glyphicon glyphicon-cog"></span>
									Settings</a></li>
							<li><a href="<%=request.getContextPath()%>/security/logout"
								target="_blank"><span class="glyphicon glyphicon-log-out"></span>
									Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<!-- 		<form role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
		</form> -->
		<ul class="nav menu">
			<c:choose>
				<c:when test="${selectItem=='user'}">
					<li class="active"><a href="index.html"><span
							class="glyphicon glyphicon-dashboard"></span> 用户</a></li>
				</c:when>

				<c:otherwise>
					<li><a href="index.html"><span
							class="glyphicon glyphicon-dashboard"></span> Dashboard</a></li>
				</c:otherwise>
			</c:choose>
			<li class="parent "><a href="#"> <span
					class="glyphicon glyphicon-list"></span> Dropdown <span
					data-toggle="collapse" href="#sub-item-1" class="icon pull-right"><em
						class="glyphicon glyphicon-s glyphicon-plus"></em></span>
			</a>
				<ul class="children collapse" id="sub-item-1">
					<li><a class="" href="#"> <span
							class="glyphicon glyphicon-share-alt"></span> Sub Item 1
					</a></li>
					<li><a class="" href="#"> <span
							class="glyphicon glyphicon-share-alt"></span> Sub Item 2
					</a></li>
					<li><a class="" href="#"> <span
							class="glyphicon glyphicon-share-alt"></span> Sub Item 3
					</a></li>
				</ul></li>
			<li role="presentation" class="divider"></li>
			<li><a href="login.html"><span
					class="glyphicon glyphicon-user"></span> Login Page</a></li>
		</ul>
		<div class="attribution">
			More Templates <a href="http://www.cssmoban.com/" target="_blank"
				title="模板之家">模板之家</a> - Collect from <a
				href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a>
		</div>
	</div>
	<!--/.sidebar-->

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<sitemesh:write property='body' />
	</div>
	<!--/.main-->
</body>
</html>