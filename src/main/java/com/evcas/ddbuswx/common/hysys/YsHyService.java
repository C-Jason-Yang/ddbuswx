package com.evcas.ddbuswx.common.hysys;

/**
 * Created by noxn on 2018/1/12.
 */
public class YsHyService {


    public static MyServiceStub myServiceStub = null;

    static {
        try {
            myServiceStub = new MyServiceStub();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
