function selectInfo(key, value, type, num, ctxPath) {
    if (BladeTool.isEmpty(key)) {
        resetInput(num);
    }
    if (type.indexOf("select_") >= 0) {
        var code = type.replace("select_", "");
        $.post(ctxPath + "/cache/getSelect", {
            code : code,
            num : num
        }, function(data) {
            if (data.code === 0) {
                $("#divinput" + num).html(data.data);
            }
        }, "json");
    } else if (type == "selectDept") {
        $.post(ctxPath + "/cache/getDeptSelect", {
            num : num
        }, function(data) {
            if (data.code === 0) {
                $("#divinput" + num).html(data.data);
            }
        }, "json");
    } else if (type == "selectUser") {
        $.post(ctxPath + "/cache/getUserSelect", {
            num : num
        }, function(data) {
            if (data.code === 0) {
                $("#divinput" + num).html(data.data);
            }
        }, "json");
    } else if (type == "selectRole") {
        $.post(ctxPath + "/cache/getRoleSelect", {
            num : num
        }, function(data) {
            if (data.code === 0) {
                $("#divinput" + num).html(data.data);
            }
        }, "json");
    } else if (type.indexOf("selectDiy_") >= 0) {
        var source = type.replace("selectDiy_", "");
        $.post(ctxPath + "/cache/getDiySelect", {
            num : num,
            source : source
        }, function(data) {
            if (data.code === 0) {
                $("#divinput" + num).html(data.data);
            }
        }, "json");
    } else if (type.indexOf("opentreeDiy_") >= 0) {
        var source = type.replace("opentreeDiy_", "");
        var html = "<input type=\"text\"  id=\"inputs"
            + num
            + "_INPUT\" class=\"form-control\" style=\"margin-left:-3px;\"/>";
        html += "<input type=\"hidden\"  id=\"inputs" + num + "\" />";
        $("#divinput" + num).html(html);

        $("#inputs" + num + "_INPUT").bind("click", function() {
            openTree(type, "inputs" + num, "请选择", source);
        });
    } else if (type.indexOf("opentree") >= 0) {
        var html = "<input type=\"text\"  id=\"inputs"
            + num
            + "_INPUT\" class=\"form-control\" style=\"margin-left:-3px;\"/>";
        html += "<input type=\"hidden\"  id=\"inputs" + num + "\" />";
        $("#divinput" + num).html(html);

        $("#inputs" + num + "_INPUT").bind("click", function() {
            openTree(type, "inputs" + num, "请选择", "");
        });
    } else if (type == "date") {
        $("#divinput" + num)
            .html(
                "<input type=\"datetime-local\"  id=\"inputs"
                + num
                + "\" class=\"form-control\" style=\"margin-left:-3px;\"/>");
    } else if (type == "text") {
        resetInput(num);
    }
    $("#hides" + num).val(key);
    $("#searchthis" + num).html(value);
}
// 计算总数：如金币，奖券等
function computeTotal(totalNum) {
    if (totalNum < 0) {
        $("#total").removeClass("text-red").addClass("text-blue");
    } else {
        $("#total").removeClass("text-blue").addClass("text-red");
    }
    $("#total").text(formatCurrency(totalNum));
}
/**
 * 将数值四舍五入(保留2位小数)后格式化成金额形式
 *
 * @param num
 *            数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ','
            + num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + num + '.' + cents);
}

/**
 * 将数值四舍五入(保留1位小数)后格式化成金额形式
 *
 * @param num
 *            数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.4'
 * @type String
 */
function formatCurrencyTenThou(num) {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 10 + 0.50000000001);
    cents = num % 10;
    num = Math.floor(num / 10).toString();
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ','
            + num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + num + '.' + cents);
}

// / 计算版本号
function CalVersion(version) {
    var returnValue = "";
    returnValue += (version >> 24) + ".";
    returnValue += (((version >> 16) << 24) >> 24) + ".";
    returnValue += (((version >> 8) << 24) >> 24) + ".";
    returnValue += ((version << 24) >> 24);
    return returnValue;
}

function toSpecialResult(data) {
    var html = '';
    if (data.indexOf("-") != -1) {
        html = data;
    } else {
        html = '<span class="text-red">' + data + '</span>';
    }
    return html;
}
// 格式化数字 s为字符 ，n为保留几位有效数字
function fmoney(s, n) {
    var symbol = '';
    var temps = s+'';
    if(temps.charAt(0)=='-'){
        symbol = '-';
    }
    s = Math.abs(s);
    n = n > 0 && n <= 20 ? n : 0;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    var result = '';
    var tempr = parseInt(r);
    if (n == 0 || tempr == 0) {
        result = t.split("").reverse().join("");
    } else {
        result = t.split("").reverse().join("") + "." + r;
    }
    result = symbol+result;
    return result;
}

function getJsonLength(jsonData) {
    var jsonLength = 0;
    for ( var item in jsonData) {
        jsonLength++;
    }
    return jsonLength;
}

function convertNumToChinese(num,type){
    var temp;
    var result='';
    num = Math.abs(num);
    if(1000>num && num>=100) {
        console.log(Math.floor(num/100)+'百');
        temp = num%100;
        if(temp !=0 ) {
            result = convertNumToChinese(temp,false);
        }
        result = (Math.floor(num/100)+'百') + result;
    } else if(10000>num && num>=1000) {
        console.log(Math.floor(num/1000)+'千');
        temp = num%1000;
        if(temp !=0 ) {
            result = convertNumToChinese(temp,false);
        }
        result = (Math.floor(num/1000)+'千') + result;
    } else if (100000000>num && num>=10000) {
        console.log(Math.floor(num/10000)+'万');
        temp = num%10000;
        if(temp !=0 ) {
            result = convertNumToChinese(temp,false);
        }
        result = (Math.floor(num/10000)+'万') + result;
    } else if (1000000000000>=num && num>=100000000) {
        console.log(Math.floor(num/100000000)+'亿');
        temp = num%100000000;
        if(temp !=0 ) {
            result = convertNumToChinese(temp,false);
        }
        result = (Math.floor(num/100000000)+'亿') + result;
    } else if (num>=1000000000000) {
        console.log(Math.floor(num/1000000000000)+'亿亿');
        temp = num%1000000000000;
        if(temp !=0 ) {
            result = convertNumToChinese(temp,false);
        }
        result = (Math.floor(num/1000000000000)+'亿亿') + result;
    } else {
        console.log(num);
        if(type) {
            result = num;
        } else {
            result = '零'+num;
        }
    }
    return result;
}
// 大数字三位逗号隔开
function formatNum(str){
    var newStr = "";
    var count = 0;
    if(str.indexOf(".")==-1){
        for(var i=str.length-1;i>=0;i--){
            if(count % 3 == 0 && count != 0){
                newStr = str.charAt(i) + "," + newStr;
            }else{
                newStr = str.charAt(i) + newStr;
            }count++;
        }
        str = newStr; //自动补小数点后两位
        // console.log(str)
    }
    else
    {
        for(var i = str.indexOf(".")-1;i>=0;i--){
            if(count % 3 == 0 && count != 0){
                newStr = str.charAt(i) + "," + newStr;
            }else{
                newStr = str.charAt(i) + newStr; //逐个字符相接起来
            }
            count++;
        }
        str = newStr + (str + "00").substr((str + "00").indexOf("."),3);
        //console.log(str)
    }
}

function backDom(title,id){
    $(".Current_page",parent.document).html(title);
    if(BladeTool.isNotEmpty(id)) {
        var active = $("#sidebar_container li .submenu li a[data-addtabs="+id+"]",parent.document);
        $(active).parent().parent().parent().siblings().children('ul').children('li').removeClass('active');
        $(active).parent().addClass('active');
        $(active).parent().siblings().removeClass('active');
        if($(active).parent().parent().parent().hasClass('open')==false){
            $(active).parent().parent().siblings('a').children('b').click();
        }
    }
}