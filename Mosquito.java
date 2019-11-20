
public class Mosquito extends Insect {
    private Boolean momGene;
    private Boolean dadGene;

    public Mosquito(int row, int col, String sex, String type, Boolean rotation,
            Boolean momGene, Boolean dadGene) {
        super(row, col, sex, type, rotation);
        this.momGene = momGene;
        this.dadGene = dadGene;
    }

    // If both genes are true, then mosquito's parents
    // were both gene-edited and this mosquito cannot reproduce.
    public Boolean reproduce(Animal two) {
        if (!(momGene && dadGene)) {
            // checks for same class AND same species
            if (this.type().equals(two.type())) {
                Insect newTwo = (Insect) two;
                if (!this.sex.equals(newTwo.sex)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Called for later reproduction; passes an edited gene,
    // represented by true, to child.
    public Boolean editedGene() {
        return momGene || dadGene;
    }
}
