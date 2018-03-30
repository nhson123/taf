package com.anecon.taf.client.genericui;

public interface GenericUiClient<T, U> {
	
	void start();
	
	void stop();
	
	T find(String by, String element);
	
	void click(U element);
	
	void rightClick(U element);
	
	void doubleClick(U element);
	
	void type(String value);
	
	void submit();
	
}