package d2.hu.offsiteinvcount.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import d2.hu.offsiteinvcount.BuildConfig;

public class EnvironmentTool {


    //get app version
    public static String getVersionApp(){
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        return versionName;
    }




   /* public static String convertDate(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }*/



    //barcode scan
    public static String scannedDataVerification_partnumber(String scanData){
        String validpartnum=null;

        if (scanData.length()>36){

            validpartnum = getPartNum_oldbarcode(scanData.substring(0,36));
        }else{
            validpartnum = scanData;
        }

        return validpartnum;
    }


    private static String getPartNum_oldbarcode(String partnumber_old){

        String validPartnum= null;

        int  counter=partnumber_old.length()-1;


        while (counter>=0){

            if (partnumber_old.charAt(counter) == ' '){

            }else{

                break;
            }
            counter--;

        }

        validPartnum = partnumber_old.substring(0,counter+1);


        return validPartnum;
    }


    public static String convertDateTimeString(String createdDate){
        SimpleDateFormat inFormat = new SimpleDateFormat(UIConstans.DATE_PATTERN_STANDARD); //datePattern
        SimpleDateFormat outFormat = new SimpleDateFormat(UIConstans.DATE_PATTERN_HU);
        Date destDate  = null;
        try{
            destDate = inFormat.parse(createdDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outFormat.format(destDate);
    }

    /*public static String convertDateTimeStringEN(String createdDate){
        SimpleDateFormat inFormat = new SimpleDateFormat(UIConstans.DATE_PATTERN_STANDARD); //datePattern
        SimpleDateFormat outFormat = new SimpleDateFormat(UIConstans.DATE_PATTERN_EN);
        Date destDate  = null;
        try{
            destDate = inFormat.parse(createdDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outFormat.format(destDate);
    }

    public static String convertDateString(String date){
        SimpleDateFormat inFormat = new SimpleDateFormat(UIConstans.DATE_PATTERN_STANDARD); //datePattern
        SimpleDateFormat outFormat = new SimpleDateFormat(UIConstans.DATE_PATTERN_INBOUND);
        Date destDate  = null;
        try{
            destDate = inFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outFormat.format(destDate);
    }



    public static String encodeStringtoUTF8(String defaultURL){
        //System.out.println(" -----------> encode to UTF-8");
        String returnValue="";
        try {

            byte [] b = defaultURL.getBytes(StandardCharsets.UTF_8);
            String value= new String(b,"UTF-8");
            returnValue = value;
            // returnValue = value.replaceAll(" ","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(" ENCODED VALUE = "+returnValue);
        return returnValue;

    }*/

}
