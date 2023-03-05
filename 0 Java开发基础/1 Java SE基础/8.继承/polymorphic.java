package The8Class;

public class polymorphic {
    public static void main(String[] args) {
        Feeder fd = new Feeder("Tom");

        Dog dog = new Dog();

        Bone bone = new Bone(500);

        fd.feed(dog, bone);

        System.out.println("-----------------");

        fd.feed(new Cat(), new Fish(100));
    }
}

class Fish extends Food {
    public Fish(int weight) {
        this.weight = weight;
    }

    public String toString() {

        return this.getClass().getSimpleName();
    }
}

class Bone extends Food {
    public Bone(int weight) {
        this.weight = weight;
    }

    public String toString() {

        return this.getClass().getSimpleName();
    }
}

class Food {
    public int weight;

    public int getWeight() {
        return weight;
    }
}

class Dog extends Animal {
    public void eat(Food food) {
        super.eat(food);
        //获取类名
        System.out.println(this.toString()+" like " + food.getClass().getSimpleName());
    }

    public String toString() {

        return this.getClass().getSimpleName();
    }

}

class Cat extends Animal {

    public void eat(Food food) {
        super.eat(food);
        //获取类名
        System.out.println(this.toString()+" like " + food.getClass().getSimpleName());
    }

    public String toString() {

        return this.getClass().getSimpleName();
    }
}

class Animal {

    public void eat(Food food) {
        //获取类名
        System.out.println("It's time to eat " + food.getClass().getSimpleName()+".");


    }
}

class Feeder {

    private String name;

    public Feeder(String name) {

        this.name = name;

    }

    public void feed(Animal a, Food f) {

        a.eat(f);

        System.out.println("Feeder " + name + " feed " + a + " with " + f.getWeight() + "g " + f + ".");

    }

}

