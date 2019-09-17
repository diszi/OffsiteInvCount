package d2.hu.offsiteinvcount.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnvironmentTool {

    public static String convertDate(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }



    //barcode scan
    public static String scannedDataVerification_partnumber(String scanData){
        String validpartnum=null;

        //System.out.println(" 1.SCANNED DATA length="+scanData.length());

        if (scanData.length()>36){

            validpartnum = getPartNum_oldbarcode(scanData.substring(0,36));
        }else{
            validpartnum = scanData;
        }

        // System.out.println(" 4.VALID PART NUMBER = "+validpartnum);
        return validpartnum;
    }


    public static String getPartNum_oldbarcode(String partnumber_old){

        String validPartnum= null;

        int  counter=partnumber_old.length()-1;

        //System.out.println(" 2. partnum_old="+partnumber_old+"; length="+partnumber_old.length()+"; counter="+counter);

        while (counter>=0){

            if (partnumber_old.charAt(counter) == ' '){
                //System.out.println(" == SPACE");

            }else{
                //System.out.println(" != SPACE : "+partnumber_old.charAt(counter));

                break;
            }
            counter--;

        }

        validPartnum = partnumber_old.substring(0,counter+1);

        // System.out.println(" 3. counter="+counter);

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

    public static String convertDateTimeStringEN(String createdDate){
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

    }

}
