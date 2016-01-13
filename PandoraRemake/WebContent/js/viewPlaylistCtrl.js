angular.module('app').controller('viewPlaylistCtrl',viewPlaylistCtrl);

function viewPlaylistCtrl($scope,  GetFactory,PostFactory){
	$scope.playlists = [];
	
	$scope.getPlaylists =	function(){
		//var temp=[];
		$scope.playlists = GetFactory.getAllPlaylists($scope)
		
		//for(var i=0;i<temp.length;i++){
		//	$scope.songs[i] = {wav:temp[i].wav, selected:false};
		//}
	};
	
	 $scope.init = function () {
	        $scope.getPlaylists();
	    };
	    
	 $scope.getPlaylistSongs = function(plName){
		 alert(plName);
		 var songs = [];
		 songs = GetFactory.getPlaylistSongs(plName);
		 alert(songs[0]);
		 };
	    
}

