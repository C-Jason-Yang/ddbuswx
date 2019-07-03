package com.evcas.ddbuswx.common.rmsys;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by noxn on 2018/1/10.
 */
@WebServiceClient(name = "ServiceService", targetNamespace = "http://webservice.api.mvsp.streamax/", wsdlLocation = "http://103.14.132.13:6006/ServicePort?wsdl")
public class BasicService extends Service {

    private final static URL SERVICESERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = BasicService.class.getResource(".");
            url = new URL(baseUrl, "http://103.14.132.13:6006/ServicePort?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SERVICESERVICE_WSDL_LOCATION = url;
    }

    public BasicService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BasicService() {
        super(SERVICESERVICE_WSDL_LOCATION, new QName("http://webservice.api.mvsp.streamax/", "ServiceService"));
    }

    /**
     *
     * @return returns WebMsgHandler
     */
    @WebEndpoint(name = "ServicePort")
    public WebMsgHandler getServicePort() {
        return super.getPort(new QName("http://webservice.api.mvsp.streamax/",
                "ServicePort"), WebMsgHandler.class);
    }
}
