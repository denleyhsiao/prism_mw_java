package Prism.test.components;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Prism.core.AbstractImplementation;
import Prism.core.PrismConstants;

public class GUI extends AbstractImplementation {

	AddGUI frame;

	public GUI() {
	}

	public void start() {
		this.frame = new AddGUI(this);
		this.frame.pack();
		this.frame.setVisible(true);
	}

	public void addNumbers(String num1, String num2) {

		Prism.core.Event r = new Prism.core.Event("add", PrismConstants.REQUEST);
		r.addParameter("num1", num1);
		r.addParameter("num2", num2);
		this.send(r);
	}

	public void subNumbers(String num1, String num2) {
		Prism.core.Event r = new Prism.core.Event("sub", PrismConstants.REQUEST);
		r.addParameter("num1", num1);
		r.addParameter("num2", num2);
		this.send(r);
	}

	public void handle(Prism.core.Event n) {
		if (n.getName().equals("Result")) {
			int result = ((Integer) n.getParameter("result")).intValue();
			this.frame.setResult(result);
		}
	}

	class AddGUI extends Frame implements ActionListener {
		/**
		 * Each serializable class needs a serial UID.
		 */
		private static final long serialVersionUID = -7226634446339728798L;

		private TextField tfNum1, tfNum2, tfResult;

		private Button btAdd; // Declare "Add" button

		private Button btSub;

		private Button btDelete; // Declare "Delete" button

		private Button btClose; // Declare "Close" button

		GUI parent;

		// Constructor
		public AddGUI(GUI par) {
			this.parent = par;
			this.setTitle("Adding two numbers");

			// Use panel p1 to group text fields
			Panel p1 = new Panel();
			p1.setLayout(new FlowLayout());
			p1.add(new Label("Num1"));
			p1.add(this.tfNum1 = new TextField(3));
			p1.add(new Label("Num2"));
			p1.add(this.tfNum2 = new TextField(3));

			Panel p3 = new Panel();
			p3.setLayout(new FlowLayout());
			p3.add(new Label("Result"));
			p3.add(this.tfResult = new TextField(3));
			this.tfResult.setEditable(false); // Set tfResult noneditable

			// Use panel p2 for the buttons
			Panel p2 = new Panel();
			p2.setLayout(new FlowLayout());

			p2.add(this.btAdd = new Button("Add"));
			p2.add(this.btSub = new Button("Sub"));

			p2.add(this.btDelete = new Button("Delete"));
			p2.add(this.btClose = new Button("Close"));

			// Set FlowLayout for the frame and add panels to the frame
			this.setLayout(new BorderLayout());
			this.add(p1, BorderLayout.NORTH);
			this.add(p3, BorderLayout.CENTER);
			this.add(p2, BorderLayout.SOUTH);

			// Register listener
			this.btAdd.addActionListener(this);
			this.btSub.addActionListener(this);
			this.btDelete.addActionListener(this);
			this.btClose.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			String num1String = this.tfNum1.getText().trim();
			String num2String = this.tfNum2.getText().trim();

			if (e.getSource() == this.btAdd) {
				this.parent.addNumbers(num1String, num2String);
			} else if (e.getSource() == this.btSub) {
				this.parent.subNumbers(num1String, num2String);
			} else if (e.getSource() == this.btDelete) {
				this.tfNum1.setText("");
				this.tfNum2.setText("");
				this.tfResult.setText("");
			} else if (e.getSource() == this.btClose) {
				System.out.println("Closing...");
				this.setVisible(false); // hide the Frame
				this.dispose(); // free the system resources
				System.exit(0); // close the application
			}
		}

		public void setResult(int result) {
			this.tfResult.setText(String.valueOf(result));
		}

	}

}