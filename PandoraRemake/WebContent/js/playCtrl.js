angular.module('app').controller('playCtrl',playCtrl);

function playCtrl($scope,  $rootScope){
	$scope.artist = "empty";
	$scope.song = "empty";
	$scope.test = "testing";
	//$scope.getPlaylistSongs = "test";
	$scope.updateArtist = function(name){
		$scope.artist=name;
	}
	$scope.like = function(){
		alert("you like this");
	};
	
	$scope.dislike = function(){
		alert("you don't like this");
	};
	
	$scope.play = function(){
		//alert("song: "+$rootScope.artist);
		$rootScope.artist=$scope.bands[$rootScope.index].name;
		$rootScope.song = $scope.bands[$rootScope.index].song;
		
		alert($rootScope.artist);
	};
	
	$scope.next = function(){
		if($rootScope.index <2){
		$rootScope.index = $rootScope.index+1;
		$rootScope.artist=$scope.bands[$rootScope.index].name;
		$rootScope.song = $scope.bands[$rootScope.index].song;
		}
		else{
			$rootScope.index=0;
		$rootScope.artist=$scope.bands[$rootScope.index].name;
		$rootScope.song = $scope.bands[$rootScope.index].song;
		}
	};
	
	$scope.back = function(){
		if($rootScope.index ==0){
		$rootScope.index = 2;
		$rootScope.artist=$scope.bands[$rootScope.index].name;
		$rootScope.song = $scope.bands[$rootScope.index].song;
		}
		else{
			$rootScope.index--;
		$rootScope.artist=$scope.bands[$rootScope.index].name;
		$rootScope.song = $scope.bands[$rootScope.index].song;
		}
	};
	//getPlaylistSongs[0] = "hi";
	
	$scope.init = function(){
		//alert("calling init");
	}
	
		
};