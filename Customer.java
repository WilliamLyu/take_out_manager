package Takeout_manage_system;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer extends Thread{//��������
	private Statement statement;
	public boolean login_state;//��¼״̬
	private String user_id;
	public Customer(){
		login_state=false;
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
	
	public boolean register(String id, String phone, String key, String name, String add){
		login_state=false;
		String sql="INSERT INTO ������ VALUES ('"+id+"','"+phone+"','"+key+"','"+name+"','"+add+"')";
		try
		{
			statement.execute(sql);
			//System.out.println("��Ϣע��ɹ������¼");
			this.user_id=id;
			login_state=true;
			return true;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	public int login(String id,String key){//��¼����
		Scanner sc=new Scanner(System.in);
		int rcount=0;
		if(login_state==true){
			System.out.println("���ѵ�¼�������ظ���¼");
			return 2;
		}
			String sql="SELECT COUNT(*) FROM ������ WHERE consumer_id='"+id+"' AND consumer_key='"+key+"'";
			try {
				ResultSet rs=statement.executeQuery(sql);
				
				if(rs.next())rcount=rs.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(rcount==1){
				login_state=true;
			//	System.out.println("��¼�ɹ�");
				this.user_id=id;
				return 1;

			}
			else{
			//	System.out.println("�û�������������");
				return 0;
			}
		
	}
	public ResultSet get_restaurant_menu(){
		
		ResultSet result = null;
		String sql="SELECT * FROM �̼�";
		try
			{
				result = statement.executeQuery(sql);
				return result;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
/*		try
		{
			while(result.next()) {
				System.out.println("�̼Һ�: " + result.getString(1) 
						+ ", �̼���ϵ��ʽ: " + result.getString(2)
						+ ", �̼���: " +result.getString(3)
						+ ", �̼ҵ�ַ: " +result.getString(4));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}
public ResultSet get_menu(String Id){
		
		String res_id = Id;
		ResultSet result = null;
		String sql="SELECT * FROM ��Ʒ where �̼Һ�='"+Id+"'";
		try
			{
				result = statement.executeQuery(sql);
				return result;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
/*
		try
		{
			while(result.next()) {
				System.out.println("��Ʒ��: " + result.getString(1) 
						+ ", �̼Һ�: " + result.getString(2)
						+ ", ����: " +result.getString(3)
						+ ", �۸�: " +result.getString(4));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}
public int add_to_shopping_cart(String Id){
	int shopping_id=-1;
	int shop=-1;
	
	ResultSet result = null;
		System.out.println("�����������еĲ�Ʒ��");
		String food_id = Id;
		String sql="SELECT �̼Һ� FROM ��Ʒ WHERE ��Ʒ��='"+food_id+"'";
		try
		{
			result = statement.executeQuery(sql);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
		}
		try
		{
			if(result.next()){
				shop =result.getInt(1);
			}
			else{
				System.out.println("��Ʒ�����ڣ���������ȷ�Ĳ�Ʒ��");
				return 0;
			}
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(shopping_id==-1  ){//������ﳵ��û�����Ʒ
			shopping_id=shop;
			String sql2="INSERT INTO ���ﳵ VALUES ('"+this.user_id+"','"+food_id+"')";
			try
			{
				statement.execute(sql2);
				System.out.println("�Ѽ������ﳵ;");
				return 1;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(shopping_id==shop){
			String sql2="INSERT INTO ���ﳵ VALUES ('"+this.user_id+"','"+food_id+"')";
			try
			{
				statement.execute(sql2);
				System.out.println("�Ѽ������ﳵ;");
				return 2;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("���ﳵֻ������һ���̼ҵ���Ʒ�������Է������β���(y) �� ѡ����ѡ���̼ҵĲ�Ʒ(n)");
				String sql3="DELETE * FROM ���ﳵ WHERE �û��û���='"+this.user_id+"'";
				try
				{
					statement.execute(sql3);
					System.out.println("�����ѽ���");
					return 3;
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		
		return 4;
		
	}

	public ResultSet show_cart()
	{
		String sql="SELECT ��Ʒ.��Ʒ��,�̼Һ�,����,�۸�  FROM ���ﳵ,��Ʒ WHERE ���ﳵ.�û��û���='"+user_id+"'and "
				+ "���ﳵ.��Ʒ�� = ��Ʒ.��Ʒ�� ";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sql);
			return result;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	}
	public boolean evaluate(String Id , String Content){
	String res_id = Id;
	String evaluation = Content;
	String sql="INSERT INTO ���� VALUES ('"+ res_id +"','"+this.user_id+"','"+evaluation+"')";
	try
	{
		statement.execute(sql);
		return true;
	} catch (SQLException e)
	{
		// TODO Auto-generated catch bloc
		return false;
	}
	}
	public ResultSet show_evaluation(String Id){
	String res_id = Id;
	String sql="SELECT * FROM ���� WHERE �̼Һ� ='"+res_id+"'";;
	
	ResultSet result = null;
	try
	{
		result = statement.executeQuery(sql);
		return result;
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		//e.printStackTrace();
		return null;
	}
	}
	public void delete_cart()
	{
		String sql="delete from ���ﳵ where �û��û��� = '"+this.user_id+"'";
		try
		{
			statement.execute(sql);
			System.out.println("�ɹ�ɾ���û����ﳵ;");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public int Order(){//�µ���������������ѡ���̼Һ�����Ա
	String Food = null;
	String Producer_Id = null;
	int Order_Id=0;
	
	ResultSet result = null;//�ҵ����ﳵ�е�ʳ�������ĸ��̼�
	String sql="SELECT ��Ʒ�� FROM ���ﳵ WHERE �û��û���='"+this.user_id+"'";
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
		if(result.next()){
			Food =result.getString(1);
		}
		
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	String sql2 = "SELECT �̼Һ� FROM ��Ʒ WHERE ��Ʒ�� = '"+Food+"' ";
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
			Producer_Id = result.getString(1) ;
		}
		
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//�̼Һ�Ѱ�����
	String sql3 = "INSERT INTO ���� VALUES(0,'"+this.user_id+"','"+Producer_Id+"',0,1)";
	try
	{
		statement.execute(sql3);
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("�Ѿ����"+user_id+"�Ķ���");
	String sql4 = "SELECT ������ FROM ���� WHERE �û��û��� = '"+this.user_id+"'AND ��Ʒ�� = '"+Food+"'";
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
		while(result.next()) {
			Order_Id = result.getInt(1) ;
		}
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return Order_Id;
	
}
	public ResultSet Get_Food_Name(String Food_Id)
	{
		String sql = "SELECT * FROM ��Ʒ  WHERE ��Ʒ�� = '"+Food_Id+"'";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sql);
			return result;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
		
	public void run(){
		String input;
		System.out.println("�û������������ַ�����ȡ����\nlogin:  ��¼\nregister:  ע��\nmenu:  ��ȡ�˵�"
				+ "\nadd:  ��������ﳵ\norder:  �¶���\nevalute:  ����");
	}
	
	
}
