package org.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyConverter implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String data = exchange.getIn().getBody(String.class);
        //Sun Dec 08 23:51:03 CET 2019
        System.out.println(data);
        String sub = data.substring(4,19) + data.substring(24,29);
        String msg = data.substring(29);

        sub = sub.replace("Jan","01");
        sub = sub.replace("Feb","02");
        sub = sub.replace("Mar","03");
        sub = sub.replace("Apr","04");
        sub = sub.replace("Dec","12");
        Date date = new SimpleDateFormat("MM dd HH:mm:ss yyyy", Locale.GERMAN).parse(sub);

        String newmsg = new SimpleDateFormat().format(date) + " " + msg;
        exchange.getIn().setBody(newmsg);
    }
}
