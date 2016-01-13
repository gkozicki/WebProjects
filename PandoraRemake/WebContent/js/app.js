var app =angular.module('app', ['ngRoute']);

app.controller('MainCtrl',MainCtrl);
app.controller('StationCtrl',StationCtrl);

app.directive('autoComplete', function($timeout) {
    return function(scope, iElement, iAttrs) {
        iElement.autocomplete({
            source: scope[iAttrs.uiItems],
            select: function() {
                $timeout(function() {
                  iElement.trigger('input');
                }, 0);
            }
        });
};
})

app.factory("GetFactory", function($http){
	return { getStations: function(pScope){
		//$("html").css("cursor", "wait");
		//pScope.addStatDisable = true; 
		var json = [];
    	var b = [];
    	var array =[];
    	$http.get("/PandoraRemake/getAllStations").success(function(data) {
    	     b = angular.fromJson(data);
    	      for (var i = 0; i<b.length; i++){
    	    	  array[i] = {name: b[i].station};
    	      }
    	    //  $rootScope.stations = $scope.array;
    	     // $scope.addStatDisable = false;
    	    	$("html").css("cursor", "default");
    	    	pScope.addStatDisable = false;
    	}).error(function(){
    		alert("error");
    		pScope.addStatDisable = false;
    		$("html").css("cursor", "default");
    	});
    	return array;
			
	},
	
		sendEmail: function(emailAddr){
			var info = "email="+emailAddr;
			alert(info);
			$http({
	    	      method: 'POST',
	    	      url: '/PandoraRemake/sendEmail',
	    	      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	    	      data:  info
	    	    }).success(function(data) {
	    	     alert("success!");
	    	}).error(function(){
	    		alert("error");
	    	});
		},
		getAllSongs: function(pScope){ 
			//alert("getting alll songs")
			var json = [];
	    	var b = [];
	    	var array =[];
	    	$http.get("/PandoraRemake/getAllSongs").success(function(data) {
	    		//alert("got all songs");
	    	     b = angular.fromJson(data);
	    	      for (var i = 0; i<b.length; i++){
	    	    	  array[i] = {wav: b[i].path,  selected:false};
	    	      }	    	  
	    	    	$("html").css("cursor", "default");
	    	}).error(function(){
	    		alert("error");
	    		$("html").css("cursor", "default");
	    	});
	    	return array;
				
		},
		getAllPlaylists: function(pScope){ 
			//alert("getting alll playlists")
			var json = [];
	    	var b = [];
	    	var array =[];
	    	$http.get("/PandoraRemake/getAllPlaylists").success(function(data) {
	    		//alert("got all playlists");
	    	//	alert(data);
	    	     b = angular.fromJson(data);
	    	      for (var i = 0; i<b.length; i++){
	    	    	  array[i] = {name: b[i].playlistname  };
	    	      }	    	  
	    	    	$("html").css("cursor", "default");
	    	}).error(function(){
	    		alert("error");
	    		$("html").css("cursor", "default");
	    	});
	    	return array;
	},
	getPlaylistSongs:function(plName){
		var result = [];
		var fromJSON = [];
		var info = "plName="+plName;
		//alert(info);
		$http({
    	      method: 'POST',
    	      url: '/PandoraRemake/getPlaylist',
    	      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    	      data:  info
    	    }).success(function(data) {
    	    	fromJSON = angular.fromJson(data);
	    	      for (var i = 0; i<fromJSON.length; i++){
	    	    	  result[i] = {name: fromJSON[i].name  };
	    	      }	
    	    	
    	    	//result=data;
    	    // alert("success! "+result[0].name);
    	}).error(function(){
    		alert("error");
    	});
		return result;
	},
	getAllStations:function(){ 
		//alert("getting alll playlists")
		var json = [];
    	var b = [];
    	var array =[];
    	$http.get("/PandoraRemake/getAllUsersStations").success(function(data) {
    	//	alert("got all playlists");
    	//	alert(data);
    	     b = angular.fromJson(data);
    	      for (var i = 0; i<b.length; i++){
    	    	  array[i] = b[i].name;
    	      }	
    	      //alert("playlist 1 = "+array[0]);
    	    	$("html").css("cursor", "default");
    	}).error(function(){
    		alert("error");
    		$("html").css("cursor", "default");
    	});
    	return array;
	}
	
	}
	
})

app.factory("PostFactory",function($http,$location){
	return { addStation: function(s,pScope){
	    	
	    	var info = "station="+s;
	    	$http({
	    	      method: 'POST',
	    	      url: '/PandoraRemake/addStation',
	    	      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	    	      data:  info
	    	    }).success(function (data) 
	    	    	      {	    	    
	    	      });
	    	     	   	    		
	    //	$scope.tableParams.reload();//to reload table
	    },//end addStation
	     UserLogin: function(name,pass){
	    	
	    	var info = "username="+name+"&password="+pass;
	    	$http({
	    	      method: 'POST',
	    	      url: '/PandoraRemake/loginAuthentication',
	    	      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	    	      data:  info
	    	    }).success(function (data) 
	    	    	      {	   
	    	    	alert(data);
	    	    	if(data==1)
	    	    	$location.path("/menu");
	    	      });
	    	     	   	    		
	    //	$scope.tableParams.reload();//to reload table
	    },//end userLogin
	    addSong: function(songFile){
	    	$http({
	    	      method: 'POST',
	    	      url: '/PandoraRemake/UploadDownloadFileServlet',
	    	     // enctype: 'multipart/form-data',
	    	      data:  songFile,
	    	      processData: false,
	    	    //  contentType:false
	    	      headers: {'Content-Type': 'multipart/form-data'}
	    	    }).success(function (data) 
	    	    	      {
	    	    	//$("html").css("cursor", "default");
	    	    	//$scope.addStatDisable = false;
	    	    	alert("success");
	    	      });
	    	
	    },
	    addPlaylist: function(pScope,songList,plName){
	    	alert("songList 1 = "+songList[0].wav);
	    	alert("stringify = "+JSON.stringify(songList));
	    	var info = "playlistName="+plName+"&songs="+JSON.stringify(songList);
	    	alert("sending info= "+info);
	    	$http({
	    	      method: 'POST',
	    	      url: '/PandoraRemake/addPlaylist',
	    	      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	    	      data:  info
	    	    }).success(function (data) 
	    	    	      {	    	    
	    	      });
	    }
	}
})

        
function MainCtrl($scope,$rootScope,$http,$location,GetFactory,PostFactory){
	var self = this;
	$rootScope.username="user1";
	$rootScope.stats = "null";
	$rootScope.json = "null";
	$rootScope.index=0;
	$rootScope.artist = "none";
	$rootScope.song = "none";
	$rootScope.test = "testing";
    $scope.stations = [];//$scope.getStations();
    $scope.addStatDisable=false;
    $scope.bands = [];
    
    $scope.changeStation = function(station){
    	alert("Station changed to: "+s.name);	
    }
    
    $scope.changeStations = function(stationName){
    	//alert("Station changed to: "+stationName);
    	
    	changeStations(stationName);
    }
    
    $scope.getStations = function(){
    	$("html").css("cursor", "wait");   	
    	$scope.addStatDisable = true;
    	$scope.stations = GetFactory.getStations($scope);
    }
    
    $scope.addStation = function(s){
    	$("html").css("cursor", "wait");
    	$scope.addStatDisable = true; 
    	PostFactory.addStation(s,$scope);
    	$scope.stations = GetFactory.getStations($scope);
    	//$scope.addStatDisable = false;
    }
    
    $scope.register = function(){
    	//alert("user= "+$scope.name);
    	GetFactory.sendEmail($scope.name);
    }
    
    $scope.init = function () {
        $scope.getStations();
    };
    $scope.go = function(location,name,password){
		//alert("path change: "+location);
    	$rootScope.username = "user1";
    	alert("username= "+name);
    	PostFactory.UserLogin(name,password);
    	//$location.path(location);
    };
    $scope.go2 = function(location){
		alert("path change: "+location);
    	//$rootScope.username = "user1";
    	//alert("username= "+name);
    	//PostFactory.UserLogin(name,password);
    	$location.path(location);
    };
    $scope.getPlaylistSongs = function(){
		//alert("I am beeing called");
		return GetFactory.getPlaylistSongs("duffman");
	};
    
}

function StationCtrl($scope,GetFactory){
    $scope.names = [];
    $scope.names = GetFactory.getAllStations();

	
}
app.controller('LoginCtrl', function($routeParams) {
	
	//alert("chaging view");
});
