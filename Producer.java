package Takeout_manage_system;


import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Producer {
	
	private int 	Producer_Number		;
	private String 	Producer_PhoneNumber;
	private String 	Producer_Address	;
	private  String	Producer_Name		;
	private int 	Producer_Money		;					/*��ʾ��ʷһ���յ��Ľ�*/
	private Statement statement;
	public Producer()
	{
		Producer_Money = 0;
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
	
	public void register(String producer_name,String producer_address,String phone){
		this.Producer_Name=producer_name;
		this.Producer_Address=producer_address;
		this.Producer_PhoneNumber=phone;
		String sql="INSERT INTO �̼� VALUES (0,'"+phone+"','"+producer_name+"','"+producer_address+"')";
		try
		{
			statement.execute(sql);
			System.out.println("��Ϣע��ɹ���");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void add_menu(String id,String value){
		String sql="INSERT INTO ��Ʒ VALUES (0,'"+this.Producer_Number+"','"+id+"','"+value+"')";
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
	public void remove_menu(String id){
		String sql5 = "DELETE FROM ��Ʒ WHERE ��Ʒ��='"+id+"'";
		try
		{
			statement.execute(sql5);
			//System.out.println("��Ϣע��ɹ���");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void get_Number(int id){
		this.Producer_Number=id;
	}
	
	public boolean login(String id,String key){//��¼����
		
		int rcount=0;
		String sql="SELECT COUNT(*) FROM �̼� WHERE �̼Һ�='"+id+"' AND �̼�����='"+key+"'";
		try {
			ResultSet rs=statement.executeQuery(sql);
			
			if(rs.next())rcount=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rcount==1){
			this.Producer_Number=Integer.parseInt(id);
			return true;
		}
		else{
			System.out.println("�û�������������");
			return false;
		}
	
}
}
	
