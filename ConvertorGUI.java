import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * CSC-205: Huan Nguyen
 * <p>
 * Question: Write a Converter to convert from Decimal number to IEEE-754
 * 
 * @author Huan Nguyen
 * 
 */

public class ConvertorGUI extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2587656641518800487L;
	Font APPLICATION_FONT = new Font("Times new Roman", Font.BOLD, 15);
	Font INPUT_TEXT_FONT = new Font("Times new Roman", Font.BOLD, 12);
	Font BUTTON_FONT = new Font("Times new Roman", Font.BOLD, 12);

	Dimension WINDOW_SIZE = new Dimension(600, 540);
	Dimension DECIMAL_TEXT_SIZE = new Dimension(550, 30);
	Dimension RESULT_TEXT_SIZE = new Dimension(550, 30);
	Dimension COMBO_BOX_SIZE = new Dimension(100, 30);
	Dimension BUTTON_SIZE = new Dimension(100, 30);

	// declare components	
	JComboBox <String> cmbFloatType = new JComboBox <>(new String[] { 
			"        Please Choose One        ", 
			"[Decimal] ====> [IEEE 754 Single Float]", 
			"[Decimal] ====> [IEEE754 Double Float]" ,
			"[IEEE754 Single Float] ====> [Decimal]",
			"[IEEE754 Double Float] ====> [Decimal]"});

	JTextField txtDecimal = new JTextField();
	JTextField txtBinarySign = new JTextField();
	JTextField txtBinaryExponent = new JTextField();
	JTextField txtBinaryMantissa = new JTextField();

	JTextField txtResult = new JTextField();
	JButton btnConvert = new JButton();

	// Input Panel
	JPanel panInput = new JPanel();	
	JPanel panSelection = new JPanel();	
	JPanel panDecimal = new JPanel();
	JPanel panBinary = new JPanel();

	// Result Panel
	JPanel panResult = new JPanel();
	JPanel panNumber = new JPanel();
	JPanel panDemonstration = new JPanel();

	private static int BIAS = 0;
	private static int MANTISSA_MAGNITUDE = 0;
	private static int BINARY_BIT_LENGTH = 0;
	private static double A_DECIMAL_NUMBER = 0;
	private static int CONVERT_TYPE = 0;
	private static String A_BINARY_NUMBER ="", SIGN_BIT = "", EXPONENT_BITS = "", MANTISSA_BITS ="";

	
	/** Constructor method: initialize the window components
	 * 
	 * */
	public ConvertorGUI() {
		super("Decimal To IEEE 754 Convertor");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - WINDOW_SIZE.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - WINDOW_SIZE.getHeight()) / 2);
		setLocation(x, y);
		
		setSize(WINDOW_SIZE);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// declare the contain for the window
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(new FlowLayout(FlowLayout.LEADING));

		panInput.setPreferredSize(new Dimension(WINDOW_SIZE.width, 160));
		FlowLayout inputLayout = new FlowLayout(FlowLayout.LEADING);
		panInput.setLayout(inputLayout);
		panInput.setBorder(new EmptyBorder(0, 0, 0, 0));
		panInput.setBackground(Color.white);

		// Selection Panel		
		panSelection.setPreferredSize(new Dimension(WINDOW_SIZE.width, 40));
		FlowLayout selectionLayout = new FlowLayout(FlowLayout.LEADING);
		panSelection.setLayout(selectionLayout);
		panSelection.setBorder(new EmptyBorder(0, 0, 0, 0));
		panSelection.setBackground(Color.white);

		// Input a decimal number Panel		
		panDecimal.setPreferredSize(new Dimension(WINDOW_SIZE.width, 90));
		FlowLayout decimalLayout = new FlowLayout(FlowLayout.LEADING);
		panDecimal.setLayout(decimalLayout);
		panDecimal.setBorder(new EmptyBorder(0, 0, 0, 0));
		panDecimal.setBackground(Color.white);

		// Input a binary number panel
		panBinary.setPreferredSize(new Dimension(WINDOW_SIZE.width, 90));
		FlowLayout binaryLayout = new FlowLayout(FlowLayout.LEADING);
		panBinary.setLayout(binaryLayout);
		panBinary.setBackground(Color.white);

		// setting for the label
		JLabel lblTitle = new JLabel();
		lblTitle.setPreferredSize(new Dimension(WINDOW_SIZE.width, 30));
		lblTitle.setFont(APPLICATION_FONT);
		lblTitle.setText("DECIMAL - IEEE 754 SINGLE [DOUBLE] CONVERTOR");		
		// add the title
		panInput.add(lblTitle);

		// title for combo box
		JLabel lblSelection = new JLabel();
		lblSelection.setFont(APPLICATION_FONT);
		lblSelection.setText("Convert Type : ");		
		// combo box
		cmbFloatType.setActionCommand("cmbFloatType");
		cmbFloatType.addActionListener(this);
		//button convert
		btnConvert.setSize(BUTTON_SIZE);
		btnConvert.setText("Convert");
		btnConvert.setFont(BUTTON_FONT);
		btnConvert.setActionCommand("btnConvert");
		btnConvert.addActionListener(this);
		btnConvert.setPreferredSize(BUTTON_SIZE);
		btnConvert.setFocusable(true);

		//add two components to the selection panel
		panSelection.add(lblSelection);
		panSelection.add(cmbFloatType);
		panSelection.add(btnConvert);


		// decimal number label
		JLabel lblDecimal = new JLabel();
		lblDecimal.setPreferredSize(DECIMAL_TEXT_SIZE);
		lblDecimal.setFont(APPLICATION_FONT);
		lblDecimal.setText("Decimal Number");		

		// setting for txtDecimal
		txtDecimal.setBackground(Color.WHITE);
		txtDecimal.setPreferredSize(DECIMAL_TEXT_SIZE);
		txtDecimal.setFont(INPUT_TEXT_FONT);
		txtDecimal.setEditable(true);
		txtDecimal.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtDecimal.setText("");
		txtDecimal.setActionCommand("txtDecimal");
		txtDecimal.addActionListener(this);
		txtDecimal.addKeyListener(keyListener);

		panDecimal.add(lblDecimal);
		panDecimal.add(txtDecimal);
		panDecimal.setVisible(false);

		// setting for binary textboxes
		// sign label
		JLabel lblBinarySign = new JLabel();
		lblBinarySign.setPreferredSize(new Dimension(50, 30));
		lblBinarySign.setFont(APPLICATION_FONT);
		lblBinarySign.setText("Sign");		

		// sign label
		JLabel lblBinaryExponent = new JLabel();
		lblBinaryExponent.setPreferredSize(new Dimension(100, 30));
		lblBinaryExponent.setFont(APPLICATION_FONT);
		lblBinaryExponent.setText("Exponent Bits");

		// sign label
		JLabel lblBinaryMantissa = new JLabel();
		lblBinaryMantissa.setPreferredSize(new Dimension(400, 30));
		lblBinaryMantissa.setFont(APPLICATION_FONT);
		lblBinaryMantissa.setText("Mantissa Bits");

		// sign textbox
		txtBinarySign.setBackground(Color.WHITE);
		txtBinarySign.setPreferredSize(new Dimension(50,30));
		txtBinarySign.setFont(INPUT_TEXT_FONT);
		txtBinarySign.setEditable(true);
		txtBinarySign.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtBinarySign.setText("");
		txtBinarySign.setActionCommand("txtBinarySign");		
		txtBinarySign.addActionListener(this);
		txtBinarySign.addKeyListener(keyListener);

		// exponent textbox
		txtBinaryExponent.setBackground(Color.WHITE);
		txtBinaryExponent.setPreferredSize(new Dimension(100,30));
		txtBinaryExponent.setFont(INPUT_TEXT_FONT);
		txtBinaryExponent.setEditable(true);
		txtBinaryExponent.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtBinaryExponent.setText("");
		txtBinaryExponent.setActionCommand("txtBinaryExponent");		
		txtBinaryExponent.addActionListener(this);
		txtBinaryExponent.addKeyListener(keyListener);

		// exponent textbox
		txtBinaryMantissa.setBackground(Color.WHITE);
		txtBinaryMantissa.setPreferredSize(new Dimension(400,30));
		txtBinaryMantissa.setFont(INPUT_TEXT_FONT);
		txtBinaryMantissa.setEditable(true);
		txtBinaryMantissa.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtBinaryMantissa.setText("");
		txtBinaryMantissa.setActionCommand("txtBinaryExponent");		
		txtBinaryMantissa.addActionListener(this);
		txtBinaryMantissa.addKeyListener(keyListener);

		panBinary.add(lblBinarySign);
		panBinary.add(lblBinaryExponent);
		panBinary.add(lblBinaryMantissa);

		panBinary.add(txtBinarySign);
		panBinary.add(txtBinaryExponent);
		panBinary.add(txtBinaryMantissa);

		panBinary.setVisible(false);
		panBinary.setEnabled(false);				

		// add component to input panel
		panInput.add(panSelection);
		panInput.add(panDecimal);
		panInput.add(panBinary);

		// Result Panel
		panResult.setPreferredSize(new Dimension(WINDOW_SIZE.width, 380));
		FlowLayout resutlLayout = new FlowLayout(FlowLayout.LEADING);		
		panResult.setLayout(resutlLayout);
		panResult.setBackground(Color.white);

		panNumber.setPreferredSize(new Dimension(WINDOW_SIZE.width, 80));
		FlowLayout numberLayout = new FlowLayout(FlowLayout.LEADING);
		panNumber.setLayout(numberLayout);
		panNumber.setBackground(Color.white);

		panDemonstration.setPreferredSize(new Dimension(WINDOW_SIZE.width, 300));
		FlowLayout demonstrationLayout = new FlowLayout(FlowLayout.LEADING);
		panDemonstration.setLayout(demonstrationLayout);
		panDemonstration.setBackground(Color.white);


		// result label
		JLabel lblResult = new JLabel();
		lblResult.setPreferredSize(new Dimension(WINDOW_SIZE.width, 30));
		lblResult.setFont(APPLICATION_FONT);
		lblResult.setText("Result");	

		// setting for txtResult
		txtResult.setBackground(Color.WHITE);
		txtResult.setPreferredSize(RESULT_TEXT_SIZE);
		txtResult.setFont(INPUT_TEXT_FONT);
		txtResult.setEditable(true);
		txtResult.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtResult.setText("");
		txtResult.setActionCommand("txtInput");
		txtResult.addActionListener(this);

		panNumber.add(lblResult);
		panNumber.add(txtResult);

		// add component to result panel
		panResult.add(panNumber);
		panResult.add(panDemonstration);

		panResult.setVisible(false);

		// add component to result panel
		contentPane.add(panInput);
		contentPane.add(panResult);		
	}

	/** Update the result bits panel
	 * 
	 * */
	private void updateDemonstrationPanel ()
	{
		panDemonstration.removeAll();
		panDemonstration.revalidate();
		panDemonstration.repaint();  

		JPanel signPanel = new JPanel();		
		JPanel exponentPanel = new JPanel();
		JPanel mantissaPanel = new JPanel();		
		mantissaPanel.setPreferredSize(new Dimension(WINDOW_SIZE.width, 350));
		FlowLayout mantissaLayout = new FlowLayout(FlowLayout.LEADING);
		mantissaPanel.setLayout(mantissaLayout);

		JCheckBox[] listCheckBoxes = new JCheckBox[BINARY_BIT_LENGTH];		
		
		for (int i = BINARY_BIT_LENGTH-1; i >=0; i--)
		{			
			listCheckBoxes[i] = new JCheckBox("" + A_BINARY_NUMBER.charAt(BINARY_BIT_LENGTH - (i+1)));
			listCheckBoxes[i].setPreferredSize(new Dimension(40, 40));
			listCheckBoxes[i].setActionCommand("chk" + i);
			listCheckBoxes[i].addActionListener(this);
			listCheckBoxes[i].setBorder(BorderFactory.createEmptyBorder());
			listCheckBoxes[i].setBorder(null);
			listCheckBoxes[i].setHorizontalAlignment(SwingConstants.CENTER);
			listCheckBoxes[i].setVerticalAlignment(SwingConstants.TOP);
			listCheckBoxes[i].setHorizontalTextPosition(SwingConstants.CENTER);
			listCheckBoxes[i].setVerticalTextPosition(SwingConstants.TOP);

			if (A_BINARY_NUMBER.charAt(BINARY_BIT_LENGTH - (i+1)) == '1')
			{
				listCheckBoxes[i].setSelected(true);
				listCheckBoxes[i].setBackground(new Color(40, 150, 97));
			}

			if (i == BINARY_BIT_LENGTH-1) 
			{
				signPanel.add(listCheckBoxes[i]);
			} 
			else if (i <= BINARY_BIT_LENGTH -2 && i >= MANTISSA_MAGNITUDE)
			{
				exponentPanel.add(listCheckBoxes[i]);
			} 
			else
			{
				mantissaPanel.add(listCheckBoxes[i]);
			}
		}
		panDemonstration.add(signPanel);
		panDemonstration.add(exponentPanel);
		panDemonstration.add(mantissaPanel);
	}

	
	/** Convert decimal number to binary IEEE-754 Single OR Double Precision Float
	 * 
	 * */
	private void decimalToIEEE754() {
		String signBits = "", exponentBits = "", mantissaBits = "";		
		int anExponent = 0;
		long wholePartNumber = 0;
		double fractionPartNumber = 0;
		String aBinaryNumber = "", aBinaryFractionNumber = "";
		String aFraction = "";
		// bit sign
		signBits = A_DECIMAL_NUMBER >= 0 ? "0" : "1";
		A_DECIMAL_NUMBER = Math.abs(A_DECIMAL_NUMBER);
		
		wholePartNumber = (long)A_DECIMAL_NUMBER;
		//fractionPartNumber = A_DECIMAL_NUMBER - wholePartNumber;
		
		aFraction = "" + A_DECIMAL_NUMBER;
		aFraction = "0." + aFraction.substring(aFraction.indexOf('.') +1);		
		fractionPartNumber = Double.parseDouble(aFraction);

		aBinaryNumber = Long.toBinaryString(wholePartNumber);
		
		
		// get the binary for fraction part
		if (fractionPartNumber > 0)
		{
			while (fractionPartNumber > 0)
			{
				fractionPartNumber *=2;
				if (fractionPartNumber >= 1) {
					aBinaryFractionNumber += "1";
					
					aFraction = "" + fractionPartNumber;
					aFraction = "0." + aFraction.substring(2);
					
					fractionPartNumber = Double.parseDouble(aFraction);
				} else {
					aBinaryFractionNumber += "0";
				}
				// get more bits for mantissa part
				// more bits mean more accurate
				if (aBinaryFractionNumber.length() >= MANTISSA_MAGNITUDE +10) {
					break;
				}
			}
			aBinaryNumber += "." + aBinaryFractionNumber;
		}
		else
		{
			aBinaryNumber += ".0";
		}
		
		if (A_DECIMAL_NUMBER >=1)
		{
			//100101.10101
			anExponent = aBinaryNumber.indexOf('.') - 1;
			mantissaBits = aBinaryNumber.substring(1, anExponent + 1) + aBinaryNumber.substring(anExponent + 2);
			anExponent = anExponent + BIAS;
		}
		else
		{
			// 0.0001010
			// 00001.
			if (aBinaryNumber.indexOf('1') > 0)
			{
				anExponent = (aBinaryNumber.indexOf('1') -1);			
				mantissaBits = aBinaryNumber.substring(anExponent + 2);
			}
			else
			{
				anExponent = BIAS;
			}
			
			anExponent = BIAS - anExponent;
		}
		
		if (mantissaBits.length() > MANTISSA_MAGNITUDE)
		{
			mantissaBits = mantissaBits.substring(0, MANTISSA_MAGNITUDE);
		} 
		else
		{
			while (mantissaBits.length() < MANTISSA_MAGNITUDE) {
				mantissaBits += "0";
			}
		}

		Boolean moveLeft = anExponent <= BIAS ? true: false;

		exponentBits = Long.toBinaryString(anExponent);
		
		
		while (exponentBits.length() < BINARY_BIT_LENGTH - MANTISSA_MAGNITUDE-1) {
			if (moveLeft) {
				exponentBits = "0" + exponentBits;
			}
			else
			{
				exponentBits += "0";
			}			
		}

		//update the variables
		SIGN_BIT = signBits;
		EXPONENT_BITS = exponentBits;
		MANTISSA_BITS = mantissaBits;

		//set text for binary text boxes
		txtBinarySign.setText(SIGN_BIT);
		txtBinaryExponent.setText(EXPONENT_BITS);
		txtBinaryMantissa.setText(MANTISSA_BITS);

		A_BINARY_NUMBER = SIGN_BIT + EXPONENT_BITS + MANTISSA_BITS;		
	}

	/** Convert binary IEEE-754 Single OR Double Precision Float to a binary number
	 * */
	private void ieee754ToDecimal() {
		double aNumber = 0;
		int anExponent = 0;
		int i = 0;

		for (i = 0; i< EXPONENT_BITS.length();i++)
		{
			// We calculate when a digit is 1 only
			if(EXPONENT_BITS.charAt(i) == '1')
			{
				anExponent += Math.pow(2, EXPONENT_BITS.length() - i -1);
			}
		}
		// case 0
		if (anExponent != 0)
		{
			anExponent -= BIAS;
		}		
		
		if (anExponent >= Math.pow(2, EXPONENT_BITS.length() -1) && isMantissaZero())
		{
			JOptionPane.showMessageDialog(panInput, "This is a Infinity number!", "Infinity Number!",JOptionPane.INFORMATION_MESSAGE);			
		}
		else if (anExponent >= Math.pow(2, EXPONENT_BITS.length() -1) && !isMantissaZero())
		{
			JOptionPane.showMessageDialog(panInput, "This is not NaN!", "NaN",JOptionPane.INFORMATION_MESSAGE);			
		}
		else
		{
			for (i = 0; i< MANTISSA_BITS.length();i++)
			{
				// We calculate when a digit is 1 only
				if(MANTISSA_BITS.charAt(i) == '1')
				{
					aNumber += Math.pow(2, -(i + 1));			
				}
			}
			// case 0
			if (anExponent != 0)
			{
				aNumber = (1 + aNumber) * Math.pow(2, anExponent);
			}
	
			if (SIGN_BIT.equals("1"))
			{
				aNumber = -aNumber;
			}
		}

		//set text for binary text boxes
		txtBinarySign.setText(SIGN_BIT);
		txtBinaryExponent.setText(EXPONENT_BITS);
		txtBinaryMantissa.setText(MANTISSA_BITS);

		A_DECIMAL_NUMBER = aNumber;

	}
	
	private Boolean isMantissaZero ()
	{
		for(int i = 0;i < MANTISSA_BITS.length(); i++)
		{
			if (MANTISSA_BITS.charAt(i) == '1')
				return false;
		}
		return true;
	}

	private void checkInputData ()
	{		
		if (txtBinarySign.getText().length() > 1)
		{ 
			txtBinarySign.setText(txtBinarySign.getText().substring(0, 1));        	  
		}

		if (txtBinaryExponent.getText().length() > (BINARY_BIT_LENGTH - MANTISSA_MAGNITUDE - 1))
		{ 
			txtBinaryExponent.setText(txtBinaryExponent.getText().substring(0, BINARY_BIT_LENGTH - MANTISSA_MAGNITUDE - 1));        	  
		}

		if (txtBinaryMantissa.getText().length() > MANTISSA_MAGNITUDE)
		{ 
			txtBinaryMantissa.setText(txtBinaryMantissa.getText().substring(0, MANTISSA_MAGNITUDE));        	  
		}
	}

	KeyListener keyListener = new KeyListener() {
		public void keyPressed(KeyEvent keyEvent) {			
			int keyCode = keyEvent.getKeyCode();        

			// for Enter
			if (keyCode == 10)
				btnConvert.requestFocus();
		}

		public void keyReleased(KeyEvent keyEvent) {
			// verify the input data
			checkInputData();        
		}

		public void keyTyped(KeyEvent keyEvent) {			
			// verify the input data
			checkInputData();  
		}
	};

	/** Data validation
	 * */
	private boolean isDataValid ()
	{
		Boolean isValid = true;
		Boolean hasFloatPoint = false;
		char[] aListNumber;

		switch (CONVERT_TYPE)
		{
		// check a decimal number
		case 1:				
		case 2:
			aListNumber = txtDecimal.getText().toCharArray();
			int i = 0;
			if (aListNumber[0] == '-')
			{
				i = 1;
			}
			for (; i < aListNumber.length; i++)
			{
				// if the string has a ., this means that this is a decimal number
				if (aListNumber[i] == '.')
				{
					// a valid decimal string is when it has only one .
					if (!hasFloatPoint)
					{
						hasFloatPoint = true;
					}
					else
					{
						isValid = false;
						throw new IllegalArgumentException("[INVALID FORMAT]! The number must contain only one float point '.'!");
					}
				}
				else if ((int)aListNumber[i] < 48 || (int)aListNumber[i] > 57)
				{
					// a valid number must also in the range of 0 and 9
					isValid = false;
					throw new IllegalArgumentException("[INVALID FORMAT]! The number must contain digit from 0-9!");
				} 
			}
			return isValid;				
			// check binary number
		case 3:				
		case 4:
			aListNumber = txtBinarySign.getText().toCharArray();				
			// check bit sign
			if (aListNumber.length > 1)
			{
				isValid = false;
				throw new IllegalArgumentException("[INVALID FORMAT]! The sign bit must be 1 digit only!");
			}
			else
			{
				//not 0 or 1
				if ((int)aListNumber[0] != 48 && (int)aListNumber[0] != 49)
				{
					isValid = false;
					txtBinarySign.requestFocus();
					throw new IllegalArgumentException("[INVALID FORMAT]! The sign bit must be 0 or 1!");					
				}
			}

			// check exponent bits
			aListNumber = txtBinaryExponent.getText().toCharArray();
			for (i = 0; i< aListNumber.length; i++)
			{
				// accept binary number only
				if ((int)aListNumber[i] < 48 || (int)aListNumber[i] > 49)
				{
					isValid = false;
					txtBinaryExponent.requestFocus();
					throw new IllegalArgumentException("[INVALID FORMAT]! The exponent bits must be 0 or 1!");
				}
			}

			aListNumber = txtBinaryMantissa.getText().toCharArray();
			for (i = 0; i< aListNumber.length; i++)
			{
				// accept binary number only
				if ((int)aListNumber[i] < 48 || (int)aListNumber[i] > 49)
				{
					isValid = false;
					txtBinaryMantissa.requestFocus();
					throw new IllegalArgumentException("[INVALID FORMAT]! The mantissa bit must be 0 or 1!");
				}
			}
			return isValid;

		}

		return isValid;
	}

	/**
	 * implement the function for action listener
	 * 
	 * @param e
	 *            the current event that users interact with
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		int cmbSelectedIndex;

		// first get the convert type that user choose
		cmbSelectedIndex = cmbFloatType.getSelectedIndex();
		try
		{
			if (cmbSelectedIndex != 0) {
				// set the convert type
				CONVERT_TYPE = cmbSelectedIndex;

				switch (CONVERT_TYPE) {
				case 1:
				case 3:
					BIAS = 127;				
					MANTISSA_MAGNITUDE = 23;
					BINARY_BIT_LENGTH = 32;
					break;

				case 2:
				case 4:
					BIAS = 1023;
					MANTISSA_MAGNITUDE = 52;
					BINARY_BIT_LENGTH = 64;
					break;
				}
				switch (actionCommand) {
				case "btnConvert":				
					// input data validation
					if (isDataValid())
					{
						switch (CONVERT_TYPE) {
						// case 1, 2 is to convert from a Decimal number to IEEE754
						// Single or IEEE754 Double Float Point
						case 1:
						case 2:					
							
							A_DECIMAL_NUMBER = Double.parseDouble(txtDecimal.getText());							
							//do the converter
							decimalToIEEE754();
							txtResult.setText(A_BINARY_NUMBER);						
							break;
						case 3:
						case 4:
							SIGN_BIT = txtBinarySign.getText().trim();
							EXPONENT_BITS = txtBinaryExponent.getText().trim();
							MANTISSA_BITS = txtBinaryMantissa.getText().trim();

							ieee754ToDecimal();			
							txtResult.setText(""+A_DECIMAL_NUMBER);
							break;
						}
						panResult.setVisible(true);
						updateDemonstrationPanel();
					}
					break;
				case "cmbFloatType":				
					switch (CONVERT_TYPE) {
					case 1:
					case 2:
						panDecimal.setVisible(true);
						txtDecimal.requestFocus();
						
						panBinary.setVisible(false);
						break;

					case 3:
					case 4:
						panDecimal.setVisible(false);
						panBinary.setVisible(true);
						txtBinarySign.requestFocus();
						break;
					}
					break;

				default:
					// make sure that user click a checkbox
					if (actionCommand.substring(0, 3).equals("chk")) {
						panDecimal.setVisible(false);
						panBinary.setVisible(true);
						
						if(CONVERT_TYPE == 1)
							cmbFloatType.setSelectedItem(3);
						else if (CONVERT_TYPE == 2)
							cmbFloatType.setSelectedItem(4);

						int index = Integer.parseInt(actionCommand.substring(3));
						// int stringLocation = BINARY_BIT_LENGTH - (index+1);
						String tempString = "";
						String  letterAtIndex = "";
						letterAtIndex =A_BINARY_NUMBER.substring(BINARY_BIT_LENGTH - (index+1), BINARY_BIT_LENGTH - index);
						if (letterAtIndex.equals("1"))
						{
							tempString = A_BINARY_NUMBER.substring(0, BINARY_BIT_LENGTH - (index+1)) + "0" + A_BINARY_NUMBER.substring(BINARY_BIT_LENGTH - index);
						}
						else
						{
							tempString = A_BINARY_NUMBER.substring(0, BINARY_BIT_LENGTH - (index+1)) + "1" + A_BINARY_NUMBER.substring(BINARY_BIT_LENGTH - index);
						}
						A_BINARY_NUMBER = tempString;
						//set text for binary text boxes
						SIGN_BIT = A_BINARY_NUMBER.substring(0,1);
						EXPONENT_BITS = A_BINARY_NUMBER.substring(1,BINARY_BIT_LENGTH - MANTISSA_MAGNITUDE);
						MANTISSA_BITS = A_BINARY_NUMBER.substring(BINARY_BIT_LENGTH - MANTISSA_MAGNITUDE);

						ieee754ToDecimal();			
						txtResult.setText(""+A_DECIMAL_NUMBER);
						updateDemonstrationPanel();
					}
					break;
				}			
			}
		}
		catch (IllegalArgumentException ex)
		{
			JOptionPane.showMessageDialog(panResult,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
