package net;
import java.sql.*;

import java.util.logging.Level;

import java.util.logging.Logger;
public class ConnectDatabase {
	  String drivename="com.mysql.jdbc.Driver";

	    String url="jdbc:mysql://localhost/shuishengmu";

	    String user="root";

	    String password="111111";

	    String insql;

	    String upsql;

	    String delsql;

	    String sql="select * from user";

	    String name;

	    Connection conn;

	    ResultSet rs=null;

	 

	    public Connection ConnectMysql(){

	                try{

	            Class.forName(drivename);

	            conn = (Connection) DriverManager.getConnection(url, user, password);

	            if (!conn.isClosed()) {

	                System.out.println("Succeeded connecting to the Database!");

	            } else {

	                System.out.println("Falled connecting to the Database!");

	            }

	        }catch(Exception ex){

	            ex.printStackTrace();

	        }

	         return conn;

	    }

	 

	         public void CutConnection(Connection conn) throws SQLException{

	             try{

	                if(rs!=null);

	                if(conn!=null);

	             }catch(Exception e){

	             e.printStackTrace();

	             }finally{

	            rs.close();

	            conn.close();

	             }

	    }

	 

	 

	         class user{//内部类，其字段对应用来存放、提取数据库中的数据

	             int userid;

	             String username="";

	             String password="";

	             String email="";

	 

	             //通过set方法，往类的实例里“存放”数据

	             //通过get方法，从类的实例里“获得”数据，然后再通过插入数据库

	             public void setId(int userid){

	                 this.userid=userid;

	             }

	             public void setName(String username){

	                 this.username=username;

	             }

	             public void setPassword(String password){

	                 this.password=password;

	             }

	             public void setEmail(String email){

	                 this.email=email;

	             }

	             public Integer getId(){

	                 return userid;

	             }

	             public String getName(){

	                 return username;

	             }

	             public String getPassword(){

	                 return password;

	             }

	             public String getEmail(){

	                 return email;

	             }

	 

	         }

	         //插入、删除、更新的方法是一样的，不一样的是数据库参数

	         public boolean InsertSql(user user){

	             try{

	 

	                  insql="insert into user(userid,username,password,email) values(?,?,?,?)";

	                  //上面的方法比下面的方法有优势，一方面是安全性，另一方面我忘记了……

	                 //insql="insert into user(userid,username,password,email) values(user.getId,user.getName,user.getPassword,user.getEmail)";

	                 PreparedStatement ps=conn.prepareStatement(insql);

	                 //.preparedStatement(insql);

	                 //PreparedStatement  ps=(PreparedStatement) conn.prepareStatement(insql);

	                 ps.setInt(1, user.getId());

	                 ps.setString(2, user.getName());

	                 ps.setString(3, user.getPassword());

	                 ps.setString(4, user.getEmail());

	                 int result=ps.executeUpdate();

	                 //ps.executeUpdate();无法判断是否已经插入

	                 if(result>0)

	                     return true;

	             }catch(Exception e){

	                 e.printStackTrace();

	             }

	             return false;

	         }

	 

	 

	         //与其他操作相比较，查询语句在查询后需要一个查询结果集（ResultSet）来保存查询结果

	         public void SelectSql(String sql){

	             try{

	                 Statement statement=conn.createStatement();

	                 rs=statement.executeQuery(sql);

	                 while(rs.next()){

	                     name=rs.getString("username");

	                    System.out.println(rs.getString("userid")+name+rs.getString("email"));

	                  }

	             }catch(Exception e){

	                 e.printStackTrace();

	             }

	 

	         }

	 

	         public boolean UpdateSql(String upsql){

	        try {

	            PreparedStatement ps = conn.prepareStatement(upsql);

	            int result=ps.executeUpdate();//返回行数或者0

	            if(result>0)

	                return true;

	        } catch (SQLException ex) {

	            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);

	        }

	        return false;

	         }

	 

	         public boolean DeletSql(String delsql){

	 

	        try {

	            PreparedStatement ps = conn.prepareStatement(upsql);

	            int result=ps.executeUpdate(delsql);

	            if(result>0)

	                return true;

	        } catch (SQLException ex) {

	            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);

	        }

	        return false;

	         }
}
