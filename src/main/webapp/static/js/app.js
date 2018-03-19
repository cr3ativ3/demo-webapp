var app = angular.module('demoApp', []);

app.controller('RatesCtrl', ['$scope', '$http', function ($scope, $http) {

  $scope.date = '2013-01-02';

  $scope.dateSelected = function(date) {
	$scope.errorMessage = '';
	$http.get('/api/rates/'+date)
      .success(function(response){
        $scope.rates = response.data;
      }).error(function(response){
		$scope.errorMessage = response.error;
	  });
   }
}]);