package lt.martynassateika.cs313mobilerobotics;

/**
 * User: Martynas Sateika
 * Date: 14.2.8
 * Time: 03.32
 */
public class Application {

    public static void main(String[] args) {
        Model model = new Model();
        View view = new View("CS313 Mobile Robotics: Filter test");
        new Controller(model, view);
    }

}
