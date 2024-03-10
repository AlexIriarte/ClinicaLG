/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DateString {

    public static String StringToDateSQL;

    public static Calendar StringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(format.parse(date));
            return dt;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DateToString(Calendar date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String dt = format.format(date);
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fechaActual() {
        LocalDateTime hoy = LocalDateTime.now();
        System.out.println(hoy.getDayOfYear() + "-" + hoy.getDayOfMonth() + "-" + hoy.getDayOfWeek());
        return hoy.getDayOfYear() + "-" + hoy.getDayOfMonth() + "-" + hoy.getDayOfWeek();
    }

    public static Calendar StringToDatetime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(format.parse(date));
            return dt;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DatetimeToString(Calendar date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String dt = format.format(date);
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date StringToDateSQL(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date(format.parse(date).getTime());
        return dt;
    }
     public static Time StringToTimeSQL(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date dt = new Date(format.parse(time).getTime());
        return new Time(dt.getTime());
    }

    public static String DateSQLToString(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dt = format.format(date);
        return dt;
    }

    public static Timestamp StringToDatetimeSQL(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Timestamp ts = new Timestamp(format.parse(date).getTime());
        return ts;
    }

    public static String DatetimeSQLToString(Timestamp date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String dt = format.format(date);
        return dt;
    }

    public static List<String> toStringVector(ArrayList<String[]> lista) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            String[] dataActual = lista.get(i);
            data.add(Arrays.toString(dataActual));
        }
        return data;
    }
}
