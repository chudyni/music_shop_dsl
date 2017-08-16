package musicshop.run;

import musicshop.domain.MusicItemsPackage;
import musicshop.domain.item.Guitar;
import musicshop.endpoint.GuitarRouter;
import musicshop.endpoint.GuitarSplitter;
import musicshop.endpoint.MusicItemPackageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

/**
 * Created by marcin.bracisiewicz
 */
@Configuration
@EnableIntegration
//@IntegrationComponentScan(basePackages = "musicshop.endpoint")
@IntegrationComponentScan
@ComponentScan(basePackages = "musicshop.endpoint")
public class Config {

    @MessagingGateway
    public interface MusicItemsPackageGateway {

        @Gateway(requestChannel = "musicItemsPackageChannel")
        void gatherMusicItemPackage(final MusicItemsPackage musicItemPackage);
    }

    //to remove
    @Bean
    public MessageChannel guitarsChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fenderChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel gibsonChannel() {
        return new DirectChannel();
    }


    //end to remove

    @Bean
    public IntegrationFlow gather() {
        return IntegrationFlows.from("musicItemsPackageChannel")
                .filter(new MusicItemPackageFilter())
                .split(new GuitarSplitter())
                .route(new GuitarRouter())
                .handle(System.out::println)
                .get();
    }

}
