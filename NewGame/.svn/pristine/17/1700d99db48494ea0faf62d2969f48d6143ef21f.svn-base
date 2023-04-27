function convertNumToChinese(num,type){
	var temp;
	var result='';
	num = Math.abs(num);
	if(1000>num && num>=100) {
		//console.log(Math.floor(num/100)+'百');
		temp = num%100;
		if(temp !=0 ) {
			result = convertNumToChinese(temp,false);
		}
		result = (Math.floor(num/100)+'百') + result;
	} else if(10000>num && num>=1000) {
		//console.log(Math.floor(num/1000)+'千');
		temp = num%1000;
		if(temp !=0 ) {
			result = convertNumToChinese(temp,false);
		}
		result = (Math.floor(num/1000)+'千') + result;
	} else if (100000000>num && num>=10000) {
		//console.log(Math.floor(num/10000)+'万');
		temp = num%10000;
		if(temp !=0 ) {
			result = convertNumToChinese(temp,false);
		}
		result = (Math.floor(num/10000)+'万') + result;
	} else if (1000000000000>=num && num>=100000000) {
		//console.log(Math.floor(num/100000000)+'亿');
		temp = num%100000000;
		if(temp !=0 ) {
			result = convertNumToChinese(temp,false);
		}
		result = (Math.floor(num/100000000)+'亿') + result;
	} else if (num>=1000000000000) {
		//console.log(Math.floor(num/1000000000000)+'亿亿');
		temp = num%1000000000000;
		if(temp !=0 ) {
			result = convertNumToChinese(temp,false);
		}
		result = (Math.floor(num/1000000000000)+'亿亿') + result;
	} else {
		//console.log(num);
		if(type) {
			result = num;
		} else {
			result = '零'+num;
		}
	}
	return result;
}

$(function(){
	$('#score').bind('input propertychange', function() {  
		var val = $(this).val();
		val = val.replace('-','');
		if (BladeTool.isNotEmpty(val)) {
			$('#showC').text(convertNumToChinese(val,true));
		} else {
			$('#showC').text('');
		}
	}); 
	$('#CheatingRateSet').bind('input propertychange', function() {  
		var setVal = $("input[name='status']:checked").val();
		$('#CheatingRateValue').val(setVal+''+Math.abs(parseInt($(this).val())));
	});
});