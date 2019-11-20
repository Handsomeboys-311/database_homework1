package collection;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class collopera {
    //---------------创建集合-------------------------
    public static void creatColl(MongoDatabase mongoDatabase, String str){
        try{
            mongoDatabase.createCollection(str);
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    //---------------选择集合-------------------------
    public static MongoCollection selectColl(MongoDatabase mongoDatabase, String str){
        try{
            MongoCollection<Document> collection = mongoDatabase.getCollection(str);
            return collection;   //返回选中的集合
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
    //---------------删除集合-------------------------
    public static void deleteColl(MongoCollection mongoCollection){
        try{
            mongoCollection.drop();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    //---------------显示集合-------------------------
    public static String displayColl(MongoDatabase mongoDatabase){
        try{
            String str = "";
            for(String name : mongoDatabase.listCollectionNames()){
                str += name + '\n';
            }
            return str;   //返回由集合内容组成的字符串
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
}
