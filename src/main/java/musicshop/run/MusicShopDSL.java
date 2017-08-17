package musicshop.run;

import musicshop.domain.MusicItemsPackage;
import musicshop.domain.item.Guitar;
import musicshop.domain.item.GuitarMark;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marcin.bracisiewicz
 */
@EnableAutoConfiguration
public class MusicShopDSL {

    public static void main(String[] args) throws InterruptedException {
        final ConfigurableApplicationContext context = SpringApplication.run(Config.class, args);

        final MusicItemsPackage musicItemPackage = prepareGuitarPackage();
        final Config.MusicItemsPackageGateway gateway = context.getBean(Config.MusicItemsPackageGateway.class);

        gateway.gatherMusicItemPackage(musicItemPackage);

        context.close();
    }

    private static MusicItemsPackage prepareGuitarPackage() {
        final Guitar guitar1 = new Guitar(GuitarMark.GIBSON, "SG 400");
        final Guitar guitar2 = new Guitar(GuitarMark.GIBSON, "Les Paul Classic");
        final Guitar guitar3 = new Guitar(GuitarMark.FENDER, "Telecaster");

        final List<Guitar> guitars = new ArrayList<Guitar>(Arrays.asList(guitar1, guitar2, guitar3));


        final MusicItemsPackage musicItemsPackage = new MusicItemsPackage();
        musicItemsPackage.setItems(guitars);

        return musicItemsPackage;
    }

}
