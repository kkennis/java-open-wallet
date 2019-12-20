package org.kkennis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Wallet {
    public String label;
    public Keychain keychain;
    public final UUID uuid;
    public String network = "testnet";

    private Wallet(String label, Keychain keychain) {
        this.uuid = UUID.randomUUID();
        this.label = label;
        this.keychain = keychain;
    }

    public static Wallet create(String label) {
        return new Wallet(label, Keychain.createNew());
    }

    // Add function to write wallet to a file

//    public Address getAddress(String path) {
//        return Address.derive(this.masterKey, path);
//    }

    public void exportToFile(String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        final StringBuilder walletData = new StringBuilder("-----WALLET DATA-----");
        fileWriter.append("\n\tlabel='").append(label).append('\'');
        fileWriter.append("\n\tpub='").append(this.keychain.pub).append('\'');
        fileWriter.append("\n\tprv='").append(this.keychain.prv).append('\'');
        fileWriter.append("\n\tnetwork='").append(network).append('\'');

        fileWriter.write(walletData.toString());

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Wallet{");
        sb.append("\n\tlabel='").append(label).append('\'');
        sb.append("\n\tkeychain='").append(this.keychain).append('\'');
        sb.append("\n\tnetwork='").append(network).append('\'');
        sb.append("\n}");
        return sb.toString();
    }

}



