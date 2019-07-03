package com.evcas.ddbuswx.common.rmsys;

/**
 * Created by noxn on 2018/1/10.
 */
public class WebServiceClient {

    private static BasicService service;
    private static BusinessService businessService;
    private static WebMsgHandler portType;
    private static WebMsgHandler businessPortType;
    static{
        try{
            service = new BasicService();
            businessService = new BusinessService();
            portType = service.getServicePort();
            businessPortType = businessService.getServicePort();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getBaseData(int msgType,int transNo,String regKey,String jsonMsg){
        return portType.get(msgType,transNo,regKey,jsonMsg);
    }

    public String getBusinessData(int msgType,int transNo,String regKey,String jsonMsg){
        return businessPortType.get(msgType,transNo,regKey,jsonMsg);
    }
}
