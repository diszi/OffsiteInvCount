package d2.hu.offsiteinvcount.ui.scan;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.ScannerConfig;
import com.symbol.emdk.barcode.ScannerException;
import com.symbol.emdk.barcode.ScannerResults;
import com.symbol.emdk.barcode.StatusData;

import java.util.ArrayList;

public class BarcodeScanner implements EMDKManager.EMDKListener, Scanner.StatusListener, Scanner.DataListener {


    // Declare variables to call interface class
    private static final int I_ON_DATA = 0;
    private static final int I_ON_STATUS = 1;
    private static final int I_ON_ERROR = 2;


    private static EMDKManager emdkManager = null;  // Declare variable to store EMDKManager object
    private static BarcodeManager barcodeManager; // Declare variable to store BarcodeManager object

    private static Scanner scanner = null; //Declare variable to hold scanner device to scan

    private static IOnScannerEvent mUIactivity = null; // Declare scanner event listener for UI activity
    private static Handler mScanHandler; // Handler to call listener
    private static IOnScannerEventRunnable mEventRunnable; // Runnable will be called on mScanHandler
    private static BarcodeScanner mBarcodeScanner;



    public static BarcodeScanner getInstance(Context context){
        if (mBarcodeScanner == null){
            mBarcodeScanner = new BarcodeScanner(context);
        }
        return mBarcodeScanner;
    }

    private BarcodeScanner(Context context){
        EMDKResults results = EMDKManager.getEMDKManager(context, this);


        // Check the return status of getEMDKManager
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            System.out.println ( "EMDKManager Request Failed");
        }
        // create Handler to move data from Scanner object to an object on the UI thread
        // i.e.: Handler (android.os.Handler) {beeae42}
        mScanHandler = new Handler(Looper.getMainLooper());
        //create runnable object
        //i.e.: com.BarcodeClassSample.BarcodeScanner$IOnScannerEventRunnable@6c47e53
        mEventRunnable = new IOnScannerEventRunnable();
    }

    //Method to release the EMDK manager
    public static void releaseEmdk() {
        System.out.println("1. releaseEmdk = "+mBarcodeScanner+"; emdkManager = "+emdkManager);
        if (emdkManager != null) {
            emdkManager.release();
            emdkManager = null;
        }
        mBarcodeScanner = null;
        System.out.println("2. releaseEmdk = "+mBarcodeScanner+"; emdkManager = "+emdkManager);
    }


    public static void registerUIobject(IOnScannerEvent UIactivity) {
        mUIactivity = UIactivity;
    }

    public static void  unregisterUIobject() {
        mUIactivity = null;
    }
    @Override
    public void onOpened(EMDKManager emdkManager) {
        System.out.println(" ---> BarcodeScanner > onOpened");
        this.emdkManager = emdkManager;


        deInitScanner();
        initializeScanner();
        setProfile();

    }

    @Override
    public void onClosed() {
        if (emdkManager != null) {
            emdkManager.release();
            emdkManager = null;
        }
        mBarcodeScanner = null;
    }

    @Override
    public void onData(ScanDataCollection scanDataCollection) {

        if (scanDataCollection != null
                && scanDataCollection.getResult() == ScannerResults.SUCCESS) {
            ArrayList<ScanDataCollection.ScanData> scanData = scanDataCollection.getScanData();
            if (scanData != null && scanData.size() > 0) {
                final ScanDataCollection.ScanData data = scanData.get(0);
                callIOnScannerEvent(I_ON_DATA, data.getData(), null);
            }
        }
    }

    @Override
    public void onStatus(StatusData statusData) {
        String statusStr = "";
        StatusData.ScannerStates state = statusData.getState();
        switch (state) {
            case IDLE: //Scanner is IDLE - this is when to request a read
                statusStr = "Scanner enabled and idle";
                try {
                    if (scanner.isEnabled() && !scanner.isReadPending()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(" onStatus => scanner = "+scanner);

                        scanner.read();
                    }
                } catch (ScannerException e) {
                    System.out.println ( "onStatus() - ScannerException " + e.getMessage());
                    e.printStackTrace();
                    statusStr = e.getMessage();
                }
                break;
            case SCANNING: //Scanner is SCANNING
                statusStr = "Scanner beam is on, aim at the barcode";
                break;
            case WAITING: //Scanner is waiting for trigger press
                statusStr = "Waiting for trigger, press to scan barcode";
                break;
            case DISABLED: //Scanner is disabled
                statusStr = "Scanner is not enabled";
                break;
            case ERROR: //Error occurred
                statusStr = "Error occurred during scanning";
                break;
        }
        //Return result to populate UI thread
        System.out.println(" STATUS = "+statusStr);
        callIOnScannerEvent(I_ON_STATUS, null, statusStr);
    }


    private void initializeScanner(){
        //code39
        try {
            //version 1
            //Get instance of the Barcode Manager object
//            barcodeManager = (BarcodeManager) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.BARCODE);
//            //Using the default scanner device to scan barcodes
//            scanner = barcodeManager.getDevice(BarcodeManager.DeviceIdentifier.DEFAULT);
//            //Add data and status listeners
//            scanner.addDataListener(this);
//            scanner.addStatusListener(this);
//            scanner.triggerType = Scanner.T   riggerType.HARD; //The trigger type is set to HARD by default
//            scanner.enable(); //Enable the scanner

            //version 2
            barcodeManager = (BarcodeManager) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.BARCODE);




            scanner = barcodeManager.getDevice(BarcodeManager.DeviceIdentifier.DEFAULT);
            scanner.triggerType = Scanner.TriggerType.HARD; //The trigger type is set to HARD by default


            scanner.disable();


            if (scanner != null){
                //Add data and status listeners


                scanner.addDataListener(this);
                scanner.addStatusListener(this);
                try{
                    scanner.enable(); //Enable the scanner
                }catch (ScannerException e){
                    e.printStackTrace();
                }
            }

        } catch (ScannerException e) {
            System.out.println ( "initializeScanner() - ScannerException " + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception ex) {
            System.out.println ( "initializeScanner() - Exception " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void setProfile(){
        try {


            ScannerConfig config = scanner.getConfig();
            config.decoderParams.code93.enabled = true;

            scanner.setConfig(config);
        } catch (ScannerException e) {
            e.printStackTrace();
            callIOnScannerEvent(I_ON_ERROR, null, null);
        }
    }



    // Method to de-initialize scanner
    public static void deInitScanner() {
        if (scanner != null) {
            try {
                if(scanner.isReadPending()) {
                    scanner.cancelRead();
                }
                scanner.disable();
                scanner.removeDataListener(mBarcodeScanner);
                scanner.removeStatusListener(mBarcodeScanner);
                scanner.release();
            } catch (ScannerException e) {
                System.out.println ( "deInitScanner() - ScannerException " + e.getMessage());
                e.printStackTrace();
            }
            scanner = null;
        }

        //Release instance of barcodeManager object
        if (barcodeManager != null) {
            barcodeManager = null;
        }
    }

    /**
     *
     * @param interfaceId - defined variable (I_ON_DATA, I_ON_STATUS, I_ON_ERROR)
     * @param data - null or data.getData() when scan
     * @param status - null or status which get after/before scan
     */
    private void callIOnScannerEvent(int interfaceId, String data, String status) {
        if (mUIactivity != null) {
            mEventRunnable.setDetails(interfaceId, data, status);
            mScanHandler.post(mEventRunnable);
        }
    }


    private static class IOnScannerEventRunnable implements Runnable {
        private int mInterfaceId = 0;
        private String mBarcodeData = "";
        private String mBarcodeStatus = "";

        public void setDetails(int id, String data, String statusStr) {
            mInterfaceId = id;
            mBarcodeData = data;
            mBarcodeStatus = statusStr;

        }

        @Override
        public void run() {
            if(mUIactivity!=null) {
                switch (mInterfaceId) {
                    case I_ON_DATA:
                        mUIactivity.onDataScanned(mBarcodeData);
                        break;
                    case I_ON_STATUS:
                        mUIactivity.onStatusUpdate(mBarcodeStatus);
                        break;
                    case I_ON_ERROR:
                        mUIactivity.onError();
                        break;
                }
            }
        }
    }
}
