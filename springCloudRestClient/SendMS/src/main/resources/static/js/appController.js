var mainApp = angular.module("mainApp",['ngStorage']);

var searchTextTimer=null,emptyField=false,searchTextLagTimer=1000,openWebsocketLagTimer=5000,getTagDatatimer=3000,recentListLimit=40,pageSize=4,
beginIndex=0,parentULTimerLag=1000,limitToFactor=32,textToHighlight;

mainApp.controller("mainAppController",['$scope','$http','$compile','$localStorage','$filter','$sce','SendClickService',
                                        function($scope,$http,$compile,$localStorage,$filter,$sce,SendClickService){
	var getTagCloudDataUrl = "/retreivalandsavems/getTagCloudData";
	//console.log("HELLO!!");
	$scope.limitToFactor = limitToFactor;
	$scope.isLoading = false;
	
	//Uncomment to clear all local storage search results
	//clearSearchResultsFromLocalStorage($localStorage);
	//$localStorage.authList=null;
	
	setTimeout(function(){
		 $http.get(getTagCloudDataUrl).then(function(response){
			 $scope.mostFrequent =JSON.parse(JSON.stringify(response.data));
		});
	},getTagDatatimer);
	
	 
	//open websocket connection over stomp
	    setTimeout(function(){
	    	mostFrequentData($scope);
	    },openWebsocketLagTimer);
	//openWebsocket($scope);
		 	
	    headerDefaultAndOtherTasks($scope);
	    //popluating recent transactions from local storage and set indices
	    populateRecentListFromLocalStorage($scope,$localStorage,pageSize,beginIndex);
	    
	    
	 $scope.selectThis = function(data,$event){
		 $event.stopPropagation();
		   //using angular copy to separate data from resourcebean else changes done on resourcebean would reflect on data as well
		 if(angular.element(".generatedTagId").length>0){
			 angular.element(".generatedTagId").hide();
		 }
			$scope.resourcebean=angular.copy(data);
			showHideResponseStatus($scope);
			doStuffForEmpty($scope);
		};
		
		 $scope.send = function($event){   
			 $event.stopPropagation();
			 console.log("urlcred before fetching from Map : "+$scope.resourcebean.urlCred);
			 $scope.resourcebean.urlCred=checkEmptyNullorUndefined($scope.resourcebean.urlCred)?
					 fetchAuthDetails($scope,$localStorage,$filter):$scope.resourcebean.urlCred;
			 console.log("urlcred before send : "+$scope.resourcebean.urlCred);
				var bean = $scope.resourcebean;
				if(!checkEmptyNullorUndefined(bean) && !checkEmptyNullorUndefined(bean.tag) && !checkEmptyNullorUndefined(bean.url)
						 && 
						(!checkEmptyNullorUndefined(bean.headers) || (checkEmptyNullorUndefined(bean.requestBody) && 'get'==bean.requestType))){
					
					//using angularjs promise here to wait for post to complete and then populate data 
					$scope.isLoading = true;
					SendClickService.send(bean).then(function(data){
						$scope.resourcebean = JSON.parse(JSON.stringify(data));
						 showHideResponseStatus($scope);
						 $scope.isLoading = false;
						 
						 //this will check if any unauthorized 401 txn status is returned and prompt for cred
						 if($scope.resourcebean.statusCodeAndReason.includes(401)){
								$scope.showAuthBox('resubmit');
							}
						 
						 addToMostRecentListAndLocalStorage($scope,$localStorage,recentListLimit);
						 clearSearchResultsFromLocalStorage($localStorage);
						 
					});
				    
				 
				}else{
					checkForEmptyBeforeSend($scope);
				}
				
		 };
		 
		 $scope.inputSearchText = function(input){
			 var result;
			 if(!checkEmptyNullorUndefined(input) && input.trim().length>2){
				 textToHighlight = input;
				 emptyField=false;
				 clearTimeout(searchTextTimer);
				 var resultFromLocalStorage = fetchSearchResultFromLocalStorage($localStorage,$filter,input.trim());
				 if(!checkEmptyNullorUndefined(resultFromLocalStorage)){
					 $scope.autoCompleteResult= JSON.parse(JSON.stringify(resultFromLocalStorage));
				 }else{
					 searchTextTimer=setTimeout(function(){
					 
					 SendClickService.searchText(input).then(function(data){
						 if(!emptyField){
							 $scope.autoCompleteResult = JSON.parse(JSON.stringify(data));						 
						 }
					 });	 
				 },searchTextLagTimer);
			 }	
				 $scope.suggestServiceName();
			 }else{
				 doStuffForEmpty($scope);
				 angular.element(".generatedTagId").hide();
			 }
			 showGreenTick($scope,$filter,$localStorage);
		 };
		 
		 $scope.closeAutoCompleteIfOpen = function($event){
			 $event.stopPropagation();
			 if(!checkEmptyNullorUndefined($scope.autoCompleteResult) && $scope.autoCompleteResult.length>0 && $event.target.className!=='autoCompleteList'){
				 doStuffForEmpty($scope);
			 }
		 };
		 
		 $scope.showPrevNext = function(prevNext){
			 if('prev'==prevNext){
				 $scope.beginIndex = (($scope.beginIndex-$scope.pageSize)>=0)?$scope.beginIndex-$scope.pageSize:$scope.beginIndex; 
				 showPrevNextImg($scope);
				 animateElement(".RecentTagListData","animateParentULbackward",parentULTimerLag);
			 }else{
				 $scope.beginIndex = (($scope.beginIndex+$scope.pageSize)<=($scope.mostRecentList.length-1))?$scope.beginIndex+$scope.pageSize:$scope.beginIndex;
				 showPrevNextImg($scope);
				 animateElement(".RecentTagListData","animateParentULforward",parentULTimerLag);
			 }
		 };
		 
		 $scope.highlightText = function(wholeString){
			 return highlightSelection(wholeString.trim(),textToHighlight.trim(),$sce);
		 };
		 
		 $scope.changeRequestType = function(){
			$scope.resourcebean.requestBody=$scope.resourcebean.responseBody=$scope.resourcebean.statusCodeAndReason=null;
			showHideResponseStatus($scope);
			$scope.suggestServiceName();
		 };
		 
		 $scope.showAuthBox = function(resubmit){
			 $scope.isAuthNeeded=true;
			 if('resubmit'===resubmit){
				 $scope.authInstructions="This url needs authentication. Request will be resubmitted after entering credentials";
			 }else{
				 $scope.authInstructions="Credentials entered will be used for basic authentication";
			 }
			 populateURLAuthDivOnShow($scope,$localStorage,$filter);
			
		 };
		 
		 $scope.sendOrSaveAuthCred = function($event){	 
			 if($scope.authInstructions.includes('resubmitted')){
				 $scope.resourcebean.urlCred=addCredtoAuthMapOrLocalStorage($scope,$localStorage,$filter);
				 $scope.send($event);
			 }else{
				 addCredtoAuthMapOrLocalStorage($scope,$localStorage,$filter);
			 } 
		 }
		 
		 $scope.closeUrlAuthDiv = function(){
			 $scope.authUserName=$scope.authPassword=null;
			 $scope.isAuthNeeded=false;
		 }
}]);





