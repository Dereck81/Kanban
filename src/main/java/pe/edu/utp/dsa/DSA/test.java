package pe.edu.utp.dsa.DSA;

import pe.edu.utp.dsa.Kanban.Task.Role;
import pe.edu.utp.dsa.Kanban.Task.User;

public class test {

    public static void main(String[] args) {

        PriorityQueue<User> e = new PriorityQueue<>();
        e.enqueue(new User("sadsad", new Role("sdads")));
        e.enqueue(new User("aadsad", new Role("sdads")));
        System.out.println(e.size());
        e.getElement(1);


    }

}
