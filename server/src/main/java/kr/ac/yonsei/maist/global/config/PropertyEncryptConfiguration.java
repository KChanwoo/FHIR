/**
 * Decrypt automatically a important information in application.yaml using jasypt library
 *
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05 ~
 * @Date 2020.10.16
 * @Reference http://www.jasypt.org/
 */
package kr.ac.yonsei.maist.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableEncryptableProperties
public class PropertyEncryptConfiguration {
    @Autowired
    private Environment environment;

    /**
     * Create Encryptor Object
     * @return encryptor object
     */
    @Bean("stringEncryptor")
    public PooledPBEStringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setPoolSize(2);
        // get password from run environment
        encryptor.setPassword(environment.getProperty("jasypt.encryptor.password", "NONE"));
        encryptor.setAlgorithm("PBEWithSHA256And128BitAES-CBC-BC");
        return encryptor;
    }

    /**
     * create encrypted text
     * @param args never use
     */
    public static void main(String[] args) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setPoolSize(2);
        encryptor.setPassword("the secret key will be here"); // check jenkins build properties
        encryptor.setAlgorithm("PBEWithSHA256And128BitAES-CBC-BC");

        String plainText = ""; // insert data and execute when need to encrypt
        String encryptedText = encryptor.encrypt(plainText);
        String decryptedText = encryptor.decrypt(encryptedText);
        System.out.println("Enc:"+encryptedText+", Dec:"+decryptedText);
    }
}