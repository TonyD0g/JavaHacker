import com.alibaba.fastjson.JSON;

public class StudentUnserialize02 {
    public static void main(String[] args) {
//        String jsonString ="{\"@type\":\"Student\",\"age\":6," +
//                "\"name\":\"hello\",\"address\":\"china\",\"properties\":{}}";
        String jsonString = "{\"@type\":\"Student\",\"age\":6," +
                "\"name\":\"hello\",\"address\":\"china\",\"Test\":\"123\",\"properties\":{}}";

        // 第一种情况：输出为 jsonObject
        Object obj = JSON.parseObject(jsonString);

        // 第二种情况：输出为 object
        // Object obj = JSON.parseObject(jsonString,Student.class);
        System.out.println(obj);
        System.out.println(obj.getClass().getName());
    }
}