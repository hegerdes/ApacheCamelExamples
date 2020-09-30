package org.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.search.TwitterSearchComponent;
import org.apache.camel.component.websocket.WebsocketComponent;
import org.apache.camel.impl.DefaultCamelContext;


public class APP {

    private static final String consumerKey = "NMqaca1bzXsOcZhP2XlwA";
    private static final String consumerSecret = "VxNQiRLwwKVD0K9mmfxlTTbVdgRpriORypnUbHhxeQw";
    private static final String accessToken = "26693234-W0YjxL9cMJrC0VZZ4xdgFMymxIQ10LeL1K8YlbBY";
    private static final String accessTokenSecret = "BZD51BgzbOdFstWZYsqB5p5dbuuDV12vrOdatzhY4E";
    private static final String search = "Debates2020";
    private static final int delay = 5000;

    public static void main(String[] args) throws Exception {


        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                WebsocketComponent wc = getContext().getComponent("websocket", WebsocketComponent.class);
                wc.setPort(9090);
                wc.setStaticResources("classpath:.");

                TwitterSearchComponent tc = getContext().getComponent("twitter-search", TwitterSearchComponent.class);
                tc.setAccessToken(accessToken);
                tc.setAccessTokenSecret(accessTokenSecret);
                tc.setConsumerKey(consumerKey);
                tc.setConsumerSecret(consumerSecret);

                fromF("twitter-search://%s?delay=%s", search, delay)
                        //.to("log:tweet")
                        .process(new MyConverter())
                        // and push tweets to all web socket subscribers on camel-tweet
                        .to("file:data/twitter.log")
                        .to("websocket:camel-tweet?sendToAll=true");
            }
        });

        context.start();        Thread.sleep(10000000);
        //context.stop();

    }
}
