package com.evcas.ddbuswx.common.lxhysys;

/**
 * Created by noxn on 2018/1/17.
 */
public class LxHyService {

    public static MyServiceStub myServiceStub = null;

    static {
        try {
            myServiceStub = new MyServiceStub();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
