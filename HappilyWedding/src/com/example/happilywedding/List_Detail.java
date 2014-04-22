package com.example.happilywedding;

public class List_Detail {

	private String Name = "";
	private String listing_id = "";
	private String Km = "";
	private String Area = "";
	private int rating = 0;

	public List_Detail(String listing_id, String name2, String km2,
			String area2, int rating) {
		// TODO Auto-generated constructor stub
		this.listing_id = listing_id;
		this.Name = name2;
		this.Km = km2;
		this.Area = area2;
		this.rating = rating;
	}

	public List_Detail() {
		// TODO Auto-generated constructor stub
	}

	/*********** Set Methods ******************/

	public void setListing_id(String listing_id) {
		this.listing_id = listing_id;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public void setKm(String Km) {
		this.Km = Km;
	}

	public void setArea(String Area) {
		this.Area = Area;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	/*********** Get Methods ****************/

	public String getListing_id() {
		return this.listing_id;
	}

	public String getName() {
		return this.Name;
	}

	public String getKm() {
		return this.Km;
	}

	public String getArea() {
		return this.Area;
	}

	public int getRating() {
		return this.rating;
	}
}
