
angular.module('app').config(function($routeProvider,$locationProvider){
  $routeProvider.when("/",
    {	
	  //templateUrl: "/PandoraRemake/partials/main.html",
      templateUrl: "/PandoraRemake/partials/login.html",
    	  controller:"LoginCtrl",
  		  controllerAs: "app"
    }
  )
  .when('/menu',
	{
	  templateUrl: "/PandoraRemake/partials/mainMenu.html",
	  controller:"MainCtrl",
	  controllerAs: "app"
	}
  )
  .when('/main',
	{
	  templateUrl: "/PandoraRemake/m/MobileRadio.html",
	  controller:"MainCtrl",
	  controllerAs: "app"
	}
  )
  .when('/upload',
	{
	  templateUrl: "/PandoraRemake/partials/myMusic2.html",
	  controller:"MainCtrl",
	  controllerAs: "app"
	}
  )
  .when('/signUp',
	{
	  templateUrl: "/PandoraRemake/partials/signUp.html",
	  controller:"MainCtrl",
	  controllerAs: "app"
	}
  )
  .when('/myPlaylists',
	{
	  templateUrl: "/PandoraRemake/partials/viewPlaylists.html",
	  controller:"MainCtrl",
	  controllerAs: "app"
	}
  ).when('/makePlaylist',
	{
	  templateUrl: "/PandoraRemake/partials/makePlaylist.html",
	  controller:"MainCtrl",
	  controllerAs: "app"
	}
  )
  
  
  .otherwise({
	  Template:"oops"
  });
  
});