var myfileContent = "";
var app = angular.module('backend-xml', []);

app.controller('BackendCtrl', function($scope) {

});

app.directive('fileReader', function() {
return {
  scope: {
    fileReader:"="
  },
  
  link: function(scope, element) {
    $(element).on('change', function(changeEvent) {
      var files = changeEvent.target.files;
      if (!validateFile(files[0].name)) {
    	  return false;
      }
      if (validateFile(files[0].name)) {
      	if (files.length) {
      		var r = new FileReader();
      		r.onload = function(e) {
      			
      			scope.$apply(function () {
      				scope.fileReader = (table);
      			});
      			myfileContent = e.target.result;
      		};
      		
      		r.readAsText(files[0]);
      	} 
      } else {
      	($scope.warning = "Please upload a file");
      }
    });
  },
};
});


app.controller('getMyController', function($scope, $http, $location) {
	console.log("coming here");
	$scope.showLimit = false;
	
	$scope.getLimit = function() {
		var url = $location.absUrl() + "/upload";
		console.log(url);
		var config = {
				headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
				
				params: { 'limit' : myfileContent }
		}

		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.allLimit = response.data;
				$scope.showLimit = true;
				$scope.getResultMessage = "";
			} else {
				$scope.getResultMessage = "Unable to parse xml";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});

	}
});

function validateFile(name){
	var regex = /\*csv$/;
	if ((name.toLowerCase().lastIndexOf(".xml")==-1)) {
		alert('Please select only files with .xml extension');
	    return false;
	}
	return true;
}
