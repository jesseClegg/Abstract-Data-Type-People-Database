/**
 * Driver program with decomposed main method
 * Tests and demonstrates all required functionalities of ADTDatabase
 * @author Jesse Clegg
 * @version 1.0
 */
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Driver {
	static ADTDatabase database = new ADTDatabase();
	public static void main(String[] args) throws IOException {
		buildDataBase();
		runTests();
	}
	/**
	 * Process raw lines of file and feed into database in suitable format
	 * @throws IOException
	 */
	public static void buildDataBase() throws IOException {
		ArrayList<String> unformattedData = new ArrayList<String>();
		unformattedData = getTestingData();
		String analyzer;
		String Name;
		String Birthday;
		String strippedData[] = new String[2];
		for (int i = 0; i < unformattedData.size(); i++) {
			analyzer = unformattedData.get(i);
			strippedData = database.formatLineFromFile(analyzer);
			Name = strippedData[0];
			Birthday = strippedData[1];
			database.add(Name, Birthday);
		}
	}
	/**
	 * Demonstrates each functionality of the database with clear test cases
	 * Use unique names for easy recognition in testing
	 */
	public static void runTests() {
		System.out.println("running tests...");
		database.printDataBase();
		System.out.println(database.inDataBase("paul revere"));
		database.add("paul revere");
		database.printDataBase();
		System.out.println(database.inDataBase("paul revere"));
		database.delete("paul revere");
		System.out.println(database.inDataBase("paul revere"));
		database.printOneEntry("sponge robert");
		database.editBirthday("sponge robert", "9999-99-99");
		database.printOneEntry("sponge robert");
		database.editName("sponge robert", "patrick star");
		database.printDataBase();
		database.printAnyList(database.getAllYear("9999"));
		database.editBirthday("kanye west", "1900-12-17");
		database.add("kanye east", "1900-12-17");
		database.add("super man", "1900-12-17");
		database.printDataBase();
		database.printAnyList(database.searchBirthday("1900", "12", "17"));
		database.printAnyList(database.searchYearAndDay("1977", "08"));
		database.printDataBase();
		System.out.println();
		System.out.println();
		ArrayList<Person> entries = new ArrayList<Person>();
		entries = database.getAllPeople();
		for (int i = 0; i < entries.size(); i++) {
			System.out.println();
			System.out.println("deleting... [" + entries.get(i).getName() + "]");
			database.delete(entries.get(i).getName());
			database.printDataBase();
		}
		System.out.println("ending tests..all functionalities demonstrated");
	}
	/**
	 * Read in and format database roster from file
	 * @return list of strings, each string a line in the file
	 * @throws IOException 
	 */
	public static ArrayList<String> getTestingData() throws IOException {
		ArrayList<String> entries = new ArrayList<String>();
		List<String> lines = Files.readAllLines(Paths.get("./dataBaseRoster.txt"));
		for (int i = 0; i < lines.size(); i++) {
			entries.add(lines.get(i));
		}
		return entries;
	}

}