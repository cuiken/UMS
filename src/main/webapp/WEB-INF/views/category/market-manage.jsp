<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>市场文件管理</title>
<link rel="stylesheet"
	href="${ctx}/js/jquery/themes/base/jquery.ui.all.css">
<script src="${ctx}/js/jquery/ui/jquery.ui.core.js"></script>
<script src="${ctx}/js/jquery/ui/jquery.ui.widget.js"></script>
<script src="${ctx}/js/jquery/ui/jquery.ui.mouse.js"></script>
<script src="${ctx}/js/jquery/ui/jquery.ui.draggable.js"></script>
<script src="${ctx}/js/jquery/ui/jquery.ui.droppable.js"></script>
<script src="${ctx}/js/jquery/ui/jquery.ui.sortable.js"></script>
<script src="${ctx}/js/jquery/ui/jquery.ui.accordion.js"></script>

<style>
<
style>h1 {
	padding: .2em;
	margin: 0;
}

#products {
	float: left;
	margin-right: 2em;
}

#cart {
	float: left;
}
/* style the list to maximize the droppable hitarea */
#sortable1 li,#sortable2 li {
	margin: 0 5px 0px 15px;
	padding: 0px;
	/*font-size: 1.2em;*/
	width: 150px;
}

.ui-widget-content{
    height: 420px;
    width: 260px;
    overflow-y: auto;
}
</style>

<script>
	function openwindow(id) {
		window.open("../category/file-market.action?themeId=" + id
				+ "&marketId=" + $("#market").val(), 'newwindow');
	}
	$(function() {
		$("#message").fadeOut(3000);
		$("#market-tab").addClass("active");
        $("#market-tab a").append("<i class='icon-remove-circle'></i>");
		$("#sortable1, #sortable2").sortable({
			cursor : 'move',
			connectWith : ".connectedSortable"
		}).disableSelection();
		$("#btn")
				.click(
						function() {
							var checked = $("#sortable2").children();
							for ( var i = 0; i < checked.length; i++) {
								$("#cart")
										.append(
												"<input type='hidden' name='checkedFileIds' value="+checked[i].id+">");
							}
							$("#manageForm").submit();
						});

		var sid = $("#market").val();

		var getRemainFileData = function(sfid) {
			$.ajax({
				dataType : "json",
				url : "market!remainFile.action?id=" + sfid,
				success : function(data) {
					var li = "";
					$.each(data, function(i, val) {
						li += "<li id="+val.id+">" + val.name + "</li>";
					});
					$("#sortable1").append(li);
				}
			});
		};
		var getShelfFileData = function(sfid) {
			$.ajax({
				dataType : "json",
				url : "market!fileInMarket.action?id=" + sfid,
				success : function(data) {
					var li = "";

					$.each(data, function(i, val) {
						li += "<li id=" + val.id + " onclick=openwindow("
								+ val.id + ");><a>" + val.name + "</a></li>";
					});
					$("#sortable2").append(li);
				}
			});
		};

		getRemainFileData(sid);
		getShelfFileData(sid);
		$("#market").change(function() {
			$("#sortable1 li").remove();
			$("#sortable2 li").remove();
			var id = $(this).children('option:selected').val();
			getRemainFileData(id);
			getShelfFileData(id);
		});

	});
</script>
</head>
<body>
	<form id="manageForm" action="market!appearOnMarket.action"
		method="post" class="form-horizontal">

		<input type="hidden" name="id" value="${id}" />

		<c:if test="${not empty actionMessages}">
			<div id="message" class="alert alert-success">${actionMessages}</div>
		</c:if>
		<fieldset>
			<legend>
				<small>市场文件上下架</small>
			</legend>
			<div id="filter" style="margin: 5px;">
				市场名称:
				<s:select list="allMarkets" listKey="id" listValue="name"
					name="checkedMarket" id="market" cssClass="span2" />

			</div>
			<div id="products">
				<h1>仓库文件</h1>
				<div class="ui-widget-content">
					<ol id="sortable1" class="connectedSortable"
						style="min-height: 300px;">

					</ol>
				</div>
			</div>

			<div id="cart">
				<h1>已上市文件</h1>
				<div class="ui-widget-content">
					<ol id="sortable2" class="connectedSortable"
						style="min-height: 300px;">

					</ol>
				</div>
			</div>

		</fieldset>
		<div class="form-actions">
			<input class="btn btn-primary" type="button" value="保存" id="btn" />&nbsp;
			<input class="btn" type="button" value="返回" onclick="history.back();">
		</div>

	</form>

</body>
</html>
