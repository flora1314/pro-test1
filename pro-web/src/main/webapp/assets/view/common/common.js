
jQuery.extend({

    defaultIfNull: function(originObj, defaultVal) {
        if (originObj) {
            return originObj;
        }
        if (!defaultVal) {
            defaultVal = "";
        }
        return defaultVal;
    },

    /**
     * 在指定日期基础上添加n天
     * @param date
     * @param days
     * @returns {string}
     */
    addDaysOfDate: function (date, days) {
        if (!date) {
            return new Date();
        }
        millions = date.getTime();
        millions += days * 1000*60*60*24;
        return new Date(millions);
    },

    containsValue: function (arrayVals, keyWord) {
        if (!arrayVals || arrayVals.length <= 0 || !keyWord) {
            return false;
        }
        for (var i = 0; i < arrayVals.length; i++) {
            if (arrayVals[i] == keyWord) {
                return true;
            }
        }
        return false;
    },

    //格式化日期
    formatDate: function(date, fmt) {
		  var o = {   
		    "M+" : date.getMonth()+1,                 //月份   
		    "d+" : date.getDate(),                    //日   
		    "H+" : date.getHours(),                   //小时   
		    "h+" : date.getHours(),                   //小时   
		    "m+" : date.getMinutes(),                 //分   
		    "s+" : date.getSeconds(),                 //秒   
		    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
		    "S"  : date.getMilliseconds()             //毫秒   
		  };   
		  if(/(y+)/.test(fmt))   
		    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  for(var k in o)   
		    if(new RegExp("("+ k +")").test(fmt))   
		  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		  return fmt;   
    },
    
    /**
     * 循环执行
     */
    keepSleepUntil: function(sleepMillions, maxLoopTimes, conditionFn, doFn) {
    	if (!maxLoopTimes) {
    		maxLoopTimes = 3;
    	}
    	var times = 0;
    	var thisObj = this;
//    	console.log(thisObj.formatDate(new Date(), "HH:mm:ss.SSS"));
    	var it = setInterval(function() {
//    		console.log(thisObj.formatDate(new Date(), "HH:mm:ss.SSS") + "等待" + times + "次...");
    		++times; // 次数累加
            var conRes = false;
            try {
                conRes = conditionFn()
                if (conRes) {
                    doFn();
                    clearInterval(it);
                }
            } catch (e) {
                console.log("定时器程序发生异常", e);
            }
    		if (times >= maxLoopTimes) {
                console.log("定时器达到次数上限" + times + "次，强制结束.");
    			clearInterval(it);
    		}
    	}, sleepMillions);
    },

    /**
     * 打印日期测试
     * array[] 毫秒数数组
     */
    printDateForTest: function(array, format) {
        for (var i = 0; i < array.length; i++) {
            console.log(this.formatDate(new Date(array[i]), format));
        }
    },


    //显示提示
    showBlock: function (param) {
        var msg = '<h3>正在提交，请稍后...</h3>';
        if (param && param['message']) {
            msg = param['message'];
        }
        $.blockUI({
            message: msg,
            css: {
                border: 'none',
                padding: '5px',
                backgroundColor: '#000',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                color: '#fff',
                opacity: .7,
                test:'test'
            }
        });
    },

    //隐藏提示
    hideBlock: function (callback) {
        $.unblockUI({onUnblock:callback});
    },

    /**
     * ajax请求通用处理类
     */
    executeAjax: function (requestUrl, requestType, requestParam, successFn, errorFn) {
        $.ajax({
            url: requestUrl,
            type: requestType,
            data: requestParam,
            success: function (result) {
                if (result.code && result.code == '0000' && result.data) {
                    if (successFn) {
                        successFn(result);
                        return;
                    }
                    $.hideBlock();
                    XUI_dialog("请求成功，未对结果做任何处理，请检查");
                } else {
                    $.hideBlock();
                    XUI_dialog(result.msg + "(code=" + result.code + ", data=" + result.data + ")");
                }
            },
            error: function (errorObj) {
                $.hideBlock();
                if (errorFn) {
                    errorFn(errorObj);
                    return;
                }
                XUI_dialog("请求失败或发生异常：" + errorObj.status + "，" + errorObj.statusText);
            }
        });
    }


});






