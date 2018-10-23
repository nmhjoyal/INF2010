// Toute modification a ce fichier ne sera pas comptabilisée
public class Main {

    // Le main fait simplement tester votre logique pour les deux exercices
    public static void main(String[] args) {
        System.out.println("Debut des tests du TP3");
        TestBinary testBinary = new TestBinary();
        testBinary.test();
//
        TestCompany testCompany = new TestCompany();
        testCompany.test();

        /*NOTRE PROPRE TEST*/

//        BinarySearchTree<Integer> treeTest = new BinarySearchTree<Integer>(50);
//        treeTest.insert(25);
//        treeTest.insert(2);
//        treeTest.insert(45);
//        treeTest.insert(33);
//        treeTest.insert(3);
//        treeTest.insert(66);
//        treeTest.insert(78);
//
//        System.out.println(treeTest.contains(44) + "\n");
//        System.out.println(treeTest.contains(33) + "\n");
//        System.out.println(treeTest.contains(50) + "\n");
//
//        treeTest.insert(80);
//        //ajout ci-bas devrait donner hauteur = 4
//        //treeTest.insert(81);
//
//        System.out.println(treeTest.contains(80) + "\n");
//        System.out.println(treeTest.getHeight() + "\n");
//        System.out.println(treeTest.toStringInOrder());

//        CompanyTree compTree = new CompanyTree(new CompanyNode(400));
//        CompanyNode node11 = new CompanyNode(50);
//        CompanyNode node12 = new CompanyNode(5);
//        CompanyNode node21 = new CompanyNode(30);
//        CompanyNode node22 = new CompanyNode(10);
//        CompanyNode node31 = new CompanyNode(90);
//        CompanyNode node32 = new CompanyNode(-60);
//        CompanyNode node33 = new CompanyNode(-30);
//        CompanyNode node34 = new CompanyNode(-15);
//
//        compTree.buy(node11);
//        compTree.buy(node12);
//        node11.buy(node21);
//        node11.buy(node22);
//        node21.buy(node31);
//        node21.buy(node32);
//        node22.buy(node33);
//        node22.buy(node34);
//
//        System.out.println(compTree.getWorstChildMoney() +"\n");
//        System.out.println(compTree.getMoney() +"\n");
//        System.out.println(compTree.getTreeInOrder() +"\n");
    }
}
