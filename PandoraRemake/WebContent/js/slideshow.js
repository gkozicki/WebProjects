angular.module('app', ['ngAnimate'])
    .controller('slideCtrl', function ($scope) { 
    	$scope.slides = [
    	                 {image: '/PandoraRemake/partials/band1.html', description: 'Image 00'},
    	                 {image: '/PandoraRemake/partials/band2.html', description: 'Image 01'},
    	                 {image: '/PandoraRemake/partials/band3.html', description: 'Image 02'},
    	             ];
    	$scope.currentIndex = 0;

        $scope.setCurrentSlideIndex = function (index) {
            $scope.currentIndex = index;
        };

        $scope.isCurrentSlideIndex = function (index) {
            return $scope.currentIndex === index;
        };
        
        $scope.prevSlide = function () {
            $scope.currentIndex = ($scope.currentIndex < $scope.slides.length - 1) ? ++$scope.currentIndex : 0;
        };

        $scope.nextSlide = function () {
            $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.slides.length - 1;
        };
    })
    
     .animation('.slide-animation', function () {
        return {
            addClass: function (element, className, done) {
                if (className == 'ng-hide') {
                    TweenMax.to(element, 0.5, {left: -element.parent().width(), onComplete: done });                    
                }
                else {
                    done();
                }
            },
            removeClass: function (element, className, done) {
                if (className == 'ng-hide') {
                	  element.removeClass('ng-hide');

                      TweenMax.set(element, { left: element.parent().width() });
                      TweenMax.to(element, 0.5, {left: 0, onComplete: done });
                  }
                else {
                    done();
                }
            }
        };
    });
    
