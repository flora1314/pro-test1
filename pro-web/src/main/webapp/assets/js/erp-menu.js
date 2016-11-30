jQuery(function(){
		var erpUrl = jQuery("#erp_user_menu_js").attr("src").split("?")[1] ;
		var menuUrl = erpUrl + "menu/getUserMenu";
		erpInitShortcutsMenu(erpUrl);
		//生成菜单
		jQuery.ajax({ 
			url:menuUrl,
			dataType:"jsonp",
			success: function (data) {
               jQuery("#menu_nav").html(data);
               erpCheckMenu();
            },
            error: function(data) {
				bootbox.alert("获取菜单失败，请稍后重试");
            }
		});
	})

//初始化shortcut菜单
function erpInitShortcutsMenu(erpUrl){
	var html =""; 
	//html+='<a class="btn btn-success" href="javascript:;" title="增加用户"><i class="icon-plus"></i></a> ';//http://sj.wumart.com/poster
	//html+='<a class="btn btn-info" href="javascript:;" title="会员查看"><i class="icon-male"></i></a> ';//http://sj.wumart.com/wmMemberList
	//html+='<a class="btn btn-warning" href="' + erpUrl + 'erpAccount" title="ERP用户管理"><i class="icon-group"></i></a> ';
	//html+='<a class="btn btn-danger" href="javascript:;" title="海报管理"><i class="icon-picture"></i></a> ';//http://sj.wumart.com/wmPoster
	var erpUrl = jQuery("#erp_user_menu_js").attr("src").split("?")[1] ;
	if(erpUrl == "http://deverp.dmall.com/"){
		html+= "<script language='javascript' src='http://devman.push.dmall.com/assets/js/desktopnotify.js'></script>" ;
	}else if(erpUrl == "http://testerp.dmall.com/"){
		html+= "<script language='javascript' src='http://testman.push.dmall.com/assets/js/desktopnotify.js'></script>" ;
	}else if(erpUrl == "http://erp.dmall.com/"){
		html+= "<script language='javascript' src='http://man.push.dmall.com/assets/js/desktopnotify.js'></script>" ;
	}	
	jQuery("#erp-shortcuts-menu").html(html);
}

function erpCheckMenu(){
		//设置菜单选中状态
		var menu_id = jQuery.cookie('wm_menu_id');
		if(menu_id){
			var menu = jQuery("#" + menu_id) ;
			menu.addClass("active");
			var parent_menu = menu.parents('li') ;
			for(var i=0;i<parent_menu.length;i++){
				jQuery(parent_menu[i]).addClass("active open");
			}
		}
	}

function wmSetMenuIdToCookie(menu_id){
	jQuery.cookie("wm_menu_id" , "menu_" + menu_id , {domain:"wumart.com" , path:"/"});
	jQuery.cookie("wm_menu_id" , "menu_" + menu_id , {domain:"dmall.com" , path:"/"});
}
