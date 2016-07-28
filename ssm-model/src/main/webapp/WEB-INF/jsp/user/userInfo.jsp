<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		//先销毁表格  
        $('#table').bootstrapTable('destroy');  
		loadUserPage(1, 10);
	});

	function loadUserPage(currentPage, limit) {
		$.ajax({
           url: '<%=request.getContextPath()%>/user/listUser',
           data : {pageSize : currentPage,pageNumber : limit},
			type : "get",
			dataType : "json",
			success : function(data) {
				$('#table').bootstrapTable({
					data : data,
					striped : true, //表格显示条纹  
					pagination : true, //启动分页  
					pageSize : limit, //每页显示的记录数  
					pageNumber : currentPage, //当前第几页  
					search : false, //是否启用查询  
					showColumns : true, //显示下拉框勾选要显示的列  
					showRefresh : true, //显示刷新按钮  
					sidePagination : "server" //表示服务端请求  
				});
			}
		});
	}
</script>

<div class="row">
	<ol class="breadcrumb">
		<li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
		<li class="active">用户列表</li>
	</ol>
</div>
<!--/.row-->

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">用户列表</h1>
	</div>
</div>
<!--/.row-->


<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
		<!-- 	<div class="panel-heading">Advanced Table</div> -->
			<div class="panel-body">
				<table data-show-refresh="true" data-show-toggle="true"
					data-show-columns="true" data-search="true"
					data-select-item-name="toolbar1" data-pagination="true"
					data-sort-order="desc" id="table">
					<thead>
						<tr>
							<th data-field="userId" data-sortable="true">用户编号</th>
							<th data-field="username">用户名称</th>
							<th data-field="password">用户密码</th>
						</tr>
					</thead>
				</table>

			</div>
		</div>
	</div>
</div>
<!--/.row-->

