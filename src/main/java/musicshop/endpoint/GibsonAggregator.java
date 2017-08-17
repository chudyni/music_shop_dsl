package musicshop.endpoint;

import musicshop.domain.MusicItemsPackage;
import musicshop.domain.item.Gibson;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.messaging.Message;

import java.util.List;

@MessageEndpoint
public class GibsonAggregator {
	
	private static final Logger log = Logger.getLogger(GibsonAggregator.class);

	@Aggregator(inputChannel = "signedGibsonChannel", outputChannel = "endChannel")
	public MusicItemsPackage completeGibsons(List<Gibson> gibsonList) {
//	public MusicItemsPackage completeGibsons(List<MusicItem> gibsonList) {
		log.info("Completing all Gibson guitars");
		
		final MusicItemsPackage itemsPackage = new MusicItemsPackage();
		itemsPackage.setItems(gibsonList);
		itemsPackage.setName("Gibson package");
		return itemsPackage;
	}
	
	@ReleaseStrategy
	//public boolean releaseChecker(List<Message<List<Gibson>>> messages) {
	public boolean releaseChecker(List<Message<?>> messages) {
	    return messages.size() >= 2;
	}

}
