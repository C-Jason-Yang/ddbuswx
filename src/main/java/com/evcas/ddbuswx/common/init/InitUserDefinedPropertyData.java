package com.evcas.ddbuswx.common.init;

import com.evcas.ddbuswx.tcp.DiscardServer;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

/**
 * Created by noxn on 2018/6/11.
 */
@Log4j2
//@Order(1)
public class InitUserDefinedPropertyData implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        log.info("tcp 8001 start  .......");
        int port = 8001;
        new DiscardServer(port).run();
    }
}
