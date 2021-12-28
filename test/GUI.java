package Prism.test;

import Prism.core.*;
import java.awt.*;
import java.awt.event.*;


public class GUI extends AbstractImplementation  
{
	
	AddGUI frame;

	public GUI()
	{
	}

	public void start(){
		frame = new AddGUI(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void addNumbers(String num1, String num2){
	
		
		Prism.core.Event r = new Prism.core.Event ("add");
    	r.eventType = PrismConstants.REQUEST;
		r.addParameter ("num1", num1);
		r.addParameter ("num2", num2); 			
		send(r);
	}
	public void subNumbers(String num1, String num2){
		Prism.core.Event r = new Prism.core.Event ("sub");
		r.addParameter ("num1", num1);
		r.addParameter ("num2", num2);
    		r.eventType = PrismConstants.REQUEST;
		send(r);
	}
	public void handle(Prism.core.Event n){
		if (n.name.equals("Result")){
			int result = ((Integer) n.getParameter ("result")).intValue();
			frame.setResult(result);
		}
	}
	
	class AddGUI extends Frame implements ActionListener{
		private TextField tfNum1, tfNum2, tfResult;
		private Button btAdd; // Declare "Add" button
		private Button btSub;
		private Button btDelete; // Declare "Delete" button
		private Button btClose; // Declare "Close" button
		GUI parent;
				
		// Constructor
		public AddGUI(GUI par){
			parent = par;
			setTitle("Adding two numbers");

			// Use panel p1 to group text fields
			Panel p1 = new Panel();
			p1.setLayout(new FlowLayout());
			p1.add(new Label("Num1"));
			p1.add(tfNum1 = new TextField(3));
			p1.add(new Label("Num2"));
			p1.add(tfNum2 = new TextField(3));
				 
			 Panel p3 = new Panel();
			p3.setLayout(new FlowLayout());
			p3.add(new Label("Result"));
			p3.add(tfResult = new TextField(3));
			tfResult.setEditable(false);   // Set tfResult noneditable

			// Use panel p2 for the buttons
			Panel p2 = new Panel();
			p2.setLayout(new FlowLayout());
	  
			 p2.add(btAdd = new Button("Add"));
			 p2.add(btSub = new Button("Sub"));
			 
			 p2.add(btDelete = new Button("Delete"));
			 p2.add(btClose = new Button("Close"));

			// Set FlowLayout for the frame and add panels to the frame
			setLayout(new BorderLayout());
			add(p1, BorderLayout.NORTH);
				 add(p3, BorderLayout.CENTER);
			add(p2, BorderLayout.SOUTH);

			// Register listener
			btAdd.addActionListener(this);
			btSub.addActionListener(this);
			 btDelete.addActionListener(this);
			 btClose.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e){
      String num1String = tfNum1.getText().trim();
      String num2String = tfNum2.getText().trim();
	  
	  if (e.getSource() == btAdd) {
		  parent.addNumbers(num1String, num2String);
	  }
	  else if (e.getSource() == btSub) {
		  parent.subNumbers(num1String, num2String);
	  }
	  else if (e.getSource() == btDelete) {
		 tfNum1.setText("");
		 tfNum2.setText("");
		 tfResult.setText("");
	  }
	  else if (e.getSource() == btClose) {
		  System.out.println("Closing...");
		  this.setVisible(false);    // hide the Frame
		  this.dispose();            // free the system resources
		  System.exit(0);            // close the application  
	  }  
  }
  
  public void setResult(int result){
	  tfResult.setText(String.valueOf(result));	  
  }

}
	
}