package org.kkennis.walletcore;

public class PathFormatException extends NumberFormatException {
    public PathFormatException() {
        super("Derivation path component must be positive integer with optional apostrophe (if hardened)");
    }
}
