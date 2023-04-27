var codeinter;
function send(formid,obj,mobile,isDeleteClass,deleteClass) {
	var wtime = Number($(obj).attr('number'));
	if (wtime == 0) {
		$(obj).text('重新获取');
		$(obj).attr('number', 60);
		reSendCode(formid,mobile,isDeleteClass,deleteClass);
	} else {
		wtime--;
		$(obj).attr('number', wtime);
		$(obj).text(wtime + '秒后重新获取');
		codeinter = setTimeout(function() {
			send(formid,obj,mobile,isDeleteClass,deleteClass);
		}, 1000);
	}
}
/*
 * formid: 表单ID
 * obj：发送验证码组件
 * mobile: 接收验证码的电话号码
 * isDeleteClass：是否改变组件样式
 * deleteClass：更改的样式类
 */
function findPassCode(formid,obj,account,isDeleteClass,deleteClass) {
	$(obj).unbind();
	send(formid,obj,account,isDeleteClass,deleteClass);
    $.ajax({
        url:BladeApp.ctxPath +"/smscha",
        type:'post',
        dataType:'json',
        data:{'account':account},
        async: false,
        success:function (data) {
            if(data.code === 0){
            	console.log(data);
            	$("#hash").val(data.data.hash);
            	$("#tamp").val(data.data.tamp);
                layer.msg(data.message, {shift: 1});
                return false;
            }
            else{
            	clearTimeout(codeinter);
        		$(obj).text('重新获取');
        		$(obj).attr('number', 60);
        		reSendCode(formid,account,isDeleteClass,deleteClass);
                layer_alert(data.message, "error");
                return false;
            }
        },
        error:function (state,type, err) {
        	clearTimeout(codeinter);
    		$(obj).text('重新获取');
    		$(obj).attr('number', 60);
    		reSendCode(formid,account,isDeleteClass,deleteClass);
        }
    });
}
function reSendCode(formid,mobile,isDeleteClass,deleteClass) {
	$("#sendcode").click(function () {
		var account = $("#account").val();
		findPassCode(formid,$(this),account,isDeleteClass,deleteClass);
	});
}
function removeClick(obj) {
	$(obj).click(function(event) {
		event.preventDefault();
	});
}