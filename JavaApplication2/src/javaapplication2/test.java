/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication2;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import redis.clients.jedis.Jedis;
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
   static Jedis jedis;
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
	 public static void SelectSql(String sql,String m,String  y){	
         try{
             Statement statement=conn.createStatement();
             rs=statement.executeQuery(sql);
             String context="";
            while(rs.next()){
                context+="title:"+rs.getString("title")+"  "+"price:"+rs.getString("price")+"  "+"person:"+rs.getString("person")+"\n";
              jedis.set(m+y,"title:"+rs.getString("title")+"  "+"price:"+rs.getString("price")+"  "+"person:"+rs.getString("person")+"\n"
                      ); 
              jedis.expire(m+y, 60);
            }      
             showMessageDialog(null,context);
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
	            	 showMessageDialog(null,"更新成功");
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
	            	 showMessageDialog(null,"删除成功.......");
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
	            	 showMessageDialog(null,"数据表创建成功.......");
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
	             showMessageDialog(null,"数据表删除成功.......");
	            	return true;
	            }
	        } catch (SQLException ex) {
	        	ex.printStackTrace();	        }
	        return false;
	         }
	 public static void Join(String joinsql){
	        try {
	        	//System.out.println(joinsql);
	             Statement statement=conn.createStatement();
	             rst=statement.executeQuery(joinsql);
	             if(rst.next()==false)
	            	  showMessageDialog(null,"联结失败......");
	             while(rst.next()){
	            	  showMessageDialog(null,"联结成功......\n"+"title:"+rs.getString("title")+"  "+"price:"+rs.getString("price")+"\n");
	            	  
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
                   String list="";
		   while (rb.next()) {  
			   list+="*****数据库所包含的表为*****\n";
		       list+="表名：" + rb.getString(3);  
		       }
                    showMessageDialog(null,list);
		   return;
	 }
   public static void main(String[] args) throws Exception {
       String sql;
       jedis=new Jedis("localhost");
       try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           //下面的mysql就是前面创建的名称为mysql的数据源
           conn = DriverManager.getConnection("jdbc:odbc:homework","root","password");
           // 上面的两个root依次表示MySQL数据库的登陆用户名和密码，根据自己的设置而更改
           Statement stmt = conn.createStatement();
    	 
    	   boolean fg=true;
    	   while(fg){
                String str ="********************mysql操作系统********************\n"
                +  "1,查询现有数据表\n"

                + "2,对现有数据表进行操作\n"

                + "3,创建数据表\n"

                + "4,删除数据表\n"

                + "5,跨数据表\n"
                +"6.退出\n";

        String b = showInputDialog(str);
        	   int a =Integer.parseInt(b);
        	   switch(a){
        	   case 1:
        		   showtable();
        		   break;
        	   case 2:
        		   boolean flag=true;
                   while(flag){
                	   String str1="**********请选择您要进行的操作**********\n"
                                   + "1、插入\n"
                                   +"2、查询\n"
                                   +"3、更新\n"
                                   + "4、删除\n"
                                   +"5、返回主菜单\n";
                	   String i=showInputDialog(str1);
                	   int ac=Integer.parseInt(i);
                           String str1t;
                	   switch(ac){
                	   case 1:
                		    str1t="*****请选择要插入的表格*****";
                		   String p=showInputDialog(str1t);
                		   boolean ty=true;
                		   while(ty){
                			   user c=new user();               		   
                    		   str1t="*****请输入插入数据的title*****";
                    		   String bc=showInputDialog(str1t);     
                    		   c.setTitle(bc);
                    		   str1t="*****请输入插入数据的price*****";
                    		   String x=showInputDialog(str1t);
                    		   c.setPrice(Integer.parseInt(x));
                    		   str1t="*****请输入插入数据的person*****";
                    		   String d=showInputDialog(str1t);
                    		   c.setPerson(Integer.parseInt(d)); 
                    		   insql="insert into "+p+"(title,price,person) values(?,?,?)";
                    		   InsertSql(insql,c);
                    		   str1t="1.继续输入\n"
                                           + "0.回到上一级菜单";
                    		   String z=showInputDialog(str1t);
                    		   if(Integer.parseInt(z)==0)
                    			   ty=false;
                		   }
                		   break;
                	   case 2:
                		   str1t="*****请选择要查询的表*****";
                		   String m=showInputDialog(str1t);
                                 //  int t=m.charAt(m.length()-1);
                                   boolean q=true;    		  
                		   while(q){
                			   str1t="*****请输入要查询的id*****";
                			   String y=showInputDialog(str1t);
                                           if(jedis.exists(m+y)){
                                             //  System.out.println("Redis");
                                           showMessageDialog(null,jedis.get(m+y));
                                           }
                                           else{
                                               jedis.del(m+y);
                			   sql="select * from "+m+" where idtext="+y;
                			   SelectSql(sql,m,y);
                                           }
                			   str1t="1.继续输入\n"+"0.回到上一级菜单";
                    		   String z=showInputDialog(str1t);
                    		   if(z.equals("0"))
                    			   q=false;
                		   }      		   
                		   //sql="select * from "+m;
                		  // SelectSql(sql);
                		   break;
                	   case 3:
                		   str1t="*****请选择更新的表******";
                		   String n=showInputDialog(str1t);
                		   boolean k=true;
                		   while(k){
                			   str1t="*****请选择要更新的id*****";
                			   String y=showInputDialog(str1t);
                			   boolean pt=true;
                    		   while(pt){
                    	           str1t="*****请选择要更新的数据项*****\n"+
                            	   "1、title\n"+
                            	   "2、price\n"+
                            	   "3、person\n"+
                            	   "4、quit\n";
                            	   String u=showInputDialog(str1t);
                            	   switch(Integer.parseInt(u)){
                            	   		case 1:
                            	   			str1t="*****请输入更新数据的title*****";
                                 		    String w=showInputDialog(str1t);
                                 		   upsql="update "+n+" set title='"+w+"' where idtext="+y;
                                 		  UpdateSql(upsql);    
                                                   if(jedis.exists(n+y)){
                                                   Statement statement=conn.createStatement();
                                                   rs=statement.executeQuery("select * from "+n+" where idtext="+y);
                                                     while(rs.next()){
                                                        jedis.set(n+y,"title:"+w+"  "+"price:"+rs.getString("price")+"  "+"person:"+rs.getString("person")+"\n");
                                                          }   
                                                   } 
                                                   rs.close();
                                 		   break;
                            	   		case 2:
                            	   		str1t="*****请输入更新数据的price*****";
                                 		    String e=showInputDialog(str1t) ;         
                                 		   upsql="update "+n+" set price='"+e+"' where idtext="+y;
                                 		  UpdateSql(upsql);
                                                               if(jedis.exists(n+y)){
                                                                     Statement statement=conn.createStatement();
                                                                   rs=statement.executeQuery("select * from "+n+" where idtext="+y);
                                                                  while(rs.next()){
                                                                  jedis.set(n+y,"title:"+rs.getString("title")+"  "+"price:"+e+"  "+"person:"+rs.getString("person")+"\n");
                                                                  
                                                                  }
                                                               }
                                 		   break;
                            	   		case 3:
                            	   			str1t="*****请输入更新数据的person*****";
                                 		   String r=showInputDialog(str1t);
                                 		  upsql="update "+n+" set person='"+r+"' where idtext="+y;
                                 		 UpdateSql(upsql);  
                                                   if(jedis.exists(n+y)){
                                                         Statement statement=conn.createStatement();
                                                         rs=statement.executeQuery("select * from "+n+" where idtext="+y);
                                                         while(rs.next()){
                                                            jedis.set(n+y,"title:"+rs.getString("title")+"  "+"price:"+rs.getString("price")+"  "+"person:"+r+"\n");
                                                             }    
                                                   }   
                                 		  break;
                            	   		case 4:
                            	   			pt=false;
                            	   			break;
                            	   }
                    		   }
                    		   str1t="1.继续输入\n"+"0.回到上一级菜单";
                    		   String z=showInputDialog(str1t);
                    		   if(Integer.parseInt(z)==0)
                    			   k=false;
                    		   else
                    			   k=true;
                		   }
                                          
                		   
                		   break;
                	   case 4:
                		   str1t="*****请选择要删除元素所在的表*****";
                		   String j=showInputDialog(str1t);
                		   boolean h=true;    		  
                		   while(h){
                			   str1t="*****请选择要删除的id*****";
                			   String y=showInputDialog(str1t);
                                                      if(jedis.exists(j+y)){
                                             jedis.del((j.charAt(j.length()-1) -1)*10000+y);
                                           }
                                                      
                			   delsql="delete from "+j+" where idtext="+y;
                			   DeletSql(delsql);
                                                      
                			   str1t="1.继续输入\n"+"0.回到上一级菜单";
                    		   String z=showInputDialog(str1t);
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
        		   String str1t="请输入创建数据表的指令（仅限一行）";
        		   cresql=showInputDialog(str1t);
        		   Create(cresql);
        		   break;
        	   case 4:
        		    str1t="请输入删除数据表的名称";
        		   dropsql=showInputDialog(str1t);
        		   Drop(dropsql);
        		   break;
        	   case 5:
        		   str1t="请输入表1的名称：";
        		   String s1=showInputDialog(str1t);
        		   str1t="请输入表2的名称：";
        		   String s2=showInputDialog(str1t);
        		   str1t="请输入关联的关键字：";
        		   String s3=showInputDialog(str1t);
        		   joinsql="select t1.title,t1.price from "+s1+" t1"+" left outer join "+s2+" t2"+" on "+"t1."+s3+" = "+"t2."+s3;
        		   Join(joinsql);
        		   break;
        	   case 6:
        		   fg=false;
        		   break;
        	   }
    	   }
    	  
       }catch (SQLException e) {
            showMessageDialog(null,"MySQL操作有误");
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           conn.close();
       }  

}
}
