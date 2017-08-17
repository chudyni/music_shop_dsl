package musicshop.endpoint;

import musicshop.domain.MusicItemsPackage;
import musicshop.domain.item.Guitar;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;

import java.util.List;

@MessageEndpoint
public class GuitarSplitter {

	@Splitter
	public List<Guitar> splitGuitars(final MusicItemsPackage musicItemsPackage) {
		return (List<Guitar>) musicItemsPackage.getItems();
	}
}
