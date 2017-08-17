package musicshop.run;

import musicshop.domain.MusicItemsPackage;
import musicshop.domain.item.Guitar;
import musicshop.domain.item.GuitarMark;
import musicshop.endpoint.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MusicItemPackageFilter musicItemPackageFilter;

    @Autowired
    private GuitarSplitter guitarSplitter;

    @Autowired
    private GuitarRouter guitarRouter;

    @Autowired
    private GibsonTransformer gibsonTransformer;

    @Autowired
    private FenderTransformer fenderTransformer;

    @Autowired
    private GibsonAggregator gibsonAggregator;

    @Autowired
    private FenderAggregator fenderAggregator;

    @MessagingGateway
    public interface MusicItemsPackageGateway {

        @Gateway(requestChannel = "musicItemsPackageChannel")
        void gatherMusicItemPackage(final MusicItemsPackage musicItemPackage);
    }

    //to remove ?? - how get channels ?
//    @Bean
//    public MessageChannel fenderChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageChannel gibsonChannel() {
//        return new DirectChannel();
//    }
    //end to remove

    @Bean
    public IntegrationFlow gather() {
        return IntegrationFlows.from("musicItemsPackageChannel")
                .filter(this.musicItemPackageFilter)
                .split(this.guitarSplitter)
//                .route(this.guitarRouter)

                //WHAT IS THIS SHIT ?
                .<Guitar, GuitarMark>route(Guitar::getMark, mapping -> mapping
                     .subFlowMapping(String.valueOf(GuitarMark.GIBSON), subflow ->
                        subflow.transform(this.gibsonTransformer))
                     .subFlowMapping(String.valueOf(GuitarMark.FENDER), subflow ->
                        subflow.transform(this.fenderTransformer)))
                //END

                .handle(System.out::println)
                .get();
    }

}
