package d2.hu.offsiteinvcount.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import d2.hu.offsiteinvcount.ui.model.Inventory;
import d2.hu.offsiteinvcount.ui.model.InventoryAsset;
import d2.hu.offsiteinvcount.ui.model.InventoryBalances;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.model.InventoryIssueReturnHistory;
import d2.hu.offsiteinvcount.ui.model.InventoryReceiptHistory;
import d2.hu.offsiteinvcount.ui.model.Item;

public class EntityMapper {


    public static List<InventoryCount> transformInventoryCountList(String body){
        //List<InventoryCount> inventoryCountBookList = new ArrayList<>();
        //System.out.println(" EntityMapper.transformInventoryCountList()");

        List<InventoryCount> inventoryCountList = new ArrayList<>();
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(body)));

            NodeList node = doc.getElementsByTagName("PLUSTCB");


            for(int i=0;i<node.getLength();i++){
                //InventoryCount countBook = transformCountBook((Element)node.item(i));
                InventoryCount inventoryCountItem = transformCountBook((Element)node.item(i));
                inventoryCountList.add(inventoryCountItem);
                // inventoryCountBookList.add(countBook);
                //System.out.println(" countBook["+i+"] = nr:"+countBook.getCountbook());
            }

            //sort list by status
            Collections.sort(inventoryCountList, new Comparator<InventoryCount>() {
                @Override
                public int compare(InventoryCount invCount1, InventoryCount invCount2) {
                    return invCount1.getStatus().compareToIgnoreCase(invCount2.getStatus());
                }
            });


        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(" InventoryCount list size = "+inventoryCountList.size());

        return inventoryCountList;


        //return inventoryCountBookList;

    }

    public static InventoryCount transformInventoryCountBookItem(String body){

        InventoryCount inventoryCount = null;
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(body)));

            NodeList node = doc.getElementsByTagName("PLUSTCB");


            for(int i=0;i<node.getLength();i++){
                inventoryCount = transformCountBook((Element)node.item(i));
            }
            //System.out.println("----> EntityMapper.transformInventoryCountBookItem = "+inventoryCount.getCountbook()+"; list size = "+inventoryCount.getCountBookLineList().size());


        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return inventoryCount;
    }


    public static List<Inventory> transformInventoryList(String body){
        // System.out.println("EntityMapper.transformInventoryList ");

        List<Inventory> inventoryList = new LinkedList<>();
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(body)));
            NodeList node = doc.getElementsByTagName("INVENTORY");
            for (int i = 0; i < node.getLength(); i++) {
                Inventory inventory = transformAllInventory((Element) node.item(i));
//                System.out.println(" ["+i+"]="+"{ partnum="+inventory.getPart_number()+"; isRotable="+inventory.isRotable()+"; rotableassetlist="+inventory.getAssetList().size()+
//                "; history size="+inventory.getHistoryList().size()+"; issue_return size="+inventory.getHistoryIssueReturnList().size()+"}");
                inventoryList.add(inventory);
            }

            // System.out.println(" Inventory list item size="+inventoryList.size());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println(" ------> GET inventory List size = "+inventoryList.size());
        return inventoryList;
    }


    public static List<InventoryReceiptHistory> transformHistoryList(String body){

        List<InventoryReceiptHistory> historyList = new LinkedList<>();
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(body)));

            NodeList node = doc.getElementsByTagName("INVENTORY");


            for (int i = 0; i < node.getLength(); i++) {
                historyList = transformHistoryListData((Element)node.item(i));
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" history size = "+historyList.size());

        return historyList;

    }

    public static List<InventoryIssueReturnHistory> transformIssueReturnList(String body){
        List<InventoryIssueReturnHistory> historyList = new LinkedList<>();

        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(body)));

            NodeList node = doc.getElementsByTagName("INVENTORY");


            for (int i = 0; i < node.getLength(); i++) {
                historyList = transformIssueReturnHistoryListData((Element)node.item(i));
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(" history -issue+return- size = "+historyList.size());

        return historyList;
    }


    public static Inventory transformSelectedInventory(String body){
        // System.out.println(" EntityMapper.transformSelectedInventory");
        Inventory inventory = null;

        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(body)));
            NodeList node = doc.getElementsByTagName("INVENTORY");
            inventory = transformInventory((Element)node.item(0));
            //  System.out.println(" ---> node inventory size="+node.getLength()+"; rotable list size="+inventory.getInvAssetList().size());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    private static Inventory transformInventory(Element element){

        //System.out.println(" -----> EntityMapper.transformInventory");
        Inventory inventory =  new Inventory();

        inventory.setPart_number(getNodeValue(element,"ITEMNUM"));
        inventory.setStoreroom(getNodeValue(element,"LOCATION"));
        inventory.setCurrent_balance(getNodeValue(element,"CURBALTOTAL"));
        inventory.setDefault_bin(getNodeValue(element,"BINNUM"));
        inventory.setStatus(getNodeValue(element,"STATUS"));

        //System.out.println("item [] = "+inventory.getPart_number()+"\t");

        NodeList invBalanceNode = element.getElementsByTagName("INVBALANCES");
        List<InventoryBalances> inventoryBalancesList = new ArrayList<>();
        for(int i=0;i<invBalanceNode.getLength();i++){
            InventoryBalances inventoryBalances = transformInvBalance((Element)invBalanceNode.item(i));
            inventoryBalancesList.add(inventoryBalances);
        }

        Collections.sort(inventoryBalancesList, new Comparator<InventoryBalances>() {
            @Override
            public int compare(InventoryBalances invbalance1, InventoryBalances invbalance2) {
                return invbalance2.getCurrBalance().compareToIgnoreCase(invbalance1.getCurrBalance());

            }
        });

        inventory.setInventoryBalanceList(inventoryBalancesList);

        NodeList itemNode = element.getElementsByTagName("ITEM");
        Item item  = transformInventoryItem((Element)itemNode.item(0));
        inventory.setItem(item);

        inventory.setDescription(item.getItem_description());
        inventory.setRotable(item.isItem_rotating());

//        List<Asset> rotablesList = new ArrayList<>();
//        System.out.print(" ---> asset is rotable = "+inventory.isRotable()+"; rotableList size="+rotablesList.size());

        List<InventoryAsset> rotablesList = new ArrayList<>();
        System.out.print(" ---> asset is rotable = "+inventory.isRotable()+"; rotableList size="+rotablesList.size());

//        if (inventory.isRotable()){
//            NodeList assetNode = element.getElementsByTagName("ASSET");
//            System.out.println("   "+inventory.getPart_number()+": asset node length= "+assetNode.getLength());
//            for (int i=0;i<assetNode.getLength();i++){
//                Asset asset = transformRotables((Element)assetNode.item(i));
//                System.out.println(inventory.getPart_number()+" -------> rotable asset ["+i+"]="+asset.getSerial_number());
//                rotablesList.add(asset);
//            }
//
//        }
        if (inventory.isRotable()){
//            NodeList aNode=element.getChildNodes();
//            for(int i=0;i<aNode.getLength();i++){
//                System.out.println("--> childnode="+aNode.item(i).getNodeName());
//            }
            NodeList assetNode = element.getElementsByTagName("ASSET");

            System.out.println("   "+inventory.getPart_number()+": asset node length= "+assetNode.getLength());
            for (int i=0;i<assetNode.getLength();i++){
                //System.out.println(" ---> assetnode parent = "+assetNode.item(i).getParentNode().getNodeName());

                if (assetNode.item(i).getParentNode().getNodeName().equals("INVENTORY")){

                    InventoryAsset inventoryAsset = transformInventoryRotables((Element)assetNode.item(i));
                    //  System.out.println(inventory.getPart_number()+" -------> rotable inv asset ["+i+"]="+inventoryAsset.getSerialnum());
                    rotablesList.add(inventoryAsset);
                }

            }

        }
        inventory.setInvAssetList(rotablesList);

        NodeList historyNode = element.getElementsByTagName("PLUSMRECEIPTINSPLINE");
        List<InventoryReceiptHistory> historyList = new ArrayList<>();
        for (int i=0;i<historyNode.getLength();i++){
            InventoryReceiptHistory history = transformHistory((Element) historyNode.item(i));
            historyList.add(history);
        }
        inventory.setHistoryList(historyList);

        NodeList historyIRNode = element.getElementsByTagName("MATUSETRANS");
        List<InventoryIssueReturnHistory> historyIssueReturnList = new ArrayList<>();
        for (int i=0;i<historyIRNode.getLength();i++){
            InventoryIssueReturnHistory historyIssueReturn = transformIssueReturnHistory((Element) historyIRNode.item(i));
            historyIssueReturnList.add(historyIssueReturn);
        }
        inventory.setHistoryIssueReturnList(historyIssueReturnList);



        return inventory;
    }


    private static InventoryBalances transformInvBalance(Element element){
        InventoryBalances invBalance = new InventoryBalances();

        invBalance.setInvBalancesID(getNodeValue(element,"INVBALANCESID"));
        invBalance.setBinnum(getNodeValue(element,"BINNUM"  ));
        invBalance.setBatch(getNodeValue(element,"LOTNUM"));
        invBalance.setCurrBalance(getNodeValue(element,"CURBAL"));
        invBalance.setExpirationDate(getNodeValue(element,"USEBY"));
        invBalance.setPhysicalCount(getNodeValue(element,"PHYSCNT"));
        invBalance.setPhysicalCountDate(getNodeValue(element,"PHYSCNTDATE"));
        //invBalance.setShelflife(getNodeValue(element,"SHELFLIFE"));
        invBalance.setReconciled(getNodeValueBoolean(element,"RECONCILED"));


        return invBalance;
    }


    private static InventoryAsset transformInventoryRotables(Element element){
        InventoryAsset asset = new InventoryAsset();

        asset.setAssetnum(getNodeValue(element,"ASSETNUM"));
        asset.setSerialnum(getNodeValue(element,"SERIALNUM"));
        asset.setDescription(getNodeValue(element,"DESCRIPTION"));
        asset.setRegistration(getNodeValue(element,"PLUSAREG"));
        asset.setStatus(getNodeValue(element,"STATUS"));


        return asset;
    }


    private static List<InventoryIssueReturnHistory> transformIssueReturnHistoryListData(Element element){
        List<InventoryIssueReturnHistory> inventoryIssueReturnHistoryList = new ArrayList<>();
        NodeList historyNode = element.getElementsByTagName("MATUSETRANS");

        for (int i=0;i<historyNode.getLength();i++){
            InventoryIssueReturnHistory history = transformIssueReturnHistory((Element) historyNode.item(i));
            //System.out.println(" history["+i+"]= ["+history.getReceipt_insp_num()+"; "+history.getSerial_num()+"]");
            inventoryIssueReturnHistoryList.add(history);
        }
        return inventoryIssueReturnHistoryList;

    }

    private static InventoryIssueReturnHistory transformIssueReturnHistory(Element element){

        InventoryIssueReturnHistory history = new InventoryIssueReturnHistory();

        history.setTransDate(getNodeValue(element,"TRANSDATE"));
        history.setTransType(getNodeValue(element,"ISSUETYPE"));
        history.setQuantity(getNodeValue(element,"QUANTITY"));
        history.setWorkorder(getNodeValue(element,"REFWO"));
        history.setAsset_num(getNodeValue(element,"ASSETNUM"));
        history.setIssueTo(getNodeValue(element,"ISSUETO"));
        history.setStoreroom(getNodeValue(element,"STORELOC"));

        history.setBatch(getNodeValue(element,"LOTNUM"        ));
        history.setEnteredBy(getNodeValue(element,"ENTERBY"));

        //System.out.println(" ASSETNUM = "+history.getAsset_num());
        if (history.getAsset_num().isEmpty()){
            // System.out.println(" isEmpty -----> nem ASSET (rotable) ==> assetnum = /üres/");

        }else{
            NodeList assetNode = element.getElementsByTagName("ASSET");
            // System.out.println(" ASSET NODE SIZE = "+assetNode.getLength());
            //repulogephez tartozo adatok
            InventoryIssueReturnHistory.MatusetransAsset matusetransAsset = transformMatuseTransAsset((Element)assetNode.item(0));
            history.setMatusetransAsset(matusetransAsset);
            //System.out.println(" --> MATUSETRANS.ASSET  = "+matusetransAsset.getAssetnum()+"; "+matusetransAsset.getSerialnum());
        }
        //System.out.println(" MATUSETRANS = "+history.getMatusetransAsset());

        return history;
    }

    private static InventoryIssueReturnHistory.MatusetransAsset transformMatuseTransAsset(Element element){
        InventoryIssueReturnHistory.MatusetransAsset asset = new InventoryIssueReturnHistory.MatusetransAsset();

        asset.setAssetnum(getNodeValue(element,"ASSETNUM"));
        asset.setDescription(getNodeValue(element,"DESCRIPTION"));
        asset.setRegistration(getNodeValue(element,"PLUSAREG"));
        asset.setSerialnum(getNodeValue(element,"SERIALNUM"));

        return asset;
    }


    private static List<InventoryReceiptHistory> transformHistoryListData(Element element){

        List<InventoryReceiptHistory> inventoryHistoryList = new ArrayList<>();
        NodeList historyNode = element.getElementsByTagName("PLUSMRECEIPTINSPLINE");

        for (int i=0;i<historyNode.getLength();i++){
            InventoryReceiptHistory history = transformHistory((Element) historyNode.item(i));
            //System.out.println(" history["+i+"]= ["+history.getReceipt_insp_num()+"; "+history.getSerial_num()+"]");
            inventoryHistoryList.add(history);
        }
        return inventoryHistoryList;

    }


    private static InventoryReceiptHistory transformHistory(Element element){
        InventoryReceiptHistory history = new InventoryReceiptHistory();

        history.setPonum(getNodeValue(element,"PONUM"));
        history.setReceipt_insp_num(getNodeValue(element,"RECEIPTINSPNUM"));
        history.setReceipt_date(getNodeValue(element,"ACTUALDATE")); //STATUSDATE

        history.setInspection_status(getNodeValue(element,"STATUS"));

        history.setSerial_num(getNodeValue(element,"SERIALNUM"));
        history.setLine(getNodeValue(element,"RECEIPTINSPLINENUM"));

        history.setDescription(getNodeValue(element,"DESCRIPTION"));
        history.setAsset_num(getNodeValue(element,"ASSETNUM"));
        history.setInspectedBy(getNodeValue(element,"INSPECTEDBY"));
        history.setEnterby(getNodeValue(element,"ENTERBY"));
        history.setInspection_date(getNodeValue(element,"INSPECTIONDATE"));
        history.setStatus_changedby(getNodeValue(element,"STATUSCHANGEBY"));
        history.setTolocation(getNodeValue(element,"TOLOCATION"));
        history.setToStoreloc(getNodeValue(element,"TOSTORELOC"));

        //4 quantity
        history.setQuantity_serviceable(getNodeValue(element,"SERVICEABLEQTY"));
        history.setQuantity_quarantined(getNodeValue(element,"QUARANTINEQTY"));
        history.setQuantity_returned(getNodeValue(element,"RETURNQTY"));
        history.setQuantity_scrapped(getNodeValue(element,"SCRAPQTY"));
        return history;

    }



    private static Inventory transformAllInventory(Element element){
        Inventory inventory =  new Inventory();

        inventory.setPart_number(getNodeValue(element,"ITEMNUM"));
        inventory.setStoreroom(getNodeValue(element,"LOCATION"));
        inventory.setCurrent_balance(getNodeValue(element,"CURBALTOTAL"));
        inventory.setDefault_bin(getNodeValue(element,"BINNUM"));
        inventory.setStatus(getNodeValue(element,"STATUS"));

        NodeList itemNode = element.getElementsByTagName("ITEM");
        Item item  = transformInventoryItem((Element)itemNode.item(0));
        inventory.setDescription(item.getItem_description());

        return inventory;
    }

    private static Item transformInventoryItem(Element element){
        Item item = new Item();
        item.setItem_description(getNodeValue(element,"DESCRIPTION"));
        item.setItem_rotating(getNodeValueBoolean(element,"ROTATING"));

        return item;
    }

    private static InventoryCount transformCountBook(Element element){

        InventoryCount inventoryCount = new InventoryCount();

        inventoryCount.setCountbook(getNodeValue(element,"COUNTBOOKNUM"));
        inventoryCount.setStatus(getNodeValue(element,"STATUS"));
        inventoryCount.setStoreroom(getNodeValue(element,"STOREROOM"));
        inventoryCount.setCountBookID(getNodeValue(element,"PLUSTCBID"));

        NodeList countBookLineNode = element.getElementsByTagName("PLUSTCBLINES");
        List<InventoryCount.CountBookLine> countBookLineList = new ArrayList<>();
        for(int i=0;i<countBookLineNode.getLength();i++){
            InventoryCount.CountBookLine countBookLine = transformCountBookLine((Element)countBookLineNode.item(i));
//            System.out.println("CountBookLine_item["+i+"]= ["+countBookLine.getPartnumber()+"; "+countBookLine.isReconcile()+"; "+countBookLine.getEquipment()+"; "
//                    +"; "+countBookLine.getPhysicalCount()+"; "+countBookLine.getSerialnumber()+"]");
            countBookLineList.add(countBookLine);
        }
        inventoryCount.setCountBookLineList(countBookLineList);


        return inventoryCount;

    }

    private static InventoryCount.CountBookLine transformCountBookLine(Element element){

        InventoryCount.CountBookLine countBookLine = new InventoryCount.CountBookLine();

        countBookLine.setBin(getNodeValue(element,"BINNUM"));
        countBookLine.setBatch(getNodeValue(element,"LOTNUM"));
        countBookLine.setPartnumber(getNodeValue(element,"ITEMNUM"));
        countBookLine.setRotable(getNodeValueBoolean(element,"ROTATING"));
        countBookLine.setSerialnumber(getNodeValue(element,"SERIALNUM"));
        countBookLine.setEquipment(getNodeValue(element,"ASSETNUM"));
        countBookLine.setCurrentBalance(getNodeValue(element,"CURBAL"));
        countBookLine.setPhysicalCount(getNodeValue(element,"PHYSCNT"));
        countBookLine.setReconcile(getNodeValueBoolean(element,"RECON"));
        countBookLine.setId(getNodeValue(element,"PLUSTCBLINESID"));
        countBookLine.setCountedBy(getNodeValue(element,"PHYSCNTBY"));
        countBookLine.setCountedDate(getNodeValue(element,"PHYSCNTDATE"));

        //System.out.println(" transformCountBookLine ---> set [ pn="+countBookLine.getPartnumber()+"; eq="+countBookLine.getEquipment()+"; phycount="+countBookLine.getPhysicalCount()+"; countedby="+countBookLine.getCountedBy()+"]");

        return countBookLine;
    }

    private static String getNodeValue(Element element, String tag) {

        return element.getElementsByTagName(tag).item(0).getTextContent();
    }


    private  static boolean getNodeValueBoolean(Element element, String tag){
        return element.getElementsByTagName(tag).item(0).getTextContent().equals("1") ;
    }


}
