package com.evcas.ddbuswx.common.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/1/12.
 */
public class XmlUtil {

    @SuppressWarnings("unchecked")
    public static <T> T xmlListToList(String xmlStr, Class<T> classOfT) {
        try {
            JAXBContext context = JAXBContext.newInstance(classOfT);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xmlStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> xmlToMap(String xmlStr) {
        StringReader read = new StringReader(xmlStr);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        Map<String, String> result = new HashMap<>();
        InputSource source = new InputSource(read);
        try {
            try {
                //创建一个新的SAXBuilder
                SAXBuilder asxB = new SAXBuilder();
                //通过输入源构造一个Document
                Document doc = asxB.build(source);
                //取的根元素
                Element root = doc.getRootElement();
                List childrenNode = root.getChildren();
                result = test(childrenNode, result);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> test(List childrenNode, Map<String, String> result) {
        for (Object o : childrenNode) {
            Element et = (Element) o;//循环依次得到子元素
            List tempChildrenNode = et.getChildren();
            if (tempChildrenNode != null && tempChildrenNode.size() > 0) {
                result = test(tempChildrenNode, result);
            }
            result.put(et.getName(), et.getText());
        }
        return result;
    }
}
