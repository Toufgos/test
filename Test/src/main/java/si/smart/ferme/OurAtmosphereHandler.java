package si.smart.ferme;

import org.atmosphere.cpr.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import com.sun.jersey.spi.resource.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
 
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Resume;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.JerseyBroadcaster;
import org.atmosphere.jersey.SuspendResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
 
//@Path("/account/{topic}")
@Produces("text/html;charset=ISO-8859-1")
@Controller("/account")
public class OurAtmosphereHandler{
	
	private @PathParam("topic")
    Broadcaster topic;
	
	@RequestMapping("/{topic}")
	public String ok(HttpServletResponse httpResponse){
		
		System.out.println("je suis au controlleur");
		subscribe(httpResponse);
		return "home";
	}
	
    
 
    //@GET
    public SuspendResponse<String> subscribe(@Context HttpServletResponse httpResponse) {
        httpResponse.addHeader("Access-Control-Allow-Origin","*");
        return new SuspendResponse.SuspendResponseBuilder<String>()
                .broadcaster(topic)
                .outputComments(true)
                .addListener(new EventsLogger())
                .build();
 
    }
 
    public static void push(String message, String topic){
 
        Collection<Broadcaster> broadcasters = BroadcasterFactory.getDefault().lookupAll();
 
        for(Broadcaster b : broadcasters){
            System.out.println(b.toString());
        }
 
        System.out.println("Request to push- Message: " + message + ", Topic: " + topic);
        Broadcaster b = null;
        if(null != (b = BroadcasterFactory.getDefault().lookup(JerseyBroadcaster.class,topic))){
            System.out.println("Request to push- Message: " + message + ", Topic: " + topic);
            b.broadcast(message + "\n");
        }
    }
 
    private class EventsLogger implements AtmosphereResourceEventListener {
 
        @Override
        public void onSuspend(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {
            event.getResource().getResponse().addHeader("Access-Control-Allow-Origin","*");
            System.out.println("onSuspend(): " + event.getResource().getRequest().getRemoteAddr() + " : " +  event.getResource().getRequest().getRemoteHost());            
        }
 
        @Override
        public void onResume(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {
            event.getResource().getResponse().addHeader("Access-Control-Allow-Origin","*");
            System.out.println("onResume(): " + event.getResource().getRequest().getRemoteAddr() + " : " +  event.getResource().getRequest().getRemoteHost());
        }
 
        @Override
        public void onDisconnect(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {            
            System.out.println("onDisconnect(): " + event.getResource().getRequest().getRemoteAddr() + " : " +  event.getResource().getRequest().getRemoteHost());
        }
 
        @Override
        public void onBroadcast(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {
            event.getResource().getResponse().addHeader("Access-Control-Allow-Origin","*");
            System.out.println("onBroadcast(): " + event.getMessage());
        }
 
        @Override
        public void onThrowable(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {            
            System.out.println("onThrowable(): " + event);
        }
    }
 
}