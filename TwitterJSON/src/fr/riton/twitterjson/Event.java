package fr.riton.twitterjson;

public class Event {

	public String id;
	public String owner;
	public int priority;
	
	public static final int EVENT_PRIORITY_TEXT = 1;
	public static final int EVENT_PRIORITY_INFO = 2;
	public static final int EVENT_PRIORITY_WARNING = 3;
	public static final int EVENT_PRIORITY_ERROR = 4;
	public static final int EVENT_PRIORITY_CRITICAL = 5;
	
	public Event(String id, String owner, int priority) {
		this.id = id;
		this.owner = owner;
		this.priority = priority;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public int getResourceColorId() {
		
		switch (priority) {
			case EVENT_PRIORITY_TEXT:
				return R.color.text;
			
			case EVENT_PRIORITY_INFO:
				return R.color.info;
				
			case EVENT_PRIORITY_WARNING:
				return R.color.warning;
			
			case EVENT_PRIORITY_ERROR:
				return R.color.error;
			
			case EVENT_PRIORITY_CRITICAL:
				return R.color.critical;				
		}
		
		return R.color.text;
	}
	
	public String toString() {
		return "Event[" + id + "]: belongs to " + owner;
	}
}
