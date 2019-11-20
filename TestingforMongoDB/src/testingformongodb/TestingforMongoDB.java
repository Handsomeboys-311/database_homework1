package testingformongodb;
import collection.collopera;
import document.docuopera;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import org.bson.Document;

public class TestingforMongoDB {
    private static boolean Mainmenub = true, Submenub = true;
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static int main, sub;
    private static String collName, key, value, nkey, nvalue;
    
    private static int mainMenu(){   //主菜单
        String str = "1,创建集合\n"
                + "2,选择并修改集合\n"
                + "3,显示当前数据库内容\n"
                + "4,退出\n";
        String choice = showInputDialog(str);
        return Integer.parseInt(choice);
    }
    private static int subMenu(){   //子菜单
        String str = "1,插入文档\n"
                + "2,显示文档\n"
                + "3,更新文档\n"
                + "4,删除文档\n"
                + "5,删除集合\n"
                + "6,返回\n";
        String choice = showInputDialog(str);
        return Integer.parseInt(choice);
    }
    public static void main(String[] args) {
        try {
            //连接到MongoDB服务
            mongoClient = new MongoClient("localhost", 27017);
            //连接到test数据库
            mongoDatabase = mongoClient.getDatabase("test");
            System.out.println("*****连接至test数据库*****");
            while (Mainmenub) {   //主菜单
                main = mainMenu();
                switch (main) {
                    case 1:   //创建集合
                        collName = showInputDialog("输入集合名");
                        collopera.creatColl(mongoDatabase, collName);   //调用collopera.java中的创建集合的方法
                        break;
                    case 2:   //选择并修改集合
                        collName = showInputDialog("输入集合名");   //通过集合名选中集合进行修改
                        MongoCollection mongoCollection = collopera.selectColl(mongoDatabase, collName);   //调用collopera.java中的选择集合的方法
                        Submenub = true;
                        while (Submenub) {   //子菜单
                            sub = subMenu();
                            switch (sub) {
                                case 1:   //插入文档
                                    Document docu;
                                    key = showInputDialog("输入键");
                                    value = showInputDialog("输入值");
                                    docu = new Document(key, value);
                                    docuopera.insertDocu(mongoCollection, docu);   //调用docuopera.java中的方法
                                    break;
                                case 2:   //显示文档
                                    showMessageDialog(null, docuopera.findDocu(mongoCollection));   //调用docuopera.java中的方法
                                    break;
                                case 3:   //更新文档
                                    key = showInputDialog("输入旧键");
                                    value = showInputDialog("输入旧值");
                                    nkey = showInputDialog("输入新键(新键与旧键不同时会在当前文档内创建新键值对)");
                                    nvalue = showInputDialog("输入新值");
                                    docuopera.updateDocu(mongoCollection, key, value, nkey, nvalue);   //调用docuopera.java中的方法
                                    break;
                                case 4:   //删除文档
                                    key = showInputDialog("输入键");
                                    value = showInputDialog("输入值");
                                    docuopera.deleteDocu(mongoCollection, key, value);   //调用docuopera.java中的方法
                                    break;
                                case 5:   //删除集合
                                    collopera.deleteColl(mongoCollection);   //调用docuopera.java中的方法
                                    Submenub = false;
                                    break;
                                case 6:   //返回
                                    Submenub = false;
                                    break;
                                default:
                                    showMessageDialog(null, "输入正确的数字");
                                    break;
                            }
                        }
                        break;
                    case 3:   //显示当前数据库内容
                        showMessageDialog(null, collopera.displayColl(mongoDatabase));   //调用collopera.java中的方法并显示消息框
                        break;
                    case 4:   //退出
                        Mainmenub = false;   //设置主菜单循环为false，结束程序
                        mongoClient.close();   //断开与MongoDB的连接
                        break;
                    default:
                        showMessageDialog(null, "输入正确数字");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}