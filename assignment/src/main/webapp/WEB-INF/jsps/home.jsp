<!DOCTYPE html>
<html ng-app="rbb">

  <head>
    <meta charset="utf-8" />
    <title>Angular </title>
    <script>document.write('<base href="' + document.location + '" />');</script>
    <link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" />
   <link rel="stylesheet" href="/js/style.css" />
    <script data-require="angular.js@1.2.x" src="https://code.angularjs.org/1.2.25/angular.js" data-semver="1.2.25"></script>
    <script data-require="jquery@*" data-semver="2.1.1" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="/js/app.js"></script>
  </head>

  <body ng-controller="MainCtrl">
	<p>Please select the Issue Count CSV file</p>
	<p></p>
    <input type="file" file-reader="fileContent" accept=".csv"  /> 
    <div id="table"></div>
  </body>

<p></p>

<body >
	<div ng-controller="getMyController">
		<button ng-click="getLimit()">Sort</button>
		<div ng-show="showLimit">
			
		</div>
		<div id="table"></div>
	</div>
 </body>
</html>
