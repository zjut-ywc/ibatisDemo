<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1">


<title>welcome</title>

<link href="css/bootstrap.css" rel="stylesheet" type="text/css"
	media="all" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap-3.1.1.min.js"></script>

</head>
<body onload="load();">
	<table class="table">
		<thead>
			<tr>
				<th>编号</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>地址</th>
			</tr>
		</thead>
		<tbody id="user_tbody">

		</tbody>
	</table>

	<script type="text/javascript">
		function load() {
			$.ajax({
				type : "post",
				url : "TestAction.do",
				data : "type=select",
				dataType : "json",
				success : function(data) {
					var obj = eval(data);
					var row = obj.rows;
					for (var i = 0; i < obj.total; i++) {
						var html = "<tr><td>" + row[i].id + "</td><td>"
								+ row[i].userName + "</td><td>"
								+ row[i].userAge + "</td><td>"
								+ row[i].userAddress + "</td></tr>";
						$("#user_tbody").append(html);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}
	</script>

</body>
</html>