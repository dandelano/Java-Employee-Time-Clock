/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.crypto;

/**
 * Interface class for crypto
 * @author dannyjdelanojr
 */
public interface TCrypto {

    /**
     * Encrypts (digests) a password.
     *
     * @param password the password to be encrypted.
     * @return the resulting digest.
     */
    public String encryptPassword(String password);

    /**
     * Checks an unencrypted (plain) password against an encrypted one (a
     * digest) to see if they match.
     *
     * @param plainPassword the plain password to check.
     * @param encryptedPassword the digest against which to check the password.
     * @return true if passwords match, false if not.
     */
    public boolean checkPassword(String plainPassword,
            String encryptedPassword);

}
