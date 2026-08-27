class Main {

    public static void main(String[] args) {
        System.out.println("Builder pattern");

        Computer newCom = new Computer.Builder("Intel16", 1024, 4096).grapicsCard(true).build();
        System.out.println(newCom.toString());
    }
}

class Computer {
    private String cpu;
    private int ram;
    private int storage;
    private boolean graphicCard;
    private boolean wifi;
    private boolean bluetooth;
    private boolean rgbLighting;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.graphicCard = builder.graphicCard;
        this.wifi = builder.wifi;
        this.bluetooth = builder.bluetooth;
        this.rgbLighting = builder.rgbLighting;
    }

    @Override
    public String toString() {

        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram=" + ram +
                ", storage=" + storage +
                ", graphicsCard=" + graphicCard +
                ", wifi=" + wifi +
                ", bluetooth=" + bluetooth +
                ", rgbLighting= " + rgbLighting +
                '}';
    }

    public static class Builder {
        // required

        private String cpu;
        private int ram;
        private int storage;

        // optional
        private boolean graphicCard;
        private boolean wifi;
        private boolean bluetooth;
        private boolean rgbLighting;

        public Builder(String cpu, int ram, int storage) {
            this.cpu = cpu;
            this.ram = ram;
            this.storage = storage;
        }

        public Builder grapicsCard(boolean value) {
            this.graphicCard = value;
            return this;
        }

        public Builder wifi(boolean value) {
            this.wifi = value;
            return this;
        }

        public Builder bluetooth(boolean value) {
            this.bluetooth = value;
            return this;
        }

        public Builder rgbLigtning(boolean value) {
            this.rgbLighting = value;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}