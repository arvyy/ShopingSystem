package lt.mif.ise.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Example {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	private String text;
	
	private Integer num;
	
	public void setText(String t) {
		text = t;
	}
	
	public String getText() {
		return text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}
