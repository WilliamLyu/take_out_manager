package Takeout_manage_system;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextArea;
public class Producer_GUI extends Thread{
	 private JFrame mainFrame;
	 private JFrame useFrame;
	 private Statement statement;
	 private Statement statement2;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;
	   private JTextArea orderlist;
	   private JLabel useheaderLabel;
	   private JTextArea show_area;
	   private JButton show_evaluation_button;
	   private JButton show_menu;
	   private JButton add_menu;
	   private JButton remove_menu;
	   
	   private Producer producer;
	   private int producer_id;
	   public Producer_GUI(){
		   producer=new Producer();
		   try
			{
				Class.forName(SQL_INFORMATION.DRIVER_MYSQL);		//����JDBC����
				//System.out.println("Driver Load Success.");
				
				Connection connection = DriverManager.getConnection(SQL_INFORMATION.URL);	//�������ݿ����Ӷ���
				statement = connection.createStatement();		//����Statement����
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   try
		   {
				
				Connection connection2 = DriverManager.getConnection(SQL_INFORMATION.URL);	//�������ݿ����Ӷ���
				statement2 = connection2.createStatement();		//����Statement����
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	   public void SwingControlDemo(){
	      prepareGUI();
	   }

	   public  void show(){
	      SwingControlDemo();      
	      showTextFieldDemo();
	   }

	   private void prepareGUI(){
	      mainFrame = new JFrame("�̼ҵ�¼");
	      mainFrame.setSize(400,400);
	      mainFrame.setLayout(new GridLayout(4, 1));
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });    
	      headerLabel = new JLabel("", JLabel.CENTER);        
	      statusLabel = new JLabel("",JLabel.CENTER);    

	      statusLabel.setSize(350,100);

	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());

	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(statusLabel);
	      /////////////////////////////////////////////////////////////////////
	      useFrame = new JFrame("�̼���Ϣ֪ͨ��");
	      useFrame.setSize(600,700);
	      useFrame.setLayout(null);
	      useFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });
	      this.useheaderLabel=new JLabel("����Ҫ��ע�Ķ������£�",JLabel.CENTER); 
	      this.useheaderLabel.setSize(350,100);
	      this.useheaderLabel.setBounds(0, 0, 400, 50);
	      orderlist=new JTextArea();
	      orderlist.setBounds(0, 50, 600, 350);
	      show_area=new JTextArea();
	      show_area.setBounds(0, 450, 600, 250);
	      show_evaluation_button = new JButton("�鿴����");
	      show_evaluation_button.setBounds(0, 400, 100, 50);
	      show_menu = new JButton("�鿴�˵�");
	      show_menu.setBounds(125, 400, 100, 50);
	      add_menu = new JButton("��Ӳ�Ʒ");
	      add_menu.setBounds(250, 400, 100, 50);
	      remove_menu = new JButton("ɾ����Ʒ");
	      remove_menu.setBounds(375, 400, 100, 50);
	      show_evaluation_button.addActionListener(new ActionListener(){
	    	  public void actionPerformed(ActionEvent e) {
	    		  	ResultSet result = null;
		   			String sql="SELECT * FROM ���� WHERE �̼Һ�='"+producer_id+"'";
		   			try
		   			{	
		   				result=statement.executeQuery(sql);
		   				show_area.setText("");
			   			while(result.next()) 
			   			{
			   				show_area.append(
			   						"�û��û���: " + result.getString(2)+"\n"
			   						+"��������: " + result.getString(3)+"\n") ;
			   			}	
		   			}
			   		catch (SQLException e1)
			   		{
			   			// TODO Auto-generated catch block
			   			e1.printStackTrace();
			   		}
	    	  }
	      });
	      show_menu.addActionListener(new ActionListener(){
	    	  public void actionPerformed(ActionEvent e) {
	    		  	ResultSet result = null;
		   			String sql="SELECT * FROM ��Ʒ WHERE �̼Һ�='"+producer_id+"'";
		   			try
		   			{	
		   				result=statement.executeQuery(sql);
		   				show_area.setText("");
			   			while(result.next()) 
			   			{
			   				show_area.append(
			   						"��Ʒ��: " + result.getString(1)+","
			   						+"����: " + result.getString(3)+","
			   						+"�۸�: " + result.getString(4)+"\n") ;
			   			}	
		   			}
			   		catch (SQLException e1)
			   		{
			   			// TODO Auto-generated catch block
			   			e1.printStackTrace();
			   		}
	    	  }
	      });
	      add_menu.addActionListener(new ActionListener(){
	    	  public void actionPerformed(ActionEvent e) {
	    		 String Id = JOptionPane.showInputDialog(useFrame,"���������");
	    		 if(Id==null)return;
		   		 String value = JOptionPane.showInputDialog(useFrame,"������۸�");
		   		 if(value==null)return;
		   		 producer.add_menu(Id, value);
		   		 JOptionPane.showMessageDialog(useFrame, "��Ʒ��ӳɹ�");
	    	  }
	      });
	      remove_menu.addActionListener(new ActionListener(){
	    	  public void actionPerformed(ActionEvent e) {
	    		 String Id = JOptionPane.showInputDialog(useFrame,"��������Ҫɾ���Ĳ�Ʒ��");
		   		 if(Id==null)return;
		   		 producer.remove_menu(Id);
		   		 JOptionPane.showMessageDialog(useFrame, "��Ʒɾ���ɹ�");
	    	  }
	      });
	      useFrame.add(useheaderLabel);
	      useFrame.add(orderlist);
	      useFrame.add(show_area);
	      useFrame.add(show_menu);
	      useFrame.add(show_evaluation_button);
	      useFrame.add(add_menu);
	      useFrame.add(remove_menu);
	      
	      mainFrame.setVisible(true);  
	      useFrame.setVisible(false);
	   }

	   private void showTextFieldDemo(){
	      headerLabel.setText("�̼ҵ�¼"); 

	      JLabel  namelabel= new JLabel("User ID: ", JLabel.RIGHT);
	      JLabel  passwordLabel = new JLabel("Password: ", JLabel.CENTER);
	      final JTextField userText = new JTextField(6);
	      final JPasswordField passwordText = new JPasswordField(6);      

	      JButton loginButton = new JButton("Login");
	      loginButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) { 
	        	 producer_id=Integer.parseInt(userText.getText());
	        	if(producer.login(userText.getText(), new String(passwordText.getPassword()))){
	        		mainFrame.setVisible(false);
	        		useFrame.setVisible(true);
	        		new Thread(new Runnable(){
	 		            @Override
	 		            public void run() {
	 		                while(true){
	 		                	scanorders();
	 		                	try {
	 								Thread.sleep(1000);
	 							} catch (InterruptedException e) {
	 								// TODO Auto-generated catch block
	 								e.printStackTrace();
	 							}
	 		                }
	 		            }
	 		        }).start();
	        	}
	        	else{
	        		statusLabel.setText("�û�������������"); 
	        	}
	                 
	         }
	      }); 

	      controlPanel.add(namelabel);
	      controlPanel.add(userText);
	      controlPanel.add(passwordLabel);       
	      controlPanel.add(passwordText);
	      controlPanel.add(loginButton);
	      mainFrame.setVisible(true);  
	   }
	   private void scanorders(){
		   orderlist.setText(null);
		   String sql="SELECT * FROM ���� WHERE �̼Һ�='"+this.producer_id+"'AND �����������=2";
			try {
				ResultSet rs=statement.executeQuery(sql);
				while(rs.next()){
					orderlist.append("������:"+rs.getString(1)+"   �û��û���:"+rs.getString(2)+"   ����Ա�ţ�"+rs.getString(4)+"\n");
					show_cart(rs.getString(2));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	   private void show_cart(String id){
		   String sql2="SELECT ���� FROM ���ﳵ,��Ʒ WHERE �û��û���='"+id+"'AND ���ﳵ.��Ʒ��=��Ʒ.��Ʒ��";
			
			try {
				ResultSet rs2=statement2.executeQuery(sql2);
				while(rs2.next()){
					orderlist.append(rs2.getString(1)+"\n");
				}
				orderlist.append("\n");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	   }
	   public void run(){
		   this.show();
	   }

}
