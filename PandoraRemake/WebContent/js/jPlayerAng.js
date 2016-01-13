var App = angular.module('App', []);
 
App.run(function($rootScope) {
      $rootScope.audio1 = 'http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Fonda01.wav';
      $rootScope.audio2 = 'http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Fonda01.wav';
});

App.directive('jplayer', function() {
      return {
        restrict: 'EA',
        template: '<div></div>',
        link: function(scope, element, attrs) {
          var $control = element,
              $player = $control.children('div'),
              cls = 'pause';
          var playing =0;
          var playlist = function(){
        	  var temp = [];
        		//var scope = angular.element(document.getElementById('mediaPlayer')).scope();
        		//alert("esting = "+scope.test);
        		//scope.$apply(temp = scope.getPlaylistSongs());
        		//alert("temp = "+temp[0].name);
        		//alert("artist = "+scope.artist);
        		//scope.getPlaylistSongs();
        		//scope.$apply(getPlaylistSongs);
        		//alert("duffman = "+temp);
        		
        		return [{        	    	
        			wav: "http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Fonda01.wav"
        			},
        			{    			
        			wav: "http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Heroic01.wav"
        			},
        			{       		    	
        				wav: "http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Boss01.wav"
        			}
        			];      		
          }//end playlist function
          var nextTrack = function(playerInstance, trackPlaying, playlist){
        	  var name = playlist[0].wav.substring(71);
        		var subname = name.substring(0,name.length-4);
        		//alert("subname= "+subname);
        		//angular.element('#mediaPlayer').scope().artist=subname;
        		//angular.element('#mediaPlayer').scope().$apply(); 
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
          var updatePlayer = function() {
            $player.jPlayer({
              // Flash fallback for outdated browser not supporting HTML5 audio/video tags
              // http://jplayer.org/download/
              swfPath: '/PandoraRemake/jPlayerDist/jplayer/jquery.jplayer.swf',
              supplied: 'wav',
              solution: 'html, flash',
              preload: 'auto',
              wmode: 'window',
              ready: function () {
                $player
                  .jPlayer("setMedia", {wav: scope.$eval(attrs.audio)})
                  .jPlayer(attrs.autoplay === 'true' ? 'play' : 'stop');
              },
              play: function() {
                $control.addClass(cls);
 
                if (attrs.pauseothers === 'true') {
                  $player.jPlayer('pauseOthers');
                }
              },
              pause: function() {
                $control.removeClass(cls);
              },
              stop: function() {
                $control.removeClass(cls);
              },
              ended: function() {
                $control.removeClass(cls);
              },
              next: function() {
            	  alert("clicked next");
              	playing = nextTrack(mediaPlayer,playing,playlist);

              }
            })
            .end()
            .unbind('click').click(function(e) {
              $player.jPlayer($control.hasClass(cls) ? 'stop' : 'play');
            });
          };
 
          scope.$watch(attrs.audio, updatePlayer);
          updatePlayer();
        }
      };
 });