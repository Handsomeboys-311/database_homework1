package testingformongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import org.bson.Document;

public class TestingforMongoDB {
    private static boolean Mainmenub = true, Submenub = true;
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static int main, sub;
    private static String collName, docuName;
    //---------------创建集合-------------------------
    private static void creatColl(String str){
        try{
            mongoDatabase.createCollection(str);
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    //---------------选择集合-------------------------
    private static MongoCollection selectColl(String str){
        try{
            MongoCollection<Document> collection = mongoDatabase.getCollection(str);
            return collection;
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
    //---------------显示集合-------------------------
    private static String displayColl(){
        try{
            String str = "";
            for(String name : mongoDatabase.listCollectionNames()){
                str += name + '\n';
            }
            return str;
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
    //---------------插入文档-------------------------
    private static void insertDocu(MongoCollection collection, Document document){
        try{
            collection.insertOne(document);
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    //---------------显示文档-------------------------
    private static String findDocu(MongoCollection collection){
        try{
            String str = "";
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while(mongoCursor.hasNext())
                str += mongoCursor.next().toString() + '\n';
            return str;
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
    //---------------更新文档-------------------------
    private static void updateDocu(MongoCollection collection, String oldKey, String oldValue,
            String newKey, String newValue){
        try{
            collection.updateOne(Filters.eq(oldKey,oldValue), 
                    new Document("$rename",new Document(newKey,newValue)));
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    //---------------删除文档-------------------------
    private static void deleteDocu(MongoCollection collection,String Key, String Value){
        try{
            collection.deleteOne(Filters.eq(Key, Value));
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    private static int mainMenu(){
        String str = "1,创建集合\n"
                + "2,选择并修改集合\n"
                + "3,显示当前数据库内容\n"
                + "4,删除集合\n"
                + "5,退出\n";
        String choice = showInputDialog(str);
        return Integer.parseInt(choice);
    }
    private static int subMenu(){
        String str = "1,插入文档\n"
                + "2,查找文档\n"
                + "3,更新文档\n"
                + "4,删除文档\n"
                + "5,返回\n";
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
                        creatColl(collName);
                        break;
                    case 2:   //选择并修改集合
                        collName = showInputDialog("输入集合名");
                        MongoCollection mongoCollection = selectColl(collName);
                        while (Submenub) {
                            sub = subMenu();
                            switch (sub) {
                                case 1:   //插入文档
                                    break;
                                case 2:   //显示文档
                                    showMessageDialog(null, findDocu(mongoCollection));
                                    break;
                                case 3:   //更新文档
                                    break;
                                case 4:   //删除文档
                                    break;
                                case 5:   //返回
                                    Submenub = false;
                                    break;
                                default:
                                    showMessageDialog(null, "输入正确的数字");
                                    break;
                            }
                        }
                        break;
                    case 3:   //显示当前数据库内容
                        showMessageDialog(null, displayColl());
                        break;
                    case 4:   //退出
                        Mainmenub = false;
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