
 <!DOCTYPE html>
<html ng-app="backend-csv">

  <head>
    <meta charset="utf-8" />
    <title>Angular </title>
    <script>document.write('<base href="' + document.location + '" />');</script>
    <link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" />
   <link rel="stylesheet" href="/js/style.css" />
    <script data-require="angular.js@1.2.x" src="https://code.angularjs.org/1.2.25/angular.js" data-semver="1.2.25"></script>
    <script data-require="jquery@*" data-semver="2.1.1" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="/js/backend-csv.js"></script>
  </head>

  <body ng-controller="BackendCtrl">
	<p>Please select the CSV file</p>
	<p></p>
    <input type="file" file-reader="fileContent" accept=".csv"  /> 
    <div id="table"></div>
  </body>

<p></p>

<body >
	<div ng-controller="getMyController">
		<button ng-click="getLimit()">Submit</button>
		<div ng-show="showLimit">
			<ul class="list-group">
				<!-- <li ng-repeat="record in allLimit.data"><h4 class="list-group-item">
						<strong>Transaction {{$index}}</strong><br />
						Transaction: {{record.ref}}<br />
						Description: {{record.description}}<br />
						Reason: {{record.invalid}}<br />
				</h4></li> -->
				<table>
					<th>Reference</th>
					<th>Description</th>
					<th>Reason</th>
					<tr ng-repeat="record in allLimit.data">
						<td> {{ record.ref }} </td>
						<td> {{ record.description }} </td>
						<td> {{ record.invalid }} </td>
				</table>
			</ul>
		</div>
		<p>{{getResultMessage}}</p>
	</div>
		
  </body>
</html>

