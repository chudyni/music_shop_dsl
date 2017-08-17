package musicshop.endpoint;

import musicshop.domain.item.Fender;
import musicshop.domain.item.Guitar;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;

@MessageEndpoint
public class FenderTransformer {
	
	private static final Logger log = Logger.getLogger(FenderTransformer.class);

	@Transformer
	public Fender signFender(final Guitar guitar) {
		log.info("Transforming to Fender");
		return new Fender(guitar.getMark(), guitar.getName());
	}
}
