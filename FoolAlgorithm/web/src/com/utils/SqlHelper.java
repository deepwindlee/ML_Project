/**
  * @param args
  * date:2015.12.31
  * function:���һ���������ݿ�Ĺ�����
  */
package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class SqlHelper  {

	//������Ҫ�ı���
    private  Connection ct=null;
    private  PreparedStatement ps=null;
    private  ResultSet rs=null;
    private  Properties pp=null;
    private  InputStream is=null;
	private CallableStatement cs=null;
	
	//�������ݿ�Ĳ���
    private  String driver=null;
    private  String url=null;
    private  String username=null;
    private  String password=null;
    
	/**
	 *  @���ܣ�����������ֻ��Ҫһ��
	 */
	public SqlHelper()
	{
		try {
			//�������ļ��ж�ȡdriver
			pp=new Properties();
		    is=SqlHelper.class.getClassLoader().getResourceAsStream("com/utils/dbinfo.properties");
			pp.load(is);
			url=pp.getProperty("url");
			driver=pp.getProperty("driver");
			username=pp.getProperty("username");
			password=pp.getProperty("password");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�������ݿ�����ʧ�ܣ�");
		}finally
		{ 
			//�ر��ļ���
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("�ر��ļ���ʧ�ܣ�");
			}
		}
	}
	
	
	/**
	 *  @���ܣ��õ�����
	 */
	public  Connection getConnection()
	{
		try {
			
			ct=DriverManager.getConnection(url,username,password);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("��ȡ����ʧ�ܣ�");
		}
		return ct;
	}
	
	/**
	 *  @���ܣ�Ϊ�˴ﵽ���������Դ��������ر���Դ���Ѳ�ѯ���Ľ������װ��һ��ArrayList��
	 */
	public  ArrayList query(String sql,String[] parameters)
	{
		ArrayList alList=null;
		//��̬�в�����ʹ��this
				ct=getConnection();
				try{
					ps=ct.prepareStatement(sql);
					ct.setAutoCommit(false);
					if(parameters!=null)
					{
						for(int i=0;i<parameters.length;i++)
						{
							ps.setString(i+1,parameters[i]);
						}
					}
					rs=ps.executeQuery();
					ct.commit();//�ύ���� 
					ct.setAutoCommit(true);
					alList=new ArrayList();
					//���Ի�ȡResultSet�е����Ժ���Ϣ�Ķ���
					ResultSetMetaData rSetMetaData=rs.getMetaData();
					//�õ�����
					int cloumn=rSetMetaData.getColumnCount();
					while(rs.next())
					{
						//һ���������飬����һ������
						Object ob[]=new Object[cloumn];
						for(int i=1;i<=cloumn;i++)
						{
							ob[i-1]=rs.getObject(i);//һ�������е����ݶ�ȡ�����ŵ�ob��
						}
						alList.add(ob);
					}
						
				}catch(Exception e)
				{
					 try {
						ct.rollback();//��������ع�  
					} catch (SQLException e1) {
						alList=null;
					} 
					 alList=null;
				}finally
				{
					close(rs, ps, ct);
				}
		return alList;
	}
	
	/**
	 *  @���ܣ�дһ��insert/delete/update����
	 */
	public  boolean executeUpdate(String sql,String[] parameters)
	{
		boolean b=true;
		try {
			ct=getConnection();
			ct.setAutoCommit(false);
			ps=ct.prepareStatement(sql);
			if(parameters!=null)
			{
				for(int i=0;i<parameters.length;i++)
				{
					ps.setString(i+1,parameters[i]);
				}
			}
			ps.executeUpdate();
			ct.commit();//�ύ���� 
			ct.setAutoCommit(true);
		} catch (Exception e) {
			try {
				ct.rollback();//��������ع�  
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			b=false;
			//�׳�ִ�в��ɹ��������쳣,�����øú����ĺ���һ��ѡ�񣬿��Դ���Ҳ���Բ�����
			throw new RuntimeException(e.getMessage());	
		}finally
		{
			close(rs,ps,ct);
		}
		return b;
	}
	
	/**
	 *  @���ܣ�//�ر���Դ
	 */
	public void close(ResultSet rs,PreparedStatement ps,Connection ct )
	{
		if(rs!=null)
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("�ر�rs����ʧ�ܣ�");
			}
		}
		
		if(ps!=null)
		{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("�ر�ps����ʧ�ܣ�");
			}
		}
		
		if(ct!=null)
		{
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("�ر�ct����ʧ�ܣ�");
			}
		}
	}
	
}
