package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"productId"})})
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private String id;

    @Column(nullable= false, unique= true)
    private String productId;
    
    private String name;

    private String description;

    private String imageUrl;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable= false, precision=8, scale=3)
    private BigDecimal price;

    @Version
    private Integer version;

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setProperties(Product product){
        this.productId = product.getProductId() == null ? this.productId : product.getProductId();
        this.name = product.getName() == null ? this.name : product.getName();
        this.description = product.getDescription() == null ? this.description : product.getDescription();
        this.imageUrl = product.getImageUrl();
        this.category = product.getCategory() == null ? this.category : product.getCategory();
        this.price = product.getPrice() == null ? this.price : product.getPrice();
        this.version = product.getVersion() == null ? this.version : product.getVersion();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
