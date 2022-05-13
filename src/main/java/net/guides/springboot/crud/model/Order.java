package net.guides.springboot.crud.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "Order")
public class Order {

	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
	private long id;
	
//	@NotBlank
//    @Size(max=100)
//    @Indexed(unique=true)
//	public int identify;
	private String level;
	private int pages;
	private String type;
	private int days;
	private float price;
	
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", level=" + level + ", pages=" + pages + ", type=" + type + ", days=" + days
				+ ", price=" + price + "]";
	}


	public Order(String level, int pages, String type, int days) {
		super();
		this.level = level;
		this.pages = pages;
		this.type = type;
		this.days = days;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public int getPages() {
		return pages;
	}


	public void setPages(int pages) {
		this.pages = pages;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getDays() {
		return days;
	}


	public void setDays(int days) {
		this.days = days;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


//	public static String getSequenceName() {
//		return SEQUENCE_NAME;
//	}


	public Order() {
		
	}
	
	
}
