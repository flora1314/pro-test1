function showPage(currentPage, pageSize, totalCount, toPageFnName) {
    if (!totalCount) {
        return;
    }
    if (!currentPage) {
        currentPage = 1;
    }
    if (!pageSize) {
        pageSize = 20;
    }
    var pageNum = Math.floor((totalCount-1)/pageSize) + 1; // 总页数
    if (currentPage > pageNum) {
        currentPage = pageNum;
    }

    if (!toPageFnName) {
        toPageFnName = "defaultToPageFn";
    }


    var str = "";
    str += "<div class='row' style='background-color: #eff3f8;'>";
    str += "    <div class='col-md-6'>";
    str += "        <div class='dataTables_info' id='sample-table-2_info'><h5><small>第&nbsp;" + currentPage + "&nbsp;页&nbsp;&nbsp;共&nbsp;" + totalCount + "&nbsp;条记录&nbsp;&nbsp;" + pageSize + "&nbsp;条/页&nbsp;&nbsp;&nbsp;共&nbsp;" + pageNum + "&nbsp;页</small></h5></div>";
    str += "    </div>";
    str += "    <div class='col-md-6'>";
    str += "        <div class='dataTables_paginate paging_bootstrap'>";
    str += "            <ul class='pagination'>";

    if (currentPage > 1) {
        str += "                <li class='prev'>";
        str += "                    <a href='javascript:void(0);' onclick='" + toPageFnName + "(" + (currentPage-1) + ");' title='上一页' alt='上一页'>";
    } else {
        str += "                <li class='prev disabled'>";
        str += "                    <a href='javascript:void(0);'  title='上一页' alt='上一页'>";
    }
    str += "                        <i class='icon-double-angle-left'></i>";
    str += "                    </a>";
    str += "                </li>";

    if (currentPage == 1) {
        str += "                <li class='active'><a href='javascript:void(0);' >1</a></li>";
    } else {
        str += "                <li><a href='javascript:void(0);' onclick='" + toPageFnName + "(1);'>1</a></li>";
    }

    if (currentPage > 4) { // 左边省略
        str += "               <li><a href='javascript:void(0);'>...</a></li>";
    }

    var maxRepeatTimes = 2;
    var repeatTimes = 0;

    var startIndex = currentPage - 2;
    if (startIndex < 2) {
        startIndex = 2;
    }
    for (var i = startIndex; i > 1 && i < currentPage; i++) { // 左边尾巴
        str += "                <li>";
        str += "                    <a href='javascript:void(0);' onclick='" + toPageFnName + "(" + i + ");'>" + i + "</a>";
        str += "                </li>";
    }

    if (currentPage > 1 && currentPage < pageNum) {
        str += "                <li class='active'>";
        str += "                    <a href='javascript:void(0);'>" + currentPage + "</a>";
        str += "                </li>";
    }

    repeatTimes = 0;
    for (var i = currentPage + 1; i < pageNum; i++) { // 右边尾巴
        str += "                <li>";
        str += "                    <a href='javascript:void(0);' onclick='" + toPageFnName + "(" + i + ");'>" + i + "</a>";
        str += "                </li>";
        if (++repeatTimes >= maxRepeatTimes) {
            break;
        };
    }

    if (currentPage < pageNum - 3) { // 右边省略
        str += "               <li><a href='javascript:void(0);'>...</a></li>";
    }

    if (pageNum > 1) { // 最后一页
        if (currentPage == pageNum) {
            str += "            <li class='active'><a href='javascript:void(0);' >" + pageNum + "</a></li>";
        } else {
            str += "            <li><a href='javascript:void(0);' onclick='" + toPageFnName + "(" + pageNum + ");'>" + pageNum + "</a></li>";
        }
    }

    if (currentPage < pageNum) {
        str += "        <li class='next'>";
        str += "            <a href='javascript:void(0);' onclick='" + toPageFnName + "(" + (currentPage + 1) + ");' title='下一页' alt='下一页'>";
    } else {
        str += "        <li class='next disabled'>";
        str += "            <a href='javascript:void(0);' title='下一页' alt='下一页'>";
    }
    str += "                    <i class='icon-double-angle-right'></i>";
    str += "            </a>";
    str += "        </li>";

    str += "    </ul>";
    str += " </div>";
    str += "</div>";
    str += "</div>";
    return str;
}

function defaultToPageFn(newPage) {
    console.log("newPage=" + newPage);
};