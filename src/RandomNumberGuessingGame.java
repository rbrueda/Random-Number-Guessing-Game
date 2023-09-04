import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class RandomNumberGuessingGame extends JFrame {
	//arraylist that stores all the numbers guess
	ArrayList<Integer> numbersGuessed = new ArrayList<>();
	
	//value that creates random number
	private static int number;
	//TextField for entering random number
	private JTextField randomNumberTextField;
	
	//Radio buttons for the range of random number generator
	private JRadioButton fiftyRadioButton, oneHundredRadioButton, twoHundredRadioButton, customRangeRadioButton;
	
	//TextField for entering custom range percentage -- figure out exception handling for this
	private JTextField customRangeTextField;
	
	//Text area to display messages, names, and notifications to the user
	private JTextArea description;
	
	//Labels for displaying results 
	private JLabel guessResultlabel;
	
	//TextField for displaying results
	private JTextField resultTextField;
	
	//Buttons for calculating, clearing, and saving game details
	private JButton submitButton, newGameButton, saveButton; 
	
	private JSeparator separator;
	
	private JLabel nameLabel;
	
	//constructor for initializing the components
	public RandomNumberGuessingGame(int number) {
		initializeComponents();
		setupLayout();
		setupListeners();
		setTitle("Random Number Guessing Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 400);
		setLocationRelativeTo(null);
	}
	
	
	public int getNumber() {
		return number;
	}
	
	//method to initialize all the components
	private void initializeComponents() {
		
		JOptionPane.showMessageDialog(this, "Welcome! Press \"New Game\" to start a new game! ");
		
		//TextField for entering the random number
		randomNumberTextField = new JTextField(10);
		
		//Radio buttons for different number ranges
		fiftyRadioButton = new JRadioButton("1-50");
		oneHundredRadioButton = new JRadioButton("1-100");
		twoHundredRadioButton = new JRadioButton("1-200");
		customRangeRadioButton = new JRadioButton("Custom");
		
		customRangeTextField = new JTextField(5);
		customRangeTextField.setEnabled(false);
		
		//group the radio buttons in one group
		ButtonGroup rangeOfNumbersGroup = new ButtonGroup();
		rangeOfNumbersGroup.add(fiftyRadioButton);
		rangeOfNumbersGroup.add(oneHundredRadioButton);
		rangeOfNumbersGroup.add(twoHundredRadioButton);
		rangeOfNumbersGroup.add(customRangeRadioButton);
		
		//Label for displaying the results for number guessed
		guessResultlabel = new JLabel("Guess Results: ");
		
		//text field that displays the result of the number guess
		resultTextField = new JTextField(10);
		resultTextField.setEditable(false);
		
		//Buttons for actions
		//button to submit the guessed number
		submitButton = new JButton("Submit");
		//button to clear the input fields
		newGameButton = new JButton("New Game");
		//button to save the numbers guessed and additional contents from the game to a file
		saveButton = new JButton("Save");
		
		//Set tooltips for radio buttons
		fiftyRadioButton.setToolTipText("Generates a number between 1 and 50");
		oneHundredRadioButton.setToolTipText("Generates a number between 1 and 100");
		twoHundredRadioButton.setToolTipText("Generates a number between 1 and 200");
		
		//set tooltips for the submit and new game
		submitButton.setToolTipText("Submit a guessed value that is between the range you selected!");
		newGameButton.setToolTipText("Want to start a new game? Click this button!");
		
		separator = new JSeparator();
		
		nameLabel = new JLabel("Random Number Guessing Game");
	}
	
	//method to set up the layout of GUI components using GridBayLayout
	private void setupLayout() {
		//set up the main content pane
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);
	
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		contentPane.add(nameLabel, gbc);
		
		//Add random number components
		gbc.gridx = 0;
		gbc.gridy = 4;
		contentPane.add(new JLabel("Select Range of Numbers: "), gbc);
		//create a panel with a set of radio buttons
		JPanel numberRangesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//radio buttons added to the panel
		numberRangesPanel.add(fiftyRadioButton);
		numberRangesPanel.add(oneHundredRadioButton);
		numberRangesPanel.add(twoHundredRadioButton);
		numberRangesPanel.add(customRangeRadioButton);
		numberRangesPanel.add(customRangeTextField);
		gbc.gridx = 1;
		gbc.gridy = 4;
		contentPane.add(numberRangesPanel, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		//add label with message to enter a number
		contentPane.add(new JLabel("Guess the number with the selected range: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 8;
		//add the text field to type the number
		contentPane.add(randomNumberTextField, gbc);
		
		//add buttons for actions
		gbc.gridx = 0;
		gbc.gridy = 12;
		//add the buttons in one row
		contentPane.add(submitButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 12;
		contentPane.add(newGameButton, gbc);
		gbc.gridx = 2;
		gbc.gridy = 12;
		contentPane.add(saveButton, gbc);
		
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 16;
//		gbc.weightx = 100;
		
		separator.setPreferredSize(new Dimension(5, 24));
		
		contentPane.add(separator, gbc);
		
		//add labels and text files for calculation results
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 20;
		//add the label saying "Guess Results:" with the corresponding text field that displays the result
		contentPane.add(guessResultlabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 20;
		contentPane.add(resultTextField, gbc);	
			
	}
	
	private void setupListeners() {
		//submit button action listener
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					numberGuess();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				saveGameDetails();
			}
		});
		
		customRangeRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				customRangeTextField.setEnabled(customRangeRadioButton.isSelected());
			}
		});
		
		randomNumberTextField.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	try {
						numberGuess();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        }
		});

	}
	
	//method that generates random number and compares to the guess value
	private void numberGuess() throws Exception{
		try {
			int guess = Integer.parseInt(randomNumberTextField.getText());
			
			//will it throw an error if number guessed is greater than range?
			
			if (guess > number) {
				submitButton.setToolTipText("Too high. Try entering lower value");
				resultTextField.setText("Unfortunately that is not the number.");
			}
			else if (guess < number) {
				submitButton.setToolTipText("Too low. Try entering higher value");
				resultTextField.setText("Unfortunately that is not the number.");
			}	
			else {
				resultTextField.setText("Congratulations! You guessed the number.");
			}
			
			numbersGuessed.add(guess);
			
		}
		catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Invalid input value. Please input integer value", "Input Error", JOptionPane.ERROR_MESSAGE);	
		}
		
	}
	
	//Method to clear all the input and result text fields
	private void newGame() {
		try {
			if (fiftyRadioButton.isSelected()) {
				number = (int) Math.floor((Math.random()*(50-1+1)))+1;
			}
			else if (oneHundredRadioButton.isSelected()) {
				number = (int)Math.floor((Math.random()*(100-1+1)))+1;
			}
			else if (twoHundredRadioButton.isSelected()) {
				number = (int)Math.floor((Math.random()*(200-1+1))+1);
			}
			else if (customRangeRadioButton.isSelected()){
				String s = customRangeTextField.getText();
				if (!s.contains("-")) {
					throw new Exception("Invalid custom range input");
				}
				String[] tokenArray = s.split("-");
				int min = Integer.parseInt(tokenArray[0]);
				int max = Integer.parseInt(tokenArray[1]);
				number = (int)Math.floor((Math.random()*(max-min+1)))+min;	
			}
			else {
				throw new Exception("Range was not selected");
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		randomNumberTextField.setText("");
		resultTextField.setText("");
		submitButton.setToolTipText("Submit a guessed value that is between the range you selected!");
		
		//clears array list form the other game
		numbersGuessed.clear();
	
	}
	
	private void saveGameDetails() {
		try (PrintWriter writer = new PrintWriter(new FileWriter("game_details.txt", true))){
			//append game details to the file
			writer.print("Number guessed: ");
			for (int i = 0; i < numbersGuessed.size(); i++) {
				writer.print(numbersGuessed.get(i) + ", ");
			}
			writer.println("");
			
			//number generated printed to the file 
			writer.println("Random number: " + getNumber());
			
			if (fiftyRadioButton.isSelected()) {
				writer.println("Number Range: 1-50");
			}
			else if (oneHundredRadioButton.isSelected()) {
				writer.println("Number Range: 1-100");
			}
			else if (twoHundredRadioButton.isSelected()) {
				writer.println("Number Range: 1-200");
			}
			else {
				writer.println("Number Range: " + customRangeTextField.getText());
			}
			
			
			
			//compare if the text field for result is equal to the string will print with win or lose to file
			//if (string1.equals(string2))
			if ((resultTextField.getText()).equals("Unfortunately that is not the number.")) {
				writer.println("Game status: lost");
			}
			else if ((resultTextField.getText()).equals("Congratulations! You guessed the number.")) {
				writer.println("Game status: won");
			}
			writer.println("--------------------------------------------");
			
			JOptionPane.showMessageDialog(this, "Information saved to file successively");
				
		}
		catch(IOException ex) {
			JOptionPane.showMessageDialog(this, "Failed to save game details. Please try again.", "Save Error", JOptionPane.ERROR_MESSAGE);
			
		}
	}
	
	
	
	

	public static void main(String[] args) {
		//Run the GUI on the Event Dispatch Thread
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new RandomNumberGuessingGame(number).setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
	
	

}

	
	

}
