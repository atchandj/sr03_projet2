$(document).ready(function() {
	if($('form input').length != 0){
		$('form input')[0].checked =  true ;
	}
	else{
		$('#surveyList').DataTable();
	}
    
} );