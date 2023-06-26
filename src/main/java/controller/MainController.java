package controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class MainController {
    public static Date getExpirationDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 20);
        return c.getTime();
    }

}
