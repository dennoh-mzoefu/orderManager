package net.guides.springboot.crud.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "GeneralOrder")
public class GeneralOrder {

	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
	private long id;

	private String Category;
	private float minBudget;
	private int duration;
	private float maxBudget;
	public GeneralOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GeneralOrder(String category, float minBudget, int duration, float maxBudget) {
		super();
		Category = category;
		this.minBudget = minBudget;
		this.duration = duration;
		this.maxBudget = maxBudget;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public float getMinBudget() {
		return minBudget;
	}
	public void setMinBudget(float minBudget) {
		this.minBudget = minBudget;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public float getMaxBudget() {
		return maxBudget;
	}
	public void setMaxBudget(float maxBudget) {
		this.maxBudget = maxBudget;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
	
}
