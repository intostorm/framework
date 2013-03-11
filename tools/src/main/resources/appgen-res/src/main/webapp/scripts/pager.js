/*

function ajaxQuery(pageNo) {
	$.post('url', {param1:1}, function(data) {
 		
 		//do other operations
 		
 		//generate pager
 		var settings = {
			'element': '#pagerDIV',
			'page': data,
			'pageNo': pageNo,
			'indexSize': 7,
			'callback' : function(clickedPageNo) {
				ajaxQuery(clickedPageNo);
			}
		};
		pager(settings);
	})
}
  
ajaxQuery(1);
*/
function pager(settings) {

	var totalCount = settings.page.totalCount;
	var totalPages = settings.page.totalPages;
	
	var minIndexNo = settings.pageNo - (settings.indexSize / 2) >= 1 
		? settings.pageNo - (settings.indexSize / 2) 
		: 1;
	var maxIndexNo = (settings.indexSize + minIndexNo - 1) >= totalPages 
		? totalPages : 
		(settings.indexSize + minIndexNo - 1);
	maxIndexNo = totalPages < maxIndexNo 
		? totalPages 
		: maxIndexNo;
	
	var isFirstAvailable = settings.pageNo > 1 && settings.pageNo <= totalPages;
	var isPreviousAvailable = settings.pageNo > 1 && settings.pageNo <= totalPages;
	var isNextAvailable = settings.pageNo >= 1 && settings.pageNo < totalPages;
	var isLastAvailable = settings.pageNo >= 1 && settings.pageNo < totalPages;

    var pagerAttributes = {
				'pageNo' : settings.pageNo,
				'totalCount' : totalCount,
				'totalPages' : totalPages,
				'indexSize' : settings.indexSize,
				'minIndexNo' : minIndexNo,
				'maxIndexNo' : maxIndexNo,
				'isFirstAvailable' : isFirstAvailable,
				'isPreviousAvailable' : isPreviousAvailable,
				'isNextAvailable' : isNextAvailable,
				'isLastAvailable' : isLastAvailable
            };

    template.openTag = '{%';
	template.closeTag = '%}'; 
	var result = template.render('pagerTemplate01', pagerAttributes);

	$(settings.element).html($(result));
	$('.pager a').not('.disabled').click(function() {
		var pageNo = $(this).attr("pageNo"); 
		if (settings.callback) settings.callback(pageNo);
	});
	
	
}

