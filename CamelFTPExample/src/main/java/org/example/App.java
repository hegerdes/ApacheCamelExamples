package org.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        String user = "pi";
        String pw = System.getenv("pi_PW");
        final String FTP_uri = "sftp:192.168.178.38:22/CamelWatch?username=" + user + "&password=" + pw;
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(FTP_uri).to("file:data/outbox");
            }
        });

        context.start();
        Thread.sleep(100000);

        // stop the CamelContext
        //context.stop();
    }
}
