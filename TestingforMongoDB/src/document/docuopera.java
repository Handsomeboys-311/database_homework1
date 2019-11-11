package document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class docuopera {
    //---------------插入文档-------------------------
    public static void insertDocu(MongoCollection collection, Document document){
        try{
            collection.insertOne(document);
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    //---------------显示文档-------------------------
    public static String findDocu(MongoCollection collection){
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
    public static void updateDocu(MongoCollection collection, String oldKey, String oldValue,
            String newKey, String newValue){
        try{
            collection.updateOne(Filters.eq(oldKey,oldValue), 
                    new Document("$set",new Document(newKey,newValue)));
            
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    //---------------删除文档-------------------------
    public static void deleteDocu(MongoCollection collection,String Key, String Value){
        try{
            collection.deleteOne(Filters.eq(Key, Value));
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
