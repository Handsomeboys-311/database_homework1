package net;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
class user{//内部类，其字段对应用来存放、提取数据库中的数据
    int id;
    static String title="";
    int price;
    int person;

    //通过set方法，往类的实例里“存放”数据
    //通过get方法，从类的实例里“获得”数据，然后再通过插入数据库
    public void setId(int userid){
        this.id=userid;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public void setPerson(int person){
        this.person=person;
    }
    public Integer getId(){
        return id;
    }
    public static String getTitle(){
        return title;
    }
    public int getPrice(){
        return price;
    }
    public int getPerson(){
        return person;
    }

}
public class test {
    static String insql;
   static String upsql;
   static String delsql;
   static String cresql;
   static String dropsql;
   static String joinsql;
   static String sql="";
   static String name;
   static Connection conn=null;
   static ResultSet rs=null;
   static ResultSet rst=null;
   static Statement statement;
	 
	 public static boolean InsertSql(String insql,user user){
         try{
             PreparedStatement ps=conn.prepareStatement(insql);
             ps.setString(1, user.getTitle());
             ps.setInt(2, user.getPrice());
             ps.setInt(3, user.getPerson());
             int result=ps.executeUpdate();
             if(result>0){
            	 System.out.println("*****插入成功*****");
            	 return true;
             }
         }catch(Exception e){
             e.printStackTrace();
         }
         return false;
     }
	 public static void SelectSql(String sql){	
         try{
             Statement statement=conn.createStatement();
             rs=statement.executeQuery(sql);
             while(rs.next()){
                System.out.println("title:"+rs.getString("title")+"  "+"price:"+rs.getString("price")+"  "+"person:"+rs.getString("person")+"\n");
              }
             rs.close();
         }catch(Exception e){
             e.printStackTrace();
         }

     }
	  public static boolean UpdateSql(String upsql){
	        try {
	            PreparedStatement ps = conn.prepareStatement(upsql);
	            int result=ps.executeUpdate();//返回行数或者0
	            if(result>0){
	            	System.out.println("更新成功");
	            	return true;
	            }
	        } catch (SQLException ex) {
	        	ex.printStackTrace();	        }
	        return false;
	         }


	 public static boolean DeletSql(String delsql){
		 
	        try {
	            PreparedStatement ps = conn.prepareStatement(delsql);
	            int result=ps.executeUpdate();
	            if(result>0){
	            	System.out.println("删除成功.......");
	            	return true;
	            }
	        } catch (SQLException ex) {
	        	ex.printStackTrace();	        }
	        return false;
	         }
	 public static boolean Create(String cresql){
		 
	        try {
	            PreparedStatement ps = conn.prepareStatement(cresql);
	            int result=ps.executeUpdate();
	            if(result>0){
	            	System.out.println("数据表创建成功.......");
	            	return true;
	            }
	        } catch (SQLException ex) {
	        	ex.printStackTrace();	        }
	        return false;
	         }
	 public static boolean Drop(String dropsql){
		 
	        try {
	            PreparedStatement ps = conn.prepareStatement(dropsql);
	            int result=ps.executeUpdate();
	            if(result>0){
	            	System.out.println("数据表删除成功.......");
	            	return true;
	            }
	        } catch (SQLException ex) {
	        	ex.printStackTrace();	        }
	        return false;
	         }
	 public static void Join(String joinsql){
		 
	        try {
	        	System.out.println(joinsql);
	             Statement statement=conn.createStatement();
	             rst=statement.executeQuery(joinsql);
	             if(rst.next()==false)
	            	 System.out.println("联结失败......");
	             while(rst.next()){
	            	 System.out.println("联结成功......");
	            	 System.out.println("title:"+rs.getString("title")+"  "+"price:"+rs.getString("price")+"\n");
	                //System.out.println("title:"+rs.getString("title")+"  "+"price:"+rs.getString("price")+"  "+"person:"+rs.getString("person")+
	                //		"title2:"+rs.getString("title2")+"  "+"price2:"+rs.getString("price2")+"\n");
	              }
	             rst.close();
	         }catch(Exception e){
	             e.printStackTrace();
	         }
	         }
	 public static void showtable() throws SQLException{
		 DatabaseMetaData meta = conn.getMetaData();  
		   ResultSet rb = meta.getTables(null, null, null,  
		   new String[] { "TABLE" });  
		   while (rb.next()) {  
			   System.out.println("*****数据库所包含的表为*****");
		       System.out.println("表名：" + rb.getString(3));  
		       }
		   return;
	 }
   public static void main(String[] args) throws Exception {
       String sql;
       
       try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           //下面的mysql就是前面创建的名称为mysql的数据源
           conn = DriverManager.getConnection("jdbc:odbc:311","root","ab123321");
           // 上面的两个root依次表示MySQL数据库的登陆用户名和密码，根据自己的设置而更改
           Statement stmt = conn.createStatement();
    	   System.out.println("********************mysql操作系统********************");
    	   boolean fg=true;
    	   while(fg){
    		   System.out.println("已连接到311数据库，请选择您要进行的操作......");
        	   System.out.println("1、查询现有数据表");
        	   System.out.println("2、对现有数据表进行操作");
        	   System.out.println("3、创建数据表");
        	   System.out.println("4、删除数据表");
        	   System.out.println("5、跨数据表");
        	   System.out.println("6、退出");
        	   Scanner s = new Scanner(System.in);
        	   String b=s.nextLine();
        	   int a =Integer.parseInt(b);
        	   switch(a){
        	   case 1:
        		   showtable();
        		   break;
        	   case 2:
        		   boolean flag=true;
                   while(flag){
                	   System.out.println("**********请选择您要进行的操作**********");
                	   System.out.println("1、插入");
                	   System.out.println("2、查询");
                	   System.out.println("3、更新");
                	   System.out.println("4、删除");
                	   System.out.println("5、返回主菜单");
                	   Scanner sc = new Scanner(System.in);
                	   String i=sc.nextLine();
                	   int ac=Integer.parseInt(i);
                	   switch(ac){
                	   case 1:
                		   System.out.println("*****请选择要插入的表格*****");
                		   String p=sc.nextLine();
                		   boolean ty=true;
                		   while(ty){
                			   user c=new user();               		   
                    		   System.out.println("*****请输入插入数据的title*****");
                    		   String bc=sc.nextLine();     
                    		   c.setTitle(bc);
                    		   System.out.println("*****请输入插入数据的price*****");
                    		   String x=sc.nextLine();
                    		   c.setPrice(Integer.parseInt(x));
                    		   System.out.println("*****请输入插入数据的person*****");
                    		   String d=sc.nextLine();
                    		   c.setPerson(Integer.parseInt(d)); 
                    		   insql="insert into "+p+"(title,price,person) values(?,?,?)";
                    		   InsertSql(insql,c);
                    		   System.out.println("继续请输入1，回到上一级菜单请输入0");
                    		   String z=sc.nextLine();
                    		   if(Integer.parseInt(z)==0)
                    			   ty=false;
                		   }
                		   break;
                	   case 2:
                		   System.out.println("*****请选择要查询的表*****");
                		   String m=sc.nextLine();
                		   sql="select * from "+m;
                		   SelectSql(sql);
                		   break;
                	   case 3:
                		   System.out.println("*****请选择更新的表*****");
                		   String n=sc.nextLine();
                		   boolean k=true;
                		   while(k){
                			   System.out.println("*****请选择要更新的id*****");
                			   String y=sc.nextLine();
                			   boolean pt=true;
                    		   while(pt){
                    			   System.out.println("*****请选择要更新的数据项*****");
                            	   System.out.println("1、title");
                            	   System.out.println("2、price");
                            	   System.out.println("3、person");
                            	   System.out.println("4、quit");
                            	   String u=sc.nextLine();
                            	   switch(Integer.parseInt(u)){
                            	   		case 1:
                            	   			System.out.println("*****请输入更新数据的title*****");
                                 		    String w=sc.nextLine();
                                 		   upsql="update "+n+" set title="+w+" where idtext="+y;
                                 		  UpdateSql(upsql);
                                 		   break;
                            	   		case 2:
                            	   			System.out.println("*****请输入更新数据的price*****");
                                 		    String e=sc.nextLine();
                                 		   upsql="update "+n+" set price="+e+" where idtext="+y;
                                 		  UpdateSql(upsql);
                                 		   break;
                            	   		case 3:
                            	   			System.out.println("*****请输入更新数据的person*****");
                                 		   String r=sc.nextLine();
                                 		  upsql="update "+n+" set person="+r+" where idtext="+y;
                                 		 UpdateSql(upsql);
                                 		  break;
                            	   		case 4:
                            	   			pt=false;
                            	   			break;
                            	   }
                        		   
                    		   }
                    		   System.out.println("继续请输入1，回到上一级菜单请输入0");
                    		   String z=sc.nextLine();
                    		   if(Integer.parseInt(z)==0)
                    			   k=false;
                    		   else
                    			   k=true;
                		   }
                		   
                		   break;
                	   case 4:
                		   System.out.println("*****请选择要删除元素所在的表*****");
                		   String j=sc.nextLine();
                		   boolean h=true;    		  
                		   while(h){
                			   System.out.println("*****请选择要删除的id*****");
                			   String y=sc.nextLine();
                			   delsql="delete from "+j+" where idtext="+y;
                			   DeletSql(delsql);
                			   System.out.println("继续请输入1，回到上一级菜单请输入0");
                    		   String z=sc.nextLine();
                    		   if(z.equals("0"))
                    			   h=false;
                		   }      		   
                		   break;
                	   case 5:
                		   flag=false;
                		   break;
                	   }
                	   
                   } 
                   break;
        	   case 3:
        		   System.out.println("请输入创建数据表的指令（仅限一行）");
        		   cresql=s.nextLine();
        		   Create(cresql);
        		   break;
        	   case 4:
        		   System.out.println("请输入删除数据表的名称");
        		   dropsql="drop table "+s.nextLine();
        		   Drop(dropsql);
        		   break;
        	   case 5:
        		   System.out.println("请输入表1的名称：");
        		   String s1=s.nextLine();
        		   System.out.println("请输入表2的名称：");
        		   String s2=s.nextLine();
        		   System.out.println("请输入关联的关键字：");
        		   String s3=s.nextLine();
        		   joinsql="select t1.title,t1.price from "+s1+" t1"+" left outer join "+s2+" t2"+" on "+"t1."+s3+" = "+"t2."+s3;
        		   Join(joinsql);
        		   break;
        	   case 6:
        		   fg=false;
        		   break;
        	   }
    	   }
    	  
       }catch (SQLException e) {
           System.out.println("MySQL操作有误");
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           conn.close();
       }  

}
}