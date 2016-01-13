angular.module('app').controller('slideCtrl',slideCtrl);

function slideCtrl($scope) { 
	$scope.slides = [
	                 {image: '/PandoraRemake/images/band1.png', description: 'Image 00'},
	                 {image: '/PandoraRemake/images/band2.png', description: 'Image 01'},
	                 {image: '/PandoraRemake/images/band3.png', description: 'Image 02'},
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
}