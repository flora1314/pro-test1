//var G_PlatformStatisticData; // 注册渠道统计结果
//var G_CityStatisticData; // 注册区域统计结果

$(function() {

	// 日期显示
	//var element = $("#date1");
	//element.daterangepicker(G_DateRangeCommonProp, function(start, end, label) {
	//	var str = start.format('YYYY-MM-DD')
	//			+ (SINGLE_DATE_PICKER ? "" : (DATE_RANGE_SPERATOR + end
	//					.format('YYYY-MM-DD')));
	//	element.val(str);
	//});
	//element.on('cancel.daterangepicker', function(ev, picker) {
	//	element.val("");
	//});
	//element.on('apply.daterangepicker', function(ev, picker) {
	//	var str = picker.startDate.format('YYYY-MM-DD')
	//			+ (SINGLE_DATE_PICKER ? "" : (DATE_RANGE_SPERATOR + picker.endDate
	//					.format('YYYY-MM-DD')));
	//	element.val(str);
	//});

	// 多选框
	//$("#test").chosen({search_contains:true}).on('change', function(evt, params) {
	//	loadStore($(this).val(), "-1");
	//});

	// 第一次加载列表
	setTimeout(function() {
		loadMainList();
	}, 500);


});


/**
 * 加载主列表数据
 */
function loadMainList(currentPage) {
	if (!currentPage) {
		currentPage = 1;
	}
	$.showBlock();
	var jqObj = $("#list");
	var param = packageParams();
	$.executeAjax("/common/loadData", "post", {}, function(result) {
		$.hideBlock();
		var htmlStr = "";
		if (!result.data) {
			htmlStr += "<tr><td colspan='4' class='center'>暂无数据</td></tr>";
			jqObj.html(htmlStr);
			return;
		}

		// 展示表格
		var page = result.data;
		var dataList = page.data; // 注意是2个data
		var totalCount = page.totalCount
		for (var i = 0; i < dataList.length; i++) {
			var row = dataList[i];
			htmlStr += "<tr>";
			htmlStr += "<td align='center'>a" + i + "</td>";
			htmlStr += "<td align='center'>b" + i + "</td>";
			htmlStr += "<td align='center'>c" + i + "</td>";
			htmlStr += "<td align='center'>d" + i + "</td>";
			htmlStr += "</tr>";
		}
		jqObj.html(htmlStr);

		$("#page").html(showPage(1, 15, totalCount, "toPage"));

	});
}


/**
 * 封装列表查询参数
 */
function packageParams() {
	var param = {};
	return param;
}

function toPage(currentPage) {
	loadMainList(currentPage)
}
