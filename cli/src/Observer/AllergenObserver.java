import kuchen.Allergen;

import java.io.Serializable;
import java.util.Set;

public class AllergenObserver implements Observer, Serializable {

    private static final long serialVersionUID = 2023;
    private Automat automat;
    Set<Allergen> allergene;

    public AllergenObserver(Automat automat) {
        this.automat = automat;
        this.automat.addObserver(this);
        allergene = this.automat.getAlleVorhandenenAllergene();
    }
    @Override
    public void update() {
        Set<Allergen> neuAllergenSet = this.automat.getAlleVorhandenenAllergene();

        if (!neuAllergenSet.equals(allergene)) {
            System.out.println("Allergens an der Automat wurden geändert");
        }

        this.allergene = neuAllergenSet;
    }
}
