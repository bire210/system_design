package cor;

public abstract class MoneyHandler {
    protected MoneyHandler nextHandler;

    public MoneyHandler() {
        this.nextHandler = null;
    }

    public abstract void meneyDespense(int amount);

    public void setNextMoneyHandler(MoneyHandler moneyHandler) {
        this.nextHandler = moneyHandler;
    }
}

class FiveHundredHandler extends MoneyHandler {
    private int noOfNotes;

    public FiveHundredHandler(int notes) {
        this.noOfNotes = notes;
    }

    @Override
    public void meneyDespense(int amount) {
        int requiredNotes = amount / 500;

        if (requiredNotes > noOfNotes) {
            requiredNotes = noOfNotes;
            noOfNotes = 0;
        } else {
            noOfNotes -= requiredNotes;
        }

        if (requiredNotes > 0) {
            System.out.println("Despensing :" + requiredNotes + "* 500");
        }

        int remainingAmount = amount - requiredNotes * 500;
        if (remainingAmount > 0) {
            if (nextHandler != null) {
                nextHandler.meneyDespense(remainingAmount);
            } else {
                System.out.println(
                        "Remaining amount of " + remainingAmount + " cannot be fulfilled (Insufficinet fund in ATM)");

            }
        }
    }
}

class TwoHundredHandler extends MoneyHandler {
    private int noOfNotes;

    TwoHundredHandler(int notes) {
        this.noOfNotes = notes;
    }

    @Override
    public void meneyDespense(int amount) {
        int requiredNotes = amount / 200;
        if (requiredNotes > noOfNotes) {
            requiredNotes = noOfNotes;
            noOfNotes = 0;
        } else {
            noOfNotes = noOfNotes - requiredNotes;
        }

        if (requiredNotes > 0) {
            System.out.println("Despensing amount" + requiredNotes + "* 200");
        }

        int remainingAmount = amount - requiredNotes * 200;

        if (remainingAmount > 0) {
            if (nextHandler != null) {
                nextHandler.meneyDespense(remainingAmount);
            } else {
                System.out.println(
                        "Remaining amount of " + remainingAmount + " cannot be fulfilled (Insufficinet fund in ATM)");

            }
        }

    }

}

class OneHundredHandler extends MoneyHandler {
    private int noOfNotes;

    OneHundredHandler(int notes) {
        this.noOfNotes = notes;
    }

    @Override
    public void meneyDespense(int amount) {
        int requiredNotes = amount / 100;
        if (requiredNotes > noOfNotes) {
            requiredNotes = noOfNotes;
            noOfNotes = 0;
        } else {
            noOfNotes = noOfNotes - requiredNotes;
        }

        if (requiredNotes > 0) {
            System.out.println("Despensing :" + requiredNotes + "* 100");
        }

        int remainingAmount = amount - requiredNotes * 100;
        if (remainingAmount > 0) {
            if (nextHandler != null) {
                nextHandler.meneyDespense(remainingAmount);
            } else {
                System.out.println(
                        "Remaining amount of " + remainingAmount + " cannot be fulfilled (Insufficinet fund in ATM)");

            }
        }
    }

}