package Takeout_manage_system;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


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
public class Delivery_Man{

	private int	Delivery_Man_Id;
	private String	PhoneNumber_Delivery_Man;
	//private int [] 	Salary_Delivery_Man;
	public boolean Delivery_situation;
	//private int Money ;
	private Statement statement;
	//private boolean If_Delivery_Done;		/*�Ƿ��������*/
	//private boolean If_Get_Food;			/*�Ƿ�ȡ�û���*/
	/*����Աȡ�͹���*/
	public Delivery_Man()
	{
		//Money = 0;
		this.Delivery_situation=false;
		//If_Delivery_Done = false;
		//If_Get_Food = false;
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
	}
	private String random_phone(){//��������绰���뺯��,�����ҪҲ���� 
		String base="0123456789";
		Random ran=new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<11;i++){
			int num=ran.nextInt(10);
			sb.append(base.charAt(num));
		}
		return sb.toString();
	}
	public void register(){
		this.PhoneNumber_Delivery_Man=random_phone();
		String sql="INSERT INTO ����Ա VALUES (0,'"+this.PhoneNumber_Delivery_Man+"',3000,'000000')";
		try
		{
			statement.execute(sql);
			//System.out.println("��Ϣע��ɹ���");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	public void get_Number(){
		String sql="SELECT ����Ա�� FROM ����Ա WHERE ����Ա��ϵ��ʽ='"+this.PhoneNumber_Delivery_Man+"'";
		try {
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next())
			this.Delivery_Man_Id=rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String show_order(int order){
		String sql="SELECT * FROM ���� WHERE ������='"+order+"'";
		try {
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()){
				String user_id=rs.getString(2);
				String producer_id=rs.getString(3);
				String ORDER="�����ţ�"+order+"  �ͻ��ţ�"+user_id+"  �̼Һţ�"+producer_id;
				return ORDER;
			}
			else{
				return "����ʹ����Ϣ����";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public String show_information(int order){
		String sql="SELECT * FROM ���� WHERE ������='"+order+"'";
		try {
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()){
				int state=rs.getInt(5);
				if(state==2){
					String sql2="SELECT * FROM �̼� WHERE �̼Һ�='"+rs.getInt(3)+"'";
					try{
						ResultSet rs2=statement.executeQuery(sql2);
						if(rs2.next()){
							String information="�̼���:"+rs2.getString(3)+"  �̼ҵ�ַ:  "+rs2.getString(4)+"  �̼���ϵ��ʽ:"+rs2.getString(2);
							
							rs.close();
							rs2.close();
							return information;
						}
						else{
							return null;
						}
					}
					catch(SQLException e){
						e.printStackTrace();
						return null;
					}
				}
				if(state==3){
					String sql3="SELECT * FROM ������ WHERE consumer_id='"+rs.getString(2)+"'";
					try{
						ResultSet rs2=statement.executeQuery(sql3);
						if(rs2.next()){
							String information="�û�ʵ��:"+rs2.getString(4)+"    �û���ַ:"+rs2.getString(5)+"    �û���ϵ��ʽ:"+rs2.getString(2);
							rs.close();
							rs2.close();
							return information;
						}
						else{
							return null;
						}
					}
					catch(SQLException e){
						e.printStackTrace();
						return null;
					}
				}
				else{
					return "������Ϣ����";
				}
			}
			else{
				return "������Ϣ����";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public boolean login(String id,String key){//��¼����
		
			int rcount=0;
			String sql="SELECT COUNT(*) FROM ����Ա WHERE ����Ա��='"+id+"' AND ����Ա����='"+key+"'";
			try {
				ResultSet rs=statement.executeQuery(sql);
				
				if(rs.next())rcount=rs.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(rcount==1){
				String sql2="SELECT ����Ա��ϵ��ʽ FROM ����Ա WHERE ����Ա��='"+id+"'";
				try {
					ResultSet rs=statement.executeQuery(sql2);
					if(rs.next()){
						this.PhoneNumber_Delivery_Man=rs.getString(1);
						this.Delivery_Man_Id=Integer.parseInt(id);
					}
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			else{
				System.out.println("�û�������������");
				return false;
			}
		
	}
	public int waitingfororders(){//����������ɨ�趩����������ֿ�ȡ���� ȡ֮
		int order_id=0;
		if(this.Delivery_situation)return 0;
		else{
			ResultSet result = null;
			String sql="SELECT * FROM ���� WHERE ����Ա��=0";
			try
				{
					result = statement.executeQuery(sql);
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try
			{
				if(result.next()) {
					order_id=result.getInt(1);
					String sql2="UPDATE ���� set ����Ա�� ='"+Delivery_Man_Id+"' WHERE ������='"+order_id+"'";
					try
					{
						statement.execute(sql2);
						System.out.println("����Ա"+this.Delivery_Man_Id+"�ѽӵ� �������̼�");
						this.Delivery_situation=true;
						
					} catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String sql4 = "UPDATE ���� set ����������� = 2 WHERE ������='"+order_id+"'";
					try
					{
						statement.execute(sql4);
					} catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("������������Ѿ�����Ϊ2(����Ա����ȡ��)");
					return order_id;
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return order_id;	
		}
	}
	public int read_user_id(){
		int User_Id = 0;
		ResultSet result = null;
		String sql1 = "SELECT �û��û��� FROM ���� WHERE ����Ա�� = '"+this.Delivery_Man_Id+"'";
		try
		{
			result = statement.executeQuery(sql1);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			if(result.next()) {
				User_Id = result.getInt(1) ;
				
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//�ҵ������ߵľ�����Ϣ
		System.out.println("����Ա "+Delivery_Man_Id+"�Ѿ���ȡ��������Ϣ"+User_Id);		
		return User_Id;
	}
	public int read_producer_id(){
		int Producer_Id = 0;
		ResultSet result = null;
		String sql2 = "SELECT �̼Һ� FROM ���� WHERE ����Ա�� = '"+this.Delivery_Man_Id+"'";
		try
		{
			result = statement.executeQuery(sql2);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			if(result.next()) {
				Producer_Id = result.getInt(1) ;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//�ҵ��̼���Ϣ
		System.out.println("����Ա "+Delivery_Man_Id+"�Ѿ���ȡ�̼���Ϣ"+Producer_Id);		
		return Producer_Id;
	}
	public void Get_Food()
	{
		//ResultSet result = null;
		//System.out.println("����Ա "+Delivery_Man_Id+"  �Ѿ��õ���Ʒ��");
		
		String sql5 = "UPDATE ���� set ����������� = 3 WHERE ����Ա�� = '"+this.Delivery_Man_Id+"'";
		try
		{
			statement.execute(sql5);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("������������Ѿ�����Ϊ3(����Ա�����Ͳ�)");
		
	}
	
	/*����Ա���͹���*/
	public void Delivery()
	{
		//System.out.println("����Ա: "+Delivery_Man_Id+"�Ѿ���ɵ��͡�");
		
		String sql5 = "DELETE FROM ���� WHERE ����Ա��='"+this.Delivery_Man_Id+"'";
		try
		{
			statement.execute(sql5);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("������������Ѿ�����Ϊ4(�û��Ѿ�ȡ����Ʒ)");
		this.Delivery_situation=false;
	}

	   
}
