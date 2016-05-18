var topicSubs = 'all'
var urlLocation = 'http://localhost:8081/ferme/account/';
 
var onData = function(data){
    $('body').append("Message Received: " + data);
}
var appSubscriber = function() {
    var callbackAdded = false;
 
    function subscribe() {
        function callback(response) {                    
            if (response.transport != 'polling' && response.state != 'connected' && response.state != 'closed') {                        
                if (response.status == 200) {
                    var data = response.responseBody;
                    if (data.length > 0 && data.search('-->')==-1) {
                        onData(data);
                    }
                }
            }
        }
 
         
        $.atmosphere.subscribe(urlLocation + topicSubs, !callbackAdded ? callback : null,$.atmosphere.request = { transport: 'websocket' });
        callbackAdded = true;
    }
     
    subscribe();
}