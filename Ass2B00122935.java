/**
 * Author: Pawel Wodyk
 * Student ID: B00122935
 * Written on: 02/12/18
 * Function:
 *      Simple Vending Machine Software
 *      It offers 4 products to sell and accept input of 1-5
 *      When 5 is selected the input is disabled and user is prompted to t press the button to finish
 *      When user press button, program  calculate total and display it to the user.
 */

// Import Statements
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

//main program
public class Ass2B00122935 extends Applet implements KeyListener, ActionListener {

    // varieble declaration
    TextArea txaUserPrompt;
    Label lblCoffee, lblTea, lblSoup, lblWater;
    TextField txfCoffee, txfTea, txfSoup, txfWater;
    Button btnFinish;
    final static double PRICE_COFFEE = 2.0, PRICE_TEA = 2.0, PRICE_SOUP = 2.0, PRICE_WATER = 1.5;

    // init method
    public void init() {
        //center layout and set the spaceing between the objects
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        
        // adds keylister on the main object
        this.addKeyListener(this);

        // inicialize the TextArea object
        txaUserPrompt = new TextArea("Please enter number coresonding with the item you wish to purchase", 
                                                                            3, 60, TextArea.SCROLLBARS_NONE);

        // inicialize the Label objects
        lblCoffee = new Label("Coffee EUR 2.00 Press [1]");
        lblTea = new Label("Tea EUR 2.00 Press [2]");
        lblSoup = new Label("Soup EUR 2.00 Press [3]");
        lblWater = new Label("Water EUR 1.50 Press [4]");

        // inicialize the TextField objects
        txfCoffee = new TextField("0", 0);
        txfTea = new TextField("0", 0);
        txfSoup = new TextField("0", 0);
        txfWater = new TextField("0", 0);

        // set the button object
        btnFinish = new Button("Finish and Pay");


        //prevents user from editing the text fields since they need to be edited by the key press event
        txfCoffee.setEnabled(false);
        txfTea.setEnabled(false);
        txfSoup.setEnabled(false);
        txfWater.setEnabled(false);

        //sets the event listeners on Button and TextArea
        btnFinish.addActionListener(this);
        txaUserPrompt.addKeyListener(this);

        // prevents the user from changeing manualy values in the TextArea and sets the colors
        txaUserPrompt.setEditable(false);
        txaUserPrompt.setBackground(Color.WHITE);
        txaUserPrompt.setForeground(Color.BLACK);

        // adds the instances of the objects in order
        add(txaUserPrompt);
        add(lblCoffee); 
        add(txfCoffee);
        add(lblTea);  
        add(txfTea);
        add(lblSoup);  
        add(txfSoup);
        add(lblWater);  
        add(txfWater);
        add(btnFinish);

    }

    // draw the border for the visual appeal, this code was taken from oracle Applet Guide
    public void paint(Graphics g) {
        // Draw a Rectangle around the applet's display area.
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    // Key listener method - run when the key is pressed
    public void keyPressed(KeyEvent key) {
        // take the input from the keyboard and assign it to temporaty char k
        char k = key.getKeyChar();
        
        // declare temporary integer temp used to incement the value in text field
        int temp = 0;
        
        //switch statement used to determine which key was pressed
        switch (k) {
            case '1':
                showStatus("Coffee Selected"); // change status bar to indicate the action
                temp = Integer.parseInt(txfCoffee.getText()); // get old value from TextField
                txfCoffee.setText(String.valueOf(++temp)); // increment value in TextField
                //return the prompt to the user
                txaUserPrompt.setText("Coffee added.\n\nTotal Cups of Coffee selected: " + txfCoffee.getText());
                break;

            case '2':
                showStatus("Tea Selected");
                temp = Integer.parseInt(txfTea.getText());
                txfTea.setText(String.valueOf(++temp));
                txaUserPrompt.setText("Tea added.\n\nTotal Cups of Tea selected: " + txfTea.getText());
                break;

            case '3':
                showStatus("Soup Selected");
                temp = Integer.parseInt(txfSoup.getText());
                txfSoup.setText(String.valueOf(++temp));
                txaUserPrompt.setText("Soup added.\n\nTotal Cups of Soup selected: " + txfSoup.getText());
                break;

            case '4':
                showStatus("Water Selected");
                temp = Integer.parseInt(txfWater.getText());
                txfWater.setText(String.valueOf(++temp));
                txaUserPrompt.setText("Water added.\n\nTotal Bottles of Water selected: " + txfWater.getText());
                break;

            case '5':
                showStatus("To Exit press button");
                txaUserPrompt.removeKeyListener(this); // removes key listener so the user cannot change any values
                txaUserPrompt.setText("Thank you for your purchase \n\nPlease enter button below to see the total amount to pay");
                break;

            default:
                txaUserPrompt.setText("\nPlease select number between 1 and 5.");
                showStatus("Wrong input");
                break;
        }
    }

    // remaining classes of KeyListener interface that need to be declared but are not used in my program
    public void keyReleased(KeyEvent key) { }
    public void keyTyped(KeyEvent key) { }

    // action listener used on button
    public void actionPerformed(ActionEvent e){
        // indicate that button was pressed in the status bar
        showStatus("Button pressed");
        
        //remove all objects except the User Prompt TextArea
        lblCoffee.removeNotify();
        txfCoffee.removeNotify();
        lblTea.removeNotify();
        txfTea.removeNotify();
        lblSoup.removeNotify();
        txfSoup.removeNotify();
        lblWater.removeNotify();
        txfWater.removeNotify();
        btnFinish.removeNotify();

        //call external method calcTotal and set the value to the returned String
        txaUserPrompt.setText(calcTotal(Integer.parseInt(txfCoffee.getText()),
                                        Integer.parseInt(txfTea.getText()),
                                        Integer.parseInt(txfSoup.getText()),
                                        Integer.parseInt(txfWater.getText())));
        // set the row set from previous value
        txaUserPrompt.setRows(15);
        // however in order to resize the object I needed to use setSize method from parent class - Component
        txaUserPrompt.setSize(getWidth()-10, getHeight()-50);
        // prevent the user from changeing values
        txaUserPrompt.removeKeyListener(this);
        txaUserPrompt.setEditable(false);

    }

    private static String calcTotal(int amountCoffee, int amountTea, int amountSoup, int amountWater){
        // declaration of temporary variebles for total values of each product and total price to pay
        double totalCoffee, totalTea, totalSoup, totalWater, toPay;

        // calculate the total amount to pay for each product
        totalCoffee = amountCoffee * PRICE_COFFEE;
        totalTea = amountTea * PRICE_TEA;
        totalSoup = amountSoup * PRICE_SOUP;
        totalWater = amountWater * PRICE_WATER;
        
        // calculate total value to pay
        toPay = totalCoffee + totalTea + totalSoup + totalWater;

        //declare the String message and add the first line of text
        String message = "Thank you for your purchase!\n\n";

        //this if statement check what customer purchased and prints only aproprate messeges
        if (amountCoffee == 0 && amountTea == 0 && amountSoup == 0 && amountWater == 0) {
            message = message.concat("You did not purchased anything\n");
        } else {
            message = message.concat("You have purchased:\n");
            if (amountCoffee != 0)  message = message.concat("Coffee\t" + amountCoffee + "\tTotal:\t" + money(totalCoffee) + " EUR\n" );
            if (amountTea != 0)     message = message.concat("Tea\t" + amountTea + "\tTotal:\t" + money(totalTea) + " EUR\n" );
            if (amountSoup != 0)    message = message.concat("Soup\t" + amountSoup + "\tTotal:\t" + money(totalSoup) + " EUR\n" );
            if (amountWater != 0)   message = message.concat("Water\t" + amountWater + "\tTotal:\t" + money(totalWater) + " EUR\n" );
        }
        message = message.concat("\nTotal amount to Pay is: " + money(toPay) + " EUR");

        //this line returns the concatenated String so it could be presented to the user 
        return message;
    }

    // this method is used to display the approprete format for money
    public static double money(double amount){
        double output = Math.round(amount * 100);
        return output/100;
    }
}
//end of the program