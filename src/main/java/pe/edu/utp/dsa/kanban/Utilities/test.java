package pe.edu.utp.dsa.kanban.Utilities;


public class test {

    public static void main(String[] args) {
        Role role = new Role("jegge");
        Role role2 = new Role("admi");
        User ns = new User("Diego", role);
        User ns1 = new User("Diego", role);
        User ns2 = new User("Diego", role2);
        System.out.println(ns.equals(ns1));
        System.out.println(ns.equals(ns2));

    }
}