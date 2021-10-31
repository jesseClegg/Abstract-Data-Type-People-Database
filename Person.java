/**
 * Representing a Person object to store data entries with fields of name 
 * and birthday. Implementation of Comparable interface allows precedence of one
 * person over another using alphabetic order of the name field.
 * @author Jesse Clegg
 * @version 1.0
 */
import java.util.*;

public class Person implements Comparable<Person> {
	/*
	 * First and Last name delimited by space character
	 */
	private String Name;
	/*
	 * Birthday in format 'YYYY-MM-DD'
	 */
	private String Birthday;

	/**
	 * default no arg constructor
	 */
	Person() {
		this.Name = null;
		this.Birthday = null;
	}

	/**
	 * @param name the name to set
	 */
	Person(String name) {
		this.Name = name;
		this.Birthday = null;
	}

	/**
	 * @param name
	 * @param birthday
	 */
	public Person(String name, String birthday) {
		this.Name = name;
		if (birthday.length() == 10) {
			this.Birthday = birthday;
		} else {
			this.Birthday = null;
		}
	}

	/**
	 * @return name of this person for printing/accessing from a generic class
	 *         without casting
	 */
	@Override
	public String toString() {
		return this.Name;
	}

	/**
	 * 
	 * @return first 4 characters of birthday 'YYYY'
	 */
	public String getYear() {
		if (this.Birthday == null || this.Birthday.length() != 10) {
			return null;
		} else {
			StringBuilder year = new StringBuilder();
			for (int i = 0; i < 4; i++) {
				year.append(this.getBirthday().charAt(i));
			}
			return year.toString();
		}
	}

	/**
	 * 
	 * @return characters 5-6 of birthday 'MM'
	 */
	public String getMonth() {
		if (this.Birthday == null || this.Birthday.length() != 10) {
			return null;
		} else {
			StringBuilder month = new StringBuilder();
			for (int i = 5; i < 7; i++) {
				month.append(this.getBirthday().charAt(i));
			}
			return month.toString();
		}
	}

	/**
	 * @return last 2 characters of birthday 'DD'
	 */
	public String getDay() {
		if (this.Birthday == null || this.Birthday.length() != 10) {
			return null;
		} else {
			StringBuilder day = new StringBuilder();
			for (int i = 8; i < 10; i++) {
				day.append(this.getBirthday().charAt(i));
			}
			return day.toString();
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return Birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		Birthday = birthday;
	}

	/**
	 * @param other a person class to compare field of name with
	 */
	@Override
	public int compareTo(Person other) {
		return Name.compareTo(other.Name);
	}
}
