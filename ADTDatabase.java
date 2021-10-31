/**
 * Representing an abstract data type Database maintaining generic nodes containing person values
 * uses binary search tree as data structure
 * @author Jesse Clegg
 * @version 1.0
 */
import java.util.*;
import java.util.ArrayList;

public class ADTDatabase<T extends Comparable<T>> {

	/**
	 * generic binary search tree
	 */
	Bst<T> tree;

	public ADTDatabase() {
		this.tree = new Bst();
	}

	/**
	 * @param tree
	 */
	public ADTDatabase(Bst<T> tree) {
		this.tree = tree;
	}

	/**
	 * @return the tree
	 */
	public Bst<T> getTree() {
		return tree;
	}

	/**
	 * @param tree the tree to be set
	 */
	public void setTree(Bst<T> tree) {
		this.tree = tree;
	}

	/**
	 * Search the 3 possible fields find the overlapping entries More extensible
	 * later on than search by birthday
	 * 
	 * @param year  find all people with this year in birthday
	 * @param month find all people with this month in birthday
	 * @param day   month find all people with this day in birthday
	 * @return
	 */
	public ArrayList<Person> searchBirthday(String year, String month, String day) {
		ArrayList<Person> result = new ArrayList<Person>();
		result = intersectionTwoLists(getAllYear(year), getAllMonth(month));
		result = intersectionTwoLists(result, getAllDay(day));
		return result;
	}

	/**
	 * Retrieve all entries with same year and month in birthday
	 * 
	 * @param year  year to find
	 * @param month month to find
	 * @return list of people fitting both year and month criteria
	 */
	public ArrayList<Person> searchYearAndMonth(String year, String month) {
		ArrayList<Person> result = new ArrayList<Person>();
		result = intersectionTwoLists(getAllYear(year), getAllMonth(month));
		return result;
	}

	/**
	 * Retrieve all entries with same year and day in birthday
	 * 
	 * @param year year to find
	 * @param day  day to find
	 * @return list of people fitting both year and day criteria
	 */
	public ArrayList<Person> searchYearAndDay(String year, String day) {
		ArrayList<Person> result = new ArrayList<Person>();
		result = intersectionTwoLists(getAllYear(year), getAllDay(day));
		return result;
	}

	/**
	 * Retrieve all entries with same day and month in birthday
	 * 
	 * @param month month to find
	 * @param day   day to find
	 * @return list of people fitting both day and month criteria
	 */
	public ArrayList<Person> searchMonthAndDay(String month, String day) {
		ArrayList<Person> result = new ArrayList<Person>();
		result = intersectionTwoLists(getAllMonth(month), getAllDay(day));
		return result;
	}

	/**
	 * Calculates overlapping entries of any 2 lists
	 * 
	 * @param list1 any list of people to compare against list2
	 * @param list2 any list of people to compare against list1
	 * @return the people found in both lists
	 */
	public ArrayList<Person> intersectionTwoLists(ArrayList<Person> list1, ArrayList<Person> list2) {
		ArrayList<Person> intersection = new ArrayList<Person>();
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).getName().equals(list2.get(j).getName())) {
					intersection.add(list2.get(j));
					break;
				}
			}
		}

		return intersection;
	}

	/**
	 * adds name with a birthday into binary tree
	 * 
	 * @param name     name of new entry
	 * @param birthday
	 */
	public void add(String name, String birthday) {
		Person newPerson = new Person(name, birthday);
		Node newNode = new Node(newPerson);
		if (this.tree == null) {
			this.tree = new Bst();
			this.tree.insert(newNode);
		} else {
			this.tree.insert(newNode);
		}
	}

	/**
	 * adds just a birthday into binary tree
	 * 
	 * @param name name of the new entry
	 */
	public void add(String name) {
		Person newPerson = new Person(name);
		Node newNode = new Node(newPerson);
		if (this.tree == null) {
			this.tree = new Bst();
			this.tree.insert(newNode);
		} else {
			this.tree.insert(newNode);
		}
	}

	/**
	 * edit birthday of an entry
	 * 
	 * @param name        entry to edit
	 * @param newBirthday birthday to assign
	 * @return true on success, else false
	 */
	public boolean editBirthday(String name, String newBirthday) {
		if (newBirthday.length() != 10) {
			return false;
		}
		if (this.inDataBase(name)) {
			Person toDelete = this.searchByName(name);
			Node nodeToRemove = new Node(toDelete);
			this.tree.delete(nodeToRemove);
			Person updated = new Person();
			updated.setBirthday(newBirthday);
			updated.setName(name);
			Node newNode = new Node();
			newNode.setKey(updated);
			this.tree.insert(newNode);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Change name of an entry
	 * 
	 * @param name    name to lookup
	 * @param newName name that will be assigned
	 * @return true on success, else false
	 */
	public boolean editName(String name, String newName) {
		if (this.inDataBase(name)) {
			Person tempPerson = new Person(name);
			Node tempNode = new Node(tempPerson);
			Node oldNode = new Node();
			oldNode = this.tree.binarySearch(tempNode);
			Person updated = new Person();
			updated.setName(newName);
			updated.setBirthday(((Person) oldNode.getKey()).getBirthday());
			this.tree.delete(oldNode);
			Node newNode = new Node();
			newNode.setKey(updated);
			this.tree.insert(newNode);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * remove entry by name
	 * 
	 * @param name name of entry to delete
	 * @return true on success, else false
	 */
	public boolean delete(String name) {
		if (this.inDataBase(name)) {
			Person tempPerson = new Person(name);
			Node tempNode = new Node(tempPerson);
			this.tree.delete(tempNode);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Prints all entries
	 */
	public void printDataBase() {
		ArrayList<Person> allEntries = new ArrayList<Person>();
		allEntries = this.getAllPeople();
		System.out.println();
		System.out.println("--------------------------------------------");
		System.out.println("The current contents of the data base...");
		System.out.println("--------------------------------------------");
		int count = 0;
		for (int i = 0; i < allEntries.size(); i++) {
			count++;
			System.out.print("Name: [" + allEntries.get(i).getName() + "]");
			System.out.print(" ");
			System.out.println("Birthday: [" + allEntries.get(i).getBirthday() + "]");
		}
		System.out.println("Entries: [" + count + "]--------------------------------");
		System.out.println();
	}

	/**
	 * Prints one entry
	 * 
	 * @param name name of entry to display
	 */
	public void printOneEntry(String name) {
		if (this.inDataBase(name)) {
			Person entry = this.searchByName(name);
			// Person entry = new Person();
			// entry=this.searchByName(name);
			System.out.print("Name: [" + entry.getName() + "]");
			System.out.print(" ");
			System.out.println("Birthday: [" + entry.getBirthday() + "]");
		}
	}

	/**
	 * Print any list of Person
	 * 
	 * @param list the list to print
	 */
	public void printAnyList(ArrayList<Person> list) {
		System.out.println("--------------------------------------------");
		if (list == null) {// may not be triggering?
			System.out.println("No Results Found");
			System.out.println("--------------------------------------------");
		} else {
			System.out.println("Results Found");
			System.out.println("--------------------------------------------");
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
		System.out.println("--------------------------------------------");
		System.out.println();
	}

	/**
	 * Check existence of entry in tree
	 * 
	 * @param name the name to check
	 * @return true if in tree, else false
	 */
	public boolean inDataBase(String name) {
		Person tempPerson = new Person(name);
		Node tempNode = new Node(tempPerson);
		Node result = new Node();
		result = this.tree.binarySearch(tempNode);
		if (result == null) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Retrieve one person by name
	 * 
	 * @param name the name to retrieve
	 * @return Person found, null on failure
	 */
	public Person searchByName(String name) {
		Person tempPerson = new Person(name);
		Node tempNode = new Node(tempPerson);
		if (this.inDataBase(name)) {
			Node result = new Node();
			result = this.tree.binarySearch(tempNode);
			if (result == null) {
				return null;
			} else {
				Person found = new Person();
				found = (Person) result.getKey();
				return found;
			}
		} else {
			return null;
		}
	}

	/**
	 * Retrieve all entries
	 * 
	 * @return the list of all person in tree
	 */
	public ArrayList<Person> getAllPeople() {
		ArrayList<Node> allNodes = new ArrayList<Node>();
		ArrayList<Person> allPeople = new ArrayList<Person>();
		allNodes = this.tree.inorder(this.tree.getRoot(), allNodes);
		for (int i = 0; i < allNodes.size(); i++) {
			allPeople.add((Person) allNodes.get(i).getKey());
		}
		return allPeople;
	}

	/**
	 * Search all entries born on a day
	 * 
	 * @param day the day to find
	 * @return list of entries matching criteria for day
	 */
	public ArrayList<Person> getAllDay(String day) {
		ArrayList<Person> entries = new ArrayList<Person>();
		ArrayList<Person> matches = new ArrayList<Person>();
		String dayFound = null;
		entries = this.getAllPeople();
		for (int i = 0; i < entries.size(); i++) {
			if (day.equals(entries.get(i).getDay())) {
				matches.add(entries.get(i));
			}
		}
		return matches;
	}

	/**
	 * Search all entries born in a month
	 * 
	 * @param month the month to find
	 * @return list of entries matching criteria for month
	 */
	public ArrayList<Person> getAllMonth(String month) {
		ArrayList<Person> entries = new ArrayList<Person>();
		ArrayList<Person> matches = new ArrayList<Person>();
		String monthFound = null;
		entries = this.getAllPeople();
		for (int i = 0; i < entries.size(); i++) {
			if (month.equals(entries.get(i).getMonth())) {
				matches.add(entries.get(i));
			}
		}
		return entries;
	}

	/**
	 * Search all entries born in a year
	 * 
	 * @param year the year to find
	 * @return list of entries matching criteria for year
	 */
	public ArrayList<Person> getAllYear(String year) {
		ArrayList<Person> entries = new ArrayList<Person>();
		ArrayList<Person> matches = new ArrayList<Person>();
		String yearFound = null;
		entries = this.getAllPeople();
		for (int i = 0; i < entries.size(); i++) {
			if (year.equals(entries.get(i).getYear())) {
				matches.add(entries.get(i));
			}
		}
		return matches;
	}

	/**
	 * Process line of a database file into array[2]: name[0] and birthday[1]
	 * 
	 * @param rawData line of a database file
	 * @return
	 */
	public String[] formatLineFromFile(String rawData) {
		StringBuilder name = new StringBuilder();
		StringBuilder birthday = new StringBuilder();
		String[] person = new String[2];
		int birthdayStart = 0;
		char index;
		for (int i = 0, whtspce = 0; i < rawData.length(); i++) {
			index = rawData.charAt(i);
			index = Character.toLowerCase(index);
			if (index == ' ') {
				whtspce++;
				if (whtspce == 2) {
					birthdayStart = ++i; // to skip the space between
					break;
				}
			}
			name.append(index);
		}
		while (birthdayStart < rawData.length()) {
			birthday.append(rawData.charAt(birthdayStart));
			birthdayStart++;
		}
		person[0] = name.toString();
		person[1] = birthday.toString();
		return person;
	}
}
