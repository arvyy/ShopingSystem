package lt.mif.ise.domain.search;

import java.util.Optional;

public class ProductCriteria {

	private String text;

	public Optional<String> getText() {
		return Optional.ofNullable(text);
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isUnspecified() {
		return text == null || text.isEmpty();
	}
	
}
