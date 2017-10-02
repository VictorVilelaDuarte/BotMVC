package aula;

public class LatLng {


	public Double latitude;
	public Double longitude;
	
	public LatLng(Double lat, Double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}
	public Double getLat() {
		return latitude;
	}
	public void setLat(Double lat) {
		this.latitude = lat;
	}
	public Double getLng() {
		return longitude;
	}
	public void setLng(Double lng) {
		this.longitude = lng;
	}
}
