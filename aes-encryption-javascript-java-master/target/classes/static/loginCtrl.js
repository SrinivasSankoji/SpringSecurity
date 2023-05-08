var app = angular.module('demoApp', []);
app.controller('loginController', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {

    $rootScope.authenticated = false;
	$scope.headingTitle = 'Login to get started.';

    $scope.logMeIn = function(){
        if(!$scope.userName || !$scope.password){
            $scope.showMessage("Missing required fields.", false);
            return;
        }
        var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
        var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);

        var aesUtil = new AesUtil(128, 1000);
        var ciphertext = aesUtil.encrypt(salt, iv, $('#key').text(), $scope.password);

        var aesPassword = (iv + "::" + salt + "::" + ciphertext);
        var password = btoa(aesPassword);
        var data = {
            userName: $scope.userName,
            password: password
        }

        $http.post('/login',data).then(function (response){
			if(response.status === 200){
                alert("Password is " + response.data.password);
			}else {
			    alert("Error occurred");
            }
		})
	};

	}]);