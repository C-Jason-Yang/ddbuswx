package com.evcas.ddbuswx;

import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.model.YsHyLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@RunWith(SpringRunner.class)
public class DdbuswxApplicationTests {
/*

    @Test
    public void contextLoads() {

        String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><lines><lineCode>1</lineCode><lineType>1</lineType><lineTypeName>普通线</lineTypeName></lines><lines><lineCode>2</lineCode><lineType>1</lineType><lineTypeName>普通线</lineTypeName></lines><lines><lineCode>3</lineCode><lineType>1</lineType><lineTypeName>普通线</lineTypeName></lines><lines><lineCode>5</lineCode><lineType>1</lineType><lineTypeName>普通线</lineTypeName></lines><lines><lineCode>6</lineCode><lineType>1</lineType><lineTypeName>普通线</lineTypeName></lines><lines><lineCode>7</lineCode><lineType>1</lineType><lineTypeName>普通线</lineTypeName></lines><lines><lineCode>8</lineCode><lineType>1</lineType><lineTypeName>普通线</lineTypeName></lines></root>";
        try {
            JAXBContext context = JAXBContext.newInstance(YsHyLine.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            YsHyLine root = (YsHyLine) unmarshaller.unmarshal(new StringReader(test));
            System.out.println(JsonTools.gson.toJson(root));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

}
