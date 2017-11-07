//if using camel case for directive name then use hyphen spaced name in markup in html

var newSpanElement,generateUniqueTagLagValue=1000,generateUniqueTagTimer;
mainApp.directive('servicenamesuggestion',['$compile','SendClickService',function($compile,SendClickService){
	return {
		 link : function($scope,element,attrs){
			 $scope.suggestServiceName = function(){
				 clearTimeout(generateUniqueTagTimer);
				 generateUniqueTagTimer= setTimeout(function(){
				 var resourcebean = $scope.resourcebean;
				 if(isSendRequestForUniqueId(resourcebean)){
					 SendClickService.generateUniqueTagID(resourcebean).then(function(data){
						 if(!checkEmptyNullorUndefined(data.generatedTagId)){
							 newSpanElement =  "<div class='generatedTagId'>System has found unique service name for you " +
							 		"<span id = 'suggestion' ng-click='populateTag($event)'>"+
							 data.generatedTagId+"</span> Click to use it or enter a service name</div>";
							
											 }else{
								 newSpanElement = "<div class='generatedTagId'>System cound not find a unique service name . Please enter one</div>";
						 }
						 element.html($compile(newSpanElement)($scope));
						 angular.element(".generatedTagId").show();
					 });
				 
			     }else{
			    	 if(angular.element("#suggestion").length>0){
						 angular.element(".generatedTagId").hide();
					 }
			     }
			 	 },generateUniqueTagLagValue);
				 }; 
				 
				 $scope.populateTag = function($event){
					 $event.stopPropagation();
					 $scope.resourcebean.tag = angular.element($event.target).text().trim();
					 angular.element(".generatedTagId").hide();
				 };
				 
				 $scope.hideShowUniqueIdSuggestion = function(){
					 console.log("hideShowUniqueIdSuggestion was triggered "+$scope.resourcebean.tag.length);
					 if($scope.resourcebean.tag.length<=0){
						 $scope.suggestServiceName();
				 }else{
					 angular.element(".generatedTagId").hide();
				 }
				 };
		 }
	};
  
}]);

