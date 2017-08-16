package musicshop.endpoint;

import musicshop.domain.MusicItemsPackage;
import musicshop.domain.item.Guitar;
import musicshop.domain.item.MusicItem;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;

import java.util.List;

@MessageEndpoint
public class MusicItemPackageFilter {
	
	private static final Logger log = Logger.getLogger(MusicItemPackageFilter.class);

	@Filter(inputChannel = "musicItemsPackageChannel", outputChannel = "guitarsChannel")
	public boolean guitarsOnly(final MusicItemsPackage musicItemsPackage) {
		log.info("---MusicItemPackageFilter---");
		final List<? extends MusicItem> itemsList = musicItemsPackage.getItems();
		for(MusicItem item : itemsList) {
			if(!this.isGuitar(item)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isGuitar(final MusicItem item) {
		return item instanceof Guitar;
	}
}
