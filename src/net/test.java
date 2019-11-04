package net;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
class user{//�ڲ��࣬���ֶζ�Ӧ������š���ȡ���ݿ��е�����
    int id;
    static String title="";
    int price;
    int person;

    //ͨ��set�����������ʵ�����š�����
    //ͨ��get�����������ʵ�����á����ݣ�Ȼ����ͨ���������ݿ�
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
            	 System.out.println("*****����ɹ�*****");
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
	            int result=ps.executeUpdate();//������������0
	            if(result>0){
	            	System.out.println("���³ɹ�");
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
	            	System.out.println("ɾ���ɹ�.......");
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
	            	System.out.println("���ݱ����ɹ�.......");
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
	            	System.out.println("���ݱ�ɾ���ɹ�.......");
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
	            	 System.out.println("����ʧ��......");
	             while(rst.next()){
	            	 System.out.println("����ɹ�......");
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
			   System.out.println("*****���ݿ��������ı�Ϊ*****");
		       System.out.println("������" + rb.getString(3));  
		       }
		   return;
	 }
   public static void main(String[] args) throws Exception {
       String sql;
       
       try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           //�����mysql����ǰ�洴��������Ϊmysql������Դ
           conn = DriverManager.getConnection("jdbc:odbc:311","root","ab123321");
           // ���������root���α�ʾMySQL���ݿ�ĵ�½�û��������룬�����Լ������ö�����
           Statement stmt = conn.createStatement();
    	   System.out.println("********************mysql����ϵͳ********************");
    	   boolean fg=true;
    	   while(fg){
    		   System.out.println("�����ӵ�311���ݿ⣬��ѡ����Ҫ���еĲ���......");
        	   System.out.println("1����ѯ�������ݱ�");
        	   System.out.println("2�����������ݱ���в���");
        	   System.out.println("3���������ݱ�");
        	   System.out.println("4��ɾ�����ݱ�");
        	   System.out.println("5�������ݱ�");
        	   System.out.println("6���˳�");
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
                	   System.out.println("**********��ѡ����Ҫ���еĲ���**********");
                	   System.out.println("1������");
                	   System.out.println("2����ѯ");
                	   System.out.println("3������");
                	   System.out.println("4��ɾ��");
                	   System.out.println("5���������˵�");
                	   Scanner sc = new Scanner(System.in);
                	   String i=sc.nextLine();
                	   int ac=Integer.parseInt(i);
                	   switch(ac){
                	   case 1:
                		   System.out.println("*****��ѡ��Ҫ����ı��*****");
                		   String p=sc.nextLine();
                		   boolean ty=true;
                		   while(ty){
                			   user c=new user();               		   
                    		   System.out.println("*****������������ݵ�title*****");
                    		   String bc=sc.nextLine();     
                    		   c.setTitle(bc);
                    		   System.out.println("*****������������ݵ�price*****");
                    		   String x=sc.nextLine();
                    		   c.setPrice(Integer.parseInt(x));
                    		   System.out.println("*****������������ݵ�person*****");
                    		   String d=sc.nextLine();
                    		   c.setPerson(Integer.parseInt(d)); 
                    		   insql="insert into "+p+"(title,price,person) values(?,?,?)";
                    		   InsertSql(insql,c);
                    		   System.out.println("����������1���ص���һ���˵�������0");
                    		   String z=sc.nextLine();
                    		   if(Integer.parseInt(z)==0)
                    			   ty=false;
                		   }
                		   break;
                	   case 2:
                		   System.out.println("*****��ѡ��Ҫ��ѯ�ı�*****");
                		   String m=sc.nextLine();
                		   sql="select * from "+m;
                		   SelectSql(sql);
                		   break;
                	   case 3:
                		   System.out.println("*****��ѡ����µı�*****");
                		   String n=sc.nextLine();
                		   boolean k=true;
                		   while(k){
                			   System.out.println("*****��ѡ��Ҫ���µ�id*****");
                			   String y=sc.nextLine();
                			   boolean pt=true;
                    		   while(pt){
                    			   System.out.println("*****��ѡ��Ҫ���µ�������*****");
                            	   System.out.println("1��title");
                            	   System.out.println("2��price");
                            	   System.out.println("3��person");
                            	   System.out.println("4��quit");
                            	   String u=sc.nextLine();
                            	   switch(Integer.parseInt(u)){
                            	   		case 1:
                            	   			System.out.println("*****������������ݵ�title*****");
                                 		    String w=sc.nextLine();
                                 		   upsql="update "+n+" set title="+w+" where idtext="+y;
                                 		  UpdateSql(upsql);
                                 		   break;
                            	   		case 2:
                            	   			System.out.println("*****������������ݵ�price*****");
                                 		    String e=sc.nextLine();
                                 		   upsql="update "+n+" set price="+e+" where idtext="+y;
                                 		  UpdateSql(upsql);
                                 		   break;
                            	   		case 3:
                            	   			System.out.println("*****������������ݵ�person*****");
                                 		   String r=sc.nextLine();
                                 		  upsql="update "+n+" set person="+r+" where idtext="+y;
                                 		 UpdateSql(upsql);
                                 		  break;
                            	   		case 4:
                            	   			pt=false;
                            	   			break;
                            	   }
                        		   
                    		   }
                    		   System.out.println("����������1���ص���һ���˵�������0");
                    		   String z=sc.nextLine();
                    		   if(Integer.parseInt(z)==0)
                    			   k=false;
                    		   else
                    			   k=true;
                		   }
                		   
                		   break;
                	   case 4:
                		   System.out.println("*****��ѡ��Ҫɾ��Ԫ�����ڵı�*****");
                		   String j=sc.nextLine();
                		   boolean h=true;    		  
                		   while(h){
                			   System.out.println("*****��ѡ��Ҫɾ����id*****");
                			   String y=sc.nextLine();
                			   delsql="delete from "+j+" where idtext="+y;
                			   DeletSql(delsql);
                			   System.out.println("����������1���ص���һ���˵�������0");
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
        		   System.out.println("�����봴�����ݱ��ָ�����һ�У�");
        		   cresql=s.nextLine();
        		   Create(cresql);
        		   break;
        	   case 4:
        		   System.out.println("������ɾ�����ݱ������");
        		   dropsql="drop table "+s.nextLine();
        		   Drop(dropsql);
        		   break;
        	   case 5:
        		   System.out.println("�������1�����ƣ�");
        		   String s1=s.nextLine();
        		   System.out.println("�������2�����ƣ�");
        		   String s2=s.nextLine();
        		   System.out.println("����������Ĺؼ��֣�");
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
           System.out.println("MySQL��������");
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           conn.close();
       }  

}
}