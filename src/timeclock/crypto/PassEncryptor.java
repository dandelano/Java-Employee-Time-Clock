/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timeclock.crypto;

import org.jasypt.digest.StandardStringDigester;

/**
 * This implements TCypto and provides password encryption and checking.
 * @author dannyjdelanojr
 */
public final class PassEncryptor implements TCrypto{
    
    // The internal digester used
    private final StandardStringDigester digester;
    
    /**
     * Creates a new instance of <tt>StrongPasswordEncryptor</tt>
     *
     */
    public PassEncryptor() {
        super();
        this.digester = new StandardStringDigester();
        this.digester.setAlgorithm("SHA-256");
        this.digester.setIterations(100000);
        this.digester.setSaltSizeBytes(16);
        this.digester.initialize();
    }
    
    
    /**
     * Encrypts (digests) a password.
     * 
     * @param password the password to be encrypted.
     * @return the resulting digest.
     * @see StandardStringDigester#digest(String)
     */
    public String encryptPassword(final String password) {
        return this.digester.digest(password);
    }

    
    /**
     * Checks an unencrypted (plain) password against an encrypted one
     * (a digest) to see if they match.
     * 
     * @param plainPassword the plain password to check.
     * @param encryptedPassword the digest against which to check the password.
     * @return true if passwords match, false if not.
     * @see StandardStringDigester#matches(String, String)
     */
    public boolean checkPassword(final String plainPassword, 
            final String encryptedPassword) {
        return this.digester.matches(plainPassword, encryptedPassword);
    }
    
}
