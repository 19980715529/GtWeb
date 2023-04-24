function downExcel(colnames,colmodel,source,postdata,code,isCloseTab) {
	$.post(BladeApp.ctxPath + "/excel/preExport", {
	code : code,
	colnames : JSON.stringify(colnames),
	colmodel : JSON.stringify(colmodel),
	postdata : JSON.stringify(postdata),
	source : source
	}, function(data) {
		var layerIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
		if (data.code === 0) {
			if(BladeTool.isNotEmpty(isCloseTab) && isCloseTab) {
				closeTab(layerIndex);
			}
			window.top.location.href = BladeApp.ctxPath + "/excel/export?code=" + data.data;
		} else {
			layer.alert(data.message, {
				icon : 2,
				title : "导出失败"
			});
		}
	}, "json");
}

function closeTab(layerIndex){
	//未找到layerIndex代表为新增tab页
	if(typeof (layerIndex) == "undefined"){
		var tabId = "";
		window.top.autoClose(tabId,"${code!}");
	} else{
		parent.layer.close(layerIndex);
	}
}