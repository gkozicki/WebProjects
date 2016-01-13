function getFromDB(plName){
	var result = [];
	//var fromJSON = [];
	var info = "plName="+plName;
	//alert(info);
	
	$.ajax({
		url: "/PandoraRemake/getPlaylist",
		type:"POST",
		data: info,
		success:function(data){
			fromJSON = angular.fromJson(data);
  	      for (var i = 0; i<fromJSON.length; i++){
  	    	  result[i] = {wav: "http://localhost:8080/PandoraRemake/"+fromJSON[i].name };
  	    	  alert("song = "+result[i].wav);
  	      }  	      	
	     alert("success! "+result[0].name);
		},
		error: function(){}
	});
	
	return result;
}

function getPlaylist(){
	//var temp = [];
	//var scope = angular.element(document.getElementById('mediaPlayer')).scope();
	//alert("esting = "+scope.test);
	//scope.$apply(temp = scope.getPlaylistSongs());
	//alert("temp = "+temp[0].name);
	//alert("artist = "+scope.artist);
	//scope.getPlaylistSongs();
	//scope.$apply(getPlaylistSongs);
	//alert("duffman = "+temp);
	var temp = [];
	temp = getFromDB("CoolRunnings");
	return temp;
	/*
	[{
    	
		wav: "http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Fonda01.wav"
		},
		{
		
		wav: "http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Heroic01.wav"
		},
		{
	    	
			wav: "http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Boss01.wav"
			}
	];
	*/
	
}

function nextTrack(playerInstance, trackPlaying, playlist) {
	var name = playlist[0].wav.substring(71);
	var subname = name.substring(0,name.length-4);
	//alert("subname= "+subname);
	angular.element('#mediaPlayer').scope().artist=subname;
	angular.element('#mediaPlayer').scope().$apply(); 
	var nextTrack   = trackPlaying +1;
	if(nextTrack > (playlist.length -1)) {	
	nextTrack = 0;	
	}	
	playing = nextTrack;

	var newTrack = playlist[nextTrack];
	
	playerInstance.jPlayer("setMedia", {
	
	
	wav     : newTrack.wav	
	});	
	playerInstance.jPlayer("play");	
	return playing;	
	}

    $(document).ready(function() {
    	
    	var playing = 0;
    	/*var playlist = [{
    	
    			wav: "http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Fonda01.wav"
    			},
    			{
				
				wav: "/PandoraRemake/music/music0002.wav"
				},
				{
			    	
	    			wav: "http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Boss01.wav"
	    			}
    		];*/
    		var playlist = getPlaylist();
    // Using jQuery object, rather than $
    var mediaPlayer = jQuery('#mediaContainer');

    mediaPlayer.jPlayer({
        // Tells JPlayer where to find the SWF file.
        swfPath: '/PandoraRemake/jPlayerDist/jplayer/jquery.jplayer.swf',
        
        // Fix for some older Andriod phones.
        solution:    "flash, html",
        
        // Tells the player that the track is available in:
        //         mp3, Ogg Vorbis and Wave formats.
        supplied : 'wav',
        
        // Assigns the CSS selectors which will control the player,
        //         creating buttons.
        cssSelector: {
            play: '#playButton',
            pause: '#pauseButton',
            stop: '#stopButton'
        },
        
        // Assigns the files for each format, once the player is loaded.
        ready: function() {jQuery(this).jPlayer("setMedia", {
            
          //  wav: '/PandoraRemake/music/music0001.wav'
        });}
        
    });
    $('#playButton').click(function() {
        $('#mediaContainer').jPlayer('play');
    });
    $('#pauseButton').click(function() {
        $('#mediaContainer').jPlayer('pause');
    });
    $('#stopButton').click(function() {
        $('#mediaContainer').jPlayer('stop');
    });
    $('#nextButton').click(function() {
    	playing = nextTrack(mediaPlayer,playing,playlist);
       // $('#mediaContainer').jPlayer('stop');
    });
   // angular.
});
