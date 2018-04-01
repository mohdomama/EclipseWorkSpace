package com.softnerve;

public class Intern {
	private String name;
	private int age;
	private int stipend;
	public Intern(){}
	public Intern(String name, int age, int stipend){
		this.name = name;
		this.age = age;
		this.stipend = stipend;	
	}
	public String getName() {
	   return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public int getAge() {
      return age;
   }
   public void setAge(int age) {
	   this.age = age;
   }
   public int getStipend() {
	   return stipend;
   }
   public void setStipend(int stipend) {
	   this.stipend = stipend;
   }
   public String toString(){
      return "Intern [ name: "+name+", age: "+ age+", stipend: "+stipend+" ]";
   }
}
