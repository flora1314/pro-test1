//var G_PlatformStatisticData; // 注册渠道统计结果
//var G_CityStatisticData; // 注册区域统计结果

$(function() {

	var element = $("#date1");
	element.daterangepicker(G_DateRangeCommonProp, function(start, end, label) {
		var str = start.format('YYYY-MM-DD')
				+ (SINGLE_DATE_PICKER ? "" : (DATE_RANGE_SPERATOR + end
						.format('YYYY-MM-DD')));
		element.val(str);
	});
	element.on('cancel.daterangepicker', function(ev, picker) {
		element.val("");
	});
	element.on('apply.daterangepicker', function(ev, picker) {
		var str = picker.startDate.format('YYYY-MM-DD')
				+ (SINGLE_DATE_PICKER ? "" : (DATE_RANGE_SPERATOR + picker.endDate
						.format('YYYY-MM-DD')));
		element.val(str);
	});

	$("#platform").chosen({search_contains:true});
	$("#city").chosen({search_contains:true});
	$("#vender").chosen({search_contains:true}).on('change', function(evt, params) {
		loadStore($(this).val(), "-1");
	});
	$("#store").chosen({search_contains:true});


	loadCity();
	loadVender('', function(venderId) {
		// 加载门店信息
		loadStore(venderId, '');
	});

	// 第一次加载列表
	setTimeout(function() {
		loadPlatformList();
	}, 500);

	$("#btnSearch").click(function() {
		loadPlatformList();
	});

	$("#btnExport").click(function() {
		var param = packageParams();
		var url = "/memStatisticExport/platform/list?param=" + encodeURI(JSON.stringify(param));
		location.href = url
	});

});


/**
 * 加载主列表数据
 */
function loadPlatformList() {
	$.showBlock();
	var jqObj = $("#list");
	var param = packageParams();
	var paramStr = JSON.stringify(param);
	var data = {param: paramStr};
	executeAjax("/memStatistic/platform/list", "post", data, function(result) {
		$.hideBlock();
		var htmlStr = "";
		if (!result.data || !result.data.data || !result.data.data.length) {
			htmlStr += "<tr><td colspan='14' class='center'>暂无数据</td></tr>";
			jqObj.html(htmlStr);
			return;
		}

		// 展示表格
		var dataList = result.data.data; // 注意是2个data
		var numSum = 0;
		var totalSum = 0;
		for (var i = 0; i < dataList.length; i++) {
			var row = dataList[i];
			var regNum = row["regNum"];
			var regNumTotal = row["regNumTotal"];
			var hasData = regNumTotal > 0 || regNum > 0;

			numSum += regNum;
			totalSum += regNumTotal;

			var colorVal = "block";
			if (hasData) {
				colorVal = "blue";
			}

			htmlStr += "<tr id='tr_" + row["key"] + "' style='font-weight: bold; color:" + colorVal + ";'>";
			htmlStr += "<td class='center'>" + row["regDateStr"] + "</td>";
			htmlStr += "<td class='center'>" + row["platformName"] + "</td>";
			htmlStr += "<td class='center'>";
			if (hasData) {
				htmlStr += "	<label class='pull-center inline'>";
				htmlStr += "	<input onclick='showDetail(this, " + row["key"] + ");' checked='checked' type='checkbox' class='ace ace-switch ace-switch-5' />";
				htmlStr += "	<span class='lbl'></span>";
				htmlStr += "	</label>";
			} else {
				htmlStr += "无数据";
			}
			htmlStr += "</td>";
			htmlStr += "<td class='center' colspan='4'>小计</td>";
			htmlStr += "<td class='center'>" + regNumTotal + "</td>";
			htmlStr += "<td class='center'>" + regNum + "</td>";
			htmlStr += "</tr>";
		}

		// 合计
		htmlStr += "<tr style='font-weight: bold;'>";
		htmlStr += "<td class='center'>合计</td>";
		htmlStr += "<td class='center' colspan='6'></td>";
		htmlStr += "<td class='center'>" + totalSum + "</td>";
		htmlStr += "<td class='center'>" + numSum + "</td>";
		htmlStr += "</tr>";

		jqObj.html(htmlStr);

		/*****开始计算图表并展示******/
		beginLoadChart(dataList, result.data.extInfo)

	});
}


function showDetail(btnObj, rowId) {
	var trObj = $(".detail_" + rowId);
	if (trObj.length > 0) { // 子列表已存在，直接显示
		if (trObj.is(":hidden")) {
			trObj.fadeIn(500);
		} else {
			trObj.fadeOut(200);
		}
		return;
	}
	loadDetailList(rowId);
}


function loadDetailList(key) {
	var jqObj = $("#tr_" + key);
	$.ajax({
		url: "/memStatistic/platform/detailList",
		type: 'post',
		data: {key: key},
		success: function (result) {
			if (result.code && result.code == '0000' && result.data) {
				var list = result.data;
				var htmlStr = "";
				for (var i = 0; i < list.length; i++) {
					var row = list[i];
					htmlStr += "<tr class='detail_" + key + "' style='display: nones;'>";
					htmlStr += "<td class='center' colspan='3'></td>";
					htmlStr += "<td class='center'>" + row["cityName"] + "</td>";
					htmlStr += "<td class='center'>" + row["venderName"] + "</td>";
					htmlStr += "<td class='center'>" + row["storeSapId"] + "</td>";
					htmlStr += "<td class='center'>" + row["storeName"] + "</td>";
					htmlStr += "<td class='center'>" + row["regNumTotal"] + "</td>";
					htmlStr += "<td class='center'>" + row["regNum"] + "</td>";
				}
				jqObj.after(htmlStr)
			} else {
				XUI_dialog(result.msg + "(code=" + result.code + ", data=" + result.data + ")");
			}
		},
		error: function (errorObj) {
			XUI_dialog("请求失败：" + errorObj.status + "，" + errorObj.statusText);
		}
	});
}

/**
 * 封装列表查询参数
 */
function packageParams() {
	var param = {};
	var regDates = $("#date1").val().split("TO");
	var platform = $("#platform").val();
	var city = $("#city").val();
	var vender = $("#vender").val();
	var store = $("#store").val();
	if (regDates[0] && regDates[1]) {
		param["regDateStart"] = $.trim(regDates[0]);
		param["regDateEnd"] = $.trim(regDates[1]);
	}
	if (platform) {
		param["platformList"] = platform;
	}
	if (city) {
		param["cityIdList"] = city;
	}
	if (vender) {
		var venderIdList = new Array();
		venderIdList.push(vender);
		param["venderIdList"] = venderIdList;
	}
	if (store) {
		param["storeIdList"] = store;
	}
	console.log(param);
	return param;
}