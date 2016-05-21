$(document).ready(function() {
    $(".clickable-row").click(function() {
        window.document.location = $(this).data("href");
    });
    
    
	if($('table').length > 0){
		for(var i = 0, max = $('table').length ; i < max; i++){
			if($('table').eq(i).hasClass("dataTable"))
			$('table').eq(i).DataTable();
		}
	}
});