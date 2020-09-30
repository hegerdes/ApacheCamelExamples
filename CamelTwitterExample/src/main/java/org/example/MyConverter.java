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

        //Crap but the native java Date parsing did not work for some reason
        sub = sub.replace("Jan","01");
        sub = sub.replace("Feb","02");
        sub = sub.replace("Mar","03");
        sub = sub.replace("Apr","04");
        sub = sub.replace("May","05");
        sub = sub.replace("Jun","06");
        sub = sub.replace("Jul","07");
        sub = sub.replace("Aug","08");
        sub = sub.replace("Sep","09");
        sub = sub.replace("Oct","10");
        sub = sub.replace("Nov","11");
        sub = sub.replace("Dec","12");
        Date date = new SimpleDateFormat("MM dd HH:mm:ss yyyy", Locale.GERMAN).parse(sub);

        String newmsg = new SimpleDateFormat().format(date) + " " + msg;
        exchange.getIn().setBody(newmsg);
    }
}
