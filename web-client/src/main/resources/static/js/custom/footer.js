window.sr = ScrollReveal();
sr.reveal('.features img', {
	origin : 'right',
	viewFactor : 0.2,
	distance : '20px'
});
smoothScroll.init();
$('document').ready(function() {
	$('.parallax').parallax();
	$(".button-collapse").sideNav();
	$("#catIconsSlider").owlCarousel({
		autoPlay : true,
		items : 4
	});

});