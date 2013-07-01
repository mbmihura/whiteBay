var RequestsCoordinator = (function() {
    var numRequestToComplete, requestsCompleted, callBacks, singleCallBack;

    return function(options) {
    	// Sets default behavior for when params are not send
        if (!options) options = {};
        numRequestToComplete = options.numRequest || 0;
        requestsCompleted = options.requestsCompleted || 0;
        callBacks = [];
        
        var fireCallbacks = function() {
        	// Execute all callouts from list
            for (var i = 0; i < callBacks.length; i++)
            	callBacks[i]();
        };
        
        // If exist, add single callout to the list to be called at the end.
        if (options.singleCallback)
        	callBacks.push(options.singleCallback);

        this.addCallbackToQueue = function(isComplete, callback) {
        	// When an async request if finished... 
            if (isComplete) requestsCompleted++;
            if (callback) callBacks.push(callback);
            if (requestsCompleted == numRequestToComplete) fireCallbacks();
        };
        this.requestComplete = function(isComplete) {
            if (isComplete) requestsCompleted++;
            if (requestsCompleted == numRequestToComplete) fireCallbacks();
        };
        this.setCallback = function(callback) {
            callBacks.push(callBack);
        };
    };
})();