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
    
    private static int mainMenu(){
        String str = "1,创建集合\n"
                + "2,选择并修改集合\n"
                + "3,显示当前数据库内容\n"
                + "4,退出\n";
        String choice = showInputDialog(str);
        return Integer.parseInt(choice);
    }
    private static int subMenu(){
        String str = "1,插入文档\n"
                + "2,查找文档\n"
                + "3,更新文档\n"
                + "4,删除文档\n"
                + "5,删除集合\n"
                + "6,返回\n";
        String choice = showInputDialog(str);
        return Integer.parseInt(choice);
    }
    public static void main(String[] args) {
        try {
            mongoClient = new MongoClient("localhost", 27017);
            mongoDatabase = mongoClient.getDatabase("test");
            System.out.println("*****连接至test数据库*****");
            while (Mainmenub) {
                main = mainMenu();
                switch (main) {
                    case 1:   //创建集合
                        collName = showInputDialog("输入集合名");
                        collopera.creatColl(mongoDatabase, collName);
                        break;
                    case 2:   //选择并修改集合
                        collName = showInputDialog("输入集合名");
                        MongoCollection mongoCollection = collopera.selectColl(mongoDatabase, collName);
                        Submenub = true;
                        while (Submenub) {
                            sub = subMenu();
                            switch (sub) {
                                case 1:   //插入文档
                                    Document docu;
                                    key = showInputDialog("输入键");
                                    value = showInputDialog("输入值");
                                    docu = new Document(key, value);
                                    docuopera.insertDocu(mongoCollection, docu);
                                    break;
                                case 2:   //显示文档
                                    showMessageDialog(null, docuopera.findDocu(mongoCollection));
                                    break;
                                case 3:   //更新文档
                                    key = showInputDialog("输入旧键");
                                    value = showInputDialog("输入旧值");
                                    nkey = showInputDialog("输入新键(新键与旧键不同时会创建新键值对)");
                                    nvalue = showInputDialog("输入新值");
                                    docuopera.updateDocu(mongoCollection, key, value, nkey, nvalue);
                                    break;
                                case 4:   //删除文档
                                    key = showInputDialog("输入键");
                                    value = showInputDialog("输入值");
                                    docuopera.deleteDocu(mongoCollection, key, value);
                                    break;
                                case 5:   //删除集合
                                    collopera.deleteColl(mongoCollection);
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
                        showMessageDialog(null, collopera.displayColl(mongoDatabase));
                        break;
                    case 4:   //退出
                        Mainmenub = false;
                        mongoClient.close();
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