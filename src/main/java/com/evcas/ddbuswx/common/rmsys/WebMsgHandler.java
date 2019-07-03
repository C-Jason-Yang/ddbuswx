package com.evcas.ddbuswx.common.rmsys;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by noxn on 2018/1/10.
 */
@WebService(name = "WebMsgHandler", targetNamespace = "http://webservice.api.mvsp.streamax/")
public interface WebMsgHandler {

    /**
     *
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "Get")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "Get", targetNamespace = "http://webservice.api.mvsp.streamax/", className = "com.cvcas.timebus.bussystem.Get")
    @ResponseWrapper(localName = "GetResponse", targetNamespace = "http://webservice.api.mvsp.streamax/", className = "com.cvcas.timebus.bussystem.GetResponse")
    public String get(@WebParam(name = "arg0", targetNamespace = "") int arg0,
                      @WebParam(name = "arg1", targetNamespace = "") int arg1,
                      @WebParam(name = "arg2", targetNamespace = "") String arg2,
                      @WebParam(name = "arg3", targetNamespace = "") String arg3);
}
