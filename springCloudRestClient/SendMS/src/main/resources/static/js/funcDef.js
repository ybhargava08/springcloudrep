var stompClient=null,emptyAnimationTimeOut,emptyAnimationTimeOutLag=1000,authList=null;

function mostFrequentData($scope){
	var socket = new SockJS('/eventPollerSubscribe');
	stompClient = Stomp.over(socket);
	
	//disabling stomp logging on console... remove this line if need to enable
	stompClient.debug = null;
	stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/eventMostFrequentPoller', function (responseBeanList) {
        	$scope.mostFrequent=JSON.parse(responseBeanList.body);
        	$scope.$apply();
        });
    });
}

function checkEmptyNullorUndefined(object){
	if(undefined!=object && undefined!==object && null!=object && ''!=object){
		return false;
	}else{
		return true;
	}	
}

function showHideResponseStatus($scope){
	 if(checkEmptyNullorUndefined($scope.resourcebean.responseBody)){
		 $scope.responseShow=false;
	 }else{
		 $scope.responseShow=true;
	 }
	 
	 if(checkEmptyNullorUndefined($scope.resourcebean.statusCodeAndReason)){
		 $scope.responseStatusShow=false;
	 }else{
		 $scope.responseStatusShow=true;
	 }
}

function doStuffForEmpty($scope){
	 clearTimeout(!checkEmptyNullorUndefined(searchTextTimer));
	 emptyField=true;
	 if(!checkEmptyNullorUndefined($scope.autoCompleteResult)){
	 	$scope.autoCompleteResult.length=0;
		}
}

function replaceAllInString(targetStr,toBeReplaced,replacedVal){
    var index=targetStr.indexOf(toBeReplaced);
    while(index >= 0){
        targetStr=targetStr.replace(toBeReplaced,replacedVal);
        index=targetStr.indexOf(toBeReplaced);
    }
    return targetStr;
}

function headerDefaultAndOtherTasks($scope){
	if(checkEmptyNullorUndefined($scope.resourcebean) || checkEmptyNullorUndefined($scope.resourcebean.headers)){
		$scope.resourcebean = {'requestType':'get','headers':'Content-Type:x-www-form-urlencoded'};
		$scope.isAuthNeeded=false;
	}
}

function addToMostRecentListAndLocalStorage($scope,$localStorage,recentListLimit){
	if(checkEmptyNullorUndefined($scope.mostRecentList)){
		$scope.mostRecentList=[];
	}
	//animateElement(".RecentTagListData li","animateMostRecentListItem",emptyAnimationTimeOutLag);
	$scope.mostRecentList.push(angular.copy($scope.resourcebean));
	var d = new Date();
	$scope.mostRecentList[$scope.mostRecentList.length-1].currDate = d;
	$scope.mostRecentList[$scope.mostRecentList.length-1].lastExecutionDate = d.getTime();
	$localStorage.recentList = $scope.mostRecentList;
	removeOldestFromRecentList($localStorage,$scope,$scope.mostRecentList,recentListLimit);
	showPrevNextImg($scope);
	
}

function removeOldestFromRecentList($localStorage,$scope,list,recentListLimit){
	if(!checkEmptyNullorUndefined(list) && list.length>recentListLimit){
		list.splice(0,1);
		$localStorage.recentList = list;
		$scope.mostRecentList = list;
		}
}

function populateRecentListFromLocalStorage($scope,$localStorage,pageSize,beginIndex){
	//uncomment this to remove all items from recent list
	//$localStorage.recentList=null;
	if(checkEmptyNullorUndefined($scope.mostRecentList)){
		$scope.mostRecentList=$localStorage.recentList;
		setDefaultIndicesRecentDataList($scope,pageSize,beginIndex);
		showPrevNextImg($scope);
	}
}

function isSendRequestForUniqueId(resourcebean){
	return ((resourcebean.requestType=='get' && !checkEmptyNullorUndefined(resourcebean.url) && checkEmptyNullorUndefined(resourcebean.requestBody)) ||
						 (resourcebean.requestType=='post' && !checkEmptyNullorUndefined(resourcebean.requestBody) 
						 && !checkEmptyNullorUndefined(resourcebean.url))) && resourcebean.url.length>20 && checkEmptyNullorUndefined(resourcebean.tag) ;

}

function addSearchResultToLocalStorage($localStorage,$filter,key,result){
	var list = $localStorage.localSearchResults;
	
	if(checkEmptyNullorUndefined(list)){
		list = [];
	}
	key = replaceAllInString(key,"#","\/");
	//console.log("list size "+list.length +" result is "+result+ " length "+result.length);
	if(result.length>0 && checkEmptyNullorUndefined($filter('filter')(list, {'key':key}))){
		var resultObj = {};
		resultObj.key = key;
		resultObj.result = result;
		//console.log("result obj created "+resultObj);
		list.push(resultObj);
		$localStorage.localSearchResults = list;
	//	console.log("after push list size "+$localStorage.localSearchResults.length);
	}
}

function fetchSearchResultFromLocalStorage($localStorage,$filter,key){
	
	var list = $localStorage.localSearchResults;
	if(!checkEmptyNullorUndefined(list)){
		var obj = $filter('filter')(list, {'key':key});
		if(!checkEmptyNullorUndefined(obj)){
		//	console.log("found object in local storage : "+obj[0].key);
			return  obj[0].result;	
		}else{
			return null;
		}
		
	}
	return null;
}

function clearSearchResultsFromLocalStorage($localStorage){
	$localStorage.localSearchResults=null;
}

function setDefaultIndicesRecentDataList($scope,pageSize,beginIndex){
		$scope.pageSize = pageSize;
		$scope.beginIndex = beginIndex;
}

function showPrevNextImg($scope){
	if(checkEmptyNullorUndefined($scope.mostRecentList) || (($scope.beginIndex-$scope.pageSize)<0)){
		$scope.showPrevImg=false;
	}else{
		$scope.showPrevImg=true;
	}
	
	if(checkEmptyNullorUndefined($scope.mostRecentList) || (($scope.beginIndex+$scope.pageSize)>($scope.mostRecentList.length-1))){
		$scope.showNextImg=false;
	}else{
		$scope.showNextImg=true;
	}
}

function checkForEmptyBeforeSend($scope){
	
	   angular.element("body").removeClass("checkIfEmpty");
	
	 if(checkEmptyNullorUndefined($scope.resourcebean) || checkEmptyNullorUndefined($scope.resourcebean.url)){
		 goToElementLocAndAnimate("#urlSection");
	 }else if(checkEmptyNullorUndefined($scope.resourcebean.tag)){
		 goToElementLocAndAnimate("#tagUrl");
	 }else if(checkEmptyNullorUndefined($scope.resourcebean.headers)){
		 goToElementLocAndAnimate("#formheader");
	 }
}

function goToElementLocAndAnimate(elementName){
	var elementRef = angular.element(elementName);
	window.scrollTo(elementRef.prop('offsetLeft'),elementRef.prop('offsetTop'));
	//console.log("left is "+elementRef.prop('offsetLeft')+" top is "+elementRef.prop('offsetTop'));
	//clearTimeout(emptyAnimationTimeOutLag);
	animateElement(elementName,"checkIfEmpty",emptyAnimationTimeOutLag);
}

function animateElement(elementName,className,animationTimeOutLag){
	//console.log("element is "+elementName);
	var elementRef;
	/*if(".RecentTagListData li"==elementName){
		elementRef=angular.element(elementName).first();
	}else{*/
		elementRef=angular.element(elementName);
	//}
	elementRef.addClass(className);
	  window.setTimeout(function(){
		  elementRef.removeClass(className);
	},animationTimeOutLag);
}

function highlightSelection(wholeString,textToHighlight,$sce){	
				return $sce.trustAsHtml(wholeString.replace(new RegExp('('+textToHighlight+')', 'gi'),	
						function(textToHighlight){
					    return "<span class='highlightFound'>"+textToHighlight+"</span>";
				   }));
	
}

function addCredentialsToHeader($scope){
	 if(!checkEmptyNullorUndefined($scope.authUserName) && !checkEmptyNullorUndefined($scope.authPassword)){
	//	 console.log("username is : "+$scope.authUserName+" password is : "+$scope.authPassword);
		 var authUserName = angular.copy($scope.authUserName);
		 var authPassword= angular.copy($scope.authPassword);
		 var remCred = angular.copy($scope.rememberCred);
		 $scope.authUserName=$scope.authPassword=null;
		 $scope.isAuthNeeded=false;
		 return  authUserName+","+authPassword+","+remCred;
	 }else if(checkEmptyNullorUndefined($scope.authUserName)){
	//	 console.log("username is empty");
		 animateElement(".urlAuth input[type='text']","checkIfEmpty",emptyAnimationTimeOutLag);
	 }else if(checkEmptyNullorUndefined($scope.authPassword)){
	//	 console.log("password is empty");
		 animateElement(".urlAuth input[type='password']","checkIfEmpty",emptyAnimationTimeOutLag);
	 }
}

function addCredtoAuthMapOrLocalStorage($scope,$localStorage,$filter){
	var key = $scope.resourcebean.url;
	
    var newValue=addCredentialsToHeader($scope);
    console.log("newValue is : "+newValue);
		if($scope.rememberCred){
			addCredtoLocalStorageOrLocalList($localStorage,$filter,key,newValue,'localStorage');
			removeAuthObjectFromLocalStorageOrLocalList($localStorage,$filter,key,'sessionList');
		}else{
			addCredtoLocalStorageOrLocalList($localStorage,$filter,key,newValue,'sessionList');
			removeAuthObjectFromLocalStorageOrLocalList($localStorage,$filter,key,'localStorage');
		}
		console.log("url is : "+$scope.resourcebean.url+" authList value is : "+authList);	
		
}

function addCredtoLocalStorageOrLocalList($localStorage,$filter,key,value,typeOfStorage){
	var list=null;
	if('localStorage'===typeOfStorage){
		list = $localStorage.authList;
	}else{
		list = authList;
	}
	 
	if(checkEmptyNullorUndefined(list)){
	    		list=[];
	}
	    console.log("setting in "+typeOfStorage+" : "+key);
	    var obj = $filter('filter')(list, {'key':key},true);
	    if(!checkEmptyNullorUndefined(obj)){
			obj[0].value=value;
		}else{
			var authObject = {};
			authObject.key=key;
			authObject.value=value;
			list.push(authObject);
			 if('localStorage'===typeOfStorage){
				 $localStorage.authList = list;
				 console.log("size of : "+typeOfStorage+ " is : "+$localStorage.authList.length);
			 }else{
				 authList=list;
				 console.log("size of : "+typeOfStorage+ " is : "+authList.length);
			 }
		}
}

function removeAuthObjectFromLocalStorageOrLocalList($localStorage,$filter,key,typeOfStorage){
	var list = null;
	if('localStorage'===typeOfStorage){
		list = $localStorage.authList;
	}else{
		list = authList;
	}
	if(!checkEmptyNullorUndefined(list)){
		var obj = $filter('filter')(list, {'key':key},true);
		if(!checkEmptyNullorUndefined(obj)){
			var index = list.indexOf(obj[0]);
			console.log("index found : "+index+" type of list to remove from "+typeOfStorage);
			if(index >-1){
				list.splice(index,1);
				console.log("index removed : "+index+" from type of list to remove from "+typeOfStorage);
				 if('localStorage'===typeOfStorage){
					 $localStorage.authList = list;
					 console.log("size of : "+typeOfStorage+ " is : "+$localStorage.authList.length);
				 }else{
					 authList=list;
					 console.log("size of : "+typeOfStorage+ " is : "+authList.length);
				 }
			}
		}
	}else{
		console.log("list is empty of type : "+typeOfStorage);
	}
}

function fetchAuthDetails($scope,$localStorage,$filter){
	var key = $scope.resourcebean.url;
	var result = null;
	result = fetchFromAuthListorLocalStorage($localStorage.authList,$filter,key);
	console.log("found in local storage : "+result);
	if(checkEmptyNullorUndefined(result)){
		result = fetchFromAuthListorLocalStorage(authList,$filter,key);
		console.log("found in sessionList : "+result);
	}
	
	return result;
}

function fetchFromAuthListorLocalStorage(list,$filter,key){
	if(!checkEmptyNullorUndefined(list)){
		var obj = $filter('filter')(list, {'key':key},true);
		if(!checkEmptyNullorUndefined(obj)){
			return obj[0].value;
		}
	}
	return null;
}

function populateURLAuthDivOnShow($scope,$localStorage,$filter){
	var result = fetchAuthDetails($scope,$localStorage,$filter);
	if(!checkEmptyNullorUndefined(result)){
		var resultArray = result.split(",");
		   console.log("final result : "+result);
		 	$scope.authUserName=angular.copy(resultArray[0]);
			 $scope.authPassword=angular.copy(resultArray[1]);
			 $scope.rememberCred = {checked : angular.copy(resultArray[2])};
	}
}

function showGreenTick($scope,$filter,$localStorage){
	if(!checkEmptyNullorUndefined($scope.resourcebean) && !checkEmptyNullorUndefined($scope.resourcebean.url) && $scope.resourcebean.url.length>5){
		var key = $scope.resourcebean.url;
		
		if((!checkEmptyNullorUndefined($localStorage.authList) && !checkEmptyNullorUndefined($filter('filter')($localStorage.authList, {'key':key},true))) || 
				(!checkEmptyNullorUndefined(authList) && !checkEmptyNullorUndefined($filter('filter')(authList, {'key':key},true)))){
			$scope.showGreenTick=true;
		}else{
			$scope.showGreenTick=false;
		}
	}else{
		$scope.showGreenTick=false;
	}
	
	
	
}