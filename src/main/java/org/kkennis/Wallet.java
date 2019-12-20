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
        walletData.append("\nlabel='").append(label).append('\'');
        walletData.append("\npub='").append(this.keychain.pub).append('\'');
        walletData.append("\nprv='").append(this.keychain.prv).append('\'');
        walletData.append("\nnetwork='").append(network).append('\'');
        walletData.append("\n-----END-----");

        fileWriter.write(walletData.toString());
        fileWriter.close();
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



