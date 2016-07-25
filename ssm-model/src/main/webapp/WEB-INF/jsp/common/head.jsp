<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Lumino - Dashboard</title>

<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/datepicker3.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery-1.11.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/chart.min.js"></script>
<script src="<%=request.getContextPath()%>/js/chart-data.js"></script>
<script src="<%=request.getContextPath()%>/js/easypiechart.js"></script>
<script src="<%=request.getContextPath()%>/js/easypiechart-data.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-datepicker.js"></script>
<script>
	$('#calendar').datepicker({});

	!function($) {
		$(document).on("click", "ul.nav li.parent > a > span.icon", function() {
			$(this).find('em:first').toggleClass("glyphicon-minus");
		});
		$(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
	}(window.jQuery);

	$(window).on('resize', function() {
		if ($(window).width() > 768)
			$('#sidebar-collapse').collapse('show')
	})
	$(window).on('resize', function() {
		if ($(window).width() <= 767)
			$('#sidebar-collapse').collapse('hide')
	})
</script>
