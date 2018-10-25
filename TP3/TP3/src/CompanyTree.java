public class CompanyTree {

    private CompanyNode root;

    public CompanyTree() { }

    // TODO: initialisation
    public CompanyTree(CompanyNode item) {
        this.root = item;
    }

    // TODO: une compagnie mère achete une autre compagnie
    // O(n)
    public void buy(CompanyNode item) {
        this.root.buy(item);
        if(item.worstChild.getMoney() < this.root.worstChild.getMoney())
            this.root.worstChild = item.worstChild;
    }

    // TODO: on retourne le montant en banque de la compagnie mère
    // O(1)
    public Integer getMoney() {
        return this.root.getMoney();
    }

    // TODO: si on avait a laisser tomber un enfant, ça serait lui
    // Ceci est le pire de tous les enfants et les sous-enfants
    // O(1)
    public Integer getWorstChildMoney() {
        return this.root.worstChild.getMoney();
    }

    // TODO: on retourne en string un presentation en ordre inverse
    // de la compagnie mère et de ses enfants
    // O(1)
    public String getTreeInOrder() {
        StringBuilder builder = new StringBuilder();
        String prefix =  "";
        this.root.fillStringBuilderInOrder(builder, prefix);
        return builder.toString();
    }
}
