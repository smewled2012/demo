$(document).ready(function() {
	$("#one-way").on('click', function() {
		$('#return-date').hide();
		$('.flight-type').removeClass("btn-primary").addClass("btn-secondary");
		$(this).addClass("btn-primary").removeClass("btn-secondary");
		$('input[name=tripType]').val("one_way");
	})

	$("#round-trip").on('click', function() {
		$('.flight-type').removeClass("btn-primary").addClass("btn-secondary");
		$(this).addClass("btn-primary").removeClass('btn-secondary');
		$('#return-date').show();
		$('input[name=tripType]').val("round_trip");
	})

	$('#one-way').click();

	$('.date-picker').datepicker({
		minDate : 0,
		dateFormat : 'mm/dd/yy'
	});

	// $('#departure-date').on('change', function(){
	// debugger;
	// $("#return-date").datepicker(
	// "change", {
	// minDate: new Date($('#departure-date').val())
	// });
	// })
})