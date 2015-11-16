import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Amino_Acid_Quiz extends JFrame{
	
	private static final long serialVersionUID = 7394662618364445516L;
	
	/**
	 * 
	 */
	
	public static String[] SHORT_NAMES =
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" 
		};
	public static String [] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"
		};
	
	/*public static final float FREQUENCY[] =
	{
		0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 
		0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 
		0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 
		0.05f, 0.05f, 0.05f, 0.05f, 0.05f
	}; */
      
	static int position = new Random().nextInt(FULL_NAMES.length);
   
	double correct = 0;
	double incorrect = 0;
     
  
	private JTextArea top = new JTextArea();
	private JTextArea timer = new JTextArea();
	private JTextField answer_field = new JTextField();
	private JButton submit = new JButton();
	private JButton bottom  = new JButton();
	
	private boolean check = false;
	
	private ArrayList<String> user_Questions = new ArrayList<String>();
	private ArrayList<String> user_Answers = new ArrayList<String>();	
	private String user_Submission = "";

    	
    public class Timer implements Runnable {
    	
    	 private ActionListener enter;
    	 private ActionListener cancel;
    	 private int time;
    	 
 		public Timer (int time, ActionListener cancel, ActionListener enter) 
 		{
			this.cancel = cancel;
			this.enter = enter;
			this.time = time;
		}
    	 
		@Override
		public void run()
		{
			while (time > 0)
			{
				if (check == true)
				{
					timer.setText(" ");
					return;
				}
				
				try 
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e) 
				{
					e.printStackTrace();
				}
				timer.setText(Integer.toString(time-1));
				time--;
			}
			exitSummary(enter, cancel);
		}
    	
    }
    
    public void startup_questions()
    {
    	check = true;
    	
		String begin_quest_ansr = "This is a test to determine the number of amino acid single letter codes you know."+"\n"+"\n"+"Are you ready to begin?"+"\n"+"(Please enter yes or no)";
		
		int reply = JOptionPane.showConfirmDialog(null,begin_quest_ansr, "Choose One", JOptionPane.YES_NO_OPTION);
	
		if (reply == JOptionPane.YES_OPTION)
		{;}
		else if (reply == JOptionPane.NO_OPTION)
		{
			JOptionPane.showMessageDialog(null,"Ok, please try again later."); 
			System.exit(0);
		} 
		
		String time_input_ansr = "How long would you like the quiz to last(in seconds)?";
		
		int time_reply = Integer.parseInt(JOptionPane.showInputDialog(time_input_ansr));
				
		quizLoop(time_reply);
    	
    }
    
    public void quizLoop(int inputTime)
    {
		setLocationRelativeTo(null);
		setSize(300,200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		top.setEditable(false);
		timer.setEditable(false);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(top, BorderLayout.PAGE_START);
		getContentPane().add(timer, BorderLayout.LINE_START);
		getContentPane().add(answer_field, BorderLayout.CENTER);
		getContentPane().add(submit, BorderLayout.LINE_END);
		getContentPane().add(bottom, BorderLayout.PAGE_END);

    	timer.setText(String.valueOf(inputTime));
		top.setText("What is the single letter code for: "+FULL_NAMES[position]+"?");
		answer_field.setText("");
		submit.setText("Submit");
		bottom.setText("Cancel Game");
		
		final ActionListener submission = new ActionListener()
		{			
			public void actionPerformed(ActionEvent e)
			{
				String currentQuestion = top.getText();
				user_Submission += answer_field.getText().toUpperCase();
				user_Questions.add(currentQuestion);
				user_Answers.add(user_Submission);			
				
        		
        		if ( user_Submission.equals(SHORT_NAMES[position]))
        			{
        				JOptionPane.showMessageDialog(null, "That is correct!");
        				correct++;
                        
                        position = new Random().nextInt(FULL_NAMES.length);                  
                        top.setText("What is the single letter code for: "+FULL_NAMES[position]+"?");
                        answer_field.setText("");
        				user_Submission = "";
        				
        			} 
        		else if ( user_Submission != SHORT_NAMES[position])
        			{
        				JOptionPane.showMessageDialog(null, "That is incorrect. The single letter code for "+FULL_NAMES[position]+" is "+SHORT_NAMES[position]);
        				incorrect++;
        				
                        position = new Random().nextInt(FULL_NAMES.length);                  
                        top.setText("What is the single letter code for: "+FULL_NAMES[position]+"?");
                        answer_field.setText("");
        				user_Submission = "";
        			}
			}
		};
				
		//Cancel mid-game
		ActionListener cancel = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String cancel_msg = "Thank you for playing. Would you like to restart the quiz?";
				int reply = JOptionPane.showConfirmDialog(null, cancel_msg, "Choose One", JOptionPane.YES_NO_OPTION);
			
				if (reply == JOptionPane.YES_OPTION)
				{
					startup_questions();
				}
				else if (reply == JOptionPane.NO_OPTION)
				{
					JOptionPane.showMessageDialog(null,"Ok, please try again later."); 
					System.exit(0);
				} 
			}
		};
		
		submit.addActionListener(submission);
		bottom.addActionListener(cancel);
		answer_field.addActionListener(submission);
		
		try 
		{	
			check = false;
			int time = Integer.parseInt(timer.getText());
			new Thread(new Timer(time, submission, cancel)).start();
		} 
		catch (Exception ex) 
		{
		    JOptionPane.showMessageDialog(null, "You did not enter a number.");		    
			submit.removeActionListener(submission);
			bottom.removeActionListener(cancel);
			answer_field.removeActionListener(submission);
			startup_questions();
		}
	} 
    
    public void exitSummary(ActionListener cancel, ActionListener submission) 
    {	
    	check = true;		
		submit.removeActionListener(submission);
		bottom.removeActionListener(cancel);
		answer_field.removeActionListener(submission);
		
		double score = (correct/(correct+incorrect)*100);
		DecimalFormat df = new DecimalFormat("#.00");
		String roundedscore = df.format(score);
		
		String ansr = "Thank you for playing."+"\n"+"Your final score is: "+roundedscore+"%"+"\n"+ "Would you like to restart the quiz?";
		int reply = JOptionPane.showConfirmDialog(null, ansr, "Choose One", JOptionPane.YES_NO_OPTION);
	
		if (reply == JOptionPane.YES_OPTION)
		{
			startup_questions();
		}
		else if (reply == JOptionPane.NO_OPTION)
		{
			JOptionPane.showMessageDialog(null,"Ok, please try again later."); 
			System.exit(0);
		}

    } 
	
    public Amino_Acid_Quiz()
    {
    	startup_questions();
    }
    
    public static void main(String[] args) 
	{
    	new Amino_Acid_Quiz();
    	      			
	}	
}
	
	
	
	
	
	
