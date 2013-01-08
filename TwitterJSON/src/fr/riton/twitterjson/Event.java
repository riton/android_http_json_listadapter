package fr.riton.twitterjson;

public class Event {

	public String id;
	public String owner;
	
	public Event(String id, String owner) {
		this.id = id;
		this.owner = owner;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String toString() {
		return "Event[" + id + "]: belongs to " + owner;
	}
}
