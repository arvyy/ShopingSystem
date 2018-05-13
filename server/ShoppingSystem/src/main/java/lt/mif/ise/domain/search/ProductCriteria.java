package lt.mif.ise.domain.search;

import java.util.Optional;

public class ProductCriteria {

	private String text;
	
	private String category;

	public Optional<String> getText() {
		return Optional.ofNullable(text);
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isUnspecified() {
		return (text == null || text.isEmpty()) && 
				(category == null || category.isEmpty());
	}

	public Optional<String> getCategory() {
		return Optional.ofNullable(category);
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
