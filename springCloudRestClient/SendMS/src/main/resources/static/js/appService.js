mainApp.service('SendClickService',['$http','$localStorage','$filter',function($http,$localStorage,$filter){
	
	
	this.send = function(bean){
		var url = "/sendms/sendRequest";
		var data = bean;
		var config = "application/json";
		
		return $http.post(url,data,config).then(function(response){
					return response.data;
		}).catch(function(response){
			data.responseBody='SOME ERROR OCCURED . PLEASE RETRY';
			data.statusCodeAndReason="500 Error";
			return data;
		});
	};
	
	this.searchText = function(input){
			input = replaceAllInString(input.trim(),"\/","#");
			var data = input;
			var config = "application/json";
		var url = "/retreivalandsavems/fetchDataBasedOnInput";
		return $http.post(url,data,config).then(function(response){
			addSearchResultToLocalStorage($localStorage,$filter,input,response.data);
			return response.data;
		});
	}
	
	this.generateUniqueTagID = function(bean){

		var url = "/retreivalandsavems/generateUniqueTagId";
		var data = bean;
		var config = "application/json";
		
		return $http.post(url,data,config).then(function(response){
					return response.data;
		}).catch(function(response){
			return null;
		});
	
	}
}]);