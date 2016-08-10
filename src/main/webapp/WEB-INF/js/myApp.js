var myApp = angular.module('myApp', ['ngRoute', 'Factorys', 'Controllers']);
var Factorys = angular.module('Factorys', []);
var Controllers = angular.module('Controllers', []);

myApp.config(function($routeProvider){
  $routeProvider
    .when('/default.do', {
      templateUrl: './page/default.html',
      controller: 'homeController'
    })
    .when('/kind.do', {
      templateUrl: './page/kind.html',
      controller: 'kindController'
    })
    .when('/search.do', {
      templateUrl: './page/search.html',
      controller: 'searchController'
    })
    .when('/cart.do', {
      templateUrl: './page/cart.html',
      controller: 'cartController'
    })
    .when('/mine.do', {
      templateUrl: './page/mine.html',
      controller: 'mineController'
    })
    .otherwise({
        redirectTo: '/default.do'
    });
});
