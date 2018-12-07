var myfileContent = "";
var app = angular.module('rbb', []);

app.controller('getMyController', function($scope, $http, $location) {
	$scope.showLimit = false;
	
	$scope.getLimit = function() {
		console.log("coming inside sort");
		var table = $("<table />").css('width','100%');
		var cell = "";
		var row = "";

		if (myfileContent == "") {
			return;
		}
		var rows = myfileContent.split('\n');
		var objects = new Array(rows.length);
		for (var i = 0; i < rows.length; i++) {
			row = $("<tr  />");
			var cells = rows[i].split(',');
			if (cells.length > 4) {
				alert('File contains invalid data');
				return false;
			}
			if ( i != 0) {
				objects[i-1] = {
						firstName: cells[0],
						lastName: cells[1],
						ic: cells[2],
						dob: cells[3],
				};
			} else {
				for (var j = 0; j < cells.length; j++) {
					var cell = $("<td />").css('border','1px solid black');
					cell.html(cells[j]);
					row.append(cell);
				}
				table.append(row);
			}
		}
		objects.sort(compare);
		for (var i = 0; i < rows.length - 1; i++) {
			row = $("<tr  />");
			var cell = $("<td />").css('border','1px solid black');
			cell.html(objects[i].firstName);
			row.append(cell);
			table.append(row);
			cell = $("<td />").css('border','1px solid black');
			cell.html(objects[i].lastName);
			row.append(cell);
			table.append(row);
			cell = $("<td />").css('border','1px solid black');
			cell.html(objects[i].ic);
			row.append(cell);
			table.append(row);
			cell = $("<td />").css('border','1px solid black');
			cell.html(objects[i].dob);
			row.append(cell);

			table.append(row);
		}
		$("#table").html('');
		$("#table").append(table);
	}
});


/*app.controller('SortCtrl', function($scope) {
  $scope.name = 'World';
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
        			var table = $("<table />").css('width','100%');
        			var cell = "";
        			var row = "";
        			myfileContent = e.target.result;
        			var rows = e.target.result.split('\n');
        			var objects = new Array(rows.length);
        			for (var i = 0; i < rows.length; i++) {
        				row = $("<tr  />");
        				var cells = rows[i].split(',');
        				if (cells.length > 4) {
        					alert('File contains invalid data');
        					return false;
        				}
        				if ( i != 0) {
        					objects[i-1] = {
        							firstName: cells[0],
        							lastName: cells[1],
        							ic: cells[2],
        							dob: cells[3],
        					};
        				} else {
        					for (var j = 0; j < cells.length; j++) {
        						var cell = $("<td />").css('border','1px solid black');
        						cell.html(cells[j]);
        						row.append(cell);
        					}
        					table.append(row);
        				}
        			}
        			objects.sort(compare);
        			for (var i = 0; i < rows.length - 1; i++) {
        				row = $("<tr  />");
        				var cell = $("<td />").css('border','1px solid black');
        				cell.html(objects[i].firstName);
        				row.append(cell);
        				table.append(row);
        				cell = $("<td />").css('border','1px solid black');
        				cell.html(objects[i].lastName);
        				row.append(cell);
        				table.append(row);
        				cell = $("<td />").css('border','1px solid black');
        				cell.html(objects[i].ic);
        				row.append(cell);
        				table.append(row);
        				cell = $("<td />").css('border','1px solid black');
        				cell.html(objects[i].dob);
        				row.append(cell);

        				table.append(row);
        			}
        			$("#table").html('');
        			$("#table").append(table);
        			scope.$apply(function () {
        				scope.fileReader = (table);
        			});
        		};
        		
        		r.readAsText(files[0]);
        	} 
        } else {
        	($scope.warning = "Please upload a file");
        }
      });
    },
  };
});*/

app.controller('MainCtrl', function($scope) {

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
        			var table = $("<table />").css('width','100%');
        			var cell = "";
        			var row = "";
        			myfileContent = e.target.result;
        			var rows = e.target.result.split('\n');
 
        			for (var i = 0; i < rows.length; i++) {
        				row = $("<tr  />");
        				var cells = rows[i].split(',');
        				if (cells.length > 4) {
        					alert('File contains invalid data');
        					return false;
        				}
        				
    					for (var j = 0; j < cells.length; j++) {
    						var cell = $("<td />").css('border','1px solid black');
    						cell.html(cells[j]);
    						row.append(cell);
    					}
    					table.append(row);
        			}

        			
        			$("#table").html('');
        			$("#table").append(table);
        			scope.$apply(function () {
        				scope.fileReader = (table);
        			});
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

function compare(a, b) {
	if (a.ic < b.ic) {
		return -1;
	} else if (a.ic > b.ic) {
		return 1;
	}
	
	return 0;
}

function validateFile(name){
	var regex = /\*csv$/;
	if ((name.toLowerCase().lastIndexOf(".csv")==-1)) {
		alert('Please select only files with .csv extension');
	    return false;
	}
	return true;
}
