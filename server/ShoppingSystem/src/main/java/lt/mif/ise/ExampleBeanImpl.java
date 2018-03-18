package lt.mif.ise;

import org.springframework.beans.factory.annotation.Value;

public class ExampleBeanImpl implements ExampleBean {
	
	/*
	 * pakisa reiksme, kuri yra application.properties faile. 
	 */
	@Value("${example.bean.name}")
	private String name;
	
	private String data;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getData() {
		return data;
	}

	@Override
	public void setData(String data) {
		this.data = data;
	}
}
