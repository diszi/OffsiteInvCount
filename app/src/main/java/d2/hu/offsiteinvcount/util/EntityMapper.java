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
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import d2.hu.offsiteinvcount.ui.model.InventoryCount;

public class EntityMapper {


    public static List<InventoryCount> transformInventoryCountList(String body){

        List<InventoryCount> inventoryCountList = new ArrayList<>();
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(body)));
            NodeList node = doc.getElementsByTagName("PLUSTCB");
           // System.out.println(" -----> EntityMapper.transformInventoryCountList");
            for(int i=0;i<node.getLength();i++){
                InventoryCount inventoryCountItem = transformCountBook((Element)node.item(i));
                inventoryCountList.add(inventoryCountItem);
            }
            //sort list by status
            Collections.sort(inventoryCountList, (invCount1, invCount2) -> invCount1.getStatus().compareToIgnoreCase(invCount2.getStatus()));

        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return inventoryCountList;

    }

    public static InventoryCount transformInventoryCountBookItem(String body){

        InventoryCount inventoryCount = null;
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(body)));
            NodeList node = doc.getElementsByTagName("PLUSTCB");
           // System.out.println(" ------> EntityMapper.transformInventoryCountBookItem");
            for(int i=0;i<node.getLength();i++){
                inventoryCount = transformCountBook((Element)node.item(i));
            }

        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return inventoryCount;
    }


    private static InventoryCount transformCountBook(Element element){
        System.out.println(" -------------> transformCountBook");
        InventoryCount inventoryCount = new InventoryCount();

        inventoryCount.setCountbook(getNodeValue(element,"COUNTBOOKNUM"));
        inventoryCount.setStatus(getNodeValue(element,"STATUS"));
        inventoryCount.setStoreroom(getNodeValue(element,"STOREROOM"));
        inventoryCount.setCountBookID(getNodeValue(element,"PLUSTCBID"));

        NodeList countBookLineNode = element.getElementsByTagName("PLUSTCBLINES");
        List<InventoryCount.CountBookLine> countBookLineList = new ArrayList<>();
        //System.out.println(" ---> line node size = "+countBookLineNode.getLength());
        for(int i=0;i<countBookLineNode.getLength();i++){
            InventoryCount.CountBookLine countBookLine = transformCountBookLine((Element)countBookLineNode.item(i));
           // System.out.println("LINE["+i+"] = "+countBookLine.getPartnumber()+" - "+countBookLine.getSerialnumber()+" - "+countBookLine.getBatch());
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

        return countBookLine;
    }

    private static String getNodeValue(Element element, String tag) {

        return element.getElementsByTagName(tag).item(0).getTextContent();
    }


    private  static boolean getNodeValueBoolean(Element element, String tag){
        return element.getElementsByTagName(tag).item(0).getTextContent().equals("1") ;
    }


}
