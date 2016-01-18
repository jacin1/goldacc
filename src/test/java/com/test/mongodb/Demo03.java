package com.test.mongodb;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

/**
 * 测试mongodb集成spring
 * 
 * @author Nick
 * 
 */
public class Demo03 {
	
	MongoClient client;
	
	DB testDB;

	@Before
	public void startUp() throws Exception{
		 // 连接到 mongodb 服务
		client = new MongoClient( "localhost" , 27017 );
		//连接到数据库
		testDB=client.getDB("test");
		boolean isAuth=testDB.authenticate("xsyi", "xsyi123".toCharArray());
	}

	// 测试MapReduce
	@Test
	public void test3(){
		DBCollection books=testDB.getCollection("books");
		//Map
		String map="function(){"+
				"	var category; "+
				" if(this.pages >=300) "+
				" category='Big Books'; "+
				" else category='Small Books'; " +
				" emit(category,{name:this.name}); "+
				"}";
		String reduce="function(key,values){ "
				+ " var sum=0; "
				+ " values.forEach(function(doc){"
				+ "	sum +=1	;"
				+ "})	;"
				+ "	 return {books:sum} ;"
				+ "}" ;
		MapReduceCommand command=new MapReduceCommand(books, map, reduce, "tmp", MapReduceCommand.OutputType.REDUCE, null);
		MapReduceOutput out =books.mapReduce(command);
		//遍历输出的执行结果集
//		for(DBObject obj:out.results()){
//			System.out.println(obj.toString());
//			
//		}
		
		//如果有定义输出到具体的集合,可以遍历集合：表记录
		DBCollection tmp=out.getOutputCollection();
		DBCursor cursor=tmp.find();
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		
		
	}
}
