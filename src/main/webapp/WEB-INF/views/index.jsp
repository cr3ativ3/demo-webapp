<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Demo App</title>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<style>
</style>
</head>
<body ng-app="demoApp" ng-controller="RatesCtrl" ng-cloak>
	<div class="mx-5 mt-3 form-group row">
		<div class="col-xs-2">
		  <label for="dateInput">Enter date below (yyyy-MM-dd)</label>
		  <input class="form-control" type="text" id="dateInput" name="dateInput" ng-model="date"/>
		</div>
	</div>
	<button class="mx-5 btn" id="loadRatesData" ng-click="dateSelected(date)">Submit</button>
	<p class="mx-5" style="color: red">{{errorMessage}}</p>
	<div>
		<table class="mx-5 table table-striped" style="width: 35%">
			<tr>
				<th>Currency</th>
				<th>Yesterday</th>
				<th>Today</th>
				<th>Difference</th>
			</tr>
			<tr ng-repeat="model in rates">
				<td>{{ model.code }}</td>
				<td>{{ model.prevRate }}</td>
				<td>{{ model.currRate }}</td>
				<td>{{ model.diff }}</td>
			</div>
		</table>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js"></script>
	<script src="/static/js/app.js"></script>
</body>
</html>