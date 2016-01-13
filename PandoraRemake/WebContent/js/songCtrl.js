angular.module('app').controller('SongCtrl',SongCtrl);

function SongCtrl($scope,  GetFactory,PostFactory){
	$scope.add = [];
	$scope.songs = [];
	
	$scope.getSongs =	function(){
		var temp=[];
		$scope.songs = GetFactory.getAllSongs($scope)
		
		//for(var i=0;i<temp.length;i++){
		//	$scope.songs[i] = {wav:temp[i].wav, selected:false};
		//}
	};
	
	 $scope.init = function () {
	        $scope.getSongs();
	    };
	    
	 $scope.addPlaylist = function(plName){
	    	 var names=[];
	    	 var nameIndex=0;
	    	for(var i=0;i<$scope.songs.length;i++){
	    		if($scope.songs[i].selected){
	    			names[nameIndex] = {wav:$scope.songs[i].wav};
	    			nameIndex++;
	    			//alert("song select: "+$scope.songs[i].wav);	 
	    		}
	    			}
	    	alert("set names: "+names[0].wav);
	    	//names = angular.fromJson($scope.add);
	    	//alert(names[0].wav);
	    	alert("adding Playlist");
	    	PostFactory.addPlaylist($scope,names,plName);
	    };
}

