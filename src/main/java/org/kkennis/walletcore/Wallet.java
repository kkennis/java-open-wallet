package org.kkennis.walletcore;

import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.UnreadableWalletException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Wallet {
    public String label;
    public Keychain keychain;
    public String network = "testnet";

    private Wallet(String label, Keychain keychain) {
        this.label = label;
        this.keychain = keychain;
    }

    public static Wallet create(String label) {
        return new Wallet(label, Keychain.createNew());
    }

    public static Wallet createFromSeed(String label, String mnemonic, long creationTimeSeconds) throws UnreadableWalletException {
        return new Wallet(label, Keychain.createFromSeed(mnemonic, creationTimeSeconds));
    }

    public static Wallet importSeedFromFile(String filePath) throws IOException, UnreadableWalletException {
        HashMap<String, String> walletValues = Wallet.parseWalletFile(filePath);

        String label = walletValues.get("label");
        String mnemonic = walletValues.get("seed");
        long creationTimeSeconds = Long.parseLong(walletValues.get("creationTime"));

        return Wallet.createFromSeed(label, mnemonic, creationTimeSeconds);
    }

    public static HashMap<String, String> parseWalletFile(String filePath) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(filePath));
        final HashMap<String, String> walletValues = new HashMap<>();

        String line;

        while ((line = reader.readLine()) != null) {
            // Test file for ending and beginning lines
            if (line.equals("-----WALLET DATA-----")) {
                continue;
            }

            if (line.equals("-----END-----")) {
                break;
            }

            String[] pair = line.split("=");
            walletValues.put(pair[0], pair[1]);
        }

        reader.close();

        return walletValues;
    }

    public DeterministicKey deriveKey(String path) throws PathFormatException {
        // Split path into List<ChildNumber>
        // This is a 'dumb' derive method, will use bitcoinj get Keys with KeyPurpose for real use

        if (path.isEmpty()) {
            return this.keychain.singleSigDerivationKeychain.getWatchingKey();
        }

        List<String> pieces = Arrays.asList(path.split("/"));

        List<ChildNumber> fullPath = pieces.stream()
                .map(p -> {
                    if (!p.matches("^\\d+'?$")) {
                        throw new PathFormatException();
                    }

                    if (p.endsWith("'")) {
                        int piece = Integer.parseInt(p.substring(0, p.length() - 1));
                        return new ChildNumber(piece, true);
                    } else {
                        int piece = Integer.parseInt(p);
                        return new ChildNumber(piece, false);
                    }
                })
                .collect(Collectors.toList());

        return this.keychain.singleSigDerivationKeychain.getKeyByPath(fullPath, true);
    }

    public String keyToAddress(DeterministicKey key) {
        byte[] addressBytes = key.getPubKeyHash();
        LegacyAddress address = LegacyAddress.fromPubKeyHash(this.keychain.networkParams, addressBytes);
        return address.toString();
    }

    public String deriveAddress(String path) throws PathFormatException {
       DeterministicKey key = this.deriveKey(path);
       return keyToAddress(key);
    }

    public void exportToFile(String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        final StringBuilder walletData = new StringBuilder("-----WALLET DATA-----");
        walletData.append("\nlabel=").append(label);
        walletData.append("\npub=").append(keychain.pub);
        walletData.append("\nseed=").append(keychain.getMnemonic());
        walletData.append("\ncreationTime=").append(keychain.getCreationTimeSeconds());
        walletData.append("\nnetwork=").append(Keychain.network);
        walletData.append("\n-----END-----");

        fileWriter.write(walletData.toString());
        fileWriter.close();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Wallet{");
        sb.append("\n\tlabel=").append(label);
        sb.append("\n\tkeychain=").append(keychain);
        sb.append("\n\tnetwork=").append(Keychain.network);
        sb.append("\n}");
        return sb.toString();
    }

}

;



