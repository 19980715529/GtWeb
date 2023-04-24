// 生产小时列表
function genDateTimeDom() {
	var datedom = "";
	for (var i = 1; i < 24; i++) {
		datedom += "<li><a href=\"#\" data-id=\"" + i + "\">" + i + "</a></li>";
	}
	$(".datetimedom").append(datedom);
	$(".datetimedom a").bind("click",function(){
		var type = "";
		if($(this).parent().parent().hasClass("start")) {
			type = "start";
		} else if($(this).parent().parent().hasClass("end")){
			type = "end";
		}
		var hours = $(this).attr("data-id");
		var createtime = $("#createtime").val();
		if (createtime.trim() == "") {
			layer_alert('请先选择日期!', "warn");
			return;
		}
		switch (type) {
		case "start":
			var endhours = getEndHours();
			console.log("end:" + endhours);
			if (endhours != "" && hours > endhours) {
				layer_alert('开始时间不能大于结束时间!', "warn");
				return;
			}
			$("#starttime").val(createtime + " " + hours + ":00:00");
			$("#searchstart").text(hours);
			$("#starthours").val(hours);
			break;
		case "end":
			var starthours = getStartHours();
			console.log("start:" + starthours);
			if (starthours != "" && hours < starthours) {
				layer_alert('结束时间不能小于开始时间!', "warn");
				return;
			}
			$("#endtime").val(createtime + " " + hours + ":59:59");
			$("#searchend").text(hours);
			$("#endhours").val(hours);
			break;
		default:
			break;
		}
	});
}

function getStartHours() {
	return $("#starthours").val();
}
function getEndHours() {
	return $("#endhours").val();
}