import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * This plugin provides a command to dump a server's keypair.
 * @author Willem Mulder
 */
public class KeyDumper extends Plugin {
    public static final Logger LOG = Logger.getLogger("Minecraft.KeyDumper");
    public static final String NAME = "KeyDumper";
    public static final String VERSION = "0.1-142";
    public static final String AUTHOR = "14mRh4X0r";
    
    public static final BaseCommand DUMPKEYS = new BaseCommand(
            "- Saves this server's keypair to file", "Usage: /dumpkeys", 1, 1) {

        @Override
        protected void execute(MessageReceiver caller, String[] parameters) {
            KeyPair pair = etc.getMCServer().F();
            
            // The following was taken from http://snipplr.com/view/18368/
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            try {
                // Store Public Key.
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                FileOutputStream fos = new FileOutputStream("publicKey.der");
                fos.write(x509EncodedKeySpec.getEncoded());
                fos.close();

                // Store Private Key.
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                fos = new FileOutputStream("privateKey.der");
                fos.write(pkcs8EncodedKeySpec.getEncoded());
                fos.close();
            } catch (IOException e) {
                caller.notify("Unable to save keypair: " + e.getMessage());
                LOG.log(Level.SEVERE, "Exception while saving keypair: ", e);
            }
            caller.notify("Keypair successfully saved.");
        }
    };
    
    static {
        LOG.setFilter(new Filter() {
            @Override
            public boolean isLoggable(LogRecord record) {
                record.setMessage("[" + NAME + "] " + record.getMessage());
                return true;
            }
        });
    }

    @Override
    public void enable() {
        ServerConsoleCommands.getInstance().add("dumpkeys", DUMPKEYS);
        LOG.info("Version " + VERSION + " by " + AUTHOR + " enabled.");
    }

    @Override
    public void disable() {
        if (ServerConsoleCommands.getInstance().getCommand("dumpkeys") == DUMPKEYS) {
            ServerConsoleCommands.getInstance().remove("dumpkeys");
        }
    }

}
