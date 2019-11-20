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
            collection.insertOne(document);   //插入Document类型的键值对
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    //---------------显示文档-------------------------
    public static String findDocu(MongoCollection collection){
        try{
            String str = "";
            /*
             * 1. 获取迭代器FindIterable<Document> 
             * 2. 获取游标MongoCursor<Document> 
             * 3. 通过游标遍历检索出的文档集合
             * 4. 将遍历出的文档集合放入字符串中
             */
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
            /*
             *updateOne()方法通过选中一对键值对，对当前键值对所在的文档进行修改；
             *若新键与旧键相同，则修改该键值对的值；
             *若新键与旧键不同，则在该文档中增加一对新键值对。
             */
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
