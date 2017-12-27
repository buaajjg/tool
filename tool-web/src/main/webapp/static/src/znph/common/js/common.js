
/**
 * 字典code转化为name
 * @param dictList
 * @param code
 * @returns
 */
function getDictName(dictList, code) {
	if(code != ''){
	    for (var i = 0; i < dictList.length; i++) {
	        if (dictList[i].code == code)
	            return dictList[i].name;
	    }
	}
}

/**
 * 字典多项codes转化为name
 * @param dictList
 * @param codes
 * @returns {String}
 */
function getMultiDictName(dictList, codes) {
	  var res = '';
	  if(codes != ''){
		  var codeArr = codes.split(',');
		  for(var i = 0; i<codeArr.length; i++){
			  if(i == 0){
    			  res += getDictName(dictList, codeArr[i]);
			  }else{
				  res += ',' + getDictName(dictList, codeArr[i]);
			  }
		  }
	  }
	 
	  return res;
}

/**
 * 判断对象是否为空
 * @param str
 * @returns {boolean}
 */
function isnull(str){
    return str == null || str == undefined || str == '' || str == 'undefined' || str == 'null';
}

/**
 * 显示图片转换
 * @param url
 * @returns {String}
 */
function pictureExchange(url){
	  return url += "/common";
}