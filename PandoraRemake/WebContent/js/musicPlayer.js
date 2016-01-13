var audioElement = $("#myTune")[0];
$(".button-pause").on("click", function() {
    $(".button-pause").blur();
    $(".button-pause").addClass("active");
    $(".button-play").removeClass("active");
    audioElement.pause();
  });

  $(".button-play").on("click", function() { 
    $(".button-play").blur();
    $(".button-play").addClass("active");
    $(".button-pause").removeClass("active");
    audioElement.play();
  });

  $(".button-stop").on("click", function() {
    $(".button-stop").blur();
    $(".button-play").removeClass("active");
    $(".button-pause").removeClass("active");
    audioElement.pause();
    audioElement.currentTime = 0;
  });

  $(".button-skip-forward").on("click", function() {
    $(".button-skip-fastword").blur();
    audioElement.currentTime += 5;
  });

  $(".button-skip-backward").on("click", function() {
    $(".button-skip-backward").blur();
    audioElement.currentTime -= 5;
  });