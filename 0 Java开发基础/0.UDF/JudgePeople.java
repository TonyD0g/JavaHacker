//定义Person类，重写equals方法，根据姓名判断两个是否是同一个人
package UDF;

public class JudgePeople {
    public  static  void  main(String[]  args)
    {
        Person1  p1  =  new  Person1("张三",  20);
        Person1  p2  =  new  Person1("张三",  20);
        System.out.println(p1.equals(p2));
    }
}

class  Person1 {
    private String name;
    private int age;

    public Person1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person1 Obj2 = (Person1) obj;
        
        if (age != Obj2.age) return false;
        return name.equals(Obj2.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        return result;
    }
}
