public class GymSignUp {
	// Object member variables defined before the constructor
	private GTerm gt;

	// maxNumRecords used to set the array lengths
	private int maxNumRecords;

	// Declare the arrays which will be used to store all of the data inputed by the
	// user
	private String[] names;

	private String[] genders;

	private char[] charGenders;

	private int[] ages;

	private double[] weightsInKgs;

	private boolean[] isPastMemberArray;

	// Used to track how many records have been entered so that it is clear which
	// array element the next input should be added to
	private int numOfRecordsEntered;

	// This group of variables are used to store the data after it has been
	// converted to strings so that it can be added into a table
	private String stringGender;

	private String stringAge;

	private String stringWeightInKgs;

	private String stringIsPastMember;

	private String rowData;

	// This group of variables are used to calculate the statistics
	private int sumOfAges;

	private int oldestMemberAge;

	private int youngestMemberAge;

	private int averageAge;

	// Create the constructor method
	// This method is later called in the main method to create an 'object' which
	// runs the program
	public GymSignUp() {

		// Constructor must start by intialising the necessary object member variables
		// maxNumRecords is used to set the maximum number of records that can be
		// inputed before the arrays need to be expanded
		// Although in practice it seems like this is more of a legacy variable name
		// from previous IIEs as the array expansion
		// is hidden from the user, so in practice there is no maximum number of records
		// as the arrays will continue to expand
		// for future maybe a more descriptive variable name would be currentArraySize
		this.maxNumRecords = 10;
		// All arrays are initialised, with space for 10 records before expansion will
		// occur
		// In a real world situation it might be more worthwhile to set a larger array
		// size from the beginning
		// but the array expansion methods were a part of the IIE requirements
		this.names = new String[this.maxNumRecords];
		this.genders = new String[this.maxNumRecords];
		this.charGenders = new char[this.maxNumRecords];
		this.ages = new int[this.maxNumRecords];
		this.weightsInKgs = new double[this.maxNumRecords];
		this.isPastMemberArray = new boolean[this.maxNumRecords];
		// This variable initialises the variable that dictates what array element the
		// current user record is entered into
		this.numOfRecordsEntered = 0;

		// GTerm window is created
		this.gt = new GTerm(1500, 700);

		// Create table that is a header for the text fields
		this.gt.addTable(700, 20, "name\tgender(m/f)\tage\tweight\thas been member");
		this.gt.println("");

		// Create the text boxes for user input
		this.gt.addTextField("", 140);
		this.gt.addTextField("", 140);
		this.gt.addTextField("", 140);
		this.gt.addTextField("", 140);
		this.gt.addTextField("", 140);

		this.gt.println("");
		this.gt.println("");
		this.gt.println("");
		this.gt.println("");

		// Create the table that user records are displayed in
		this.gt.addTable(700, 400, "name\tgender(m/f)\tage\tweight\thas been member");

		// Create a button that will display statistics about the gym users by calling
		// the
		// displayStatistics method, which in turn calls the calculate statistics method
		this.gt.addButton("Display Statistics", this, "displayStatistics");

		this.gt.addButton("Remove Gym Member", this, "removeButton");

		this.gt.addButton("Edit Gym Member", this, "editMemberButton");

		// A button that allows the user to enter new records
		// It stores the data that the user entered into arrays and then displays it to
		// a table
		this.gt.addButton("Add New Gym Member", this, "addViaButton");

		// END OF CONSTRUCTOR

	}

	public void addViaButton() {

		//
		// Input for name
		//

		// The value for 'name' is taken from the first text box
		String name = this.gt.getTextFromEntry(0);

		// Input validation to ensure text field is not empty before the value is sent
		// to the array
		while (name == null || name.isBlank()) {
			name = this.gt.getInputString("Please enter your name");
		}

		//
		// Input for gender
		//

		// The value for 'gender' is taken from the second text box
		String gender = this.gt.getTextFromEntry(1);

		// Input validation to ensure text field is not empty before the value is sent
		// to the array
		while (gender == null || gender.isBlank() || gender.length() != 1) {
			gender = this.gt
					.getInputString("Please enter gender as either 'm','f' or 'o'. (Gender must be a single letter)");
		}

		// Input validation to ensure text field contains valid values for gender
		while (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f") && !gender.equalsIgnoreCase("o")) {
			gender = this.gt.getInputString("Please enter either 'm', 'f' or 'o'");
		}

		char charGender = gender.charAt(0);

		//
		// Input for age
		//

		// The value for 'age' is taken from the third text box
		int age = Integer.parseInt(this.gt.getTextFromEntry(2));

		// Validate if age is within specified range
		while (age <= 0 || age > 125) {
			age = Integer.parseInt(this.gt.getInputString("Please enter a valid age, (must be between 0-125)"));
		}

		// Convert to string to check if field is empty
		String validateAge = String.valueOf(age);

		// Validate that the user has entered a value for age so that the program does
		// not crash
		// HELP - this part of the program is not working - for some reason, the program
		// will not re-ask for age
		while (validateAge.isEmpty() || validateAge == null) {
			age = Integer.parseInt(this.gt.getInputString("Please enter a valid age, (must be between 0-125)"));
		}

		//
		// Input for weight
		//

		// The value for 'weightInKgs' is taken from the fourth text box
		Double weightInKgs = Double.parseDouble(this.gt.getTextFromEntry(3));

		// Validate if weightInKgs is within specified range
		while (weightInKgs <= 0 || weightInKgs > 200) {
			weightInKgs = Double
					.parseDouble(this.gt.getInputString("Please enter a valid weight, (must be between 0-200)"));
		}

		// Convert to string to check if field is empty
		String validateWeight = String.valueOf(weightInKgs);

		// Validate that the user has entered a value for age so that the program does
		// not crash
		while (validateWeight.isEmpty()) {
			weightInKgs = Double
					.parseDouble(this.gt.getInputString("Please enter a valid weight, (must be between 0-200)"));
		}

		//
		// Input for is past member
		//

		// The value for 'isPastMember' is taken from the fifth text box
		boolean isPastMember = Boolean.parseBoolean(this.gt.getTextFromEntry(4));

		// Convert to string to perform input validation in next step
		String stringIsPastMember = String.valueOf(isPastMember);

		// Validate if isPastMember has valid value
		while (!stringIsPastMember.equalsIgnoreCase("true") && !stringIsPastMember.equalsIgnoreCase("false")) {
			isPastMember = Boolean.parseBoolean(this.gt.getInputString("Please enter either 'true' or 'false'"));
		}

		//
		// Enter data into table by calling addNewGymMember and refreshTable
		//

		// The back-end method takes the user inputs as parameters and adds them to
		// their respective arrays
		//

		// This condition checks if these parameters have been added to their respective
		// arrays

		if (addNewGymMember(name, gender, charGender, age, weightInKgs, isPastMember)) {
			// If they have been added to the array then the refreshTable method will be
			// called
			// Refresh table clears the table, then loops through each array and re-adds all
			// of the array data to the table
			refreshTable();
			// Clear both text boxes for the next entry
			this.gt.setTextInEntry(0, "");
			this.gt.setTextInEntry(1, "");
			this.gt.setTextInEntry(2, "");
			this.gt.setTextInEntry(3, "");
			this.gt.setTextInEntry(4, "");

			// If the data was not added to the arrays then the program will call the
			// following error message
		} else {
			this.gt.showErrorDialog("Could not add record");
		}

	}

	// Create addNewGymMember method
	public boolean addNewGymMember(String name, String gender, char charGender, int age, double weightInKgs,
			boolean isPastMember) {

		// A new set of arrays are created as temporary variables in case the original
		// arrays need to be expanded
		String[] longerNames;

		String[] longerGenders;

		char[] longerCharGenders;

		int[] longerAges;

		double[] longerWeightsInKgs;

		boolean[] longerIsPastMemberArray;

		boolean recordEntered = false;

		//
		// Add 'name' to array
		//

		// Array expansion logic for names
		if (this.numOfRecordsEntered >= this.maxNumRecords) {

			// Create new, longer array
			longerNames = new String[this.maxNumRecords * 2];

			// Copy the old values to the new array
			int j = 0;
			while (j < this.numOfRecordsEntered) {
				longerNames[j] = this.names[j];
				j++;
			}

			// Make old array equal new array
			this.names = longerNames;

		}
		// The current name element in the names array is assigned the current value of
		// name

		this.names[this.numOfRecordsEntered] = name;

		//
		// Add 'gender' to array -- why do I have a genders array?
		// Some of the unncessary code in this program is a result of trying to meet the
		// requirements of previous IIEs
		// e.g. that there must a 'char'. In its current form my program does not need
		// to contain a char and the functionality could easily be achieved by taking a
		// one word string
		//

		// Array expansion logic for genders
		if (this.numOfRecordsEntered >= this.maxNumRecords) {

			// Create new, longer array
			longerGenders = new String[this.maxNumRecords * 2];

			// Copy the old values to the new array
			int j = 0;
			while (j < this.numOfRecordsEntered) {
				longerGenders[j] = this.genders[j];
				j++;
			}

			// Make old array equal new array
			this.genders = longerGenders;

		}

		// The current gender element in the genders array is assigned the current value
		// of this.gender

		this.genders[this.numOfRecordsEntered] = gender;

		//
		// Add char gender to array
		//

		// Array expansion logic for charGenders
		if (this.numOfRecordsEntered >= this.maxNumRecords) {

			// Create new, longer array
			longerCharGenders = new char[this.maxNumRecords * 2];

			// Copy the old values to the new array
			int j = 0;
			while (j < this.numOfRecordsEntered) {
				longerCharGenders[j] = this.charGenders[j];
				j++;
			}

			// Make old array equal new array
			this.charGenders = longerCharGenders;

		}

		this.charGenders[this.numOfRecordsEntered] = charGender;

		//
		// Add age to array
		//

		// Array expansion logic for ages
		if (this.numOfRecordsEntered >= this.maxNumRecords) {

			// Create new, longer array
			longerAges = new int[this.maxNumRecords * 2];

			// Copy the old values to the new array
			int j = 0;
			while (j < this.numOfRecordsEntered) {
				longerAges[j] = ages[j];
				j++;
			}

			// Make old array equal new array
			this.ages = longerAges;

		}

		// The current age element in the ages array is assigned the current value of
		// this.age
		this.ages[this.numOfRecordsEntered] = age;

		//
		// Add weight to array
		//

		// Array expansion logic for weights
		if (this.numOfRecordsEntered >= this.maxNumRecords) {

			// Create new, longer array
			longerWeightsInKgs = new double[this.maxNumRecords * 2];

			// Copy the old values to the new array
			int j = 0;
			while (j < this.numOfRecordsEntered) {
				longerWeightsInKgs[j] = this.weightsInKgs[j];
				j++;
			}

			// Make old array equal new array
			this.weightsInKgs = longerWeightsInKgs;

		}

		// The current weightInKgs element from the weightsInKgs array is assigned the
		// current value of this.weightInKgs
		this.weightsInKgs[this.numOfRecordsEntered] = weightInKgs;

		//
		// Add isPastMember to array
		//

		// Array expansion logic for if the person is a past member
		if (this.numOfRecordsEntered >= this.maxNumRecords) {

			// Create new, longer array
			longerIsPastMemberArray = new boolean[this.maxNumRecords * 2];

			// Copy the old values to the new array
			int j = 0;
			while (j < this.numOfRecordsEntered) {
				longerIsPastMemberArray[j] = this.isPastMemberArray[j];
				j++;
			}

			// Make old array equal new array
			this.isPastMemberArray = longerIsPastMemberArray;

		}

		// The current isPastMember element from the isPastMemberArray is assigned the
		// current value of this.isPastMember
		this.isPastMemberArray[this.numOfRecordsEntered] = isPastMember;

		//
		//
		//
		//
		// INCREMENT LOOP COUNTER
		//
		//
		//
		//

		// Update array length for later conditionals if the loop repeats and arrays
		// need to be expanded again
		if (this.numOfRecordsEntered >= this.maxNumRecords) {
			this.maxNumRecords *= 2;
		}

		// Increment the while loop
		this.numOfRecordsEntered++;

		// Create a space before the next record
		this.gt.println("");

		// Set boolean value to true after all of the above code has run
		recordEntered = true;

		// Return the boolean value of the method
		return recordEntered;

	}

	// Create the calculate statistics method
	// I created this method to meet the requirement that the addNewGym member
	// method should only contain code related to storing the values in arrays
	// So I needed to take code related to calculating statistics out and store it
	// somewhere
	public void calculateStatistics() {
		// Age is a whole number and can be stored using an int
		// sumOfAges has been chosen as a name because the variable stores the running
		// total of the gym members ages to later be used when calculating the average
		// age of the gyms members
		this.sumOfAges = 0;

		// Age is a whole number and can be stored using an int
		// oldestMemberAge has been chosen as a name because the variable will store the
		// age of the gyms oldest member
		// It has been initialised with the value of 0 because the first value for age
		// will be larger than zero and that will be the new oldest age
		this.oldestMemberAge = 0;

		// Age is a whole number and can be stored using an int
		// youngestMemberAge has been chosen as a name because the variable will store
		// the
		// age of the gyms youngest member
		// It has been initialised with the value of 1000 because the first value for
		// age
		// will be less than 1000 and that will be the new youngest age
		this.youngestMemberAge = 1000;

		// Age is a whole number and can be stored using an int
		// averageAge has been chosen as a name because this variable will store the
		// average age of the gyms members
		this.averageAge = 0;

		// Reset the loop counter to zero
		int i = 0;

		// The condition has been formulated so that the loop runs once for each element
		// in the array
		while (i < this.numOfRecordsEntered) {

			// The statements in this code block can run in any order

			// The value of the current array element is added to the cumulative total of
			// ages
			this.sumOfAges += this.ages[i];

			// This condition compares the age stored in the current array element to the
			// current oldest recorded age
			// If it is greater, then it is the new oldest age
			if (this.ages[i] > this.oldestMemberAge) {
				this.oldestMemberAge = this.ages[i];
			}

			// This condition compares the age stored in the current array element to the
			// current youngest recorded age
			// If it is less, then it is the new youngest age
			if (this.ages[i] < this.youngestMemberAge) {
				this.youngestMemberAge = this.ages[i];
			}

			// increment the loop counter
			i++;

			// Average age is calculated by dividing the cumulative total of ages by the
			// number of ages
			this.averageAge = this.sumOfAges / this.numOfRecordsEntered;
		}
	}

	// Create refresh table method
	public void refreshTable() {

		// As I used a table to create headers for the text input boxes the table that
		// data is displayed in is now table index '1'
		// clear data from this table
		this.gt.clearRowsOfTable(1);

		// This loop iterates the number of times that records have been entered
		// On each iteration data from each array is retrieved from the array, converted
		// to a string, combined into a single string and inserted into the table
		int counter = 0;
		while (counter < this.numOfRecordsEntered) {
			this.stringGender = String.valueOf(charGenders[counter]);
			this.stringAge = String.valueOf(ages[counter]);
			this.stringWeightInKgs = String.valueOf(weightsInKgs[counter]);
			this.stringIsPastMember = String.valueOf(isPastMemberArray[counter]);

			this.rowData = this.names[counter] + "\t" + this.stringGender + "\t" + this.stringAge + "\t"
					+ this.stringWeightInKgs + "\t" + this.stringIsPastMember;

			this.gt.addRowToTable(1, this.rowData);

			counter++;

		}
	}

	public void displayStatistics() {

		// Checks that at least one record has been entered before the user is able to
		// display statistics
		if (this.numOfRecordsEntered > 0) {
			calculateStatistics();

			this.gt.println("");
			this.gt.println("");
			this.gt.println("Statistics");
			this.gt.println("");
			this.gt.println("");

			// DISPLAY STATSTICS

			this.gt.println("");
			this.gt.println("Ages:");
			this.gt.println("The average age of your gym members is " + String.valueOf(averageAge));
			this.gt.println("The youngest member of your gym is " + String.valueOf(youngestMemberAge));
			this.gt.println("The oldest member of your gym is " + String.valueOf(oldestMemberAge));
			this.gt.println("");
		} else {
			this.gt.println("");
			this.gt.println("");
			this.gt.println("Statistics");
			this.gt.println("");
			this.gt.println("");

			this.gt.println("Cannot display statistics as no records have been entered");
		}
	}

	// This method removes an element from an array by moving all elements with
	// higher array indexes one to the left
	public void removeButton() {

		// Check that at least one record has been entered before attempting to remove
		// records
		if (this.numOfRecordsEntered == 0) {
			this.gt.showErrorDialog("No records to remove");
		} else {

			// Get user input for which record to remove based on 'name'
			String recordToRemove = this.gt.getInputString("Which record would you like to remove?");

			// Create a variable to store a value that shows if the users record exists
			boolean recordExists = false;

			int j = 0;

			// Loop through all of the records for name and see if there are any matches
			// This is only to generate an error message if there is no record to remove
			// The loop that alters the arrays also checks this
			while (j < this.numOfRecordsEntered) {
				if (this.names[j].equalsIgnoreCase(recordToRemove)) {
					recordExists = true;
					j++;
				} else {
					j++;
				}

			}

			// Display error message if no records match
			if (recordExists == false) {
				this.gt.showErrorDialog("That record does not exist");
			} else {
				// If record has a match show alternate message
				removeMember(recordToRemove);
				refreshTable();
				this.gt.showMessageDialog("Record removed");
			}
		}

	}

	public void removeMember(String recordToRemove) {

		// Initialise the loop counter variable
		int i = 0;
		// The first condition in the statement checks that the loop has not reached the
		// end of the added data
		// This part of the condition is checked first so that the loop doesn't attempt
		// to check a non-existent array index and crash the program
		// AND
		// while the condition has not found a match for the target
		// if neither of these conditions are met then the rest of the loops code body
		// does not execute
		// The variable currentNumData represents the number of elements in that array
		while (i < this.numOfRecordsEntered && !this.names[i].equalsIgnoreCase(recordToRemove)) {
			i++;
		}

		// At this point, we're outside the loop and it means that we have either
		// reached the end of the array (without finding a match)
		// or found a match for target

		if (i < this.numOfRecordsEntered) {
			// This moves the pointer to an index that has a value initialised from the
			// empty space where the next value would be set
			this.numOfRecordsEntered -= 1;
			// Because currentNumData has been decremented, the largest value we will get to
			// in the next loop will be one less than currentNumData
			while (i < this.numOfRecordsEntered) {
				// Each array index gets the value from the right
				this.names[i] = this.names[i + 1];
				this.genders[i] = this.genders[i + 1];
				this.charGenders[i] = this.charGenders[i + 1];
				this.ages[i] = this.ages[i + 1];
				this.weightsInKgs[i] = this.weightsInKgs[i + 1];
				this.isPastMemberArray[i] = this.isPastMemberArray[i + 1];
				i++;
			}

		}

	}

	public void editMemberButton() {

		// Check that at least one record has been entered before attempting to remove
		// records
		if (this.numOfRecordsEntered == 0) {
			this.gt.showErrorDialog("No records to edit");
		} else {

			String recordToEdit = this.gt.getInputString("Who's record would you like to edit?");

			// Create a variable to store a value that shows if the users record exists
			boolean recordExists = false;

			int j = 0;

			// Loop through all of the records for name and see if there are any matches
			// This is only to generate an error message if there is no record to edit
			while (j < this.numOfRecordsEntered) {
				if (this.names[j].equalsIgnoreCase(recordToEdit)) {
					recordExists = true;
					j++;
				} else {
					j++;
				}

			}

			// Display error message if no records match
			if (recordExists == false) {
				this.gt.showErrorDialog("That record does not exist");
			} else {
				// If record exists, get new values for record
				String newName = this.gt.getInputString("Enter a new value for name");
				String newGender = this.gt.getInputString("Enter a new value for gender");
				char newCharGender = newGender.charAt(0);
				int newAge = Integer.parseInt(this.gt.getInputString("Enter a new value for age"));
				double newWeightInKgs = Double.parseDouble(this.gt.getInputString("Enter a new value for weight"));
				boolean newIsPastMember = Boolean
						.parseBoolean(this.gt.getInputString("Enter a new value for is past member"));
				editMember(newName, newGender, newCharGender, newAge, newWeightInKgs, newIsPastMember, recordToEdit, recordExists);
				refreshTable();
			}

		}

	}

	public void editMember(String newName, String newGender, char newCharGender, int newAge, double newWeightInKgs,
			boolean newIsPastMember, String recordToEdit, boolean recordExists) {
		int i = 0;
		// while we haven't reached the end of the added data - CHECK this one first so
		// it doesnt attempt to check a non existent array index and crash
		// AND
		// while we haven't found a match for the target
		while (i < this.numOfRecordsEntered && !this.names[i].equalsIgnoreCase(recordToEdit)) {
			i++;
		}

		// At this point, we're outside the loop and it means that we have either
		// reached the end of the array (without finding a match)
		// or found a match for target

		if (i < this.numOfRecordsEntered && recordExists == true) {
			this.names[i] = newName;
			this.genders[i] = newGender;
			this.charGenders[i] = newCharGender;
			this.ages[i] = newAge;
			this.weightsInKgs[i] = newWeightInKgs;
			this.isPastMemberArray[i] = newIsPastMember;

			i++;

		}
	}

	public static void main(String[] args) {
		GymSignUp objectName = new GymSignUp();
	}

}
