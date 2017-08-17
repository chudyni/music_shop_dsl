package musicshop.endpoint;

import musicshop.domain.MusicItemsPackage;
import musicshop.domain.item.Fender;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.messaging.Message;

import java.util.List;

@MessageEndpoint
public class FenderAggregator {
	
	private static final Logger log = Logger.getLogger(FenderAggregator.class);

	@Aggregator(inputChannel = "signedFenderChannel", outputChannel = "endChannel")
	public MusicItemsPackage completeFenders(final List<Fender> fendersList) {
//	public MusicItemsPackage completeFenders(final List<MusicItem> fendersList) {
		log.info("Completing all Fender guitars");
		
		final MusicItemsPackage itemsPackage = new MusicItemsPackage();
		itemsPackage.setItems(fendersList);
		itemsPackage.setName("Fender package");
		return itemsPackage;
	}
	
	@ReleaseStrategy
	public boolean realeaseChecker(List<Message<?>> messages) {
		return messages.size() >= 1;
	}
}
