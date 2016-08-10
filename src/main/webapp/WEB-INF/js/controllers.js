// Controllers.controller('ctMenuController', function($scope, $http, $q, ctMenuFactory){
//
//     $scope.getToken = function(){
//         alert('getToken');
//         //ctMenuFactory.getToken().then(
//         //    function(result){
//         //        $scope.ac_token = result.access_token;
//         //        alert(result);
//         //        alert(result.access_token);
//         //    },
//         //    function(error){}
//         //);
//         $http({
//             method: 'GET',
//             url: 'http://localhost:8080/wechat/getToken.do'
//         });
//     };
//
//     $scope.createMenu = function(){
//         alert('createMenu');
//         $http({
//             method: 'GET',
//             url: 'http://localhost:8080/wechat/createMenu.do'
//         });
//         //var menu_data = {
//         //    "button": [
//         //        {
//         //            "type": "view",
//         //            "name": "主页",
//         //            "url": "http://120.27.123.7:8081/wechat/home.do"
//         //        }
//         //    ]
//         //};
//         //var data = {access_token: $scope.ac_token, body: menu_data};
//         //alert(data);
//         //ctMenuFactory.createMenu(data).then(
//         //    function (result){
//         //        alert(result.data);
//         //    },
//         //    function(error){
//         //        alert('error')
//         //    }
//         //);
//     };
//
//
//
//     $scope.init = function(){
//         var menu_data = {
//             "button": [
//                 {
//                     "type": "view",
//                     "name": "主页",
//                     "url": "http://120.27.123.7:8081/wechat/home.do"
//                 }
//             ]
//         };
//         ctMenuFactory.getToken().then(
//             function(result){
//                 $scope.access_token = result.access_token;
//                 console.log(result.access_token);
//                 var data = {access_token: result.access_token, body: menu_data};
//                 ctMenuFactory.createMenu(data).then(
//                     function (result){
//                         console.log(result.data);
//                     },
//                     function(error){
//                         console.log('error')
//                     }
//                 );
//             },
//             function(error){
//                 $scope.access_token = '';
//                 console.log('token is null');
//             }
//         );
//     };
// });

Controllers.controller('homeController', function($scope, $rootScope){
  $rootScope.isHome = true;
  $rootScope.isKind = false;
  $rootScope.isCart = false;
  $rootScope.isSearch = false;
  $rootScope.isMine = false;
  console.log('homeController');
});
Controllers.controller('kindController', function($scope, $rootScope){
  $rootScope.isHome = false;
  $rootScope.isKind = true;
  $rootScope.isCart = false;
  $rootScope.isSearch = false;
  $rootScope.isMine = false;
  console.log('');
});
Controllers.controller('searchController', function($scope, $rootScope) {
  $rootScope.isHome = false;
  $rootScope.isKind = false;
  $rootScope.isCart = false;
  $rootScope.isSearch = true;
  $rootScope.isMine = false;
});
Controllers.controller('cartController', function($scope, $rootScope) {
  $rootScope.isHome = false;
  $rootScope.isKind = false;
  $rootScope.isCart = true;
  $rootScope.isSearch = false;
  $rootScope.isMine = false;
});
Controllers.controller('mineController', function($scope, $rootScope,  $http, $q, userInfoFactory) {
  console.log('mineController');
  $rootScope.isHome = false;
  $rootScope.isKind = false;
  $rootScope.isCart = false;
  $rootScope.isSearch = false;
  $rootScope.isMine = true;
  if(angular.isUndefined(userInfoFactory.getNickName())) {
    var deferred = $q.defer();
    var promise = deferred.promise;
    $http({
        method: 'GET',
        url: 'http://120.27.123.7:8081/wechat/getNickNameAndHeadImgUrl.do'
    }).then(
            function(result){
                userInfoFactory.setNickName(result.data.nickName);
                userInfoFactory.setHeadImgUrl(result.data.headImgUrl);
                $scope.nickName = userInfoFactory.getNickName();
                $scope.headImgUrl = userInfoFactory.getHeadImgUrl();
                deferred.resolve('success');
            },function(){
                deferred.reject('error');
            }
        );
  }else{
    $scope.nickName = userInfoFactory.getNickName();
    $scope.headImgUrl = userInfoFactory.getHeadImgUrl();
  }
    console.log('nickName = ' + $scope.nickName);
    console.log('headImgUrl = ' + $scope.headImgUrl);
});
Controllers.controller('indexController', function($scope, $rootScope){

});
