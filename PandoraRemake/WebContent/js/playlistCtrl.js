angular.module('app').controller('playlistCtrl',playlistCtrl);
//angular.module('app', []);
function playlistCtrl($scope,  $rootScope){
	$scope.tabUrl= ""; 
	$scope.tab = function(selectedTab){
		if(selectedTab === "tab1"){
			//alert("equals: "+selectedTab);
			$scope.tabUrl = "/PandoraRemake/partials/uploadFile.html";
			//alert("tab equals: "+$scope.tabUrl);
		}
		else if(selectedTab === "tab2"){
			//alert("equals: "+selectedTab);
			$scope.tabUrl = '/PandoraRemake/partials/mainMenu.html';
		}
		else if(selectedTab === "tab3"){
			//alert("equals: "+selectedTab);
			$scope.tabUrl = '/PandoraRemake/partials/login.html';
		}
		else{
			//alert("equals: "+selectedTab);
			$scope.tabUrl = '/';
		}
	};
	$scope.getTabUrl= function(){
		//alert("getting tab: "+$scope.tabUrl);
		return $scope.tabUrl;
	};
};