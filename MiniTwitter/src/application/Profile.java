package application;

// Profile part of Observer design pattern

public interface Profile {
	public void attach(Observer observer);
	public void notifyObservers();
}
