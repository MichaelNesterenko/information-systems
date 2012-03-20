package mishanesterenko.informationsystems.logicsolve.domain;

/**
 * @author Michael Nesterenko
 *
 */
public class Production {
    private String ifPart;
    private String thenPart;

    public Production(final String ifP, final String thenP) {
        ifPart = ifP;
        thenPart = thenP;
    }

    public String getIf() {
        return ifPart;
    }

    public String getThen() {
        return thenPart;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Production)) {
            return false;
        }
        Production op = (Production) o;
        return (op.ifPart == ifPart || ifPart != null && ifPart.equals(op.ifPart))
                && (op.thenPart == thenPart || thenPart != null && thenPart.equals(op.thenPart));
    }

    @Override
    public int hashCode() {
        return (ifPart == null ? 0 : ifPart.hashCode()) ^ (thenPart == null ? 0 : thenPart.hashCode());
    }

    @Override
    public String toString() {
        return ifPart + "->" + thenPart;
    }
}
