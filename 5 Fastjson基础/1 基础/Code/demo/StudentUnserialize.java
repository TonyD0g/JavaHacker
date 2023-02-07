import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class StudentUnserialize {
    public static void main(String[] args) {
        String jsonString = "{\"@type\":\"Student\",\"age\":6,\"name\":\"test\"}";
        Student student = JSON.parseObject(jsonString, Student.class, Feature.SupportNonPublicField);
        System.out.println("--------------------------");
        System.out.println("object: " + student);
        System.out.println("--------------------------");
        System.out.println(student.getClass().getName());
    }
}