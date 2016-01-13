	$(document).ready(function(){
		$('#navigation ul a').click(function(){
			$('#navigation ul a').removeClass('selected');
			$(this).addClass('selected');
			$('#content_changer').html('You have selected '+ $(this).html());
		});
	});