package pdtg.lsmsjms;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class LsMsJmsApplication {

    public static void main(String[] args) throws Exception {

        Random rand = new Random();
        ActiveMQServer server = ActiveMQServers.newActiveMQServer( new ConfigurationImpl()
                .setPersistenceEnabled(false)
                .setJournalDirectory("build/data/journal")
                .setSecurityEnabled(false)
                .addAcceptorConfiguration("invm","vm://"+rand.nextInt(100)));

        server.start();
        SpringApplication.run(LsMsJmsApplication.class, args);
    }

}
