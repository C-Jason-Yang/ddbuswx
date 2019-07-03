package com.evcas.ddbuswx.common.init;

import com.evcas.ddbuswx.tcp.DiscardServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * Created by noxn on 2018/6/11.
 */
@Order(1)
public class InitUserDefinedPropertyData implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        int port = 8001;
        new DiscardServer(port).run();
    }
}
