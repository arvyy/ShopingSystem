package lt.mif.ise.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.mif.ise.domain.Category;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Product")
public class ProductSearch {
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private String id;
	
	private String productId;
	
	private String name;
	
	private String imageUrl;
	
	   @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "category_id")
	    private Category category;
	    
	@Column(nullable= false, precision=8, scale=3)
	private BigDecimal price;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
