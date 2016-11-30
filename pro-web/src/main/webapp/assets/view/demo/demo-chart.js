
var levelChartUtil; // 图表计算工具类
var levelChartNodeData;

var chartProp = {
    exporting: {
        enabled:true
    },
    credits: {
        enabled:false
    },
    title: {
        text: '会员等级情况统计报表',
        x: -20 //center
    },
    subtitle: {
        text: '',
        x: -20
    },
//		xAxis: {
//			categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
//			             'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
//		},
    yAxis: {
        title: {
            text: '数量（人）'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
        valueSuffix: '人'
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
//		series: [{
//			name: 'Tokyo',
//			data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
//		}, {
//			name: 'New York',
//			data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
//		}, {
//			name: 'Berlin',
//			data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
//		}, {
//			name: 'London',
//			data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
//		}],
    test:1
};

$(function () {


    // 点击tab按钮判断是否需要加载图表
    $("#myTab li").click(function() {

        var jqObj = $(this);
        if (jqObj.hasClass("active")) {
            return;
        }
        var dateFmt = "yyyy-MM-dd";

        // 加载第二个图表
        if (jqObj.hasClass("chart2")) {
            var chartDivObj = $("#chart2");
            var chartContainerObj = $("#chart2").find(".highcharts-container");
            if (chartContainerObj && chartContainerObj.length > 0) {
                return;
            }
            $.keepSleepUntil(500, 3, function() { // 直到当前div被激活时方才加载图表
                return $('#chart2').hasClass("active");
            }, function() {
                var xAxisData = levelChartUtil.calcuXAxis(levelChartNodeData, "y2");
                var xAxisTextData = []; // 将毫秒数转换为格式化的日期
                for (var i = 0; i < xAxisData.length; i++) {
                    xAxisTextData[i] = $.formatDate(new Date(xAxisData[i]*1), dateFmt);
                }
                var xAxis = {
                    categories: xAxisTextData
                };
                var yAxis = levelChartUtil.calcuYAxis(levelChartNodeData, xAxisData, "y2");
                chartProp["xAxis"] = xAxis;
                chartProp["series"] = yAxis;
                chartProp["title"]["text"] = "会员等级统计报表-新增会员";
                $('#chart2').highcharts(chartProp);
            });
            return;
        }
    });

});

/**
 * 图表计算入口
 */
function beginLoadChart(psData) {

    levelChartUtil = new LevelChartUtil(psData); // 初始化工具类
    resetChartDiv();

    var jqChartObj1 = $("#myTab li").eq(0);
    $.keepSleepUntil(200, 3, function() {
        return jqChartObj1.hasClass("active");
    }, function() {
        // 加载第一个图
        levelChartNodeData = levelChartUtil.pkgLevelChartData("memLevel", "memLevelName"); // 注册渠道统计
        //console.log(JSON.stringify(platChartData));
        var xAxisData = levelChartUtil.calcuXAxis(levelChartNodeData, "y1");
        var xAxisTextData = []; // 将毫秒数转换为格式化的日期
        var dateFmt = "yyyy-MM-dd";
        for (var i = 0; i < xAxisData.length; i++) {
            xAxisTextData[i] = $.formatDate(new Date(xAxisData[i]*1), dateFmt);
        }
        var xAxis = {
            categories: xAxisTextData
        };
        var yAxis = levelChartUtil.calcuYAxis(levelChartNodeData, xAxisData, "y1");
        chartProp["xAxis"] = xAxis;
        chartProp["series"] = yAxis;
        chartProp["title"]["text"] = "会员等级统计报表-会员总数";
        $('#chart1').highcharts(chartProp);
    });

}

/**
 * 重置
 */
function resetChartDiv() {
    $("#myTab li").each(function(index, obj) {
        var jqObj = $(obj);
        var chartDivObj = $("#chart" + (index+1));
        if (chartDivObj.find(".highcharts-container").length > 0) {
            chartDivObj.find(".highcharts-container").remove();
        }
    });
    $("#myTab li").eq(0).find("a").trigger("click"); // 模拟点击第一个tab
}


