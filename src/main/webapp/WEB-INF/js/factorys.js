Factorys.factory('ctMenuFactory', function($http, $q){
    var getToken = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            method: 'GET',
            url: 'https://api.weixin.qq.com/cgi-bin/token',
            params: {grant_type: 'client_credential', appid: 'wx6f5e3508c7c04844', secret: '05166ae0302d2f8459566de66a3a5a72'}
        }).then(
            function(result){
                deferred.resolve(result.data);
            },
            function(){
                deferred.reject('error')
            }
        );
        return promise;
    };

    var createMenu = function(data){
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            method: 'POST',
            url: ' https://api.weixin.qq.com/cgi-bin/menu/create',
            data: data
        }).then(
            function(result){
                deferred.resolve(result.data)
            },
            function(){
                deferred.reject('error');
            }
        );
    }

    return{
        createMenu: createMenu,
        getToken: getToken
    }
});

Factorys.factory('userInfoFactory', function(){
    var nickName;
    var headImgUrl;
    return {
        setNickName: function(data) {console.log('setNickName');console.log(data);nickName = data;},
        getNickName: function() {return nickName;},
        setHeadImgUrl: function(data) {headImgUrl = data;},
        getHeadImgUrl: function() {return headImgUrl;}
    }
});